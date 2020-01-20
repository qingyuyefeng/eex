package com.eex.mvp.mine.security.googleverify.unbindgoogle

import android.os.Bundle
import com.eex.constant.Constants
import com.eex.domin.entity.google.Google
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 14:07
 */
class UnbindGooglePresenter @Inject constructor(
        private val repo: UnbindGoogleRepoImpl,
        private val mmkv: MMKV
) : BasePresenterImpl<UnbindGoogleContract.View, Google>(), UnbindGoogleContract.Presenter {

    fun unBind(pwd: String, code: String) {
        mView?.showProgress()
        repo.unbind(pwd, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Google>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Google) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3
                        )
                    }

                })
    }

    fun removeGoogle(){
        mmkv.remove(Constants.GOOGLE_KEY)
    }

    override fun handleViewState(vo: Google) {
        when (vo.level) {
            3 -> mView?.unbindSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Google()
    }

}