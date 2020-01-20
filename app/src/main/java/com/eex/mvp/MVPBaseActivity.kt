package com.eex.mvp

import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.gyf.barlibrary.ImmersionBar
import com.eex.ActivityStack
import com.eex.EEXApp
import com.eex.R
import com.eex.common.base.UserConstants
import com.eex.constant.Constants
import com.eex.dialogs.LoadingDialog
import com.eex.extensions.toast
import com.eex.navigation.Navigator
import com.tencent.mmkv.MMKV
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

abstract class MVPBaseActivity<VO, V : BaseView, T : BasePresenterImpl<V, VO>> : AppCompatActivity(),
        BaseView,
        HasFragmentInjector,
        HasSupportFragmentInjector {
    @Inject
    lateinit var presenter: T
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>
    @Inject
    lateinit var activityStack: ActivityStack
    @Inject
    lateinit var mmkv: MMKV

    var mLoadingDialog: LoadingDialog? = null
    var toolBar: Toolbar? = null

    @get:LayoutRes
    abstract val layoutId: Int

    val context: Context
        get() = this

    lateinit var baseView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.initPageState(intent.extras)
        presenter.attachView(this as V)
        baseView = LayoutInflater.from(context).inflate(layoutId, null)
        setContentView(baseView)
        activityStack!!.addActivity(this)
        lifecycle.addObserver(presenter)
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

    fun setupToolbar(toolBar: Toolbar) {
        this.toolBar = toolBar
        setSupportActionBar(toolBar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    override fun setTitle(@StringRes resId: Int) {
        toolBar?.findViewById<TextView>(R.id.tv_title)
                ?.setText(resId)
    }

    override fun setTitle(title: CharSequence) {
        toolBar?.findViewById<TextView>(R.id.tv_title)
                ?.text = title
    }

    override fun setTitleColor(@ColorRes resId: Int) {
        toolBar?.findViewById<TextView>(R.id.tv_title)
                ?.setTextColor(ResourcesCompat.getColor(resources,resId,null))
    }

    fun setNavigationIcon(@DrawableRes resId: Int, onClick: () -> Unit) {
        toolBar?.setNavigationIcon(resId)
        toolBar?.setNavigationOnClickListener { onClick() }
    }

    fun setRightMenu(@StringRes resId: Int, onClick: () -> Unit) {
        toolBar?.findViewById<TextView>(R.id.tv_right)
                ?.setText(resId)
        toolBar?.findViewById<TextView>(R.id.tv_right)
                ?.setOnClickListener {
                    onClick()
                }
    }

    fun setRightMenu(
            right: CharSequence,
            onClick: () -> Unit
    ) {
        toolBar?.findViewById<TextView>(R.id.tv_right)
                ?.text = right
        toolBar?.findViewById<TextView>(R.id.tv_right)
                ?.setOnClickListener {
                    onClick()
                }
    }

    fun setRightMenuColor(@ColorRes resId: Int) {
        toolBar?.findViewById<TextView>(R.id.tv_right)
                ?.setTextColor(ResourcesCompat.getColor(resources,resId,null))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            close()
        }
        return super.onOptionsItemSelected(item)
    }


    open fun close() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        activityStack!!.popActivity(this)
    }

    override fun showToast(msg: String) {
        toast { msg }
    }

    override fun showToast(resId: Int) {
        toast(resId)
    }

    override fun showProgress() {
        if (mLoadingDialog == null) {
            try {
                mLoadingDialog = LoadingDialog(this)
                mLoadingDialog!!.show()
            } catch (e: Exception) {
            }

        } else {
            mLoadingDialog!!.show()
        }
    }

    override fun dissmissProgress() {
        try {
            if (mLoadingDialog != null) {
                mLoadingDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showRefreshing() {

    }

    override fun hideRefreshing() {

    }

    override fun showLoadMore() {

    }

    override fun hideLoadMore() {

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return supportFragmentInjector
    }

    fun logout() {
        //tokenId失效时强制退出登录
        mmkv.clear() //保存一些基本设置，上次登录的输入名字
        EEXApp.finishAllActivity()
        Navigator.toLoginActivity(this)
    }
}
