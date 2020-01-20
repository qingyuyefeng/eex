package com.eex.mvp.mine.security.newtradingpwd

import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.constant.Constants
import com.eex.domin.entity.trading.Trading
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 14:23
 */
class NewTradingPwdPresenter @Inject constructor(
        private val repo: NewTradingPwdRepoImpl,
        private val mmkv: MMKV
) : BasePresenterImpl<NewTradingPwdContract.View, Trading>(), NewTradingPwdContract.Presenter {

    val phone: String
        get() = mmkv.decodeString(Constants.PHONE_CERTIFY, "")
    val email: String
        get() = mmkv.decodeString(Constants.EMAIL_CERTIFY, "")
    val google: Int
        get() = mmkv.decodeInt(Constants.GOOGLE_STATE, 0)

    private var pwd = ""

    fun getPhoneCode(phone: String) {
        mView?.showProgress()
        repo.getPhoneCode(phone)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Trading>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Trading) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                msg = t.msg
                        )
                    }

                })
    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    fun undateAccount(newAccountPwd: String, code: String) {
        this.pwd = newAccountPwd
        mView?.showProgress()
        repo.updateAccount(newAccountPwd, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Trading>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Trading) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                msg = t.msg
                        )
                    }

                })
    }

    fun saveTradingPwd() {
        mmkv.encode(Constants.TRADE_PASSWORD, pwd)
    }

    override fun handleViewState(vo: Trading) {
        when (vo.level) {
            1 -> mView?.getphoneCodeSuccess(vo)
            2 -> mView?.setPwdSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Trading()
    }
}