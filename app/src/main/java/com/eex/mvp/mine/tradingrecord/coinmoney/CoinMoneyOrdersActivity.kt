package com.eex.mvp.mine.tradingrecord.coinmoney

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RadioButton
import com.eex.R
import com.eex.common.view.FilterPoupWindow
import com.eex.domin.entity.dealrecord.CurrentRecordBean
import com.eex.domin.entity.dealrecord.DealRecord
import com.eex.domin.entity.dealrecord.StopLossBean
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.mine.tradingrecord.coinmoney.adapter.CurrentLegeAdapter
import com.eex.mvp.mine.tradingrecord.coinmoney.adapter.StopLossAdapter
import kotlinx.android.synthetic.main.re_activity_coinmoney_order.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 16:34
 */
class CoinMoneyOrdersActivity : MVPBaseActivity<DealRecord, CoinMoneyContract.View, CoinMoneyPresenter>(), CoinMoneyContract.View {

    private var filterList: MutableList<CurrentRecordBean>? = null
    private var deleAdapter: CurrentLegeAdapter? = null

    private var currentList: MutableList<CurrentRecordBean>? = null
    private var historyList: MutableList<CurrentRecordBean>? = null
    private var recordList: MutableList<CurrentRecordBean>? = null

    private var stopLossList: MutableList<StopLossBean>? = null
    private var stopLossAdapter: StopLossAdapter? = null

    private var pageNo = 1
    private var pageSize = 100

    private var index = 0
    private var cancelType = 1

    private var filterPop: FilterPoupWindow? = null

    override val layoutId: Int
        get() = R.layout.re_activity_coinmoney_order

    override fun getDataSuccess(deal: DealRecord) {
        filterList!!.clear()
        if (deal.currentList.isNotEmpty()) {
            deal.currentList.forEach {
                it.index = index
            }
            filterList!!.addAll(deal.currentList)
            deleAdapter?.notifyDataSetChanged()
            when (index) {
                0 -> {
                    currentList!!.clear()
                    currentList!!.addAll(deal.currentList)
                }
                1 -> {
                    historyList!!.clear()
                    historyList!!.addAll(deal.currentList)
                }
                2 -> {
                    recordList!!.clear()
                    recordList!!.addAll(deal.currentList)
                }
            }
        }
        showEmpty(filterList!!)
    }

    override fun cancelSuccess(deal: DealRecord) {
        showToast(deal.msg)
        when(cancelType){
            1->{
                when (currentList!![deal.cancelPos].delStatus) {
                    1 -> currentList!![deal.cancelPos].delStatus = 5
                    2 -> currentList!![deal.cancelPos].delStatus = 4
                }
                filterList!!.clear()
                filterList!!.addAll(currentList!!)
                deleAdapter?.setEnable(currentList!![deal.cancelPos])
                deleAdapter?.notifyItemChanged(deal.cancelPos)
            }
            2->{
                stopLossList!![deal.cancelPos].triggerState = 3
                stopLossAdapter?.notifyItemChanged(deal.cancelPos)
            }
        }

    }

