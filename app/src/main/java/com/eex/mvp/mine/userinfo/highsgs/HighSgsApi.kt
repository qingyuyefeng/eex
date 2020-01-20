package com.eex.mvp.mine.userinfo.highsgs

import com.eex.common.base.Data
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 18:28
 */
interface HighSgsApi {
    /**
     * 上传图片
     */
//       File file = new File(path);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    @Multipart
    @POST("user/fileupload/upload")
    fun upload(@Header("authorization") apikey: String, @Part file: MultipartBody.Part): Observable<Data<Any>>

    /**
     * 实名认证3提交接口
     */
//    hashMap.put("handIdCardUrl", "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" + pho3)
//    hashMap.put("idCardFrontUrl","http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" + pho1)
//    hashMap.put("idCardBackUrl", "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" + pho2)
    @FormUrlEncoded
    @POST("user/thr-level-auth")
    fun levelauth(@Header("authorization") apikey: String,
                  @Field("idCardFrontUrl") idCardFrontUrl: String,
                  @Field("idCardBackUrl") idCardBackUrl: String,
                  @Field("handIdCardUrl") handIdCardUrl: String): Observable<Data<Any>>
}