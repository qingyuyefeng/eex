package com.eex

/**
 * 专门负责主接口
 * 测试接口
 */

object WPConfig {
    /**
     *
     */
//
    private var PRODUCE: String? = "http://www.eex.la/"//正式库地址
    private var COINCASHFLOW: String? = "https://mn.hqz.com/v2/"//饼状图
    private val ws: String? = "ws://www.eex.la/" //socket
//    private var TEST: String? = null//定义测试地址
//    private var ws: String? = null//定义测试地址


    //测试库服务器地址
    init {
//
//        TEST = "http://192.168.18.220/"
//        TEST = "http://192.168.18.220/"  //秦磊
//        ws = "ws://192.168.18.220/"

    }

    //apk下载地址
    const val downloadUrl = "http://7e-apps.oss-cn-shanghai.aliyuncs.com/appsoft/7ebit.apk"

    //浏览图片
    const val regist = "http://www.7ebit.info/lore/regist/" //单独数据
    const val PicBaseUrl = "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" //单独数据
    const val Chart = "http://chat-7ebit.s3-website-ap-northeast-1.amazonaws.com/" //单独数据

    /**
     *  isDebug = true的时候日志显示
     *   isDebug = false的时候日志不显示
     */
    const val isDebug = true
    val BaseUrl: String? = PRODUCE //正式
    val CoinCashUrl: String? = COINCASHFLOW //正式
    val WSUrl: String? = ws //socket
//    val BaseUrl: String? = TEST


}