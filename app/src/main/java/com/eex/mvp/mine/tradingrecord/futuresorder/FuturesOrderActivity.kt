package com.eex.mvp.mine.tradingrecord.futuresorder

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.eex.R
import com.eex.common.view.FuturesFilterPoupWindow
import com.eex.domin.entity.dealrecord.FuturesOrder
import com.eex.domin.entity.dealrecord.FuturesOrderBean
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.mine.tradingrecord.coinmoney.CoinChoose
import com.eex.mvp.mine.tradingrecord.futuresorder.adapter.FuturesOrderAdapter
import kotlinx.android.synthetic.main.re_activity_futures_order.*
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 15:41
 */
class FuturesOrderActivity : MVPBaseActivity<FuturesOrder, FuturesOrderCintract.View, FuturesOrderPresenter>(), FuturesOrderCintract.View {

    override val layoutId: Int
        get() = R.layout.re_activity_futures_order

    private var filterPop: FuturesFilterPoupWindow? = null
    private var index = 0

    private var filterList: MutableList<FuturesOrderBean>? = null
    private var adapter: FuturesOrderAdapter? = null

    private var currentList: MutableList<FuturesOrderBean>? = null
    private var positionsList: MutableList<FuturesOrderBean>? = null
    private var sellList: MutableList<FuturesOrderBean>? = null
    private var cancelList: MutableList<FuturesOrderBean>? = null

    override fun getDataSuccess(futures: FuturesOrder) {
        filterList!!.clear()
        var delkeys = ""
        if (futures.futuresList.isNotEmpty()) {
            val list = ArrayList<String>()
            futures.futuresList.forEach { bean ->
                bean.index = index
                if (list.none { it == bean.delkey }) {
                    list.add(bean.delkey)
                }
            }
            list.forEach {
                delkeys += "$it,"
            }
            filterList!!.addAll(futures.futuresList)
//            adapter?.notifyDataSetChanged()

            when (index) {
                0 -> {
                    currentList!!.clear()
                    currentList!!.addAll(futures.futuresList)
                }
                1 -> {
                    positionsList!!.clear()
                    positionsList!!.addAll(futures.futuresList)
                }
                2 -> {
                    sellList!!.clear()
                    sellList!!.addAll(futures.futuresList)
                }
                3 -> {
                    cancelList!!.clear()
                    cancelList!!.addAll(futures.futuresList)
                }
            }
        }
        showEmpty(filterList!!)
        if (delkeys.isNotEmpty() && index != 3) {
            presenter.getNowData(delkeys.substring(0, delkeys.length - 1))
        }
    }

    override fun getNowDataSuccess(futures: FuturesOrder) {
        if (futures.nowList.isNotEmpty()) {
            futures.nowList.forEach {
                adapter?.setNewData(it)
            }
            adapter?.notifyDataSetChanged()
        }
    }


    override fun cancelCurSuccess(futures: FuturesOrder) {
        showToast(futures.msg)
        currentList!!.removeAt(futures.canclePos)
        filterList!!.clear()
        filterList!!.addAll(currentList!!)
        adapter?.notifyItemRemoved(futures.canclePos)

        cancelList!!.clear()

        showEmpty(filterList!!)
    }

    override fun closeOutSuccess(futures: FuturesOrder) {
        positionsList!!.removeAt(futures.canclePos)
        filterList!!.clear()
        filterList!!.addAll(positionsList!!)
        adapter?.notifyItemRemoved(futures.canclePos)
        sellList!!.clear()
    }

    override fun overNightSuccess(futures: FuturesOrder) {
        showToast(futures.msg)
    }

