package com.eex.popup

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.link184.kidadapter.setUp
import com.eex.R
import com.eex.domin.entity.attorney.DealType
import kotlinx.android.synthetic.main.item_trade_type.view.item_type
import kotlinx.android.synthetic.main.item_trade_type.view.op_container

class DealTypeWindow(
  val context: Context,
  val types: List<DealType>,
  val currentType: DealType,
  val onItemClick: (DealType) -> Unit
) : PopupWindow() {
  private var type_container: RelativeLayout
  private var list_type: RecyclerView

  val menuView: View = LayoutInflater.from(context)
      .inflate(R.layout.popup_trade_type, null)

  init {
    type_container = menuView.findViewById<View>(R.id.type_container) as RelativeLayout

    list_type = menuView.findViewById(R.id.list_type)
    this.contentView = menuView
    // 设置SelectPicPopupWindow弹出窗体的宽
//    this.width = PixValue.dip.valueOf(60f)
    this.width = RelativeLayout.LayoutParams.WRAP_CONTENT
    // 设置SelectPicPopupWindow弹出窗体的高
    this.height = RelativeLayout.LayoutParams.WRAP_CONTENT
//    this.height = PixValue.dip.valueOf(60f)
    // 设置SelectPicPopupWindow弹出窗体可点击
    this.isFocusable = true
    this.isOutsideTouchable = true
    // 实例化一个ColorDrawable颜色为半透明
    val dw = ColorDrawable(-0x50000000)
    this.setBackgroundDrawable(dw)
    initOps()
  }

  fun setBackGround(@DrawableRes background: Int) {
    type_container.setBackgroundResource(background)
  }

  private fun initOps() {
    list_type.setUp<DealType> {
      withLayoutManager {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      }
      withLayoutResId(R.layout.item_trade_type)
      withItems(types.toMutableList())
      bind { type ->
        item_type.text = type.desc
        if (type == currentType) {
          item_type.setTextColor(ResourcesCompat.getColor(resources, R.color.color_0080ff, null))
          op_container.setBackgroundColor(
              ResourcesCompat.getColor(resources, R.color.color_f2f4f9, null)
          )
        } else {
          item_type.setTextColor(ResourcesCompat.getColor(resources, R.color.color_4d6599, null))
          op_container.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
        }
        setOnClickListener {
          onItemClick(type)
          dismiss()
        }
      }
    }
  }

}