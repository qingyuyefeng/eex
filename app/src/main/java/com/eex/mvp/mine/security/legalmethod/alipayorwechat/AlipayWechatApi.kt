package com.eex.mvp.mine.security.legalmethod.alipayorwechat

import com.eex.common.base.Data
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/26 11:12
 */
interface AlipayWechatApi {
    /**
     * 手机获取短信验证码接口
     */
//    hashMap.put("sendType", "sms_take_money")
//    hashMap.put("phone", kv.decodeString("phone"))
//    @FormUrlEncoded
//    @POST("user/sms/send")
//    fun send(@Header("authorization") apikey: String, @FieldMap hashMap: HashMap<String, String>): Observable<Data<Any>>
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
//    @FormUrlEncoded
//    @POST("user/email/send-code")
//    fun email(@Header("authorization") apikey: String, @FieldMap hashMap: HashMap<String, String>): Observable<Data<Any>>
    @FormUrlEncoded
    @POST("user/email/send-code")
    fun getEmailCode(@Header("authorization") apikey: String,
                     @Field("sendType") sendType: String = "3",
                     @Field("email") email: String,
                     @Field("userName") userName: String,
                     @Field("emailType") emailType: String = "2"): Observable<Data<Any>>

    /**
     * 上传图片
     */
//       File file = new File(path);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    @Multipart
    @POST("user/fileupload/upload")
    fun upload(@Header("authorization") apikey: String, @Part file: MultipartBody.Part): Observable<Data<Any>>

    /**
     * c2c绑定支付方式
     */
//    hashMap.put("accountType", "2")
//    hashMap.put("imageUrl", imgurl)
//
//    if (id != null)
//    {
//        hashMap.put("id", id)
//    }
//
//    hashMap.put("userName", edtName.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("code", edtCode.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("accountNo", edtZhiFuBao.getText().toString().trim(
//    { it <= ' ' }))

    @FormUrlEncoded
    @POST("ctc/merchdealaccount/save-or-update")
    fun setAlipayOrWechat(@Header("authorization") apikey: String,
                    @Field("accountType") accountType: String = "2", //2,3
                    @Field("userName") userName: String,
                    @Field("id") id: String? = null,
                    @Field("code") code: String,
                    @Field("accountNo") accountNo: String,
                    @Field("imageUrl") imageUrl: String): Observable<Data<Any>>

}