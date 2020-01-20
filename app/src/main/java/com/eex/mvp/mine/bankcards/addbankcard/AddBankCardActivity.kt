package com.eex.mvp.mine.bankcards.addbankcard

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.eex.R
import com.eex.domin.entity.bankcards.BankCards
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_add_bank.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 18:02
 */
class AddBankCardActivity : MVPBaseActivity<BankCards, AddBankContract.View, AddBankPresenter>(), AddBankContract.View {

    override val layoutId = R.layout.re_activity_add_bank

    override fun addBankSuccess(bankCards: BankCards) {
        showToast(bankCards.msg)
        setResult(111)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_bank_name.setOnClickListener {
            Navigator.toBankListActivity(this@AddBankCardActivity, 3000)
        }

        btn_add_card.setOnClickListener {
            val surname = et_family_name.text.toString().trim()
            val givename = et_given_name.text.toString().trim()
            val bankName = tv_bank_name.text.toString()
            val bankCardNo = et_card_number.text.toString().trim()
            val bankChildName = et_branch_bank.text.toString().trim()
            val bankAddress = et_bank_place.text.toString().trim()

            if (surname.isEmpty() || givename.isEmpty() || bankName.isEmpty()
                    || bankCardNo.isEmpty() || bankChildName.isEmpty() || bankAddress.isEmpty()) {
                showToast(R.string.please_enter_all_message)
                return@setOnClickListener
            }

            presenter.addBank(bankName, bankAddress, bankChildName, givename, surname, bankCardNo)
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