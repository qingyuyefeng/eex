package com.eex.mvp.mine.security.legalmethod.alipayorwechat

import com.eex.domin.entity.legalmethod.LegalMethod
import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/26 10:35
 */
interface AlipayOrWechatRepo {
    fun getPhoneCode(): Observable<LegalMethod>
    fun getEmailCode(): Observable<LegalMethod>
    fun uploadPic(file: MultipartBody.Part): Observable<LegalMethod>
    fun setAlipayOrWechat(accountType: String, userName: String, id: String, code: String, accountNo: String, imageUrl: String): Observable<LegalMethod>
}