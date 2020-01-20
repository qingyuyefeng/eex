package com.eex.mvp.mine.security.phoneoremail

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

/**
 * @Description:绑定手机或者邮箱的api
 * @Author: xq
 * @CreateDate: 2019/12/12 16:29
 */
interface BindsApi {
    /**
     * 获取手机短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/sms/send")
    fun getCode(@Header("authorization") apikey: String,
                @Field("phone") phone: String,
                @Field("sendType") sendType: String,
                @Field("smsType") emailType: String = "2"): Observable<Data<Any>>

    /**
     * 获取邮箱短信验证码接口
     */
    @FormUrlEncoded
    @POST("user/email/send-code")
    fun gainCode(@Header("authorization") apikey: String,
                 @Field("email") email: String,
                 @Field("sendType") sendType: String,
                 @Field("emailType") emailType: String = "2"): Observable<Data<Any>>

    /**
     * 手机认证接口
     */
//    hashMap.put("mobilePhone", txNameNuber.getText().toString().trim(
//    { it <= ' ' }) + " " + ecall)
//    hashMap.put("username", kv.decodeString("username"))
//    hashMap.put("code", passcode + "")
    @FormUrlEncoded
    @POST("user/safety/bind-phone")
    fun bindPhone(@Header("authorization") apikey: String,
                  @Field("username") username: String,
                  @Field("mobilePhone") mobilePhone: String,
                  @Field("code") code: String): Observable<Data<Any>>

    /**
     * 手机解绑接口
     */
//    hashMap.put("mobilePhone", txNameNuber.getText().toString().trim(
//    { it <= ' ' }) + " " + ecall)
//    hashMap.put("username", kv.decodeString("username"))
//    hashMap.put("code", passcode + "")
    @FormUrlEncoded
    @POST("user/safety/cenel-bind-phone")
    fun unbindPhone(@Header("authorization") apikey: String,
                    @Field("username") username: String,
                    @Field("mobilePhone") mobilePhone: String,
                    @Field("code") code: String): Observable<Data<Any>>

    /**
     * 邮箱绑定
     */
//    requestParam.put("email", edtEMail.getText().toString().trim(
//    { it <= ' ' }))
//    requestParam.put("code", edtYZM.getText().toString().trim(
//    { it <= ' ' }))
    @FormUrlEncoded
    @POST("user/safety/bind-email")
    fun bindEmail(@Header("authorization") apikey: String,
                  @Field("email") email: String,
                  @Field("code") code: String): Observable<Data<Any>>

    /**
     * 取消邮箱绑定
     */
    //    requestParam.put("email", edtEMail.getText().toString().trim(
//    { it <= ' ' }))
//    requestParam.put("code", edtYZM.getText().toString().trim(
//    { it <= ' ' }))
    @FormUrlEncoded
    @POST("user/safety/cenel-bind-email")
    fun unbindEmail(@Header("authorization") apikey: String,
                    @Field("email") email: String,
                    @Field("code") code: String): Observable<Data<Any>>
}