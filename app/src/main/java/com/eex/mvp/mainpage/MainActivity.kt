package com.eex.mvp.mainpage

import android.content.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.RadioButton
import com.eex.BuildConfig
import com.eex.R
import com.eex.constant.Keys
import com.eex.home.activity.WebViewActivity
import com.eex.mvp.MVPBaseActivity
import com.eex.mvp.assrtsjava.fragment.AssetsFragment
import com.eex.mvp.homefrag.HomeFragment
import com.eex.mvp.market.fragment.MarketFragment
import com.eex.mvp.mine.mainfrag.MineFragment
import com.eex.mvp.transaction.TransactionFragment
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.bottom_main_bar.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 17:01
 */
class MainActivity : MVPBaseActivity<MainPage, MainContract.View, MainPresenter>(),
        MainContract.View {

    private var fragments: MutableList<Fragment> = ArrayList()

    private var lastIndex = 0

    private var mDialog: AlertDialog? = null

    override fun getVersionSuccess(mainPage: MainPage) {
        if (mainPage.bean.isBuildHaveNewVersion && mainPage.bean.buildVersion > BuildConfig.VERSION_NAME) {
            mDialog = AlertDialog.Builder(this)
                    .setTitle(mainPage.bean.buildVersion + "版本更新")
                    .setMessage(mainPage.bean.versionUpdateContent)
                    .setPositiveButton("立即更新") { dialog, which ->
                        intent.putExtra("url", mainPage.bean.buildShortcutUrl)
                        intent.setClass(this, WebViewActivity::class.java)
                        startActivity(intent)
                    }
                    .setCancelable(mainPage.bean.updata == 1)
                    .show()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(
                context: Context?,
                intent: Intent?
        ) {
            if (intent!!.getBooleanExtra(Keys.CHANGEPASSWORD, false)) {
                radio_group.check(R.id.home_rb)
            }
        }
    }

    override val layoutId: Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
        val filter = IntentFilter(Keys.CHANGEPASSWORD)
        registerReceiver(receiver, filter)
    }

    private fun initView() {
        fragments.add(HomeFragment())
        fragments.add(MarketFragment())
        fragments.add(TransactionFragment())
        fragments.add(AssetsFragment())
        fragments.add(MineFragment())

        switchFragment(fragments[lastIndex])
        radio_group.setOnCheckedChangeListener { group, checkedId ->
            val index = group.indexOfChild(group.findViewById(checkedId))
            if (index == 3 || index == 4) {
                if (TextUtils.isEmpty(presenter.tokenId)) {
                    showToast(R.string.please_login_first)
                    Navigator.toLoginActivity(this@MainActivity)
                    (radio_group.getChildAt(lastIndex) as RadioButton).isChecked = true
                    return@setOnCheckedChangeListener
                }
            }
            switchFragment(fragments[index])
            lastIndex = index
        }

    }

    override fun selectTab(
            idx: Int,
            params: Bundle?
    ) {
        val target = fragments[idx]
        target.arguments = params
        (radio_group.getChildAt(idx) as RadioButton).isChecked = true
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        for (fragment1 in fragments) {
            if (fragment1.isAdded) {
                fragmentManager.beginTransaction()
                        .hide(fragment1)
                        .commit()
            }
        }
        if (fragment.isAdded) {
            fragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }
    }

    private var time1 = 0L
    override fun onKeyDown(
            keyCode: Int,
            event: KeyEvent?
    ): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val time2 = System.currentTimeMillis()
            if (time2 - time1 > 2000) {
                showToast(R.string.mine_Press)
                time1 = time2
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
        presenter.checkApp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.hasExtra("bundle")) {
            val mainIdx = intent.getBundleExtra("bundle")
                    .getInt(Keys.MAIN_SELECT, 0)
            selectTab(mainIdx, intent?.getBundleExtra("bundle"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}