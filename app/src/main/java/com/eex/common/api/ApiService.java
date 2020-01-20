package com.eex.common.api;

import com.eex.assets.bean.AddressListData;
import com.eex.assets.bean.BankListData;
import com.eex.assets.bean.BiAddres;
import com.eex.assets.bean.Bilistdata;
import com.eex.assets.bean.CBlistData;
import com.eex.assets.bean.Ctcaccount;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.ExchangeRate;
import com.eex.assets.bean.FunCode;
import com.eex.assets.bean.MoneyData;
import com.eex.assets.bean.Personal;
import com.eex.assets.bean.PersonalData;
import com.eex.assets.bean.RechargeCZData;
import com.eex.assets.bean.Remittance;
import com.eex.assets.bean.ResultListdata;
import com.eex.assets.bean.Sever;
import com.eex.assets.bean.ShiList;
import com.eex.assets.bean.getFunCode;
import com.eex.common.base.Data;
import com.eex.domin.entity.dealrecord.FuturesOrderBean;
import com.eex.home.bean.Accumulative24H;
import com.eex.home.bean.AdvertisingUser;
import com.eex.home.bean.Advertisingvo;
import com.eex.home.bean.BiUserData;
import com.eex.home.bean.CTwoCMain;
import com.eex.home.bean.CentsRed;
import com.eex.home.bean.CoinTradeConfig;
import com.eex.home.bean.CoinfigList;
import com.eex.home.bean.ConducMoney;
import com.eex.home.bean.ConducMoneydetails;
import com.eex.home.bean.CtcUserInfo;
import com.eex.home.bean.Graphics;
import com.eex.home.bean.History;
import com.eex.home.bean.IsUserExanState;
import com.eex.home.bean.JiecDeltaleMoneyData;
import com.eex.home.bean.LoginUser;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.home.bean.Merchdealaccount;
import com.eex.home.bean.MiningInfo;
import com.eex.home.bean.MinningDetails;
import com.eex.home.bean.MoneySearc;
import com.eex.home.bean.NewSdatalist;
import com.eex.home.bean.OdrderId;
import com.eex.home.bean.OrderDetail;
import com.eex.home.bean.Page;
import com.eex.home.bean.PageList;
import com.eex.home.bean.PaningMoneyDetails;
import com.eex.home.bean.Purchase;
import com.eex.home.bean.SMdata;
import com.eex.home.bean.SecondKillRecord;
import com.eex.home.bean.SecondRecordlist;
import com.eex.home.bean.SecondkillDetails;
import com.eex.home.bean.SecondkillLsitvo;
import com.eex.home.bean.SecondlistData;
import com.eex.home.bean.Stauts;
import com.eex.home.bean.Storehouse;
import com.eex.home.bean.Subordinate;
import com.eex.home.bean.TwoRealName;
import com.eex.home.bean.UpdatApp;
import com.eex.home.bean.User;
import com.eex.home.bean.Weihu;
import com.eex.home.bean.WelfareCountAbout;
import com.eex.home.bean.YesListData;
import com.eex.home.bean.getOrePool;
import com.eex.home.bean.getTotle;
import com.eex.market.bean.CoinCashFlowBean;
import com.eex.market.bean.DealSummaryPage;
import com.eex.market.bean.Delegation;
import com.eex.market.bean.HistoryBean;
import com.eex.market.bean.JsonRoot;
import com.eex.market.bean.KlineBiData;
import com.eex.market.bean.MeChoiceListvo;
import com.eex.market.bean.Money;
import com.eex.market.bean.PurchaseDatalIst;
import com.eex.market.bean.PurchaseDta;
import com.eex.market.bean.Stoploss;
import com.eex.market.weight.BuyMoney;
import com.eex.market.weight.CashFlow;
import com.eex.market.weight.Root;
import com.eex.mine.bean.Detllett;
import com.eex.mine.bean.Frient;
import com.eex.mine.bean.Google;
import com.eex.mine.bean.GoogleBind;
import com.eex.mine.bean.RechargeFY;
import com.eex.mine.bean.UserType;
import com.eex.mvp.assrtsjava.bean.Assets;
import com.eex.mvp.market.bean.DealPairList;
import com.eex.mvp.trading.AssetsCoin;
import com.eex.mvp.trading.IndexEntrustDTO;
import com.eex.mvp.trading.IndexMarketList;
import com.eex.mvp.trading.KDataList;
import com.eex.notice.bean.IndustryBean;
import com.eex.notice.bean.NewSXQ;
import com.eex.notice.bean.NoticeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author 空
 * @date 2017/6/8 0008
 */

