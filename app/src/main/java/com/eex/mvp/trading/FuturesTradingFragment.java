package com.eex.mvp.trading;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.base.Data;
import com.eex.common.util.CommonUtil;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber;
import com.eex.constant.Keys;
import com.eex.constant.Constants;
import com.eex.domin.entity.dealrecord.FuturesOrderBean;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.UpdateParamView;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mainpage.MainContract;
import com.eex.mvp.market.bean.DealPairList;
import com.eex.mvp.market.bean.dealCoinDTOList;
import com.eex.mvp.mine.tradingrecord.futuresorder.FuturesOrderApi;

import com.eex.mvp.trading.activity.FuturesTradingActivity;
import com.eex.navigation.Navigator;
import com.google.gson.Gson;
import com.icechao.klinelib.adapter.KLineChartAdapter;
import com.icechao.klinelib.base.BaseKLineChartView;
import com.icechao.klinelib.formatter.DateFormatter;
import com.icechao.klinelib.formatter.ValueFormatter;
import com.icechao.klinelib.utils.DateUtil;
import com.icechao.klinelib.utils.SlidListener;
import com.icechao.klinelib.utils.Status;
import com.icechao.klinelib.view.KLineChartView;
import io.reactivex.functions.Consumer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.Locale;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: Futures
 * @Package: com.futures.trading.fragment.Futurestrading
 * @ClassName: FuturesTradingFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/10/31 14:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/31 14:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 期货交易
 */
