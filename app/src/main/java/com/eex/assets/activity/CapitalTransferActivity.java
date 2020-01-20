package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;

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
 * @ClassName: CapitalTransferActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 13:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 13:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 资金划转
 */
public class CapitalTransferActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.TXzijin)
    TextView TXzijin;
    /**
     *
     */
    @BindView(R.id.LLimgType)
    LinearLayout LLimgType;
    /**
     *
     */
    @BindView(R.id.txJiaoyi)
    TextView txJiaoyi;
    /**
     *
     */
    @BindView(R.id.txBiget)
    TextView txBiget;
    /**
     *
     */
    @BindView(R.id.LLBi)
    LinearLayout LLBi;
    /**
     *
     */
    @BindView(R.id.edtNuber)
    EditText edtNuber;
    /**
     *
     */
    @BindView(R.id.txMin)
    TextView txMin;
    /**
     *
     */
    @BindView(R.id.txQuanbu)
    TextView txQuanbu;
    /**
     *
     */
    @BindView(R.id.btnPut)
    Button btnPut;


    private String type = "";
    private String coin1, nuber1;
    private String MoneyData = "";
    private String coinCode;

    private ArrayList<Personal> list = new ArrayList<Personal>();
    /**
     * 全部数据
     */
    private ArrayList<CtcaccountList> data = new ArrayList();


    /**
     * c2c余额
     */
    private BigDecimal C2CMoney;
    /**
     * 资金余额
     */
    private BigDecimal ZijinMoney;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_capital_transfer;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("资金划转");


        if (getIntent().getStringExtra("type") != null) {
            type = getIntent().getStringExtra("type");
            if (type.equals("2")) {
                TXzijin.setText("C2C账户");
                txJiaoyi.setText("交易账户");

            } else {
                TXzijin.setText("交易账户");
                txJiaoyi.setText("C2C账户");
            }
        }
        if (getIntent().getStringExtra("coin1") != null) {
            coin1 = getIntent().getStringExtra("coin1");
            nuber1 = getIntent().getStringExtra("nuber1");
            txBiget.setText(coin1);
            LLBi.setClickable(false);
            MoneyData = nuber1;
            coinCode = coin1;
            txMin.setText("最多可转入" + nuber1 + coin1);
        }

        //获取C2C账户币种详情
        net();
        //获取资金账户币种详情
        init();
    }

    /**
     * 获取C2C账户币种详情
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(ctcaccountData -> {

                    if (ctcaccountData.getData() != null) {
                        data.clear();
                        data.addAll(ctcaccountData.getData().getList());
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getCoinCode().equals(coin1)) {
                                C2CMoney = data.get(i).getUsableMoney();
                                return;
                            }
                        }
                    } else {
                        t(ctcaccountData.getMsg());
                    }
                }, throwable -> {

                });

    }

    /**
     * 获取资金账户币种详情
     */
    @SuppressLint("CheckResult")
    private void init() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        list = data.getData().getUserCoinAccount();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().equals(coin1)) {
                                ZijinMoney = list.get(i).getCoinMoney();
                                return;
                            }
                        }
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

    @OnClick({R.id.comtitlebar, R.id.TXzijin, R.id.LLimgType, R.id.txJiaoyi, R.id.txBiget, R.id.LLBi, R.id.edtNuber, R.id.txMin, R.id.txQuanbu, R.id.btnPut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.TXzijin:
                break;
            case R.id.LLimgType:

                try {

                    if (TXzijin.getText().toString().trim().equals("C2C账户")) {
                        type = "1";
                        TXzijin.setText("交易账户");
                        txJiaoyi.setText("C2C账户");
                        txMin.setText("最多可转入" + ZijinMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coin1);
                    } else {
                        type = "2";
                        TXzijin.setText("C2C账户");
                        txJiaoyi.setText("交易账户");
                        txMin.setText("最多可转入" + C2CMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coin1);
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.txJiaoyi:
                break;
            case R.id.txBiget:
                break;
            case R.id.LLBi:
                intent.setClass(CapitalTransferActivity.this, C2CListBiActivity.class);
                startActivityForResult(intent, 2000);
                break;
            case R.id.edtNuber:
                break;
            case R.id.txMin:
                break;
            case R.id.txQuanbu:

                try {
                    if (type.equals("1")) {
                        edtNuber.setText(ZijinMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {
                        edtNuber.setText(C2CMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.btnPut:

                if (txBiget.getText().toString().trim().equals("请选择币种")) {
                    Toast.makeText(CapitalTransferActivity.this, "请选择币种", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtNuber.getText().toString().trim().equals("")) {
                    Toast.makeText(CapitalTransferActivity.this, "请输入转入数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                putData(type);
                break;
            default:
                break;
        }
    }

    /**
     * @param type
     */
    @SuppressLint("CheckResult")
    private void putData(String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("transferType", type);
        hashMap.put("coinCode", coinCode);
        hashMap.put("transferNum", edtNuber.getText().toString().trim());
        ApiFactory.getInstance()
                .fundstransfer(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts()) {
                        Toast.makeText(CapitalTransferActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }
}
