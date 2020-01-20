package com.eex.mvp.mine.security.tradenick

import android.os.Bundle
import com.eex.domin.entity.trading.Trading
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 16:19
 */
class TradeNickPresenter @Inject constructor(
    private val repo:TradeNickRepoImpl
) : BasePresenterImpl<TradeNickContract.View, Trading>(), TradeNickContract.Presenter {

    fun setNick(nick:String){
        mView?.showProgress()
        repo.setNick(nick)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<Trading>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Trading) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                msg = t.msg
                        )
                    }
                })
    }

    override fun handleViewState(vo: Trading) {
        when (vo.level) {
            1 -> mView?.setNickSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Trading()
    }

}