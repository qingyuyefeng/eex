package com.eex.repository.mapper

import com.eex.common.base.Data
import com.eex.domin.entity.security.Security
import com.eex.home.bean.SMdata
import com.eex.mvp.mine.security.NickBean
import io.reactivex.functions.Function

object SecurityMapper : Function<Data<SMdata>, Security> {
    override fun apply(data: Data<SMdata>): Security = Security(
            level = data.data.level,
            authStatus = data.data.authStatus
    )
}

object SecurityMapper1 : Function<Data<List<Any>>, Security> {
    override fun apply(data: Data<List<Any>>): Security = Security(
            tradingType = if (data.data == null || data.data.isEmpty()) "" else "123"
    )
}

object SecurityMapper2 : Function<Data<NickBean>, Security> {
    override fun apply(data: Data<NickBean>): Security = Security(
            tradingNick = if (null == data.data) "" else data.data.nickName
    )
}