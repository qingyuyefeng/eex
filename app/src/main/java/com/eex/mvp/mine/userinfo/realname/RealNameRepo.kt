package com.eex.mvp.mine.userinfo.realname

import com.eex.domin.entity.realname.RealName
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/19 10:15
 */
interface RealNameRepo {
    fun realName(contry: String,
                 cardType: String,
                 cardNo: String,
                 sex: String,
                 surname: String,
                 givename: String): Observable<RealName>
}