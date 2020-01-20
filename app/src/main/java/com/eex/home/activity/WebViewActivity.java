package com.eex.home.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eex.R;
import com.eex.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * @Package: com.overthrow.home.activity
 * @ClassName: WebViewActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/11 17:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/11 17:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView webView;


    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        if (getIntent().getStringExtra("url") != null) {
            url = getIntent().getStringExtra("url");
            webView.loadUrl(url);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            // 设置可以支持缩放
            webView.getSettings().setSupportZoom(true);
            // 设置出现缩放工具
            webView.getSettings().setBuiltInZoomControls(false);
            webView.setInitialScale(100);

            //webView.setWebViewClient(new WebViewClient(){
            //    @Override
            //    public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //        view.loadUrl(url);
            //        return true;
            //    }
            //
            //    @Override
            //    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //        //证书错误
            //        handler.proceed();
            //        //用户选择继续加载
            //        // handler.proceed();
            //        //用户取消
            //        //handler.cancel()
            //    }
            //});
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
}
