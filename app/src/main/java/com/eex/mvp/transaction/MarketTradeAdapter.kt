package com.eex.mvp.transaction

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.transaction.spot.TransactionRecord
import com.eex.mvp.transaction.MarketTradeAdapter.ViewHoder
import java.math.BigDecimal

class MarketTradeAdapter(
  private val mContext: Activity,
  private val dealType: DealType,
  private val onItemClick: (Int) -> Unit = {}
) : RecyclerView.Adapter<ViewHoder>() {
  private val records: MutableList<TransactionRecord> = mutableListOf()
  private var scale = 5
  fun setNewData(
    data: List<TransactionRecord>,
    scale: Int
  ) {
    records.clear()
    records.addAll(data)
    this.scale = scale
    notifyDataSetChanged()
  }

  private val inflater: LayoutInflater = LayoutInflater.from(mContext)

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  inner class ViewHoder(view: View) : RecyclerView.ViewHolder(view) {
    var textview_Price: TextView = itemView.findViewById(R.id.textview_Price)
    var textview_nuber: TextView = itemView.findViewById(R.id.textview_nuber)
    var simpleProgressbar: ProgressBar = itemView.findViewById(R.id.probar)
  }

  override fun onCreateViewHolder(
    p0: ViewGroup,
    p1: Int
  ): ViewHoder = ViewHoder(inflater.inflate(R.layout.item_buy_adapter, null))

  override fun getItemCount(): Int = records.size

  override fun onBindViewHolder(
    viewHoder: ViewHoder,
    position: Int
  ) {
    viewHoder.textview_Price?.text = records[position].price.setScale(
        8, BigDecimal.ROUND_DOWN
    )
        .stripTrailingZeros()
        .toPlainString()
        .toString()
    viewHoder.textview_nuber?.text = records[position].num.setScale(
        scale, BigDecimal.ROUND_DOWN
    )
        .stripTrailingZeros()
        .toPlainString()
        .toString()

    var max = records[0].num
        .multiply(BigDecimal(100))
    for (i in records.indices) {
      if (max.compareTo(records[position].num) == -1) {
        max = records[position].num
      }
    }

    if (dealType == SALE) {
      viewHoder.textview_Price?.setTextColor(CommonUtil.getColor(R.color.color_a50000))
      viewHoder.simpleProgressbar!!.progressDrawable =
        ContextCompat.getDrawable(mContext, R.drawable.my_progressbar)
    } else {
      viewHoder.textview_Price?.setTextColor(CommonUtil.getColor(R.color.color_00a546))
      viewHoder.simpleProgressbar!!.progressDrawable =
        ContextCompat.getDrawable(mContext, R.drawable.new_mypr)
    }

    viewHoder.simpleProgressbar!!.max = 10
    val str = max.divide(BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP)
    val newStr = records[position].num
        .divide(str, 10, BigDecimal.ROUND_HALF_UP)
    val add = newStr.toInt()
    viewHoder.simpleProgressbar!!.progress = add

    viewHoder.itemView.setOnClickListener {
      onItemClick(position)
    }
  }
}
