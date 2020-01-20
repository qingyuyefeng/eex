package com.eex.mvp.mine.security.phoneoremail

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.constant.Constants
import com.eex.domin.entity.bindphoneoremail.BindPE
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 15:38
 */
class PhoneOrEmailPresenter @Inject constructor(
        private val repo: BindsRepoImpl,
        private val mmkv: MMKV
) : BasePresenterImpl<PhoneOrEmailContract.View, BindPE>(), PhoneOrEmailContract.Presenter {

    val email: String
        get() = mmkv.decodeString(Constants.EMAIL_CERTIFY)

    val phone: String
        get() = mmkv.decodeString(Constants.PHONE_CERTIFY)

    val hasEmail: Boolean
        get() = !TextUtils.isEmpty(mmkv.decodeString(Constants.EMAIL_CERTIFY))

    val hasPhone: Boolean
        get() = !TextUtils.isEmpty(mmkv.decodeString(Constants.PHONE_CERTIFY))

    override fun initPageState(params: Bundle?) {
        voObservable.value = BindPE()
    }

    fun getPhoneCode(phone: String, sendType: String) {
        mView?.showProgress()
        repo.getEmailCode(phone, sendType)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BindPE>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BindPE) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1
                        )
                    }
                })
    }

    fun getEmailCode(email: String, sendType: String) {
        mView?.showProgress()
        repo.getEmailCode(email, sendType)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BindPE>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BindPE) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1
                        )
                    }
                })
    }

    fun bindPhone(mobilePhone: String, code: String) {
        mView?.showProgress()
        repo.bindPhone(mobilePhone, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BindPE>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BindPE) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2
                        )
                    }

                })
    }

    fun unbindPhone(mobilePhone: String, code: String) {
        mView?.showProgress()
        repo.unbindPhone(mobilePhone, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BindPE>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BindPE) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3
                        )
                    }

                })
    }

    fun bindEmail(email: String, code: String) {
        mView?.showProgress()
        repo.bindEmail(email, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BindPE>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BindPE) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 4
                        )
                    }

                })
    }

    fun unbindEmail(email: String, code: String) {
        mView?.showProgress()
        repo.unbindEmail(email, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<BindPE>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: BindPE) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 5
                        )
                    }

                })
    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    fun dealData(type: Int, phoneoremail: String) {
        //1、获取验证码  2、绑定手机  3、解绑手机  4、绑定邮箱  5、解绑邮箱
        when (type) {
            2 -> mmkv.encode(Constants.PHONE_CERTIFY, phoneoremail)
            3 -> mmkv.remove(Constants.PHONE_CERTIFY)
            4 -> mmkv.encode(Constants.EMAIL_CERTIFY, phoneoremail)
            5 -> mmkv.remove(Constants.EMAIL_CERTIFY)
        }
    }

    override fun handleViewState(vo: BindPE) {
        when (vo.level) {
            0->{}
            1 -> mView?.getCodeSuccess()
            else -> mView?.dealSuccess(vo)
        }
    }
}
