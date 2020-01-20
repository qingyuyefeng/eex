package com.eex.market.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.home.bean.MainData;
import com.eex.market.bean.Buy;
import com.eex.market.bean.CnySize;
import com.eex.market.bean.PurchaseDatalIst;
import com.eex.market.bean.PurchaseDta;
import com.eex.market.bean.Sell;
import com.eex.market.frgament.KLine.NewDealFrament;
import com.eex.market.frgament.KLine.SellFragment;
import com.eex.market.weight.Root;
import com.eex.market.weight.WebViewScrollView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;



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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.market.activity
 * @ClassName: KLineActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 13:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 13:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KLineActivity extends BaseActivity {


    @BindView(R.id.sousuo_iv)
    ImageView sousuoIv;
    /**
     *
     */
    @BindView(R.id.sousuo_tv)
    TextView sousuoTv;
    /**
     *
     */
    @BindView(R.id.main_header_campaign_search)
    CheckBox mainHeaderCampaignSearch;
    /**
     *
     */
    @BindView(R.id.kline_tv_close)
    TextView klineTvClose;
    /**
     *
     */
    @BindView(R.id.kline_tv_sub)
    TextView klineTvSub;
    /**
     *
     */
    @BindView(R.id.kline_tv_percent)
    TextView klineTvPercent;
    /**
     *
     */
    @BindView(R.id.kline_tv_max)
    TextView klineTvMax;
    /**
     *
     */
    @BindView(R.id.kline_tv_min)
    TextView klineTvMin;
    /**
     *
     */
    @BindView(R.id.kline_tv_num)
    TextView klineTvNum;
    /**
     *
     */
    @BindView(R.id.LLtype)
    LinearLayout LLtype;
    /**
     *
     */
    @BindView(R.id.tx_name)
    TextView txName;
    /**
     *
     */
    @BindView(R.id.pop_LL)
    LinearLayout popLL;
    /**
     *
     */
    @BindView(R.id.tx_qiehuan)
    TextView txQiehuan;
    /**
     *
     */
    @BindView(R.id.view_kline_tv_ma5)
    TextView viewKlineTvMa5;
    /**
     *
     */
    @BindView(R.id.view_kline_tv_ma10)
    TextView viewKlineTvMa10;
    /**
     *
     */
    @BindView(R.id.view_kline_tv_ma20)
    TextView viewKlineTvMa20;
    /**
     *
     */
    @BindView(R.id.view_kline_tv_ma30)
    TextView viewKlineTvMa30;
    /**
     *
     */
    @BindView(R.id.ll_MA)
    LinearLayout llMA;
    /**
     *
     */
    @BindView(R.id.KlineWebView1)
    WebView webView;
    /**
     *
     */
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    /**
     *
     */
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    /**
     *
     */
    @BindView(R.id.scrView)
    WebViewScrollView scrView;
    /**
     *
     */
    @BindView(R.id.btn_buy)
    Button btnBuy;
    /**
     *
     */
    @BindView(R.id.btn_sell)
    Button btnSell;
    /**
     * 交易币
     */
    private String JYBi;
    /**
     * 定价币
     */
    private String DJBi;
    /**
     * BTC_USDT
     */
    private String BinameData;
    /**
     * BTC/USDT
     */
    private String BiName;




    private String[] titles = {"委托订单", "最近成交"};
    private ArrayList<Fragment> fragments = new ArrayList<>();


    private ArrayList<MainData> data = new ArrayList<>();
    private ArrayList<PurchaseDatalIst> datalIsts = new ArrayList<>();

    private ArrayList<Sell> sellList = new ArrayList<>();
    private ArrayList<Buy> buyList = new ArrayList<>();

    private String ckType = "Yes";

    private PopupWindow popLeft;

    private View layoutLeft;

    /**
     * 菜单数据项
     */
    private List<Map<String, String>> listLeft = new ArrayList<Map<String, String>>();
    ;
    /**
     * 选择的时间
     */
    private String strItem;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_kline;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        if (getIntent().getStringExtra("JYBi") != null) {
            JYBi = getIntent().getStringExtra("JYBi");
        }
        if (getIntent().getStringExtra("DJBi") != null) {
            DJBi = getIntent().getStringExtra("DJBi");
        }

        if (getIntent().getStringExtra("BinameData") != null) {
            BinameData = getIntent().getStringExtra("BinameData");
            SellFragment.BinameData = BinameData;
            NewDealFrament.BinameData = BinameData;
        }
        if (getIntent().getStringExtra("Biname") != null) {
            BiName = getIntent().getStringExtra("Biname");
            sousuoTv.setText(BiName);
        }





        //默认加载5分钟K线
        setwebView();
        //K线数据
        net();
        //获取交易对详情
        getIndexMaketList();
        //
        WebSocket();

    }

    private void WebSocket() {

        RxWebSocket.get(WPConfig.INSTANCE.getBaseUrl() + "websocket/" + BinameData)
                .compose(RxSchedulers.io_main())
                .subscribe(new WebSocketSubscriber2<Root<PurchaseDta>>() {
                    @Override
                    protected void onMessage(Root<PurchaseDta> purchaseDtaRoot) {
                        if (purchaseDtaRoot != null) {

                            if (purchaseDtaRoot.getData().getSell() != null) {

                                sellList.clear();
                                sellList.addAll(purchaseDtaRoot.getData().getSell());

                            }

                            if (purchaseDtaRoot.getData().getBuy() != null) {

                                buyList.clear();
                                buyList.addAll(purchaseDtaRoot.getData().getBuy());

                            }

                            //更新24h成交价
                            try {
                                if (purchaseDtaRoot.getData().getListOrder().size() != 0) {
                                    KLineActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //更新最新成交记录
                                            setNewMoney(purchaseDtaRoot.getData().getListOrder());
                                        }


                                    });
                                }

                            } catch (Exception e) {

                            }

                        } else {

                            Log.e("TAG", "onMessage: " + purchaseDtaRoot.getMsg());
                        }
                    }


                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连1");

                    }

                });
    }

    /**
     * 默认加载5分钟K线
     */
    private void setwebView() {

        webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&5min");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setVerticalScrollbarOverlay(true);
        webView.setInitialScale(100);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //是否使用缓存
        webView.getSettings().setAppCacheEnabled(false);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // webview自己加载URL，让后通知系统不需要HandleURL
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //用javascript隐藏系统定义的404页面信息
                String data = "Page NO FOUND！";
                Log.e("description", description);
            }

        });
    }


    /**
     * K线数据
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dealPair", BinameData);
        hashMap.put("size", "10");
        ApiFactory.getInstance()
                .TradingHall(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data != null) {
                        klineTvClose.setText(data.getData().get(0).getDealPrice().setScale(4, BigDecimal.ROUND_CEILING).toString());
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }


    /***
     *
     * 获取交易对详情
     */
    @SuppressLint("CheckResult")
    private void getIndexMaketList() {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("delkeys", BinameData);

        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {

                    if (arrayListData.getCode() == 200) {

                        data.clear();
                        data.addAll(arrayListData.getData());

                        if (arrayListData.getData().get(0).getDealNum() != null && arrayListData.getData().get(0).getHigePrice() != null && arrayListData.getData().get(0).getLastPrice() != null) {
                            klineTvNum.setText(arrayListData.getData().get(0).getDealNum().setScale(4, BigDecimal.ROUND_DOWN).toString());
                            klineTvMax.setText(arrayListData.getData().get(0).getHigePrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
                            klineTvMin.setText(arrayListData.getData().get(0).getFooPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());

                            BigDecimal money = arrayListData.getData().get(0).getNewPrice().divide(arrayListData.getData().get(0).getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                            BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                            BigDecimal monetData = arrayListData.getData().get(0).getNewPrice().subtract(arrayListData.getData().get(0).getLastPrice());
                            if (monetData.compareTo(BigDecimal.ZERO) == 1) {
                                klineTvSub.setText("+" + monetData.toString());
                                klineTvSub.setTextColor(getResources().getColor(R.color.K_bul));
                            } else if (monetData.compareTo(BigDecimal.ZERO) == -1) {
                                klineTvSub.setText(monetData.toString());
                                klineTvSub.setTextColor(getResources().getColor(R.color.K_red));
                            }

                            if (newData.compareTo(BigDecimal.ZERO) == 1) {
                                klineTvPercent.setTextColor(getResources().getColor(R.color.K_bul));
                                klineTvPercent.setText("+" + newData.setScale(3, BigDecimal.ROUND_DOWN) + "%");
                            } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                                klineTvPercent.setTextColor(getResources().getColor(R.color.K_red));
                                klineTvPercent.setText(newData.setScale(3, BigDecimal.ROUND_DOWN) + "%");
                            }

                        }


                    } else {
                        t(arrayListData.getMsg());

                    }


                }, throwable -> {


                });

    }

    /**
     * 更新最新成交记录
     *
     * @param listOrder
     */
    private void setNewMoney(List<CnySize> listOrder) {

        datalIsts.clear();

        if (listOrder != null && !listOrder.isEmpty()) {
            for (CnySize cnySize : listOrder) {
                PurchaseDatalIst purchaseDatalIst = new PurchaseDatalIst();
                purchaseDatalIst.setDealTime(cnySize.getDealDate());
                purchaseDatalIst.setDealNum(cnySize.getDealNum());
                purchaseDatalIst.setDealPrice(cnySize.getDealAmount());
                datalIsts.add(0, purchaseDatalIst);
            }
            //设置最新成交价

            if (datalIsts != null && datalIsts.size() != 0) {
                klineTvClose.setText(datalIsts.get(0).getDealPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
                BigDecimal monetData = datalIsts.get(datalIsts.size() - 1).getDealPrice().subtract(data.get(data.size() - 1).getLastPrice());

                BigDecimal money = datalIsts.get(datalIsts.size() - 1).getDealPrice().divide(data.get(data.size() - 1).getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

                //推送的最高价
                BigDecimal NeWPri = datalIsts.get(0).getDealPrice();
                //推送的最低价
                BigDecimal NewdowPri = datalIsts.get(0).getDealPrice();
                //获取最高价
                for (int i = 0; i < datalIsts.size(); i++) {
                    BigDecimal newHigtPrice = datalIsts.get(i).getDealPrice();
                    if (NeWPri.compareTo(newHigtPrice) == 1) {
                        NeWPri = datalIsts.get(0).getDealPrice();
                    } else {
                        NeWPri = datalIsts.get(i).getDealPrice();
                    }
                }
                //获取最低价
                for (int i = 0; i < datalIsts.size(); i++) {
                    BigDecimal newHigtPrice = datalIsts.get(i).getDealPrice();
                    if (NewdowPri.compareTo(newHigtPrice) == -1) {
                        NewdowPri = datalIsts.get(0).getDealPrice();
                    } else {
                        NewdowPri = datalIsts.get(i).getDealPrice();
                    }
                }

                //设置最低价
                BigDecimal dowPri = data.get(0).getFooPrice();
                if (dowPri.compareTo(NewdowPri) == -1) {
                    klineTvMin.setText(dowPri.setScale(4, BigDecimal.ROUND_DOWN).toString());
                } else {
                    klineTvMin.setText(NewdowPri.setScale(4, BigDecimal.ROUND_DOWN).toString());
                }
                //设置24H最高价
                BigDecimal Higtmoney = data.get(0).getHigePrice();
                if (Higtmoney.compareTo(NeWPri) == 1) {
                    klineTvMax.setText(Higtmoney.setScale(4, BigDecimal.ROUND_DOWN).toString());
                } else {
                    klineTvMax.setText(NeWPri.setScale(4, BigDecimal.ROUND_DOWN).toString());
                }
                //设置价格涨幅多少
                if (monetData.compareTo(BigDecimal.ZERO) == 1) {
                    klineTvSub.setText("+" + monetData.toString());
                    klineTvSub.setTextColor(getResources().getColor(R.color.K_bul));
                } else if (monetData.compareTo(BigDecimal.ZERO) == -1) {
                    klineTvSub.setText(monetData.toString());
                    klineTvSub.setTextColor(getResources().getColor(R.color.K_red));
                }
                //设置价格涨幅百分比
                if (newData.compareTo(BigDecimal.ZERO) == 1) {
                    klineTvPercent.setTextColor(getResources().getColor(R.color.K_bul));
                    klineTvPercent.setText("+" + newData.setScale(3, BigDecimal.ROUND_DOWN) + "%");
                } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                    klineTvPercent.setTextColor(getResources().getColor(R.color.K_red));
                    klineTvPercent.setText(newData.setScale(3, BigDecimal.ROUND_DOWN) + "%");
                }
                //设置成交量
                BigDecimal nuber = data.get(0).getDealNum();
                BigDecimal totalInvestMoney = BigDecimal.ZERO;
                for (CnySize cnySize : listOrder) {
                    totalInvestMoney = totalInvestMoney.add(cnySize.getDealNum());
                }
                klineTvNum.setText(nuber.add(totalInvestMoney).toString());
            }

        }

    }


    @Override
    protected void initUiAndListener() {


        //委托订单
        fragments.add(new SellFragment());
        //最近成交
        fragments.add(new NewDealFrament());


        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        xTablayout.setupWithViewPager(viewPager);




        HashMap<String, String> mapTemp0 = new HashMap<String, String>();
        mapTemp0.put("item", "分时");
        listLeft.add(mapTemp0);

        HashMap<String, String> mapTemp = new HashMap<String, String>();
        mapTemp.put("item", "1分钟");
        listLeft.add(mapTemp);

        HashMap<String, String> mapTemp1 = new HashMap<String, String>();
        mapTemp1.put("item", "5分钟");
        listLeft.add(mapTemp1);

        HashMap<String, String> mapTemp2 = new HashMap<String, String>();
        mapTemp2.put("item", "15分钟");
        listLeft.add(mapTemp2);

        HashMap<String, String> mapTemp3 = new HashMap<String, String>();
        mapTemp3.put("item", "30分钟");
        listLeft.add(mapTemp3);

        HashMap<String, String> mapTemp4 = new HashMap<String, String>();
        mapTemp4.put("item", "60分钟");
        listLeft.add(mapTemp4);

        HashMap<String, String> mapTemp5 = new HashMap<String, String>();
        mapTemp5.put("item", "日线");
        listLeft.add(mapTemp5);

        HashMap<String, String> mapTemp6 = new HashMap<String, String>();
        mapTemp6.put("item", "周线");
        listLeft.add(mapTemp6);

        HashMap<String, String> mapTemp7 = new HashMap<String, String>();
        mapTemp7.put("item", "月线");
        listLeft.add(mapTemp7);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.sousuo_iv, R.id.sousuo_tv, R.id.main_header_campaign_search, R.id.kline_tv_close, R.id.kline_tv_sub, R.id.kline_tv_percent, R.id.kline_tv_max, R.id.kline_tv_min, R.id.kline_tv_num, R.id.LLtype, R.id.tx_name, R.id.pop_LL, R.id.tx_qiehuan, R.id.view_kline_tv_ma5, R.id.view_kline_tv_ma10, R.id.view_kline_tv_ma20, R.id.view_kline_tv_ma30, R.id.ll_MA, R.id.KlineWebView1, R.id.xTablayout, R.id.view_pager, R.id.scrView, R.id.btn_buy, R.id.btn_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sousuo_iv:
                finish();
                break;
            case R.id.sousuo_tv:
                break;
            case R.id.main_header_campaign_search:
                if (ckType.equals("Yes")) {
                    mainHeaderCampaignSearch.setChecked(true);
                    ckType = "No";

                    //根据ID查询配置
                    saveOrUpdateDealPairCollection();
                } else {
                    mainHeaderCampaignSearch.setChecked(false);
                    ckType = "Yes";
                }
                break;
            case R.id.kline_tv_close:
                break;
            case R.id.kline_tv_sub:
                break;
            case R.id.kline_tv_percent:
                break;
            case R.id.kline_tv_max:
                break;
            case R.id.kline_tv_min:
                break;
            case R.id.kline_tv_num:
                break;
            case R.id.LLtype:
                break;
            case R.id.tx_name:

                if (popLeft != null && popLeft.isShowing()) {
                    popLeft.dismiss();
                } else {
                    layoutLeft = getLayoutInflater().inflate(R.layout.pop_menulist, null);
                    ListView menulistLeft = (ListView) layoutLeft.findViewById(R.id.menulist);
                    SimpleAdapter listAdapter = new SimpleAdapter(KLineActivity.this, listLeft, R.layout.pop_menuitem, new String[]{"item"}, new int[]{R.id.menuitem});
                    menulistLeft.setAdapter(listAdapter);

                    // 点击listview中item的处理
                    menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // 改变顶部对应TextView值
                            strItem = listLeft.get(arg2).get("item");
                            txName.setText(strItem);
                            //指标切换
                            if (strItem.equals("分时")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&line");
                            }
                            if (strItem.equals("1分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1min");
                            }
                            if (strItem.equals("5分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&5min");
                            }
                            if (strItem.equals("15分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&15min");
                            }
                            if (strItem.equals("30分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&30min");
                            }
                            if (strItem.equals("60分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&60min");
                            }
                            if (strItem.equals("日线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1day");
                            }
                            if (strItem.equals("周线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1week");
                            }
                            if (strItem.equals("月线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1mon");
                            }
//                                    isType(strItem);
                            // 隐藏弹出窗口
                            if (popLeft != null && popLeft.isShowing()) {
                                popLeft.dismiss();
                            }
                        }
                    });

                    // 创建弹出窗口
                    // 窗口内容为layoutLeft，里面包含一个ListView
                    // 窗口宽度跟tvLeft一样
                    popLeft = new PopupWindow(layoutLeft, txName.getWidth(),
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    ColorDrawable cd = new ColorDrawable(000000);
                    popLeft.setBackgroundDrawable(cd);
                    popLeft.setAnimationStyle(R.style.PopupAnimation);
                    popLeft.update();
                    popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    // 设置popupwindow可点击
                    popLeft.setTouchable(true);
                    // 设置popupwindow外部可点击
                    popLeft.setOutsideTouchable(true);
                    // 获取焦点
                    popLeft.setFocusable(true);

                    // 设置popupwindow的位置（相对tvLeft的位置）
                    int topBarHeight = popLL.getBottom();
                    popLeft.showAsDropDown(txName, 0,
                            (topBarHeight - txName.getHeight()) / 2);

                    popLeft.setTouchInterceptor(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // 如果点击了popupwindow的外部，popupwindow也会消失
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                popLeft.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                }


                break;
            //选择K线时间
            case R.id.pop_LL:

                if (popLeft != null && popLeft.isShowing()) {
                    popLeft.dismiss();
                } else {
                    layoutLeft = getLayoutInflater().inflate(R.layout.pop_menulist, null);
                    ListView menulistLeft = (ListView) layoutLeft.findViewById(R.id.menulist);
                    SimpleAdapter listAdapter = new SimpleAdapter(KLineActivity.this, listLeft, R.layout.pop_menuitem, new String[]{"item"}, new int[]{R.id.menuitem});
                    menulistLeft.setAdapter(listAdapter);

                    // 点击listview中item的处理
                    menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // 改变顶部对应TextView值
                            strItem = listLeft.get(arg2).get("item");
                            txName.setText(strItem);
                            //指标切换
                            if (strItem.equals("分时")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&line");
                            }
                            if (strItem.equals("1分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1min");
                            }
                            if (strItem.equals("5分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&5min");
                            }
                            if (strItem.equals("15分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&15min");
                            }
                            if (strItem.equals("30分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&30min");
                            }
                            if (strItem.equals("60分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&60min");
                            }
                            if (strItem.equals("日线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1day");
                            }
                            if (strItem.equals("周线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1week");
                            }
                            if (strItem.equals("月线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + BinameData + "&1mon");
                            }
//                                    isType(strItem);
                            // 隐藏弹出窗口
                            if (popLeft != null && popLeft.isShowing()) {
                                popLeft.dismiss();
                            }
                        }
                    });

                    // 创建弹出窗口
                    // 窗口内容为layoutLeft，里面包含一个ListView
                    // 窗口宽度跟tvLeft一样
                    popLeft = new PopupWindow(layoutLeft, txName.getWidth(),
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    ColorDrawable cd = new ColorDrawable(000000);
                    popLeft.setBackgroundDrawable(cd);
                    popLeft.setAnimationStyle(R.style.PopupAnimation);
                    popLeft.update();
                    popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    // 设置popupwindow可点击
                    popLeft.setTouchable(true);
                    // 设置popupwindow外部可点击
                    popLeft.setOutsideTouchable(true);
                    // 获取焦点
                    popLeft.setFocusable(true);

                    // 设置popupwindow的位置（相对tvLeft的位置）
                    int topBarHeight = popLL.getBottom();
                    popLeft.showAsDropDown(txName, 0,
                            (topBarHeight - txName.getHeight()) / 2);

                    popLeft.setTouchInterceptor(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // 如果点击了popupwindow的外部，popupwindow也会消失
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                popLeft.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                }


                break;
            case R.id.tx_qiehuan:
                intent.setClass(KLineActivity.this, KlineHoziActivity.class);
                if (txName.getText().toString().equals("分时")) {
                    intent.putExtra("time", "line");
                }
                if (txName.getText().toString().equals("1分钟")) {
                    intent.putExtra("time", "1min");
                }
                if (txName.getText().toString().equals("5分钟")) {
                    intent.putExtra("time", "5min");
                }
                if (txName.getText().toString().equals("15分钟")) {
                    intent.putExtra("time", "15min");
                }
                if (txName.getText().toString().equals("30分钟")) {
                    intent.putExtra("time", "30min");
                }
                if (txName.getText().toString().equals("60分钟")) {
                    intent.putExtra("time", "60min");
                }
                if (txName.getText().toString().equals("日线")) {
                    intent.putExtra("time", "1day");
                }
                if (txName.getText().toString().equals("周线")) {
                    intent.putExtra("time", "1week");
                }
                if (txName.getText().toString().equals("月线")) {
                    intent.putExtra("time", "1mon");
                }
                intent.putExtra("name", BinameData);
                startActivity(intent);


                break;
            case R.id.view_kline_tv_ma5:
                break;
            case R.id.view_kline_tv_ma10:
                break;
            case R.id.view_kline_tv_ma20:
                break;
            case R.id.view_kline_tv_ma30:
                break;
            case R.id.ll_MA:
                break;
            case R.id.KlineWebView1:
                break;
            case R.id.xTablayout:
                break;
            case R.id.view_pager:
                break;
            case R.id.scrView:
                break;
            case R.id.btn_buy:
                finish();
                break;
            case R.id.btn_sell:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 根据ID查询配置
     */
    @SuppressLint("CheckResult")
    private void saveOrUpdateDealPairCollection() {

        if (kv.decodeString("tokenId") == null) {
            mainHeaderCampaignSearch.setChecked(false);
            Toast.makeText(KLineActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dealpear", BinameData);

        ApiFactory.getInstance()
                .saveOrUpdateDealPairCollection(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(klineBiDataData -> {

                    if (klineBiDataData.getData() != null) {

                        Toast.makeText(getActivity(), klineBiDataData.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), klineBiDataData.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }, throwable -> {

                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getBaseUrl() + "websocket/" + BinameData).subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }




    }
}
