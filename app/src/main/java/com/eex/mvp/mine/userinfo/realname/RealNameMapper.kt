package com.eex.mvp.mine.userinfo.realname

import com.eex.common.base.Data
import com.eex.domin.entity.realname.RealName
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 18:11
 */
object RealNameMapper:Function<Data<Any>,RealName> {
    override fun apply(t: Data<Any>): RealName {
        return RealName(msg = t.msg)
    }
}