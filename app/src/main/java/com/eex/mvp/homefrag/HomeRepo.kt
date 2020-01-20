package com.eex.mvp.homefrag

import com.eex.domin.entity.home.Home
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 16:36
 */
interface HomeRepo {
    fun getBanners(useragent:String,websiteType:String):Observable<Home>

    fun getList1():Observable<Home>

    fun getList2(delkeys:String):Observable<Home>

    fun getNotices(websiteType:String):Observable<Home>

    fun openNet():Observable<Home>
}