package com.eex.mvp.usercenter.setnewpassword

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/3 15:23
 */
interface SetNewApi {
    /**
     * 找回密码第二步接口
     */
//    hashMap.put("newPwd", Utils.md5(password + "hello, moto"))
//    val status = tel.contains("@")
//    if (status)
//    {
//        hashMap.put("checkType", "3")
//        hashMap.put("phoneoremail", tel)
//    }
//    else
//    {
//        hashMap.put("checkType", "1")
//        hashMap.put("phoneoremail", "+86 $tel")
//    }
//    hashMap.put("code", code)
    @FormUrlEncoded
    @POST("user/reset-pwd")
    fun resetPassword(@Field("newPwd") newPwd: String,
                      @Field("checkType") checkType: String,
                      @Field("phoneoremail") phoneoremail: String,
                      @Field("code") code: String): Observable<Data<Any>>
}