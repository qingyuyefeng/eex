package com.eex.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.eex.R
import com.eex.assets.activity.BankListActivity
import com.eex.constant.Keys
import com.eex.home.activity.home.AdvertiseActivity
import com.eex.home.activity.home.BuySellActivity
import com.eex.home.activity.home.C2CSetMoneyActivity
import com.eex.home.activity.home.PurchaseCurrencyActivity
import com.eex.home.activity.home.SecondkillActivity
import com.eex.home.activity.home.SellDetelisActivity
import com.eex.home.activity.login.SeverActivity
import com.eex.mine.activity.MeMoneyPwordActivity
import com.eex.mine.activity.NewsPersionActivity
import com.eex.mvp.attorney.AttorneyActivity
import com.eex.mvp.attorney.HistoryAttorneyActivity
import com.eex.mvp.bonds.BondsActivity
import com.eex.mvp.mainpage.MainActivity
import com.eex.mvp.mine.bankcards.BankCardsManagerActivity
import com.eex.mvp.mine.bankcards.addbankcard.AddBankCardActivity
import com.eex.mvp.mine.financialcenter.financialrecords.FinancialRecordsActivity
import com.eex.mvp.mine.logout.LogoutActivity
import com.eex.mvp.mine.moneyaddress.MoneyAddressActivity
import com.eex.mvp.mine.moneyaddress.addaddress.AddAddressActivity
import com.eex.mvp.mine.security.SecurityActivity
import com.eex.mvp.mine.security.googleverify.bindgoogle1.GoogleVerificationActivity
import com.eex.mvp.mine.security.googleverify.bindgoogle2.GoogleVerifyActivity2
import com.eex.mvp.mine.security.googleverify.unbindgoogle.UnbindGoogleActivity
import com.eex.mvp.mine.security.legalmethod.LegalMethodActivity
import com.eex.mvp.mine.security.legalmethod.alipayorwechat.AlipayOrWechatActivity
import com.eex.mvp.mine.security.legalmethod.bankcard.BankCardSettingActivity
import com.eex.mvp.mine.security.newtradingpwd.NewTradingPwdActivity
import com.eex.mvp.mine.security.nrewpassword.NewPasswordActivity
import com.eex.mvp.mine.security.phoneoremail.PhoneOrEmailActivity
import com.eex.mvp.mine.security.tradenick.TradeNickActivity
import com.eex.mvp.mine.tradingrecord.futuresorder.FuturesOrderDetailActivity
import com.eex.mvp.mine.tradingrecord.TradingRecordActivity
import com.eex.mvp.mine.tradingrecord.coinmoney.CoinMoneyOrdersActivity
import com.eex.mvp.mine.tradingrecord.futuresorder.FuturesOrderActivity
import com.eex.mvp.mine.tradingrecord.legalorder.LegalOrderActivity
import com.eex.mvp.mine.userinfo.UserInfoActivity
import com.eex.mvp.mine.userinfo.highsgs.HighSgsActivity
import com.eex.mvp.mine.userinfo.realname.RealNameActivity
import com.eex.mvp.transaction.cointradingdetail.CoinTradingDetailActivity
import com.eex.mvp.usercenter.PhoneListActivity
import com.eex.mvp.usercenter.login.LoginActivity
import com.eex.mvp.usercenter.register.RegisterActivity
import com.eex.mvp.usercenter.retrieve.RetrievePasswordActivity
import com.eex.mvp.usercenter.setnewpassword.SetNewPasswordActivity
import java.io.Serializable

object Navigator {
    const val REQUEST_CODE_MONEY_PASSWD = 10001

    fun toRealNameActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, RealNameActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toHighSgsActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, HighSgsActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toMeMoneyPwordActivity(activity: Any) {
        if (activity is Activity) {
            val intent = Intent(activity, MeMoneyPwordActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE_MONEY_PASSWD)
        } else if (activity is Fragment) {
            val intent = Intent(activity.activity, MeMoneyPwordActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE_MONEY_PASSWD)
        }
    }

