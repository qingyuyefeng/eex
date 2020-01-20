package com.eex.mvp.mine.security.legalmethod.bankcard

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.extensions.filterResult
import com.eex.mvp.mine.security.legalmethod.LegalMethodMapper
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 17:48
 */
class BankCardRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :BankCardRepo{
    override fun getPhoneCode(): Observable<LegalMethod> {
        return service.createApi(BankCardApi::class.java)
                .getPhoneCode(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                        phone = mmkv.decodeString(Constants.PHONE_CERTIFY,""),
                        userName = mmkv.decodeString(Constants.USERNAME,""))
                .filterResult()
                .map(LegalMethodMapper)
    }

    override fun getEmailCode(): Observable<LegalMethod> {
        return service.createApi(BankCardApi::class.java)
                .getEmailCode(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                        email = mmkv.decodeString(Constants.EMAIL_CERTIFY,""),
                        userName = mmkv.decodeString(Constants.USERNAME,""))
                .filterResult()
                .map(LegalMethodMapper)
    }

    override fun setBankCard(userName:String,id:String,code: String, accountNo: String, bankName: String, childBankName: String, bankAddress: String): Observable<LegalMethod> {
        return service.createApi(BankCardApi::class.java)
                .setBankCard(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                        userName = userName,
                        id = if(id.isEmpty()) null else id,code = code,
                        accountNo = accountNo,bankName = bankName,
                        childBankName = childBankName,bankAddress = bankAddress)
                .filterResult()
                .map(LegalMethodMapper)

    }
}