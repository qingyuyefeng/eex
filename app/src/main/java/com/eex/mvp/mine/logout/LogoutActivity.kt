package com.eex.mvp.mine.logout

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.eex.BuildConfig
import com.eex.EEXApp
import com.eex.R
import com.eex.common.base.UserConstants
import com.eex.constant.Constants
import com.eex.home.weight.MyDialog
import com.eex.mvp.NoNetBaseActivity
import com.eex.mvp.usercenter.login.LoginActivity
import com.eex.navigation.Navigator
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.re_activity_logout.*
import java.util.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/4 17:20
 */
class LogoutActivity : NoNetBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.re_activity_logout)
        initView()
    }

    private fun initView() {
        tv_version.text = "V${BuildConfig.VERSION_NAME}"


        val curLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }
//        val lang = curLocale.language + "-" + curLocale.country
        when (curLocale) {
            Locale.SIMPLIFIED_CHINESE -> {
                //中文  
                tv_language.text = getString(R.string.simple_chinese)
            }
            Locale.ENGLISH -> {
                //英文  
                tv_language.text = getString(R.string.english)
            }
        }


        btn_OutLogin.setOnClickListener {
            showLogoutDialog()
            //            kv.removeValueForKey(Constants.TOKEN_ID)
//            kv.removeValueForKey(Constants.USERNAME)
//            kv.removeValueForKey(Constants.PASSWORD)
//            kv.removeValueForKey(Constants.TRADE_PASSWORD)
//            kv.removeValueForKey(Constants.USER_STATUS)
//            kv.removeValueForKey(Constants.EMAIL_CERTIFY)
//            kv.removeValueForKey(Constants.REAL_NAME_CERTIFY)
//            kv.removeValueForKey(Constants.SALT)
//            kv.removeValueForKey(Constants.USERCODE)
//            kv.removeValueForKey(Constants.HASEMAIL)
//            kv.removeValueForKey(Constants.HASPHONE)
//            kv.removeValueForKey(Constants.PHONE_CERTIFY)
//            kv.removeValueForKey(Constants.INVATE_CODE)
//            kv.removeValueForKey(Constants.GOOGLE_STATE)
//            kv.removeValueForKey(Constants.EXAM_STATE)
//            kv.removeValueForKey(Constants.GOOGLE_KEY)
//            kv.removeValueForKey(Constants.MERCHANT)

        }
    }
    private fun showLogoutDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_2cbuysell, null)
        val title = view.findViewById(R.id.tltle) as TextView
        val btnDismiss = view.findViewById(R.id.btn_dimss) as Button
        val btnSell = view.findViewById(R.id.btn_sell) as Button
        val dialog = MyDialog(this, 0, 0, view, R.style.DialogTheme)
        dialog.setCancelable(true)
        title.text = getString(R.string.sure_logout)
        dialog.show()
        btnDismiss.setOnClickListener { dialog.dismiss() }
        btnSell.setOnClickListener {
            val kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE)
            kv.clear()
            Navigator.toLogin(this)
        }
    }
}