public class FuturesTradingFragment extends BaseFragment
    implements UpdateParamView {

  /**
   * 交易对
   */
  @BindView(R.id.trading_currency_tv)
  TextView tradingCurrencyTv;
  /**
   * 选择交易对
   */
  @BindView(R.id.trading_currency_image)
  ImageView tradingCurrencyImage;
  /**
   * 全屏K线
   */
  @BindView(R.id.trading_full_screen_image)
  ImageView tradingFullScreenImage;
  /**
   * 数值
   */
  @BindView(R.id.trading_value)
  TextView tradingValue;
  /**
   * 数值
   */
  @BindView(R.id.trading_percentage)
  TextView tradingPercentage;
  /**
   * K线
   */
  @BindView(R.id.kLineChartView)
  KLineChartView chartView;
  /**
   * `
   * 选择时间
   */
  @BindView(R.id.trading_atthe)
  KLineMenuView tradingAtthe;
  /**
   * 选择市价下单  触发下单
   */
  @BindView(R.id.trading_price_order)
  KLineMenuView tradingPriceOrder;
  /**
   * 新手指南
   */
  @BindView(R.id.trading_finger_guide)
  LinearLayout tradingFingerGuide;
  /**
   * 输入触发价
   */
  @BindView(R.id.trading_edtexit)
  EditText tradingEdtexit;
  /**
   * 触发价 减
   */
  @BindView(R.id.trading_reduce)
  ImageView tradingReduce;
  /**
   * 触发价 加
   */
  @BindView(R.id.trading_add)
  ImageView tradingAdd;
  /**
   * 触发价  加和减 输入
   */
  @BindView(R.id.linear_add)
  LinearLayout linearAdd;
  /**
   * 输入 买入数
   */
  @BindView(R.id.trading_BTC_edtexit)
  EditText tradingBTCEdtexit;
  /**
   * 买入数  减
   */
  @BindView(R.id.trading_BTC_reduce)
  ImageView tradingBTCReduce;
  /**
   * 买入数   加
   */
  @BindView(R.id.trading_BTC_add)
  ImageView tradingBTCAdd;
  /**
   * 买入市值
   */
  @BindView(R.id.trading_Buy_value)
  TextView tradingBuyValue;
  /**
   * 买入市值 交易对
   */
  @BindView(R.id.trading_BiBuy_value)
  TextView tradingBiBuyValue;

  @BindView(R.id.btnXD0)
  Button btnXD0;

  @BindView(R.id.btnXD1)
  Button btnXD1;

  @BindView(R.id.btnXD2)
  Button btnXD2;

  @BindView(R.id.btnXD3)
  Button btnXD3;

  @BindView(R.id.btnXD4)
  Button btnXD4;

  //    /**
  //     * 杠杆倍数
  //     */
  //    @BindView(R.id.trading_seekbarWithIntervals)
  //    MagicIndicator tradingSeekbarWithIntervals;
  /**
   * 支付保证金
   */
  @BindView(R.id.trading_Payment_of)
  EditText tradingPaymentOf;
  /**
   * 支付保证金  交易对
   */
  @BindView(R.id.trading_BiPayment_of)
  TextView tradingBiPaymentOf;
  /**
   * 倍数
   */
  @BindView(R.id.trading_Charge_money)
  TextView tradingChargeMoney;
  /**
   * 选择杠杠的倍数
   */
  @BindView(R.id.choice_Charge_money)
  LinearLayout choiceChargeMoney;
  /**
   * 资金划转
   */
  @BindView(R.id.trading_Transfer_of)
  LinearLayout tradingTransferOf;
  /**
   * 止盈
   */
  @BindView(R.id.trading_Check_surplus)
  TextView tradingCheckSurplus;
  /**
   * 止损
   */
  @BindView(R.id.trading_Stop_loss)
  TextView tradingStopLoss;
  /**
   * 可用
   */
  @BindView(R.id.trading_available)
  TextView tradingAvailable;
  /**
   * 买涨
   */
  @BindView(R.id.trading_Buy_up)
  Button tradingBuyUp;
  /**
   * 买跌
   */
  @BindView(R.id.trading_To_buy)
  Button tradingToBuy;
  /**
   * 委托  持仓  结算  撤单
   */
  @BindView(R.id.trading_trading_Warehousing)
  KLineMenuView tradingTradingWarehousing;
  /**
   * 只显示当前交易对
   */
  @BindView(R.id.trading_Show_only)
  CheckBox tradingShowOnly;
  /**
   * 查看更多
   */
  @BindView(R.id.trading_view_more)
  TextView tradingViewMore;
  /**
   * recylerView
   */
  @BindView(R.id.rv_futures_foot)
  RecyclerView rv_futures_foot;

  @BindView(R.id.more_trading_image)
  ImageView moreTradingImage;
  @BindView(R.id.trading_more)
  TextView tradingMore;
  @BindView(R.id.more_index_image)
  ImageView moreIndexImage;
  @BindView(R.id.trading_index)
  TextView tradingIndex;

  @BindView(R.id.kline_operater)
  View klineperater;
  @BindView(R.id.kline_time)
  View klineTime;
  @BindView(R.id.text_view_ma)
  TextView textViewMa;
  @BindView(R.id.text_view_boll)
  TextView textViewBoll;
  @BindView(R.id.linear_layout_master_operater)
  LinearLayout linearLayoutMasterOperater;
  @BindView(R.id.text_view_macd)
  TextView textViewMacd;
  @BindView(R.id.text_view_kdj)
  TextView textViewKdj;
  @BindView(R.id.text_view_rsi)
  TextView textViewRsi;
  @BindView(R.id.text_view_wr)
  TextView textViewWr;
  @BindView(R.id.linear_layout_attached_operater)
  LinearLayout linearLayoutAttachedOperater;
  @BindView(R.id.text_view_one_minute)
  TextView textViewOneMinute;
  @BindView(R.id.text_view_five_minute)
  TextView textViewFiveMinute;
  @BindView(R.id.text_view_half_hour)
  TextView textViewHalfHour;
  @BindView(R.id.text_view_one_week)
  TextView textViewOneWeek;
  @BindView(R.id.text_view_one_mounth)
  TextView textViewOneMounth;
  @BindView(R.id.linear_layout_index_more)
  LinearLayout linearLayoutIndexMore;

  /**
   * empty
   */
  @BindView(R.id.tv_empty)
  TextView tv_empty;
  /**
   * 从第多少条开始
   */
  private int startNo = 0;
  /**
   * 每页多少条数据
   */
  private int endNo = 50000;

  public static String Name = "BTC_USDT";
  public static String Time;

  Unbinder unbinder;

  private int pageSize = 15;
  private int pageNo = 1;
  private int type = 1;  //1持仓 2 结算 3 撤单 4委托

  private KLineChartAdapter<KDataList> adapter;
  private ArrayList<KDataList> kChartBeans = new ArrayList<>();

  private FuturesFootAdapter footAdapter;
  private List<FuturesOrderBean> footBeanList;
  private AlertDialog checkDialog;

  private boolean flage = true;
  private IndexMarketList indexMarketList;

  private BigDecimal delAmount;
  private BigDecimal delNum;

  private int defaultIndex = 2;
  private List<String> tabTitles = Arrays.asList("时分", "1分钟", "15分钟", "1小时");
  private List<String> Triggerorder = Arrays.asList("市价下单", "触发下单");
  private List<String> Warehousing = Arrays.asList("委托", "持仓", "结算", "撤单");
  private List<String> tabParams =
      Arrays.asList("time", "1min", "5min", "15min", "30min", "60min", "1day", "1week", "1mon");
  private List<String> percentTitles = Arrays.asList("25%", "50%", "75%", "100%");

  /**
   * 保证金
   */
  private BigDecimal Bond;
  /**
   * 计算买入市值
   */
  private BigDecimal buyvule;

  /**
   * 用去区分 	交易类型 (1 涨 2 跌)
   */
  private int dealType;

  /**
   * 用去区分   委托方式 1市价 2触发价
   */
  private int delWay = 1;

  /**
   * 用去区分 杠杆倍数
   */
  private int lever = 100;
  /**
   * 可用
   */
  private BigDecimal Available = new BigDecimal(0);
  /**
   *
   */
  private int earnestMoneyType = 1;

  /**
   * 限制最大买入和最小买入
   * minNum :最小买入
   * maxNum :最大买入
   */
  private double minNum = 1;
  private double maxNum = 1000;
  /**
   * quantityRetention int ;数量保留位数
   * priceReservation int 价格保留位数
   */
  private BigDecimal quantityRetention = new BigDecimal(0.00);
  private BigDecimal priceReservation = new BigDecimal(0.00);

  /**
   * 计算涨跌幅
   * newData
   */
  private BigDecimal newData;

  private int rgIndex = 0;
  private BigDecimal profit, profitPrice, loss, lossPrice;  //止盈金额，止盈价格，止损金额，止损价格
  private int profit1, profitPrice1, loss1, lossPrice1; //分别带的小数位数

  private ArrayList<DealPairList> list = new ArrayList<>();
  private ArrayList<dealCoinDTOList> list1 = new ArrayList<>();
  /**
   * 杠杠adapter
   */
  private GearingAdapter gearingAdapter;
  private int mLastCheckedPosition = -1;
  private List<Gearing> gearingList = new ArrayList<>();
  /**
   * 杠杠截取
   */
  private String[] Slever;

  private Vibrator vibrator;

  @Override
  public void onNewParams(@Nullable Bundle param) {
    Name = param.getString(Keys.PARAM_PAIR_DATA);
    tradingCurrencyTv.setText(Name + "");
    //
    getfutureDealPair(Name);

    clearSocket();
    refreshData(null);
    if(tradingBiPaymentOf!=null){
      tradingBiPaymentOf.setText(Name.substring(Name.indexOf("_") + 1, Name.length()));
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();

    //用于查询杠杠倍数,和交易数量和位数
    getfutureDealPairList(Name);
    //指数行情
    //    getIndexMarketList(Name);
    //币种资金详情
    getassetsCoin();

    bonds(type);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    clearSocket();
  }

  private void clearSocket() {

    if (socketSubscriber != null && !socketSubscriber.isDisposed()) {
      socketSubscriber.dispose();
    }
  }

  @Override
  protected void lazyLoad() {

  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    //用于查询杠杠倍数,和交易数量和位数
    getfutureDealPairList(Name);
    net(Time = "1min");

    vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    initKLineView();
    //币种资金详情
    //getassetsCoin();
  }

  /**
   * 获取价格
   */
  @SuppressLint("CheckResult")
  private void getfutureDealPair(String delkeys) {

    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("delkeys", delkeys);
    ApiFactory.getInstance()
        .getIndexMarketList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {

          if (arrayListData.getCode() == 200 && arrayListData.getData() != null) {
            indexMarketList = arrayListData.getData().get(0);
            tradingEdtexit.setText(arrayListData.getData()
                .get(0)
                .getNewPrice()
                .setScale(8, BigDecimal.ROUND_HALF_UP)
                .toString());
            try {
              if (arrayListData.getData().get(0).getNewPrice() != null) {
                tradingValue.setText(arrayListData.getData()
                    .get(0)
                    .getNewPrice()
                    .setScale(3, BigDecimal.ROUND_HALF_UP)
                    .toString());
                tradingBuyUp.setText(arrayListData.getData()
                    .get(0)
                    .getNewPrice()
                    .setScale(3, BigDecimal.ROUND_HALF_UP)
                    .toString() + "买涨");
                tradingToBuy.setText(arrayListData.getData()
                    .get(0)
                    .getNewPrice()
                    .setScale(3, BigDecimal.ROUND_HALF_UP)
                    .toString() + "买跌");
                if (arrayListData.getData()
                    .get(0).getNewPrice() != null && arrayListData.getData()
                    .get(0).getLastPrice() != null) {
                  newData = arrayListData.getData()
                      .get(0)
                      .getNewPrice()
                      .subtract(arrayListData.getData()
                          .get(0).getLastPrice())
                      .divide(arrayListData.getData()
                          .get(0).getLastPrice(), 5, BigDecimal.ROUND_HALF_UP)
                      .multiply(new BigDecimal(100))
                      .setScale(5, BigDecimal.ROUND_HALF_UP);
                  if (newData.compareTo(BigDecimal.ZERO) == 1) {
                    tradingPercentage.setText(
                        "+" + newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                    tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_00a546));
                  } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                    tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_a50000));
                    tradingPercentage.setText(
                        newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                  } else {
                    tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_a50000));
                    tradingPercentage.setText(
                        newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                  }
                } else {
                  tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_00a546));
                  tradingPercentage.setText(
                      newData.setScale(3, BigDecimal.ROUND_HALF_UP).toString() + "%");
                }
              }
            } catch (Exception e) {
              Timber.e(e);
            }
          }
        }, throwable -> {
          Timber.e(throwable);
        });
  }

  /**
   * 用于查询杠杠倍数,和交易数量和位数
   */
  @SuppressLint("CheckResult")
  private void getfutureDealPairList(String name) {

    HashMap<String, String> hashMap = new HashMap<>();
    ApiFactory.getInstance()
        .getfutureDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(new Consumer<Data<ArrayList<DealPairList>>>() {
          @Override
          public void accept(Data<ArrayList<DealPairList>> data) throws Exception {

            if (data.getCode() == 200) {
              list = data.getData() == null ? new ArrayList<>() : data.getData();
              list1.clear();
              for (int i = 0; i < list.size(); i++) {
                list1.addAll(list.get(i).getDealCoinDTOList());
              }
              for (int j = 0; j < list1.size(); j++) {
                if (list1.get(j).getDealPair().equals(name)) {

                  //数量保留位数
                  quantityRetention = new BigDecimal(list1.get(j).getQuantityRetention());
                  //价格保留位数
                  priceReservation = new BigDecimal(list1.get(j).getPriceReservation());
                  //最小交易数量
                  minNum = list1.get(j).getMinNum();
                  //最大交易数量
                  maxNum = list1.get(j).getMaxNum();
                  //杠杠解析
                  Slever = list1.get(j).getLever().split("_");
                  gearingList.clear();
                  for (int k = 0; k < Slever.length; k++) {
                    Gearing gearing = new Gearing();
                    gearing.setLevel(Slever[k]);
                    gearingList.add(gearing);
                  }
                  tradingChargeMoney.setText(
                      gearingList.get(gearingList.size() - 1).getLevel() + "X");
                  lever = Integer.parseInt(gearingList.get(gearingList.size() - 1).getLevel());
                }
              }
            }
          }
        }, throwable -> {

        });
  }

  private void showCheckDialog(FuturesOrderBean dto) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.check_full_stop_dialog, null);

    RadioGroup rg_choose = view.findViewById(R.id.rg_choose);
    View v_amount = view.findViewById(R.id.v_amount);
    View v_price = view.findViewById(R.id.v_price);
    TextView tv_profit_price = view.findViewById(R.id.tv_profit_price);
    ImageView iv_profit_up = view.findViewById(R.id.iv_profit_up);
    EditText et_profit = view.findViewById(R.id.et_profit);
    ImageView iv_profit_down = view.findViewById(R.id.iv_profit_down);
    TextView tv_loss_price = view.findViewById(R.id.tv_loss_price);
    ImageView iv_loss_up = view.findViewById(R.id.iv_loss_up);
    EditText et_loss = view.findViewById(R.id.et_loss);
    ImageView iv_loss_down = view.findViewById(R.id.iv_loss_down);
    Button sure = view.findViewById(R.id.btn_sure);
    Button cancel = view.findViewById(R.id.btn_cancel);

    try {
      profit1 =
          (dto.getTargetProfit() + "").length() - (dto.getTargetProfit() + "").indexOf(".") - 1;
      if (profit1 > dto.getPriceReservation()) {
        profit1 = dto.getPriceReservation();
      }
      profitPrice1 =
          (dto.getTargetProfitPrice() + "").length() - (dto.getTargetProfitPrice() + "").indexOf(
              ".") - 1;
      if (profitPrice1 > dto.getPriceReservation()) {
        profitPrice1 = dto.getPriceReservation();
      }
      loss1 = (dto.getStopLoss() + "").length() - (dto.getStopLoss() + "").indexOf(".") - 1;
      if (loss1 > dto.getPriceReservation()) {
        loss1 = dto.getPriceReservation();
      }
      lossPrice1 =
          (dto.getStopLossPrice() + "").length() - (dto.getStopLossPrice() + "").indexOf(".") - 1;
      if (lossPrice1 > dto.getPriceReservation()) {
        lossPrice1 = dto.getPriceReservation();
      }
    } catch (Exception e) {

    }

    profit = new BigDecimal(CommonUtil.cutNumber(dto.getTargetProfit(), profit1) + "");
    profitPrice =
        new BigDecimal(CommonUtil.cutNumber(dto.getTargetProfitPrice(), profitPrice1) + "");

    loss = new BigDecimal(CommonUtil.cutNumber(dto.getStopLoss(), loss1) + "");
    lossPrice = new BigDecimal(CommonUtil.cutNumber(dto.getStopLossPrice(), lossPrice1) + "");

    tv_profit_price.setText(dto.getCoinCode() + "止盈价格 " + profitPrice); //止盈价格
    et_profit.setText(String.valueOf(profit));
    tv_loss_price.setText(dto.getCoinCode() + "止损价格 " + lossPrice);
    et_loss.setText(String.valueOf(loss));

    rg_choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = group.indexOfChild(group.findViewById(checkedId));
        switch (index) {
          case 0:
            rgIndex = 0;
            tv_profit_price.setText(dto.getCoinCode() + "止盈价格 " + profitPrice);
            et_profit.setText(String.valueOf(profit));
            tv_loss_price.setText(dto.getCoinCode() + "止损价格 " + lossPrice);
            et_loss.setText(String.valueOf(loss));
            v_amount.setVisibility(View.VISIBLE);
            v_price.setVisibility(View.INVISIBLE);
            break;
          case 1:
            rgIndex = 1;
            tv_profit_price.setText(dto.getCoinCode() + "止盈金额 " + profit);
            et_profit.setText(String.valueOf(profitPrice));
            tv_loss_price.setText(dto.getCoinCode() + "止损金额 " + loss);
            et_loss.setText(String.valueOf(lossPrice));
            v_amount.setVisibility(View.INVISIBLE);
            v_price.setVisibility(View.VISIBLE);
            break;
        }
      }
    });

    et_profit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        try {
          String s1 = s.toString();
          if (s1.startsWith(".")) {
            s1 = "0" + s1;
          }
          if (s1.endsWith(".")) {
            s1 += "0";
          }
          if (s1.length() == 0 || s1.endsWith("-")) {
            s1 = "0";
          }
          if (rgIndex == 0) {
            profit = new BigDecimal(s1);
          } else {
            profitPrice = new BigDecimal(s1);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    et_loss.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        try {
          String s1 = s.toString();
          if (s1.startsWith(".")) {
            s1 = "0" + s1;
          }
          if (s1.endsWith(".")) {
            s1 += "0";
          }
          if (s1.length() == 0 || s1.endsWith("-")) {
            s1 = "0";
          }
          if (rgIndex == 0) {
            loss = new BigDecimal(s1);
          } else {
            lossPrice = new BigDecimal(s1);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    iv_profit_up.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (rgIndex == 0) {
          profit = profit.add(new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, profit1))));
          et_profit.setText(String.valueOf(profit));
        } else {
          profitPrice = profitPrice.add(
              new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, profitPrice1))));
          et_profit.setText(String.valueOf(profitPrice));
        }
      }
    });

    iv_profit_down.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (rgIndex == 0) {
          profit =
              profit.subtract(new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, profit1))));
          et_profit.setText(String.valueOf(profit));
        } else {
          profitPrice = profitPrice.subtract(
              new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, profitPrice1))));
          et_profit.setText(String.valueOf(profitPrice));
        }
      }
    });

    iv_loss_up.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (rgIndex == 0) {
          loss = loss.add(new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, loss1))));
          et_loss.setText(String.valueOf(loss));
        } else {
          lossPrice =
              lossPrice.add(new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, lossPrice1))));
          et_loss.setText(String.valueOf(lossPrice));
        }
      }
    });
    iv_loss_down.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (rgIndex == 0) {
          loss = loss.subtract(new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, loss1))));
          et_loss.setText(String.valueOf(loss));
        } else {
          lossPrice = lossPrice.subtract(
              new BigDecimal(1.0).divide(new BigDecimal(Math.pow(10, lossPrice1))));
          et_loss.setText(String.valueOf(lossPrice));
        }
      }
    });

    sure.setOnClickListener(new View.OnClickListener() {
      @SuppressLint("CheckResult")
      @Override
      public void onClick(View v) {

        if (profit.compareTo(new BigDecimal(0.0)) < 0) {
          t("止盈金额需大于0");
          return;
        }
        if (dto.getDealType() == 1) {  //买涨
          if (profitPrice.compareTo(new BigDecimal(dto.getAveAmount())) < 0) {
            t("买涨的止盈价格需大于买入价");
            return;
          }
        } else if (dto.getDealType() == 2) {  //买跌
          if (profitPrice.compareTo(new BigDecimal(dto.getAveAmount())) > 0 ||
              profitPrice.compareTo(new BigDecimal(1.0 / Math.pow(10, dto.getPriceReservation())))
                  < 0) {
            t("买跌的止盈价格需大于0并且小于买入价");
            return;
          }
        }
        if (loss.compareTo(new BigDecimal(0.0)) > 0 ||
            loss.add(new BigDecimal(dto.getEarnestMoney() * 0.8)).compareTo(new BigDecimal(0.0))
                < 0) {
          t("止损金额需小于0且不能亏损超过保证金的80%");
          return;
        }
        if (lossPrice.compareTo(new BigDecimal(0.0)) > 0 ||
            lossPrice.add(new BigDecimal(dto.getEarnestMoney() * 0.8))
                .compareTo(new BigDecimal(0.0)) < 0) {
          t("止损价格需小于0且不能亏损超过保证金的80%");
          return;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        //                indexEntrustId	string	是	委托id
        //                targetProfit	float	是	止盈金额
        //                targetProfitPrice	float	是	止盈价格
        //                stopLoss	float	是	止损金额
        //                stopLossPrice	float	是	止损价格
        hashMap.put("indexEntrustId", dto.getId());
        hashMap.put("targetProfit", profit + "");
        hashMap.put("targetProfitPrice", profitPrice + "");
        hashMap.put("stopLoss", loss + "");
        hashMap.put("stopLossPrice", lossPrice + "");

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        dialog.setCancelable(true);

        RxExtensionKt.filterResult(ApiFactory.getInstance()
            .updateSurplusDamage(kv.decodeString(Constants.TOKEN_ID, ""), hashMap))
            .compose(RxSchedulers.io_main())
            .subscribe(data -> {
              dialog.dismiss();
              if (data.isStauts()) {
                checkDialog.dismiss();
              }
              t(data.getMsg());
            }, throwable -> {
              Timber.e(throwable);
            });
      }
    });

    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkDialog.dismiss();
      }
    });
    checkDialog = new AlertDialog.Builder(getActivity())
        .setView(view)
        .setCancelable(true)
        .show();
  }

  /**
   * 币种资金详情
   */
  @SuppressLint("CheckResult")
  private void getassetsCoin() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("coinCode", Name.substring(Name.indexOf("_") + 1, Name.length()));

    ApiFactory.getInstance()
        .getassetsCoin(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {

          if (arrayListData.getCode() == 200) {
            if (arrayListData.getData().getUsableMoney() != null) {

              Available =
                  arrayListData.getData().getUsableMoney().setScale(3, BigDecimal.ROUND_HALF_UP);
              tradingAvailable.setText("可用" + arrayListData.getData()
                  .getUsableMoney()
                  .setScale(3, BigDecimal.ROUND_HALF_UP) + arrayListData.getData().getCoinCode());
            }
          }
        }, throwable -> {
          Timber.e(throwable);
        });
  }

  WebSocketSubscriber socketSubscriber = null;
  private long lastTime = 0L;

  private WebSocketSubscriber genKlineSocket() {
    return new WebSocketSubscriber() {

      @SuppressLint("SetTextI18n")
      @Override
      protected void onMessage(String data) {
        if (TextUtils.isEmpty(data)) {
          return;
        }
        try {
          JSONObject obj = new JSONObject(data);
          String kline = obj.optString("datatype", "");

          if (!TextUtils.isEmpty(kline)) {
            KDataList kdata = new Gson().fromJson(obj.optString("data", ""), KDataList.class);
            try {
              if (kdata.getClosingPrice() != null) {

                KDataList kk = kChartBeans.get(kChartBeans.size() - 1);
                if (kdata.getClosingPrice() >= kk.getClosingPrice()) {
                  kdata.setFloorPrice(kk.getClosingPrice());
                } else {
                  kdata.setTopPrice(kk.getClosingPrice());
                }
                kdata.setkTime(kdata.getkTime());
                long curTime = System.currentTimeMillis();

                if (Time.equals("time")) {
                  adapter.addLast(kdata);
                } else if (Time.equals("1min")) {
                  if (curTime-lastTime >= 1000*60) {
                    lastTime = curTime;
                    adapter.addLast(kdata);
                  }
                  adapter.changeItem(adapter.getCount() - 1, kdata);
                } else if (Time.equals("5min")) {
                  if (curTime-lastTime >= 1000*60*5) {
                    lastTime = curTime;
                    adapter.addLast(kdata);
                  }
                  adapter.changeItem(adapter.getCount() - 1, kdata);
                } else if (Time.equals("15min")) {
                  if (curTime-lastTime >= 1000*60*15) {
                    lastTime = curTime;
                    adapter.addLast(kdata);
                  }
                  adapter.changeItem(adapter.getCount() - 1, kdata);
                } else if (Time.equals("30min")) {
                  if (curTime-lastTime >= 1000*60*30) {
                    lastTime = curTime;
                    adapter.addLast(kdata);
                  }
                  adapter.changeItem(adapter.getCount() - 1, kdata);
                } else if (Time.equals("60min")) {
                  if (curTime-lastTime >= 1000*60*60) {
                    lastTime = curTime;
                    adapter.addLast(kdata);
                  }
                  adapter.changeItem(adapter.getCount() - 1, kdata);
                } else if (Time.equals("1day")) {
                  adapter.addLast(kdata);
                } else if (Time.equals("1week")) {
                  adapter.addLast(kdata);
                } else if (Time.equals("1mon")) {
                  adapter.addLast(kdata);
                }

                chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_NORMAL_WITH_LAST);
              }
            } catch (Exception e) {
              Log.e(TAG, "onMessage: " + e.toString());
            }
          } else {
            IndexMarketList imdata = new Gson().fromJson(data, IndexMarketList.class);
            footAdapter.setSocketNewData(imdata);
            try {
              tradingValue.setText(imdata
                  .getNewPrice()
                  .setScale(3, BigDecimal.ROUND_HALF_UP)
                  .toString());
              tradingBuyUp.setText(imdata
                  .getNewPrice()
                  .setScale(3, BigDecimal.ROUND_HALF_UP)
                  .toString() + "买涨");
              tradingToBuy.setText(imdata
                  .getNewPrice()
                  .setScale(3, BigDecimal.ROUND_HALF_UP)
                  .toString() + "买跌");
              if (imdata.getNewPrice() != null && imdata.getLastPrice() != null) {

                newData = imdata.getNewPrice()
                    .subtract(imdata.getLastPrice())
                    .divide(imdata.getLastPrice(), 5, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100))
                    .setScale(5, BigDecimal.ROUND_HALF_UP);
                if (newData.compareTo(BigDecimal.ZERO) > 0) {
                  tradingPercentage.setText(
                      "+" + newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                  tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_00a546));
                } else if (newData.compareTo(BigDecimal.ZERO) < 0) {
                  tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_a50000));
                  tradingPercentage.setText(
                      newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                } else {
                  tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_a50000));
                  tradingPercentage.setText(
                      newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                }
              } else {
                tradingPercentage.setTextColor(CommonUtil.getColor(R.color.color_00a546));
                tradingPercentage.setText(
                    newData.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
              }
            } catch (Exception e) {
              Log.e(TAG, "ws 异常: " + e.toString());
            }
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override
      protected void onReconnect() {

      }
    };
  }

  /**
   *
   */
  @SuppressLint("CheckResult")
  private void getIndexMarketList(String delkeys) {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("delkeys", delkeys);
    ApiFactory.getInstance()
        .getIndexMarketList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {

          if (arrayListData.getCode() == 200 && arrayListData.getData() != null) {

            if (footAdapter != null) {
              for (int i = 0; i < arrayListData.getData().size(); i++) {
                footAdapter.setNewData(arrayListData.getData().get(i));
              }
              footAdapter.notifyDataSetChanged();
            }
          }
        }, throwable -> {
          Timber.e(throwable);
        });
  }

  private void initKLineView() {

    TextView loadingView = new TextView(getActivity());
    loadingView.setText("正在加载...");
    adapter = new KLineChartAdapter();

    //设置数据适配器
    chartView.setAdapter(adapter)
        //设置开场动画
        .setAnimLoadData(false)
        ////设置每个点的宽度
        //.setChartItemWidth(50)
        ////设置蜡烛宽度
        //.setCandleWidth(30)
        //设置十字线相交圆点半径
        .setSelectedPointRadius(20)
        //设置十字线交点小圆颜色
        .setSelectedPointColor(Color.RED)
        //添加日期格式化,可动态修改
        .setDateTimeFormatter(new DateFormatter() {
          @Override
          public String format(Date date) {
            return DateUtil.HHMMFormat.format(date);
          }
        })
        //网格列
        .setGridColumns(5)
        //网格行
        .setGridRows(5)
        .setLogoAlpha(100)
        //左滑超出宽度
        .setOverScrollRange(getActivity().getWindowManager().getDefaultDisplay().getWidth() / 5)
        .setCandleSolid(false)
        .setOnSelectedChangedListener(new BaseKLineChartView.OnSelectedChangedListener() {
          @RequiresApi(api = Build.VERSION_CODES.O)
          @Override
          public void onSelectedChanged(BaseKLineChartView view, int index, float... values) {
            vibrator.vibrate(VibrationEffect.createOneShot(defaultIndex, 10));
          }
        })
        //滑动边f界监听(可能重复调用)
        .setSlidListener(new SlidListener() {
          @Override
          public void onSlidLeft() {

          }

          @Override
          public void onSlidRight() {

          }
        })
        //Y值精度格式化(可重复设置)
        .setValueFormatter(new ValueFormatter() {
          @Override
          public String format(float value) {
            return String.format("%.03f", value);
          }
        })
        //成交量格式化
        .setVolFormatter(new ValueFormatter() {
          @Override
          public String format(float value) {
            return String.format("%.03f", value);
          }
        })
        //.setBetterX(true)
        .setBetterSelectedX(true)
        //显示loading
        .showLoading();
    //chartView.getAdapter().setDataTools();

    //chartView.setScaleXMax(1);
    //chartView.setScaleXMin(0.5f);
  }

  @SuppressLint("CheckResult")
  private void net(String time) {
    clearSocket();
    HashMap<String, String> hashMap = new HashMap<>();
    //交易对
    hashMap.put("dealPair", Name);
    //K线类型
    hashMap.put("ktype", time);
    //开始条数
    hashMap.put("startNo", String.valueOf(startNo));
    //结束条数
    hashMap.put("endNo", String.valueOf(endNo));

    ApiFactory.getInstance()
        .getKDataList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          if (data.getData() != null) {
            //初始化控件加载数据
            Log.e(TAG, "初始化控件加载数据: " + data.getData().toString());
            kChartBeans.clear();
            kChartBeans.addAll(data.getData());
            initData(kChartBeans);
            chartView.hideSelectData();
          } else {

          }
        }, throwable -> {
          Timber.e(throwable);
        });
  }

  /**
   * 赋值K线
   */
  private void initData(ArrayList<KDataList> kDataList) {

    Collections.sort(kDataList, new Comparator<KDataList>() {
      @Override public int compare(KDataList o1, KDataList o2) {
        Date date1 = new Date(o1.getkTime());
        Date date2 = new Date(o2.getkTime());
        // 对日期字段进行升序，如果欲降序可采用after方法
        if (date1.after(date2)) {
          return 1;
        }
        return -1;
      }
    });
    adapter.resetData(kDataList);
    chartView.setIndexDraw(Status.IndexStatus.MACD);
    chartView.hideLoading();
    new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                  initSocket();
                                }
                              }

        , 3000);
  }

  /**
   * ws：RxWebSocket
   * <p>
   * RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "futurewebsocket/" + Name + "_INDEX_PRICE")
   */
  private void initSocket() {
    clearSocket();
    //ws://www.eex.la/futurewebsocket/BTC_USDT_PRICE_ANDROID
    socketSubscriber = genKlineSocket();
    RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "futurewebsocket/" + Name + "_PRICE_ANDROID")
        .compose(RxSchedulers.io_main())
        .subscribe(socketSubscriber);
  }

  TextWatcher watcher1 = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      earnestMoneyType = 2;
    }

    @Override
    public void afterTextChanged(Editable s) {
      //tradingEdtexit.removeTextChangedListener(watcher1);
      tradingBTCEdtexit.removeTextChangedListener(watcher2);
      tradingPaymentOf.removeTextChangedListener(watcher3);

      /**
       *
       * 保证金  = 个数 * 买入价格 / 杠杠倍数
       */
      try {
        if (indexMarketList.getNewPrice() != null) {

          Bond = new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).multiply(
              new BigDecimal(tradingEdtexit.getText().toString().trim())
                  .divide(new BigDecimal(lever), 8, BigDecimal.ROUND_HALF_DOWN));

          tradingPaymentOf.setText(
              Bond.setScale(priceReservation.intValue(), BigDecimal.ROUND_DOWN).toString());

          tradingBiBuyValue.setText(" ≈CNY");

          /**
           *
           * 止盈 = 保证金  *5
           */
          tradingCheckSurplus.setText("止盈:" + Bond.multiply(new BigDecimal(5))
              .setScale(3, BigDecimal.ROUND_HALF_DOWN)
              .toString());

          /**
           *
           * 止损 = 保证金  *0.8
           */
          tradingStopLoss.setText("止损:" + Bond.multiply(new BigDecimal(0.8))
              .setScale(3, BigDecimal.ROUND_HALF_DOWN)
              .toString());
          /**
           *
           * 买入市值  = 保证金 * cny
           * 算保证金*那个cny
           */
          buyvule = Bond.multiply(indexMarketList.getCny());
          tradingBuyValue.setText(
              buyvule.setScale(priceReservation.intValue(), BigDecimal.ROUND_HALF_DOWN)
                  .toString());
        }
      } catch (Exception e) {

      }
      //tradingEdtexit.addTextChangedListener(watcher1);
      tradingBTCEdtexit.addTextChangedListener(watcher2);
      tradingPaymentOf.addTextChangedListener(watcher3);
    }
  };
  TextWatcher watcher2 = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      earnestMoneyType = 1;
    }

    @Override
    public void afterTextChanged(Editable s) {
      tradingEdtexit.removeTextChangedListener(watcher1);
      //tradingBTCEdtexit.removeTextChangedListener(watcher2);
      tradingPaymentOf.removeTextChangedListener(watcher3);
      try {
        if (indexMarketList.getNewPrice() != null) {

          if (delWay==1) {
            //tradingBTCEdtexit.getText().toString().trim())
            Bond = new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).multiply(
                indexMarketList.getNewPrice())
                .divide(new BigDecimal(lever), 8, BigDecimal.ROUND_HALF_DOWN);

            tradingPaymentOf.setText(
                Bond.setScale(priceReservation.intValue(), BigDecimal.ROUND_DOWN).toString());
          }else if (delWay==2){
            //tradingBTCEdtexit.getText().toString().trim())
            Bond = new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).multiply(
                new BigDecimal(tradingEdtexit.getText().toString().trim())
                    .divide(new BigDecimal(lever), 8, BigDecimal.ROUND_HALF_DOWN));

            tradingPaymentOf.setText(
                Bond.setScale(priceReservation.intValue(), BigDecimal.ROUND_DOWN).toString());
          }
          tradingBiBuyValue.setText(" ≈CNY");

          earnestMoneyType = 1;

          /**
           *
           * 止盈 = 保证金  *5
           */
          tradingCheckSurplus.setText("止盈:" + Bond.multiply(new BigDecimal(5))
              .setScale(3, BigDecimal.ROUND_HALF_DOWN)
              .toString());

          /**
           *
           * 止损 = 保证金  *0.8
           */
          tradingStopLoss.setText("止损:" + Bond.multiply(new BigDecimal(0.8))
              .setScale(3, BigDecimal.ROUND_HALF_DOWN)
              .toString());
          /**
           *
           * 买入市值  = 保证金 * cny
           * 算保证金*那个cny
           */
          buyvule = Bond.multiply(indexMarketList.getCny());
          tradingBuyValue.setText(
              buyvule.setScale(priceReservation.intValue(), BigDecimal.ROUND_HALF_DOWN)
                  .toString());
        }
      } catch (Exception e) {

      }
      tradingEdtexit.addTextChangedListener(watcher1);
      //tradingBTCEdtexit.addTextChangedListener(watcher2);
      tradingPaymentOf.addTextChangedListener(watcher3);
    }
  };
  TextWatcher watcher3 = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      earnestMoneyType = 2;
    }

    @Override
    public void afterTextChanged(Editable s) {
      tradingEdtexit.removeTextChangedListener(watcher1);
      tradingBTCEdtexit.removeTextChangedListener(watcher2);
      tradingPaymentOf.removeTextChangedListener(watcher3);



      /**
       *
       * 保证金  = 个数 * 买入价格 / 杠杠倍数
       */
      try {
        if (indexMarketList.getNewPrice() != null) {


          if (delWay ==1){

            //tradingBTCEdtexit.getText().toString().trim())
            Bond = new BigDecimal(tradingPaymentOf.getText().toString().trim()).setScale(
                quantityRetention.intValue(), BigDecimal.ROUND_HALF_DOWN);
            BigDecimal number = Bond.multiply(new BigDecimal(lever))
                .divide(indexMarketList.getNewPrice(), 8, BigDecimal.ROUND_HALF_DOWN)
                .setScale(3, BigDecimal.ROUND_HALF_DOWN);
            tradingBTCEdtexit.setText(number.toString());
          }else if (delWay ==2){
            Bond = new BigDecimal(tradingPaymentOf.getText().toString().trim()).setScale(
                quantityRetention.intValue(), BigDecimal.ROUND_HALF_DOWN);
            BigDecimal number = Bond.multiply(new BigDecimal(lever))
                .divide(new BigDecimal(tradingEdtexit.getText().toString().trim()), 8, BigDecimal.ROUND_HALF_DOWN)
                .setScale(3, BigDecimal.ROUND_HALF_DOWN);
            tradingBTCEdtexit.setText(number.toString());
            tradingBTCEdtexit.setText(number.toString());
          }

          tradingPaymentOf.setText(Bond.setScale(priceReservation.intValue(), BigDecimal.ROUND_DOWN).toString());
          tradingBiBuyValue.setText(" ≈CNY");

          /**
           *
           * 止盈 = 保证金  *5
           */
          tradingCheckSurplus.setText("止盈:" + Bond.multiply(new BigDecimal(5))
              .setScale(3, BigDecimal.ROUND_HALF_DOWN)
              .toString());

          /**
           *
           * 止损 = 保证金  *0.8
           */
          tradingStopLoss.setText("止损:" + Bond.multiply(new BigDecimal(0.8))
              .setScale(3, BigDecimal.ROUND_HALF_DOWN)
              .toString());
          /**
           *
           * 买入市值  = 保证金 * cny
           * 算保证金*那个cny
           */
          buyvule = Bond.multiply(indexMarketList.getCny());
          tradingBuyValue.setText(buyvule.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
        }
      } catch (Exception e) {

      }
      tradingEdtexit.addTextChangedListener(watcher1);
      tradingBTCEdtexit.addTextChangedListener(watcher2);
      tradingPaymentOf.addTextChangedListener(watcher3);
    }
  };

  @Override
  protected void initUiAndListener() {
    chartView.setVolShowState(!chartView.getVolShowState());
    /**
     * 时分秒 分钟  小时 天 周 月
     * 默认 为1分钟
     * defaultIndex ==0；时分
     * defaultIndex ==1；1分钟
     * defaultIndex ==1；5分钟
     * defaultIndex ==1；15分钟
     * defaultIndex ==1；30分钟
     * defaultIndex ==1；1小时
     * defaultIndex ==1；1天
     * defaultIndex ==1；1周
     * defaultIndex ==1；1月
     */
    tradingAtthe.setData(tabTitles, 1);
    tradingAtthe.setOnTabSelectListener(new KLineMenuView.OnTabSelectListener() {
      @Override
      public void onTabClick(int position) {
        defaultIndex = position;

        //时分
        if (position == 0) {
          chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_CLOSE_WITH_LAST);
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.TIME_LINE);
          //1分钟
        } else if (position == 1) {
          Time = "1min";
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.K_LINE);
        } else if (position == 3) {
          Time = "15min";
          chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_NORMAL_WITH_LAST);
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.K_LINE);
        } else if (position == 5) {
          Time = "60min";
          chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_NORMAL_WITH_LAST);
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.K_LINE);
        }
        net(Time);
      }
    });

    /**
     * 市价下单 和  触发下单
     */
    tradingPriceOrder.setData(Triggerorder, 0);
    tradingPriceOrder.setOnTabSelectListener(new KLineMenuView.OnTabSelectListener() {
      @Override
      public void onTabClick(int position) {
        defaultIndex = position;
        if (position == 1) {
          delWay = 2;
          linearAdd.setVisibility(View.VISIBLE);
        } else if (position == 0) {
          delWay = 1;
          linearAdd.setVisibility(View.GONE);
        }
      }
    });

    /**
     * 委托  持仓  结算  撤单
     *
     *
     */
    tradingTradingWarehousing.setData(Warehousing, 1);
    tradingTradingWarehousing.setOnTabSelectListener(new KLineMenuView.OnTabSelectListener() {
      @Override
      public void onTabClick(int position) {
        //显示不同的list数据
        if (position == 0) {
          type = 4;
        } else {
          type = position;
        }
        bonds(type);
      }
    });

    //触发下单->输入
    tradingEdtexit.addTextChangedListener(watcher1);
    //买入BTC个数
    tradingBTCEdtexit.addTextChangedListener(watcher2);
    //保证金
    tradingPaymentOf.addTextChangedListener(watcher3);

    footBeanList = new ArrayList();
    footAdapter = new FuturesFootAdapter(getActivity(), footBeanList);
    footAdapter.setCheckStopFull(new FuturesFootAdapter.CheckStopFull() {
      @Override
      public void checkSF(int pos) {
        showCheckDialog(footBeanList.get(pos));
      }

      @SuppressLint("CheckResult")
      @Override
      public void overNight(int pos) {
        FuturesOrderBean dto = footBeanList.get(pos);
        RxExtensionKt.filterResult(ApiFactory.getInstance(FuturesOrderApi.class)
            .overNight(kv.decodeString(Constants.TOKEN_ID, ""),
                dto.getId(), dto.getCoinCode(), dto.getPricingCoin()))
            .compose(RxSchedulers.io_main())
            .subscribe(data -> {
              if (data.isStauts()) {
                footAdapter.notifyItemChanged(pos);
              }
              t(data.getMsg());
            }, throwable -> {
              t("网络异常");
            });
      }

      @SuppressLint("CheckResult")
      @Override
      public void clostOut(int pos) {
        try {
          FuturesOrderBean dto = footBeanList.get(pos);
          RxExtensionKt.filterResult(ApiFactory.getInstance(FuturesOrderApi.class)
              .closeOut(kv.decodeString(Constants.TOKEN_ID, ""),
                  dto.getDelAmount() + "",
                  dto.getId(), "1"))
              .compose(RxSchedulers.io_main())
              .subscribe(data -> {
                if (data.isStauts()) {
                  dto.setIndex(2);
                  footBeanList.remove(pos);
                  footAdapter.notifyItemRemoved(pos);
                }
                t(data.getMsg());
              }, throwable -> {
                t("网络异常");
              });
        } catch (Exception e) {

        }
      }

      @SuppressLint("CheckResult")
      @Override
      public void cancel(int pos) {
        try {
          FuturesOrderBean dto = footBeanList.get(pos);
          RxExtensionKt.filterResult(ApiFactory.getInstance(FuturesOrderApi.class)
              .cancelFuture(kv.decodeString(Constants.TOKEN_ID, ""),
                  dto.getId(), dto.getCoinCode(), dto.getPricingCoin(), dto.getDelWay() + ""))
              .compose(RxSchedulers.io_main())
              .subscribe(data -> {
                if (data.isStauts()) {
                  footBeanList.remove(pos);
                  footAdapter.notifyItemRemoved(pos);
                }
                t(data.getMsg());
              }, throwable -> {
                t("网络异常");
              });
        } catch (Exception e) {

        }
      }
    });

    rv_futures_foot.setAdapter(footAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setAutoMeasureEnabled(true);
    rv_futures_foot.setLayoutManager(layoutManager);

    tradingShowOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {  //只显示当前交易对
          List<FuturesOrderBean> list = new ArrayList<>();
          for (int i = 0; i < footBeanList.size(); i++) {
            if (footBeanList.get(i).getDelkey().equals(Name)) {
              list.add(footBeanList.get(i));
            }
          }
          footAdapter = new FuturesFootAdapter(getActivity(), list);
          footAdapter.notifyDataSetChanged();
        } else {
          footAdapter = new FuturesFootAdapter(getActivity(), footBeanList);
          footAdapter.notifyDataSetChanged();
        }
      }
    });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_trading_swipe_refresh;
  }

  /**
   * 持仓结算撤销列表
   */
  @SuppressLint("CheckResult")
  private void bonds(int type) {
    if (TextUtils.isEmpty(kv.decodeString("tokenId"))) {
      return;
    }

    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("pageSize", String.valueOf(pageSize));
    hashMap.put("pageNo", String.valueOf(pageNo));
    hashMap.put("type", String.valueOf(type));
    hashMap.put("delKey", "");

    footBeanList.clear();

    ApiFactory.getInstance()
        .bonds1(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getCode() == 200) {
            if (data.getData().getResultList() != null
                && data.getData().getResultList().size() > 0) {
              footBeanList.addAll(data.getData().getResultList());
              for (FuturesOrderBean dto : footBeanList) {
                dto.setIndex(type);
              }
              footAdapter.notifyDataSetChanged();
              rv_futures_foot.setVisibility(View.VISIBLE);
              tv_empty.setVisibility(View.GONE);
              String delkeys = "";
              for (int i = 0; i < footBeanList.size(); i++) {
                if (!delkeys.contains(footBeanList.get(i).getDelkey())) {
                  delkeys += footBeanList.get(i).getDelkey() + ",";
                }
              }
              if (!TextUtils.isEmpty(delkeys)) {
                getIndexMarketList(delkeys.substring(0, delkeys.length() - 1));
              }
            } else {
              rv_futures_foot.setVisibility(View.GONE);
              tv_empty.setVisibility(View.VISIBLE);
            }
          } else {
            rv_futures_foot.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
            t(data.getMsg());
          }
        }, throwable -> {
          Timber.e(throwable);
        });
  }

  /**
   * 选择杠杆倍数
   */
  private void gearing() {
    View popView = View.inflate(getActivity(), R.layout.popupwindow_gearing, null);

    RecyclerView recyclerView = popView.findViewById(R.id.recyclerView);

    //获取屏幕宽高
    int weight = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels;

    final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
    popupWindow.setAnimationStyle(R.style.anim_popup_dir);
    //这里必须设置为true才能点击区域外或者消失
    popupWindow.setFocusable(true);
    //这个控制PopupWindow内部控件的点击事件
    popupWindow.setTouchable(true);
    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    popupWindow.setOutsideTouchable(true);
    popupWindow.update();

    gearingAdapter = new GearingAdapter(gearingList);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setAdapter(gearingAdapter);
    setItemChecked(gearingList.size() - 1);
    recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
      @Override
      public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        tradingEdtexit.removeTextChangedListener(watcher1);
        tradingBTCEdtexit.removeTextChangedListener(watcher2);
        tradingPaymentOf.removeTextChangedListener(watcher3);
        setItemChecked(position);
        tradingChargeMoney.setText(gearingList.get(position).getLevel() + "X");
        lever = Integer.parseInt(gearingList.get(position).getLevel());

        try {
          /**
           * 杠杠倍数跟着保证金变,数量不变
           */
          BigDecimal number =
              new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).multiply(
                  indexMarketList.getNewPrice()
                      .divide(new BigDecimal(gearingList.get(position).getLevel()), 8,
                          BigDecimal.ROUND_HALF_DOWN)
                      .setScale(3, BigDecimal.ROUND_HALF_DOWN));

          tradingPaymentOf.setText(number.toString());

          /**
           *
           * 止盈 = 保证金  *5
           */
          tradingCheckSurplus.setText("止盈:" + Bond.multiply(new BigDecimal(5))
              .setScale(3, BigDecimal.ROUND_HALF_UP)
              .toString());

          /**
           *
           * 止损 = 保证金  *0.8
           */
          tradingStopLoss.setText("止损:" + Bond.multiply(new BigDecimal(0.8))
              .setScale(3, BigDecimal.ROUND_HALF_UP)
              .toString());
          /**
           *
           * 买入市值  = 保证金 * cny
           * 算保证金*那个cny
           */
          buyvule = Bond.multiply(indexMarketList.getCny());
          tradingBuyValue.setText(buyvule.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
        } catch (Exception e) {

        }

        popupWindow.dismiss();

        tradingEdtexit.addTextChangedListener(watcher1);
        tradingBTCEdtexit.addTextChangedListener(watcher2);
        tradingPaymentOf.addTextChangedListener(watcher3);
      }
    });

    popupWindow.dismiss();

    //popupWindow出现屏幕变为半透明
    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
    lp.alpha = 1.0f;
    getActivity().getWindow().setAttributes(lp);
    popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 50);
    BackgroudAlpha((float) 0.5);
    popupWindow.setOnDismissListener(new FuturesTradingFragment.popupwindowdismisslistener());
  }

  private void setItemChecked(int position) {

    if (mLastCheckedPosition == position) {
      return;
    }
    gearingAdapter.mBooleanArray.put(position, true);
    if (mLastCheckedPosition > -1) {
      gearingAdapter.mBooleanArray.put(mLastCheckedPosition, false);
      gearingAdapter.notifyItemChanged(mLastCheckedPosition);
    }
    gearingAdapter.notifyDataSetChanged();
    mLastCheckedPosition = position;
  }

  private void BackgroudAlpha(float alpha) {
    WindowManager.LayoutParams l = getActivity().getWindow().getAttributes();
    l.alpha = alpha;
    getActivity().getWindow().setAttributes(l);
  }

  @OnClick({
      R.id.trading_currency_tv, R.id.trading_currency_image, R.id.trading_full_screen_image,
      R.id.trading_value,
      R.id.trading_percentage, R.id.kLineChartView,
      R.id.trading_atthe, R.id.trading_price_order,
      R.id.text_view_ma, R.id.text_view_boll,
      R.id.trading_more, R.id.trading_index,
      R.id.linear_layout_master_operater, R.id.text_view_macd, R.id.text_view_kdj,
      R.id.text_view_rsi,
      R.id.text_view_wr, R.id.linear_layout_attached_operater, R.id.text_view_one_minute,
      R.id.text_view_five_minute, R.id.text_view_half_hour, R.id.text_view_one_week,
      R.id.text_view_one_mounth, R.id.linear_layout_index_more,
      R.id.trading_finger_guide, R.id.trading_edtexit, R.id.trading_reduce, R.id.trading_add,
      R.id.linear_add,
      R.id.trading_BTC_edtexit, R.id.trading_BTC_reduce, R.id.trading_BTC_add,
      R.id.trading_Buy_value,
      R.id.trading_BiBuy_value,
      R.id.btnXD0, R.id.btnXD1, R.id.btnXD2, R.id.btnXD3, R.id.btnXD4,
      R.id.trading_Payment_of, R.id.trading_BiPayment_of, R.id.trading_Charge_money,
      R.id.choice_Charge_money,
      R.id.trading_Transfer_of, R.id.trading_Check_surplus, R.id.trading_Stop_loss,
      R.id.trading_available,
      R.id.trading_Buy_up, R.id.trading_To_buy, R.id.trading_trading_Warehousing,
      R.id.trading_view_more
  })
  public void onViewClicked(View view) {
    Bundle bundle = new Bundle();
    switch (view.getId()) {
      //交易对
      case R.id.trading_currency_tv:
      case R.id.trading_currency_image:
        bundle = new Bundle();
        if (getActivity() instanceof MainActivity) {
          Activity activity = (MainActivity) getActivity();
          bundle.putInt(Keys.TRANS_SELECT, 2);
          ((MainContract.View) activity).selectTab(1, null);
        }
        break;
      //全屏K线
      case R.id.trading_full_screen_image:
        intent.setClass(getContext(), FuturesTradingActivity.class);
        intent.putExtra("Name", Name);
        startActivity(intent);
        break;
      //数值
      case R.id.trading_value:

        break;

      case R.id.trading_more:

        if (flage == true) {
          klineTime.setVisibility(View.VISIBLE);
          klineperater.setVisibility(View.GONE);
          tradingMore.setTextColor(CommonUtil.getColor(R.color.text_color));
          tradingIndex.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
          moreTradingImage.setBackground(getResources().getDrawable(R.mipmap.trading_more));
          flage = false;
        } else {
          klineTime.setVisibility(View.GONE);
          tradingMore.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
          moreTradingImage.setBackground(getResources().getDrawable(R.mipmap.more_trading));
          flage = true;
        }
        break;
      case R.id.trading_index:

        if (flage == true) {
          klineperater.setVisibility(View.VISIBLE);
          linearLayoutMasterOperater.setVisibility(View.VISIBLE);
          linearLayoutAttachedOperater.setVisibility(View.VISIBLE);
          klineTime.setVisibility(View.GONE);
          tradingMore.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
          moreIndexImage.setBackground(getResources().getDrawable(R.mipmap.trading_more));
          tradingIndex.setTextColor(CommonUtil.getColor(R.color.text_color));
          flage = false;
        } else {
          klineperater.setVisibility(View.GONE);
          tradingIndex.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
          moreIndexImage.setBackground(getResources().getDrawable(R.mipmap.more_trading));
          flage = true;
        }

        break;
      case R.id.text_view_ma:

        chartView.hideSelectData();
        chartView.changeMainDrawType(Status.MainStatus.MA);
        textViewBoll.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMacd.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewKdj.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewRsi.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewWr.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMa.setTextColor(CommonUtil.getColor(R.color.text_color));

        break;
      case R.id.text_view_boll:

        chartView.hideSelectData();
        chartView.changeMainDrawType(Status.MainStatus.BOLL);
        textViewMa.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMacd.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewKdj.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewRsi.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewWr.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewBoll.setTextColor(CommonUtil.getColor(R.color.text_color));

        break;
      case R.id.linear_layout_master_operater:
        break;
      case R.id.text_view_macd:

        chartView.hideSelectData();
        chartView.setIndexDraw(Status.IndexStatus.MACD);
        textViewMa.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewBoll.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewKdj.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewRsi.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewWr.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMacd.setTextColor(CommonUtil.getColor(R.color.text_color));

        break;
      case R.id.text_view_kdj:

        chartView.hideSelectData();
        chartView.setIndexDraw(Status.IndexStatus.KDJ);
        textViewMa.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewBoll.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMacd.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewRsi.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewWr.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewKdj.setTextColor(CommonUtil.getColor(R.color.text_color));

        break;
      case R.id.text_view_rsi:

        chartView.hideSelectData();
        chartView.setIndexDraw(Status.IndexStatus.RSI);
        textViewMa.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewBoll.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMacd.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewKdj.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewWr.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewRsi.setTextColor(CommonUtil.getColor(R.color.text_color));

        break;
      case R.id.text_view_wr:

        chartView.hideSelectData();
        chartView.setIndexDraw(Status.IndexStatus.WR);
        textViewMa.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewBoll.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewMacd.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewKdj.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewRsi.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        textViewWr.setTextColor(CommonUtil.getColor(R.color.text_color));

        break;
      case R.id.linear_layout_attached_operater:
        break;

      case R.id.text_view_one_minute:
        net(Time = "5min");
        chartView.hideSelectData();
        chartView.setKlineState(Status.KlineStatus.K_LINE);
        textViewOneMinute.setTextColor(CommonUtil.getColor(R.color.text_color));
        textViewHalfHour.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneWeek.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewFiveMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneMounth.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        break;
      case R.id.text_view_five_minute:
        net(Time = "1day");
        chartView.hideSelectData();
        chartView.setKlineState(Status.KlineStatus.K_LINE);
        textViewFiveMinute.setTextColor(CommonUtil.getColor(R.color.text_color));
        textViewOneMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewHalfHour.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneWeek.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneMounth.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        break;
      case R.id.text_view_half_hour:

        net(Time = "1week");
        chartView.hideSelectData();
        chartView.setKlineState(Status.KlineStatus.K_LINE);
        textViewHalfHour.setTextColor(CommonUtil.getColor(R.color.text_color));
        textViewOneMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewFiveMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneWeek.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneMounth.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        break;
      case R.id.text_view_one_week:
        net(Time = "1mon");
        chartView.hideSelectData();
        chartView.setKlineState(Status.KlineStatus.K_LINE);

        textViewOneWeek.setTextColor(CommonUtil.getColor(R.color.text_color));
        textViewOneMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewFiveMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewHalfHour.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneMounth.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        break;
      case R.id.text_view_one_mounth:

        net(Time = "1year");
        chartView.hideSelectData();
        chartView.setKlineState(Status.KlineStatus.K_LINE);
        textViewOneMounth.setTextColor(CommonUtil.getColor(R.color.text_color));
        textViewOneMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewFiveMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewHalfHour.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneWeek.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        break;
      case R.id.linear_layout_index_more:
        //数值
      case R.id.trading_percentage:
        break;
      //K线
      case R.id.kLineChartView:
        break;
      //选择时间
      case R.id.trading_atthe:
        //net(Time = "time");
        break;
      //选择市价下单  触发下单
      case R.id.trading_price_order:

        break;
      //新手指南
      case R.id.trading_finger_guide:
        //                intent.setClass(getContext(), TradingFingerGuideActivity.class);
        //                startActivity(intent);
        break;
      //输入触发价
      case R.id.trading_edtexit:
        break;
      //触发价 减
      case R.id.trading_reduce:
        earnestMoneyType = 2;
        if (!tradingEdtexit.getText().toString().trim().equals("")) {
          if (new BigDecimal(tradingEdtexit.getText().toString().trim()).compareTo(
              BigDecimal.ZERO)
              == 0) {
            tradingEdtexit.setText("0");
          }
          if (new BigDecimal(tradingEdtexit.getText().toString().trim()).compareTo(
              BigDecimal.ZERO) == 1) {
            BigDecimal price = new BigDecimal(tradingEdtexit.getText().toString().trim());
            BigDecimal subPrice = price.subtract(
                new BigDecimal(XJPriceSubMethod(tradingEdtexit.getText().toString().trim())));
            tradingEdtexit.setText(
                subPrice.stripTrailingZeros()
                    .toPlainString());
          }
        }

        break;
      //触发价 加
      case R.id.trading_add:
        earnestMoneyType = 2;
        if (!tradingEdtexit.getText().toString().trim().equals("")) {
          BigDecimal price = new BigDecimal(tradingEdtexit.getText().toString().trim());
          BigDecimal subPrice = price.add(
              new BigDecimal(XJPriceSubMethod(tradingEdtexit.getText().toString().trim())));
          tradingEdtexit.setText(
              subPrice.stripTrailingZeros()
                  .toPlainString());
        } else {
          tradingEdtexit.setText("1");
        }

        break;
      //触发价  加和减 输入
      case R.id.linear_add:

        break;
      //输入 买入数
      case R.id.trading_BTC_edtexit:

        break;
      //买入数  减
      case R.id.trading_BTC_reduce:
        earnestMoneyType = 1;
        if (!tradingBTCEdtexit.getText().toString().trim().equals("")) {
          if (new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).compareTo(
              BigDecimal.ZERO)
              == 0) {
            tradingBTCEdtexit.setText("0");
          }
          if (new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).compareTo(
              BigDecimal.ZERO) == 1) {
            BigDecimal price = new BigDecimal(tradingBTCEdtexit.getText().toString().trim());
            BigDecimal subPrice = price.subtract(
                new BigDecimal(XJPriceSubMethod(tradingBTCEdtexit.getText().toString().trim())));
            tradingBTCEdtexit.setText(
                subPrice.stripTrailingZeros()
                    .toPlainString());
          }
        }

        break;
      //买入数   加
      case R.id.trading_BTC_add:
        earnestMoneyType = 1;
        if (!tradingBTCEdtexit.getText().toString().trim().equals("")) {
          BigDecimal price = new BigDecimal(tradingBTCEdtexit.getText().toString().trim());
          BigDecimal subPrice = price.add(
              new BigDecimal(XJPriceSubMethod(tradingBTCEdtexit.getText().toString().trim())));
          tradingBTCEdtexit.setText(
              subPrice.toPlainString());
        } else {
          tradingBTCEdtexit.setText("1");
        }

        break;
      //买入市值
      case R.id.trading_Buy_value:
        break;
      //买入市值 交易对
      case R.id.trading_BiBuy_value:
        break;
      //杠杆倍数

      case R.id.btnXD0:
        levelClick("0");
        break;

      case R.id.btnXD1:
        levelClick("0.25");
        break;
      case R.id.btnXD2:
        levelClick("0.5");
        break;
      case R.id.btnXD3:
        levelClick("0.75");
        break;
      case R.id.btnXD4:
        levelClick("1");
        break;
      //            case R.id.trading_seekbarWithIntervals:
      //                break;
      // 支付保证金
      case R.id.trading_Payment_of:
        earnestMoneyType = 2;

        break;
      //支付保证金  交易对
      case R.id.trading_BiPayment_of:
        break;
      //倍数
      case R.id.trading_Charge_money:
        //选择杠杆倍数
      case R.id.choice_Charge_money:
        //选择杠杆倍数
        gearing();
        break;
      //资金划转
      case R.id.trading_Transfer_of:
        bundle.putString(Keys.PARAM_PAIR, Name);
        if (getActivity() instanceof MainActivity) {
          Activity activity = (MainActivity) getActivity();
          bundle.putInt(Keys.TRANS_SELECT, 2);
          ((MainContract.View) activity).selectTab(3, bundle);
        }

        break;
      //止盈
      case R.id.trading_Check_surplus:
        break;
      //止损
      case R.id.trading_Stop_loss:
        break;
      //可用
      case R.id.trading_available:
        break;
      //买涨
      case R.id.trading_Buy_up:

        //1 涨
        dealType = 1;

        if (TextUtils.isEmpty(tradingEdtexit.getText().toString().trim())) {
          t("请输入触发价");
          return;
        }

        if (TextUtils.isEmpty(tradingBTCEdtexit.getText().toString().trim())) {
          t("请输入买入数量");
          return;
        }

        if (Double.parseDouble(tradingBTCEdtexit.getText().toString().trim()) < minNum) {
          t("输入买入数量小于" + minNum);
          return;
        }

        if (Double.parseDouble(tradingBTCEdtexit.getText().toString().trim()) > maxNum) {

          t("输入买入数量大于" + maxNum);
          return;
        }

        getcreateIndexEntrust(dealType);
        break;
      //买跌
      case R.id.trading_To_buy:

        //跌
        dealType = 2;

        if (TextUtils.isEmpty(tradingEdtexit.getText().toString().trim())) {
          t("请输入触发价");
          return;
        }

        if (TextUtils.isEmpty(tradingBTCEdtexit.getText().toString().trim())) {
          t("请输入买入数量");
          return;
        }
        if (Double.parseDouble(tradingBTCEdtexit.getText().toString().trim()) < minNum) {
          t("输入买入数量小于" + minNum);
          return;
        }

        if (Double.parseDouble(tradingBTCEdtexit.getText().toString().trim()) > maxNum) {

          t("输入买入数量大于" + maxNum);
          return;
        }
        getcreateIndexEntrust(dealType);
        break;
      //持仓  结算  撤单
      case R.id.trading_trading_Warehousing:
        break;
      //查看更多
      case R.id.trading_view_more:
        Navigator.INSTANCE.toFuturesOrderActivity(getActivity());
        break;

      default:
        break;
    }
  }

  private void levelClick(String level) {

    btnXD0.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
    btnXD1.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
    btnXD2.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
    btnXD3.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
    btnXD4.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

    btnXD0.setBackgroundResource(R.drawable.re_shape_btn2);
    btnXD1.setBackgroundResource(R.drawable.re_shape_btn2);
    btnXD2.setBackgroundResource(R.drawable.re_shape_btn2);
    btnXD3.setBackgroundResource(R.drawable.re_shape_btn2);
    btnXD4.setBackgroundResource(R.drawable.re_shape_btn2);
    if (level.equals("0")) {
      btnXD0.setTextColor(CommonUtil.getColor(R.color.white));
      btnXD0.setBackgroundResource(R.drawable.re_shape_btn1);
    } else if (level.equals("0.25")) {
      btnXD1.setTextColor(CommonUtil.getColor(R.color.white));
      btnXD1.setBackgroundResource(R.drawable.re_shape_btn1);
    } else if (level.equals("0.5")) {
      btnXD2.setTextColor(CommonUtil.getColor(R.color.white));
      btnXD2.setBackgroundResource(R.drawable.re_shape_btn1);
    } else if (level.equals("0.75")) {
      btnXD3.setTextColor(CommonUtil.getColor(R.color.white));
      btnXD3.setBackgroundResource(R.drawable.re_shape_btn1);
    } else if (level.equals("1")) {
      btnXD4.setTextColor(CommonUtil.getColor(R.color.white));
      btnXD4.setBackgroundResource(R.drawable.re_shape_btn1);
    }

    tradingEdtexit.removeTextChangedListener(watcher1);
    tradingBTCEdtexit.removeTextChangedListener(watcher2);
    tradingPaymentOf.removeTextChangedListener(watcher3);
    try {

      Bond = new BigDecimal(Available.multiply(new BigDecimal(level))
          .setScale(2, BigDecimal.ROUND_HALF_UP)
          .toString());

      BigDecimal number = Bond.multiply(new BigDecimal(lever))
          .divide(indexMarketList.getNewPrice(), 8, BigDecimal.ROUND_HALF_DOWN)
          .setScale(3, BigDecimal.ROUND_HALF_DOWN);
      tradingBTCEdtexit.setText(number.toString());

      tradingPaymentOf.setText(
          Bond.setScale(priceReservation.intValue(), BigDecimal.ROUND_DOWN).toString());
      tradingBiBuyValue.setText(" ≈CNY");

      earnestMoneyType = 2;

      /**
       *
       * 止盈 = 保证金  *5
       */
      tradingCheckSurplus.setText("止盈:" + Bond.multiply(new BigDecimal(5))
          .setScale(3, BigDecimal.ROUND_HALF_UP)
          .toString());

      /**
       *
       * 止损 = 保证金  *0.8
       */
      tradingStopLoss.setText("止损:" + Bond.multiply(new BigDecimal(0.8))
          .setScale(3, BigDecimal.ROUND_HALF_UP)
          .toString());
      /**
       *
       * 买入市值  = 保证金 * cny
       * 算保证金*那个cny
       */
      buyvule = Bond.multiply(indexMarketList.getCny());
      tradingBuyValue.setText(buyvule.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
    } catch (Exception e) {

    }

    tradingEdtexit.addTextChangedListener(watcher1);
    tradingBTCEdtexit.addTextChangedListener(watcher2);
    tradingPaymentOf.addTextChangedListener(watcher3);
  }

  /**
   * 点击其他部分popwindow消失时，屏幕恢复透明度
   */
  class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

    @Override
    public void onDismiss() {
      BackgroudAlpha((float) 1);
    }
  }

  /**
   * 小数最后一位相加相减
   */
  private String XJPriceSubMethod(String price) {
    String NewPrice = "";
    int Length = 0;
    if (!price.equals("")) {
      //是小数
      if (price.contains(".")) {
        NewPrice = price.substring(price.indexOf(".") + 1);
        Length = NewPrice.length();
      }
    }

    if (Length == 1) {
      return "0.1";
    }
    if (Length == 2) {
      return "0.01";
    }
    if (Length == 3) {
      return "0.001";
    }
    if (Length == 4) {
      return "0.0001";
    }
    if (Length == 5) {
      return "0.00001";
    }
    if (Length == 6) {
      return "0.000001";
    }
    if (Length == 7) {
      return "0.0000001";
    }
    if (Length == 8) {
      return "0.00000001";
    }
    if (Length == 9) {
      return "0.000000001";
    }
    if (Length == 10) {
      return "0.0000000001";
    }
    if (Length == 11) {
      return "0.00000000001";
    }
    if (Length == 12) {
      return "0.000000000001";
    }
    if (Length == 13) {
      return "0.0000000000001";
    }
    if (Length == 14) {
      return "0.00000000000001";
    } else {
      return "1";
    }
  }

  /**
   * 买张和买跌
   */
  @SuppressLint("CheckResult")
  private void getcreateIndexEntrust(int dealType) {

    HashMap<String, String> hashMap = new HashMap<>();

    //币种 btc
    hashMap.put("coinCode", Name.substring(0, Name.indexOf("_")));
    //交易区usdt
    hashMap.put("pricingCoin", Name.substring(Name.indexOf("_") + 1, Name.length()));
    //交易类型 (1 涨 2 跌)
    hashMap.put("dealType", String.valueOf(dealType));
    //委托价格
    hashMap.put("delAmount", new BigDecimal(tradingEdtexit.getText().toString().trim()).setScale(
        priceReservation.intValue(),
        BigDecimal.ROUND_HALF_DOWN).toString());
    //委托数量
    hashMap.put("delNum", new BigDecimal(tradingBTCEdtexit.getText().toString().trim()).setScale(
        quantityRetention.intValue(),
        BigDecimal.ROUND_HALF_DOWN).toString());
    //    hashMap.put("delNum","1");

    //委托方式 1市价 2触发价
    hashMap.put("delWay", String.valueOf(delWay));
    //杠杆倍数
    hashMap.put("lever", String.valueOf(lever));
    //保证金
    hashMap.put("earnestMoney", tradingPaymentOf.getText().toString().trim());

    //	输入类型 1 输入方法为个数 2 输入方法为保证金

    hashMap.put("earnestMoneyType", String.valueOf(earnestMoneyType));

    Log.e(TAG, "传值: " + hashMap.toString());
    Log.e(TAG, "委托数量: " + quantityRetention);
    Log.e(TAG, "委托价格: " + priceReservation);

    ApiFactory.getInstance()
        .getcreateIndexEntrust(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          if (data.getCode() == 200) {
            int index = tradingTradingWarehousing.getSelIndex();
            if (index == 0) {
              type = 4;
            } else {
              type = index;
            }
            bonds(type);
            new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                if (type == 4 || type == 1) {
                  bonds(type);
                }
              }
            }, 8000);
          }
          t(data.getMsg());
        }, throwable -> {
          Timber.e(throwable);
        });
  }
}
