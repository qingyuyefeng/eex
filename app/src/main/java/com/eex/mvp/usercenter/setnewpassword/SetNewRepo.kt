package com.eex.mvp.usercenter.setnewpassword

import com.eex.domin.entity.retrieve.Retrieve
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/3 16:14
 */
interface SetNewRepo {
    fun resetPassword(newPwd: String, checkType: String, phoneoremail: String, code: String): Observable<Retrieve>
}