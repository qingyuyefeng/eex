package com.eex.mvp.homefrag.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.eex.R
import me.bakumon.library.adapter.BulletinAdapter

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/2 20:24
 */
class NoticeAdapter(
        val context: Context,
        val list:ArrayList<String>
): BulletinAdapter<String>(context, list) {
    override fun getView(position: Int): View {
        val view = getRootView(R.layout.notice_simple_item)
        val textView = view.findViewById<View>(R.id.tv_content) as TextView
        textView.text = mData[position]
        return view
    }
}