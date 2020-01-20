package com.eex.mvp.usercenter.setnewpassword

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.retrieve.Retrieve
import com.eex.home.weight.PwdCheckUtil
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_retrieve_password2.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/3 14:43
 */
class SetNewPasswordActivity : MVPBaseActivity<Retrieve, SetNewContract.View, SetNewPresenter>(), SetNewContract.View {

    override val layoutId: Int = R.layout.re_activity_retrieve_password2

    override fun setNewSuccess(retrieve: Retrieve) {
        showToast(retrieve.msg)
        Navigator.toLoginActivity(this@SetNewPasswordActivity)
    }

    private var retrieve: Retrieve? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrieve = intent.getSerializableExtra("retrieve") as Retrieve

        login_back.setOnClickListener {
            CommonUtil.hideKeyboard(this)
            finish()
        }

        cb_pwd1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                et_first_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                et_first_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }
        cb_pwd2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                et_confirm_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                et_confirm_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }
        btn_next.setOnClickListener {
            val password = et_first_pwd.text.toString().trim { it <= ' ' }
            val surePassword = et_confirm_pwd.text.toString().trim { it <= ' ' }

            //判断密码是否为null
            if (TextUtils.isEmpty(password)) {
                showToast(R.string.please_input_need_password)
                return@setOnClickListener
            }

            if (password.length < 6 || password.length > 30) {
                showToast(R.string.Please_enter2)
                return@setOnClickListener
            }

            if (!PwdCheckUtil.isLetterDigit(surePassword)) {
                showToast(R.string.Please_enter1)
                return@setOnClickListener
            }

            if (surePassword.length < 6 || surePassword.length > 30) {
                showToast(R.string.Please_enter2)
                return@setOnClickListener
            }


            //判断输入密码和输入的确认密码一样
            if (password != surePassword) {
                showToast(R.string.two_passwords_not_same)
                return@setOnClickListener
            }
            val newPwd = Utils.md5(password + "hello, moto")
            if (retrieve != null) {
                when (retrieve!!.type) {
                    1 -> presenter.resetPassword(newPwd, "3", retrieve!!.username, retrieve!!.code)
                    2 -> presenter.resetPassword(newPwd, "1", retrieve!!.username, retrieve!!.code)
                }
            }
        }
    }

}