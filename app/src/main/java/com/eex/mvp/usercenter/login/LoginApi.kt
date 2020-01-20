package com.eex.mvp.usercenter.login

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*


/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/25 13:55
 */
interface LoginApi {
    /**
     * 登录1
     */
    @FormUrlEncoded
    @POST("user/login")
    fun loginFirst(@Field("username") username: String,
                   @Field("password") password: String,
                   @Field("loginType") loginType: String = "2"): Observable<Data<LoginBean>>

    /**
     * 登录2(手机登录或邮箱登录)
     */
    @FormUrlEncoded
    @POST("user/login")
    fun loginSecondPE(@Field("checkType") checkType: String,
                    @Field("phoneoremail") phoneoremail: String,
                    @Field("code") code: String,
                    @Field("username") username: String,
                    @Field("password") password: String,
                    @Field("loginType") loginType: String = "2"): Observable<Data<LoginBean>>
    /**
     * 登录2(GOOGLE登录)
     */
    @FormUrlEncoded
    @POST("user/login")
    fun loginSecondGO(@Field("checkType") checkType: String,
                    @Field("googleKey") googleKey: String,
                    @Field("code") code: String,
                    @Field("username") username: String,
                    @Field("password") password: String,
                    @Field("loginType") loginType: String = "2"): Observable<Data<LoginBean>>

    /**
     * 手机获取短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/sms/send")
    fun sendPhoneCode(
            @Field("phone") phone:String,
            @Field("userName") userName:String,
            @Field("sendType") sendType:String = "sms_take_money",
            @Field("smsType") smsType: String = "2"): Observable<Data<Any>>


    /**
     * 邮箱获取短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/email/send-code")
    fun sendEmailCode(@Field("email") email:String,
                      @Field("userName") userName:String,
                      @Field("sendType") sendType:String = "3",
                      @Field("emailType") emailType: String = "2"): Observable<Data<Any>>

}
