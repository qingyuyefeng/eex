package com.eex.mvp.assrtsjava.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.Bilistdata;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.common.view.NewComTitleBar;
import com.eex.constant.Keys;
import com.eex.extensions.RxExtensionKt;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.mvp.assrtsjava.adapter.CommMoneyDetailsAdapter;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mainpage.MainContract;
import com.eex.mvp.mine.financialcenter.FinancialCenterActivity;
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
 * @ClassName: CommMoneyDetailsActivity
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 17:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 17:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CommMoneyDetailsActivity extends BaseActivity  implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.accountdetailsbar)
    NewComTitleBar accountdetailsbar;
    @BindView(R.id.account_details_img)
    ImageView accountDetailsImg;
    @BindView(R.id.account_details)
    TextView accountDetails;
    @BindView(R.id.account_details_bi)
    TextView accountDetailsBi;
    @BindView(R.id.account_details_Total)
    TextView accountDetailsTotal;
    @BindView(R.id.assets_available1)
    TextView assetsAvailable1;
    @BindView(R.id.assets_freeze1)
    TextView assetsFreeze1;
    @BindView(R.id.account_details_equivalent)
    TextView accountDetailsEquivalent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.assets_Charge_money)
    LinearLayout assetsChargeMoney;
    @BindView(R.id.assets_Mention_money)
    LinearLayout assetsMentionMoney;
    @BindView(R.id.assets_transfer)
    LinearLayout assetsTransfer;
    @BindView(R.id.account_details_financial)
    LinearLayout accountDetailsFinancial;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.accountdetails)
    LinearLayout accountdetails;


    private String BiName = "";
    private String coin = "";
    private String imgAddressurl = "";
    private String coinName = "";
    private String TotalMoeny = "";
    private String CoinMoney = "";
    private String dongjiedata = "";


    private ArrayList<Bilistdata> list = new ArrayList<>();
    private ArrayList<CtcaccountList> ctcaccountLists = new ArrayList<>();
    private ArrayList<Personal> mList = new ArrayList<Personal>();
    private ArrayList<MainList> itemslist1 = new ArrayList<MainList>();
    private ArrayList<MainList> newList = new ArrayList<MainList>();
    private ArrayList<MainData> dataList = new ArrayList<MainData>();

    private BigDecimal CurrencyRate, FixedRate, MaxNum, MinNum;
    private CommMoneyDetailsAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_account_details;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        if (getIntent().getStringExtra("coin") != null) {
            coin = getIntent().getStringExtra("coin");
            imgAddressurl = getIntent().getStringExtra("imgAddress");
            coinName = getIntent().getStringExtra("coinName");
            TotalMoeny = getIntent().getStringExtra("TotalMoeny");
            CoinMoney = getIntent().getStringExtra("CoinMoney");
            dongjiedata = getIntent().getStringExtra("dongjie");
        }



        accountdetailsbar.setTitle(coin);

        accountDetailsTotal.setText(TotalMoeny);
        assetsAvailable1.setText(CoinMoney);
        assetsFreeze1.setText(dongjiedata);
        accountDetails.setText(coinName);
        accountDetailsBi.setText(coin);
