package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.ExchangeRate;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;

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
 * @ClassName: ForeignCurrencyRechargeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ForeignCurrencyRechargeActivity extends BaseActivity {

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
    @BindView(R.id.tx_tixian)
    TextView txTixian;
    /**
     *
     */
    @BindView(R.id.btn_next)
    Button btnNext;


    private String chargeFee;
    private String chargeMaxNum;
    private String chargeMinNum;
    private String currency;
    private String currencyStr;

    private String Nuber = "";

    private ExchangeRate getData;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_foreign_currency_recharge;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        if (getIntent().getStringExtra("currency") != null) {
            currency = getIntent().getStringExtra("currency");
            txMoneyNuber.setText(currency);
            txSever.setText(0 + currency);
            txZongjiNuber.setText(0 + currency);
            txSjNuber.setText(0 + currency);
        }
        if (getIntent().getStringExtra("currencyStr") != null) {
            currencyStr = getIntent().getStringExtra("currencyStr");
            comtitlebar.setTitle(currencyStr + "充值");
            biName.setText(currencyStr);

            chargeFee = getIntent().getStringExtra("chargeFee");
            BigDecimal chage = new BigDecimal(chargeFee);
            BigDecimal chage1 = chage.multiply(new BigDecimal("100"));
            txMoneyPercentage.setText("(" + chage1.stripTrailingZeros().toPlainString() + "%)");
            chargeMaxNum = getIntent().getStringExtra("chargeMaxNum");
            chargeMinNum = getIntent().getStringExtra("chargeMinNum");
            txChargeMaxMinNum.setText("单笔充值限额: " + chargeMinNum + currency + "-" + chargeMaxNum + currency);
        }

        //获取指定汇率
        net();
        comtitlebar.setImageView(R.drawable.chongzhijilu);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(ForeignCurrencyRechargeActivity.this, RechargeListActivity.class);
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
                .getExchangeRate(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(exchangeRateData -> {

                    if (exchangeRateData.getData() != null) {
                        getData = exchangeRateData.getData();
                        exchangeRate.setText("1" + exchangeRateData.getData().getCode() + "≈" + exchangeRateData.getData().getRefePrice() + "CNYE");
                    } else {
                        t(exchangeRateData.getMsg());
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
                    BigDecimal severMoney = new BigDecimal(chargeFee).multiply(new BigDecimal(Nuber));
                    txSever.setText(severMoney.setScale(3, BigDecimal.ROUND_DOWN) + currency);
                    BigDecimal Reat1 = new BigDecimal(Nuber).add(new BigDecimal(chargeFee).multiply(new BigDecimal(Nuber)));

                    txZongjiNuber.setText(Reat1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + currency);
                    if (getData != null) {
                        BigDecimal Reat = new BigDecimal(Nuber).multiply(new BigDecimal(getData.getRefePrice()));
                        txSjNuber.setText(Reat.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "CNYE");
                    } else {
                        txSjNuber.setText("0CNYE");
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

    @OnClick({R.id.comtitlebar, R.id.bi_name, R.id.LL_CNTE, R.id.edt_Nuber, R.id.tx_moneyNuber, R.id.tx_chargeMaxMinNum, R.id.tx_moneyPercentage, R.id.tx_sever, R.id.tx_zongjiNuber, R.id.tx_sj_Nuber, R.id.ExchangeRate, R.id.ckb_put, R.id.tx_tixian, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.bi_name:
                break;
            case R.id.LL_CNTE:
                break;
            case R.id.edt_Nuber:
                break;
            case R.id.tx_moneyNuber:
                break;
            case R.id.tx_chargeMaxMinNum:
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
            case R.id.tx_tixian:
                if (ckbPut.isChecked()) {
                    ckbPut.setChecked(false);
                } else {
                    ckbPut.setChecked(true);
                }
                break;
            case R.id.btn_next:
                putMoney();

                break;

            default:
                break;
        }
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void putMoney() {

        if (Nuber.equals("")) {
            Toast.makeText(ForeignCurrencyRechargeActivity.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if (new BigDecimal(Nuber).compareTo(new BigDecimal(chargeMaxNum)) == 1) {
            Toast.makeText(ForeignCurrencyRechargeActivity.this, "单笔最多充值" + chargeMaxNum + currency, Toast.LENGTH_SHORT).show();
            return;
        }
        if (new BigDecimal(Nuber).compareTo(new BigDecimal(chargeMinNum)) == -1) {
            Toast.makeText(ForeignCurrencyRechargeActivity.this, "单笔最少充值" + chargeMinNum + currency, Toast.LENGTH_SHORT).show();
            return;
        }
        if (ckbPut.isChecked() == false) {
            Toast.makeText(ForeignCurrencyRechargeActivity.this, "请勾选充值须知", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> hasMap = new HashMap<>();
        hasMap.put("dealAmount", Nuber);
        hasMap.put("currency", currency);
        hasMap.put("url", WPConfig.INSTANCE.getBaseUrl() + "");

        ApiFactory.getInstance()
                .remittanceother(kv.decodeString("tokenId"),hasMap)
                .compose(RxSchedulers.io_main())
                .subscribe(remittanceData -> {

                    if (remittanceData.getData() != null) {
                        intent.putExtra("PAYEE_ACCOUNT", remittanceData.getData().getPayee_account().trim());
                        intent.putExtra("PAYEE_NAME", remittanceData.getData().getPayee_name());
                        intent.putExtra("PAYMENT_AMOUNT", remittanceData.getData().getPayment_amount().toString());
                        intent.putExtra("PAYMENT_UNITS", remittanceData.getData().getPayment_units());
                        intent.putExtra("PAYMENT_ID", remittanceData.getData().getCharacter_encoding());
                        intent.putExtra("STATUS_URL", remittanceData.getData().getStatus_url());
                        intent.putExtra("PAYMENT_URL", remittanceData.getData().getPayment_url());
                        intent.putExtra("NOPAYMENT_URL", remittanceData.getData().getNopayment_url());
                        intent.putExtra("BAGGAGE_FIELDS", remittanceData.getData().getBaggage_fields());
                        intent.putExtra("INTERFACE_LANGUAGE", remittanceData.getData().getInterface_language());
                        intent.putExtra("CHARACTER_ENCODING", remittanceData.getData().getCharacter_encoding());
                        intent.putExtra("V2_HASH", remittanceData.getData().getV2_hash());
                        intent.putExtra("url", remittanceData.getData().getUrl());
                        intent.setClass(ForeignCurrencyRechargeActivity.this, EpayWebViewActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        t(remittanceData.getMsg());
                    }
                }, throwable -> {

                });
    }
}
