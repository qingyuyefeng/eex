package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: TwoRealNameTypeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 15:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 15:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TwoRealNameTypeActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.TXBtc)
    TextView TXBtc;
    /**
     *
     */
    @BindView(R.id.TXcny)
    TextView TXcny;
    /**
     *
     */
    @BindView(R.id.button2)
    Button button;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_two_real_name_type;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("级别2认证完成");
        comtitlebar.setRightText("完成");

        //获取等级配置
        getlevelconfig();



    }

    /**
     * getlevelconfig
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void getlevelconfig() {

        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getlevelconfig(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if(data.isStauts()==true){
                        TXBtc.setText("您的提币额度为"+data.getData().getLevel2Btc()+"BTC(近24h)");
                        TXcny.setText("您的提现额度为"+data.getData().getLevel2Cnye()+"CNYE（近24h）");
                    }else {
                        t(data.getMsg());
                    }
                },throwable -> {

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

    @OnClick({R.id.comtitlebar, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.button2:
                //实名认证
                intent.setClass(getApplicationContext(),PhotoActivity.class);
                startActivity(intent);
                finish();
                break;

                default:
                    break;

        }
    }
}
