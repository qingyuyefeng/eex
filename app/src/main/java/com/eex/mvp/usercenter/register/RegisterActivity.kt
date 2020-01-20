package com.eex.mvp.usercenter.register

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.register.Register
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_register.*

/**
 * @Description:注册（手机注册和邮箱注册综合）
 * @Author: xq
 * @CreateDate: 2019/11/28 16:45
 */
class RegisterActivity : MVPBaseActivity<Register, RegisterContract.View, RegisterPresenter>(), RegisterContract.View {

    override val layoutId: Int = R.layout.re_activity_register

    private var type = 1 //1手机注册 2邮箱
    private var register: Register? = null

    override fun getImageCodeSuccess(register: Register) {
        this.register = register
        //将Base64编码字符串解码成Bitmap
        val decodedString = Base64.decode(register.imageCode.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1], Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        //设置ImageView图片
        graphics_image.setImageBitmap(decodedByte)
    }

    override fun getCodeSuccess(register: Register) {
        showToast(register.msg)
        presenter.openTimer(this, btn_YZM, getString(R.string.regain_code))
    }

    override fun registerSuccess(register: Register) {
        showToast(register.msg)
        Navigator.toLoginActivity(this@RegisterActivity)
//        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        register_back.setOnClickListener {
            CommonUtil.hideKeyboard(this)
            finish()
        }

        presenter.getImageCode()
        graphics_linear.visibility = if (type == 1) View.VISIBLE else View.GONE

        ll_Phone.setOnClickListener {
            Navigator.toPhoneListActivity(this@RegisterActivity,2000)
        }

        login_username_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (type == 1) {
                    if (login_username_edit.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(login_username_edit.text.toString().trim { it <= ' ' })) {
                        ll_Phone.visibility = View.VISIBLE
                    } else {
                        ll_Phone.visibility = View.GONE
                    }
                }
            }

        })

        btn_YZM.setOnClickListener {
            val input = login_username_edit.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(input)) {
                showToast(if (type == 1) R.string.please_input_phone else R.string.please_input_email)
                return@setOnClickListener
            }
            when (type) {
                1 -> {
                    if (register == null) {
                        showToast(R.string.getting_image_code_error)
                        return@setOnClickListener
                    }
                    val imageCode = graphics_YZM_edit.text.toString().trim { it <= ' ' }
                    if (TextUtils.isEmpty(imageCode)) {
                        showToast(R.string.please_input_image_code)
                        return@setOnClickListener
                    }
                    presenter.getPCode(tx_name_nuber.text.toString() + " " + input, imageCode, register!!.imageKey)
                }
                else -> {
                    presenter.getECode(input)
                }
            }
        }
        sever_textView.setOnClickListener {
            //跳转查看用户协议界面
            Navigator.toSeverActivity(this@RegisterActivity)
        }

        login_login_paword.setOnClickListener {
            //获取注册账号
            val input = login_username_edit.text.toString().trim { it <= ' ' }
            //密码
            val password = login_userpaswod_edit.text.toString().trim { it <= ' ' }
            //确认密码
            val surePassword = new_login_userpaswod_edit.text.toString().trim { it <= ' ' }
            //验证码
            val verifyCode = login_userYZM_edit.text.toString().trim { it <= ' ' }
            //推荐码
            val referralCode = login_userTJ_edit.text.toString().trim { it <= ' ' }
            //判断手机号码是否为null
            if (TextUtils.isEmpty(input)) {
                showToast(if (type == 1) R.string.please_input_phone else R.string.please_input_email)
                return@setOnClickListener
            }
            //判断密码是否为null
            if (TextUtils.isEmpty(password) || password.length < 6) {
                showToast(R.string.please_input_need_password)
                return@setOnClickListener
            }
            //判断确认密码是否为null
            if (TextUtils.isEmpty(surePassword)) {
                showToast(R.string.please_input_confirm_password)
                return@setOnClickListener
            }
            //判断短信验证码是否为null
            if (TextUtils.isEmpty(verifyCode)) {
                showToast(R.string.login_yzm)
                return@setOnClickListener
            }

            //是否同意了服务协议
            if (!ck_register.isChecked) {
                showToast(R.string.please_check_agreement)
                return@setOnClickListener
            }

            //判断输入密码和输入的确认密码一样
            if (password != surePassword) {
                showToast(R.string.two_passwords_not_same)
                return@setOnClickListener
            }

            when (type) {
                1 -> presenter.register(tx_name_nuber.text.toString() + " " + input, Utils.md5(password + "hello, moto"), verifyCode, referralCode)
                2 -> presenter.register(input, Utils.md5(password + "hello, moto"), verifyCode, referralCode)
            }

        }

        putUser.setOnClickListener {
            if (type == 1) {
                type = 2
                login_username_edit.hint = getString(R.string.please_input_email)
                putUser.text = getString(R.string.phone_register)
                graphics_linear.visibility = View.GONE
                ll_Phone.visibility = View.GONE
            } else {
                type = 1
                login_username_edit.hint = getString(R.string.please_input_phone)
                putUser.text = getString(R.string.email_register)
                graphics_linear.visibility = View.VISIBLE
                if (login_username_edit.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(login_username_edit.text.toString().trim { it <= ' ' })) {
                    ll_Phone.visibility = View.VISIBLE
                } else {
                    ll_Phone.visibility = View.GONE
                }
            }
        }
        NOpaswd.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000) {
            if (data != null) {
                val name = data.getStringExtra("name")
                val code = data.getStringExtra("code")
                guojia_name.text = name
                tx_name_nuber.text = code
            }
        }
    }
}