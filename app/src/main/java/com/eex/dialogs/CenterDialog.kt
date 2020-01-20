package com.eex.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager

open class CenterDialog(cxt: Context) : Dialog(cxt){

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle()
  }

  private fun setStyle() {
    val window = window
    //设置dialog在屏幕底部
    window!!.setGravity(Gravity.CENTER)
    //设置dialog弹出时的动画效果，从屏幕底部向上弹出
    window.decorView.setPadding(0, 0, 0, 0)
    //获得window窗口的属性
    val lp = window.attributes
    //设置窗口宽度为充满全屏
    lp.width = WindowManager.LayoutParams.MATCH_PARENT
    //设置窗口高度为包裹内容
    lp.height = WindowManager.LayoutParams.MATCH_PARENT
    //将设置好的属性set回去
    window.attributes = lp
    setCanceledOnTouchOutside(true)
    setCancelable(true)
  }



}