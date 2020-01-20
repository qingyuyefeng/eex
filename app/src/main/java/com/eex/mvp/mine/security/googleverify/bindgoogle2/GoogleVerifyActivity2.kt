package com.eex.mvp.mine.security.googleverify.bindgoogle2

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.google.Google
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_goole2.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 9:21
 */
class GoogleVerifyActivity2 : MVPBaseActivity<Google, GoogleContract2.View, GooglePresenter2>(), GoogleContract2.View {
    override val layoutId: Int
        get() = R.layout.re_activity_goole2

    override fun checkCodeSuccess(google: Google) {
        showToast(google.msg)
        Navigator.toSecurityActivity(this@GoogleVerifyActivity2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_code1.requestFocus()

        val secret = intent.getStringExtra("secretKey")

        val tvs = arrayOf(tv_code1, tv_code2, tv_code3, tv_code4, tv_code5, tv_code6)
        for (i in tvs.indices) {
            tvs[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s!!.isNotEmpty()) {
                        if (i < 5) {
                            tvs[i + 1].requestFocus()
                        } else {
                            tvs[i].clearFocus()
                            CommonUtil.hideKeyboard(this@GoogleVerifyActivity2)
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
        }

        btn_next_step.setOnClickListener {
            var code = ""
            tvs.forEach {
                code += it.text.toString()
            }
            if (code.length < 5){
                showToast(R.string.please_input_code)
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(secret)){
                return@setOnClickListener
            }
            presenter.checkCode(code,secret)
        }
    }

}