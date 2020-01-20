package com.eex.mvp.mine.security

import com.eex.domin.entity.security.Security
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class SecurityContract {
  interface View : BaseView {
    fun refreshPage(security: Security)
//    fun showRealNameDialog()
//    fun showBindPhoneDialog()
    fun getTypeSuccess(security: Security)
    fun getNickSuccess(security: Security)
  }

  interface Presenter : BasePresenter<View>
}