//        accountDetailsEquivalent.setText(CoinMoney);
        Glide.with(mContext).load( WPConfig.PicBaseUrl +imgAddressurl + "").into(accountDetailsImg);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //查看币种是否可以充币提币
        net();
        //获取c2c资金账户币种详情
        init();
        //获取资金账户币种详情
        getMoneyData();
        //获取同种币种
        getAboutData();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "0");
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {

                        list.clear();
                        list.addAll(data.getData());
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (coin.equals(data.getData().get(i).getCoinCode())) {
                                assetsChargeMoney.setVisibility(View.VISIBLE);
                                assetsMentionMoney.setVisibility(View.VISIBLE);
                                return;
                            } else {
                                assetsChargeMoney.setVisibility(View.GONE);
                                assetsMentionMoney.setVisibility(View.GONE);
                            }
                        }

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    @SuppressLint("CheckResult")
    private void init() {

        HashMap<String, String> hashMap = new HashMap<>();

        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {
                        ctcaccountLists.clear();
                        ctcaccountLists.addAll(data.getData().getList());

                        for (int i = 0; i < ctcaccountLists.size(); i++) {
                            if (coin.equals(ctcaccountLists.get(i).getCoinCode())) {
                                assetsTransfer.setVisibility(View.VISIBLE);
                                return;
                            } else {
                                assetsTransfer.setVisibility(View.GONE);
                            }
                        }
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    /**
     * 获取资金账户币种详情
     */
    @SuppressLint("CheckResult")
    private void getMoneyData() {
        HashMap<String, String> hashMap = new HashMap<>();

        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {
                        mList.clear();
                        mList.addAll(data.getData().getUserCoinAccount());

                        for (int i = 0; i < mList.size(); i++) {
                            if (coin.equals(mList.get(i).getCoinCode())) {
                                dongjiedata = mList.get(i).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                CoinMoney = mList.get(i).getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                TotalMoeny = mList.get(i).getTotalMoeny().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                accountDetailsTotal.setText(TotalMoeny);
                                assetsAvailable1.setText(CoinMoney);
                                assetsFreeze1.setText(dongjiedata);
                                return;
                            }
                        }
                    } else {

                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    /**
     * 获取同种币种
     */

    @SuppressLint("CheckResult")
    private void getAboutData() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {
                        BiName = "";
                        itemslist1 = new ArrayList<>();
                        itemslist1.clear();
                        itemslist1.addAll(data.getData());

                        newList = new ArrayList<MainList>();
                        newList.clear();
                        String sq = coin + "_";
                        for (int i = 0; i < itemslist1.size(); i++) {
                            if (itemslist1.get(i).getDealPair().contains(sq.toUpperCase())) {
                                String data1 = itemslist1.get(i).getDealPair().substring(0, itemslist1.get(i).getDealPair().indexOf("_"));
                                if (data1.equals(coin)) {
                                    newList.add(itemslist1.get(i));
                                }
                            }
                        }
                        for (int e = 0; e < newList.size(); e++) {

                            BiName += newList.get(e).getDealPair() + ",";

                        }

                        putData(BiName);

                    } else {

                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });


    }

    @SuppressLint("CheckResult")
    private void putData(String biName) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("delkeys", biName);
        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {

                        dataList.clear();
                        if (data.getData() != null) {
                            dataList.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                        }
                    } else {

                    }
                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {


        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new CommMoneyDetailsAdapter(dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.accountdetailsbar, R.id.account_details_img, R.id.account_details, R.id.account_details_bi, R.id.account_details_Total, R.id.assets_available1, R.id.assets_freeze1, R.id.account_details_equivalent, R.id.assets_Charge_money, R.id.assets_Mention_money, R.id.assets_transfer, R.id.account_details_financial})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accountdetailsbar:
                break;
            case R.id.account_details_img:
                break;
            case R.id.account_details:
                break;
            case R.id.account_details_bi:
                break;
            case R.id.account_details_Total:
                break;
            case R.id.assets_available1:
                break;
            case R.id.assets_freeze1:
                break;
            case R.id.account_details_equivalent:
                break;
//            充币
            case R.id.assets_Charge_money:
                authStauts();
                break;
//            提币
            case R.id.assets_Mention_money:
                isName();
                break;
//            划转
            case R.id.assets_transfer:

                intent.putExtra("type", "1");
                intent.putExtra("coinCode", coin);
                intent.putExtra("UsableMoney", CoinMoney);
                intent.setClass(getActivity(), AssetsChargeMoneActivity.class);
                startActivity(intent);
                break;
//            理财
            case R.id.account_details_financial:
//                if (kv.decodeString("tokenId") == null) {
//                    Toast.makeText(getActivity(),  "请登录后操作", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord").equals("")) {
//                        Toast.makeText(getActivity(), "请设置交易密码后再操作", Toast.LENGTH_SHORT).show();
//                    } else {
//                        intent.setClass(getActivity(), FinancialCenterActivity.class);
//                        startActivity(intent);
//                    }
//                }
                intent = new Intent(getActivity(),MainActivity.class);
                Bundle  bundle= new Bundle();
                bundle.putString(Keys.PARAM_PAIR, "ETH_USDT");
                bundle.putInt(Keys.MAIN_SELECT, 2);
                bundle.putInt(Keys.TRANS_SELECT, 0);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void isName() {

        HashMap<String, String> hashMap = new HashMap<>();

        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    //
                                    getBiListData();
                                } else {
                                    Toast.makeText(CommMoneyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else if (data.getData().getLevel().equals(2)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    //
                                    getBiListData();
                                } else {
                                    Toast.makeText(CommMoneyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else if (data.getData().getLevel().equals(1)) {

                                Toast.makeText(CommMoneyDetailsActivity.this, "请完成实名等级2后操作", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CommMoneyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {

                            Log.e("错误", "isName: " + e.toString() );
                        }

                    }

                });
    }

    @SuppressLint("CheckResult")
    private void getBiListData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "1");
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        list = data.getData();

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().equals(coin)) {
                                CurrencyRate = list.get(i).getCurrencyRate();
                                FixedRate = list.get(i).getFixedRate();
                                MaxNum = list.get(i).getMaxNum();
                                MinNum = list.get(i).getMinNum();
                            }
                        }

                        intent.putExtra("coinCode", coin);
                        intent.putExtra("currencyRate", CurrencyRate.stripTrailingZeros().toPlainString());
                        intent.putExtra("fixedRate", FixedRate.stripTrailingZeros().toPlainString());
                        intent.putExtra("coinMax", MaxNum.stripTrailingZeros().toPlainString());
                        intent.putExtra("coinMin", MinNum.stripTrailingZeros().toPlainString());
                        intent.setClass(CommMoneyDetailsActivity.this, WithdrawMoneyActivity.class);
                        startActivity(intent);


                    }

                }, throwable -> {

                });
    }

    @SuppressLint("CheckResult")
    private void authStauts() {
        HashMap<String, String> hashMap = new HashMap<>();

        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {

                                    intent.putExtra("coinCode", coin);
                                    intent.setClass(CommMoneyDetailsActivity.this, CurrencyRechargeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CommMoneyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else if (data.getData().getLevel().equals(2)) {
                                intent.putExtra("coinCode", coin);
                                intent.setClass(CommMoneyDetailsActivity.this, CurrencyRechargeActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(1)) {

                                intent.putExtra("coinCode", coin);
                                intent.setClass(CommMoneyDetailsActivity.this, CurrencyRechargeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CommMoneyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.e("TAG1111", "authStauts: " + e.toString() );
                            //Toast.makeText(getActivity(),"TAG", Toast.LENGTH_SHORT).show();
                        }

                    }

                });


    }

    @Override
    public void onRefresh() {
        //查看币种是否可以充币提币
        net();
        //获取c2c资金账户币种详情
        init();
        //获取资金账户币种详情
        getMoneyData();
        //获取同种币种
        getAboutData();
    }
}
