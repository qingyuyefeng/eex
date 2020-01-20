package com.eex.mvp.mainpage

import android.os.Bundle
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 17:01
 */
class MainContract {
  interface View : BaseView {

    fun getVersionSuccess(mainPage: MainPage)

    fun selectTab(
      idx: Int,
      params: Bundle?
    )
  }

  interface Presenter : BasePresenter<View>
}