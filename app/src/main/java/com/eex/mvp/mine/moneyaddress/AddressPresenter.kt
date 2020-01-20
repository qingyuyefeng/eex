package com.eex.mvp.mine.moneyaddress

import android.os.Bundle
import com.eex.domin.entity.moneyaddress.Address
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 10:56
 */
class AddressPresenter @Inject constructor(
        private val repo: AddressRepoImpl
) : BasePresenterImpl<AddressContract.View, Address>(), AddressContract.Presenter {

    fun getAddressList() {
        mView?.showProgress()
        repo.getAddress()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Address>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Address) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                addrList = t.addrList
                        )
                    }
                })
    }

    fun deledeAddr(id:String,pos:Int){
        mView?.showProgress()
        repo.deleteAddr(id)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<Address>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Address) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                deleteId = pos,
                                msg = t.msg
                        )
                    }
                })

    }

    override fun handleViewState(vo: Address) {
        when (vo.level) {
            1 -> mView?.getAddrSuccess(vo)
            2 -> mView?.deleteAddrSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Address()
    }

}