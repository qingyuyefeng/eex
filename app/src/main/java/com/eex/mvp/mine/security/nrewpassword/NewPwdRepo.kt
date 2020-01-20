package com.eex.mvp.mine.security.nrewpassword

import com.eex.common.base.Data
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/19 10:37
 */
interface NewPwdRepo {
    fun changePwd( password: String, newPwd: String): Observable<Data<Any>>
}