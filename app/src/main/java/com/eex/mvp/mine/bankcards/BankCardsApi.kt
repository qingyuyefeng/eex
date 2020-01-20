package com.eex.mvp.mine.bankcards

import com.eex.common.base.Data
import com.eex.domin.entity.bankcards.BankCardBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 15:02
 */
interface BankCardsApi {
    /**
     * 获取可用银行卡
     */
    @POST("user/bank/list-bank-card")
    fun getBankCards(@Header("authorization") apikey: String): Observable<Data<List<BankCardBean>>>

    /**
     * 删除银行卡
     */
    @FormUrlEncoded
    @POST("user/bank/del-bank-card")
    fun deleteBankCard(@Header("authorization") apikey: String, @Field("bankId") bankId: String): Observable<Data<Any>>


    /* val hasMap = HashMap<String, String>()
     //开户行
     hasMap.put("bankName", textviewBank.getText().toString().trim(
     { it <= ' ' }))
     //开户行所在地
     hasMap.put("bankAddress", textviewSheng.getText().toString().trim(
     { it <= ' ' }))
     //开户支行
     hasMap.put("bankChildName", BnakEdtBankName.getText().toString().trim(
     { it <= ' ' }))
     //mingzi
     hasMap.put("givename", BankEdtNamefo.getText().toString().trim(
     { it <= ' ' }))
     //姓名
     hasMap.put("surname", BankEdtName.getText().toString().trim(
     { it <= ' ' }))
     //账号
     hasMap.put("bankCardNo", rechargeEdtBankNuber.getText().toString().trim(
     { it <= ' ' }))*/
    /**
     * 添加银行卡
     */
    @FormUrlEncoded
    @POST("user/bank/add-bank-card")
    fun addBankCard(@Header("authorization") apikey: String,
                    @Field("bankName") bankName: String,
                    @Field("bankAddress") bankAddress: String,
                    @Field("bankChildName") bankChildName: String,
                    @Field("givename") givename: String,
                    @Field("surname") surname: String,
                    @Field("bankCardNo") bankCardNo: String
    ): Observable<Data<Any>>
}