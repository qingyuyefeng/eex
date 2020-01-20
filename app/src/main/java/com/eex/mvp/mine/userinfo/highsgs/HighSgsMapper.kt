package com.eex.mvp.mine.userinfo.highsgs

import com.eex.common.base.Data
import com.eex.domin.entity.realname.RealName
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 11:44
 */
object HighSgsMapper : Function<Data<Any>, RealName> {
    override fun apply(t: Data<Any>): RealName {
        return RealName(msg = t.msg,
                picName = t.data.toString())
    }
}