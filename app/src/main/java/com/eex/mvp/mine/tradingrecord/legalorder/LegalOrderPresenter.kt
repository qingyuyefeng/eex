package com.eex.mvp.mine.tradingrecord.legalorder

import android.os.Bundle
import com.eex.domin.entity.dealrecord.LegalOrder
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 11:40
 */
class LegalOrderPresenter @Inject constructor(
        private val repo: LegalOrderRepoImpl
) : BasePresenterImpl<LegalOrderContract.View, LegalOrder>(), LegalOrderContract.Presenter {

    fun getLegalOrder() {
        mView?.showProgress()
        repo.getLegalOrder()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalOrder>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalOrder) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                legalList = t.legalList
                        )
                    }
                })
    }


    override fun initPageState(params: Bundle?) {
        voObservable.value = LegalOrder()
    }

    override fun handleViewState(vo: LegalOrder) {
        when (vo.level) {
            1 -> mView?.getDataSuccess(vo)
        }
    }

}