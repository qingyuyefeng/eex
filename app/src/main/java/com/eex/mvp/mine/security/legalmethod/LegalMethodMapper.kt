package com.eex.mvp.mine.security.legalmethod

import com.eex.common.base.Data
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.domin.entity.legalmethod.MerchdAccount
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 17:59
 */
object LegalMethodMapper:Function<Data<Any>,LegalMethod> {
    override fun apply(t: Data<Any>): LegalMethod {
        return LegalMethod(
                msg = t.msg
        )
    }
}

object LegalMethodMapper1:Function<Data<List<MerchdAccount>>,LegalMethod> {
    override fun apply(t: Data<List<MerchdAccount>>): LegalMethod {
        return LegalMethod(
                listAccount = t.data
        )
    }
}

object LegalMethodMapper2 : Function<Data<Any>, LegalMethod> {
    override fun apply(t: Data<Any>): LegalMethod {
        return LegalMethod(msg = t.msg,
                picName = t.data.toString())
    }
}