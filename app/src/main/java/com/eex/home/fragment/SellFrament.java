package com.eex.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.home.activity.home.GetCoinTradeConfigListActivity;
import com.eex.home.bean.CoinTradeConfig;
import com.eex.home.bean.MainData;
import com.eex.home.weight.MyDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
 * @Package: com.overthrow.home.fragment
 * @ClassName: SellFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 16:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 16:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 出售
 */
public class SellFrament extends BaseFragment {

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
    @BindView(R.id.LLPremium)
    LinearLayout LLPremium;
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
    /**
     *
     */
    Unbinder unbinder;

    /**
     * 自定义dialog
     */
    private MyDialog mMyDialog;
    private MyDialog mMyDialog1;

    private View view;


    private ArrayList<MainData> list = new ArrayList<>();
    private CoinTradeConfig coinTradeConfigs;

    @Override
    protected void lazyLoad() {
        net();

    }
    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();


    }

    private void net() {
        txPremium.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Premium = txPremium.getText().toString().trim();
                if (!txCoinName.getText().toString().trim().equals("请选择币种")) {
                    try {

                        if (!list.get(0).getUsdtCny().equals("") && !Premium.equals("") && !Premium.equals("-") && !Premium.equals(".")) {
                            BigDecimal NewStr = new BigDecimal(Premium).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                            BigDecimal data = list.get(0).getUsdtCny().multiply(NewStr);
                            edtPrice.setText(data.setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        }
                    } catch (Exception e) {

                    }
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


    }


    @Override
    protected void initUiAndListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sell;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tx_coinName, R.id.LL_coin, R.id.tx_Premium, R.id.LLPremium, R.id.edt_Nuber, R.id.minimum, R.id.Highest, R.id.edt_remark, R.id.ckb, R.id.Btn_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tx_coinName:
                break;
            case R.id.LL_coin:
                intent.putExtra("type", "buy");
                intent.setClass(getContext(), GetCoinTradeConfigListActivity.class);
                startActivityForResult(intent, 2000);

                break;
            case R.id.tx_Premium:
                txPremium.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String Premium = txPremium.getText().toString().trim();
                        if (!txCoinName.getText().toString().trim().equals("请选择币种")) {
                            if (!list.get(0).getUsdtCny().equals("") && !Premium.equals("") && !Premium.equals("-") && !Premium.equals(".")) {
                                BigDecimal NewStr = new BigDecimal(Premium).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                                BigDecimal data = list.get(0).getUsdtCny().multiply(NewStr);
                                edtPrice.setText(data.setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case R.id.LLPremium:

                view = getActivity().getLayoutInflater().inflate(R.layout.yijia_dialog, null);
                mMyDialog1 = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                mMyDialog1.setCancelable(true);
                mMyDialog1.show();
                break;
            case R.id.edt_Nuber:
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
                break;
            case R.id.minimum:
                break;
            case R.id.Highest:
                break;
            case R.id.edt_remark:
                break;
            case R.id.ckb:
                ckb.setChecked(true);
                break;
            case R.id.Btn_buy:


                if (txCoinName.getText().toString().trim().equals("请选择币种")) {
                    Toast.makeText(getActivity(), "请选择币种", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtPrice.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入价格", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String str = txPremium.getText().toString().trim();
                    BigDecimal NewStr = new BigDecimal(str).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
                    if (NewStr.compareTo(coinTradeConfigs.getPremiumMin()) == -1) {
                        Toast.makeText(getActivity(), "最小设置溢价不能小于" + (coinTradeConfigs.getPremiumMin().subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()) + "%", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (NewStr.compareTo(coinTradeConfigs.getPremiumMax()) == 1) {
                        Toast.makeText(getActivity(), "最大设置溢价不能大于" + (coinTradeConfigs.getPremiumMax().subtract(new BigDecimal(1)).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()) + "%", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {

                }
                if (edtNuber.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入购买数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (minimum.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入单笔最低购买量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Highest.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入单笔最高购买量", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ckb.isChecked() == false) {
                    Toast.makeText(getActivity(), "请勾选相关条例", Toast.LENGTH_SHORT).show();
                    return;
                }
                view = getActivity().getLayoutInflater().inflate(R.layout.dialog_sell_caonii, null);
                mMyDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                mMyDialog.setCancelable(true);
                TextView UnitPrice = (TextView) view.findViewById(R.id.UnitPrice);
                TextView Nuber = (TextView) view.findViewById(R.id.Nuber);
                TextView Quota = (TextView) view.findViewById(R.id.Quota);
                UnitPrice.setText(edtPrice.getText().toString().trim() + "CNY");
                Quota.setText(minimum.getText().toString().trim() + "CNY-" + Highest.getText().toString().trim() + "CNY");
                Nuber.setText(edtNuber.getText().toString().trim() + txCoinName.getText().toString().trim());
                mMyDialog.show();
                Button btn_dimss = (Button) view.findViewById(R.id.btn_dimss);
                btn_dimss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMyDialog.dismiss();
                    }
                });

                Button btn_sell = (Button) view.findViewById(R.id.btn_sell);
                btn_sell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //c2c创建广告
                        creatAdvertising();
                    }
                });

                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2000) {
            if (data != null) {
                txCoinName.setText(data.getStringExtra("coin"));
                coinName.setText("(" + data.getStringExtra("coin") + ")");
                getIndexMaketList(data.getStringExtra("coin"));
                getCoinTradeConfig(data.getStringExtra("coin"));


            }
        }
    }


    /***
     *
     * 首页list
     * @param coin
     */
    @SuppressLint("CheckResult")
    private void getIndexMaketList(String coin) {

        HashMap<String, String> hashMap = new HashMap<>();
        if (coin.equals("USDT")) {
            hashMap.put("delkeys", "USDT_CNYE");
        } else {
            hashMap.put("delkeys", coin + "_USDT");
        }

        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        list.clear();
                        list.addAll(data.getData());

                        try {
                            if (coin.equals("USDT")) {
                                edtPrice.setText(list.get(0).getNewPrice().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            } else {
                                edtPrice.setText(list.get(0).getUsdtCny().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            }
                        } catch (Exception e) {

                        }


                    } else {
                        t(data.getMsg());
                    }


                }, throwable -> {

                });
    }


    /**
     * c2c根据币种查询详情
     *
     * @param coin
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void getCoinTradeConfig(String coin) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coin", coin);
        ApiFactory.getInstance()
                .getCoinTradeConfig(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        coinTradeConfigs = data.getData();
                        Bond.setText("交易保证金" + data.getData().getBuyMargin().stripTrailingZeros().toPlainString() + data.getData().getBuyMarginCoin() + "");
                        severMoney.setText("成交平台将收取" + data.getData().getServiceRate().multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString() + "%的手续费" + "");
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }


    /**
     * c2c创建广告
     */
    @SuppressLint("CheckResult")
    private void creatAdvertising() {


        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("tradeCoin", txCoinName.getText().toString().trim());
        hashMap.put("tradeType", "1");
        String str = txPremium.getText().toString().trim();
        BigDecimal NewStr = new BigDecimal(str).divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1"));
        hashMap.put("premium", NewStr.toString());
        hashMap.put("quantity", edtNuber.getText().toString().trim());
        if (!edtRemark.getText().toString().trim().equals("")) {
            hashMap.put("remark", edtRemark.getText().toString().trim());
        } else {
            hashMap.put("remark", "1. 订单有效期为15分钟，请您及时在有效期内付款并点击「标记为已付款」按钮，我才可以释放数字币给您2. 开始交易后数字币由系统锁定托管，请安心下单");

        }
        hashMap.put("minTradeNum", minimum.getText().toString().trim());
        hashMap.put("maxTradeNum", Highest.getText().toString().trim());

        ApiFactory.getInstance()
                .creatAdvertising(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        mMyDialog.dismiss();
                        t(data.getMsg());
                        getActivity().finish();
                    } else {
                        t(data.getMsg());

                    }
                }, throwable -> {

                });
    }
}
