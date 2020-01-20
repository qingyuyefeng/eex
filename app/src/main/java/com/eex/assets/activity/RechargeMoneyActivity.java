package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.PhoneNameActivity;
import com.eex.home.activity.home.RealNameActivity;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.weight.MyDialog;

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
 * @Package: com.overthrow.assets.activity
 * @ClassName: RechargeMoneyActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 11:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 11:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 充值提现
 */
public class RechargeMoneyActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.RcMoney_ll)
    LinearLayout RcMoneyLl;
    /**
     *
     */
    @BindView(R.id.Tmoney_LL)
    LinearLayout TmoneyLL;
    /**
     *
     */
    @BindView(R.id.recharge_money)
    LinearLayout rechargeMoney;
    /**
     *
     */
    @BindView(R.id.TB_LL)
    LinearLayout TBLL;


    /**
     * dialog
     */
    private TextView Tltle;
    private Button btnDimss;
    private Button btnSell;
    /**
     * mMyDialog
     */
    private MyDialog mMyDialog;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_money;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("充值提现");

    }

    @Override
    protected void initUiAndListener() {

    }

    @OnClick({R.id.comtitlebar, R.id.RcMoney_ll, R.id.Tmoney_LL, R.id.recharge_money, R.id.TB_LL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            //充值
            case R.id.RcMoney_ll:
                //是否实名
                authStauts();
                break;

            //提现
            case R.id.Tmoney_LL:

                IsMoney();
                break;
            //充币
            case R.id.recharge_money:
                MoneyName();
                break;

            //提币
            case R.id.TB_LL:
                IsNameTB();
                break;
            default:
                break;
        }
    }

    /**
     * 提币
     */
    @SuppressLint("CheckResult")
    private void IsNameTB() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }
                    if (data.isStauts() == false) {

                    } else {
                        if (data.getData() != null) {

                            try {
                                if (data.getData().getLevel().equals(1)) {
                                    view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                                    Tltle = (TextView) view.findViewById(R.id.tltle);
                                    btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                                    btnSell = (Button) view.findViewById(R.id.btn_sell);
                                    mMyDialog = new MyDialog(RechargeMoneyActivity.this, 0, 0, view, R.style.DialogTheme);
                                    mMyDialog.setCancelable(true);
                                    Tltle.setText("请完成实名等级2认证");
                                    mMyDialog.show();
                                    btnDimss.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mMyDialog.dismiss();
                                        }
                                    });
                                    btnSell.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //手机认证
                                            if (kv.decodeString("phone") != null && kv.decodeString("phone").equals("")) {
                                                intent.setClass(RechargeMoneyActivity.this, RealNameActivity.class);
                                                startActivity(intent);
                                                mMyDialog.dismiss();
                                            } else {
                                                mMyDialog.dismiss();
                                                view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                                                Tltle = (TextView) view.findViewById(R.id.tltle);
                                                btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                                                btnSell = (Button) view.findViewById(R.id.btn_sell);
                                                mMyDialog = new MyDialog(RechargeMoneyActivity.this, 0, 0, view, R.style.DialogTheme);
                                                mMyDialog.setCancelable(true);
                                                Tltle.setText("请绑定手机号后交易");
                                                mMyDialog.show();
                                                btnDimss.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        mMyDialog.dismiss();
                                                    }
                                                });
                                                btnSell.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.setClass(RechargeMoneyActivity.this, PhoneNameActivity.class);
                                                        startActivity(intent);
                                                        mMyDialog.dismiss();
                                                    }
                                                });
                                            }

                                        }
                                    });
                                } else if (data.getData().getLevel().equals(2)) {
                                    intent.setClass(RechargeMoneyActivity.this, CurrencyListActivity.class);
                                    startActivity(intent);
                                } else if (data.getData().getLevel().equals(3)) {
                                    if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                        Intent intent = new Intent();
                                        intent.setClass(RechargeMoneyActivity.this, CurrencyListActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RechargeMoneyActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            } catch (Exception e) {
                                Toast.makeText(RechargeMoneyActivity.this, getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                });
    }


    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void authStauts() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {

                                    intent.setClass(RechargeMoneyActivity.this, MoneyCZListActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RechargeMoneyActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }

                            } else if (data.getData().getLevel().equals(2)) {
                                intent.setClass(RechargeMoneyActivity.this, MoneyCZListActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(1)) {

                                intent.setClass(RechargeMoneyActivity.this, MoneyCZListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RechargeMoneyActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

                    }

                });


    }


    /**
     * 充币
     */
    @SuppressLint("CheckResult")
    private void MoneyName() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("type", "1");
                        intent.setClass(RechargeMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {

                                    intent.setClass(RechargeMoneyActivity.this, RechargeBAListActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RechargeMoneyActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }

                            } else if (data.getData().getLevel().equals(2)) {
                                intent.setClass(RechargeMoneyActivity.this, RechargeBAListActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(1)) {

                                intent.setClass(RechargeMoneyActivity.this, RechargeBAListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RechargeMoneyActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    /**
     * 提现
     */
    @SuppressLint("CheckResult")
    private void IsMoney() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(1)) {
                                view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                                Tltle = (TextView) view.findViewById(R.id.tltle);
                                btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                                btnSell = (Button) view.findViewById(R.id.btn_sell);
                                mMyDialog = new MyDialog(RechargeMoneyActivity.this, 0, 0, view, R.style.DialogTheme);
                                mMyDialog.setCancelable(true);
                                Tltle.setText("请绑定手机号后交易");
                                mMyDialog.show();
                                btnDimss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMyDialog.dismiss();
                                    }
                                });
                                btnSell.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //手机认证
                                        if (kv.decodeString("phone") != null && kv.decodeString("phone").equals("")) {
                                            intent.setClass(RechargeMoneyActivity.this, RealNameActivity.class);
                                            startActivity(intent);
                                            mMyDialog.dismiss();
                                        } else {
                                            mMyDialog.dismiss();
                                            view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                                            Tltle = (TextView) view.findViewById(R.id.tltle);
                                            btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                                            btnSell = (Button) view.findViewById(R.id.btn_sell);
                                            mMyDialog = new MyDialog(RechargeMoneyActivity.this, 0, 0, view, R.style.DialogTheme);
                                            mMyDialog.setCancelable(true);
                                            Tltle.setText("请绑定手机号后交易");
                                            mMyDialog.show();
                                            btnDimss.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mMyDialog.dismiss();
                                                }
                                            });
                                            btnSell.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent();
                                                    intent.setClass(RechargeMoneyActivity.this, PhoneNameActivity.class);
                                                    startActivity(intent);
                                                    mMyDialog.dismiss();
                                                }
                                            });
                                        }

                                    }
                                });
                            } else if (data.getData().getLevel().equals(2)) {
                                intent.setClass(RechargeMoneyActivity.this, EpayRechargeListActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone")!= null && !kv.decodeString("phone").equals("")) {
                                    Intent intent = new Intent();
                                    intent.setClass(RechargeMoneyActivity.this, EpayRechargeListActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RechargeMoneyActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();

                                }
                            }


                        } catch (Exception e) {
                            Toast.makeText(RechargeMoneyActivity.this, getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

                    }

                });


    }
}
