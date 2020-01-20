package com.eex.mvp.mainpage

import android.os.Bundle
import com.eex.BuildConfig
import com.eex.apis.RetrofitService
import com.eex.common.api.ApiService
import com.eex.constant.Constants
import com.eex.constant.Keys
import com.eex.extensions.asyncAssign
import com.eex.extensions.filterResult
import com.eex.home.bean.UpdatApp
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 17:09
 */
class MainPresenter @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : BasePresenterImpl<MainContract.View, MainPage>(), MainContract.Presenter {

    val tokenId: String
        get() = mmkv.decodeString(Constants.TOKEN_ID, "")

    fun checkApp() {
        val map = HashMap<String, String>()
        map[Keys.APPKEY] = Keys.APPKEY_VALUE
        map[Keys.APIKEY] = Keys.APIKEY_VALUE
        map[Keys.BUILD_VERSION] = BuildConfig.VERSION_NAME
        map["websiteType"] = "2"
        service.createApi(ApiService::class.java)
                .checkApp(map)
                .filterResult()
                .map {
                    MainPage(bean = it.data?: UpdatApp())
                }
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<MainPage>() {
                    override fun onDone() {
                    }

                    override fun onNext(t: MainPage) {
                        voObservable.value = voObservable.value!!.copy(
                                level = voObservable.value!!.level + 1,
                                bean = t.bean
                        )
                    }
                })
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = MainPage()
    }

    override fun handleViewState(vo: MainPage) {
        if (vo.level > -1) {
            mView?.getVersionSuccess(voObservable.value!!)
        }
    }
}