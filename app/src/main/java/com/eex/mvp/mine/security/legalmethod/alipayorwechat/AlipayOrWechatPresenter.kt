package com.eex.mvp.mine.security.legalmethod.alipayorwechat

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import com.eex.common.util.TimeCount
import com.eex.constant.Constants
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 10:14
 */
class AlipayOrWechatPresenter @Inject constructor(
        private val repo: AlipayOrWechatRepoImpl,
        private val mmkv: MMKV
) : BasePresenterImpl<AlipayOrWechatContract.View, LegalMethod>(), AlipayOrWechatContract.Presenter {

    val phone: String
        get() = mmkv.decodeString(Constants.PHONE_CERTIFY)

    fun getPhoneCode() {
        mView?.showProgress()
        repo.getPhoneCode()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1
                        )
                    }

                })
    }

    fun getEmailCode() {
        mView?.showProgress()
        repo.getEmailCode()
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 1
                        )
                    }

                })
    }

    fun setAlipayOrWechat(accountType: String, userName: String, id: String, code: String, accountNo: String, imageUrl: String) {
        mView?.showProgress()
        repo.setAlipayOrWechat(accountType, userName, id, code, accountNo, imageUrl)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 3,
                                msg = t.msg
                        )
                    }
                })
    }

    fun uploadPic(path: String, bitmap: Bitmap) {
        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        mView?.showProgress()

        repo.uploadPic(filePart)
                .doOnSubscribe(doOnSubscriber)
                .asyncAssign()
                .subscribe(object : CompletionObserver<LegalMethod>() {
                    override fun onDone() {
                        mView?.dissmissProgress()
                    }

                    override fun onNext(t: LegalMethod) {
                        voObservable.value = voObservable.value!!.copy(
                                level = 2,
                                picName = t.picName,
                                bitmap = bitmap
                        )
                    }
                })

    }

    fun openTimer(context: Context, button: Button, text: String) {
        val time = TimeCount(context, button, text, 60000, 1000)
        time.start()
    }

    override fun initPageState(params: Bundle?) {
        voObservable.value = LegalMethod()
    }

    override fun handleViewState(vo: LegalMethod) {
        when (vo.level) {
            1 -> mView?.getCodeSuccess(vo)
            2 -> mView?.uploadPicSuccess(vo)
            3 -> mView?.setMethodSuccess(vo)
        }
    }

}