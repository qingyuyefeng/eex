package com.eex.mvp.mine.security.googleverify.bindgoogle2

import com.eex.domin.entity.google.Google
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:35
 */
interface GoogleRepo2 {
    fun checkCode(code:String,secret: String):Observable<Google>
}