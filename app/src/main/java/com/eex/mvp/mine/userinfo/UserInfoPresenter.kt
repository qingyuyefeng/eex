package com.eex.mvp.mine.userinfo

import android.os.Bundle
import com.eex.apis.RetrofitService
import com.eex.apis.SecurityApi
import com.eex.constant.Constants
import com.eex.domin.entity.security.Security
import com.eex.extensions.asyncAssign
import com.eex.extensions.filterResult
import com.eex.mvp.BasePresenterImpl
import com.eex.repository.mapper.SecurityMapper
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/2 15:31
 */
class UserInfoPresenter @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : BasePresenterImpl<UserInfoContract.View, Security>(), UserInfoContract.Presenter {
    override fun initPageState(params: Bundle?) {
        voObservable.value = Security()
    }

    fun getInfo() {
        mView?.showProgress()
        service.createApi(SecurityApi::class.java)
                .authStauts(mmkv.decodeString(Constants.TOKEN_ID))
                .filterResult()
                .map(SecurityMapper)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Security>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Security) {
                        voObservable.value = voObservable.value!!.copy(
                                number = 1,
                                level = t.level,
                                authStatus = t.authStatus
                        )
                    }
                })
    }

    override fun handleViewState(vo: Security) {
        when (vo.number) {
            1 -> mView?.getInfoSuccess(vo)
        }
    }
}