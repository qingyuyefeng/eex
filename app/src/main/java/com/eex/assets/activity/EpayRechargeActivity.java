package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.bean.ExchangeRate;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.home.weight.Utils;

import java.math.BigDecimal;
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
 * @ClassName: EpayRechargeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 10:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 10:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 提现
 */
public class EpayRechargeActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.bi_name)
    TextView biName;
    /**
     *
     */
    @BindView(R.id.LL_CNTE)
    LinearLayout LLCNTE;
    /**
     *
     */
    @BindView(R.id.edt_Epay)
    EditText edtEpay;
    /**
     *
     */
    @BindView(R.id.edt_Nuber)
    EditText edtNuber;
    /**
     *
     */
    @BindView(R.id.tx_moneyNuber)
    TextView txMoneyNuber;
    /**
     *
     */
    @BindView(R.id.tx_chargeMaxMinNum)
    TextView txChargeMaxMinNum;
    /**
     *
     */
    @BindView(R.id.me_Money)
    TextView meMoney;
    /**
     *
     */
    @BindView(R.id.tx_moneyPercentage)
    TextView txMoneyPercentage;
    /**
     *
     */
    @BindView(R.id.tx_sever)
    TextView txSever;
    /**
     *
     */
    @BindView(R.id.tx_zongjiNuber)
    TextView txZongjiNuber;
    /**
     *
     */
    @BindView(R.id.tx_sj_Nuber)
    TextView txSjNuber;
    /**
     *
     */
    @BindView(R.id.ExchangeRate)
    TextView exchangeRate;
    /**
     *
     */
    @BindView(R.id.ckb_put)
    CheckBox ckbPut;
    /**
     *
     */
    @BindView(R.id.tx_tltle)
    TextView txTltle;
    /**
     *
     */
    @BindView(R.id.btn_next)
    Button btnNext;


    private String currency;
    private String currencyStr;
    private String withdrawFee;
    private String withdrawMaxNum;
    private String withdrawMinNum;
    private String Nuber = "";
    private String isType = "phone";
    private TimeCount time;
    private Dialog dialog;

    private ExchangeRate list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_epay_recharge;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.tixian));
        comtitlebar.setImageView(R.drawable.chongzhijilu);

        if (getIntent().getStringExtra("currency") != null) {
            currency = getIntent().getStringExtra("currency");
            txMoneyNuber.setText(currency);
            txSever.setText(0 + currency);
            txZongjiNuber.setText(0 + currency);
            txSjNuber.setText(0 + "CNYE");
        }
        if (getIntent().getStringExtra("currencyStr") != null) {
            currencyStr = getIntent().getStringExtra("currencyStr");
            comtitlebar.setTitle(currencyStr + "提现");
            txTltle.setText("提现成功后,按照国际实时汇率将CNYE换算成等值" + currencyStr + "打款到用户EPAY账户");
            biName.setText(currencyStr);
            withdrawFee = getIntent().getStringExtra("withdrawFee");
            BigDecimal chage = new BigDecimal(withdrawFee);
            BigDecimal chage1 = chage.multiply(new BigDecimal("100"));
            txMoneyPercentage.setText("(" + chage1.stripTrailingZeros().toPlainString() + "%)");
            withdrawMaxNum = getIntent().getStringExtra("withdrawMaxNum");
            withdrawMinNum = getIntent().getStringExtra("withdrawMinNum");
            txChargeMaxMinNum.setText("单笔提现限额: " + withdrawMinNum + currency + "-" + withdrawMaxNum + currency);
        }
