package com.eex.mvp.mine.tradingrecord.futuresorder

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.dealrecord.FuturesOrderBean
import com.eex.mvp.NoNetBaseActivity
import kotlinx.android.synthetic.main.re_activity_futures_order_detail.*
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/8 10:21
 */
class FuturesOrderDetailActivity : NoNetBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.re_activity_futures_order_detail)

        initView()
    }

    private fun initView() {
        val data = intent.getSerializableExtra("data") as FuturesOrderBean
        data?.run {
            tv_delkey.text = delkey.replace("_", "/")
            tv_pix_code.text = pricingCoin
            val nowPrice = data.nowPrice ?: 0.0
            val lastPrice = data.lastPrice ?: 0.0
            val cny = cny ?: BigDecimal(0)
            tv_now_price.text = NumberFormat.getInstance().format(nowPrice)
            tv_now_price.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (nowPrice >= lastPrice) R.color.color_00a546 else R.color.color_a50000))
            tv_now_rate.text = if (lastPrice != 0.0)
                "${(((nowPrice - lastPrice) / lastPrice * 10000).toInt()) / 100.0}%"
            else "0%"
            tv_now_rate.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (nowPrice >= lastPrice) R.color.color_00a546 else R.color.color_a50000))
            if (index == 2) {
                tv_profit_price.text = (if (profitLoss >= 0) "+" else "") + NumberFormat.getInstance().format(profitLoss)
                tv_profit_price.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (profitLoss >= 0) R.color.color_00a546 else R.color.color_a50000))
                tv_profit_rate.text = (if (profitLoss >= 0) "+" else "") +
                        if (earnestMoney != 0.0)
                            "${((profitLoss / earnestMoney * 10000).toInt()) / 100.0}%"
                        else "0%"
                tv_profit_rate.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (profitLoss >= 0) R.color.color_00a546 else R.color.color_a50000))
            } else {
                val change = if (dealType == 1) (nowPrice - delAmount) * delNum else (delAmount - nowPrice) * delNum
                tv_profit_price.text = (if (change >= 0) "+" else "") + NumberFormat.getInstance().format(change)
                tv_profit_price.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (change >= 0) R.color.color_00a546 else R.color.color_a50000))
                tv_profit_rate.text = (if (change >= 0) "+" else "") +
                        if (earnestMoney != 0.0)
                            "${((change / earnestMoney * 10000).toInt()) / 100.0}%"
                        else "0%"
                tv_profit_rate.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (change >= 0) R.color.color_00a546 else R.color.color_a50000))
            }
            tv_profit_difference.text = "止盈${targetProfit}"
            tv_price_left.text = "价格 $targetProfitPrice"
            tv_loss_difference.text = "止损${stopLoss}"
            tv_price_right.text = "价格 $stopLossPrice"
            tv_buy_pricingCoin.text = "买入市值 $pricingCoin"
            tv_pricingCoin_value.text = "${CommonUtil.cutNumber(delAmount * delNum, priceReservation)}"
            tv_coincode_value.text = "$coinCode ${delNum}个"
            tv_earnings.text = NumberFormat.getInstance().format(earnestMoney)
            tv_level_levels.text = "杠杆≈${lever}X"
            if (dealType == 1) {
                tv_buy_type1.text = getString(R.string.buy_up)
                tv_buy_type1.setBackgroundResource(R.drawable.buy_up_tv_bg)
                tv_buy_type2.text = getString(R.string.buy_up)
                tv_buy_type2.setBackgroundResource(R.drawable.buy_up_tv_bg)

            } else if (dealType == 2) {
                tv_buy_type1.text = getString(R.string.buy_down)
                tv_buy_type1.setBackgroundResource(R.drawable.buy_down_tv_bg)
                tv_buy_type2.text = getString(R.string.buy_down)
                tv_buy_type2.setBackgroundResource(R.drawable.buy_down_tv_bg)
            }
            tv_create_price.text = NumberFormat.getInstance().format(delAmount)
            tv_create_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime)
            when (index) {
                0, 4 -> {
                    ll_rest_quantity.visibility = View.VISIBLE

                    tv_rest_quantity.text = residueNum.toString()
                }
                1 -> {
                    ll_night_charge.visibility = View.VISIBLE
                    ll_deal_type.visibility = View.VISIBLE

                    tv_night_charge.text = nightMoney.toString()
                    tv_deal_type.text = if (delWay == 1) "市价交易" else "委托交易"
                }
                2 -> {
                    ll_night_state.visibility = View.VISIBLE
                    ll_night_charge.visibility = View.VISIBLE
                    ll_close_out_price.visibility = View.VISIBLE
                    ll_close_out_charge.visibility = View.VISIBLE
                    ll_close_amount.visibility = View.VISIBLE
                    ll_close_profit_loss.visibility = View.VISIBLE
                    ll_close_out_time.visibility = View.VISIBLE

                    tv_night_state.text = if (bondsNight == 1) "过夜" else "不过夜"
                    tv_night_charge.text = nightMoney.toString()
                    tv_close_out_price.text = opPrice.toString()
                    tv_close_out_charge.text = serviceCharge.toString()
                    tv_close_amount.text = NumberFormat.getInstance().format(earnestMoney + profitLoss - actualServiceCharge)
                    tv_close_profit_loss.text = profitLoss.toString()
                    tv_close_profit_loss.setTextColor(ContextCompat.getColor(this@FuturesOrderDetailActivity, if (profitLoss >= 0) R.color.color_00a546 else R.color.color_a50000))
                    tv_close_out_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(opTime)
                }
                3 -> {
                    ll_cancel_time.visibility = View.VISIBLE
                }
            }
        }
    }
}