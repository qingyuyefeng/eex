package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.Merchdealaccount;

import java.util.ArrayList;
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
 * @ClassName: C2CSetMoneyActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 17:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 17:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 收付款设置
 */
public class C2CSetMoneyActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.bankType)
    TextView bankType;
    /**
     *
     */
    @BindView(R.id.c2c_Bank)
    LinearLayout c2cBank;
    /**
     *
     */
    @BindView(R.id.weixinType)
    TextView weixinType;
    /**
     *
     */
    @BindView(R.id.C2C_weixin)
    LinearLayout C2CWeixin;
    /**
     *
     */
    @BindView(R.id.zhifubaoType)
    TextView zhifubaoType;
    /**
     *
     */
    @BindView(R.id.c2c_zhifubao)
    LinearLayout c2cZhifubao;

    private ArrayList<Merchdealaccount> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2_cset_money;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("收付款设置");
        //c2c查询用户是否绑定支付方式
        net();
    }


    /**
     * c2c查询用户是否绑定支付方式
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        ApiFactory.getInstance()
                .merchdealaccount(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {

                        list.clear();
                        list.addAll(data.getData());

                        if (data.getData() != null && data.getData().size() != 0) {
                            if (data.getData().get(0).getAccountType() == 1) {
                                bankType.setText("修改");
                            } else if (data.getData().get(0).getAccountType() == 2) {
                                zhifubaoType.setText("修改");
                            } else {
                                weixinType.setText("修改");
                            }


                            try {
                                if (data.getData().get(1).getAccountType() == 1) {
                                    bankType.setText("修改");
                                } else if (data.getData().get(1).getAccountType() == 2) {
                                    zhifubaoType.setText("修改");
                                } else {
                                    weixinType.setText("修改");
                                }

                            } catch (Exception e) {

                            }


                            try {
                                if (data.getData().get(2).getAccountType() == 1) {
                                    bankType.setText("修改");
                                } else if (data.getData().get(2).getAccountType() == 2) {
                                    zhifubaoType.setText("修改");
                                } else {
                                    weixinType.setText("修改");
                                }
                            } catch (Exception e) {

                            }

                        } else {
                            bankType.setText("未绑定");
                            zhifubaoType.setText("未绑定");
                            weixinType.setText("未绑定");
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

    @OnClick({R.id.comtitlebar, R.id.c2c_Bank, R.id.C2C_weixin, R.id.c2c_zhifubao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.c2c_Bank:

                if (list != null && list.size() != 0) {
                    intent.putExtra("userName", list.get(0).getUserName());
                    intent.putExtra("accountNo", list.get(0).getAccountNo());
                    intent.putExtra("bankName", list.get(0).getBankName());
                    intent.putExtra("childBankName", list.get(0).getChildBankName());
                    intent.putExtra("bankAddress", list.get(0).getBankAddress());
                    intent.putExtra("id", list.get(0).getId());
                }
                intent.setClass(C2CSetMoneyActivity.this, C2CBankActivity.class);
                startActivity(intent);
                break;
            case R.id.C2C_weixin:
                if (!weixinType.getText().toString().equals("修改")) {
                    intent.putExtra("imageUrl", "no");
                    intent.setClass(C2CSetMoneyActivity.this, C2CWeiXinActivity.class);
                    startActivity(intent);
                } else {
                    if (list.get(0).getAccountType() == 3) {
                        intent.putExtra("id", list.get(0).getId());
                        intent.putExtra("imageUrl", list.get(0).getImageUrl());
                        intent.putExtra("userName", list.get(0).getUserName());
                        intent.putExtra("accountNo", list.get(0).getAccountNo());
                        intent.setClass(C2CSetMoneyActivity.this, C2CWeiXinActivity.class);
                        startActivity(intent);
                    } else if (list.get(1).getAccountType() == 3) {
                        intent.putExtra("id", list.get(1).getId());
                        intent.putExtra("imageUrl", list.get(1).getImageUrl());
                        intent.putExtra("userName", list.get(1).getUserName());
                        intent.putExtra("accountNo", list.get(1).getAccountNo());
                        intent.setClass(C2CSetMoneyActivity.this, C2CWeiXinActivity.class);
                        startActivity(intent);

                    } else if (list.get(2).getAccountType() == 3) {
                        intent.putExtra("id", list.get(2).getId());
                        intent.putExtra("imageUrl", list.get(2).getImageUrl());
                        intent.putExtra("userName", list.get(2).getUserName());
                        intent.putExtra("accountNo", list.get(2).getAccountNo());
                        intent.setClass(C2CSetMoneyActivity.this, C2CWeiXinActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.c2c_zhifubao:

                if (!zhifubaoType.getText().toString().trim().equals("修改")) {
                    intent.putExtra("imageUrl", "no");
                    intent.setClass(C2CSetMoneyActivity.this, C2CZhiFuBaoActivity.class);
                    startActivity(intent);
                } else {
                    if (list.get(0).getAccountType() == 2) {

                        intent.putExtra("imageUrl", list.get(0).getImageUrl());
                        intent.putExtra("userName", list.get(0).getUserName());
                        intent.putExtra("accountNo", list.get(0).getAccountNo());
                        intent.setClass(C2CSetMoneyActivity.this, C2CZhiFuBaoActivity.class);
                        startActivity(intent);

                    } else if (list.get(1).getAccountType() == 2) {
                        intent.putExtra("id", list.get(1).getId());
                        intent.putExtra("imageUrl", list.get(1).getImageUrl());
                        intent.putExtra("userName", list.get(1).getUserName());
                        intent.putExtra("accountNo", list.get(1).getAccountNo());
                        intent.setClass(C2CSetMoneyActivity.this, C2CZhiFuBaoActivity.class);
                        startActivity(intent);
                    } else if (list.get(2).getAccountType() == 2) {
                        intent.putExtra("imageUrl", list.get(2).getImageUrl());
                        intent.putExtra("userName", list.get(2).getUserName());
                        intent.putExtra("accountNo", list.get(2).getAccountNo());
                        intent.setClass(C2CSetMoneyActivity.this, C2CZhiFuBaoActivity.class);
                        startActivity(intent);
                    }
                }

                break;
            default:
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        net();
    }


}
