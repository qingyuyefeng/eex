package com.eex.mvp.mine.userinfo.highsgs

import com.eex.domin.entity.realname.RealName
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 18:26
 */
class HighSgsContract {
    interface View : BaseView {
        fun uploadSuccess(realName: RealName)
        fun highSgsSuccess(realName: RealName)
    }

    interface Presenter : BasePresenter<View>
}