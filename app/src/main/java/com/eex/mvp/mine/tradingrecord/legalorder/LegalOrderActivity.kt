package com.eex.mvp.mine.tradingrecord.legalorder

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.eex.R
import com.eex.common.view.SpinnerPopupWindow
import com.eex.domin.entity.dealrecord.LegalOrder
import com.eex.domin.entity.dealrecord.LegalOrderBean
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.mine.tradingrecord.legalorder.adapter.LegalOrderAdapter
import kotlinx.android.synthetic.main.re_activity_legal_order.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 12:06
 */
class LegalOrderActivity : MVPBaseActivity<LegalOrder, LegalOrderContract.View, LegalOrderPresenter>(), LegalOrderContract.View {
    override val layoutId: Int
        get() = R.layout.re_activity_legal_order

    private var legalList: MutableList<LegalOrderBean>? = null
    private var adapter: LegalOrderAdapter? = null

    private var statusList = arrayListOf("全部订单", "进行中", "已完成", "已取消")
    private var spw: SpinnerPopupWindow<String>? = null

    private var filterList: MutableList<LegalOrderBean>? = null

    override fun getDataSuccess(legalOrder: LegalOrder) {
        if (legalOrder.legalList.isNotEmpty()) {
            legalList!!.clear()
            legalList!!.addAll(legalOrder.legalList)
            filterList!!.clear()
            filterList!!.addAll(legalOrder.legalList)
            adapter?.notifyDataSetChanged()
        }
        showEmpty(filterList!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        legalList = ArrayList()
        filterList = ArrayList()
        adapter = LegalOrderAdapter(this, filterList!!)
        rv_legal_record.adapter = adapter
        rv_legal_record.layoutManager = LinearLayoutManager(this)

        tv_filter.setOnClickListener {

        }
        tv_all.setOnClickListener {
            if (spw == null) {
                spw = SpinnerPopupWindow(this, statusList, tv_all.measuredWidth)
                spw!!.setItemClick(object : SpinnerPopupWindow.ItemClick {
                    override fun itemClick(position: Int) {
                        when (position) {
                            0 -> {  //全部
                                filterList!!.clear()
                                filterList!!.addAll(legalList!!)
                                adapter?.notifyDataSetChanged()
                            }
                            1 -> {  //进行中
                                filterList!!.clear()
                                filterList!!.addAll(
                                        legalList!!.filter {
                                            it.state == 1 || it.state == 2
                                        })
                                adapter?.notifyDataSetChanged()
                            }
                            2 -> { //已完成
                                filterList!!.clear()
                                filterList!!.addAll(
                                        legalList!!.filter {
                                            it.state == 5
                                        })
                                adapter?.notifyDataSetChanged()
                            }
                            3 -> { //已取消
                                filterList!!.clear()
                                filterList!!.addAll(
                                        legalList!!.filter {
                                            it.state == 3
                                        })
                                adapter?.notifyDataSetChanged()
                            }
                        }
                        showEmpty(filterList!!)
                        spw?.dismiss()
                    }
                })
            }
            spw?.showAsDropDown(tv_all)
        }

        presenter.getLegalOrder()
        tv_empty.setOnClickListener {
            presenter.getLegalOrder()
        }

    }

    private fun showEmpty(list: MutableList<*>) {
        if (list.isEmpty()) {
            rv_legal_record.visibility = View.GONE
            tv_empty.visibility = View.VISIBLE
        } else {
            rv_legal_record.visibility = View.VISIBLE
            tv_empty.visibility = View.GONE
        }
    }
}