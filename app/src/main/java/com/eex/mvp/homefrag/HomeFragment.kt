package com.eex.mvp.homefrag

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.webkit.WebView
import com.eex.R
import com.eex.common.util.SwipeUtil
import com.eex.constant.Keys
import com.eex.domin.entity.home.Home
import com.eex.domin.entity.home.HomeBean2
import com.eex.mvp.MVPBaseFragment
import com.eex.mvp.homefrag.adapter.HomeAdapter1
import com.eex.mvp.homefrag.adapter.HomeFootAdapter
import com.eex.mvp.mainpage.MainContract
import kotlinx.android.synthetic.main.re_fragment_home.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:10
 */
class HomeFragment : MVPBaseFragment<Home, HomeContract.View, HomePresenter>(), HomeContract.View {

    private var adapter1: HomeAdapter1? = null
    private var list1: MutableList<HomeBean2>? = null

    private var adapter2: HomeFootAdapter? = null
    private var list2: MutableList<HomeBean2>? = null

    override val layoutId: Int
        get() = R.layout.re_fragment_home

    override fun getBannersSuccess(home: Home) {
        adapter2?.setBanners(home)
    }

    override fun getList1Success(home: Home) {
        var delkeys = ""
        home.list1.forEach {
            delkeys += it.dealPair + ","
        }
        presenter.getList2(delkeys.substring(0, delkeys.length - 1))
    }

    override fun getList2Success(home: Home) {
        list2!!.clear()
        list2!!.addAll(home.list2)
        adapter2?.notifyDataSetChanged()

        list1!!.clear()
        home.list2.forEach {
            when {
                it.delKey == "BTC_USDT" -> list1!!.add(it)
                it.delKey == "ETH_USDT" -> list1!!.add(it)
                it.delKey == "BCHABC_USDT" -> list1!!.add(it)
            }
        }
        adapter1?.notifyDataSetChanged()

        SwipeUtil.loadCompleted(srl_fresh)
//        connect()
        presenter.openConnect()
    }

    override fun getNoticeSuccess(home: Home) {
        adapter2?.setNotices(home)
    }

    override fun getRefreshSuccess(home: Home) {
        Handler().postDelayed({
            val t = home.refreshBean
            for (i in 0 until list2!!.size) {
                if (t.delKey == list2!![i].delKey) {
                    list2!![i] = t
                    adapter2?.notifyItemChanged(i+1)
                    break
                }
            }
            for (j in 0 until list1!!.size) {
                if (t.delKey == list1!![j].delKey) {
                    list1!![j] = t
                    adapter1?.notifyItemChanged(j)
                    break
                }
            }
        },500)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list1 = ArrayList()
        adapter1 = HomeAdapter1(activity!!, list1!!)
        adapter1!!.setItemClick(object : HomeAdapter1.ItemClick {
            override fun click(pos: Int) {
                val bundle = Bundle()
                bundle.putString(Keys.PARAM_PAIR, list1!![pos].delKey)
                bundle.putInt(Keys.TRANS_SELECT, 0)
                (activity as MainContract.View).selectTab(2, bundle)
            }
        })

        list2 = ArrayList()
        adapter2 = HomeFootAdapter(activity!!, adapter1!!, list2!!)
        rv_home2.adapter = adapter2
        rv_home2.layoutManager = LinearLayoutManager(activity!!)
        adapter2!!.setClicks(object : HomeFootAdapter.Clicks {
            override fun tab1Click() {
                //限时秒杀
            }

            override fun tab2Click() {
                //锁仓理财
            }

            override fun tab3Click() {
                //法币交易 跳交易页面的法币
                val bundle = Bundle()
                bundle.putInt(Keys.TRANS_SELECT, 2)
                (activity as MainContract.View).selectTab(2, bundle)
            }

            override fun tab4Click() {
                //合约交易 跳交易页面的期货
                val bundle = Bundle()
                bundle.putInt(Keys.TRANS_SELECT, 1)
                (activity as MainContract.View).selectTab(2, bundle)
            }

            override fun tab5Click() {
                //免费挖矿
            }

            override fun bodyItemClick(delkey:String) {
                val bundle = Bundle()
                bundle.putString(Keys.PARAM_PAIR, delkey)
                bundle.putInt(Keys.TRANS_SELECT, 0)
                (activity as MainContract.View).selectTab(2, bundle)
            }
        })

        initData()

        val useragent = WebView(context).settings.userAgentString
        val websiteType: String = "2"
        presenter.getBanners(useragent, websiteType)

        presenter.getList1()

        presenter.getNotice(websiteType)

        SwipeUtil.init(srl_fresh)
        srl_fresh.setOnRefreshListener {
            presenter.getList1()
            Handler().postDelayed({
                SwipeUtil.loadCompleted(srl_fresh)
            }, 8000)
        }

    }

    private fun initData() {
        list1!!.clear()
        //3个name固定
        list1!!.add(HomeBean2(delKey = "BCHABC_USDT"))
        list1!!.add(HomeBean2(delKey = "ETH_USDT"))
        list1!!.add(HomeBean2(delKey = "BTC_USDT"))

        adapter1?.notifyDataSetChanged()

    }
}