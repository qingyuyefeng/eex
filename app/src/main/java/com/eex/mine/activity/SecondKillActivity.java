package com.eex.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.CentsRedActivity;
import com.eex.home.activity.home.MeMiningListActivity;
import com.eex.home.activity.home.MoneyListActivity;
import com.eex.home.activity.home.SecondRecordListActivity;

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
 * @ClassName: SecondKillActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 9:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 9:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 * 理财中心
 */
public class SecondKillActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.RcMoney_ll1)
    LinearLayout RcMoneyLl1;
    /**
     *
     */
    @BindView(R.id.RcMoney_ll2)
    LinearLayout RcMoneyLl2;
    /**
     *
     */
    @BindView(R.id.RcMoney_RedMoney)
    LinearLayout RcMoneyRedMoney;
    /**
     *
     */
    @BindView(R.id.RcMoney_wakuang)
    LinearLayout RcMoneyWakuang;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_kill;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("理财中心");

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

    @OnClick({R.id.comtitlebar, R.id.RcMoney_ll1, R.id.RcMoney_ll2, R.id.RcMoney_RedMoney, R.id.RcMoney_wakuang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.RcMoney_ll1:
                if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord").equals("")) {
                    Toast.makeText(SecondKillActivity.this, "请设置交易密码后再操作", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(SecondKillActivity.this, MoneyListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.RcMoney_ll2:
                intent.setClass(SecondKillActivity.this, SecondRecordListActivity.class);
                startActivity(intent);
                break;
            case R.id.RcMoney_RedMoney:
                if (kv.decodeString("tokenId") == null) {
                    Toast.makeText(SecondKillActivity.this,
                            "请登录后操作", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(SecondKillActivity.this, CentsRedActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.RcMoney_wakuang:
                if (kv.decodeString("tokenId") != null) {
                    intent.setClass(SecondKillActivity.this, MeMiningListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SecondKillActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
