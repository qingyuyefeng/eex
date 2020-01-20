package com.eex.mvp.mine.security.googleverify.bindgoogle1

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
class GooglePresenter @Inject constructor(
        private val repo: GoogleRepoImpl
) : BasePresenterImpl<GoogleContract.View, Google>(), GoogleContract.Presenter {

    fun getKey() {
        mView?.showProgress()
        repo.createKey()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Google>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Google) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                googleKey = t.googleKey,
                                userName = t.userName,
                                company = t.company
                        )
                    }

                })
    }

    override fun handleViewState(vo: Google) {
        when (vo.level) {
            1 -> mView?.getKeySuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Google()
    }

}