    override fun getLossDataSuccess(deal: DealRecord) {
        if (deal.stopLossList.isNotEmpty()) {
            stopLossList!!.clear()
            stopLossList!!.addAll(deal.stopLossList)
            stopLossAdapter?.notifyDataSetChanged()
        }
        showEmpty(stopLossList!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {

        index = intent.getIntExtra("index", 0)

        title_bar.setRightTvShow()
        title_bar.setRightTvClick(View.OnClickListener {
            //筛选
            val list = ArrayList<CoinChoose>()
            when (index) {
                0 -> {
                    currentList!!.forEach { record ->
                        if (list.none { it.name == record.coinCode }) {
                            list.add(CoinChoose(record.coinCode, false))
                        }
                    }
                }
                1 -> {
                    historyList!!.forEach { record ->
                        if (list.none { it.name == record.coinCode }) {
                            list.add(CoinChoose(record.coinCode, false))
                        }
                    }
                }
                2 -> {
                    recordList!!.forEach { record ->
                        if (list.none { it.name == record.coinCode }) {
                            list.add(CoinChoose(record.coinCode, false))
                        }
                    }
                }
            }
            filterPop = FilterPoupWindow(this, index, list) { attorny, saleType, dealStatus, coinType ->
                if (attorny == 2) { //选中止盈止损
                    rv_coin_money.adapter = stopLossAdapter
                    presenter.getStopLossData(pageNo, pageSize)
                    return@FilterPoupWindow
                }
                when (index) {
                    0 -> {
                        filterList!!.clear()
                        filterList!!.addAll(currentList!!)
                        if (dealStatus != 0) {
                            val newList1 = filterList!!.filter {
                                it.delStatus == dealStatus
                            }
                            filterList!!.clear()
                            filterList!!.addAll(newList1)
                        }
                        if (coinType != "all") {
                            val newList2 = filterList!!.filter {
                                it.coinCode == coinType
                            }
                            filterList!!.clear()
                            filterList!!.addAll(newList2)
                        }
                    }
                    1 -> {
                        filterList!!.clear()
                        filterList!!.addAll(historyList!!)
                        if (saleType != 0) {
                            val newList1 = filterList!!.filter {
                                it.dealType == saleType
                            }
                            filterList!!.clear()
                            filterList!!.addAll(newList1)
                        }
                    }
                    2 -> {
                        filterList!!.clear()
                        filterList!!.addAll(recordList!!)
                        if (saleType != 0) {
                            val newList1 = filterList!!.filter {
                                it.dealType == saleType
                            }
                            filterList!!.clear()
                            filterList!!.addAll(newList1)
                        }
                        if (coinType != "all") {
                            val newList2 = filterList!!.filter {
                                it.coinCode == coinType
                            }
                            filterList!!.clear()
                            filterList!!.addAll(newList2)
                        }
                    }
                }
                deleAdapter?.notifyDataSetChanged()
                showEmpty(filterList!!)
            }
            filterPop?.showAsDropDown(title_bar)
        })
        (rg_coin_money.getChildAt(index) as RadioButton).isChecked = true
        rg_coin_money.setOnCheckedChangeListener { group, checkedId ->
            index = group.indexOfChild(group.findViewById(checkedId))
            rv_coin_money.adapter = deleAdapter
            when (index) {
                0 -> { //当前委托
                    if (currentList!!.isEmpty()) {
                        presenter.getCurrent(pageNo, pageSize, "1,2")
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(currentList!!)
                        showEmpty(filterList!!)
                    }
                }
                1 -> { //历史委托
                    if (historyList!!.isEmpty()) {
                        presenter.getCurrent(pageNo, pageSize, "3,4,5")
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(historyList!!)
                        showEmpty(filterList!!)
                    }
                }
                2 -> { //成交记录
                    if (recordList!!.isEmpty()) {
                        presenter.getCurrent(pageNo, pageSize, "2,3,4")
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(recordList!!)
                        showEmpty(filterList!!)
                    }
                }
            }
        }
        filterList = ArrayList()

        deleAdapter = CurrentLegeAdapter(this, filterList!!)
        deleAdapter!!.setRevocation(object : CurrentLegeAdapter.Revocation {
            override fun revocate(bean: CurrentRecordBean, pos: Int) {
                cancelType = 1
                presenter.cancelDelegation(bean.orderNo, bean.coinCode, bean.fixPriceCoinCode, pos)
            }
        })
        rv_coin_money.adapter = deleAdapter
        rv_coin_money.layoutManager = LinearLayoutManager(this)

        historyList = ArrayList()
        recordList = ArrayList()
        currentList = ArrayList()


        showEmpty(filterList!!)

        when (index) {
            0 -> presenter.getCurrent(pageNo, pageSize, "1,2")
            1 -> presenter.getCurrent(pageNo, pageSize, "3,4,5")
            2 -> presenter.getCurrent(pageNo, pageSize, "2,3,4")
        }
        tv_empty.setOnClickListener {
            when (index) {
                0 -> presenter.getCurrent(pageNo, pageSize, "1,2")
                1 -> presenter.getCurrent(pageNo, pageSize, "3,4,5")
                2 -> presenter.getCurrent(pageNo, pageSize, "2,3,4")
            }
        }

        //止盈止损
        stopLossList = ArrayList()
        stopLossAdapter = StopLossAdapter(this, stopLossList!!, index)
        stopLossAdapter!!.setRevocation(object : StopLossAdapter.Revocation {
            override fun revocate(bean: StopLossBean, pos: Int) {
                cancelType = 2
                presenter.cancelStoploss(bean.orderNo, pos)
            }
        })
    }

    private fun showEmpty(list: MutableList<*>) {
        if (list.isEmpty()) {
            rv_coin_money.visibility = View.GONE
            tv_empty.visibility = View.VISIBLE
        } else {
            rv_coin_money.visibility = View.VISIBLE
            tv_empty.visibility = View.GONE
        }
    }
}
