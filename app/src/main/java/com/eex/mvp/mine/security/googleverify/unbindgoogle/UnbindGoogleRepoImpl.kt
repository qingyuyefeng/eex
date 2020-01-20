package com.eex.mvp.mine.security.googleverify.unbindgoogle

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
 * @CreateDate: 2019/12/17 14:55
 */
class UnbindGoogleRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : UnbindGoogleRepo {
    override fun unbind(pwd: String, code: String): Observable<Google> {
        return service.createApi(GoogleApi::class.java)
                .unbindGoogle(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        googleKey = mmkv.decodeString(Constants.GOOGLE_KEY, ""),
                        password = pwd, codes = code)
                .filterResult()
                .map(UnbindGoogleMapper)
    }
}