package com.eex.mvp.usercenter.setnewpassword

import com.eex.apis.RetrofitService
import com.eex.domin.entity.retrieve.Retrieve
import com.eex.extensions.filterResult
import com.eex.mvp.usercenter.retrieve.RetrieveMapper
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/3 16:15
 */
class SetNewRepoImpl @Inject constructor(
        private val service: RetrofitService
) :SetNewRepo{
    override fun resetPassword(newPwd: String, checkType: String, phoneoremail: String, code: String): Observable<Retrieve> {
        return service.createApi(SetNewApi::class.java)
                .resetPassword(newPwd, checkType, phoneoremail, code)
                .filterResult()
                .map(RetrieveMapper)
    }

}