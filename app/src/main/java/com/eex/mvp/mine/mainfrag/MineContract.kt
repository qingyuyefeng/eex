package com.eex.mvp.mine

import com.eex.domin.entity.mine.Mine
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 9:53
 */
class MineContract {
    interface View:BaseView{
        fun getInfoSuccess(mine: Mine)
    }
    interface Presenter:BasePresenter<View>
}