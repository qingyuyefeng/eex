package com.eex.mvp.mine.security.googleverify.unbindgoogle

import com.eex.domin.entity.google.Google
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 14:54
 */
interface UnbindGoogleRepo {
    fun unbind(pwd:String, code:String):Observable<Google>
}