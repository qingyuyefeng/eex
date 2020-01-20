package com.eex.mvp.mine.tradingrecord

import android.os.Bundle
import com.eex.R
import com.eex.mvp.NoNetBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_trading_record.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 16:18
 */
class TradingRecordActivity : NoNetBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.re_activity_trading_record)

        ll_coin_money.setOnClickListener {
            //币币订单
            Navigator.toCoinMoneyOrdersActivity(this)
        }
        ll_futures.setOnClickListener {
            //期货订单
            Navigator.toFuturesOrderActivity(this)
        }
        ll_legal.setOnClickListener {
            //法币订单
            Navigator.toLegalOrderActivity(this)
        }
    }
}