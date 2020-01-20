package com.eex.mvp.mine.bankcards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import com.eex.R
import com.eex.domin.entity.bankcards.BankCardBean

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 15:55
 */
class BankCardAdapter(
        private val context: Context,
        list: MutableList<BankCardBean>
) : BaseAdapter<BankCardBean, BankCardAdapter.BankCardHolder>(list) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BankCardHolder {
        return BankCardHolder(LayoutInflater.from(context).inflate(R.layout.re_bankcards_item, p0,false))
    }


    inner class BankCardHolder(view: View) : BaseViewHolder<BankCardBean>(view) {

        val tv_bankName = view.findViewById<TextView>(R.id.tv_bank_name)
        val tv_bankNumber = view.findViewById<TextView>(R.id.tv_bank_number)
        val tv_delete = view.findViewById<TextView>(R.id.tv_delete)

        override fun bindView(item: BankCardBean) {
            tv_bankName.text = item.bankName
            tv_bankNumber.text = item.bankCardNo

            itemView.setOnClickListener {
                deleteCard?.itemClick(item)
            }
            tv_delete.setOnClickListener {
                deleteCard?.delete(item.id,layoutPosition)
            }
        }

    }

    private var deleteCard: DeleteCard? = null
    fun setDeleteCard(deleteCard: DeleteCard) {
        this.deleteCard = deleteCard
    }

    interface DeleteCard {
        fun delete(id: String, position: Int)
        fun itemClick(bean:BankCardBean)
    }

}