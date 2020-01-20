package com.eex.mvp.mine.security.nrewpassword

import android.os.Bundle
import com.eex.common.base.Data
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/13 11:50
 */
class NewPwdPresenter @Inject constructor(
        private val repo: NewPwdRepoImpl
) : BasePresenterImpl<NewPwdContract.View, Data<Any>>(), NewPwdContract.Presenter {

    override fun initPageState(params: Bundle?) {
//        voObservable.value = Data()
    }

    fun changePwd(oldPwd: String, newPwd: String) {
        mView?.showProgress()
        repo.changePwd(oldPwd, newPwd)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Data<Any>>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Data<Any>) {
                        voObservable.value = t
                    }

                })
    }

    override fun handleViewState(vo: Data<Any>) {
        if (vo.isStauts) {
            mView?.setNewPwdSuccess(vo)
        }
    }
}