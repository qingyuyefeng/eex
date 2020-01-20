package com.eex.mvp.mine.security.newtradingpwd

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 14:58
 */
interface NewTradingPwdApi {

    /**
     * 手机获取短信验证码接口
     */
//    requestParam.put("sendType", "sms_take_money")
//    requestParam.put("phone", kv.decodeString("phone"))
//    requestParam.put("userName", kv.decodeString("username"))
    @FormUrlEncoded
    @POST("user/sms/send")
    fun phoneCode(@Header("authorization") apikey: String,
                  @Field("sendType") sendType: String = "sms_take_money",
                  @Field("phone") phone: String,
                  @Field("userName") userName: String,
                  @Field("smsType") smsType: String = "2"): Observable<Data<Any>>


    /**
     * 校验交易密码
     */
//    hashMap.put("newAccountPwd", Utils.md5(newMoneyPassword.getText().toString().trim(
//    { it <= ' ' }) + "hello, moto"))
//    hashMap.put("code", codeNuber.getText().toString().trim(
//    { it <= ' ' }))
    @FormUrlEncoded
    @POST("user/find-account-pwd")
    fun updateAccount(@Header("authorization") apikey: String,
                      @Field("newAccountPwd") newAccountPwd: String,
                      @Field("code") code: String): Observable<Data<Any>>

}