package com.eex.di.modules;

import com.eex.mvp.attorney.AttorneyActivity;
import com.eex.mvp.attorney.HistoryAttorneyActivity;
import com.eex.mvp.bonds.BondsActivity;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mine.bankcards.BankCardsManagerActivity;
import com.eex.mvp.mine.bankcards.addbankcard.AddBankCardActivity;
import com.eex.mvp.mine.financialcenter.FinancialCenterActivity;
import com.eex.mvp.mine.financialcenter.financialrecords.FinancialRecordsActivity;
import com.eex.mvp.mine.moneyaddress.MoneyAddressActivity;
import com.eex.mvp.mine.moneyaddress.addaddress.AddAddressActivity;
import com.eex.mvp.mine.security.SecurityActivity;
import com.eex.mvp.mine.security.googleverify.bindgoogle1.GoogleVerificationActivity;
import com.eex.mvp.mine.security.googleverify.bindgoogle2.GoogleVerifyActivity2;
import com.eex.mvp.mine.security.googleverify.unbindgoogle.UnbindGoogleActivity;
import com.eex.mvp.mine.security.legalmethod.LegalMethodActivity;
import com.eex.mvp.mine.security.legalmethod.alipayorwechat.AlipayOrWechatActivity;
import com.eex.mvp.mine.security.legalmethod.bankcard.BankCardSettingActivity;
import com.eex.mvp.mine.security.newtradingpwd.NewTradingPwdActivity;
import com.eex.mvp.mine.security.nrewpassword.NewPasswordActivity;
import com.eex.mvp.mine.security.phoneoremail.PhoneOrEmailActivity;
import com.eex.mvp.mine.security.tradenick.TradeNickActivity;
import com.eex.mvp.mine.tradingrecord.coinmoney.CoinMoneyOrdersActivity;
import com.eex.mvp.mine.tradingrecord.futuresorder.FuturesOrderActivity;
import com.eex.mvp.mine.tradingrecord.legalorder.LegalOrderActivity;
import com.eex.mvp.mine.userinfo.UserInfoActivity;
import com.eex.mvp.mine.userinfo.highsgs.HighSgsActivity;
import com.eex.mvp.mine.userinfo.realname.RealNameActivity;
import com.eex.mvp.usercenter.login.LoginActivity;
import com.eex.mvp.usercenter.register.RegisterActivity;
import com.eex.mvp.usercenter.retrieve.RetrievePasswordActivity;
import com.eex.mvp.usercenter.setnewpassword.SetNewPasswordActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllActivitysModule {

  @ContributesAndroidInjector
  abstract SecurityActivity contributeSecurityActivityInjector();

  @ContributesAndroidInjector
  abstract LoginActivity contributeLoginActivityInjector();

  @ContributesAndroidInjector
  abstract RegisterActivity contributeRegisterActivityInjector();

  @ContributesAndroidInjector
  abstract RetrievePasswordActivity contributeRetrieveActivityInjector();

  @ContributesAndroidInjector
  abstract SetNewPasswordActivity contributeSetNewActivityInjector();

  @ContributesAndroidInjector
  abstract MainActivity contributeMainActivityInjector();

  @ContributesAndroidInjector
  abstract BankCardsManagerActivity contributeBankCardsManagerActivityInjector();

  @ContributesAndroidInjector
  abstract AddBankCardActivity contributeAddBankCardActivityInjector();

  @ContributesAndroidInjector
  abstract FinancialCenterActivity contributeFinancialCenterActivityInjector();

  @ContributesAndroidInjector
  abstract AttorneyActivity contributeAttorneyActivityInjector();

  @ContributesAndroidInjector
  abstract HistoryAttorneyActivity contributeHistoryAttorneyActivityInjector();

  @ContributesAndroidInjector
  abstract PhoneOrEmailActivity contributePhoneOrEmailActivityInjector();

  @ContributesAndroidInjector
  abstract NewPasswordActivity contributeNewPasswordActivityInjector();

  @ContributesAndroidInjector
  abstract GoogleVerificationActivity contributeGoogleVerificationActivityInjector();

  @ContributesAndroidInjector
  abstract GoogleVerifyActivity2 contributeGoogleVerifyActivity2Injector();

  @ContributesAndroidInjector
  abstract UnbindGoogleActivity contributeUnbindGoogleActivityInjector();

  @ContributesAndroidInjector
  abstract MoneyAddressActivity contributeMoneyAddressActivityInjector();

  @ContributesAndroidInjector
  abstract AddAddressActivity contributeAddAddressActivityInjector();

  @ContributesAndroidInjector
  abstract FinancialRecordsActivity contributeFinancialRecordsActivityInjector();

  @ContributesAndroidInjector
  abstract RealNameActivity contributeRealNameActivityInjector();

  @ContributesAndroidInjector
  abstract HighSgsActivity contributeHighSgsActivityInjector();

  @ContributesAndroidInjector
  abstract NewTradingPwdActivity contributeNewTradingPwdActivityInjector();

  @ContributesAndroidInjector
  abstract TradeNickActivity contributeTradeNickActivityInjector();

  @ContributesAndroidInjector
  abstract LegalMethodActivity contributeLegalMethodActivityInjector();

  @ContributesAndroidInjector
  abstract BankCardSettingActivity contributeBankCardSettingActivityInjector();

  @ContributesAndroidInjector
  abstract AlipayOrWechatActivity contributeAlipayOrWechatActivityInjector();

  @ContributesAndroidInjector
  abstract BondsActivity contributeBondsActivityInjector();

  @ContributesAndroidInjector
  abstract CoinMoneyOrdersActivity contributeCoinMoneyOrdersActivityInjector();

  @ContributesAndroidInjector
  abstract FuturesOrderActivity contributeFuturesOrderActivityInjector();

 @ContributesAndroidInjector
  abstract LegalOrderActivity contributeLegalOrderActivityInjector();

  @ContributesAndroidInjector
  abstract UserInfoActivity contributeUserInfoActivityInjector();
}
