package com.eex.mvp.usercenter.register

import com.eex.common.base.Data
import com.eex.home.bean.Graphics
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 14:30
 */
interface RegisterApi {

    /**
     * 图形验证码
     */
//    @FormUrlEncoded
    @POST("user/captcha")
    fun getImageCode(): Observable<Data<Graphics>>

    /**
     * 邮箱获取短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/email/send-code")
    fun sendEmailCode(@Field("email") email: String,
                      @Field("sendType") sendType: String = "4",
                      @Field("emailType") emailType: String = "2"): Observable<Data<Any>>

    /**
     * 手机获取短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/sms/send")
    fun sendPhoneCode(
            @Field("phone") phone: String,
            @Field("verCode") verCode: String,
            @Field("verKey") verKey: String,
            @Field("sendType") sendType: String = "sms_regist",
            @Field("smsType") smsType: String = "2"): Observable<Data<Any>>

    /**
     * 注册(包括手机和邮箱)
     */
    @FormUrlEncoded
    @POST("user/regist")
    fun register(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("code") code: String,
            @Field("invateCode") invateCode: String,
            @Field("regist") regist: String = "2",
            @Field("websiteType") websiteType: String = "2"): Observable<Data<Any>>
}