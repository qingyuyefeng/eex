package com.eex.mvp.mine.security.legalmethod.bankcard

import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.constant.Constants
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 9:45
 */
class BankCardPresenter @Inject constructor(
        private val repo: BankCardRepoImpl,
        private val mmkv: MMKV
) : BasePresenterImpl<BankCardContract.View, LegalMethod>(), BankCardContract.Presenter {

    val phone: String
        get() = mmkv.decodeString(Constants.PHONE_CERTIFY, "")
    val email: String
        get() = mmkv.decodeString(Constants.EMAIL_CERTIFY, "")

    fun getPhoneCode() {
        mView?.showProgress()
        repo.getPhoneCode()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1
                        )
                    }
                })
    }

    fun getEmailCode() {
        mView?.showProgress()
        repo.getEmailCode()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2
                        )
                    }
                })
    }

    fun setBankCard(userName: String, id: String, code: String, accountNo: String, bankName: String, childBankName: String, bankAddress: String) {
        mView?.showProgress()
        repo.setBankCard(userName, id, code, accountNo, bankName, childBankName, bankAddress)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                msg = t.msg
                        )
                    }
                })
    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    override fun handleViewState(vo: LegalMethod) {
        when (vo.level) {
            1 -> mView?.getPhoneCodeSuccess(vo)
            2 -> mView?.getEmailCodeSuccess(vo)
            3 -> mView?.setBankCardSuccess(vo)
        }
    }

    override fun initPageState(bundle: Bundle) {
        voObservable.value = LegalMethod()
    }
}