package nya.xfy

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info

object Miraimuteplugin : KotlinPlugin(JvmPluginDescription(id = "nya.xfy.miraimuteplugin", version = "1.0.0")) {

    private var num: Int = 1
    private var preMessage: String = ""
    private var preSenderId: Long = 0

    override fun onEnable() {
        logger.info { "Plugin loaded" }
        this.globalEventChannel().subscribeAlways<GroupMessageEvent> {
            if (sender.id == preSenderId && message.serializeToMiraiCode() == preMessage) {
                num++
                if (num >= 3) {
                    if (group.botPermission > sender.permission)
                        sender.mute(60)
                    num = 0
                }
            }else {
                preSenderId = sender.id
                preMessage = message.serializeToMiraiCode()
                num=1
            }
        }
    }
}