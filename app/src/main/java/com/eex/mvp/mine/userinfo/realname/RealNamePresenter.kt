package com.eex.mvp.mine.userinfo.realname

import android.os.Bundle
import com.eex.domin.entity.realname.RealName
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 16:15
 */
class RealNamePresenter @Inject constructor(
        private val repo: RealNameRepoImpl
) : BasePresenterImpl<RealNameContract.View, RealName>(), RealNameContract.Presenter {

    fun realName(contry: String,
                 cardType: String,
                 cardNo: String,
                 sex: String,
                 surname: String,
                 givename: String) {
        mView?.showProgress()
        repo.realName(contry, cardType, cardNo, sex, surname, givename)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<RealName>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: RealName) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                msg = t.msg
                        )
                    }
                })
    }


    override fun handleViewState(vo: RealName) {
        when (vo.level) {
            1 -> mView?.realNameSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = RealName()
    }

}