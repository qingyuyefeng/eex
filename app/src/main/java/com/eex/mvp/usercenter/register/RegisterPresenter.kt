package com.eex.mvp.usercenter.register

import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.domin.entity.register.Register
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 14:18
 */
class RegisterPresenter @Inject constructor(
        private val repo: RegisterRepoImpl
) : BasePresenterImpl<RegisterContract.View, Register>(), RegisterContract.Presenter {

    override fun initPageState(params:Bundle?) {

    }

    fun getImageCode() {
        mView?.showProgress()
        repo.getImageCode()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Register>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Register) {
                        voObservable.value = t.copy(
                                level = 1
                        )
                    }

                })
    }

    fun getPCode(input:String,imageCode:String,imageKey:String) {
        mView?.showProgress()
        repo.sendPhoneCode(input,imageCode,imageKey)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<Register>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Register) {
                        voObservable.value = t.copy(
                                level = 2
                        )
                    }

                })
    }

    fun getECode(email: String) {
        mView?.showProgress()
        repo.sendEmailCode(email)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Register>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Register) {
                        voObservable.value = t.copy(
                                level = 2
                        )
                    }

                })
    }

    fun register(username: String, password: String, code: String, invateCode: String) {
        mView?.showProgress()
        repo.register(username, password, code, invateCode)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<Register>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Register) {
                        voObservable.value = t.copy(
                                level = 3
                        )
                    }

                })
    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    override fun handleViewState(vo: Register) {
        when (vo.level) {
            1 -> mView?.getImageCodeSuccess(vo)
            2 -> mView?.getCodeSuccess(vo)
            3 -> mView?.registerSuccess(vo)
        }
    }
}