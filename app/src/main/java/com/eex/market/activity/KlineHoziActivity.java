package com.eex.market.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 * @ClassName: KlineHoziActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 16:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KlineHoziActivity extends BaseActivity {


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
    @BindView(R.id.textName)
    TextView textName;
    /**
     *
     */
    @BindView(R.id.LLfish)
    LinearLayout LLfish;
    /**
     *
     */
    @BindView(R.id.web_Kline)
    WebView webView;


    private ArrayList<Map<String, String>> listLeft = new ArrayList<>();

    private String name1;
    private String time;


    private PopupWindow popLeft;


    private View layoutLeft;
    private String strItem;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_kline_hozi;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {



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


        name1 = getIntent().getStringExtra("name");
        textName.setText(name1);
        time = getIntent().getStringExtra("time");
        txName = (TextView) findViewById(R.id.tx_name);
        if (time.equals("line")) {
            txName.setText("分时");
        }
        if (time.equals("1min")) {
            txName.setText("1分钟");
        }
        if (time.equals("5min")) {
            txName.setText("5分钟");
        }
        if (time.equals("15min")) {
            txName.setText("15分钟");
        }
        if (time.equals("30min")) {
            txName.setText("30分钟");
        }
        if (time.equals("60min")) {
            txName.setText("60分钟");
        }
        if (time.equals("1day")) {
            txName.setText("日线");
        }
        if (time.equals("1week")) {
            txName.setText("周线");
        }
        if (time.equals("1mon")) {
            txName.setText("月线");
        }


        webView = (WebView) findViewById(R.id.web_Kline);
        webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline" + name1 + "&" + time);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setVerticalScrollbarOverlay(true);
        webView.setInitialScale(100);
        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // webview自己加载URL，让后通知系统不需要HandleURL
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initUiAndListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tx_name, R.id.pop_LL, R.id.textName, R.id.LLfish, R.id.web_Kline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tx_name:


                if (popLeft != null && popLeft.isShowing()) {
                    popLeft.dismiss();
                } else {
                    layoutLeft = getLayoutInflater().inflate(R.layout.pop_menulist, null);
                    ListView menulistLeft = (ListView) layoutLeft.findViewById(R.id.menulist);
                    SimpleAdapter listAdapter = new SimpleAdapter(KlineHoziActivity.this, listLeft, R.layout.pop_menuitem, new String[]{"item"}, new int[]{R.id.menuitem});
                    menulistLeft.setAdapter(listAdapter);

                    // 点击listview中item的处理
                    menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int arg2, long arg3) {
                            // 改变顶部对应TextView值
                            strItem = listLeft.get(arg2).get("item");
                            textName.setText(strItem);
                            //指标切换
                            if (strItem.equals("分时")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&line");
                            }
                            if (strItem.equals("1分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1min");
                            }
                            if (strItem.equals("5分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&5min");
                            }
                            if (strItem.equals("15分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&15min");
                            }
                            if (strItem.equals("30分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&30min");
                            }
                            if (strItem.equals("60分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&60min");
                            }
                            if (strItem.equals("日线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1day");
                            }
                            if (strItem.equals("周线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1week");
                            }
                            if (strItem.equals("月线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1mon");
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
            case R.id.pop_LL:
                if (popLeft != null && popLeft.isShowing()) {
                    popLeft.dismiss();
                } else {
                    layoutLeft = getLayoutInflater().inflate(R.layout.pop_menulist, null);
                    ListView menulistLeft = (ListView) layoutLeft.findViewById(R.id.menulist);
                    SimpleAdapter listAdapter = new SimpleAdapter(KlineHoziActivity.this, listLeft, R.layout.pop_menuitem, new String[]{"item"}, new int[]{R.id.menuitem});
                    menulistLeft.setAdapter(listAdapter);

                    // 点击listview中item的处理
                    menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int arg2, long arg3) {
                            // 改变顶部对应TextView值
                            strItem = listLeft.get(arg2).get("item");
                            textName.setText(strItem);
                            //指标切换
                            if (strItem.equals("分时")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&line");
                            }
                            if (strItem.equals("1分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1min");
                            }
                            if (strItem.equals("5分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&5min");
                            }
                            if (strItem.equals("15分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&15min");
                            }
                            if (strItem.equals("30分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&30min");
                            }
                            if (strItem.equals("60分钟")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&60min");
                            }
                            if (strItem.equals("日线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1day");
                            }
                            if (strItem.equals("周线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1week");
                            }
                            if (strItem.equals("月线")) {
                                webView.loadUrl(WPConfig.INSTANCE.getBaseUrl() + "m-kline/" + name1 + "&1mon");
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
            case R.id.textName:
                break;
            case R.id.LLfish:
                finish();
                break;
            case R.id.web_Kline:
                break;
            default:
                break;
        }
    }
}
