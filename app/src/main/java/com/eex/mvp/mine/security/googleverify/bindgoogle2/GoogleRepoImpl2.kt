package com.eex.mvp.mine.security.googleverify.bindgoogle2

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.google.Google
import com.eex.extensions.filterResult
import com.eex.mvp.mine.security.googleverify.GoogleApi
import com.eex.mvp.mine.security.googleverify.unbindgoogle.UnbindGoogleMapper
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:36
 */
class GoogleRepoImpl2 @Inject constructor(
        val service: RetrofitService,
        val mmkv: MMKV
) : GoogleRepo2 {
    override fun checkCode(code: String, secret: String): Observable<Google> {
        return service.createApi(GoogleApi::class.java)
                .bindGoogle(mmkv.decodeString(Constants.TOKEN_ID, ""), code, secret)
                .filterResult()
                .map(UnbindGoogleMapper)
    }
}