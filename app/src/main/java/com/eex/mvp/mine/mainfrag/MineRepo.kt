package com.eex.mvp.mine.mainfrag

import com.eex.domin.entity.mine.Mine
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 9:58
 */
interface MineRepo {
    fun isSeller():Observable<Mine>
}