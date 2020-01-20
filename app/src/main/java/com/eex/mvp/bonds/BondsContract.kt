package com.eex.mvp.bonds

import android.content.Context

import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView
import com.eex.mvp.attorney.AttorneyContract.View
import com.eex.mvp.refresh.RefreshContract

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class BondsContract {
  interface ActivityView : BaseView

  interface ActivityPresenter : BasePresenter<ActivityView>

  interface View<ITEM>: RefreshContract.View<ITEM>

  interface Presenter<ITEM>:RefreshContract.Presenter<com.eex.mvp.attorney.AttorneyContract.View<ITEM>,ITEM>
}
