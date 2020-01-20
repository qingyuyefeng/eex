package com.eex.assets.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.NewComTitleBar;


import java.util.Locale;

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
 * @Package: com.overthrow.assets.activity
 * @ClassName: EpayWebViewActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EpayWebViewActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    NewComTitleBar comtitlebar;
    @BindView(R.id.webview)
    WebView webview;
    private String PAYEE_ACCOUNT;
    private String PAYEE_NAME;
    private String PAYMENT_AMOUNT;
    private String PAYMENT_UNITS;
    private String PAYMENT_ID;
    private String STATUS_URL;
    private String PAYMENT_URL;
    private String NOPAYMENT_URL;
    private String BAGGAGE_FIELDS;
    private String INTERFACE_LANGUAGE;
    private String CHARACTER_ENCODING;
    private String V2_HASH;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_epay_web_view;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        String locale = Locale.getDefault().toString();
        Log.e("PAYEE_ACCOUNT", getIntent().getStringExtra("PAYEE_ACCOUNT"));
        if (getIntent().getStringExtra("PAYEE_ACCOUNT") != null) {
            PAYEE_ACCOUNT = getIntent().getStringExtra("PAYEE_ACCOUNT");
            PAYEE_NAME = getIntent().getStringExtra("PAYEE_NAME");
            PAYMENT_AMOUNT = getIntent().getStringExtra("PAYMENT_AMOUNT");
            PAYMENT_UNITS = getIntent().getStringExtra("PAYMENT_UNITS");
            PAYMENT_ID = getIntent().getStringExtra("PAYMENT_ID");
            STATUS_URL = getIntent().getStringExtra("STATUS_URL");
            PAYMENT_URL = getIntent().getStringExtra("PAYMENT_URL");
            NOPAYMENT_URL = getIntent().getStringExtra("NOPAYMENT_URL");
            BAGGAGE_FIELDS = getIntent().getStringExtra("BAGGAGE_FIELDS");
            INTERFACE_LANGUAGE = getIntent().getStringExtra("INTERFACE_LANGUAGE");
            CHARACTER_ENCODING = getIntent().getStringExtra("CHARACTER_ENCODING");
            V2_HASH = getIntent().getStringExtra("V2_HASH");
            url = getIntent().getStringExtra("url");
            String URL = "https://api.epay.com/paymentApi/merReceive?PAYEE_ACCOUNT=" + PAYEE_ACCOUNT + "&PAYEE_NAME=" + PAYEE_NAME.trim() + "&PAYMENT_AMOUNT=" + PAYMENT_AMOUNT + "&PAYMENT_UNITS=" + PAYMENT_UNITS + "&STATUS_URL=" + STATUS_URL + "&PAYMENT_URL=" + PAYMENT_URL + "&NOPAYMENT_URL=" + NOPAYMENT_URL + "&BAGGAGE_FIELDS=" + BAGGAGE_FIELDS + "&INTERFACE_LANGUAGE=" + locale + "&CHARACTER_ENCODING=" + CHARACTER_ENCODING + "&V2_HASH=" + V2_HASH;
            webview.setWebViewClient(new WebViewClient() {
                // 这个方法在用户试图点开页面上的某个链接时被调用
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url != null) {
                        // 如果想继续加载目标页面则调用下面的语句
                        view.loadUrl(url);
                        // 如果不想那url就是目标网址，如果想获取目标网页的内容那你可以用HTTP的API把网页扒下来。
                    }
                    // 返回true表示停留在本WebView（不跳转到系统的浏览器）
                    return true;
                }
            });
            webview.loadUrl(URL.toString().trim());
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setJavaScriptEnabled(true);
            // 设置可以支持缩放
            webview.getSettings().setSupportZoom(true);
            // 设置出现缩放工具
            webview.getSettings().setBuiltInZoomControls(false);
            webview.setInitialScale(100);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Log.e("aaaaaa2", url + "PAYMENT_URL:" + PAYMENT_URL + "NOPAYMENT_URL:" + NOPAYMENT_URL);
                    if (url != null && url.equals(PAYMENT_URL)) {
                        Intent intent = new Intent();
                        intent.putExtra("ViewType", "RechargeYes");
                        intent.setClass(EpayWebViewActivity.this, PaymentTypeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (url != null && url.equals(NOPAYMENT_URL)) {
                        Intent intent = new Intent();
                        intent.putExtra("ViewType", "RechargeNo");
                        intent.setClass(EpayWebViewActivity.this, PaymentTypeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });

        }

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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
