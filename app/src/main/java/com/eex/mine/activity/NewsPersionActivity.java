package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;

import java.util.HashMap;

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
 * @Package: com.overthrow.mine.activity
 * @ClassName: NewsPersionActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/4 11:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/4 11:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewsPersionActivity extends BaseActivity {

    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    @BindView(R.id.tx_name)
    WebView txName;


    public String id = "";
    private String opendid = "";
    private String newId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_persion;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("详情");
        if (getIntent().getStringExtra("id") != null && getIntent().getStringExtra("id") != "") {
            id = getIntent().getStringExtra("id");

        }
//        if (getIntent().getStringExtra("opendid") != null && getIntent().getStringExtra("opendid") != "") {
        opendid = getIntent().getStringExtra("opendid");
//            NewSdETELDataVO dataVO = JsonUtil.fromJson(opendid, NewSdETELDataVO.class);
//            String msg = dataVO.getMsg();
//            newId = msg.substring(msg.lastIndexOf(",") + 1);
//        }

        net();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("categoryId", id);
        hashMap.put("website", "cn");

        ApiFactory.getInstance()
                .getone(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        String text = data.getData().getContent();
                        txName.getSettings().setJavaScriptEnabled(true);
                        txName.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
                        txName.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    }
                }, throwable -> {

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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
