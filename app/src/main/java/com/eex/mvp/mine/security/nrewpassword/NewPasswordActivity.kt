package com.eex.mvp.mine.security.nrewpassword

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.eex.R
import com.eex.common.base.Data
import com.eex.constant.Keys
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_new_password.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/13 11:15
 */
class NewPasswordActivity : MVPBaseActivity<Data<Any>,NewPwdContract.View,NewPwdPresenter>(),NewPwdContract.View {

    override val layoutId: Int = R.layout.re_activity_new_password

    override fun setNewPwdSuccess(data: Data<Any>) {
        showToast(data.msg)
        //暂定为发送广播，让主页选中第一个frag
        val intent = Intent()
        intent.putExtra(Keys.CHANGEPASSWORD,true)
        sendBroadcast(intent)
        logout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        cb_pwd1.setOnCheckedChangeListener { buttonView, isChecked ->
            et_old_pwd.transformationMethod = if (isChecked) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        }
        cb_pwd2.setOnCheckedChangeListener { buttonView, isChecked ->
            et_new_pwd.transformationMethod = if (isChecked) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        }
        btn_ok.setOnClickListener {
            val oldPwd = et_old_pwd.text.toString().trim()

            if (TextUtils.isEmpty(oldPwd)) {
                showToast(R.string.please_input_password)
                return@setOnClickListener
            }

//            Utils.md5(password + "hello, moto")
            val newPwd = et_new_pwd.text.toString().trim()

            if (TextUtils.isEmpty(newPwd) || newPwd.length < 6 || isNumber(newPwd)) {
                showToast(R.string.please_input_need_password)
                return@setOnClickListener
            }

            presenter.changePwd(Utils.md5(oldPwd + "hello, moto"),Utils.md5(newPwd + "hello, moto"))

        }

    }

    private fun isNumber(string:String):Boolean{
        string.forEach {
            if(!it.isDigit()){
                return false
            }
        }
        return true
    }
}