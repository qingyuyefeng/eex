package com.eex.mvp.mine.userinfo.realname

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.realname.RealName
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 17:13
 */
class RealNameRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :RealNameRepo{
    override fun realName(contry: String, cardType: String, cardNo: String, sex: String, surname: String, givename: String): Observable<RealName> {
        return service.createApi(RealNameApi::class.java)
                .twolevelauth(mmkv.decodeString(Constants.TOKEN_ID,""),
                        contry, cardType, cardNo, sex, surname, givename)
                .filterResult()
                .map(RealNameMapper)
    }
}