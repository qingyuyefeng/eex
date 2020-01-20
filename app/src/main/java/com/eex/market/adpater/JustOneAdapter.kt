package com.eex.market.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.BaseAdapter
import com.eex.R

/**
 * @Description: item——webview
 * @Author: xq
 * @CreateDate: 2019/11/13 10:06
 */
class JustOneAdapter(val context:Context,
                     val list: MutableList<String>):BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_sale_webview,parent,false)
            val webDialog = view.findViewById<WebView>(R.id.webDialog)
            webDialog.loadUrl(list[position])
            webDialog.settings.javaScriptEnabled = true
            webDialog.settings.builtInZoomControls = false
            webDialog.settings.setSupportZoom(false)
            webDialog.settings.displayZoomControls = false
            //水平不显示
            webDialog.isHorizontalScrollBarEnabled = false
            //垂直不显示
            webDialog.isVerticalScrollBarEnabled = false
        }else{
            view = convertView
        }
        return view
    }

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = 1
}