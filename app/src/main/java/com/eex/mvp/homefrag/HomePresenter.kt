package com.eex.mvp.homefrag

import android.os.Bundle
import com.eex.constant.Constants
import com.eex.domin.entity.home.Home
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:21
 */
class HomePresenter @Inject constructor(
        private val repo: HomeRepoImpl,
        private val mmkv: MMKV
) : BasePresenterImpl<HomeContract.View, Home>(), HomeContract.Presenter {

    val hasToken: Boolean
        get() = mmkv.decodeString(Constants.TOKEN_ID, "").isNotEmpty()

    fun getBanners(useragent: String, websiteType: String) {
        repo.getBanners(useragent, websiteType)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Home>() {
                    override fun onDone() {
                    }

                    override fun onNext(t: Home) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 4,
                                bannerList = t.bannerList
                        )
                    }
                })
    }

    fun getList1() {
      mView?.showProgress()
        repo.getList1()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Home>() {
                    override fun onDone() {

                    }

                    override fun onNext(t: Home) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1,
                                list1 = t.list1
                        )
                    }

                })
    }

    fun getList2(delkeys: String) {
        repo.getList2(delkeys)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Home>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: Home) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                list2 = t.list2
                        )
                    }

                })
    }

    fun getNotice(websiteType: String) {
        repo.getNotices(websiteType)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Home>() {
                    override fun onDone() {
                    }

                    override fun onNext(t: Home) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                noticeData = t.noticeData
                        )
                    }

                })
    }

    fun openConnect() {
        repo.openNet()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<Home>() {
                    override fun onDone() {
                    }

                    override fun onNext(t: Home) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 5,
                                refreshBean = t.refreshBean
                        )
                    }
                })
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = Home()
    }

    override fun handleViewState(vo: Home) {
        when (vo.level) {
            1 -> mView?.getList1Success(vo)
            2 -> mView?.getList2Success(vo)
            3 -> mView?.getNoticeSuccess(vo)
            4 -> mView?.getBannersSuccess(vo)
            5 -> mView?.getRefreshSuccess(vo)
        }
    }

}