package nya.xfy

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ConsoleExperimentalApi::class)
object MiraiMutePlugin : KotlinPlugin(JvmPluginDescription(id = "nya.xfy.MiraiMutePlugin", version = "1.1.0")) {

    private var num: Int = 1
    private var preMessage: String = ""
    private var preSenderId: Long = 0
    private lateinit var map: MutableMap<Long, Int>

    override fun onEnable() {
        logger.info { "Plugin loaded" }

        MiraiMutePluginData.reload()
        MiraiMutePluginConfig.reload()

        map = MiraiMutePluginData.mutableMap

        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).equals(MiraiMutePluginConfig.clearTime))
                    map.clear()
            }
        }, Date(), 60000)

        this.globalEventChannel().subscribeAlways<GroupMessageEvent> {
            if (sender.id == preSenderId && message.serializeToMiraiCode() == preMessage) {
                num++
                if (num >= 3) {
                    if (group.botPermission > sender.permission) {
                        if (map.containsKey(sender.id)) {
                            if (map.getValue(sender.id) * MiraiMutePluginConfig.timer <= MiraiMutePluginConfig.max) {
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

    override fun onDisable() {
        MiraiMutePluginData.mutableMap = map
        super.onDisable()
    }
}