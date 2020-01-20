package com.eex.mvp.homefrag

import com.eex.WPConfig
import com.eex.apis.RetrofitService
import com.eex.common.websocket.RxWebSocket
import com.eex.domin.entity.home.Home
import com.eex.domin.entity.home.HomeBean2
import com.eex.extensions.filterResult
import com.google.gson.Gson
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 16:37
 */
class HomeRepoImpl @Inject constructor(
        private val service: RetrofitService
) : HomeRepo {

    override fun getBanners(useragent: String, websiteType: String): Observable<Home> {
        return service.createApi(HomeApi::class.java)
                .getBanner(useragent, websiteType)
                .filterResult()
                .map(HomeMapper4)
    }

    override fun getList1(): Observable<Home> {
        return service.createApi(HomeApi::class.java)
                .getDealPairList()
                .filterResult()
                .map(HomeMapper1)
    }

    override fun getList2(delkeys: String): Observable<Home> {
        return service.createApi(HomeApi::class.java)
                .getIndexMaketList(delkeys)
                .filterResult()
                .map(HomeMapper2)
    }

    override fun getNotices(websiteType: String): Observable<Home> {
        return service.createApi(HomeApi::class.java)
                .getNotices(websiteType = websiteType)
                .filterResult()
                .map(HomeMapper3)
    }

    override fun openNet(): Observable<Home> {
        return RxWebSocket.get(WPConfig.WSUrl!! + "websocket/ALL_ALL")
                .filter {
                    it.string != null
                }
                .map {
                    Gson().fromJson(it.string, HomeBean2::class.java)
                }
                .map(HomeMapper5)
    }

}