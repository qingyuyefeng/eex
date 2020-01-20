package com.eex.mvp.mine.userinfo.realname

import com.eex.domin.entity.realname.RealName
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 16:14
 */
class RealNameContract {
    interface View:BaseView{
        fun realNameSuccess(realName: RealName)
    }
    interface Presenter:BasePresenter<View>
}