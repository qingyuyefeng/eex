package com.eex.mvp.mine.bankcards

import android.os.Bundle
import com.eex.domin.entity.bankcards.BankCards
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 14:46
 */
class BankCardsPresenter @Inject constructor(
        private val repo: BankCardsRepoImpl
) : BasePresenterImpl<BankCardsContract.View, BankCards>(), BankCardsContract.Presenter {

    override fun initPageState(params: Bundle?) {

    }

    fun getData() {
        mView?.showProgress()
        repo.getBankCards()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BankCards>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BankCards) {
                        voObservable.value = t.copy(
                                level = 1
                        )
                    }

                })
    }

    fun deleteItem(cardId: String,position:Int) {
        mView?.showProgress()
        repo.deleteBankcard(cardId)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BankCards>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BankCards) {
                        voObservable.value = voObservable.value?.copy(
                                level = 2,
                                msg = t.msg,
                                deleteId = position
                        )
                    }
                })
    }

    override fun handleViewState(vo: BankCards) {
        when (vo.level) {
            1 -> mView?.getCardsSuccess(vo)
            2 -> mView?.deleteSuccess(vo)
        }

    }
}