package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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
 * @ClassName: noTradActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/3 12:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/3 12:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 修改商家姓名
 */
public class noTradActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.notd_ed)
    EditText notdEd;
    /**
     *
     */
    @BindView(R.id.Trade_sell)
    Button TradeSell;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notrad;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("修改商家名称");


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

    @OnClick({R.id.comtitlebar, R.id.Trade_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.Trade_sell:

                if (notdEd.getText().toString().trim() == null) {
                    t("请输入修改商家名称");
                }

                if (notdEd.getText().toString().trim().length() > 5) {
                    t("不能超多五个字符");
                }

                sure(notdEd.getText().toString().trim());

                break;
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void sure(String trim) {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("nickName", notdEd.getText().toString().trim());
        ApiFactory.getInstance()
                .ctcNickName(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {

                        finish();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });


    }
}
