package com.eex.mvp.mine.security.legalmethod.alipayorwechat

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.extensions.filterResult
import com.eex.mvp.mine.security.legalmethod.LegalMethodMapper
import com.eex.mvp.mine.security.legalmethod.LegalMethodMapper2
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/26 11:05
 */
class AlipayOrWechatRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :AlipayOrWechatRepo{
    override fun getPhoneCode(): Observable<LegalMethod> {
        return service.createApi(AlipayWechatApi::class.java)
                .getPhoneCode(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                phone = mmkv.decodeString(Constants.PHONE_CERTIFY,""),
                        userName = mmkv.decodeString(Constants.USERNAME,""))
                .filterResult()
                .map(LegalMethodMapper)
    }

    override fun getEmailCode(): Observable<LegalMethod> {
        return service.createApi(AlipayWechatApi::class.java)
                .getEmailCode(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                        email = mmkv.decodeString(Constants.EMAIL_CERTIFY,""),
                        userName = mmkv.decodeString(Constants.USERNAME,""))
                .filterResult()
                .map(LegalMethodMapper)
    }

    override fun uploadPic(file: MultipartBody.Part): Observable<LegalMethod> {
        return service.createApi(AlipayWechatApi::class.java)
                .upload(mmkv.decodeString(Constants.TOKEN_ID, ""), file)
                .filterResult()
                .map(LegalMethodMapper2)
    }

    override fun setAlipayOrWechat(accountType: String, userName: String, id: String, code: String, accountNo: String, imageUrl: String): Observable<LegalMethod> {
        return service.createApi(AlipayWechatApi::class.java)
                .setAlipayOrWechat(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                        accountType = accountType,
                        userName = userName,
                        id = if(id.isEmpty()) null else id,
                        code = code,
                        accountNo = accountNo,
                        imageUrl = imageUrl)
                .filterResult()
                .map(LegalMethodMapper)
    }

}