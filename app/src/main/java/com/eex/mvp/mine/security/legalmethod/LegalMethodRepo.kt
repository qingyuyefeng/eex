package com.eex.mvp.mine.security.legalmethod

import com.eex.domin.entity.legalmethod.LegalMethod
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 20:25
 */
interface LegalMethodRepo {
    fun merchdealaccount(): Observable<LegalMethod>
}