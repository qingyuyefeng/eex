package com.eex.mvp.mine.security.phoneoremail

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.eex.R
import com.eex.domin.entity.bindphoneoremail.BindPE
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.re_activity_bind_phone_or_email.*
import kotlinx.android.synthetic.main.re_activity_bind_phone_or_email.btn_get_code

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 15:38
 */
class PhoneOrEmailActivity : MVPBaseActivity<BindPE, PhoneOrEmailContract.View, PhoneOrEmailPresenter>(), PhoneOrEmailContract.View {
    override val layoutId: Int
        get() = R.layout.re_activity_bind_phone_or_email

    override fun getCodeSuccess() {
        presenter.openTimer(this@PhoneOrEmailActivity, btn_get_code, getString(R.string.regain_code))
    }

    override fun dealSuccess(bindPE: BindPE) {
        //1、获取验证码  2、绑定手机  3、解绑手机  4、绑定邮箱  5、解绑邮箱
        when (bindPE.level) {
            2 -> {
                showToast(R.string.bind_phone_success)
                presenter.dealData(2,phone)
            }
            3 -> {
                showToast(R.string.unbind_phone_success)
                presenter.dealData(3,phone)
            }
            4 -> {
                showToast(R.string.bind_email_success)
                presenter.dealData(4,email)
            }
            5 -> {
                showToast(R.string.unbind_email_success)
                presenter.dealData(5,email)
            }
        }
        finish()
    }

    private var type: Int = 1
    private var phone = ""
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = intent.getIntExtra("phoneoremail", 1)
        when (type) {
            1 -> {
                title_bar.setTitle(getString(R.string.phone_verification))
                tv_phone_or_email.text = getString(R.string.phone)
                et_phone_or_email.hint = getString(R.string.please_input_phone)
                tv_phone_tips.visibility = View.VISIBLE
                if (presenter.hasPhone) {
                    et_phone_or_email.setText(presenter.phone)
                    et_phone_or_email.isEnabled = false
                    btn_ok.text = getString(R.string.unbind_phone)
                } else {
                    btn_ok.text = getString(R.string.bind_phone)
                }
            }
            2 -> {
                title_bar.setTitle(getString(R.string.email_verification))
                tv_phone_or_email.text = getString(R.string.email)
                et_phone_or_email.hint = getString(R.string.please_input_email)
                tv_phone_tips.visibility = View.GONE
                if (presenter.hasEmail) {
                    et_phone_or_email.setText(presenter.email)
                    et_phone_or_email.isEnabled = false
                    btn_ok.text = getString(R.string.unbind_email)
                } else {
                    btn_ok.text = getString(R.string.bind_email)
                }
            }
        }

        initView()
    }

    private fun initView() {

        et_phone_or_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (type == 1) {
                    if (et_phone_or_email.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(et_phone_or_email.text.toString().trim { it <= ' ' })) {
                        ll_phone_place.visibility = View.VISIBLE
                    } else {
                        ll_phone_place.visibility = View.GONE
                    }
                }
            }

        })

        btn_get_code.setOnClickListener {
            when (type) {
                1 -> {
                    phone = et_phone_or_email.text.toString().trim()
                    val sendType = if (presenter.hasPhone) "sms_cancel_bind_phone" else "sms_bind_phone"
                    if (TextUtils.isEmpty(phone)) {
                        showToast(R.string.please_input_phone)
                        return@setOnClickListener
                    }
                    if (ll_phone_place.visibility == View.VISIBLE) {
                        phone = tv_area_code.text.toString() + " " + phone
                    }
                    presenter.getPhoneCode(phone = phone, sendType = sendType)
                }
                2 -> {
                    email = et_phone_or_email.text.toString().trim()
                    val sendType = if (presenter.hasEmail) "3" else "2"
                    if (TextUtils.isEmpty(email)) {
                        showToast(R.string.please_input_email)
                        return@setOnClickListener
                    }
                    presenter.getEmailCode(email = email, sendType = sendType)
                }
            }
        }
        btn_ok.setOnClickListener {
            when (type) {
                1 -> {
                    phone = et_phone_or_email.text.toString().trim()
                    if (TextUtils.isEmpty(phone)) {
                        showToast(R.string.please_input_phone)
                        return@setOnClickListener
                    }
                    val code = et_code.text.toString().trim()
                    if (TextUtils.isEmpty(code)) {
                        showToast(R.string.please_input_code)
                        return@setOnClickListener
                    }
                    if (ll_phone_place.visibility == View.VISIBLE) {
                        phone = tv_area_code.text.toString() + " " + phone
                    }
                    if (presenter.hasPhone) {
                        presenter.unbindPhone(phone, code)
                    } else {
                        presenter.bindPhone(phone, code)
                    }
                }
                2 -> {
                    email = et_phone_or_email.text.toString().trim()
                    if (TextUtils.isEmpty(email)) {
                        showToast(R.string.please_input_email)
                        return@setOnClickListener
                    }
                    val code = et_code.text.toString().trim()
                    if (TextUtils.isEmpty(code)) {
                        showToast(R.string.please_input_code)
                        return@setOnClickListener
                    }
                    if (presenter.hasEmail) {
                        presenter.unbindEmail(email, code)
                    } else {
                        presenter.bindEmail(email, code)
                    }
                }
            }
        }
    }
}