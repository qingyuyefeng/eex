package com.eex.mvp.mine.mainfrag

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.common.util.StringUtil
import com.eex.domin.entity.mine.Mine
import com.eex.mvp.MVPBaseFragment
import com.eex.mvp.assrtsjava.activity.Capitalflow.CapitalFlowActivity
import com.eex.mvp.assrtsjava.activity.CurrencyChoiceActivity
import com.eex.mvp.assrtsjava.activity.Recharge.RechargeActivity
import com.eex.mvp.assrtsjava.activity.TransferActivity
import com.eex.mvp.assrtsjava.activity.withdrawal.CashWithdrawalActivity
import com.eex.mvp.mine.MineContract
import com.eex.navigation.Navigator
import kotlinx.android.synthetic.main.layout_trading.*
import kotlinx.android.synthetic.main.re_frament_mine.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/4 17:21
 */
class MineFragment : MVPBaseFragment<Mine, MineContract.View, MinePresenter>(), MineContract.View {

    override val layoutId: Int = R.layout.re_frament_mine

    override fun getInfoSuccess(mine: Mine) {
        if (mine.merchantStatus == 2) {
            tv_title1.visibility = View.VISIBLE
            tv_title2.visibility = View.VISIBLE
        } else {
            tv_title1.visibility = View.GONE
            tv_title2.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_bar.setBackGone()

        initViewClick()
    }

    override fun onResume() {
        super.onResume()
        if (CommonUtil.isEmail(presenter.userName)) {
            tv_user_name.text = presenter.userName.replace("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)".toRegex(), "$1****$3$4")
        } else {
            tv_user_name.text = StringUtil.gettextView(presenter.userName)
        }
    }

    private fun initViewClick() {
        iv_user_msg.setOnClickListener {
            Navigator.toUserInfoActivity(activity!!)
        }
        assets_recharge_coin.setOnClickListener {
            //充值充币记录
            val intent = Intent(activity, CapitalFlowActivity::class.java)
            intent.putExtra("flag", 0)
            startActivity(intent)
        }
        assets_withdrawal_records.setOnClickListener {
            //全部充值提现记录
            val intent = Intent(activity, CapitalFlowActivity::class.java)
            intent.putExtra("flag", 0)
            startActivity(intent)
        }
        assets_top_up.setOnClickListener {
            val intent = Intent(activity, RechargeActivity::class.java)
            startActivity(intent)
        } //充值
        assets_withdrawal.setOnClickListener {
            val intent = Intent(activity, CashWithdrawalActivity::class.java)
            startActivity(intent)

        } //提现
        assets_charge_money.setOnClickListener {
            val intent = Intent(activity, CurrencyChoiceActivity::class.java)
            intent.putExtra("CurrencyChoice", "1")
            startActivity(intent)
        } //充币
        assets_mention_money.setOnClickListener {
            val intent = Intent(activity, CurrencyChoiceActivity::class.java)
            intent.putExtra("CurrencyChoice", "2")
            startActivity(intent)
        } //提币
        assets_transfer.setOnClickListener {
            val intent = Intent(activity, TransferActivity::class.java)
            startActivity(intent)
        }//划转

        ll_trading_record.setOnClickListener {
            //交易记录
            Navigator.toTradingRecordActivity(activity!!)
        }
        ll_invite_commission.setOnClickListener {
            //邀请返佣

        }
        ll_financial_center.setOnClickListener {
            //理财中心
            if (TextUtils.isEmpty(presenter.accountPassWord)) {
                showToast("请先设定交易密码")
                return@setOnClickListener
            }
            Navigator.toFinancialCenterActivity(activity!!)
        }
        ll_mention_money_address.setOnClickListener {
            //提币地址
            Navigator.toMoneyAddressActivity(activity!!)
        }
        ll_bank_card_management.setOnClickListener {
            //银行卡管理
            Navigator.toBankCardsManagerActivity(activity!!)
        }
        ll_security.setOnClickListener {
            //安全中心
            Navigator.toSecurityActivity(activity!!)
        }
        ll_setting.setOnClickListener {
            //设置
            Navigator.toLogoutActivity(activity!!)
        }
    }

}