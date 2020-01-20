package com.eex.mvp.usercenter.retrieve

import com.eex.common.base.Data
import com.eex.domin.entity.retrieve.Retrieve
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 18:19
 */
object RetrieveMapper:Function<Data<Any>,Retrieve> {
    override fun apply(t: Data<Any>): Retrieve {
        return Retrieve(msg = t.msg)
    }
}