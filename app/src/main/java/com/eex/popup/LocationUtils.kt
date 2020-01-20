package com.eex.popup

import android.content.Context
import android.view.View
import android.view.WindowManager

object LocationUtils {
  fun isPopUp(
    context: Context,
    locY: Int,
    anchorViewHeight: Int,
    windowHeight: Int
  ): Boolean {
    val screenHeight = getScreenHeight(context)
    return screenHeight - locY - anchorViewHeight < windowHeight
  }

  private fun measureWindowHeight(contentView: View): Int {
    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    return contentView.measuredHeight
  }

  fun measureWindowWidth(contentView: View): Int {
    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    return contentView.measuredWidth
  }

  fun calculatePopWindowPos(
    anchorView: View,
    contentView: View
  ): IntArray {
    val windowPos = IntArray(2)
    val anchorLoc = IntArray(2)
    anchorView.getLocationOnScreen(anchorLoc)
    val anchorHeight = anchorView.height
    // 获取屏幕的高宽
    val screenHeight = getScreenHeight(anchorView.context)
    val screenWidth = getScreenWidth(anchorView.context)
    // 计算contentView的高宽
    val windowHeight = measureWindowHeight(contentView)
    val windowWidth = measureWindowWidth(contentView)
    // 判断需要向上弹出还是向下弹出显示
    if (isPopUp(anchorView.context, anchorLoc[1], anchorHeight, windowHeight)) {
      windowPos[0] = anchorLoc[0]
      windowPos[1] = anchorLoc[1] - 10 * windowHeight / 9
    } else {
      windowPos[0] = anchorLoc[0]
      windowPos[1] = anchorLoc[1] + windowHeight / 5
    }
    return windowPos
  }

  /**
   * 获取屏幕的宽度
   *
   * @param context
   * @return
   */
  fun getScreenWidth(context: Context): Int {
    val manager = context
        .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = manager.defaultDisplay
    return display.width
  }

  /**
   * 获取屏幕的高度
   *
   * @param context
   * @return
   */
  fun getScreenHeight(context: Context): Int {
    val manager = context
        .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = manager.defaultDisplay
    return display.height
  }
}