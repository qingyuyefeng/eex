package com.eex.mvp.usercenter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.eex.R
import com.eex.common.util.PhoneList
import com.eex.common.view.EmptyView
import com.eex.home.adapter.PhoneListAdapter
import com.eex.home.bean.PhoneListBean
import com.eex.mvp.NoNetBaseActivity
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_phone_list.*
import org.json.JSONArray
import org.json.JSONException

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 17:39
 */
class PhoneListActivity : NoNetBaseActivity() {

    private var adapter: PhoneListAdapter? = null
    private var list: ArrayList<PhoneListBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_list)

        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .keyboardEnable(true)
                .init()
        initView()

    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        list = ArrayList()
        try {
            val phone = JSONArray(PhoneList.phone)
            for (i in 0 until phone.length()) {
                val jsonObject2 = phone.getJSONObject(i)
                val phoneListBean = PhoneListBean()
                phoneListBean.name = jsonObject2.getString("name")
                phoneListBean.code = jsonObject2.getString("code")
                list!!.add(phoneListBean)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        adapter = PhoneListAdapter(list!!)
        recyclerView.adapter = adapter
        adapter?.emptyView = EmptyView(this)
        adapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener {
            adapter, view, position ->
            val intent = Intent()
            intent.putExtra("name", list!![position].name)
            intent.putExtra("code", list!![position].code)
            setResult(1000, intent)
            finish()
        }

    }
}