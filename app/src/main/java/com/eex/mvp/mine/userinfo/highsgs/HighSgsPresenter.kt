package com.eex.mvp.mine.userinfo.highsgs

import android.graphics.Bitmap
import android.os.Bundle
import com.eex.domin.entity.realname.RealName
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 11:16
 */
class HighSgsPresenter @Inject constructor(
        private val repo: HighSgsRepoImpl
) : BasePresenterImpl<HighSgsContract.View, RealName>(), HighSgsContract.Presenter {

    fun uploadPic(path: String, bitmap: Bitmap, i: Int) {
        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        mView?.showProgress()

        repo.uploadPic(filePart)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<RealName>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: RealName) {
                        voObservable.value = voObservable.value!!.copy(
                                level = i,
                                picName = t.picName,
                                bitmap = bitmap
                        )
                    }
                })

    }

    fun hishSgs(idCardFrontUrl: String, idCardBackUrl: String, handIdCardUrl: String){
        mView?.showProgress()
        repo.highSgs(idCardFrontUrl, idCardBackUrl, handIdCardUrl)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object :CompletionObserver<RealName>(){
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: RealName) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 4,
                                msg = t.msg
                        )
                    }
                })
    }


    override fun handleViewState(vo: RealName) {
        when (vo.level) {
            1, 2, 3 -> mView?.uploadSuccess(vo)
            4 -> mView?.highSgsSuccess(vo)
        }
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = RealName()
    }
}