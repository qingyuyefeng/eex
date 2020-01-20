package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.adpater.CurrencyDetailsAdapter;
import com.eex.assets.bean.Bilistdata;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.activity.home.ConducMoneyActivity;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.market.activity.DonationActivity;

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
 * @ClassName: CurrencyDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 11:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 11:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CurrencyDetailsActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.c2cdetail_recylerView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.LLRecharge)
    LinearLayout LLRecharge;
    /**
     *
     */
    @BindView(R.id.LLPut)
    LinearLayout LLPut;
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


    private CurrencyDetailsAdapter adapter;

    private BigDecimal CurrencyRate, FixedRate, MaxNum, MinNum;

    private FinalBitmap fb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_currency_details;
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
        comtitlebar.setTitle(coin);
        ////查看币种是否可以充币提币
        net();
        //获取c2c资金账户币种详情
        init();
    }

    /**
     * //查看币种是否可以充币提币
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "0");
        ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(CurrencyDetailsActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }

                    if (data.getData() != null) {

                        list.clear();
                        list.addAll(data.getData());
                        for (int i = 0; i < data.getData().size(); i++) {
                            if (coin.equals(data.getData().get(i).getCoinCode())) {
                                LLRecharge.setVisibility(View.VISIBLE);
                                LLPut.setVisibility(View.VISIBLE);
                                return;
                            } else {
                                LLRecharge.setVisibility(View.GONE);
                                LLPut.setVisibility(View.GONE);
                            }
                        }

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }

    /**
     * 获取c2c资金账户币种详情
     */
    @SuppressLint("CheckResult")
    private void init() {

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
                                LLTransfer.setVisibility(View.VISIBLE);
                                return;
                            } else {
                                LLTransfer.setVisibility(View.GONE);
                            }
                        }
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {

//
        adapter = new CurrencyDetailsAdapter(dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        //添加头部布局
        addHeaderView();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                intent.putExtra("JYBi", dataList.get(position).getDelKey().substring(0, dataList.get(position).getDelKey().indexOf("_")));
                intent.putExtra("DJBi", dataList.get(position).getDelKey().substring(dataList.get(position).getDelKey().indexOf("_") + 1, dataList.get(position).getDelKey().length()));
                // BTC/USDT
                intent.putExtra("Biname", dataList.get(position).getDelKey().replace("_", "/"));
                //BTC_USDT
                intent.putExtra("KlinBiname", dataList.get(position).getDelKey());
                intent.setClass(getActivity(), DonationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.c2cdetail_recylerView, R.id.LLRecharge, R.id.LLPut, R.id.LLTransfer, R.id.LLFinanciaManagement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.c2cdetail_recylerView:
                break;
            //充币
            case R.id.LLRecharge:
                //充币
                authStauts();
                break;
            //提币
            case R.id.LLPut:
                //提币
                isName();
                break;
            ////资金划转
            case R.id.LLTransfer:
                intent.putExtra("type", "1");
                intent.putExtra("coin1", coin);
                intent.putExtra("nuber1", CoinMoney);
                intent.setClass(CurrencyDetailsActivity.this, CapitalTransferActivity.class);
                startActivity(intent);
                break;
            //锁仓理财
            case R.id.LLFinanciaManagement:

                if (kv.decodeString("tokenId") == null) {
                    Toast.makeText(CurrencyDetailsActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                } else {
                    if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord").equals("")) {
                        Toast.makeText(CurrencyDetailsActivity.this, "请设置交易密码后再操作", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.setClass(CurrencyDetailsActivity.this, ConducMoneyActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提币
     */
    @SuppressLint("CheckResult")
    private void isName() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    //
                                    getBiListData();
                                } else {
                                    Toast.makeText(CurrencyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else if (data.getData().getLevel().equals(2)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    //
                                    getBiListData();
                                } else {
                                    Toast.makeText(CurrencyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else if (data.getData().getLevel().equals(1)) {

                                Toast.makeText(CurrencyDetailsActivity.this, "请完成实名等级2后操作", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CurrencyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void getBiListData() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "1");
        ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(CurrencyDetailsActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }
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

                        intent.putExtra("Bname", coin);
                        intent.putExtra("currencyRate", CurrencyRate.stripTrailingZeros().toPlainString());
                        intent.putExtra("fixedRate", FixedRate.stripTrailingZeros().toPlainString());
                        intent.putExtra("coinMax", MaxNum.stripTrailingZeros().toPlainString());
                        intent.putExtra("coinMin", MinNum.stripTrailingZeros().toPlainString());
                        intent.setClass(CurrencyDetailsActivity.this, CurrencyActivity.class);
                        startActivity(intent);


                    }

                }, throwable -> {

                });

    }


    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void authStauts() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {

                                    intent.putExtra("name", coin);
                                    intent.setClass(CurrencyDetailsActivity.this, RechargeMoneyBActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CurrencyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else if (data.getData().getLevel().equals(2)) {
                                intent.putExtra("name", coin);
                                intent.setClass(CurrencyDetailsActivity.this, RechargeMoneyBActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(1)) {

                                intent.putExtra("name", coin);
                                intent.setClass(CurrencyDetailsActivity.this, RechargeMoneyBActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CurrencyDetailsActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

                    }

                });


    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取资金账户币种详情
        getMoneyData();
        //获取同种币种
        getAboutData();
    }

    /**
     * 获取资金账户币种详情
     */
    @SuppressLint("CheckResult")
    private void getMoneyData() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getData() != null) {
                        mList.clear();
                        mList.addAll(data.getData().getUserCoinAccount());

                        for (int i = 0; i < mList.size(); i++) {
                            if (coin.equals(mList.get(i).getCoinCode())) {
                                dongjiedata = mList.get(i).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                CoinMoney = mList.get(i).getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
                                TotalMoeny = mList.get(i).getTotalMoeny().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

                            }
                        }
                    } else {

                    }
                }, throwable -> {

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

                    Log.e("获取同种币种", "getAboutData: " + data.getData().toString());
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

                    Log.e("putData", "getAboutData: " + data.getData().toString());
                    if (data.getData() != null) {

                        dataList.clear();
                        if (data.getData() != null) {
                            dataList.addAll(data.getData());
                            setView(dataList);
                        }
                    } else {

                    }
                }, throwable -> {

                });
    }

    private void setView(ArrayList<MainData> dataList) {
        adapter = new CurrencyDetailsAdapter(dataList);
        recyclerView.setAdapter(adapter);
        //添加头部布局
        addHeaderView();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                intent.putExtra("JYBi", dataList.get(position).getDelKey().substring(0, dataList.get(position).getDelKey().indexOf("_")));
                intent.putExtra("DJBi", dataList.get(position).getDelKey().substring(dataList.get(position).getDelKey().indexOf("_") + 1, dataList.get(position).getDelKey().length()));
                // BTC/USDT
                intent.putExtra("Biname", dataList.get(position).getDelKey().replace("_", "/"));
                //BTC_USDT
                intent.putExtra("KlinBiname", dataList.get(position).getDelKey());
                intent.setClass(getActivity(), DonationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addHeaderView() {

        View headerView = getLayoutInflater().inflate(R.layout.item_currency_c2c, null);
        ImageView imgAddress = (ImageView) headerView.findViewById(R.id.imgAddress);
        fb = FinalBitmap.create(getActivity());
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        fb.display(imgAddress, WPConfig.PicBaseUrl + imgAddressurl + "");
        TextView dongjie = (TextView) headerView.findViewById(R.id.dongjie);
        dongjie.setText(dongjiedata);
        TextView cocinName = (TextView) headerView.findViewById(R.id.cocinName);
        cocinName.setText(coin);
        TextView cocinAbbreviation = (TextView) headerView.findViewById(R.id.cocinAbbreviation);
        cocinAbbreviation.setText(coinName);

        TextView TotalAssets = (TextView) headerView.findViewById(R.id.TotalAssets);
        TotalAssets.setText(TotalMoeny);

        TextView AvailableBalance = (TextView) headerView.findViewById(R.id.AvailableBalance);
        AvailableBalance.setText(CoinMoney);
        adapter.addHeaderView(headerView);

    }


}
