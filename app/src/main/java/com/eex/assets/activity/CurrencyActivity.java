package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.bean.BiUserData;

import java.math.BigDecimal;
import java.util.HashMap;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import retrofit2.Response;


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
 * @ClassName: CurrencyActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 10:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 10:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 转出 name
 */
public class CurrencyActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_availableName)
    TextView txAvailableName;
    /**
     *
     */
    @BindView(R.id.tx_available)
    TextView txAvailable;
    /**
     *
     */
    @BindView(R.id.tx_FrozenName)
    TextView txFrozenName;
    /**
     *
     */
    @BindView(R.id.tx_Frozen)
    TextView txFrozen;
    /**
     *
     */
    @BindView(R.id.tx_title)
    TextView txTitle;
    /**
     *
     */
    @BindView(R.id.currency_BiAddressLL)
    LinearLayout currencyBiAddressLL;
    /**
     *
     */
    @BindView(R.id.ed_biNuber)
    EditText edBiNuber;
    /**
     *
     */
    @BindView(R.id.edt_Remark)
    EditText edtRemark;
    /**
     *
     */
    @BindView(R.id.LLtag)
    LinearLayout LLtag;
    /**
     *
     */
    @BindView(R.id.tc_sever)
    TextView tcSever;
    /**
     *
     */
    @BindView(R.id.btn_OK)
    Button btnOK;
    /**
     *
     */
    @BindView(R.id.tx_severnuber)
    TextView txSevernuber;
    /**
     *
     */
    @BindView(R.id.tx_sever_nuberset)
    TextView txSeverNuberset;


    /**
     * 1代表是这个币0不是
     */
    private int tag = 0;
    private String isType = "phone";
    private TimeCount time;
    private Integer type;
    private String Data;

    private String name, currencyRate, fixedRate, coinMax, coinMin;

    private BiUserData bidata;

    private Dialog dialog;
    private TextView txvew_phone;
    private TextView textview_Email;
    private EditText edt_YZM;
    private Button btn_YZM;
    private Button button;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_currency;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (getIntent().getStringExtra("Bname") != null) {
            name = getIntent().getStringExtra("Bname");
            fixedRate = getIntent().getStringExtra("fixedRate");
            currencyRate = getIntent().getStringExtra("currencyRate");
            coinMax = getIntent().getStringExtra("coinMax");
            coinMin = getIntent().getStringExtra("coinMin");
        } else {
            name = "币";
        }

        if (name.equals("XLM") || name.equals("XRP")) {
            tag = 1;
            LLtag.setVisibility(View.VISIBLE);
        } else {
            tag = 0;
            LLtag.setVisibility(View.GONE);
        }

        net();

        init();

        comtitlebar.setImageView(R.drawable.cq_recharge_zd);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("coin", name);
                intent.setClass(CurrencyActivity.this, CurrencylistDataActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        BigDecimal newcurrencyRate = new BigDecimal(currencyRate + "");
        Double d1 = newcurrencyRate.doubleValue();
        txSeverNuberset.setText(getResources().getString(R.string.name1) + name + getResources().getString(R.string.name2) + name + getResources().getString(R.string.name3) + name + getResources().getString(R.string.name4) + name + getResources().getString(R.string.name5));
        txSevernuber.setText(getResources().getString(R.string.name6) + coinMin + getResources().getString(R.string.ge) + name + getResources().getString(R.string.name7) + coinMax + getResources().getString(R.string.ge) + name);
        tcSever.setText(getActivity().getResources().getString(R.string.wanglusever) + d1 + name);

        txAvailableName.setText(getResources().getString(R.string.keyong1) + name + ":");
        txFrozenName.setText(getResources().getString(R.string.dongjie1) + name + ":");
        comtitlebar.setTitle(getResources().getString(R.string.zhuanchu1) + name);


        edBiNuber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    BigDecimal b1 = new BigDecimal(currencyRate);
                    BigDecimal b2 = new BigDecimal(edBiNuber.getText().toString().trim());
                    BigDecimal b3 = b1.multiply(b2);
                    BigDecimal b4 = b3.add(new BigDecimal(fixedRate));
                    tcSever.setText(getResources().getString(R.string.wanglusever) + b4.stripTrailingZeros().toPlainString() + name);
                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hasMap = new HashMap<>();
        hasMap.put("coinCode", name);

        ApiFactory.getInstance()
                .getaccountinfo1(kv.decodeString("tokenId"), hasMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(CurrencyActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }

                    bidata = data.getData();
                    if (data.getData() != null) {
                        txAvailable.setText(data.getData().getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        BigDecimal d = data.getData().getFrozenMoney();
                        txFrozen.setText(d.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {
                        t(data.getMsg());
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

    @OnClick({R.id.comtitlebar, R.id.tx_availableName, R.id.tx_available, R.id.tx_FrozenName, R.id.tx_Frozen, R.id.tx_title, R.id.currency_BiAddressLL, R.id.ed_biNuber, R.id.edt_Remark, R.id.LLtag, R.id.tc_sever, R.id.btn_OK, R.id.tx_severnuber, R.id.tx_sever_nuberset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_availableName:
                break;
            case R.id.tx_available:
                break;
            case R.id.tx_FrozenName:
                break;
            case R.id.tx_Frozen:
                break;
            case R.id.tx_title:
                break;
            case R.id.currency_BiAddressLL:

                intent.setClass(CurrencyActivity.this, CurrencyAddresListActivity.class);
                intent.putExtra("type", "type");
                startActivityForResult(intent, 2000);
                break;
            case R.id.ed_biNuber:
                break;
            case R.id.edt_Remark:
                break;
            case R.id.LLtag:
                break;
            case R.id.tc_sever:
                break;
            case R.id.btn_OK:

                if (edBiNuber.getText().toString().trim().equals("")) {
                    Toast.makeText(CurrencyActivity.this, R.string.tibinuber,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tag == 1) {
                    if (edtRemark.getText().toString().trim().equals("")) {
                        Toast.makeText(CurrencyActivity.this, "请输入标签",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                try {
                    if (Double.parseDouble(edBiNuber.getText().toString().trim()) > Double.parseDouble(bidata.getCoinMoney().stripTrailingZeros().toPlainString())) {
                        Toast.makeText(CurrencyActivity.this, "转出数量不能大于可用数量",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(edBiNuber.getText().toString().trim()) < Double.parseDouble(coinMin)) {
                        Toast.makeText(CurrencyActivity.this, "最小提币数量不能小于" + coinMin,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(edBiNuber.getText().toString().trim()) > Double.parseDouble(coinMax)) {
                        Toast.makeText(CurrencyActivity.this, "最大提币数量不能大于" + coinMax,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Double.parseDouble(edBiNuber.getText().toString().trim()) < 0) {
                        Toast.makeText(CurrencyActivity.this, R.string.tibinuber1,
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }

                if (txTitle.getText().toString().equals("请选择钱包地址")) {
                    Toast.makeText(CurrencyActivity.this, R.string.moneyaddress,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog();


                break;
            case R.id.tx_severnuber:
                break;
            case R.id.tx_sever_nuberset:
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (data != null) {
                Data = data.getStringExtra("name");
                txTitle.setText(Data);
            }

        }

    }

    private void Dialog() {
        dialog = new Dialog(CurrencyActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(CurrencyActivity.this).inflate(R.layout.dialog_timoney, null);
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
        txvew_phone = (TextView) dialogView.findViewById(R.id.txvew_phone);
        textview_Email = (TextView) dialogView.findViewById(R.id.textview_Email);
        edt_YZM = (EditText) dialogView.findViewById(R.id.edt_YZM);
        btn_YZM = (Button) dialogView.findViewById(R.id.btn_YZM);
        button = (Button) dialogView.findViewById(R.id.button);
        dialog.show();


        if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
            isType = "phone";
            txvew_phone.setText("手机验证码");
            btn_YZM.setVisibility(View.VISIBLE);
        }

        if (kv.decodeInt("googleState") == 1) {
            isType = "google";
            txvew_phone.setText("Google身份验证器");
            btn_YZM.setVisibility(View.GONE);

        }

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
                            .send(kv.decodeString("tokenId"), requestParam)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {

                                if (data.getCode() == 200) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(CurrencyActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                    time.start();
                                    Toast.makeText(CurrencyActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(CurrencyActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
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

                                if (data.getCode() == 200) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(CurrencyActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                    time.start();
                                    Toast.makeText(CurrencyActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CurrencyActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {

                            });
                }
            }
        });



        //体现
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(CurrencyActivity.this, R.string.login_yzm, Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<>();
                try {

                    if (isType.equals("phone")) {
                        //1手机，3邮箱,2谷歌
                        hashMap.put("checkType", "1");
                        hashMap.put("phoneoremail", kv.decodeString("phone") + "");
                    } else if (isType.equals("google")) {
                        hashMap.put("checkType", "2");
                        hashMap.put("googleKey", kv.decodeString("googleKey"));
                    } else {
                        hashMap.put("checkType", "3");
                        hashMap.put("phoneoremail", kv.decodeString("email") + "");
                    }
                    Double sever = Double.parseDouble(fixedRate) + (Double.parseDouble(edBiNuber.getText().toString().trim()) * Double.parseDouble(currencyRate));
                    tcSever.setText(getActivity().getResources().getString(R.string.wanglusever) + sever + getActivity().getResources().getString(R.string.ge) + name);
                    //提币手续费
                    hashMap.put("serviceHarge", sever.toString().trim());
                    //虚拟货币类型
                    hashMap.put("coinCode", bidata.getCoinCode());
                    //钱包地址
                    if (tag == 1) {
                        hashMap.put("walletAddress", Data + "_" + edtRemark.getText().toString().trim());
                    } else {
                        hashMap.put("walletAddress", Data + "");
                    }
                    //提币数量
                    hashMap.put("coinMoney", edBiNuber.getText().toString().trim());
                    //验证码（Google身份验证器）
                    hashMap.put("code", edt_YZM.getText().toString().trim() + "");

                    ApiFactory.getInstance()
                            .coinextract(kv.decodeString("tokenId"), hashMap)
                            .compose(RxSchedulers.io_main())
                            .subscribe(new Consumer<Response<Data>>() {
                                @Override
                                public void accept(Response<Data> data) throws Exception {

                                    //替换原来的tokenId
                                    String str = data.raw().headers("authorization").toString().substring(1, data.raw().headers("authorization").toString().length() -1);
                                    kv.encode("tokenId", str);

                                    if (data.body().getCode() == 200) {

                                        dialog.dismiss();
                                        finish();
                                        t(data.body().getMsg());
                                    } else {
                                        dialog.dismiss();
                                        t(data.body().getMsg());
                                    }

                                }


                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {

                                }
                            });

                } catch (Exception e) {
                    Toast.makeText(CurrencyActivity.this, R.string.bangding, Toast.LENGTH_SHORT).show();
                }

            }

        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        net();
    }


}
