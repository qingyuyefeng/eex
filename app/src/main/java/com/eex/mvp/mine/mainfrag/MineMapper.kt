package com.eex.mvp.mine.mainfrag

import com.eex.common.base.Data
import com.eex.domin.entity.mine.Mine
import com.eex.home.bean.CtcUserInfo
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 10:40
 */
object MineMapper:Function<Data<CtcUserInfo>, Mine> {
    override fun apply(t: Data<CtcUserInfo>): Mine {
        return Mine(
            merchantStatus = t.data.merchantStatus
        )
    }
}