package com.eex.mvp.transaction.cointradingdetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eex.R
import com.eex.common.base.LazyLoadFragment
import com.eex.common.view.SpinnerPopupWindow
import com.eex.mvp.transaction.cointradingdetail.adapter.DelegationBuyAdapter
import com.eex.mvp.transaction.cointradingdetail.adapter.DelegationSellAdapter
import kotlinx.android.synthetic.main.re_fragment_delegation_order.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/16 19:18
 */
class DelegationOrderFragment : LazyLoadFragment() {

    companion object {
        fun newInstance(dealPair: String): DelegationOrderFragment {
            val frag = DelegationOrderFragment()
            val bundle = Bundle()
            bundle.putString("dealPair", dealPair)
            frag.arguments = bundle
            return frag
        }
    }

    private var buyList: MutableList<BuyOrSell>? = null
    private var buyAdapter: DelegationBuyAdapter? = null
    private var buysortList: MutableList<BuyOrSell> = ArrayList()

    private var sellList: MutableList<BuyOrSell>? = null
    private var sellAdapter: DelegationSellAdapter? = null
    private var sellsortList: MutableList<BuyOrSell> = ArrayList()

    private var dealPair: String = ""
    private var spw: SpinnerPopupWindow<String>? = null
    private var strList: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealPair = arguments?.getString("dealPair", "") ?: ""
        isInit = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.re_fragment_delegation_order, container, false)
    }

    override fun lazyLoad() {
//        Log.e("lazy", "执行了11111")
//        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buyList = ArrayList()
        buyAdapter = DelegationBuyAdapter(activity!!, buyList!!, buysortList)
        rv_buy_in.adapter = buyAdapter
        val manager = LinearLayoutManager(activity)
        manager.isAutoMeasureEnabled = true
        rv_buy_in.layoutManager = manager

        sellList = ArrayList()
        sellAdapter = DelegationSellAdapter(activity!!, sellList!!, sellsortList)
        rv_sell_out.adapter = sellAdapter
        val manager1 = LinearLayoutManager(activity)
        manager1.isAutoMeasureEnabled = true
        rv_sell_out.layoutManager = manager1

        tv_choose_number.setOnClickListener {
            if (spw == null) {
                spw = SpinnerPopupWindow(activity!!, strList!!, tv_choose_number.measuredWidth)
                spw!!.setItemClick(object : SpinnerPopupWindow.ItemClick {
                    override fun itemClick(position: Int) {
                        buyAdapter?.setNumber(position + 3)
                        sellAdapter?.setNumber(position + 3)
                        tv_choose_number.text = strList!![position]
                        spw!!.dismiss()
                    }
                })
            }
            spw?.showAsDropDown(tv_choose_number, 0, -180)
        }

        test()
    }

    fun test() {
//        buyList!!.clear()
//        sellList!!.clear()
//        (0..5).forEach {
//            buyList!!.add(CurrentRecordBean())
//            sellList!!.add(CurrentRecordBean())
//        }
//        buyAdapter?.notifyDataSetChanged()
//        sellAdapter?.notifyDataSetChanged()
        strList = ArrayList()
        (3..6).forEach {
            strList!!.add("$it" + "位小数")
        }
    }

    fun getData(tickBean: TickBean) {
        buyList!!.clear()
        sellList!!.clear()

        buyList!!.addAll(tickBean.buy?:ArrayList())
        sellList!!.addAll(tickBean.sell?:ArrayList())

        if (buyList!!.isNotEmpty()) {
            buysortList.clear()
            buysortList.addAll(buyList!!)
            buysortList.sortWith(Comparator { o1, o2 ->
                if (o2.residueNum >= o1.residueNum) {
                    1
                } else {
                    -1
                }
            })
        }
        if (sellList!!.isNotEmpty()) {
            sellsortList.clear()
            sellsortList.addAll(sellList!!)
            sellsortList.sortWith(Comparator { o1, o2 ->
                if (o2.residueNum >= o1.residueNum) {
                    1
                } else {
                    -1
                }
            })
        }
        if(buyList!!.isEmpty() && sellList!!.isEmpty()){
            ll_rv_top.visibility = View.GONE
        }else{
            ll_rv_top.visibility = View.VISIBLE
        }
        buyAdapter?.notifyDataSetChanged()
        sellAdapter?.notifyDataSetChanged()
    }
}