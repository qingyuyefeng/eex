package com.eex.mvp.mine.financialcenter.financialrecords

import com.eex.common.base.Data
import com.eex.domin.entity.financialcenter.MoneyCoin
import com.eex.home.bean.Page
import com.eex.home.bean.PaningMoneyDetails
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/21 18:04
 */
interface FinancialRecordsApi {
    /**
     * 列表查询可理财的币种更新后接口
     */
    @POST("lockingMoney/config/getLockMoneyDetailCoin")
    fun getMoneyDetailCoins(@Header("authorization") apikey: String): Observable<Data<List<MoneyCoin>>>


    /**
     * 分页查询可理财
     */
    /*
    *        hashMap.put("coinCode", txBiName.getText().toString().trim());
        }
        hashMap.put("pageNo", pageNo + "");
        hashMap.put("pageSize", pageSize + "");
        String type = textType.getText().toString().trim();
        Log.e("name", txBiName.getText().toString().trim() + "---" + type);
        if (type.equals("全部")) {

        } else if (type.equals("已锁仓")) {
            hashMap.put("state", "1");
        } else if (type.equals("解仓待审核")) {
            hashMap.put("state", "2");
        } else if (type.equals("解仓审核不通过")) {
            hashMap.put("state", "4");
        } else if (type.equals("已解仓")) {
            hashMap.put("state", "3");
        } else if (type.equals("部分已解仓")) {
            hashMap.put("state", "5");
        }
    * */
    @FormUrlEncoded
    @POST("lockingMoney/record/findLockingRecordPage")
    fun findLockingRecordPage(@Header("authorization") apikey: String,
                              @Field("coinCode") coinCode: String,
                              @Field("pageNo") pageNo: String,
                              @Field("pageSize") pageSize: String,
                              @Field("state") state: String): Observable<Data<Page<PaningMoneyDetails>>>
}