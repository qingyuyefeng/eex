package com.eex.mvp.attorney

import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView
import com.eex.mvp.refresh.RefreshContract

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class AttorneyContract {
  interface ActivityView : BaseView

  interface ActivityPresenter : BasePresenter<ActivityView>

  interface View<ITEM>:RefreshContract.View<ITEM>
  interface Presenter<ITEM>:RefreshContract.Presenter<View<ITEM>,ITEM>

}