public interface ApiService {

  /**
   * guide
   * 引导页面
   */
  @FormUrlEncoded
  @POST("user/checkApp")
  Observable<Data> guide(@FieldMap HashMap<String, String> hashMap);

  /**
   * 是否后台维护
   */
  @GET("appweihu/app.json")
  Observable<Weihu> weihu();

  /**
   * 获取更新版本信息
   */
  @FormUrlEncoded
  @POST("user/checkApp")
  Observable<Data<UpdatApp>> checkApp(@FieldMap HashMap<String, String> hashMap);

  //    登陆

  /**
   * 登录
   */
  @FormUrlEncoded
  @POST("user/login")
  Observable<Data<User<LoginUser>>> login(@FieldMap HashMap<String, String> hashMap);

  /**
   * 获取更新版本信息
   */
  @FormUrlEncoded
  @POST("user/addAppVersionDetail")
  Observable<Data> addAppVersionDetail(@FieldMap HashMap<String, String> hashMap);

  /**
   * 手机获取短信验证码接口
   */
  @FormUrlEncoded
  @POST("user/sms/send")
  Observable<Data> send(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 手机获取短信验证码接口
   */
  @FormUrlEncoded
  @POST("user/sms/send")
  Observable<Data> send1(@FieldMap HashMap<String, String> hashMap);

  /**
   * 手机获取短信验证码接口
   */
  @FormUrlEncoded
  @POST("user/email/send-code")
  Observable<Data> sendcode(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 图形验证码
   */
  @FormUrlEncoded
  @POST("user/captcha")
  Observable<Data<Graphics>> captcha(@FieldMap HashMap<String, String> hashMap);

  /**
   * 获取邮箱验证码
   */
  @FormUrlEncoded
  @POST("user/email/send-code")
  Observable<Data> email(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取邮箱验证码
   */
  @FormUrlEncoded
  @POST("user/email/send-code")
  Observable<Data> email1(@FieldMap HashMap<String, String> hashMap);

  /**
   * 校验短信
   */
  @FormUrlEncoded
  @POST("user/sms/check-code")
  Observable<Data> checkcode(@FieldMap HashMap<String, String> hashMap);

  /**
   * 手机号码注册
   */
  @FormUrlEncoded
  @POST("user/regist")
  Observable<Data> regist(@FieldMap HashMap<String, String> hashMap);

  /**
   * 找回密码第二步接口
   */
  @FormUrlEncoded
  @POST("user/reset-pwd")
  Observable<Data> resetpwd(@FieldMap HashMap<String, String> hashMap);

  //    首页

  /**
   * 首页广告
   */
  @FormUrlEncoded
  @POST("user/index/banner")
  Observable<Data<Page<Subordinate>>> banner(@Header("user-agent") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 最新消息
   */
  @FormUrlEncoded
  @POST("user/index/list-page")
  Observable<Data<NewSdatalist<IndustryBean>>> listpage(@FieldMap HashMap<String, String> hashMap);

  /**
   * 24h待分红累计约
   * 24h单币分红约
   */
  @FormUrlEncoded
  @POST("coinwelfare/count/count-current-coin-welfare")
  Observable<Data<WelfareCountAbout>> coinwelfare(@FieldMap HashMap<String, String> hashMap);

  /**
   * EBT流通量
   */
  @FormUrlEncoded
  @POST("coinwelfare/count/count-ebt-circulate")
  Observable<Data> circulate(@FieldMap HashMap<String, String> hashMap);

  /**
   * 矿池量
   * 当前算法
   * 当日产量
   */
  @FormUrlEncoded
  @POST("trademining/info/getMiningInfo")
  Observable<Data<MiningInfo>> getMiningInfo(@FieldMap HashMap<String, String> hashMap);

  /**
   * c2c是否答题通过
   */
  @FormUrlEncoded
  @POST("ctc/ctcaccount/get-ctcdeal-state")
  Observable<Data<IsUserExanState>> ctcaccount(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 首页list集合
   */
  @FormUrlEncoded
  @POST("TradingHall/dealPair/getDealPairList")
  Observable<Data<ArrayList<MainList>>> getDealPairList(@FieldMap HashMap<String, String> hashMap);

  /**
   * 首页list集合
   */
  @FormUrlEncoded
  @POST("future/indexKline/getDealPairList")
  Observable<Data<ArrayList<DealPairList>>> getfutureDealPairList(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * user/get-auth-stauts
   */
  @FormUrlEncoded
  @POST("user/get-auth-stauts")
  Observable<Data<SMdata>> authStauts(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 首页list
   */
  @FormUrlEncoded
  @POST("TradingHall/macket/getIndexMaketList")
  Observable<Data<ArrayList<MainData>>> getIndexMaketList(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c根据币种查询详情
   */
  @FormUrlEncoded
  @POST("ctc/coinTradeConfig/getCoinTradeConfig")
  Observable<Data<CoinTradeConfig>> getCoinTradeConfig(@FieldMap HashMap<String, String> hashMap);

  /**
   * c2c答题提交
   */
  @FormUrlEncoded
  @POST("ctc/ruleexam/submit-exam")
  Observable<Data> submitruleexam(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c创建广告
   */
  @FormUrlEncoded
  @POST("ctc/advertising/creatAdvertising")
  Observable<Data> creatAdvertising(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c获取法币交易列表
   */
  @FormUrlEncoded
  @POST("ctc/coinTradeConfig/getCoinTradeConfigList")
  Observable<Data<ArrayList<CoinfigList>>> getCoinTradeConfigList(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c查询用户是否绑定支付方式
   */
  @FormUrlEncoded
  @POST("ctc/merchdealaccount/get-list")
  Observable<Data<ArrayList<Merchdealaccount>>> merchdealaccount(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);
  //手机认证 user/safety/bind-phone

  /**
   * 手机认证接口
   */
  @FormUrlEncoded
  @POST("user/safety/bind-phone")
  Observable<Data> bindphone(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 手机解绑接口
   */
  @FormUrlEncoded
  @POST("user/safety/cenel-bind-phone")
  Observable<Data> cenelbindphone(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //用户二级认证

  /**
   * 用户二级认证
   */
  @FormUrlEncoded
  @POST("user/two-level-auth")
  Observable<Data> twolevelauth(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //级别2认证完成

  /**
   * 获取等级配置
   */
  @FormUrlEncoded
  @POST("user/coin/getlevelconfig")
  Observable<Data<TwoRealName>> getlevelconfig(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);
  //实名认证

  /**
   * 上传图片
   */
  @Multipart
  @POST("user/fileupload/upload")
  Observable<Data> upload(@Header("authorization") String apikey, @Part MultipartBody.Part file);

  /**
   * 实名认证3提交接口
   */
  @FormUrlEncoded
  @POST("user/thr-level-auth")
  Observable<Data> levelauth(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c绑定支付方式
   */
  @FormUrlEncoded
  @POST("ctc/merchdealaccount/save-or-update")
  Observable<Data> saveorupdate(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c广告列表
   */
  @FormUrlEncoded
  @POST("ctc/advertisingSelect/getAdvertisingPage")
  Observable<Data<Page<Advertisingvo>>> getAdvertisingPage(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c获取聊天记录
   */
  @FormUrlEncoded
  @POST("chatweb/userChatRecordHistory/getHistory")
  Observable<Data<Page<History>>> getHistory(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c查询订单详情
   */
  @FormUrlEncoded
  @POST("ctc/shop/buy/getOrderDetailById")
  Observable<Data<OdrderId>> getOrderDetailById(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c买家标记标记为已付款
   */
  @FormUrlEncoded
  @POST("ctc/shop/buy/updatePayBeConfirm")
  Observable<Data> updatePayBeConfirm(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c卖家标记标记为已收款
   */
  @FormUrlEncoded
  @POST("ctc/shop/buy/updatePayOK")
  Observable<Data> updatePayOK(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c取消订单id
   */
  @FormUrlEncoded
  @POST("ctc/shop/buy/updatePayCancle")
  Observable<Data> updatePayCancle(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c上传聊天图片
   */
  @Multipart
  @POST("chatweb/userChatRecord/uploadFile")
  Observable<Data> uploadFile(@Header("authorization") String apikey,
      @Part MultipartBody.Part file);

  /**
   * c2c答题列表
   */
  @FormUrlEncoded
  @POST("ctc/ruleexam/get-list")
  Observable<Data<ArrayList<CTwoCMain>>> ruleexam(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //    分红挖矿详情

  /**
   * 获取昨日累计分红
   */
  @FormUrlEncoded
  @POST("coinwelfare/count/last-welfare")
  Observable<Data<MinningDetails>> lastwelfare(@FieldMap HashMap<String, String> hashMap);

  //我的分红

  /**
   * 获取我的分紅列表
   */
  @FormUrlEncoded
  @POST("coinwelfare/count/get-welfare-record")
  Observable<Data<Page<CentsRed>>> getwelfarerecord(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //取24H待分红累计

  /**
   * 获取24H待分红累计
   */
  @FormUrlEncoded
  @POST("coinwelfare/count/get-wait-welfare")
  Observable<Data<ArrayList<Accumulative24H>>> getwaitwelfare(
      @FieldMap HashMap<String, String> hashMap);

  // 我的挖矿

  /**
   * 获取我的挖矿列表
   */
  @FormUrlEncoded
  @POST("trademining/info/getTotleOrePoolRecordList")
  Observable<Data<PageList<getTotle>>> trademining(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //挖矿详情

  /**
   * 获取我的挖矿列表详情
   */
  @FormUrlEncoded
  @POST("trademining/info/getOrePoolRecordList")
  Observable<Data<PageList<getOrePool>>> getOrePoolRecordList(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  //秒杀专区

  /**
   * 获取秒杀列表
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/getPage")
  Observable<Data<Page<SecondkillLsitvo>>> getPage(@FieldMap HashMap<String, String> hashMap);

  /**
   * 是否实名
   */
  @FormUrlEncoded
  @POST("user/get-auth-stauts")
  Observable<Data<Stauts>> getauthstauts(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);
  //秒杀详情

  /**
   * 获取秒杀配置
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/findSecondKillOrderConfigById")
  Observable<Data<SecondkillDetails>> findSecondKillOrderConfigById(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 秒杀提交
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/saveSecondOrder")
  Observable<Data> saveSecondOrder(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //锁仓理财

  /**
   * 查询理财配置
   */
  @FormUrlEncoded
  @POST("lockingMoney/config/getLockingConfigByCoinCode")
  Observable<Data<ConducMoney<ConducMoneydetails>>> getLockingConfigByCoinCode(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 提现验证码提交接口
   */
  @FormUrlEncoded
  @POST("user/depositcoin/get-coin-account-info")
  Observable<Data<BiUserData>> getcoinaccountinfo(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 查询理财配置
   */
  @FormUrlEncoded
  @POST("lockingMoney/record/saveLockingMoney")
  Observable<Data<BiUserData>> saveLockingMoney(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 列表查询可理财的币种更新后接口
   */
  @FormUrlEncoded
  @POST("lockingMoney/config/getLockMoneyDetailCoin")
  Observable<Data<ArrayList<MoneySearc>>> getLockMoneyDetailCoin(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 分页查询可理财
   */
  @FormUrlEncoded
  @POST("lockingMoney/record/findLockingRecordPage")
  Observable<Data<Page<PaningMoneyDetails>>> findLockingRecordPage(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  //理财币种

  /**
   * 锁仓查询理财币种
   */
  @FormUrlEncoded
  @POST("lockingMoney/config/getLockMoneyCoin")
  Observable<Data<ArrayList<YesListData>>> getLockMoneyCoin(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //理财明细

  /**
   * 解仓列表详情
   */
  @FormUrlEncoded
  @POST("lockingMoney/detail/findLockMoneyDetail")
  Observable<Data<PageList<JiecDeltaleMoneyData>>> findLockMoneyDetail(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  //解仓

  /**
   * 根据ID查询解仓信息
   */
  @FormUrlEncoded
  @POST("lockingMoney/record/findLockingMoneyById")
  Observable<Data<Storehouse>> findLockingMoneyById(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 解仓
   */
  @FormUrlEncoded
  @POST("lockingMoney/record/openLockingMoney")
  Observable<Data<Storehouse>> openLockingMoney(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  //秒杀记录

  /**
   * 获取秒杀记录
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/getSecondKillRecordPage")
  Observable<Data<Page<SecondKillRecord>>> getSecondKillRecordPage(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取秒杀记录
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/unLockSecondOrder")
  Observable<Data> unLockSecondOrder(@FieldMap HashMap<String, String> hashMap);

  /**
   * 根据ID查询配置
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/findSecondKillRecordById")
  Observable<Data<SecondlistData>> findSecondKillRecordById(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c查询用户订单列表
   */
  @FormUrlEncoded
  @POST("ctc/shop/buy/getOrderDetailList")
  Observable<Data<Page<OrderDetail>>> getOrderDetailList(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c查询用户订单列表
   */
  @FormUrlEncoded
  @POST("lockingMoney/kill/getAllCoinCode")
  Observable<Data<ArrayList<SecondRecordlist>>> getAllCoinCode(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 市场
   */

  /**
   * 根据ID查询配置
   */
  @FormUrlEncoded
  @POST("TradingHall/dealPair/getDealPairCollectionList")
  Observable<Data<PageList<MeChoiceListvo>>> getDealPairCollectionList(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 我的委托-当前委托
   */
  @FormUrlEncoded
  @POST("TradingHall/delegation/getDelegationList")
  Observable<Data<Page<Delegation>>> getDelegationList(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 创新版成交汇总
   */
  @FormUrlEncoded
  @POST("TradingHall/delegation/getDelegationDealSummaryPage")
  Observable<Data<Page<DealSummaryPage>>> getDelegationDealSummaryPage(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 当前委托记录止盈止损
   */
  @FormUrlEncoded
  @POST("TradingHall/stoploss/getStoplossList")
  Observable<Data<Page<Stoploss>>> getStoplossList(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 当前委托记录撤单止盈止损
   */
  @FormUrlEncoded
  @POST("TradingHall/stoploss/cancelStoploss")
  Observable<Data> cancelStoploss(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 取消委托
   */
  @FormUrlEncoded
  @POST("TradingHall/delegation/cancelDelegation")
  Observable<Data> cancelDelegation(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 饼状图json
   */
  @GET("static/data/coin.json")
  Observable<JsonRoot> staticdata();

  /**
   * 饼状图
   */
  @GET("index/coinCashFlow")
  Observable<CashFlow<CoinCashFlowBean<HistoryBean>>> CoinCashFlow(
      @Query("coincode") String coincode);

  //交易大厅List

  /**
   * 交易大厅List
   */
  @FormUrlEncoded
  @POST("TradingHall/macket/getTickMaket")
  Observable<Root<PurchaseDta>> getTickMaket(@FieldMap HashMap<String, String> hashMap);

  /**
   * K线数据
   */
  @FormUrlEncoded
  @POST("TradingHall/macket/getTickList")
  Observable<Data<ArrayList<PurchaseDatalIst>>> TradingHall(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 根据ID查询配置
   */
  @FormUrlEncoded
  @POST("TradingHall/dealPair/saveOrUpdateDealPairCollection")
  Observable<Data<KlineBiData>> saveOrUpdateDealPairCollection(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取余额
   */
  @FormUrlEncoded
  @POST("user/user-deal-account")
  Observable<Data<ArrayList<BuyMoney<Money>>>> account(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 交易大厅，买卖
   */
  @FormUrlEncoded
  @POST("TradingHall/delegation/creatDelegation")
  Observable<Data> creatDelegation(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 创建止盈止损
   */
  @FormUrlEncoded
  @POST("TradingHall/stoploss/creatStoploss")
  Observable<Data> creatStoploss(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 资产
   */

  /**
   * 个人中心
   */
  @FormUrlEncoded
  @POST("user/user-account-info")
  Observable<Data<PersonalData<Personal>>> accountinfo(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 解冻(后台TMD说的)
   */
  @FormUrlEncoded
  @POST("lockingMoney/config/unfreezeCoinMoney")
  Observable<Data> unfreezeCoinMoney(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取所有能充值的币种
   */
  @FormUrlEncoded
  @POST("user/depositcoin/get-coin-list")
  Observable<Data<ArrayList<Bilistdata>>> depositcoin(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c资金划转币种列表
   */
  @FormUrlEncoded
  @POST("ctc/ctcaccount/list-account-info")
  Observable<Data<Ctcaccount<CtcaccountList>>> listctcaccount(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c资金划转
   */
  @FormUrlEncoded
  @POST("ctc/ctcaccount/funds-transfer")
  Observable<Data> fundstransfer(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c我的广告
   */
  @FormUrlEncoded
  @POST("ctc/advertisingSelect/getAdvertisingUserPage")
  Observable<Data<Page<AdvertisingUser>>> AdvertisingUserPage(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c上架广告
   */
  @FormUrlEncoded
  @POST("ctc/advertising/shelfAdvertising")
  Observable<Data> shelfAdvertising(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c下架广告
   */
  @FormUrlEncoded
  @POST("ctc/advertising/obtainedAdvertising")
  Observable<Data> obtainedAdvertising(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c删除广告
   */
  @FormUrlEncoded
  @POST("ctc/advertising/deleteAdvertising")
  Observable<Data> deleteAdvertising(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c根据id获取广告详情
   */
  @FormUrlEncoded
  @POST("ctc/advertisingSelect/getAdvertising")
  Observable<Data<Advertisingvo>> getAdvertising(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c用户买入货币
   */
  @FormUrlEncoded
  @POST("ctc/shop/buy/buy")
  Observable<Data<Purchase>> shopbuy(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c用户卖入货币
   */
  @FormUrlEncoded
  @POST("ctc/shop/sell/sell")
  Observable<Data<Purchase>> shopsell(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * c2c修改广告
   */
  @FormUrlEncoded
  @POST("ctc/advertising/updateAdvertising")
  Observable<Data> updateAdvertising(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取充值法币币种
   */
  @FormUrlEncoded
  @POST("user/deposit/getFunCode")
  Observable<Data<ArrayList<getFunCode>>> getFunCode(@FieldMap HashMap<String, String> hashMap);

  /**
   * 充值生成汇款单
   */
  @FormUrlEncoded
  @POST("user/deposit/create-remittance")
  Observable<Data<RechargeCZData>> remittance(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取余额
   */
  @FormUrlEncoded
  @POST("user/deposit/view")
  Observable<Data<MoneyData>> deposit(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 人民币流水接口
   */
  @FormUrlEncoded
  @POST("user/deposit/list-deposit-or-extract-page")
  Observable<Data<PageList<ResultListdata>>> extractpage(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取指定汇率
   */
  @FormUrlEncoded
  @POST("user/deposit/getExchangeRate")
  Observable<Data<ExchangeRate>> getExchangeRate(@FieldMap HashMap<String, String> hashMap);

  /**
   * 调用epay充值
   */
  @FormUrlEncoded
  @POST("user/deposit/create-remittance-other")
  Observable<Data<Remittance>> remittanceother(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取提现法币币种
   */
  @FormUrlEncoded
  @POST("user/getWithdrawFunCode")
  Observable<Data<ArrayList<FunCode>>> getWithdrawFunCode(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 提现接口
   */
  @FormUrlEncoded
  @POST("user/extract")
  Observable<Data> extract(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取手续费接口
   */
  @FormUrlEncoded
  @POST("user/get-cny-account-info")
  Observable<Data<Sever>> cnyaccount(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 删除银行卡
   */
  @FormUrlEncoded
  @POST("user/bank/del-bank-card")
  Observable<Data> delete(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取可用银行卡
   */
  @FormUrlEncoded
  @POST("user/bank/list-bank-card")
  Observable<Data<ArrayList<BankListData>>> listbank(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 添加银行卡
   */
  @FormUrlEncoded
  @POST("user/bank/add-bank-card")
  Observable<Data> addbank(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 国家地区市list集合
   */
  @FormUrlEncoded
  @POST("mobile/user/appbankcode/appcity.do")
  Observable<ShiList> appbankcode(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 校验交易密码
   */
  @FormUrlEncoded
  @POST("user/update-account-pwd")
  Observable<Data> updateaccount(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取指定汇率
   */
  @FormUrlEncoded
  @POST("user/deposit/getExchangeRate")
  Observable<Data<ExchangeRate>> ExchangeRat(@FieldMap HashMap<String, String> hashMap);

  /**
   * 获取指定汇率
   */
  @FormUrlEncoded
  @POST("user/extractOther")
  Observable<Data> extractOther(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取所有能充值的币种
   */
  @FormUrlEncoded
  @POST("user/depositcoin/get-coin-list")
  Observable<Data<ArrayList<Bilistdata>>> depositcoincoinlist(
      @Header("authorization") String apikey, @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取币接口
   */
  @FormUrlEncoded
  @POST("user/depositcoin/get-coin-account-info")
  Observable<Data<BiAddres>> getaccountinfo(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 提币记录
   */
  @FormUrlEncoded
  @POST("user/depositcoin/get-coin-addr-page")
  Observable<Data<PageList<CBlistData>>> coinaddrpage(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 提现验证码提交接口
   */
  @FormUrlEncoded
  @POST("user/depositcoin/get-coin-account-info")
  Observable<Data<BiUserData>> getaccountinfo1(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取币接口
   */
  @FormUrlEncoded
  @POST("user/coin/coin-extract")
  Observable<Response<Data>> coinextract(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 获取钱包地址接口
   */
  @FormUrlEncoded
  @POST("user/coinaddress/list-coin-address-page")
  Observable<Data<PageList<AddressListData>>> coinaddress(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 删除币账户地址接口
   */
  @FormUrlEncoded
  @POST("user/coinaddress/del-coin-address")
  Observable<Data> delcoinaddress(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 新增币账户地址接口
   */
  @FormUrlEncoded
  @POST("user/coinaddress/add-coin-account")
  Observable<Data> addcoinaddress(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 公告
   */

  /**
   * 最新消息
   */
  @FormUrlEncoded
  @POST("user/index/list-type")
  Observable<Data<ArrayList<NoticeBean>>> listtype(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 我的
   */

  /**
   * 查看当前用户状态
   */
  @FormUrlEncoded
  @POST("user/get-user-grade")
  Observable<Data<UserType>> usergrade(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 获取等级配置
   */
  @FormUrlEncoded
  @POST("user/coin/get-level-config")
  Observable<Data<TwoRealName>> levelconfig(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 新闻详情
   */
  @FormUrlEncoded
  @POST("user/index/get-one")
  Observable<Data<NewSXQ>> getone(@FieldMap Map<String, String> hashMap);

  /**
   * 推荐返佣
   */
  @FormUrlEncoded
  @POST("user/brokerage/get-invite-and-charges")
  Observable<Data<RechargeFY>> brokerage(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 返佣详情
   */
  @FormUrlEncoded
  @POST("user/brokerage/view-charges")
  Observable<Data<PageList<Detllett>>> viewcharges(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 获取所有邀请朋友链接
   */
  @FormUrlEncoded
  @POST("user/brokerage/view-invite-friend")
  Observable<Data<PageList<Frient>>> viewinvitefriend(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 修改交易密码
   */
  @FormUrlEncoded
  @POST("user/find-account-pwd")
  Observable<Data> findaccount(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 生成googleKey
   */
  @FormUrlEncoded
  @POST("user/create-google-key")
  Observable<Data<Google>> creategoogle(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 绑定googleKeyind
   */
  @FormUrlEncoded
  @POST("user/bind-google-validate")
  Observable<Data<GoogleBind>> validate(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 取消绑定googleKeyind
   */
  @FormUrlEncoded
  @POST("user/relieve-google-validate")
  Observable<Data<GoogleBind>> googlevalidate(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 邮箱绑定
   */
  @FormUrlEncoded
  @POST("user/safety/bind-email")
  Observable<Data> bindemail(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 取消邮箱绑定
   */
  @FormUrlEncoded
  @POST("user/safety/cenel-bind-email")
  Observable<Data> cenelbind(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 修改密码
   */

  @FormUrlEncoded
  @POST("user/safety/updatepwd")
  Observable<Data> updatepwd(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 修改商家名称
   */

  @FormUrlEncoded
  @POST("ctc/ctcaccount/ctcNickName")
  Observable<Data> ctcNickName(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 获取c2c账户名称
   */
  @FormUrlEncoded
  @POST("ctc/ctcaccount/ctcUserInfo")
  Observable<Data<CtcUserInfo>> ctcUserInfo(@Header("authorization") String apikey,
      @FieldMap Map<String, String> hashMap);

  /**
   * 上传错误信息
   *
   * @param type 1：android 2:ios
   * @param message 日志内容
   */
  @FormUrlEncoded
  @POST("user/createAppLog")
  Observable<Data<Object>> collectError(@Field("type") int type, @Field("logs") String message);

  /**
   * 指数k线历史
   */
  @FormUrlEncoded
  @POST("future/indexKline/getKDataList")
  Observable<Data<ArrayList<KDataList>>> getKDataList(@FieldMap HashMap<String, String> hashMap);

  /**
   * 持仓结算撤销列表
   */
  @FormUrlEncoded
  @POST("future/indexEntrust/bonds")
  Observable<Data<PageList<IndexEntrustDTO>>> bonds(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);
  /**
   * 持仓结算撤销列表
   */
  @FormUrlEncoded
  @POST("future/indexEntrust/bonds")
  Observable<Data<PageList<FuturesOrderBean>>> bonds1(@Header("authorization") String apikey,
                                                      @FieldMap HashMap<String, String> hashMap);

  /**
   * 个人中心
   */
  @FormUrlEncoded
  @POST("future/indexAssets/assets")
  Observable<Data<ArrayList<Assets>>> assets(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 指数行情
   */
  @FormUrlEncoded
  @POST("future/indexKline/getIndexMarketList")
  Observable<Data<ArrayList<IndexMarketList>>> getIndexMarketList(
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 指数行情
   */
  @FormUrlEncoded
  @POST("future/indexAssets/assetsCoin")
  Observable<Data<AssetsCoin>> getassetsCoin(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 指数行情
   */
  @FormUrlEncoded
  @POST("future/indexEntrust/createIndexEntrust")
  Observable<Data> getcreateIndexEntrust(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 止盈止损
   */
  @FormUrlEncoded
  @POST("future/indexEntrust/updateSurplusDamage")
  Observable<Data<Object>> updateSurplusDamage(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 互转资金
   */
  @FormUrlEncoded
  @POST("future/indexAssets/transferAssets")
  Observable<Data> gettransferAssets(@Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);

  /**
   * 币币交易 获取K线
   */
  @FormUrlEncoded
  @POST("TradingHall/macket/getKDataList")
  Observable<Data<ArrayList<KDataList>>>  getKDataListmacket(@FieldMap HashMap<String, String> hashMap);

  /**
   * 取消收藏
   */
  @FormUrlEncoded
  @POST("TradingHall/dealPair/deleteDealPairCollectionByIds")
  Observable<Data<Object>> cancelFocus(
      @Header("authorization") String apikey,
      @FieldMap HashMap<String, String> hashMap);
}
