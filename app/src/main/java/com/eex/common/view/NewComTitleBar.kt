package com.eex.common.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.eex.R
import com.eex.common.util.CommonUtil


class NewComTitleBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    private var titleTv: TextView? = null
    private var rightTv: TextView? = null
    private var backIv: ImageView? = null
    private var rightImg: ImageView? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.simple_title, this)

        backIv = view.findViewById(R.id.iv_back)
        titleTv = view.findViewById(R.id.tv_title)
        rightTv = view.findViewById(R.id.tv_right)
        rightImg = view.findViewById(R.id.iv_right)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.new_title_bar)

        val back_img = typedArray.getResourceId(R.styleable.new_title_bar_iv_back_resource, -1)
        backIv!!.setImageResource(if (back_img != -1) back_img else R.mipmap.back_triangle)

        val title = typedArray.getString(R.styleable.new_title_bar_title_resource)
        titleTv!!.text = title

        val right_img = typedArray.getResourceId(R.styleable.new_title_bar_iv_right, -1)
        rightImg!!.setImageResource(if (right_img != -1) right_img else R.mipmap.down_triangle)

        val right_text = typedArray.getString(R.styleable.new_title_bar_tv_right)
        rightTv!!.text = right_text

        typedArray.recycle()

        backIv!!.setOnClickListener {
            CommonUtil.hideKeyboard(context as Activity)
            context.onBackPressed()
        }
    }

    fun setBackGone() {
        backIv!!.visibility = View.GONE
    }

    fun setTitle(string: String) {
        titleTv!!.text = string
    }

    fun setRightIvShow() {
        rightImg!!.visibility = View.VISIBLE
    }

    fun setRightIvClick(click: OnClickListener) {
        rightImg!!.setOnClickListener(click)
    }

    fun setRightTvShow() {
        rightTv!!.visibility = View.VISIBLE
    }

    fun setRightTvClick(click: OnClickListener) {
        rightTv!!.setOnClickListener(click)
    }


    fun setBackListener(listener: OnClickListener) {
        backIv!!.setOnClickListener(listener)
    }

}
