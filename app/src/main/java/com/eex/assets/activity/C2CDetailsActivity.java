package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.BuySellActivity;


import net.tsz.afinal.FinalBitmap;

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
 * @ClassName: C2CDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 13:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 13:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CDetailsActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.imgAddress)
    ImageView imgAddress;
    /**
     *
     */
    @BindView(R.id.cocinName)
    TextView cocinName;
    /**
     *
     */
    @BindView(R.id.cocinAbbreviation)
    TextView cocinAbbreviation;
    /**
     *
     */
    @BindView(R.id.TotalAssets)
    TextView TotalAssets;
    /**
     *
     */
    @BindView(R.id.AvailableBalance)
    TextView AvailableBalance;
    /**
     *
     */
    @BindView(R.id.Freeze)
    TextView Freeze;
    /**
     *
     */
    @BindView(R.id.LLTransfer)
    LinearLayout LLTransfer;
    /**
     *
     */
    @BindView(R.id.LLFinanciaManagement)
    LinearLayout LLFinanciaManagement;

    private FinalBitmap fb;
    private String coin = "";
    private String imgurl = "";
    private String totalAssets = "";
    private String usableMoney = "";
    private String frozenMoney = "";


    private ArrayList<Personal> list = new ArrayList<Personal>();
    /**
     * 全部数据
     */
    private ArrayList<CtcaccountList> ctcaccountLists = new ArrayList();


    @Override
    protected int getLayoutId() {

        return R.layout.activity_c2_cdetails;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        if (getIntent().getStringExtra("coin") != null) {
            coin = getIntent().getStringExtra("coin");
            imgurl = getIntent().getStringExtra("imgurl");
            totalAssets = getIntent().getStringExtra("totalAssets");
            usableMoney = getIntent().getStringExtra("usableMoney");
            frozenMoney = getIntent().getStringExtra("frozenMoney");

        }

        comtitlebar.setTitle(coin + "");

        fb = FinalBitmap.create(getActivity());
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        fb.display(imgAddress, WPConfig.PicBaseUrl + imgurl + "");

        /**
         *
         获取资金账户币种
         */
        getziJinData();
        /**
         *
         获取c2c资金账户币种详情
         */
        getData();
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

    @OnClick({R.id.comtitlebar, R.id.imgAddress, R.id.cocinName, R.id.cocinAbbreviation, R.id.TotalAssets, R.id.AvailableBalance, R.id.Freeze, R.id.LLTransfer, R.id.LLFinanciaManagement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.imgAddress:
                break;
            case R.id.cocinName:
                break;
            case R.id.cocinAbbreviation:
                break;
            case R.id.TotalAssets:
                break;
            case R.id.AvailableBalance:
                break;
            case R.id.Freeze:
                break;
            case R.id.LLTransfer:
                intent.setClass(C2CDetailsActivity.this, CapitalTransferActivity.class);

                intent.putExtra("type", "2");
                intent.putExtra("coin1", coin);
                intent.putExtra("nuber1", usableMoney);
                startActivity(intent);
                break;
            case R.id.LLFinanciaManagement:
                intent.setClass(C2CDetailsActivity.this , BuySellActivity.class);
                startActivity(intent);

                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        /**
         *
         获取资金账户币种
         */
        getziJinData();
        /**
         *
         获取c2c资金账户币种详情
         */
        getData();

    }

    /**
     * 获取c2c资金账户币种详情
     */
    @SuppressLint("CheckResult")
    private void getData() {

        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {

                        ctcaccountLists.clear();
                        ctcaccountLists.addAll(data.getData().getList());
                        for (int i = 0; i < ctcaccountLists.size(); i++) {
                            if (coin.equals(ctcaccountLists.get(i).getCoinCode())) {
                                totalAssets = ctcaccountLists.get(i).getTotalAssets().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                usableMoney = ctcaccountLists.get(i).getUsableMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                frozenMoney = ctcaccountLists.get(i).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                TotalAssets.setText(totalAssets);
                                AvailableBalance.setText(usableMoney);
                                Freeze.setText(frozenMoney);
                            }
                        }

                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    /**
     * 获取资金账户币种
     */
    @SuppressLint("CheckResult")
    private void getziJinData() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        list = data.getData().getUserCoinAccount();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().equals(coin)) {

                                LLTransfer.setVisibility(View.VISIBLE);
                                return;
                            } else {
                                LLTransfer.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }
}
