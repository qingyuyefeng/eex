package com.eex.mvp.homefrag

import com.eex.common.base.Data
import com.eex.common.websocket.WebSocketInfo
import com.eex.domin.entity.home.*
import com.eex.home.bean.Page
import com.google.gson.Gson
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 17:36
 */
object HomeMapper1 : Function<Data<List<ListBean>>, Home> {
    override fun apply(t: Data<List<ListBean>>): Home {
        return Home(
                list1 = t.data
        )
    }
}

object HomeMapper2 : Function<Data<List<HomeBean2>>, Home> {
    override fun apply(t: Data<List<HomeBean2>>): Home {
        return Home(
                list2 = t.data
        )
    }
}

object HomeMapper3 : Function<Data<Notice>, Home> {
    override fun apply(t: Data<Notice>): Home {
        return Home(
               noticeData = t.data
        )
    }
}
object HomeMapper4 : Function<Data<Page<Banner>>, Home> {
    override fun apply(t: Data<Page<Banner>>): Home {
        return Home(
                bannerList = t.data.resultList
        )
    }
}

object HomeMapper5 : Function<HomeBean2, Home> {
    override fun apply(t: HomeBean2): Home {
        return Home(
                refreshBean = t
        )
    }
}