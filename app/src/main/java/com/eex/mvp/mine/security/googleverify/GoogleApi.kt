package com.eex.mvp.mine.security.googleverify

import com.eex.common.base.Data
import com.eex.mvp.mine.security.googleverify.bindgoogle1.GoogleBean
import com.eex.mvp.mine.security.googleverify.unbindgoogle.UnbindGoogle
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:30
 */
interface GoogleApi {
    /**
     * 生成googleKey
     */
    @POST("user/create-google-key")
    fun createGoogle(@Header("authorization") apikey: String): Observable<Data<GoogleBean>>

    /**
     * 绑定googleKeyind
     */
//    requestParam.put("codes", edtYZM.getText().toString().trim(
//    requestParam.put("savedSecret", edtGoole.getText().toString().trim(
    @FormUrlEncoded
    @POST("user/bind-google-validate")
    fun bindGoogle(@Header("authorization") apikey: String,
                   @Field("codes") codes: String,
                   @Field("savedSecret") savedSecret: String): Observable<Data<UnbindGoogle>>

    /**
     * 取消绑定googleKeyind
     */
//    hashMap.put("codes", edtYZM.getText().toString().trim(
//    { it <= ' ' }))
//    hashMap.put("googleKey", kv.decodeString("googleKey"))
//
//    hashMap.put("password", Utils.md5(edtLoginpwd.getText().toString().trim(
//    { it <= ' ' }) + "hello, moto"))
    @FormUrlEncoded
    @POST("user/relieve-google-validate")
    fun unbindGoogle(@Header("authorization") apikey: String,
                     @Field("codes") codes: String,
                     @Field("googleKey") googleKey: String,
                     @Field("password") password: String): Observable<Data<UnbindGoogle>>
}