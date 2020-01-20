package com.eex.mvp.mine.security.googleverify.bindgoogle2

import android.os.Bundle
import com.eex.domin.entity.google.Google
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:21
 */
class GooglePresenter2 @Inject constructor(
        private val repo: GoogleRepoImpl2
) : BasePresenterImpl<GoogleContract2.View, Google>(), GoogleContract2.Presenter {

    fun checkCode(code:String,secret: String) {
        mView?.showProgress()
        repo.checkCode(code,secret)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Google>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Google) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                msg = t.msg
                        )
                    }

                })
    }

    override fun handleViewState(vo: Google) {
        when (vo.level) {
            2 -> mView?.checkCodeSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Google()
    }

}