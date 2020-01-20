package com.eex.mvp.mine.financialcenter

import android.os.Bundle
import com.eex.domin.entity.financialcenter.FinancialCenter
import com.eex.domin.entity.financialcenter.MoneyCoin
import com.eex.extensions.asyncAssign
import com.eex.home.weight.Utils
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 14:17
 */
class FinancialCenterPresenter @Inject constructor(
        private val repo: FinancialRepoImpl
) : BasePresenterImpl<FinancialCenterContract.View, FinancialCenter>(), FinancialCenterContract.Presenter {

    var proList: MutableList<MoneyCoin>? = null

    var cycleType: String = ""

    var checkType: Int  //1本息续约 2本金续约 3到期结束
        get() = 1
        set(value) {
            voObservable.value = voObservable.value!!.copy(
                    type = value
            )
        }

    override fun initPageState(params: Bundle?) {
        proList = ArrayList()
        voObservable.value = FinancialCenter()
    }

    fun getMoneyCoins() {
//        mView?.showProgress()
        repo.getMoneyCoins()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<FinancialCenter>() {
                    override fun onDone() {
//                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FinancialCenter) {
                        proList!!.addAll(t.coinList)
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                coinList = t.coinList
                        )
                    }

                })
    }

    fun getCycleTypes(coinCode: String) {
//        mView?.showProgress()
        repo.getCycleTypes(coinCode)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<FinancialCenter>() {
                    override fun onDone() {
//                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FinancialCenter) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                coinCycleType = t.coinCycleType
                        )
                    }
                })
    }

    fun getSettings(tradingPwd:String,coinCode: String, coinMoney: String, financialcycleConfigId: String, lockAssetsConfigId: String, type: String) {
        mView?.showProgress()
        repo.getSettings(Utils.md5(tradingPwd + "hello, moto"),coinCode, coinMoney, financialcycleConfigId, lockAssetsConfigId, type)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<FinancialCenter>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: FinancialCenter) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                msg = t.msg
                        )
                    }

                })
    }

    override fun handleViewState(vo: FinancialCenter) {
        when (vo.level) {
            1 -> {
                mView?.getCoinsSuccess(vo)
            }
            2 -> {
                mView?.getCycleTypesSuccess(vo)
            }
            3 -> mView?.getSettingsSuccess(vo)
        }
    }
}
