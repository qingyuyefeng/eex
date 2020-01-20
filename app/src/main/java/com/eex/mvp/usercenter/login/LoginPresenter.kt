package com.eex.mvp.usercenter.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.constant.Constants
import com.eex.domin.entity.login.Login
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/25 11:05
 */
class LoginPresenter @Inject constructor(
        private val repo: LoginRepoImpl,
        val mmkv: MMKV,
        val sp:SharedPreferences
) : BasePresenterImpl<LoginContract.View, Login>(), LoginContract.Presenter {

    val userName: String
        get() = mmkv.decodeString(Constants.USERNAME, "")
    val inputNumber: String
        get() = sp.getString(Constants.INPUT_NUMBER, "")
    val passWord: String
        get() = mmkv.decodeString(Constants.PASSWORD, "")
    val language: String
        get() = mmkv.decodeString(Constants.LANGUAGE, "")

    override fun initPageState(params: Bundle?) {

    }

    fun loginFirst(username: String, password: String, input: String) {
        mmkv.encode(Constants.USERNAME, username)
        mmkv.encode(Constants.PASSWORD, password)
        sp.edit().putString(Constants.INPUT_NUMBER, input).apply()
        mView?.showProgress()
        repo.loginOne(username = username, password = password)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Login>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Login) {
                        voObservable.value = t.copy(
                                level = 1,
                                inputNumber = input,
                                language = language
                        )
                    }
                })

    }

    fun loginSencondPE(checkType: String,
                       phoneoremail: String,
                       code: String,
                       username: String,
                       password: String) {
        mView?.showProgress()
        repo.loginTwoPE(checkType, phoneoremail, code, username, password)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Login>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Login) {
                        voObservable.value = t.copy(
                                level = 3
                        )
                    }
                })

    }

    fun loginSencondGO(checkType: String,
                       googleKey: String,
                       code: String,
                       username: String,
                       password: String) {
        mView?.showProgress()
        repo.loginTwoGO(checkType, googleKey, code, username, password)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Login>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Login) {
                        voObservable.value = t.copy(
                                level = 3
                        )
                    }
                })

    }

    fun getPCode(phone: String, userName: String) {
        mView?.showProgress()
        repo.getPhoneCode(phone, userName)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Login>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Login) {
                        voObservable.value = voObservable.value?.copy(
                                level = 2,
                                msg =t.msg
                        )
                    }

                })
    }

    fun getECode(email: String, userName: String) {
        mView?.showProgress()
        repo.getEmailCode(email, userName)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Login>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Login) {
                        voObservable.value = voObservable.value?.copy(
                                level = 2,
                                msg = t.msg
                        )
                    }

                })
    }

    override fun handleViewState(vo: Login) {
        when (vo.level) {
            1, 3 -> mView?.loginSuccess(vo)
            2 -> mView?.getCodeSuccess(vo)
        }
    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    fun saveMMKV(login: Login) {
        mmkv.encode(Constants.USERID, login.id)
        mmkv.encode(Constants.TOKEN_ID, login.tokenId)
        mmkv.encode(Constants.USERNAME, login.userName)
        mmkv.encode(Constants.PASSWORD, login.passWord)
        mmkv.encode(Constants.TRADE_PASSWORD, login.accountPassWord)
        mmkv.encode(Constants.USER_STATUS, login.status)
        mmkv.encode(Constants.EMAIL_CERTIFY, login.email)
        mmkv.encode(Constants.REAL_NAME_CERTIFY, login.realName)
        mmkv.encode(Constants.SALT, login.salt)
        mmkv.encode(Constants.USERCODE, login.userCode)
        mmkv.encode(Constants.HASEMAIL, login.hasEmail)
        mmkv.encode(Constants.HASPHONE, login.hasPhone)
        mmkv.encode(Constants.PHONE_CERTIFY, login.phone)
        mmkv.encode(Constants.INVATE_CODE, login.invateCode)
        mmkv.encode(Constants.GOOGLE_STATE, login.googleState)
        mmkv.encode(Constants.EXAM_STATE, login.examState)
        mmkv.encode(Constants.GOOGLE_KEY, login.googleKey)
        mmkv.encode(Constants.MERCHANT, login.merchant)
    }

}