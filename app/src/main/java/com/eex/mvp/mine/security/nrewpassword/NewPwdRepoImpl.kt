package com.eex.mvp.mine.security.nrewpassword

import com.eex.apis.RetrofitService
import com.eex.common.base.Data
import com.eex.constant.Constants
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/19 10:37
 */
class NewPwdRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : NewPwdRepo {
    override fun changePwd(password: String, newPwd: String): Observable<Data<Any>> {
        return  service.createApi(NewPwdApi::class.java)
                .changePwd(mmkv.decodeString(Constants.TOKEN_ID,""),password,newPwd)
                .filterResult()
    }
}