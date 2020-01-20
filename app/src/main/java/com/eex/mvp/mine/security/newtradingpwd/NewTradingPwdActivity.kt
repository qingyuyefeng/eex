package com.eex.mvp.mine.security.newtradingpwd

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.trading.Trading
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.re_activity_new_trading_pwd.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 11:41
 */
class NewTradingPwdActivity : MVPBaseActivity<Trading, NewTradingPwdContract.View, NewTradingPwdPresenter>(), NewTradingPwdContract.View {

    private var alert: AlertDialog? = null

    private var btn_getCode:Button? = null

    override val layoutId: Int
        get() = R.layout.re_activity_new_trading_pwd

    override fun getphoneCodeSuccess(trading: Trading) {
        showToast(trading.msg)
        presenter.openTimer(this,btn_getCode!!,getString(R.string.regain_code))
    }

    override fun setPwdSuccess(trading: Trading) {
        showToast(trading.msg)
        alert!!.dismiss()
        presenter.saveTradingPwd()
        CommonUtil.hideKeyboard(this)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cb_pwd1.setOnCheckedChangeListener { buttonView, isChecked ->
            et_new_pwd1.transformationMethod = if (isChecked) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        }
        cb_pwd2.setOnCheckedChangeListener { buttonView, isChecked ->
            et_new_pwd2.transformationMethod = if (isChecked) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        }

        btn_ok.setOnClickListener {
            val newPwd1 = et_new_pwd1.text.toString().trim()

            if (TextUtils.isEmpty(newPwd1) || newPwd1.length < 6) {
                showToast(R.string.please_enter_6_30_trading_pwd)
                return@setOnClickListener
            }
            val newPwd2 = et_new_pwd2.text.toString().trim()

            if (TextUtils.isEmpty(newPwd2) || newPwd2.length < 6) {
                showToast(R.string.please_enter_6_30_trading_pwd)
                return@setOnClickListener
            }

            if (newPwd1 != newPwd2) {
                showToast(R.string.two_passwords_not_same)
                return@setOnClickListener
            }

            showAlert(newPwd1)
        }
    }

    private fun showAlert(newPwd: String) {
        if (alert == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.re_popupwindow_time, null)

            val tv_typePhone = view.findViewById<TextView>(R.id.txvew_phone)
            val et_code = view.findViewById<EditText>(R.id.edt_YZM)
            btn_getCode = view.findViewById<Button>(R.id.btn_YZM)
            val btn_ok = view.findViewById<Button>(R.id.button)


            tv_typePhone.text = getString(R.string.phone)

            btn_getCode!!.setOnClickListener {
                presenter.getPhoneCode(presenter.phone)
            }

            btn_ok.setOnClickListener {
                val code = et_code.text.toString().trim()
                if (code.isEmpty()) {
                    showToast(R.string.please_input_code)
                    return@setOnClickListener
                }
                presenter.undateAccount(Utils.md5(newPwd + "hello, moto") , code)
            }

            alert = AlertDialog.Builder(this)
                    .setView(view)
                    .show()

        }else{
            alert?.show()
        }
    }

}