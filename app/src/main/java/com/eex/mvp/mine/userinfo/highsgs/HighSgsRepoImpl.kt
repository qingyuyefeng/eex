package com.eex.mvp.mine.userinfo.highsgs

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.realname.RealName
import com.eex.extensions.filterResult
import com.eex.mvp.mine.userinfo.realname.RealNameMapper
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 11:32
 */
class HighSgsRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : HighSgsRepo {
    override fun uploadPic(file: MultipartBody.Part): Observable<RealName> {
        return service.createApi(HighSgsApi::class.java)
                .upload(mmkv.decodeString(Constants.TOKEN_ID, ""), file)
                .filterResult()
                .map(HighSgsMapper)
    }

    override fun highSgs(idCardFrontUrl: String, idCardBackUrl: String, handIdCardUrl: String): Observable<RealName> {
        return service.createApi(HighSgsApi::class.java)
                .levelauth(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        Constants.PICTUREURL + idCardFrontUrl,
                        Constants.PICTUREURL + idCardBackUrl,
                        Constants.PICTUREURL + handIdCardUrl)
                .filterResult()
                .map(RealNameMapper)
    }
}