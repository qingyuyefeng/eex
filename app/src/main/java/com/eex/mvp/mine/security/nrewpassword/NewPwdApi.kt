package com.eex.mvp.mine.security.nrewpassword

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/13 11:42
 */
interface NewPwdApi {
    /**
     * 修改密码
     *
     * @param hashMap
     * @return
     */
//    requestParam.put("password", Utils.md5(OriginalPassword.getText().toString().trim it <= ' ' } + "hello, moto"))
//    requestParam.put("newPwd", Utils.md5(newPassword.getText().toString().trim{ it <= ' ' } + "hello, moto"))
    @FormUrlEncoded
    @POST("user/safety/updatepwd")
    fun changePwd(@Header("authorization") apikey: String,
                           @Field("password") password: String,
                           @Field("newPwd") newPwd: String): Observable<Data<Any>>
}