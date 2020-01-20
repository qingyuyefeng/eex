package com.eex.mvp.usercenter.setnewpassword

import android.os.Bundle
import com.eex.domin.entity.retrieve.Retrieve
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/3 16:05
 */
class SetNewPresenter @Inject constructor(
    private val repo:SetNewRepoImpl
):BasePresenterImpl<SetNewContract.View,Retrieve>(),SetNewContract.Presenter {

    override fun initPageState(params: Bundle?) {

    }

    fun resetPassword(newPwd: String, checkType: String, phoneoremail: String, code: String){
        mView?.showProgress()
        repo.resetPassword(newPwd, checkType, phoneoremail, code)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<Retrieve>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Retrieve) {
                        voObservable.value = t
                    }

                })
    }

    override fun handleViewState(vo: Retrieve) {
        mView?.setNewSuccess(vo)
    }
}