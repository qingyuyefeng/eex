package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.bean.Personal;
import com.eex.assets.bean.Sever;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.PhoneNameActivity;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.weight.MyDialog;

import java.math.BigDecimal;
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
 * @Package: com.overthrow.assets.activity
 * @ClassName: WithdrawalsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 17:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 17:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 人民币提现
 */
public class WithdrawalsActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_hotMoney)
    TextView txHotMoney;
    /**
     *
     */
    @BindView(R.id.textview_Bank)
    TextView textviewBank;
    /**
     *
     */
    @BindView(R.id.Bank_LL)
    LinearLayout BankLL;
    /**
     *
     */
    @BindView(R.id.ed_overMoney)
    EditText edOverMoney;
    /**
     *
     */
    @BindView(R.id.tx_severMoney)
    TextView txSeverMoney;
    /**
     *
     */
    @BindView(R.id.btn_PutMoney)
    Button btnPutMoney;


    private View view;
    private TextView Tltle;
    private Button btnDimss;
    private Button btnSell;
    private MyDialog mMyDialog;
    private String isType = "phone";


    private String cardBank;
    private String surname;
    private String givename;
    private String subBank;
    private String id;
    private TimeCount time;
    private ArrayList<Personal> list = new ArrayList<Personal>();
    private Sever sever;
    private PopupWindow popupWindow;

    private TextView txvew_phone;
    private TextView textview_Email;
    private EditText edt_YZM;
    private Button btn_YZM;
    private Button button;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawals;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setImageView(R.drawable.cq_recharge_zd);
        comtitlebar.setTitle("人民币提现");
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(WithdrawalsActivity.this, WithdrawalsListActivity.class);
                startActivity(intent);
            }
        });
        getid();

    }

    protected void getid() {

        edOverMoney.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
//                Log.e("输入过程中执行该方法", "文字变化");
                try {
                    BigDecimal b1 = new BigDecimal(edOverMoney.getText().toString());
                    BigDecimal ServiceMoney = sever.getServiceMoney();
                    BigDecimal b3 = b1.multiply(new BigDecimal(new java.text.DecimalFormat("#.000").format(ServiceMoney)));
                    BigDecimal B4 = b1.subtract(b3);
                    txSeverMoney.setText("手续费金额为" + b3.setScale(2, BigDecimal.ROUND_DOWN) + "元," + "实际到账金额为" + B4.setScale(2, BigDecimal.ROUND_DOWN));

//                    tx_severMoney.setText(getResources().getString(R.string.serds)+b3.setScale(3,BigDecimal.ROUND_CEILING)+"元");
                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
//                Log.e("输入前确认执行该方法", "开始输入");

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
//                Log.e("输入结束执行该方法", "输入结束");

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

    @OnClick({R.id.comtitlebar, R.id.tx_hotMoney, R.id.textview_Bank, R.id.Bank_LL, R.id.ed_overMoney, R.id.tx_severMoney, R.id.btn_PutMoney})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_hotMoney:
                break;
            case R.id.textview_Bank:
                break;
            case R.id.Bank_LL:
                intent.setClass(WithdrawalsActivity.this, BankActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, 2000);
                break;
            case R.id.ed_overMoney:
                break;
            case R.id.tx_severMoney:
                break;
            case R.id.btn_PutMoney:
                if (textviewBank.getText().toString().trim().equals("请选择收款账户")) {
                    Toast.makeText(WithdrawalsActivity.this, "请选择收款账户", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edOverMoney.getText().toString().trim().equals("")) {
                    Toast.makeText(WithdrawalsActivity.this, R.string.qingshuru, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(edOverMoney.getText().toString().trim()) < 500) {
                    Toast.makeText(WithdrawalsActivity.this, R.string.nuberdaxiao, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                    showPopueWindow();
                } else {
                    isPhone();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void showPopueWindow() {

        View popView = View.inflate(WithdrawalsActivity.this, R.layout.popupwindow_timoney, null);
        txvew_phone = (TextView) popView.findViewById(R.id.txvew_phone);
        textview_Email = (TextView) popView.findViewById(R.id.textview_Email);
        edt_YZM = (EditText) popView.findViewById(R.id.edt_YZM);
        btn_YZM = (Button) popView.findViewById(R.id.btn_YZM);
        button = (Button) popView.findViewById(R.id.button);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);


        if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
            isType = "phone";
            txvew_phone.setText("手机");
            btn_YZM.setVisibility(View.VISIBLE);
        } else if (kv.decodeInt("googleState") == 1) {
            isType = "google";
            txvew_phone.setText("谷歌");
            btn_YZM.setVisibility(View.GONE);
        }
//            else {
//                isType = "email";
//                txvew_phone.setText("邮箱");
//                btn_YZM.setVisibility(View.VISIBLE);
//            }

        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {

                if (isType.equals("phone")) {
                    //电话发送提币验证码
                    HashMap<String, String> requestParam = new HashMap<>();
                    requestParam.put("sendType", "sms_take_money");
                    requestParam.put("phone", kv.decodeString("phone"));

                    ApiFactory.getInstance()
                            .email(kv.decodeString("tokenId"), requestParam)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {
                                if (data.isStauts() == true) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(WithdrawalsActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                    time.start();
                                    Toast.makeText(WithdrawalsActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    t(data.getMsg());
                                }
                            }, throwable -> {

                            });

                } else if (isType.equals("email")) {
                    //邮箱发送提币验证码
                    HashMap<String, String> requestParam = new HashMap<>();
                    requestParam.put("sendType", "5");
                    requestParam.put("email", kv.decodeString("email"));

                    ApiFactory.getInstance()
                            .email(kv.decodeString("tokenId"), requestParam)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {
                                if (data.isStauts() == true) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(WithdrawalsActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                    time.start();
                                    Toast.makeText(WithdrawalsActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    t(data.getMsg());
                                }
                            }, throwable -> {

                            });
                }

            }
        });
        //提现
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(WithdrawalsActivity.this, R.string.login_yzm, Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> requestParam = new HashMap<>();
                try {
                    if (isType.equals("phone")) {
                        //1手机
                        requestParam.put("checkType", "1");
                        requestParam.put("phoneoremail", kv.decodeString("phone"));
                        //银行名称
                        requestParam.put("bankName", cardBank);
//                            requestParam.put("userBankCardNo", subBank);//银行卡号
                        //银行卡号
                        requestParam.put("bankCardId", id);
                        //姓
                        requestParam.put("lastName", surname);
                        //名
                        requestParam.put("firstName", givename);
                        //金额
                        requestParam.put("dealAmount", edOverMoney.getText().toString().trim());
                        requestParam.put("code", edt_YZM.getText().toString().trim());
                    } else {
                        //银行名称
                        requestParam.put("bankName", cardBank);
//                            requestParam.put("userBankCardNo", subBank);//银行卡号
                        //银行卡号
                        requestParam.put("bankCardId", id);

                        //姓
                        requestParam.put("lastName", surname);
                        //名
                        requestParam.put("firstName", givename);
                        //金额
                        requestParam.put("dealAmount", edOverMoney.getText().toString().trim());
                        requestParam.put("checkType", "2");
                        requestParam.put("code", edt_YZM.getText().toString().trim());
                        requestParam.put("googleKey", kv.decodeString("googleKey"));

                        ApiFactory.getInstance()
                                .extract(kv.decodeString("tokenId"), requestParam)
                                .compose(RxSchedulers.io_main())
                                .subscribe(data -> {
                                    if (data.isStauts() == true) {

                                        //可用余额
                                        getMoneyData();
                                        edOverMoney.setText("");
                                        Toast.makeText(WithdrawalsActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                        popupWindow.dismiss();
                                    } else {
                                        t(data.getMsg());
                                    }
                                }, throwable -> {

                                });
                    }

                } catch (Exception e) {
                    Toast.makeText(WithdrawalsActivity.this, R.string.bangding, Toast.LENGTH_SHORT).show();
                }
                //银行名称
                requestParam.put("bankName", cardBank);
                //银行卡号
                requestParam.put("bankCardId", id);
                //姓
                requestParam.put("lastName", surname);
                //名
                requestParam.put("firstName", givename);
                //金额
                requestParam.put("dealAmount", edOverMoney.getText().toString().trim());
                requestParam.put("code", edt_YZM.getText().toString().trim());


                ApiFactory.getInstance()
                        .extract(kv.decodeString("tokenId"), requestParam)
                        .compose(RxSchedulers.io_main())
                        .subscribe(data -> {
                            if (data.isStauts() == true) {

                                //可用余额
                                getMoneyData();
                                edOverMoney.setText("");
                                Toast.makeText(WithdrawalsActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                            } else {
                                t(data.getMsg());
                            }
                        }, throwable -> {

                        });

            }

        });

        popupWindow.dismiss();
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
         popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 50);
        BackgroudAlpha((float) 0.5);
        popupWindow.setOnDismissListener(new WithdrawalsActivity.popupwindowdismisslistener());

    }


    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = getWindow().getAttributes();
        l.alpha = alpha;
        getWindow().setAttributes(l);
    }

    /**
     * 点击其他部分popwindow消失时，屏幕恢复透明度
     */
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            BackgroudAlpha((float) 1);
        }
    }

    /**
     * 可用余额
     */
    @SuppressLint("CheckResult")
    private void getMoneyData() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        list = data.getData().getUserCoinAccount();
                        txHotMoney.setText(data.getData().getUseMoneyCny().setScale(2, BigDecimal.ROUND_DOWN).toString());

                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }


    private void isPhone() {
        view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
        Tltle = (TextView) view.findViewById(R.id.tltle);
        btnDimss = (Button) view.findViewById(R.id.btn_dimss);
        btnSell = (Button) view.findViewById(R.id.btn_sell);
        mMyDialog = new MyDialog(WithdrawalsActivity.this, 0, 0, view, R.style.DialogTheme);
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
                intent.setClass(WithdrawalsActivity.this, PhoneNameActivity.class);
                startActivity(intent);
                mMyDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //可用余额
        getMoneyData();
        //手续费
        GetServiceMoney();

    }

    @SuppressLint("CheckResult")
    private void GetServiceMoney() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .cnyaccount(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {


                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(WithdrawalsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(WithdrawalsActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }
                    if (data.getData() != null) {

                        sever = data.getData();
                        BigDecimal ServiceMoney = data.getData().getServiceMoney();

                        txSeverMoney.setText("手续费金额0元" + "实际到账金额0元");

                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (data != null) {
                cardBank = data.getStringExtra("cardBank");
                subBank = data.getStringExtra("subBank");
                surname = data.getStringExtra("surname");
                givename = data.getStringExtra("givename");
                id = data.getStringExtra("id");
                Log.e("bank", cardBank + "--" + subBank + "--" + surname + "--" + givename);
                textviewBank.setText(subBank);
            }

        }
    }


}
