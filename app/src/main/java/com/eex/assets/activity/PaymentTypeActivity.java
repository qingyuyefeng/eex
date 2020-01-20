package com.eex.assets.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;

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
 * @ClassName: PaymentTypeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PaymentTypeActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.imgview_type)
    ImageView imgviewType;
    /**
     *
     */
    @BindView(R.id.texview_type)
    TextView texviewType;
    /**
     *
     */
    @BindView(R.id.textview_Explain1)
    TextView textviewExplain1;
    /**
     *
     */
    @BindView(R.id.textview_Explain2)
    TextView textviewExplain2;


    private String ViewType;
    /**
     * 提现币种名称比如日元，美元，英鎊
     */
    private String ReflectName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_type;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        if (getIntent().getStringExtra("ViewType") != null) {
            ViewType = getIntent().getStringExtra("ViewType");
        }
        //充值成功
        if (ViewType.equals("RechargeYes")) {
            comtitlebar.setTitle("充值成功");
            imgviewType.setImageResource(R.drawable.chenggong);
            texviewType.setText("充值成功,等待审核");
            textviewExplain1.setText("平台将于24h内完成充值审核,");
            textviewExplain2.setVisibility(View.VISIBLE);
            textviewExplain2.setText("审核通过后会将CNYE直接充值到您的资金账户中");
        }
        //充值失败
        if (ViewType.equals("RechargeNo")) {
            comtitlebar.setTitle("充值失败");
            imgviewType.setImageResource(R.drawable.shibai);
            texviewType.setText("充值失败");
            textviewExplain1.setText("余额不足");
            textviewExplain2.setVisibility(View.GONE);
        }
        //体现成功
        if (ViewType.equals("ReflectYes")) {
            comtitlebar.setTitle("提现成功");
            ReflectName = getIntent().getStringExtra("ReflectName");
            imgviewType.setImageResource(R.drawable.chenggong);
            texviewType.setText("提现成功,等待审核");
            textviewExplain1.setText("平台将于24h内完成体现审核,审核通过后会将CNYE换算成等值的" + ReflectName + "打款到您的EPAY账户");
        }
        //体现失败
        if (ViewType.equals("ReflectNo")) {
            comtitlebar.setTitle("提现失败");
            imgviewType.setImageResource(R.drawable.shibai);
            texviewType.setText("提现失败");
            textviewExplain1.setText("余额不足");
            textviewExplain2.setVisibility(View.GONE);
        }

        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转充值选币页面
                if (ViewType.equals("RechargeYes") || ViewType.equals("RechargeNo")) {
                    intent.setClass(PaymentTypeActivity.this, MoneyCZListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //跳转提现选币页面
                    intent.setClass(PaymentTypeActivity.this, EpayRechargeListActivity.class);
                    startActivity(intent);
                    finish();
                }
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

    @OnClick({R.id.comtitlebar, R.id.imgview_type, R.id.texview_type, R.id.textview_Explain1, R.id.textview_Explain2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.imgview_type:
                break;
            case R.id.texview_type:
                break;
            case R.id.textview_Explain1:
                break;
            case R.id.textview_Explain2:
                break;

            default:
                break;
        }
    }
}
