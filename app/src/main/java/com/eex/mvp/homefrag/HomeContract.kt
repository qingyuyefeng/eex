package com.eex.mvp.homefrag

import com.eex.domin.entity.home.Home
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:17
 */
class HomeContract {
    interface View:BaseView{
        fun getList1Success(home:Home)
        fun getList2Success(home: Home)

        fun getNoticeSuccess(home: Home)
        fun getBannersSuccess(home: Home)

        fun getRefreshSuccess(home: Home)
    }
    interface Presenter:BasePresenter<View>
}