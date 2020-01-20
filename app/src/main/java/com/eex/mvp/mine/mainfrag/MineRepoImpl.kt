package com.eex.mvp.mine.mainfrag

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.mine.Mine
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 9:58
 */
class MineRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
):MineRepo{
    override fun isSeller(): Observable<Mine> {
        return service.createApi(MineApi::class.java)
                .isSeller(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(MineMapper)
    }
}