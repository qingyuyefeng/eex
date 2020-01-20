package com.eex.mvp.mine.moneyaddress

import com.eex.domin.entity.moneyaddress.Address
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 10:53
 */
class AddressContract {
    interface View:BaseView{
        fun getAddrSuccess(addr:Address)
        fun deleteAddrSuccess(addr: Address)
    }
    interface Presenter:BasePresenter<View>
}