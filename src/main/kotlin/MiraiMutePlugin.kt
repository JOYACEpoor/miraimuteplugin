package nya.xfy

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.info
import java.util.*

object MiraiMutePlugin : KotlinPlugin(JvmPluginDescription(id = "nya.xfy.miraimuteplugin", version = "1.1.3")) {

    private val map = MiraiMutePluginData.mutableMap
    override fun onEnable() {
        logger.info { "Plugin loaded" }
        MiraiMutePluginData.reload()
        MiraiMutePluginConfig.reload()

        if (MiraiMutePluginConfig.clearTime > -1&&MiraiMutePluginConfig.clearTime<24) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR, MiraiMutePluginConfig.clearTime)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            Timer().schedule(object : TimerTask() { override fun run() { map.clear() } }, Date(calendar.timeInMillis), 86400000)
        }

        for (_groupId in MiraiMutePluginConfig.groupList) {
            var num = 1
            var preMessage = ""
            var preSenderId: Long = 0
            GlobalEventChannel.filter { it is GroupMessageEvent && it.group.id == _groupId }.subscribeAlways<GroupMessageEvent> {
                if (group.botPermission > MemberPermission.MEMBER) {
                    if (sender.id == preSenderId && message.serializeToMiraiCode() == preMessage) {
                        num++
                        if (num >= 3) {
                            if (group.botPermission > sender.permission) {
                                if (map.containsKey(sender.id)) {
                                    if (map.getValue(sender.id) * MiraiMutePluginConfig.timer > MiraiMutePluginConfig.max) {
                                        map[sender.id] = MiraiMutePluginConfig.max
                                    } else {
                                        map[sender.id] = map.getValue(sender.id) * MiraiMutePluginConfig.timer
                                    }
                                } else {
                                    map[sender.id] = MiraiMutePluginConfig.min
                                }
                                sender.mute(map.getValue(sender.id))
                            }
                            num = 0
                        }
                    } else {
                        preSenderId = sender.id
                        preMessage = message.serializeToMiraiCode()
                        num = 1
                    }
                }
            }
        }
    }

    override fun onDisable() {
        MiraiMutePluginData.mutableMap = map
        super.onDisable()
    }
}