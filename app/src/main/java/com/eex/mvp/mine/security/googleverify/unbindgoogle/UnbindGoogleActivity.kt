package com.eex.mvp.mine.security.googleverify.unbindgoogle

import android.os.Bundle
import com.eex.R
import com.eex.domin.entity.google.Google
import com.eex.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.re_activity_unbind_goole.*
import android.content.ClipboardManager
import android.text.TextUtils


/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 14:00
 */
class UnbindGoogleActivity : MVPBaseActivity<Google, UnbindGoogleContract.View, UnbindGooglePresenter>(), UnbindGoogleContract.View {
    override val layoutId: Int
        get() = R.layout.re_activity_unbind_goole

    override fun unbindSuccess(google: Google) {
        showToast(R.string.unbind_google_success)
        presenter.removeGoogle()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_paste.setOnClickListener {
            val cm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

            if(!TextUtils.isEmpty(cm.text)){
                et_google_code.setText(cm.text)
            }
        }

        btn_unbind_google.setOnClickListener {
            val pwd = et_login_password.text.toString().trim()
            if(TextUtils.isEmpty(pwd)){
                showToast(R.string.please_input_password)
                return@setOnClickListener
            }
            val code = et_google_code.text.toString().trim()
            if(TextUtils.isEmpty(code)){
                showToast(R.string.please_input_code)
                return@setOnClickListener
            }
            presenter.unBind(pwd, code)
        }
    }

}