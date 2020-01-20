package com.eex.mvp.mine.security.googleverify.unbindgoogle

import com.eex.common.base.Data
import com.eex.domin.entity.google.Google
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 15:12
 */
object UnbindGoogleMapper:Function<Data<UnbindGoogle>,Google> {
    override fun apply(t: Data<UnbindGoogle>): Google {
        return Google(
                msg = t.msg
        )
    }
}