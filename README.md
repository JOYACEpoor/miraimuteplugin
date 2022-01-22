# miraimuteplugin
一个简单的qq群复读禁言插件  使用前记得先给你的bot上管理员

# 新增了配置系统！对应了新增的功能
# 注意！！v1.1.3更新后需要删除原来的配置文件重新配置

在.\config\nya.xfy.miraimuteplugin\miraimutepluginconfig.yml中有五个配置项

    min  //是最小禁言时间，按秒算，请不要输入小于1的数值
    max //是最大禁言时间，按秒算，请不要输入大于2592000的数值
    timer //是禁言时间递增所称的倍数，从最小开始乘，但不会超过最大值，超过最大值按最大值算
    clearTime //是刷新禁言递增的时间，默认每天四点刷新，如需此功能请填入0-23的数字，不需要可以填入-1禁用
    groupList //请在这里输入你需要启用此插件的群号，格式如下
    groupList: 
     - 123456
     - 112233
     
有问题issue|pr is welcome
