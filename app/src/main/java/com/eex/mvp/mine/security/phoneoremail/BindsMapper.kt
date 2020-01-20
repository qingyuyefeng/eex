package com.eex.mvp.mine.security.phoneoremail

import com.eex.common.base.Data
import com.eex.domin.entity.bindphoneoremail.BindPE
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 17:04
 */
object BindsMapper:Function<Data<Any>,BindPE> {
    override fun apply(t: Data<Any>): BindPE {
        return BindPE(
                level = 1
        )
    }
}