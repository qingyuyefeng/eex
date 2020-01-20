package com.eex.mvp.mine.security

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.eex.R
import com.eex.R.style
import com.eex.common.util.StringUtil
import com.eex.domin.entity.security.Security
import com.eex.home.weight.MyDialog
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_setting.*


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class SecurityActivity : MVPBaseActivity<Security, SecurityContract.View, SecurityPresenter>(),
        SecurityContract.View {

    override val layoutId: Int
        get() = R.layout.re_activity_setting

    override fun getTypeSuccess(security: Security) {
        if (TextUtils.isEmpty(security.tradingType)) {
            tv_if_set_method.text = getString(R.string.not_set)
            tv_if_set_method.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
        } else {
            tv_if_set_method.text = getString(R.string.mine_setting)
            tv_if_set_method.setTextColor(ContextCompat.getColor(this, R.color.color_cfd6e6))
        }
    }

    override fun getNickSuccess(security: Security) {
        if (TextUtils.isEmpty(security.tradingNick)) {
            tv_if_set_trading_nickname.text = getString(R.string.not_set)
            tv_if_set_trading_nickname.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
        } else {
            tv_if_set_trading_nickname.text = getString(R.string.mine_setting)
            tv_if_set_trading_nickname.setTextColor(ContextCompat.getColor(this, R.color.color_cfd6e6))
            tv_show_trading_nickname.text = security.tradingNick
        }
    }

    override fun refreshPage(security: Security) {
        when (security.level) {
            0 -> {
                tv_show_level.text = getString(R.string.danger)
                v_level1.setBackgroundResource(R.drawable.security_level_none)
                v_level2.setBackgroundResource(R.drawable.security_level_none)
                v_level3.setBackgroundResource(R.drawable.security_level_none)
                if (security.authStatus != 1) {
                    showRealNameDialog()
                }
            }
            1 -> {
                tv_show_level.text = getString(R.string.low)
                v_level1.setBackgroundResource(R.drawable.security_level_low)
                v_level2.setBackgroundResource(R.drawable.security_level_none)
                v_level3.setBackgroundResource(R.drawable.security_level_none)
            }
            2 -> {
                tv_show_level.text = getString(R.string.medium)
                v_level1.setBackgroundResource(R.drawable.security_level_medium)
                v_level2.setBackgroundResource(R.drawable.security_level_medium)
                v_level3.setBackgroundResource(R.drawable.security_level_none)
                if (security.authStatus != 0 && security.authStatus != 1) {
                    showRealNameDialog()
                }
            }
            3 -> {
                tv_show_level.text = getString(R.string.safe)
                v_level1.setBackgroundResource(R.drawable.security_level_high)
                v_level2.setBackgroundResource(R.drawable.security_level_high)
                v_level3.setBackgroundResource(R.drawable.security_level_high)
            }
        }
    }

    private fun initView() {

        if (TextUtils.isEmpty(presenter.phone)) {
            tv_if_bind_phone.text = getString(R.string.not_bind)
            tv_if_bind_phone.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
        } else {
            tv_show_phone.text = StringUtil.gettextView(presenter.phone)
            tv_if_bind_phone.text = getString(R.string.unbind)
            tv_if_bind_phone.setTextColor(ContextCompat.getColor(this, R.color.color_cfd6e6))
        }
        if (TextUtils.isEmpty(presenter.email)) {
            tv_if_bind_email.text = getString(R.string.not_bind)
            tv_if_bind_email.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
        } else {
            tv_show_email.text = presenter.email.replace("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)".toRegex(), "$1****$3$4")
            tv_if_bind_email.text = getString(R.string.unbind)
            tv_if_bind_email.setTextColor(ContextCompat.getColor(this, R.color.color_cfd6e6))
        }

        if (!presenter.hasGoogleCertify) {
            tv_if_google_verification.text = getString(R.string.not_bind)
            tv_if_google_verification.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
        } else {
            tv_if_google_verification.text = getString(R.string.unbind)
            tv_if_google_verification.setTextColor(ContextCompat.getColor(this, R.color.color_cfd6e6))
        }

        if (!presenter.hasSetTradePassword) {
            tv_if_set_tradingpwd.text = getString(R.string.not_set)
            tv_if_set_tradingpwd.setTextColor(ContextCompat.getColor(this, R.color.color_cc3333))
        } else {
            tv_if_set_tradingpwd.text = getString(R.string.mine_setting)
            tv_if_set_tradingpwd.setTextColor(ContextCompat.getColor(this, R.color.color_cfd6e6))
        }

        ll_bind_phone.setOnClickListener { Navigator.toPhoneOrEmailActivity(this@SecurityActivity, extra = 1) }
        ll_bind_email.setOnClickListener { Navigator.toPhoneOrEmailActivity(this@SecurityActivity, extra = 2) }
        ll_login_password.setOnClickListener { Navigator.toNewPasswordActivity(this) }
        ll_google_verification.setOnClickListener {
            if (!presenter.hasGoogleCertify) {
                Navigator.toGooleActivity(this)
            } else {
                Navigator.toUnbindGoogleActivity(this)
            }
        }
        ll_collection_method.setOnClickListener {
            //法币收款方式
            Navigator.toLegalMethodActivity(this)
        }
        ll_trading_password.setOnClickListener {
            //法币支付密码
            Navigator.toNewTradingPwdActivity(this, 111)
        }
        ll_trading_nickname.setOnClickListener {
            //法币交易昵称
            Navigator.toTradeNickActivity(this, 111)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.checkAuthStatus()
        presenter.getType()
        presenter.getNick()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }


    private fun showRealNameDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_2cbuysell, null)
        val title = view.findViewById(R.id.tltle) as TextView
        val btnDismiss = view.findViewById(R.id.btn_dimss) as Button
        val btnSell = view.findViewById(R.id.btn_sell) as Button
        val dialog = MyDialog(this, 0, 0, view, style.DialogTheme)
        dialog.setCancelable(true)
        title.text = "请完成实名等级2认证"
        dialog.show()
        btnDismiss.setOnClickListener { dialog.dismiss() }
        btnSell.setOnClickListener {
            Navigator.toRealNameActivity(this)
            dialog.dismiss()
        }
    }

    private fun showBindPhoneDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_2cbuysell, null)
        if (!TextUtils.isEmpty(presenter.phone)) {
            Navigator.toMeMoneyPwordActivity(this)
        } else {
            val dialog = MyDialog(this, 0, 0, view, R.style.DialogTheme)
            val title = view.findViewById(R.id.tltle) as TextView
            val btnDismiss = view.findViewById(R.id.btn_dimss) as Button
            val btnSell = view.findViewById(R.id.btn_sell) as Button
            dialog.setCancelable(true)
            title.text = "请绑定手机号后交易"
            dialog.show()
            btnDismiss.setOnClickListener { dialog.dismiss() }
            btnSell.setOnClickListener {
                Navigator.toPhoneOrEmailActivity(this, extra = 1)
                dialog.dismiss()
            }
        }
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        //收款方式和昵称单独走的接口
        when (resultCode) {
            220 -> presenter.getType()
            222 -> presenter.getNick()  //设置昵称回来
        }

    }
}
