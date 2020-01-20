package com.eex.mvp.mine.security.legalmethod.bankcard

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 17:28
 */
interface BankCardApi {
    /**
     * 手机获取短信验证码接口
     */
//    hashMap.put("sendType", "sms_take_money")
//    hashMap.put("phone", kv.decodeString("phone"))
    @FormUrlEncoded
    @POST("user/sms/send")
    fun getPhoneCode(@Header("authorization") apikey: String,
                     @Field("sendType") sendType: String = "sms_take_money",
                     @Field("phone") phone: String,
                     @Field("userName") userName: String,
                     @Field("smsType") smsType: String = "2"): Observable<Data<Any>>

    /**
     * 获取邮箱验证码
     */
//    hashMap.put("sendType", "3")
//    hashMap.put("email", kv.decodeString("email"))
//    hashMap.put("userName", kv.decodeString("username"))
    @FormUrlEncoded
    @POST("user/email/send-code")
    fun getEmailCode(@Header("authorization") apikey: String,
                     @Field("sendType") sendType: String = "3",
                     @Field("email") email: String,
                     @Field("userName") userName: String,
                     @Field("emailType") emailType: String = "2"): Observable<Data<Any>>

    /**
     * c2c绑定支付方式
     */
//    if (id != "")
//    {
//        hashMap.put("id", id)
//    }
//    hashMap.put("accountType", "1")
//    hashMap.put("userName", edtName.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("code", edtCode.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("accountNo", edtBnakNuber.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("bankName", edtBank.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("childBankName", edtBankBranch.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("bankAddress", edtBankAddress.getText().toString().trim(
//    { it <= ' ' }))
    @FormUrlEncoded
    @POST("ctc/merchdealaccount/save-or-update")
    fun setBankCard(@Header("authorization") apikey: String,
                    @Field("accountType") accountType: String = "1",
                    @Field("userName") userName: String,
                    @Field("id") id: String? = null,
                    @Field("code") code: String,
                    @Field("accountNo") accountNo: String,
                    @Field("bankName") bankName: String,
                    @Field("childBankName") childBankName: String,
                    @Field("bankAddress") bankAddress: String): Observable<Data<Any>>
}