package com.eex.mvp.mine.bankcards.addbankcard

import com.eex.domin.entity.bankcards.BankCards
import android.os.Bundle
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/7 13:22
 */
class AddBankPresenter @Inject constructor(
        private val repo: AddBankRepoImpl
) : BasePresenterImpl<AddBankContract.View, BankCards>(), AddBankContract.Presenter {

    override fun initPageState(params: Bundle?) {
      voObservable.value = BankCards()
    }

    fun addBank(bankName: String,
                bankAddress: String,
                bankChildName: String,
                givename: String,
                surname: String,
                bankCardNo: String) {
        mView?.showProgress()
        repo.addBankCard(bankName, bankAddress, bankChildName, givename, surname, bankCardNo)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BankCards>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BankCards) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                msg = t.msg
                        )
                    }
                })
    }

    override fun handleViewState(vo: BankCards) {
        when (vo.level) {
            3 -> mView?.addBankSuccess(vo)
        }
    }
}