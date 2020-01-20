package com.eex.common.view;

import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
 * @Package: com.overthrow.common.view
 * @ClassName: WebUtil
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 16:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 16:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebUtil {
    public static void webSetting(WebView mWebView, Activity activity) {
        webSetting(mWebView, activity, true);
    }

    public static void webSetting(WebView mWebView, Activity activity, boolean viewPort) {
        mWebView.setOnLongClickListener(v -> true);
        WebSettings webSettings = mWebView.getSettings();

        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        //        mWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //        设置H5的缓存是否打开，默认关闭。
        webSettings.setAppCacheEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        //当你设置为true时，就代表你想要你的WebView支持多窗口，但是一旦设置为true，必须要重写WebChromeClient的onCreateWindow方法。
        webSettings.setSupportMultipleWindows(true);

        webSettings.setJavaScriptEnabled(true);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //        缩放按钮的显示隐藏
        webSettings.setBuiltInZoomControls(true);
        //播放视频
        webSettings.setPluginState(WebSettings.PluginState.ON);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //以下两条设置可以使页面适应手机屏幕的分辨率，完整的显示在屏幕上
        //设置是否使用WebView推荐使用的窗口
        webSettings.setUseWideViewPort(viewPort);
        //允许弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //防止中文乱码
        webSettings.setDefaultTextEncodingName("UTF-8");
        //设置WebView加载页面的模式
        webSettings.setLoadWithOverviewMode(true);

//        // 设置setWebChromeClient对象
//        webView.setWebChromeClient(wvcc);

        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

//        mWebView.addJavascriptInterface(new AllWebInterface(activity), "operation");//operation
//        mWebView.setWebChromeClient(new WebChromeClient.FileChooserParams());
    }

    public static void clearWebViewCache(WebView webView) {
        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank");
        }
    }

}