    fun toGooleActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, GoogleVerificationActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toGooleActivity2(
            activity: Activity,
            requestCode: Int = -1,
            extra: String
    ) {
        val intent = Intent()
        intent.putExtra("secretKey", extra)
        intent.setClass(activity, GoogleVerifyActivity2::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toUnbindGoogleActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, UnbindGoogleActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toNewPasswordActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, NewPasswordActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toMainActivity(
            activity: Activity,
            requestCode: Int = -1,
            extra: String? = null
    ) {
        val intent = Intent()
        intent.setClass(activity, MainActivity::class.java)
        if (extra != null) {
            intent.putExtra("coinCode", extra)
        }
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toLoginActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setClass(activity, LoginActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toLogin(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        context.startActivity(intent)
    }

    fun toRegisterActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, RegisterActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toRetrievePasswordActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, RetrievePasswordActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toSetNewPasswordActivity(
            activity: Activity,
            requestCode: Int = -1,
            ser: Serializable
    ) {
        val intent = Intent()
        intent.putExtra("retrieve", ser)
        intent.setClass(activity, SetNewPasswordActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toPhoneListActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, PhoneListActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toLogoutActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, LogoutActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toSecurityActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setClass(activity, SecurityActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toSeverActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, SeverActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toBankCardsManagerActivity(
            activity: Activity,
            requestCode: Int = -1,
            extra: Int = 0
    ) {
        val intent = Intent()
        intent.setClass(activity, BankCardsManagerActivity::class.java)
        intent.putExtra("flag", extra)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toAddBankCardsActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, AddBankCardActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toBankListActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, BankListActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toFinancialCenterActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        Toast.makeText(activity, R.string.not_in_time, Toast.LENGTH_SHORT)
                .show()
//        val intent = Intent()
//        intent.setClass(activity, FinancialCenterActivity::class.java)
//        if (requestCode == -1) {
//            activity.startActivity(intent)
//        } else {
//            activity.startActivityForResult(intent, requestCode)
//        }
    }

    fun toAttorneyActivity(context: Context) {
        val intent = Intent()
        intent.setClass(context, AttorneyActivity::class.java)
        context.startActivity(intent)
    }

    fun toHistoryAttorneyActivity(context: Context) {
        val intent = Intent()
        intent.setClass(context, HistoryAttorneyActivity::class.java)
        context.startActivity(intent)
    }

    fun toPhoneOrEmailActivity(
            activity: Activity,
            requestCode: Int = -1,
            extra: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, PhoneOrEmailActivity::class.java)
        if (extra != -1) {
            intent.putExtra("phoneoremail", extra)
        }
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toLegalMethodActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, LegalMethodActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toUserInfoActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, UserInfoActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toMoneyAddressActivity(
            activity: Activity,
            requestCode: Int = -1,
            extra: Int = 0
    ) {
        val intent = Intent()
        intent.setClass(activity, MoneyAddressActivity::class.java)
        intent.putExtra("flag", extra)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toAddAddressActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, AddAddressActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toFinancialRecordsActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, FinancialRecordsActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toC2CSetMoneyActivity(context: Context) {
        context.startActivity(Intent(context, C2CSetMoneyActivity::class.java))
    }

    fun toSellCurrency(
            context: Context,
            id: String
    ) {
        val intent = Intent(context, SellDetelisActivity::class.java)
        intent.putExtra(Keys.PARAM_ID, id)
        context.startActivity(intent)
    }

    fun toBuyCurrency(
            context: Context,
            id: String
    ) {
        val intent = Intent(context, PurchaseCurrencyActivity::class.java)
        intent.putExtra(Keys.PARAM_ID, id)
        context.startActivity(intent)
    }

    fun toPublish(context: Context) {
        context.startActivity(Intent(context, AdvertiseActivity::class.java))
    }

    fun toSecondkillActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, SecondkillActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toBuySellActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, BuySellActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toNewTradingPwdActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, NewTradingPwdActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toTradeNickActivity(
            activity: Activity,
            requestCode: Int = -1
    ) {
        val intent = Intent()
        intent.setClass(activity, TradeNickActivity::class.java)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toBankCardSettingActivity(
            activity: Activity,
            requestCode: Int = -1,
            extra: String = ""
    ) {
        val intent = Intent()
        intent.setClass(activity, BankCardSettingActivity::class.java)
        intent.putExtra("id", extra)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toAlipayOrWechatActivity(
            activity: Activity,
            requestCode: Int = -1,
            extra1: Int = 0,
            extra2: String = ""
    ) {
        val intent = Intent()
        intent.setClass(activity, AlipayOrWechatActivity::class.java)
        intent.putExtra("type", extra1)
        intent.putExtra("id", extra2)
        if (requestCode == -1) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun toBonds(context: Context) {
        context.startActivity(Intent(context, BondsActivity::class.java))
    }

    fun toTradingRecordActivity(context: Context) {
        context.startActivity(Intent(context, TradingRecordActivity::class.java))
    }

    fun toCoinMoneyOrdersActivity(context: Context, extra: Int = 0) {
        val intent = Intent(context, CoinMoneyOrdersActivity::class.java)
        intent.putExtra("index", extra)
        context.startActivity(intent)
    }

    fun toFuturesOrderActivity(context: Context) {
        context.startActivity(Intent(context, FuturesOrderActivity::class.java))
    }

    fun toLegalOrderActivity(context: Context) {
        context.startActivity(Intent(context, LegalOrderActivity::class.java))
    }

    fun toNewsDetail(context: Context, newsId: String) {
        val intent = Intent(context, NewsPersionActivity::class.java)
        intent.putExtra(Keys.PARAM_ID, newsId)
        context.startActivity(intent)
    }
    fun toFuturesOrderDetailActivity(context: Context, extra: Serializable) {
        val intent = Intent(context, FuturesOrderDetailActivity::class.java)
        intent.putExtra("data", extra)
        context.startActivity(intent)
    }

    fun toCoinTradingDetailActivity(context: Context,extra: Bundle) {
        val intent = Intent(context, CoinTradingDetailActivity::class.java)
        intent.putExtra("extra",extra)
        context.startActivity(intent)
    }
}
