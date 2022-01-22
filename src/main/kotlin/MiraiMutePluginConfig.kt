package nya.xfy

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object MiraiMutePluginConfig: AutoSavePluginConfig("MiraiMutePluginConfig") {
    val min: Int by value(60)
    val max: Int by value(7200)
    val timer: Int by value(2)
    val clearTime: Int by value(4)
    val groupList: List<Long> by value()
}