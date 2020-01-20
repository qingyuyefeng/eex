package com.eex.mvp.mine.mainfrag

import android.content.SharedPreferences
import android.os.Bundle
import com.eex.constant.Constants
import com.eex.domin.entity.mine.Mine
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.mvp.mine.MineContract
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 9:56
 */
class MinePresenter @Inject constructor(
        private val repo: MineRepoImpl,
        private val mmkv: MMKV,
        private val sp:SharedPreferences
) : BasePresenterImpl<MineContract.View, Mine>(), MineContract.Presenter {

    val accountPassWord: String
        get() = mmkv.decodeString(Constants.TRADE_PASSWORD, "")

    val userName: String
        get() = sp.getString(Constants.INPUT_NUMBER, "")

    override fun initPageState(params: Bundle?) {
        voObservable.value = Mine()
    }

    override fun onNewParams(params: Bundle?) {
        super.onNewParams(params)
        getUserInfo()
    }

    fun getUserInfo() {
        mView?.showProgress()
        repo.isSeller()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Mine>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Mine) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                merchantStatus = t.merchantStatus
                        )
                    }

                })
    }

    override fun handleViewState(vo: Mine) {
        when (vo.level) {
            1 -> mView?.getInfoSuccess(vo)
        }
    }
}