//        获取指定汇率
        net();

        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(EpayRechargeActivity.this, WithdrawalsListActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 获取指定汇率
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("currency", currency);
        ApiFactory.getInstance()
                .ExchangeRat(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        list = data.getData();
                        GetUser();
                        exchangeRate.setText("1" + data.getData().getCode() + "≈" + data.getData().getRefePrice() + "CNYE");
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }

    /**
     *
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void GetUser() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        BigDecimal newdata = new BigDecimal(list.getRefePrice());
                        BigDecimal MeOney = (data.getData().getUseMoneyCny().divide(newdata, 10, BigDecimal.ROUND_HALF_DOWN));
                        meMoney.setText("可用CNYE:" + data.getData().getUseMoneyCny().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + ",约" + MeOney.setScale(2, BigDecimal.ROUND_CEILING).stripTrailingZeros().toPlainString() + currency);
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {

        edtNuber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Nuber = edtNuber.getText().toString().trim();
                if (!Nuber.equals("")) {
                    BigDecimal severMoney = new BigDecimal(withdrawFee).multiply(new BigDecimal(Nuber));
                    txSever.setText(severMoney.setScale(3, BigDecimal.ROUND_DOWN) + currency);
                    BigDecimal Reat1 = new BigDecimal(Nuber).subtract(new BigDecimal(withdrawFee).multiply(new BigDecimal(Nuber)));
                    txZongjiNuber.setText(Reat1.setScale(6, BigDecimal.ROUND_DOWN) + currency);
                    if (list != null) {
                        BigDecimal Reat = new BigDecimal(Nuber).multiply(new BigDecimal(list.getRefePrice()));
                        txSjNuber.setText(Reat.setScale(6, BigDecimal.ROUND_DOWN) + "CNYE");
                    } else {
                        txSjNuber.setText("0");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.bi_name, R.id.LL_CNTE, R.id.edt_Epay, R.id.edt_Nuber, R.id.tx_moneyNuber, R.id.tx_chargeMaxMinNum, R.id.me_Money, R.id.tx_moneyPercentage, R.id.tx_sever, R.id.tx_zongjiNuber, R.id.tx_sj_Nuber, R.id.ExchangeRate, R.id.ckb_put, R.id.tx_tltle, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.bi_name:
                break;
            case R.id.LL_CNTE:
                break;
            case R.id.edt_Epay:
                break;
            case R.id.edt_Nuber:
                break;
            case R.id.tx_moneyNuber:
                break;
            case R.id.tx_chargeMaxMinNum:
                break;
            case R.id.me_Money:
                break;
            case R.id.tx_moneyPercentage:
                break;
            case R.id.tx_sever:
                break;
            case R.id.tx_zongjiNuber:
                break;
            case R.id.tx_sj_Nuber:
                break;
            case R.id.ExchangeRate:
                break;
            case R.id.ckb_put:
                break;
            case R.id.tx_tltle:
                if (ckbPut.isChecked()) {
                    ckbPut.setChecked(false);
                } else {
                    ckbPut.setChecked(true);
                }
                break;
            case R.id.btn_next:

                if (Nuber.equals("")) {
                    Toast.makeText(EpayRechargeActivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(Nuber).compareTo(new BigDecimal(withdrawMaxNum)) == 1) {
                    Toast.makeText(EpayRechargeActivity.this, "单笔最多提现" + withdrawMaxNum + currency, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(Nuber).compareTo(new BigDecimal(withdrawMinNum)) == -1) {
                    Toast.makeText(EpayRechargeActivity.this, "单笔最少提现" + withdrawMinNum + currency, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(Nuber).compareTo(BigDecimal.ZERO) == 0) {
                    Toast.makeText(EpayRechargeActivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtEpay.getText().toString().trim().equals("")) {
                    Toast.makeText(EpayRechargeActivity.this, "请输入EPAY账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ckbPut.isChecked() == false) {
                    Toast.makeText(EpayRechargeActivity.this, "请勾选提现须知", Toast.LENGTH_SHORT).show();
                    return;
                }
                DiaLog();
                break;
            default:
                break;
        }
    }

    private void DiaLog() {


        dialog = new Dialog(EpayRechargeActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(EpayRechargeActivity.this).inflate(R.layout.item_epay_tixia, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final EditText moneyPword = (EditText) dialogView.findViewById(R.id.moneyPword);
        moneyPword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        final EditText edt_YZM = (EditText) dialogView.findViewById(R.id.edt_YZM);
        final Button btn_YZM = (Button) dialogView.findViewById(R.id.btn_YZM);
        Button button = (Button) dialogView.findViewById(R.id.button);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                HashMap<String, String> requestParam = new HashMap<>();
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(EpayRechargeActivity.this, "请输入手机验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (moneyPword.getText().toString().trim().equals("")) {
                    Toast.makeText(EpayRechargeActivity.this, "请输入交易密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestParam.put("checkType", "1");
                Log.e("Utils", moneyPword.getText().toString().trim());
                requestParam.put("accountPassWord", Utils.md5(moneyPword.getText().toString().trim() + "hello, moto"));
                requestParam.put("dealAmount", Nuber);
                requestParam.put("phoneoremail",kv.decodeString("phone"));
                requestParam.put("currency", currency);
                requestParam.put("epayAccount", edtEpay.getText().toString().trim());
                requestParam.put("code", edt_YZM.getText().toString().trim());

                ApiFactory.getInstance()
                        .extractOther(kv.decodeString("tokenId"),requestParam)
                        .compose(RxSchedulers.io_main())
                        .subscribe(data -> {
                            if (data.getCode() == 200) {
                                dialog.dismiss();
                                intent.putExtra("ViewType", "ReflectYes");
                                intent.putExtra("ReflectName", currencyStr);
                                intent.setClass(EpayRechargeActivity.this, PaymentTypeActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (data.getCode() == 1030) {
                                dialog.dismiss();

                                intent.putExtra("ViewType", "ReflectNo");
                                intent.setClass(EpayRechargeActivity.this, PaymentTypeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(EpayRechargeActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }, throwable -> {

                        });


            }
        });
        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                HashMap<String, String> requestParam = new HashMap<>();
                requestParam.put("sendType", "sms_take_money");
                requestParam.put("phone",kv.decodeString("phone"));
                requestParam.put("userName",kv.decodeString("username"));


                ApiFactory.getInstance()
                        .send(kv.decodeString("tokenId"),requestParam)
                        .compose(RxSchedulers.io_main())
                        .subscribe(data -> {
                            if (data.getCode() == 200) {
                                //构造CountDownTimer对象
                                time = new TimeCount(EpayRechargeActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                time.start();
                                t(data.getMsg());
                            } else {
                                t(data.getMsg());
                            }
                        }, throwable -> {

                        });

            }
        });
    }
}
