package com.eex.mvp.mine.security.googleverify.bindgoogle1

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.google.Google
import com.eex.extensions.filterResult
import com.eex.mvp.mine.security.googleverify.GoogleApi
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:36
 */
class GoogleRepoImpl @Inject constructor(
        val service: RetrofitService,
        val mmkv: MMKV
) : GoogleRepo {
    override fun createKey(): Observable<Google> {
        return service.createApi(GoogleApi::class.java)
                .createGoogle(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(GoogleMapper)
    }
}