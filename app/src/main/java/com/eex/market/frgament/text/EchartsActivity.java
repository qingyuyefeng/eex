package com.eex.market.frgament.text;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

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
 * @Package: com.overthrow.market.frgament.text
 * @ClassName: EchartsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/26 14:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/26 14:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EchartsActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_echarts;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        webView.loadUrl("file:///android_asset/pie-nest.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDisplayZoomControls(false);
//        webView.getSettings().setDomStorageEnabled(true);
//        // 设置可以支持缩放
//        webView.getSettings().setSupportZoom(false);
//        // 设置出现缩放工具
//        webView.getSettings().setBuiltInZoomControls(false);
//        webView.setVerticalScrollbarOverlay(true);
//        webView.setInitialScale(100);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setAppCacheEnabled(false);//是否使用缓存
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
        final String coin = "ETH";
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //休眠5秒
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:getdata('" + coin + "')");
                    }
                });

            }
        }.start();


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
