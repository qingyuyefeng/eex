package com.eex.mvp.mine.tradingrecord.futuresorder

import android.os.Bundle
import com.eex.domin.entity.dealrecord.FuturesOrder
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 15:06
 */
class FuturesOrderPresenter @Inject constructor(
        private val repo: FuturesOrderRepoImpl,
        val mmkv: MMKV
) : BasePresenterImpl<FuturesOrderCintract.View, FuturesOrder>(), FuturesOrderCintract.Presenter {

    /*   init {
           observeSubState({it.level},{
             1->mView?.getDataSuccess(voObservable.value!!)
           })
       }*/
    fun getData(type: Int, delKey: String = "") {
        mView?.showProgress()
        repo.getData(type, delKey)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<FuturesOrder>() {
                    override fun onDone() {
//                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FuturesOrder) {
                        if(t.futuresList.isEmpty()){
                            mView?.dissmissProgress()
                        }
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                futuresList = t.futuresList
                        )
                    }
                })
    }

    fun cancel(id: String, coinCode: String, pricingCoin: String, delWay: String,cancelPso: Int) {
        mView?.showProgress()
        repo.cancelFuture(id, coinCode, pricingCoin, delWay)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<FuturesOrder>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FuturesOrder) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                msg = t.msg,
                                canclePos = cancelPso
                        )
                    }
                })
    }

    fun getNowData(delkeys:String) {
//        mView?.showProgress()
        repo.getNowData(delkeys)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<FuturesOrder>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FuturesOrder) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                nowList = t.nowList
                        )
                    }
                })
    }

    fun closeOut(delAmount: String, indexEntrustId: String,closePos:Int){
        mView?.showProgress()
        repo.closeOut(delAmount, indexEntrustId)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<FuturesOrder>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FuturesOrder) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 4,
                                canclePos = closePos,
                                msg = t.msg
                        )
                    }
                })
    }
    fun overNight(id: String, coinCode: String, pricingCoin: String){
        mView?.showProgress()
        repo.overNight(id, coinCode, pricingCoin)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<FuturesOrder>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FuturesOrder) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 5,
                                msg = t.msg
                        )
                    }
                })
    }
    fun stopFull(id: String, targetProfit: String, targetProfitPrice: String, stopLoss: String, stopLossPrice: String){
        mView?.showProgress()
        repo.stopFull(id, targetProfit, targetProfitPrice, stopLoss, stopLossPrice)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<FuturesOrder>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FuturesOrder) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 6,
                                msg = t.msg
                        )
                    }
                })
    }


    override fun initPageState(params: Bundle?) {
        voObservable.value = FuturesOrder()
    }

    override fun handleViewState(vo: FuturesOrder) {
        when (vo.level) {
            1 -> mView?.getDataSuccess(vo)
            2 -> mView?.cancelCurSuccess(vo)
            3 -> mView?.getNowDataSuccess(vo)
            4 -> mView?.closeOutSuccess(vo)
            5 -> mView?.overNightSuccess(vo)
            6 -> mView?.stopFullSuccess(vo)
        }
    }
}