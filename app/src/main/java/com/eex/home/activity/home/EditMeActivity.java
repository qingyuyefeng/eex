package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.Advertisingvo;
import com.eex.home.weight.MyDialog;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: EditMeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/30 17:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/30 17:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 * 广告编辑
 */
public class EditMeActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.txtype)
    TextView txtype;
    /**
     *
     */
    @BindView(R.id.tx_coinName)
    TextView txCoinName;
    /**
     *
     */
    @BindView(R.id.LL_coin)
    LinearLayout LLCoin;
    /**
     *
     */
    @BindView(R.id.edt_price)
    TextView edtPrice;
    /**
     *
     */
    @BindView(R.id.tx_Premium)
    EditText txPremium;
    /**
     *
     */
    @BindView(R.id.LL_Premium)
    LinearLayout LLPremium;
    /**
     *
     */
    @BindView(R.id.txnuber)
    TextView txnuber;
    /**
     *
     */
    @BindView(R.id.coinName)
    TextView coinName;
    /**
     *
     */
    @BindView(R.id.edt_Nuber)
    EditText edtNuber;
    /**
     *
     */
    @BindView(R.id.editText)
    TextView editText;
    /**
     *
     */
    @BindView(R.id.unit1)
    TextView unit1;
    /**
     *
     */
    @BindView(R.id.minimum)
    EditText minimum;
    /**
     *
     */
    @BindView(R.id.unit2)
    TextView unit2;
    /**
     *
     */
    @BindView(R.id.Highest)
    EditText Highest;
    /**
     *
     */
    @BindView(R.id.edt_remark)
    EditText edtRemark;
    /**
     *
     */
    @BindView(R.id.ckb)
    CheckBox ckb;
    /**
     *
     */
    @BindView(R.id.tx_ckb)
    TextView txCkb;
    /**
     *
     */
    @BindView(R.id.Bond)
    TextView Bond;
    /**
     *
     */
    @BindView(R.id.severMoney)
    TextView severMoney;
    /**
     *
     */
    @BindView(R.id.Btn_buy)
    Button BtnBuy;


    private String coin, id, type, sever, Margin, MarginCoin, getTradeType;
    private String reark = "";
    public Advertisingvo advertisingvo;

    private View LLPremiumView;
    private MyDialog mMyDialog1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_me;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("编辑广告");
        coin = getIntent().getStringExtra("coin");
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        sever = getIntent().getStringExtra("sever");
        Margin = getIntent().getStringExtra("Margin");
        MarginCoin = getIntent().getStringExtra("MarginCoin");
        reark = getIntent().getStringExtra("reark");
        getTradeType = getIntent().getStringExtra("getTradeType");


        if (getTradeType.equals("卖")) {
            txnuber.setText("卖出数量");
            txtype.setText("卖出币种");
        } else {
            txnuber.setText("购买数量");
            txtype.setText("购买币种");
        }

        String str = new BigDecimal(sever).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
        severMoney.setText("成交平台将收取" + str + "%的手续费");

        try {
            if (!reark.equals("") && reark != null) {
                edtRemark.setText(reark);
            }
        } catch (Exception e) {
            edtRemark.setText("");
        }


        txPremium.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Premium = txPremium.getText().toString().trim();
                if (!advertisingvo.getTradePrice().equals("") && !Premium.equals("") && !Premium.equals("-") && !Premium.equals(".")) {
                    BigDecimal NewStr = new BigDecimal(Premium).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                    BigDecimal data = advertisingvo.getTradePrice().multiply(NewStr);
                    edtPrice.setText(data.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNuber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nuber = edtNuber.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                if (!price.equals("") && !nuber.equals("")) {
                    BigDecimal data = new BigDecimal(nuber).multiply(new BigDecimal(price));
                    editText.setText(data.setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    editText.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        net();
    }

    /**
     * c2c根据id获取广告详情
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hasMap = new HashMap<>();
        hasMap.put("id", id);
        ApiFactory.getInstance()
                .getAdvertising(kv.decodeString("tokenId"), hasMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    advertisingvo = data.getData();
                    if (data != null) {
                        edtPrice.setText(data.getData().getTradePrice().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
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

    @OnClick({R.id.comtitlebar, R.id.txtype, R.id.tx_coinName, R.id.LL_coin, R.id.edt_price, R.id.tx_Premium, R.id.LL_Premium, R.id.txnuber, R.id.coinName, R.id.edt_Nuber, R.id.editText, R.id.unit1, R.id.minimum, R.id.unit2, R.id.Highest, R.id.edt_remark, R.id.ckb, R.id.tx_ckb, R.id.Bond, R.id.severMoney, R.id.Btn_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.txtype:
                break;
            case R.id.tx_coinName:
                break;
            case R.id.LL_coin:
                break;
            case R.id.edt_price:
                break;
            case R.id.tx_Premium:
                break;
            case R.id.LL_Premium:
                PremiumDialig();
                break;
            case R.id.txnuber:
                break;
            case R.id.coinName:
                break;
            case R.id.edt_Nuber:
                break;
            case R.id.editText:
                break;
            case R.id.unit1:
                break;
            case R.id.minimum:
                break;
            case R.id.unit2:
                break;
            case R.id.Highest:
                break;
            case R.id.edt_remark:
                break;
            case R.id.ckb:
                break;
            case R.id.tx_ckb:
                ckb.setChecked(true);
                break;
            case R.id.Bond:
                break;
            case R.id.severMoney:
                break;
            case R.id.Btn_buy:
                modifyData();
                break;

            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void modifyData() {

        if (txPremium.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入溢价比例", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtNuber.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入购买数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (minimum.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入单笔最低数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Highest.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入单笔最高数量", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (edt_remark.getText().toString().trim().equals("")) {
//            Toast.makeText(mContext, "请输入备注", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (ckb.isChecked() == false) {
            Toast.makeText(getActivity(), "请勾选相关条例", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("coin1111", type);
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", id);
        requestParam.put("tradeCoin", coin);
        Log.e("coin", coin);
        if (type.equals("sell")) {
            requestParam.put("tradeType", "1");
        } else {
            requestParam.put("tradeType", "2");
        }
        BigDecimal NewStr = new BigDecimal(txPremium.getText().toString().trim()).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
        requestParam.put("premium", NewStr.toString());
        requestParam.put("quantity", edtNuber.getText().toString().trim());
        requestParam.put("minTradeNum", minimum.getText().toString().trim());
        requestParam.put("maxTradeNum", Highest.getText().toString().trim());
        if (!edtRemark.getText().toString().trim().equals("")) {
            requestParam.put("remark", edtRemark.getText().toString().trim());
        } else {
            requestParam.put("remark", " ");
        }

        ApiFactory.getInstance()
                .updateAdvertising(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        finish();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });


    }

    /**
     *
     */
    private void PremiumDialig() {

        LLPremiumView = getLayoutInflater().inflate(R.layout.yijia_dialog, null);
        mMyDialog1 = new MyDialog(getActivity(), 0, 0, LLPremiumView, R.style.DialogTheme);
        mMyDialog1.setCancelable(true);
        mMyDialog1.show();

    }
}
