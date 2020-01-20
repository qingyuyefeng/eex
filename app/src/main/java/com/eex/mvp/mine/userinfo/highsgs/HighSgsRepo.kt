package com.eex.mvp.mine.userinfo.highsgs

import com.eex.domin.entity.realname.RealName
import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 11:30
 */
interface HighSgsRepo {
    fun uploadPic(file: MultipartBody.Part): Observable<RealName>

    fun highSgs(idCardFrontUrl: String, idCardBackUrl: String, handIdCardUrl: String): Observable<RealName>
}