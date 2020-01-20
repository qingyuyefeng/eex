package com.eex

import android.os.Environment


object Config {
  val HOST_URL = "http://www.eex.la/"
  var MOCK = false
  val DOWN_LOAD_URL = "http://7e-apps.oss-cn-shanghai.aliyuncs.com/appsoft/7ebit.apk"
  val INVITE_URL = "http://www.7ebit.info/lore/regist/"
  val PIC_URL = "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/"
  val UP_LOAD_PIC_URL = "http://chat-7ebit.s3-website-ap-northeast-1.amazonaws.com/"
  const val PIE_CHART_URL = "https://mn.hqz.com/v2/"
  val KEEP_ALIVE_URL = "ws://www.eex.la/"

  val PAGE_SIZE = 10

  val BASE_DIR_SD = (Environment.getExternalStorageDirectory().path
          + "/Android/data/" + BuildConfig.APPLICATION_ID)
  val APK_DIR_SD = "$BASE_DIR_SD/cache/apk"
}
