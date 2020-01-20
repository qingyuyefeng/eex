package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.activity.BankActivity;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.C2CSetMoneyActivity;

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
 * @ClassName: ReceivablesActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 15:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 15:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 收付款设置
 */
public class ReceivablesActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.set_BANK_LL)
    LinearLayout setBANKLL;
    /**
     *
     */
    @BindView(R.id.C2C_LL)
    LinearLayout C2CLL;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receivables;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("收付款设置");
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

    @OnClick({R.id.comtitlebar, R.id.set_BANK_LL, R.id.C2C_LL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.set_BANK_LL:
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(ReceivablesActivity.this, BankActivity.class);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                } else {
                    Toast.makeText(ReceivablesActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.C2C_LL:
                IsNameBank();
                break;
            default:
                break;
        }
    }

    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void IsNameBank() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    try {
                        if (data.getData().getLevel().equals(1)) {
                            Toast.makeText(ReceivablesActivity.this, "请实名认证等级2后操作", Toast.LENGTH_SHORT).show();
                        } else if (data.getData().getLevel().equals(2)) {

                            intent.setClass(ReceivablesActivity.this, C2CSetMoneyActivity.class);
                            startActivity(intent);
                        } else if (data.getData().getLevel().equals(3)) {
                            Intent intent = new Intent();
                            intent.setClass(ReceivablesActivity.this, C2CSetMoneyActivity.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {

                    }
                }, throwable -> {

                });


    }
}
