package com.eex.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eex.EEXApp
import com.eex.R
import com.gyf.barlibrary.ImmersionBar

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/10 14:29
 */
open class NoNetBaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EEXApp.addActivity(this)
        setStatusColor()
    }

    private fun setStatusColor(vararg color: Int) {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(if (color.isEmpty()) R.color.white else color[0])
                .keyboardEnable(true)
                .init()
    }

    override fun onResume() {
        super.onResume()
        Log.e("现在在这里：", this.javaClass.canonicalName)
    }

    override fun onDestroy() {
        super.onDestroy()
        EEXApp.removeActivity(this)
        //必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy()
    }
}