package com.eex.mvp.mine.moneyaddress.addaddress

import android.os.Bundle
import com.eex.domin.entity.moneyaddress.Address
import com.eex.domin.entity.moneyaddress.CoinBean
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:40
 */
class AddAddrPresenter @Inject constructor(
        private val repo: AddAddrRepoImpl
) : BasePresenterImpl<AddAddrContract.View, Address>(), AddAddrContract.Presenter {

    var coinList: MutableList<CoinBean>? = null

    fun getCoins() {
        mView?.showProgress()
        repo.getCoinType()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Address>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Address) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                coinList = t.coinList
                        )
                    }
                })
    }

    fun addAddr(coinCode: String, walletAddress: String,remark:String) {
        mView?.showProgress()
        repo.addAddr(coinCode, walletAddress,remark)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Address>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Address) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 4,
                                msg = t.msg
                        )
                    }
                })
    }

    override fun handleViewState(vo: Address) {
        when (vo.level) {
            3 -> mView?.getCoinSuccess(vo)
            4 -> mView?.addSuccess(vo)
        }
    }

    override fun initPageState(params:Bundle?) {
        coinList = ArrayList()
        voObservable.value = Address()
    }

}