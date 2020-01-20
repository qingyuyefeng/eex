package com.eex.mvp.mine.security.legalmethod

import android.os.Bundle
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 20:02
 */
class LegalMethodPresenter @Inject constructor(
        private val repo: LegalMethodRepoImpl
) : BasePresenterImpl<LegalMethodContract.View, LegalMethod>(), LegalMethodContract.Presenter {

    fun getMethodAccount() {
        mView?.showProgress()
        repo.merchdealaccount()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                listAccount = t.listAccount
                        )
                    }
                })
    }

    override fun handleViewState(vo: LegalMethod) {
        when (vo.level) {
            1 -> mView?.getMethodAccountSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = LegalMethod()
    }
}