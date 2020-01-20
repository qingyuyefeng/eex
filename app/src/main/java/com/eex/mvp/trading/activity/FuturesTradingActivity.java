package com.eex.mvp.trading.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber;
import com.eex.mvp.trading.IndexMarketList;

import com.eex.mvp.trading.KDataList;
import com.eex.mvp.trading.KLineMenuView;
import com.google.gson.Gson;
import com.icechao.klinelib.adapter.KLineChartAdapter;
import com.icechao.klinelib.base.BaseKLineChartView;
import com.icechao.klinelib.formatter.DateFormatter;
import com.icechao.klinelib.formatter.ValueFormatter;
import com.icechao.klinelib.utils.DateUtil;
import com.icechao.klinelib.utils.SlidListener;
import com.icechao.klinelib.utils.Status;
import com.icechao.klinelib.view.KLineChartView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * @ProjectName: EEX
 * @Package: com.eex.mvp.trading.activity
 * @ClassName: FuturesTradingActivity
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2020/1/8 15:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/1/8 15:55
 * @CreateDate: 2020/1/8 15:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/1/8 15:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class FuturesTradingActivity extends BaseActivity {

  @BindView(R.id.text_view_ma) TextView textViewMa;
  @BindView(R.id.text_view_boll) TextView textViewBoll;
  @BindView(R.id.linear_layout_master_operater) LinearLayout linearLayoutMasterOperater;
  @BindView(R.id.text_view_macd) TextView textViewMacd;
  @BindView(R.id.text_view_kdj) TextView textViewKdj;
  @BindView(R.id.text_view_rsi) TextView textViewRsi;
  @BindView(R.id.text_view_wr) TextView textViewWr;
  @BindView(R.id.linear_layout_attached_operater) LinearLayout linearLayoutAttachedOperater;
  @BindView(R.id.text_view_one_minute) TextView textViewOneMinute;
  @BindView(R.id.text_view_five_minute) TextView textViewFiveMinute;
  @BindView(R.id.text_view_half_hour) TextView textViewHalfHour;
  @BindView(R.id.text_view_one_week) TextView textViewOneWeek;
  @BindView(R.id.text_view_one_mounth) TextView textViewOneMounth;
  @BindView(R.id.linear_layout_index_more) LinearLayout linearLayoutIndexMore;
  @BindView(R.id.trading_atthe) KLineMenuView tradingAtthe;
  @BindView(R.id.trading_more) TextView tradingMore;
  @BindView(R.id.more_trading_image) ImageView moreTradingImage;
  @BindView(R.id.trading_index) TextView tradingIndex;
  @BindView(R.id.more_index_image) ImageView moreIndexImage;
  @BindView(R.id.kLineChartView) KLineChartView chartView;
  @BindView(R.id.kline_operater) View klineperater;
  @BindView(R.id.kline_time) View klineTime;
  private String Name;
  private boolean flage = true;

  /**
   * 从第多少条开始
   */
  private int startNo = 0;
  /**
   * 每页多少条数据
   */
  private int endNo = 10000;

  private Vibrator vibrator;
  private int defaultIndex = 1;

  private ArrayList<KDataList> kChartBeans = new ArrayList<>();
  private KLineChartAdapter<KDataList> adapter;

  public static String Time;
  private List<String> tabTitles = Arrays.asList("返回", "时分", "1分钟", "15分钟", "1小时");

  @Override
  protected int getLayoutId() {
    return R.layout.re_activity_frllscreen;
  }

  @Override protected void refreshData(Bundle savedInstanceState) {
    if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    Name = getIntent().getStringExtra("Name");
    vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    initKLineView();
    net("5min");
    gitID();
    initSocket();
  }

  @Override protected void initUiAndListener() {

  }

  private void gitID() {
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
        if (position == 0) {
          getActivity().finish();
          //时分
        } else if (position == 1) {
          chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_CLOSE_WITH_LAST);
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.TIME_LINE);
          //1分钟
        } else if (position == 2) {
          net(Time = "5min");
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.K_LINE);
        } else if (position == 3) {
          Time = "15min";
          net(Time);
          chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_NORMAL_WITH_LAST);
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.K_LINE);
        } else if (position == 4) {
          Time = "60min";
          net(Time);
          chartView.setMaxMinCalcModel(Status.MaxMinCalcModel.CALC_NORMAL_WITH_LAST);
          chartView.hideSelectData();
          chartView.setKlineState(Status.KlineStatus.K_LINE);
        }
        net(Time);
      }
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
        //设置每个点的宽度
        .setChartItemWidth(50)
        //设置蜡烛宽度
        .setCandleWidth(30)
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

    chartView.setScaleXMax(1);
    chartView.setScaleXMin(0.5f);
  }

  @SuppressLint("CheckResult")
  private void net(String time) {

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

            kChartBeans.clear();
            kChartBeans.addAll(data.getData());
            initData(kChartBeans);
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
    chartView.hideSelectData();
    new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                  initSocket();
                                }
                              }

        , 3000);
  }

  private void initSocket() {

    clearSocket();
    //ws://www.eex.la/futurewebsocket/BTC_USDT_PRICE_ANDROID
    socketSubscriber = genKlineSocket();
    RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "futurewebsocket/" + Name + "_PRICE_ANDROID")
        .compose(RxSchedulers.io_main())
        .subscribe(socketSubscriber);
  }

  private long lastTime = 0L;
  WebSocketSubscriber socketSubscriber = null;

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
          IndexMarketList imdata = new Gson().fromJson(data, IndexMarketList.class);
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
                kdata.setkTime(kdata.ktime);
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

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @OnClick({
      R.id.text_view_ma, R.id.text_view_boll, R.id.linear_layout_master_operater,
      R.id.text_view_macd, R.id.text_view_kdj, R.id.text_view_rsi, R.id.text_view_wr,
      R.id.linear_layout_attached_operater, R.id.text_view_one_minute, R.id.text_view_five_minute,
      R.id.text_view_half_hour, R.id.text_view_one_week, R.id.text_view_one_mounth,
      R.id.linear_layout_index_more, R.id.trading_atthe, R.id.trading_more, R.id.more_trading_image,
      R.id.trading_index, R.id.more_index_image, R.id.kLineChartView, R.id.kline_operater,
      R.id.kline_time
  }) public void onClick(View view) {
    switch (view.getId()) {
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
        net(Time = "1mon");
        chartView.hideSelectData();
        chartView.setKlineState(Status.KlineStatus.K_LINE);
        textViewOneMounth.setTextColor(CommonUtil.getColor(R.color.text_color));
        textViewOneMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewFiveMinute.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewHalfHour.setTextColor(CommonUtil.getColor(R.color.color_4d6599));
        textViewOneWeek.setTextColor(CommonUtil.getColor(R.color.color_4d6599));

        break;
      case R.id.linear_layout_index_more:
        break;
      case R.id.trading_atthe:
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
      case R.id.more_trading_image:
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
      case R.id.more_index_image:
        break;
      case R.id.kLineChartView:
        break;
      case R.id.kline_operater:
            break;
      case R.id.kline_time:
            break;
    }
  }

  private void clearSocket() {

    if (socketSubscriber != null && !socketSubscriber.isDisposed()) {
      socketSubscriber.dispose();
    }
  }


}
