package com.eex.mvp.mine.moneyaddress

import com.eex.common.base.Data
import com.eex.domin.entity.moneyaddress.Address
import com.eex.domin.entity.moneyaddress.AddressList
import com.eex.domin.entity.moneyaddress.CoinBean
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 14:55
 */
object AddressMapper : Function<Data<AddressList>, Address> {
    override fun apply(t: Data<AddressList>): Address {
        return Address(
                addrList = t.data.resultList ?: ArrayList()
        )
    }
}


object AddAddrMapper : Function<Data<List<CoinBean>>, Address> {
    override fun apply(t: Data<List<CoinBean>>): Address {
        return Address(
                coinList = t.data
        )
    }
}

object AddAddrMapper1 : Function<Data<Any>, Address> {
    override fun apply(t: Data<Any>): Address {
        return Address(
                msg = t.msg
        )
    }
}