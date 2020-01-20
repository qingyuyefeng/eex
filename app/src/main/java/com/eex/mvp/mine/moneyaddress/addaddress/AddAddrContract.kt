package com.eex.mvp.mine.moneyaddress.addaddress

import com.eex.domin.entity.moneyaddress.Address
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:35
 */
class AddAddrContract {
    interface View:BaseView{
        fun getCoinSuccess(address: Address)
        fun addSuccess(address: Address)
    }
    interface Presenter:BasePresenter<View>
}