    override fun stopFullSuccess(futures: FuturesOrder) {
        checkDialog?.dismiss()
        showToast(futures.msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title_bar.setRightTvShow()
        title_bar.setRightTvClick(View.OnClickListener {
            //筛选
            val list = ArrayList<CoinChoose>()
            //如果刚筛选过，又回到初始重新筛选
            filterList!!.clear()
            filterList!!.addAll(
                    when (index) {
                        0 -> currentList!!
                        1 -> positionsList!!
                        2 -> sellList!!
                        else -> cancelList!!
                    }
            )
            filterList!!.forEach { record ->
                if (list.none { it.name == record.coinCode }) {
                    list.add(CoinChoose(record.coinCode, false))
                }
            }
            filterPop = FuturesFilterPoupWindow(this, index, list, callbk = { attorny, dealType, ifOver, coinType ->
                if (attorny != 0) {
                    val newList = filterList!!.filter {
                        it.delWay == attorny
                    }
                    filterList!!.clear()
                    filterList!!.addAll(newList)
                }
                if (dealType != 0) {
                    val newList = filterList!!.filter {
                        it.dealType == dealType
                    }
                    filterList!!.clear()
                    filterList!!.addAll(newList)
                }
                if (ifOver != -1) {
                    val newList = filterList!!.filter {
                        it.bondsNight == ifOver
                    }
                    filterList!!.clear()
                    filterList!!.addAll(newList)
                }
                if (coinType != "all") {
                    val newList = filterList!!.filter {
                        it.coinCode == coinType
                    }
                    filterList!!.clear()
                    filterList!!.addAll(newList)
                }
                adapter?.notifyDataSetChanged()
                showEmpty(filterList!!)
            })
            filterPop?.showAsDropDown(title_bar)
        })

        filterList = ArrayList()
        adapter = FuturesOrderAdapter(this, filterList!!)
        adapter!!.setClick(object : FuturesOrderAdapter.TextViewClick {
            override fun cancel(pos: Int) {
                val dto = filterList!![pos]
                presenter.cancel(dto.id, dto.coinCode, dto.pricingCoin, dto.delWay.toString(), pos)
            }

            override fun overNight(pos: Int) {
                val dto = filterList!![pos]
                presenter.overNight(dto.id, dto.coinCode, dto.pricingCoin)
            }

            override fun fullStop(pos: Int) {
                showCheckDialog(filterList!![pos])
            }

            override fun closeOut(pos: Int) {
                val dto = filterList!![pos]
                presenter.closeOut(dto.delAmount.toString(), dto.id, pos)
            }
        })
        rv_futures_record.adapter = adapter
        rv_futures_record.layoutManager = LinearLayoutManager(this)

        currentList = ArrayList()
        positionsList = ArrayList()
        sellList = ArrayList()
        cancelList = ArrayList()

        rg_futures_order.setOnCheckedChangeListener { group, checkedId ->
            index = group.indexOfChild(group.findViewById(checkedId))
            when (index) {
                0 -> { //当前委托
                    if (currentList!!.isEmpty()) {
                        presenter.getData(4)
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(currentList!!)
                        adapter?.notifyDataSetChanged()
                    }
                }
                1 -> { //所有持仓
                    if (positionsList!!.isEmpty()) {
                        presenter.getData(1)
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(positionsList!!)
                        adapter?.notifyDataSetChanged()
                    }
                }
                2 -> { //持仓结算
                    if (sellList!!.isEmpty()) {
                        presenter.getData(2)
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(sellList!!)
                        adapter?.notifyDataSetChanged()
                    }
                }
                3 -> { //持仓撤单
                    if (cancelList!!.isEmpty()) {
                        presenter.getData(3)
                    } else {
                        filterList!!.clear()
                        filterList!!.addAll(cancelList!!)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
            showEmpty(filterList!!)
        }

        presenter.getData(if (index == 0) 4 else index)

        tv_empty.setOnClickListener {
            presenter.getData(if (index == 0) 4 else index)
        }
    }


    private fun showEmpty(list: MutableList<*>) {
        if (list.isEmpty()) {
            rv_futures_record.visibility = View.GONE
            tv_empty.visibility = View.VISIBLE
        } else {
            rv_futures_record.visibility = View.VISIBLE
            tv_empty.visibility = View.GONE
        }
    }

    private var rgIndex = 0

    private var profit: BigDecimal = BigDecimal(0)
    private var profitPrice: BigDecimal = BigDecimal(0)
    private var loss: BigDecimal = BigDecimal(0)
    private var lossPrice: BigDecimal = BigDecimal(0)  //止盈金额，止盈价格，止损金额，止损价格

    private var profit1: Int = 0
    private var profitPrice1: Int = 0
    private var loss1: Int = 0
    private var lossPrice1: Int = 0 //分别带的小数位数
    private var checkDialog: AlertDialog? = null

    private fun showCheckDialog(dto: FuturesOrderBean) {
        val view = LayoutInflater.from(this).inflate(R.layout.check_full_stop_dialog, null)

        val rg_choose = view.findViewById<RadioGroup>(R.id.rg_choose)
        val v_amount = view.findViewById<View>(R.id.v_amount)
        val v_price = view.findViewById<View>(R.id.v_price)
        val tv_profit_price = view.findViewById<TextView>(R.id.tv_profit_price)
        val iv_profit_up = view.findViewById<ImageView>(R.id.iv_profit_up)
        val et_profit = view.findViewById<EditText>(R.id.et_profit)
        val iv_profit_down = view.findViewById<ImageView>(R.id.iv_profit_down)
        val tv_loss_price = view.findViewById<TextView>(R.id.tv_loss_price)
        val iv_loss_up = view.findViewById<ImageView>(R.id.iv_loss_up)
        val et_loss = view.findViewById<EditText>(R.id.et_loss)
        val iv_loss_down = view.findViewById<ImageView>(R.id.iv_loss_down)
        val sure = view.findViewById<Button>(R.id.btn_sure)
        val cancel = view.findViewById<Button>(R.id.btn_cancel)

        try {
            profit1 = (dto.targetProfit.toString() + "").length - (dto.targetProfit.toString() + "").indexOf(".") - 1
            if (profit1 > dto.priceReservation) {
                profit1 = dto.priceReservation
            }
            profitPrice1 = (dto.targetProfitPrice.toString() + "").length - (dto.targetProfitPrice.toString() + "").indexOf(
                    ".") - 1
            if (profitPrice1 > dto.priceReservation) {
                profitPrice1 = dto.priceReservation
            }
            loss1 = (dto.stopLoss.toString() + "").length - (dto.stopLoss.toString() + "").indexOf(".") - 1
            if (loss1 > dto.priceReservation) {
                loss1 = dto.priceReservation
            }
            lossPrice1 = (dto.stopLossPrice.toString() + "").length - (dto.stopLossPrice.toString() + "").indexOf(".") - 1
            if (lossPrice1 > dto.priceReservation) {
                lossPrice1 = dto.priceReservation
            }
        } catch (e: Exception) {

        }

        profit = BigDecimal(dto.targetProfit).setScale(profit1, BigDecimal.ROUND_HALF_UP)
        profitPrice = BigDecimal(dto.targetProfitPrice).setScale(profitPrice1, BigDecimal.ROUND_HALF_UP)
        loss = BigDecimal(dto.stopLoss).setScale(loss1, BigDecimal.ROUND_HALF_UP)
        lossPrice = BigDecimal(dto.stopLossPrice).setScale(lossPrice1, BigDecimal.ROUND_HALF_UP)

        tv_profit_price.text = dto.coinCode + "止盈价格 " + profitPrice //止盈价格
        et_profit.setText(profit.toString())
        tv_loss_price.text = dto.coinCode + "止损价格 " + lossPrice
        et_loss.setText(loss.toString())

        rg_choose.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(group.findViewById(checkedId))
            when (index) {
                0 -> {
                    rgIndex = 0
                    tv_profit_price.text = dto.coinCode + "止盈价格 " + profitPrice
                    et_profit.setText(profit.toString())
                    tv_loss_price.text = dto.coinCode + "止损价格 " + lossPrice
                    et_loss.setText(loss.toString())
                    v_amount.visibility = View.VISIBLE
                    v_price.visibility = View.INVISIBLE
                }
                1 -> {
                    rgIndex = 1
                    tv_profit_price.text = dto.coinCode + "止盈金额 " + profit
                    et_profit.setText(profitPrice.toString())
                    tv_loss_price.text = dto.coinCode + "止损金额 " + loss
                    et_loss.setText(lossPrice.toString())
                    v_amount.visibility = View.INVISIBLE
                    v_price.visibility = View.VISIBLE
                }
            }
        }

        et_profit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                try {
                    var s1 = s.toString()
                    if (s1.startsWith(".")) {
                        s1 = "0$s1"
                    }
                    if (s1.endsWith(".")) {
                        s1 += "0"
                    }
                    if (s1.isEmpty() || s1.endsWith("-")) {
                        s1 = "0"
                    }
                    if (rgIndex == 0) {
                        profit = BigDecimal(s1)
                    } else {
                        profitPrice = BigDecimal(s1)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

        et_loss.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                try {
                    var s1 = s.toString()
                    if (s1.startsWith(".")) {
                        s1 = "0$s1"
                    }
                    if (s1.endsWith(".")) {
                        s1 += "0"
                    }
                    if (s1.isEmpty() || s1.endsWith("-")) {
                        s1 = "0"
                    }
                    if (rgIndex == 0) {
                        loss = BigDecimal(s1)
                    } else {
                        lossPrice = BigDecimal(s1)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

        iv_profit_up.setOnClickListener {
            if (rgIndex == 0) {
                profit = profit.add(BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, profit1.toDouble()))))
                et_profit.setText(profit.toString())
            } else {
                profitPrice = profitPrice.add(
                        BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, profitPrice1.toDouble()))))
                et_profit.setText(profitPrice.toString())
            }
        }

        iv_profit_down.setOnClickListener {
            if (rgIndex == 0) {
                profit = profit.subtract(BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, profit1.toDouble()))))
                et_profit.setText(profit.toString())
            } else {
                profitPrice = profitPrice.subtract(
                        BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, profitPrice1.toDouble()))))
                et_profit.setText(profitPrice.toString())
            }
        }

        iv_loss_up.setOnClickListener {
            if (rgIndex == 0) {
                loss = loss.add(BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, loss1.toDouble()))))
                et_loss.setText(loss.toString())
            } else {
                lossPrice = lossPrice.add(BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, lossPrice1.toDouble()))))
                et_loss.setText(lossPrice.toString())
            }
        }
        iv_loss_down.setOnClickListener {
            if (rgIndex == 0) {
                loss = loss.subtract(BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, loss1.toDouble()))))
                et_loss.setText(loss.toString())
            } else {
                lossPrice = lossPrice.subtract(
                        BigDecimal(1.0).divide(BigDecimal(Math.pow(10.0, lossPrice1.toDouble()))))
                et_loss.setText(lossPrice.toString())
            }
        }

        sure.setOnClickListener(View.OnClickListener {
            if (profit < BigDecimal(0.0)) {
                showToast("止盈金额需大于0")
                return@OnClickListener
            }
            if (dto.dealType == 1) {  //买涨
                if (profitPrice < BigDecimal(dto.aveAmount)) {
                    showToast("买涨的止盈价格需大于买入价")
                    return@OnClickListener
                }
            } else if (dto.dealType == 2) {  //买跌
                if (profitPrice > BigDecimal(dto.aveAmount) || profitPrice < BigDecimal(1.0 / Math.pow(10.0, dto.priceReservation.toDouble()))) {
                    showToast("买跌的止盈价格需大于0并且小于买入价")
                    return@OnClickListener
                }
            }
            if (loss > BigDecimal(0.0) || loss.add(BigDecimal(dto.earnestMoney * 0.8)) < BigDecimal(0.0)) {
                showToast("止损金额需小于0且不能亏损超过保证金的80%")
                return@OnClickListener
            }
            if (lossPrice > BigDecimal(0.0) || lossPrice.add(BigDecimal(dto.earnestMoney * 0.8)) < BigDecimal(0.0)) {
                showToast("止损价格需小于0且不能亏损超过保证金的80%")
                return@OnClickListener
            }

            presenter.stopFull(dto.id, profit.toString(), profitPrice.toString(), loss.toString(), lossPrice.toString())

        })

        cancel.setOnClickListener { checkDialog?.dismiss() }
        checkDialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .show()
    }
}