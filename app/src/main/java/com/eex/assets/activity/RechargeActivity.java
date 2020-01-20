package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

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
 * @ClassName: RechargeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 14:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 14:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 充值
 */
public class RechargeActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.recharge_edt_name)
    EditText rechargeEdtName;
    /**
     *
     */
    @BindView(R.id.recharge_edt_namefo)
    EditText rechargeEdtNamefo;
    /**
     *
     */
    @BindView(R.id.textView_Bank)
    TextView textViewBank;
    /**
     *
     */
    @BindView(R.id.real_Bank_LL)
    LinearLayout realBankLL;
    /**
     *
     */
    @BindView(R.id.recharge_edt_bankName)
    EditText rechargeEdtBankName;
    /**
     *
     */
    @BindView(R.id.recharge_edt_money)
    EditText rechargeEdtMoney;
    /**
     *
     */
    @BindView(R.id.teviewServiceMoney)
    TextView teviewServiceMoney;
    /**
     *
     */
    @BindView(R.id.tx_nuber)
    TextView txNuber;
    /**
     *
     */
    @BindView(R.id.textView_CNY)
    TextView textViewCNY;
    /**
     *
     */
    @BindView(R.id.nextHZ_btn)
    Button nextHZBtn;
    /**
     *
     */
    @BindView(R.id.ll)
    LinearLayout ll;
    /**
     *
     */
    @BindView(R.id.textView_CNYTO)
    TextView textViewCNYTO;


    private int nuber = 1;
    /**
     * 定义范围开始数字
     */
    public static final int START = 1;
    /**
     * 定义范围结束数字
     */
    public static final int END = 99;
    private BigDecimal b3;
    private String Textview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.chongzi));
        comtitlebar.setImageView(R.drawable.cq_recharge_zd);

        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(RechargeActivity.this, RechargeListActivity.class);
                startActivity(intent);
            }
        });

        randomNuber();
    }

    private void randomNuber() {

        Random random = new Random();
        //产生随机数
        nuber = random.nextInt(END - START + 1) + START;
        teviewServiceMoney.setText("0." + nuber + "");
        rechargeEdtMoney.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        rechargeEdtMoney.setKeyListener(new DigitsKeyListener(false, true));
        rechargeEdtMoney.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    BigDecimal b1 = new BigDecimal(rechargeEdtMoney.getText().toString().trim());
                    BigDecimal b2 = new BigDecimal(teviewServiceMoney.getText().toString().trim());
                    b3 = b1.add(b2);
                    txNuber.setText(getResources().getString(R.string.money) + b3 + "CNY");
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
                if (s.toString().contains(".")) {

                    if (s.length() - 1 - s.toString().indexOf(".") >= 0) {
                        rechargeEdtMoney.setText(s.toString().subSequence(0, s.toString().indexOf(".")));
                        rechargeEdtMoney.setSelection(s.toString().subSequence(0, s.toString().indexOf(".")).length());
                    }
                }
// 这部分是处理如果用户输入以.开头，在前面加上0
                if (s.toString().trim().substring(0).equals(".")) {
                    rechargeEdtMoney.setText("0");
                    rechargeEdtMoney.setSelection(1);
                }
// 这里处理用户 多次输入.的处理
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        rechargeEdtMoney.setText(s.subSequence(0, 1));
                        rechargeEdtMoney.setSelection(1);
                        return;
                    }
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

    @OnClick({R.id.comtitlebar, R.id.recharge_edt_name, R.id.recharge_edt_namefo, R.id.textView_Bank, R.id.real_Bank_LL, R.id.recharge_edt_bankName, R.id.recharge_edt_money, R.id.teviewServiceMoney, R.id.tx_nuber, R.id.textView_CNY, R.id.nextHZ_btn, R.id.ll, R.id.textView_CNYTO})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.recharge_edt_name:
                break;
            case R.id.recharge_edt_namefo:
                break;
            case R.id.textView_Bank:
                break;
            //选择银行卡
            case R.id.real_Bank_LL:
                intent.setClass(RechargeActivity.this, BankListActivity.class);
                startActivityForResult(intent, 2000);
                break;
            case R.id.recharge_edt_bankName:
                break;
            case R.id.recharge_edt_money:
                break;
            case R.id.teviewServiceMoney:
                break;
            case R.id.tx_nuber:
                break;
            case R.id.textView_CNY:
                break;
            case R.id.nextHZ_btn:
                getData();
                break;
            case R.id.ll:
                break;
            case R.id.textView_CNYTO:
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void getData() {

        HashMap<String, String> hashMap = new HashMap<>();
        if (TextUtils.isEmpty(rechargeEdtName.getText().toString().trim())) {
            Toast.makeText(this, R.string.xing, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(rechargeEdtNamefo.getText().toString().trim())) {
            Toast.makeText(this, R.string.ming, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(rechargeEdtBankName.getText().toString().trim())) {
            Toast.makeText(this, R.string.bankname, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(rechargeEdtMoney.getText().toString().trim())) {
            Toast.makeText(this, R.string.moneynuber, Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(rechargeEdtMoney.getText().toString()) < 500) {
            Toast.makeText(this, R.string.moneyMin, Toast.LENGTH_SHORT).show();
            return;
        }
        //持卡人姓
        hashMap.put("firstName", rechargeEdtName.getText().toString().trim());
        //持卡人名
        hashMap.put("lastName", rechargeEdtNamefo.getText().toString().trim());
        //用户银行卡号
        hashMap.put("userBankCardNo", rechargeEdtBankName.getText().toString().trim());
        //交易金额
        hashMap.put("dealAmount", b3 + "");
        //银行名称
        hashMap.put("bankName", Textview);

        ApiFactory.getInstance()
                .remittance(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(rechargeCZDataData -> {


                    if (rechargeCZDataData.getCode() == 10002 || rechargeCZDataData.getCode() == 10001) {
                        rechargeEdtMoney.setText("");

                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RechargeActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }

                    if (rechargeCZDataData != null) {
                        rechargeEdtMoney.setText("");
                        //订单号
                        intent.putExtra("TransactionNum",rechargeCZDataData.getData().getDealOrderNo());
                        //银行卡名称
                        intent.putExtra("BankName",rechargeCZDataData.getData().getBankName());
                        //银行卡号
                        intent.putExtra("AccountNumber",rechargeCZDataData.getData().getUserBankCardNo());

                        //支行地址
                        intent.putExtra("BankAddress",rechargeCZDataData.getData().getChildBankName());
                        //备注
                        intent.putExtra("remark",rechargeCZDataData.getData().getRemark());
                        //姓
                        intent.putExtra("surname",rechargeCZDataData.getData().getSurname());
                        //名
                        intent.putExtra("givenName",rechargeCZDataData.getData().getGivenName());

                        //收款人姓名
                        intent.putExtra("AccountName",rechargeCZDataData.getData().getFirstName()+rechargeCZDataData.getData().getLastName());
                        //金额
                        intent.putExtra("TransactionMoney",String.valueOf(rechargeCZDataData.getData().getDealAmount()));
                        //状态
                        intent.putExtra("Status",String.valueOf(rechargeCZDataData.getData().getDealStatus()));
                        //备注
                        intent.putExtra("remark",rechargeCZDataData.getData().getCode());
                        intent.setClass(RechargeActivity.this,RemittanceActivity.class);
                        startActivity(intent);

                    } else {
                        rechargeEdtMoney.setText("");
                        t(rechargeCZDataData.getMsg());
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2000) {
            //创建Random类对象
            Textview = data.getStringExtra("textview");
            textViewBank.setText(Textview);

        }
    }
}
