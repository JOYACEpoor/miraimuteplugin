package nya.xfy

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object MiraiMutePluginConfig: AutoSavePluginConfig("MiraiMutePluginConfig") {
    val min:Int by value(60)
    val max:Int by value(6000)
    val timer:Int by value(10)
    val clearTime:String by value("04:00")
    val groupList:List<Long> by value()
}