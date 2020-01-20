package com.eex.mvp.mine.security.legalmethod.bankcard

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_bankcard_setting.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 9:39
 */
class BankCardSettingActivity : MVPBaseActivity<LegalMethod, BankCardContract.View, BankCardPresenter>(), BankCardContract.View {

    private var id = ""

    override val layoutId: Int
        get() = R.layout.re_activity_bankcard_setting

    override fun getPhoneCodeSuccess(legalMethod: LegalMethod) {
        presenter.openTimer(this, btn_get_code, getString(R.string.regain_code))
    }

    override fun getEmailCodeSuccess(legalMethod: LegalMethod) {
        presenter.openTimer(this, btn_get_code, getString(R.string.regain_code))
    }

    override fun setBankCardSuccess(legalMethod: LegalMethod) {
        showToast(legalMethod.msg)
        CommonUtil.hideKeyboard(this)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getStringExtra("id")

        tv_bank_name.setOnClickListener {
            Navigator.toBankListActivity(this, 3000)
        }

        btn_get_code.setOnClickListener {
            if (presenter.phone.isNotEmpty()) {
                presenter.getPhoneCode()
            } else {
                presenter.getEmailCode()
            }
        }

        btn_save.setOnClickListener {
            val name = et_given_name.text.toString().trim()
            val bankName = tv_bank_name.text.toString()
            val bankChildName = et_branch_bank.text.toString().trim()
            val cardNo = et_card_number.text.toString().trim()
            val bankPlace = et_bank_place.text.toString().trim()
            val code = et_code.text.toString().trim()
            if (name.isEmpty() || bankName.isEmpty() || bankChildName.isEmpty() ||
                    cardNo.isEmpty() || bankPlace.isEmpty() || code.isEmpty()) {
                showToast(R.string.please_enter_all_message)
                return@setOnClickListener
            }
            presenter.setBankCard(name, id, code, cardNo, bankName, bankChildName, bankPlace)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == 2000) {
                tv_bank_name.text = data.getStringExtra("textview")
                tv_bank_name.setTextColor(ContextCompat.getColor(this, R.color.color_4d6599))
            }
        }
    }
}