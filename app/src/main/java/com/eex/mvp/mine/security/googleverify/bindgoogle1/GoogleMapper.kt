package com.eex.mvp.mine.security.googleverify.bindgoogle1

import com.eex.common.base.Data
import com.eex.domin.entity.google.Google
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:40
 */
object GoogleMapper:Function<Data<GoogleBean>,Google> {
    override fun apply(t: Data<GoogleBean>): Google {
        return Google(
                googleKey = t.data.googleKey,
                company = t.data.company,
                userName = t.data.userName
        )
    }
}