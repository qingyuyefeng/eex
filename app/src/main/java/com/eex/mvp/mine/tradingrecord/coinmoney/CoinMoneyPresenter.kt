package com.eex.mvp.mine.tradingrecord.coinmoney

import android.os.Bundle
import com.eex.common.base.Data
import com.eex.domin.entity.dealrecord.DealRecord
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 16:38
 */

class CoinMoneyPresenter @Inject constructor(
        private val repo: CoinMoneyRepoImpl
) : BasePresenterImpl<CoinMoneyContract.View, DealRecord>(), CoinMoneyContract.Presenter {
    override fun initPageState(params: Bundle?) {
        voObservable.value = DealRecord()
    }

    fun getCurrent(pageNo: Int, pageSize:Int,delStatus: String) {
        mView?.showProgress()
        repo.getCurrentData(pageNo, pageSize,delStatus)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<DealRecord>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: DealRecord) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                currentList = t.currentList
                        )
                    }
                })
    }

    fun cancelDelegation(order: String, coinCode: String, fixPriceCoinCode: String,cancelPos:Int){
        mView?.showProgress()
        repo.cancelDelegation(order, coinCode, fixPriceCoinCode)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<DealRecord>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: DealRecord) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                msg = t.msg,
                                cancelPos = cancelPos
                        )
                    }
                })
    }

    fun getStopLossData(pageNo: Int, pageSize: Int){
        mView?.showProgress()
        repo.getStopLossData(pageNo, pageSize)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<DealRecord>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: DealRecord) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                stopLossList = t.stopLossList
                        )
                    }
                })
    }

    fun cancelStoploss(orderNo: String,cancelPos:Int){
        mView?.showProgress()
        repo.cancelStoploss(orderNo)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<DealRecord>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: DealRecord) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                msg = t.msg,
                                cancelPos = cancelPos
                        )
                    }
                })
    }

    override fun handleViewState(vo: DealRecord) {
        when (vo.level) {
            1 -> mView?.getDataSuccess(vo)
            2 -> mView?.cancelSuccess(vo)
            3 -> mView?.getLossDataSuccess(vo)
        }
    }

}