package com.eex.mvp.mine.userinfo.realname

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 10:51
 */
interface RealNameApi {
    /**
     * 用户二级认证
     */
//    if (type === "" || type === "0")
//    {
//        hashMap.put("contry", "中国")
//        hashMap.put("cardType", "0")
//        hashMap.put("cardNo", edtID.getText().toString().trim({ it <= ' ' }))
//    }
//    else
//    {
//        hashMap.put("cardType", "1")
//        hashMap.put("contry", edtGuojia.getText().toString().trim({ it <= ' ' }))
//        hashMap.put("cardNo", edtHuzhao.getText().toString().trim({ it <= ' ' }))
//    }
//    hashMap.put("sex", "2")
//    hashMap.put("surname", edtName.getText().toString().trim()
//    hashMap.put("givename", edtNameNew.getText().toString().trim()
    @FormUrlEncoded
    @POST("user/two-level-auth")
    fun twolevelauth(@Header("authorization") apikey: String,
                     @Field("contry") contry: String,
                     @Field("cardType") cardType: String,
                     @Field("cardNo") cardNo: String,
                     @Field("sex") sex: String,
                     @Field("surname") surname: String,
                     @Field("givename") givename: String): Observable<Data<Any>>
}