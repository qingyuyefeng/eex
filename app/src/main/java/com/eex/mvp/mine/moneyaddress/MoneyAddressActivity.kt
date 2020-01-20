package com.eex.mvp.mine.moneyaddress

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.eex.R
import com.eex.domin.entity.moneyaddress.Address
import com.eex.domin.entity.moneyaddress.AddressBean
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.mine.bankcards.adapter.AddressAdapter
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_address.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 10:45
 */
class MoneyAddressActivity : MVPBaseActivity<Address, AddressContract.View, AddressPresenter>(), AddressContract.View {
    private var adapter: AddressAdapter? = null
    private var list: MutableList<AddressBean>? = null
    private var flag = 0

    override val layoutId: Int = R.layout.re_activity_address

    override fun getAddrSuccess(addr: Address) {
        if (addr.addrList.isEmpty()) {
            rv_money_address.visibility = View.GONE
            ll_add_address_con.visibility = View.GONE
            ll_add_address.visibility = View.VISIBLE
        } else {
            rv_money_address.visibility = View.VISIBLE
            ll_add_address_con.visibility = View.VISIBLE
            ll_add_address.visibility = View.GONE
            list?.clear()
            list!!.addAll(addr.addrList)
            adapter?.notifyDataSetChanged()
        }
    }


    override fun deleteAddrSuccess(addr: Address) {
        showToast(addr.msg)
        list?.removeAt(addr.deleteId)
        adapter?.notifyItemRemoved(addr.deleteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flag = intent.getIntExtra("flag", 0)

        initView()
        presenter.getAddressList()
    }

    private fun initView() {
        list = ArrayList()
        adapter = AddressAdapter(this, list!!)
        adapter?.setDeleteAddress(object : AddressAdapter.DeleteAddress {

            override fun itemClick(bean: AddressBean) {
                if (flag == 1) {
                    val intent = Intent()
                    intent.putExtra("bean",bean)
                    setResult(250,intent)
                    finish()
                }
            }

            override fun delete(id: String, position: Int) {
                presenter.deledeAddr(id, position)
            }
        })
        rv_money_address.adapter = adapter
        rv_money_address.layoutManager = LinearLayoutManager(this)

        ll_add_address.setOnClickListener {
            Navigator.toAddAddressActivity(this, 111)
        }
        ll_add_address_con.setOnClickListener {
            Navigator.toAddAddressActivity(this, 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 111) {
            presenter.getAddressList()
        }
    }
}