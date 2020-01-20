package com.eex.mvp.transaction.cointradingdetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eex.R
import com.eex.apis.TransactionApi
import com.eex.common.api.ApiFactory
import com.eex.common.base.Data
import com.eex.common.base.LazyLoadFragment
import com.eex.extensions.asyncAssign
import com.eex.extensions.filterResult
import com.eex.market.bean.PurchaseDatalIst
import com.eex.mvp.transaction.cointradingdetail.adapter.NewestDealAdapter
import com.eex.subcribers.CompletionObserver
import kotlinx.android.synthetic.main.re_only_recycler_view.*
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/16 19:18
 */
class NewestDealsFragment : LazyLoadFragment() {

    companion object {
        fun newInstance(dealPair: String): NewestDealsFragment {
            val frag = NewestDealsFragment()
            val bundle = Bundle()
            bundle.putString("dealPair", dealPair)
            frag.arguments = bundle
            return frag
        }
    }

    private var dealPair: String = ""
    private var newestList: MutableList<PurchaseDatalIst>? = null
    private var newestAdapter: NewestDealAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealPair = arguments?.getString("dealPair", "") ?: ""
        isInit = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.re_only_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newestList = ArrayList()
        newestAdapter = NewestDealAdapter(activity!!, newestList!!)
        rv_new_deal.adapter = newestAdapter
        val manager = LinearLayoutManager(activity)
        manager.isAutoMeasureEnabled = true
        rv_new_deal.layoutManager = manager

//        test()
        getData(dealPair)
    }

    private fun test() {
        newestList!!.clear()
        (0..5).forEach {
            newestList!!.add(PurchaseDatalIst(BigDecimal("123"), BigDecimal("456"), System.currentTimeMillis()))
        }
        newestAdapter?.notifyDataSetChanged()
    }

    override fun lazyLoad() {
//        Log.e("lazy", "执行了22222")
//        getData(dealPair)
    }

    private fun getData(dealPair: String) {
        val deal = if(dealPair.contains("/")){
            dealPair.replace("/","_")
        }else{
            dealPair
        }
        ApiFactory.getInstance(TransactionApi::class.java)
                .recentTransactions(deal)
//                .filterResult()
                .filter {
                    it.isStauts
                }
                .asyncAssign()
                .subscribe(object : CompletionObserver<Data<ArrayList<PurchaseDatalIst>>>() {
                    override fun onDone() {
                    }

                    override fun onNext(t: Data<ArrayList<PurchaseDatalIst>>) {
                        if (t.data != null && t.data.isNotEmpty()) {
                            newestList!!.clear()
                            newestList!!.addAll(t.data)
                            newestAdapter?.notifyDataSetChanged()
                        }
                    }
                })

    }
}