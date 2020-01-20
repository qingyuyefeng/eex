package com.eex.mvp.usercenter.retrieve

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 17:36
 */
interface RetrieveApi {
    /**
     * 获取邮箱验证码
     */
    @FormUrlEncoded
    @POST("user/email/send-code")
    fun sendEmailCode(
            @Field("email") email: String,
            @Field("sendType") sendType: String = "1",
            @Field("emailType") emailType: String = "2"): Observable<Data<Any>>

    /**
     * 手机获取短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/sms/send")
    fun sendPhoneCode(@Field("phone") phone: String,
                      @Field("sendType") sendType: String = "sms_find_login_pw",
                      @Field("smsType") smsType: String = "2"): Observable<Data<Any>>

    /**
     * 校验验证码进入下一步
     */
//    if (type == "emal"){
//        hashMap.put("phoneoremail", ecall)
//    }
//    else{
//        hashMap.put("phoneoremail", txNameNuber.getText().toString().trim({ it <= ' ' }) + " " + ecall + "")
//    }
//    hashMap.put("code", verifyCode + "")
//    hashMap.put("checkType", ckeckType + "")
//    hashMap.put("restType", "9")
    @FormUrlEncoded
    @POST("user/sms/check-code")
    fun checkCode(@Field("phoneoremail") phoneoremail: String,
                  @Field("code") code: String,
                  @Field("checkType") checkType: String,
                  @Field("restType") restType: String = "9"): Observable<Data<Any>>
}