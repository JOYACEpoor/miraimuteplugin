package nya.xfy

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object MiraiMutePluginConfig : AutoSavePluginConfig("MiraiMutePluginConfig") {
    @ValueDescription("最小禁言时间，按秒算，请不要输入小于1的数值")
    val min: Int by value(60)

    @ValueDescription("最大禁言时间，按秒算，请不要输入大于2592000的数值")
    val max: Int by value(7200)

    @ValueDescription("禁言时间递增所称的倍数，从最小开始乘，但不会超过最大值，超过最大值按最大值算")
    val timer: Int by value(2)

    @ValueDescription("刷新禁言递增的时间，默认每天四点刷新，如需此功能请填入0-23的数字，不需要可以填入-1禁用")
    val clearTime: Int by value(4)

    @ValueDescription(
        """
        请在这里输入你需要启用此插件的群号，格式如下
        groupList: 
         - 123456
         - 112233
    """
    )
    val groupList: List<Long> by value()

    @ValueDescription("复读禁言次数，默认为3")
    val frequency: Int by value(3)
}