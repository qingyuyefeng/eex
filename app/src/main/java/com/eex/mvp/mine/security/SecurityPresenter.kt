package com.eex.mvp.mine.security

import android.os.Bundle
import android.text.TextUtils
import com.eex.constant.Constants
import com.eex.domin.entity.security.Security
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.repository.impl.SecurityRepoImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class SecurityPresenter @Inject constructor(
        private val repo: SecurityRepoImpl,
        val mmkv: MMKV
) : BasePresenterImpl<SecurityContract.View, Security>(), SecurityContract.Presenter {

    val isCertification: Boolean
        get() = mmkv.decodeString(Constants.TOKEN_ID).orEmpty().isNotEmpty()

    val hasSetTradePassword: Boolean
        get() = mmkv.decodeString(Constants.TRADE_PASSWORD).orEmpty().isNotEmpty()

    val hasGoogleCertify: Boolean
        get() = !TextUtils.isEmpty(mmkv.decodeString(Constants.GOOGLE_KEY))

    val email: String
        get() = mmkv.decodeString(Constants.EMAIL_CERTIFY, "")

    val phone: String
        get() = mmkv.decodeString(Constants.PHONE_CERTIFY, "")

//    init {
//        observeSubState({
//            it.level
//        }, {
//            mView?.refreshPage(it)
//        })
//        observeSubState({
//            it
//        }, {
//            if (it.isDialogShown) {
//                if (it.level == 1) {
//                    mView?.showRealNameDialog()
//                } else {
//                    mView?.showBindPhoneDialog()
//                }
//            }
//        })
//    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Security()
    }

    fun checkAuthStatus(showDialog: Boolean = false) {
        mView?.showProgress()
        repo.checkAuthStatus()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Security>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(security: Security) {
                        voObservable.value = voObservable.value?.copy(
                                number = 1,
                                level = security.level,
                                authStatus = security.authStatus,
                                isDialogShown = showDialog
                        )
                        mmkv.encode(Constants.REAL_NAME_CERTIFY, security.authStatus)
                    }
                })
    }

    fun getType() {
        repo.getTradingType()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Security>() {
                    override fun onDone() {

                    }

                    override fun onNext(t: Security) {
                        voObservable.value = voObservable.value!!.copy(
                                number = 2,
                                tradingType = t.tradingType
                        )
                    }

                })
    }

    fun getNick() {
        repo.getTradingNick()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Security>() {
                    override fun onDone() {

                    }

                    override fun onNext(t: Security) {
                        voObservable.value = voObservable.value!!.copy(
                                number = 3,
                                tradingNick = t.tradingNick
                        )
                    }

                })
    }

    override fun handleViewState(vo: Security) {
        when (vo.number) {
            1 -> mView?.refreshPage(vo)
            2 -> mView?.getTypeSuccess(vo)
            3 -> mView?.getNickSuccess(vo)
        }
    }
}
