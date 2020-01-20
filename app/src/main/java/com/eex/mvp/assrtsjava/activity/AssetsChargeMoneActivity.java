package com.eex.mvp.assrtsjava.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.NewComTitleBar;
import com.eex.extensions.RxExtensionKt;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: AssetsChargeMoneActivity
 * @Description: 资金划转
 * @Author: hcj
 * @CreateDate: 2019/12/24 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 16:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class AssetsChargeMoneActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    NewComTitleBar comtitlebar;
    @BindView(R.id.chargemone_name)
    TextView chargemoneName;
    @BindView(R.id.chargemone_line)
    LinearLayout chargemoneLine;
    @BindView(R.id.Capital_account)
    TextView CapitalAccount;
    @BindView(R.id.Capital_image)
    ImageView CapitalImage;
    @BindView(R.id.Capital_c2c)
    TextView CapitalC2c;
    @BindView(R.id.Capital_please)
    EditText CapitalPlease;
    @BindView(R.id.Capital_edtoMuber)
    EditText CapitalEdtoMuber;
    @BindView(R.id.Capital_name)
    TextView CapitalName;
    @BindView(R.id.Capital_all)
    TextView CapitalAll;
    @BindView(R.id.Capital_money)
    Button CapitalMoney;


    private String type = "1";
    private String UsableMoney;
    private String coinCode;
    private String MoneyData = "";

    private ArrayList<Personal> list = new ArrayList<Personal>();
    /**
     * 全部数据
     */
    private ArrayList<CtcaccountList> data = new ArrayList();
    /**
     * c2c余额
     */
    private BigDecimal C2CMoney = new BigDecimal(0);
    /**
     * 资金余额
     */
    private BigDecimal ZijinMoney = new BigDecimal(0);

    @Override
    protected int getLayoutId() {
        return R.layout.re_actvity_chargemone_choice;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.Capital_transfer));

        if (getIntent().getStringExtra("coinCode") != null) {
            coinCode = getIntent().getStringExtra("coinCode");
            UsableMoney = getIntent().getStringExtra("UsableMoney");

        }
        chargemoneName.setText(coinCode);
        MoneyData = UsableMoney;

//        CapitalEdtoMuber.setText(UsableMoney + coinCode);

        CapitalName.setText(coinCode);


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

        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(ctcaccountData -> {

                    if (ctcaccountData.getData() != null) {
                        data.clear();
                        data.addAll(ctcaccountData.getData().getList());
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getCoinCode().equals(coinCode)) {
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

        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        list = data.getData().getUserCoinAccount();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().equals(coinCode)) {
                                ZijinMoney = list.get(i).getCoinMoney();
                                CapitalEdtoMuber.setText(ZijinMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
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


    @OnClick({R.id.comtitlebar, R.id.chargemone_name, R.id.chargemone_line, R.id.Capital_account, R.id.Capital_image, R.id.Capital_c2c, R.id.Capital_please, R.id.Capital_edtoMuber, R.id.Capital_name, R.id.Capital_all, R.id.Capital_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.chargemone_name:
                break;
            case R.id.chargemone_line:
                intent.setClass(getActivity(), TransferActivity.class);
                startActivity(intent);
                break;
            case R.id.Capital_account:
                break;
            case R.id.Capital_image:
                CapitalImage.setEnabled(false);
                if(type.equals("2")){
                    type = "1";
                    CapitalAccount.setText(R.string.Capital_account);
                    CapitalC2c.setText(R.string.assets_c2c_account);
                    CapitalEdtoMuber.setText(ZijinMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    type = "2";
                    CapitalAccount.setText(R.string.assets_c2c_account);
                    CapitalC2c.setText(R.string.Capital_account);
                    CapitalEdtoMuber.setText(C2CMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                }
                CapitalPlease.setText("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CapitalImage.setEnabled(true);
                    }
                },1500);

                break;
            case R.id.Capital_c2c:
                break;
            case R.id.Capital_please:
                break;
            case R.id.Capital_edtoMuber:
                break;
            case R.id.Capital_name:
                break;
            case R.id.Capital_all:
                try {
                    if (type.equals("1")) {

                        CapitalPlease.setText(ZijinMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {

                        CapitalPlease.setText(C2CMoney.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.Capital_money:

                if (chargemoneName.getText().toString().trim().equals("")) {
                    t("请选择币种");
                    return;
                }
                if (CapitalPlease.getText().toString().trim().equals("")) {
                    t("请输入转入数量");
                    return;
                }

                putData(type);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void putData(String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("transferType", type);
        hashMap.put("coinCode", coinCode);
        hashMap.put("transferNum", CapitalPlease.getText().toString().trim());
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .fundstransfer(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts()) {
                        Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }
}
