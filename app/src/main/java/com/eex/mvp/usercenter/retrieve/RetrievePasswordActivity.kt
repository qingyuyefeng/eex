package com.eex.mvp.usercenter.retrieve

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.retrieve.Retrieve
import com.eex.home.weight.Utils
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_retrieve_password1.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 17:32
 */
class RetrievePasswordActivity : MVPBaseActivity<Retrieve, RetrieveContract.View, RetrievePresenter>(), RetrieveContract.View {

    private var typeDialog: AlertDialog? = null
    private var retrieve: Retrieve? = null

    override val layoutId: Int = R.layout.re_activity_retrieve_password1

    override fun getCodeSuccess(retrieve: Retrieve) {
        showToast(retrieve.msg)
        presenter.openTimer(this, btn_get_code, getString(R.string.regain_code))
    }

    override fun nextSuccess(retrieve: Retrieve) {
        Navigator.toSetNewPasswordActivity(this@RetrievePasswordActivity,ser = this.retrieve!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        this.retrieve = Retrieve()

        login_back.setOnClickListener {
            CommonUtil.hideKeyboard(this)
            finish()
        }

        rl_choose_type.setOnClickListener {
            showDialog()
        }

        et_account.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (retrieve!!.type == 2) {
                    if (et_account.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(et_account.text.toString().trim { it <= ' ' })) {
                        ll_country.visibility = View.VISIBLE
                    } else {
                        ll_country.visibility = View.GONE
                    }
                }
            }

        })

        ll_country.setOnClickListener {
            Navigator.toPhoneListActivity(this,2000)
        }

        btn_get_code.setOnClickListener {
            if (retrieve!!.type == 0) {
                showToast(R.string.please_choose_type)
                return@setOnClickListener
            }
            val input = et_account.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(input)) {
                showToast(R.string.please_input_account)
                return@setOnClickListener
            }
            when (retrieve!!.type) {
                1 -> presenter.getEmailCode(input)
                2 -> presenter.getPhoneCode(tx_name_nuber.text.toString() + " " + input)
            }
        }

        btn_next.setOnClickListener {
            if (retrieve!!.type == 0) {
                showToast(R.string.please_choose_type)
                return@setOnClickListener
            }
            val input1 = et_account.text.toString().trim()
            val input2 = et_code.text.toString().trim()
            if (TextUtils.isEmpty(input1)) {
                showToast(R.string.please_input_account)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(input2)) {
                showToast(R.string.please_input_code)
                return@setOnClickListener
            }
            when (retrieve!!.type) {
                1 -> {
                    presenter.checkCode(input1, input2, "3")
                    retrieve = retrieve!!.copy(username = input1, code = input2)
                }
                2 -> {
                    presenter.checkCode(tx_name_nuber.text.toString() + " " + input1, input2, "1")
                    retrieve = retrieve!!.copy(username = tx_name_nuber.text.toString() + " " + input1, code = input2)
                }
            }
        }
    }

    private fun showDialog() {
        if (typeDialog != null) {
            typeDialog?.show()
            return
        }
        val popView = View.inflate(this@RetrievePasswordActivity, R.layout.retrieve_dialog, null)
        typeDialog = AlertDialog.Builder(this)
                .setView(popView)
                .create()

        val ll_Email = popView.findViewById<LinearLayout>(R.id.ll_Email)
        val ll_phone = popView.findViewById<LinearLayout>(R.id.ll_phone)

        ll_Email.setOnClickListener {
            retrieve = retrieve!!.copy(type = 1)
            tv_type_name.text = getString(R.string.email_type)
            tv_type_name.setTextColor(ContextCompat.getColor(this@RetrievePasswordActivity,R.color.main_title2))
            typeDialog?.dismiss()
            ll_country.visibility = View.GONE
        }
        ll_phone.setOnClickListener {
            retrieve = retrieve!!.copy(type = 2)
            tv_type_name.text = getString(R.string.phone_type)
            tv_type_name.setTextColor(ContextCompat.getColor(this@RetrievePasswordActivity,R.color.main_title2))
            typeDialog?.dismiss()
            if (et_account.text.toString().trim { it <= ' ' }.length >= 4 && Utils.isInteger(et_account.text.toString().trim { it <= ' ' })) {
                ll_country.visibility = View.VISIBLE
            } else {
                ll_country.visibility = View.GONE
            }
        }
        typeDialog?.show()
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