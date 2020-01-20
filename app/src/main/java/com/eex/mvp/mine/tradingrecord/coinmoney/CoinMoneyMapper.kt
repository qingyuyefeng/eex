package com.eex.mvp.mine.tradingrecord.coinmoney

import com.eex.common.base.Data
import com.eex.domin.entity.dealrecord.CurrentRecordBean
import com.eex.domin.entity.dealrecord.DealRecord
import com.eex.domin.entity.dealrecord.StopLossBean
import com.eex.home.bean.Page
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 19:33
 */
object CoinMoneyMapper : Function<Data<Page<CurrentRecordBean>>, DealRecord> {
    override fun apply(t: Data<Page<CurrentRecordBean>>): DealRecord {
        return DealRecord(
                currentList = t.data.resultList?: ArrayList()
        )
    }
}

object CoinMoneyMapper1 : Function<Data<Any>, DealRecord> {
    override fun apply(t: Data<Any>): DealRecord {
        return DealRecord(
                msg = t.msg
        )
    }
}

object CoinMoneyMapper2 : Function<Data<Page<StopLossBean>>, DealRecord> {
    override fun apply(t: Data<Page<StopLossBean>>): DealRecord {
        return DealRecord(
                stopLossList = t.data.resultList?: ArrayList()
        )
    }
}