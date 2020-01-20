package com.eex.mvp.mine.bankcards.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import com.eex.R
import com.eex.domin.entity.moneyaddress.AddressBean
import java.text.SimpleDateFormat

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 15:55
 */
class AddressAdapter(
        private val context: Context,
        list: MutableList<AddressBean>
) : BaseAdapter<AddressBean, AddressAdapter.AddressHolder>(list) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AddressHolder {
        return AddressHolder(LayoutInflater.from(context).inflate(R.layout.re_address_item, p0,false))
    }


    inner class AddressHolder(view: View) : BaseViewHolder<AddressBean>(view) {

        val tv_name = view.findViewById<TextView>(R.id.tv_aac)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_content = view.findViewById<TextView>(R.id.tv_content)
        val tv_extra = view.findViewById<TextView>(R.id.tv_extra)
        val tv_delete = view.findViewById<TextView>(R.id.tv_delete)

        override fun bindView(item: AddressBean) {
            tv_name.text = item.coinCode
            tv_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(item.createTime)
            tv_content.text = item.walletAddress
            if(TextUtils.isEmpty(item.remark)){
                tv_extra.visibility = View.GONE
            }else{
                tv_extra.text = item.remark
                tv_extra.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                deleteAddr?.itemClick(item)
            }

            tv_delete.setOnClickListener {
                deleteAddr?.delete(item.id,layoutPosition)
            }
        }

    }

    private var deleteAddr: DeleteAddress? = null
    fun setDeleteAddress(deleteAddr: DeleteAddress) {
        this.deleteAddr = deleteAddr
    }

    interface DeleteAddress {
        fun delete(id: String, position: Int)
        fun itemClick(bean:AddressBean)
    }

}