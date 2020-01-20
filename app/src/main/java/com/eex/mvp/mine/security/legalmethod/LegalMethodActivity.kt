package com.eex.mvp.mine.security.legalmethod

import android.support.v4.content.ContextCompat
import com.eex.R
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.mvp.MVPBaseActivity
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.re_activity_legal_method.*

/**
 * @Description:法币收款方式
 * @Author: xq
 * @CreateDate: 2019/12/19 16:49
 */
class LegalMethodActivity:MVPBaseActivity<LegalMethod,LegalMethodContract.View,LegalMethodPresenter>(),LegalMethodContract.View {

    private var bankId = ""
    private var alipayId = ""
    private var wechatId = ""

    override val layoutId: Int
        get() = R.layout.re_activity_legal_method

    override fun getMethodAccountSuccess(legalMethod: LegalMethod) {
        if(legalMethod.listAccount.isNotEmpty()){
            bankId = legalMethod.listAccount[0].id
            tv_if_bank_card.text = getString(R.string.settings)
            tv_if_bank_card.setTextColor(ContextCompat.getColor(this,R.color.color_cfd6e6))

            legalMethod.listAccount.forEach {
                if(it.accountType == 2){  //有支付宝
                    alipayId = it.id
                    tv_if_alipay.text = getString(R.string.settings)
                    tv_if_alipay.setTextColor(ContextCompat.getColor(this,R.color.color_cfd6e6))
                }else if(it.accountType == 3){  //有微信
                    wechatId = it.id
                    tv_if_we_chat.text = getString(R.string.settings)
                    tv_if_we_chat.setTextColor(ContextCompat.getColor(this,R.color.color_cfd6e6))
                }
            }

        }else{
            tv_if_bank_card.text = getString(R.string.not_set)
            tv_if_bank_card.setTextColor(ContextCompat.getColor(this,R.color.color_cc3333))
            tv_if_we_chat.text = getString(R.string.not_set)
            tv_if_we_chat.setTextColor(ContextCompat.getColor(this,R.color.color_cc3333))
            tv_if_alipay.text = getString(R.string.not_set)
            tv_if_alipay.setTextColor(ContextCompat.getColor(this,R.color.color_cc3333))
        }
    }


    override fun onResume() {
        super.onResume()

        presenter.getMethodAccount()

        ll_bank_card.setOnClickListener {
            Navigator.toBankCardSettingActivity(this,extra = bankId)
        }
        ll_we_chat.setOnClickListener {
            Navigator.toAlipayOrWechatActivity(this,extra1 = 2,extra2 = wechatId)
        }
        ll_alipay.setOnClickListener {
            Navigator.toAlipayOrWechatActivity(this,extra1 = 1,extra2 = alipayId) //1支付宝 2微信

        }
    }
}