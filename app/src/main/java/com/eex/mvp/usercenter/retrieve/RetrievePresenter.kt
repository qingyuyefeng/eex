package com.eex.mvp.usercenter.retrieve

import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.domin.entity.retrieve.Retrieve
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 18:21
 */
class RetrievePresenter @Inject constructor(
        private val repo: RetrieveRepoImpl
) : BasePresenterImpl<RetrieveContract.View, Retrieve>(), RetrieveContract.Presenter {

    override fun initPageState(params: Bundle?) {

    }
    fun getEmailCode(email: String) {
        mView?.showProgress()
        repo.getEmailCode(email)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Retrieve>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Retrieve) {
                        voObservable.value = t.copy(
                                level = 1
                        )
                    }

                })
    }

    fun getPhoneCode(phone: String) {
        mView?.showProgress()
        repo.getPhoneCode(phone)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Retrieve>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Retrieve) {
                        voObservable.value = t.copy(
                                level = 1
                        )
                    }

                })
    }

    fun checkCode(phoneoremail: String, code: String, checkType: String) {
        mView?.showProgress()
        repo.checkCode(phoneoremail, code, checkType)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Retrieve>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Retrieve) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                msg = t.msg
                        )
                    }
                })
    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    override fun handleViewState(vo: Retrieve) {
        when (vo.level) {
            1 -> mView?.getCodeSuccess(vo)
            2 -> mView?.nextSuccess(vo)
        }
    }
}