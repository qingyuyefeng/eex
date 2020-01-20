package com.eex.mvp.usercenter.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.login.Login
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_login.*

/**
 * @Description: login
 * @Author: xq
 * @CreateDate: 2019/11/25 11:13
 */
class LoginActivity : MVPBaseActivity<Login, LoginContract.View, LoginPresenter>(), LoginContract.View {

    private var codeDialog: AlertDialog? = null
    private var tv_typePhone: TextView? = null
    private var et_code: EditText? = null
    private var btn_getCode: Button? = null
    private var btn_login: Button? = null

    override val layoutId: Int
        get() = R.layout.re_activity_login

    override fun loginSuccess(login: Login) {
        if (login.level == 1) {
            showDialog(login)
        } else {
            showToast(login.msg)
            //存储User数据
            presenter.saveMMKV(login)
            codeDialog?.dismiss()
            Navigator.toMainActivity(this@LoginActivity)
        }
    }

    override fun getCodeSuccess(login: Login) {
        showToast(login.msg)
        presenter.openTimer(this@LoginActivity, btn_getCode!!, resources.getString(R.string.regain_code))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        login_back.setOnClickListener {
            CommonUtil.hideKeyboard(this)
            Navigator.toMainActivity(this@LoginActivity)
        }
        et_login_username.setText(presenter.inputNumber)
        if (et_login_username.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(et_login_username.text.toString().trim { it <= ' ' })) {
            ll_Phone.visibility = View.VISIBLE
        } else {
            ll_Phone.visibility = View.GONE
        }

        ll_Phone.setOnClickListener {
            Navigator.toPhoneListActivity(this@LoginActivity, 2000)

        }

        et_login_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_login_username.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(et_login_username.text.toString().trim { it <= ' ' })) {
                    ll_Phone.visibility = View.VISIBLE
                } else {
                    ll_Phone.visibility = View.GONE
                }
            }

        })

        cb_login_password.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                et_login_password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                et_login_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }
        btn_login_first.setOnClickListener {
            var userName = et_login_username.text.toString().trim { it <= ' ' }
            //判断手机号码
            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(this, R.string.please_input_account, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var passWord = et_login_password.text.toString().trim { it <= ' ' }
            //判断密码
            if (TextUtils.isEmpty(passWord)) {
                Toast.makeText(this, R.string.please_input_password, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val input = userName
            if (!CommonUtil.isEmail(userName)) {
                //电话
                userName = login_call_numbe.text.toString().trim { it <= ' ' } + " " + userName
            }
            //密码
            passWord = Utils.md5(passWord + "hello, moto")

            presenter.loginFirst(userName, passWord, input)
        }

        tv_forget_password.setOnClickListener {
            Navigator.toRetrievePasswordActivity(this@LoginActivity)
        }
        ll_register.setOnClickListener {
            Navigator.toRegisterActivity(this@LoginActivity)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000) {
            if (data != null) {
                val name = data.getStringExtra("name")
                val code = data.getStringExtra("code")
                logiin_chaina.text = name
                login_call_numbe.text = code
            }
        }
    }

    private fun showDialog(login: Login) {
        if (codeDialog != null) {
            codeDialog?.show()
            return
        }
        val popView = View.inflate(this@LoginActivity, R.layout.re_popupwindow_time, null)

        codeDialog = AlertDialog.Builder(this)
                .setView(popView)
                .setCancelable(true)
                .create()

        tv_typePhone = popView.findViewById(R.id.txvew_phone)
        et_code = popView.findViewById(R.id.edt_YZM)
        btn_getCode = popView.findViewById(R.id.btn_YZM)
        btn_login = popView.findViewById(R.id.button)

        when {
            login.phone != "" -> {
                login.loginType = "phone"
                tv_typePhone!!.text = resources.getString(R.string.phone)
                btn_getCode!!.visibility = View.VISIBLE
            }
            login.googleState == "1" -> {
                login.loginType = "google"
                tv_typePhone!!.text = resources.getString(R.string.google)
                btn_getCode!!.visibility = View.GONE
            }
            else -> {
                login.loginType = "email"
                tv_typePhone!!.text = resources.getString(R.string.email)
                btn_getCode!!.visibility = View.VISIBLE
            }
        }

        //获取验证码
        btn_getCode!!.setOnClickListener {
            if (login.loginType == "phone") {
                presenter.getPCode(login.phone, login.userName)
            } else if (login.loginType == "email") {
                presenter.getECode(login.email, login.userName)
            }
        }

        //登录
        btn_login!!.setOnClickListener(View.OnClickListener {
            val code = et_code!!.text.toString().trim { it <= ' ' }
            //判断验证码是否为null
            if (TextUtils.isEmpty(code)) {
                showToast(R.string.please_input_code)
                return@OnClickListener
            }
            CommonUtil.hideKeyboard(this@LoginActivity)
            //checkType ==1 手机号码登陆
            //checkType ==2 google邮箱登陆
            //checkType ==2 其他邮箱登陆
            when (login.loginType) {
                "phone" -> {
                    //checkType, phoneoremail, googleKey, code, username, password
                    presenter.loginSencondPE("1", login.phone, code, presenter.userName, presenter.passWord)
                }
                "google" -> {
                    presenter.loginSencondGO("2", login.googleKey, code, presenter.userName, presenter.passWord)
                }
                "email" -> {
                    presenter.loginSencondPE("3", login.email, code, presenter.userName, presenter.passWord)
                }
            }
        })
        val lp = window.attributes
        lp.alpha = 1.0f
        window.attributes = lp
        codeDialog?.show()
        backgroundAlpha(0.5f)
        codeDialog?.setOnDismissListener {
            backgroundAlpha(1f)
        }
    }

    private fun backgroundAlpha(alpha: Float) {
        val l = this.window.attributes
        l.alpha = alpha
        window.attributes = l
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CommonUtil.hideKeyboard(this)
            Navigator.toMainActivity(this@LoginActivity)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}