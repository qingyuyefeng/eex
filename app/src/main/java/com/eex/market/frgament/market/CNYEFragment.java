package com.eex.market.frgament.market;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.CommonUtil;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.market.activity.DonationActivity;
import com.eex.market.adpater.ReclierViewNewAdapter;
import com.eex.market.bean.MeChoiceListvo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;


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
 * @Package: com.overthrow.market.frgament
 * @ClassName: CNYEFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/20 10:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/20 10:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * <p>
 * CNYEF
 */
public class CNYEFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.tx_name)
    TextView txName;
    /**
     *
     */
    @BindView(R.id.ck_name)
    CheckBox ckName;
    /**
     *
     */
    @BindView(R.id.tx_Nuber)
    TextView txNuber;
    /**
     *
     */
    @BindView(R.id.ck_nuber)
    CheckBox ckNuber;
    /**
     *
     */
    @BindView(R.id.tx_money)
    TextView txMoney;
    /**
     *
     */
    @BindView(R.id.ck_money)
    CheckBox ckMoney;
    /**
     *
     */
    @BindView(R.id.tx_up)
    TextView txUp;
    /**
     *
     */
    @BindView(R.id.ck_up)
    CheckBox ckUp;
    /**
     *
     */
    @BindView(R.id.LL_type)
    LinearLayout LLType;
    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    private ArrayList<MeChoiceListvo> list = new ArrayList<>();
    private ArrayList<MainData> data = new ArrayList<>();
    private ArrayList<MainList> mainLists = new ArrayList<>();

    private ReclierViewNewAdapter adapter;

    private String dealPair = "";

    private String MoneyType = "NO";

    /**
     * RecyclerView滑动监听开关
     */
    private boolean reckviewTouth = true;

    //上下状态
    private String Type = "down";
    //上下切换按钮
    private String isType = "成交量";

    private String ckType1 = "2";
    private String ckType2 = "1";
    private String ckType3 = "1";
    private String ckType4 = "1";

    private BigDecimal newData1, newData11;
    private BigDecimal newData2, newData22;
    //button状态
    private String ButtonType = "CNYE";


    @Override
    protected void lazyLoad() {

        //首页list集合
        getDealPairList("CNYE");
        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        //首页list集合
        getDealPairList("CNYE");
        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }


    /**
     * 首页list集合
     *
     * @param cnye
     */
    @SuppressLint("CheckResult")
    private void getDealPairList(String cnye) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pricingCoin", cnye);
        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    try {
                        if (data.isStauts() == true) {
                            dealPair = "";
                            mainLists.clear();
                            mainLists.addAll(data.getData());
                            for (int i = 0; i < mainLists.size(); i++) {
                                dealPair += mainLists.get(i).getDealPair() + ",";
                            }
                            getIndexMaketList(dealPair);

                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    /***
     *
     * 获取交易对详情
     * @param dealPair
     */
    @SuppressLint("CheckResult")
    private void getIndexMaketList(String dealPair) {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("delkeys", dealPair);

        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (arrayListData.getCode() == 200) {
                        data.clear();


//                        for (int i = 0; i < data.size(); i++) {
//
//                            String text = data.get(i).getDelKey();
//                            String text3 = text.substring(text.indexOf("_"));
//                            if (text3.equals("_USDT")) {
//                                if (data.get(i).getNewPrice() != null && data.get(i).getDealNum() != null) {
//                                    data.add(data.get(i));
//                                }
//                            }
//                        }
                        Collections.sort(data, new Comparator<MainData>() {

                            @Override
                            public int compare(MainData o1, MainData o2) {
                                if (o2.getDealNum() == null) {
                                    return -1;
                                }
                                if (o1.getDealNum() == null) {
                                    return 1;
                                }
                                if (o2.getNewPrice() == null) {
                                    return -1;
                                }
                                if (o1.getNewPrice() == null) {
                                    return 1;
                                }
                                return o2.getDealNum().multiply(o2.getNewPrice()).compareTo(o1.getDealNum().multiply(o1.getNewPrice())) > 0 ? 1 : -1;

                            }
                        });
                        data.addAll(arrayListData.getData());


                        adapter.notifyDataSetChanged();

                    } else {
                        t(arrayListData.getMsg());
                    }


                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    /**
     * 获取实时价格
     */
    private void webSoket() {


        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/ALL_ALL")
                .compose(RxSchedulers.io_main())
                .subscribe(new WebSocketSubscriber2<MainData>() {
                    @Override
                    protected void onMessage(MainData pageData) {
                        MoneyType = "NO";
                        if (pageData != null) {
                            try {
                                for (int i = 0; i < data.size(); i++) {

                                    if (data.get(i).getDelKey().equals(pageData.getDelKey())) {
                                        data.get(i).setDealNum(pageData.getDealNum());
                                        data.get(i).setDelKey(pageData.getDelKey());
                                        data.get(i).setFooPrice(pageData.getFooPrice());
                                        data.get(i).setHigePrice(pageData.getHigePrice());
                                        data.get(i).setNewPrice(pageData.getNewPrice());
                                        data.get(i).setUsdtCny(pageData.getUsdtCny());
                                        data.get(i).setLastPrice(pageData.getLastPrice());

                                    }

                                }
                                if (reckviewTouth) {
                                    try {
                                        inviewdata(ButtonType, data);
                                        adapter.setNewData(data);
                                    } catch (Exception e) {

                                    }
                                }

                            } catch (Exception e) {
                                Log.e("USDTtype", "无更新");
                            }
                        }

                    }


                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连1");

                    }

                });
    }

    /**
     * 在webSoket 根据需求来 排序
     *
     * @param buttonType
     * @param data
     */
    private void inviewdata(String buttonType, ArrayList<MainData> data) {
        if (ButtonType.equals("自选")) {
            if (isType.equals("名称")) {
                if (Type.equals("down")) {
                    returnNameList1(data);
                } else {
                    returnNameList(data);
                }
                return;
            }
            if (isType.equals("成交量")) {
                if (Type.equals("down")) {
                    //成交量从大到小
                    returnNuberList1(data);
                } else {
                    //成交量从小到大
                    returnNuberList(data);
                }
                return;
            }
            if (isType.equals("最新价")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }
            if (isType.equals("涨跌")) {
                if (Type.equals("down")) {
                    returnUPList(data);
                } else {
                    returnDownList(data);
                }
                return;
            }
            return;
        }
//EBT
        if (ButtonType.equals("PAX")) {
            if (isType.equals("名称")) {
                if (Type.equals("down")) {
                    returnNameList(data);
                } else {
                    returnNameList1(data);
                }
                return;
            }
            if (isType.equals("成交量")) {
                if (Type.equals("down")) {
                    returnNuberList1(data);
                } else {
                    returnNuberList(data);
                }
                return;
            }
            if (isType.equals("最新价")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }
            if (isType.equals("涨跌")) {
                if (Type.equals("down")) {
                    returnUPList(data);
                } else {
                    returnDownList(data);
                }
                return;
            }
            return;
        }
        //USDT
        if (ButtonType.equals("USDT")) {
            if (isType.equals("名称")) {
                if (Type.equals("down")) {
                    returnNameList(data);
                } else {
                    returnNameList1(data);
                }
                return;
            }
            if (isType.equals("成交量")) {
                if (Type.equals("down")) {
                    returnNuberList1(data);
                } else {
                    returnNuberList(data);
                }
                return;
            }
            if (isType.equals("最新价")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }
            if (isType.equals("涨跌")) {
                if (Type.equals("down")) {
                    returnUPList(data);
                } else {
                    returnDownList(data);
                }
                return;
            }
            return;
        }
        //CNYE
        if (ButtonType.equals("CNYE")) {
            if (isType.equals("名称")) {
                if (Type.equals("down")) {
                    returnNameList(data);
                } else {
                    returnNameList1(data);
                }
                return;
            }
            if (isType.equals("成交量")) {
                if (Type.equals("down")) {
                    returnNuberList1(data);
                } else {
                    returnNuberList(data);
                }
                return;
            }
            if (isType.equals("最新价")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }
            if (isType.equals("涨跌")) {
                if (Type.equals("down")) {
                    returnUPList(data);
                } else {
                    returnDownList(data);
                }
                return;
            }
            return;
        }
    }


    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new ReclierViewNewAdapter(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));


        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                if (CommonUtil.isFastClick()) {
                    switch (view.getId()) {
                        case R.id.ll_Btn:
                            reckviewTouth = false;
                            //BTC
                            intent.putExtra("JYBi", data.get(position).getDelKey().substring(0, data.get(position).getDelKey().indexOf("_")));
                            //USDT
                            intent.putExtra("DJBi", data.get(position).getDelKey().substring(data.get(position).getDelKey().indexOf("_") + 1, data.get(position).getDelKey().length()));
                            // BTC/USDT
                            intent.putExtra("Biname", data.get(position).getDelKey().replace("_", "/"));
                            //BTC_USDT
                            intent.putExtra("KlinBiname", data.get(position).getDelKey());
                            //

                            intent.setClass(getContext(), DonationActivity.class);
                            startActivity(intent);
                            reckviewTouth = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        //
        webSoket();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market_recyclerview;
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

    @OnClick({R.id.tx_name, R.id.ck_name, R.id.tx_Nuber, R.id.ck_nuber, R.id.tx_money, R.id.ck_money, R.id.tx_up, R.id.ck_up, R.id.LL_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            ///默认降序，当用户点击后变为升序即ckType1位true
            case R.id.tx_name:

                isType = "名称";

                if (ckType1.equals("1")) {//A--B
                    txName.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckName.setVisibility(View.VISIBLE);
                    ckName.setChecked(false);
                    ckType1 = "2";
                    returnNameList(data);
                    Type = "down";
                } else {//B--A
                    txName.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckName.setVisibility(View.VISIBLE);
                    ckName.setChecked(true);
                    ckType1 = "1";
                    returnNameList1(data);
                    Type = "up";
                }

                txNuber.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckNuber.setVisibility(View.GONE);

                txMoney.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckMoney.setVisibility(View.GONE);

                txUp.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckUp.setVisibility(View.GONE);

                break;
            case R.id.ck_name:
                break;
            case R.id.tx_Nuber:

                isType = "成交量";
                //大-小
                if (ckType2.equals("1")) {
                    txNuber.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckNuber.setVisibility(View.VISIBLE);
                    ckNuber.setChecked(false);
                    ckType2 = "2";
                    returnNuberList(data);
                    Type = "up";
                } else {//小到大
                    txNuber.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckNuber.setVisibility(View.VISIBLE);
                    ckNuber.setChecked(true);
                    ckType2 = "1";
                    returnNuberList1(data);
                    Type = "down";
                }
                txName.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckName.setVisibility(View.GONE);

                txMoney.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckMoney.setVisibility(View.GONE);

                txUp.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckUp.setVisibility(View.GONE);
                break;
            case R.id.ck_nuber:
                break;
            //最新价格排序
            case R.id.tx_money:
                isType = "最新价";
                //大-小
                if (ckType3.equals("1")) {
                    txMoney.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckMoney.setVisibility(View.VISIBLE);
                    ckMoney.setChecked(false);
                    returnNewPriceList(data);
                    Type = "down";
                    ckType3 = "2";
                } else {//小-大
                    txMoney.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckMoney.setVisibility(View.VISIBLE);
                    ckMoney.setChecked(true);
                    ckType3 = "1";
                    returnNewPriceList1(data);
                    Type = "up";
                }

                txNuber.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckNuber.setVisibility(View.GONE);

                txName.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckName.setVisibility(View.GONE);

                txUp.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckUp.setVisibility(View.GONE);
                break;
            case R.id.ck_money:
                break;
            //24h涨跌排序
            case R.id.tx_up:
                isType = "涨跌";
                //涨
                if (ckType4.equals("1")) {
                    txUp.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckUp.setVisibility(View.VISIBLE);
                    ckUp.setChecked(false);
                    ckType4 = "2";
                    returnUPList(data);
                    Type = "down";
                } else {//跌
                    txUp.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                    ckUp.setVisibility(View.VISIBLE);
                    ckUp.setChecked(true);
                    ckType4 = "1";
                    returnDownList(data);
                    Type = "up";
                }
                txMoney.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckMoney.setVisibility(View.GONE);

                txNuber.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckNuber.setVisibility(View.GONE);

                txName.setTextColor(getActivity().getResources().getColor(R.color.main_textviewc));
                ckName.setVisibility(View.GONE);
                break;
            case R.id.ck_up:
                break;
            case R.id.LL_type:
                break;

            default:
                break;
        }
    }

    /**
     * 按最新成交价量排序小-大
     *
     * @param data
     */
    private void returnNewPriceList1(ArrayList<MainData> data) {
        Collections.sort(data, new Comparator<MainData>() {

            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getNewPrice() == null) {
                    return -1;
                }
                if (o1.getNewPrice() == null) {
                    return 1;
                }
                return o2.getNewPrice().compareTo(o1.getNewPrice()) > 0 ? 1 : -1;
            }
        });
        adapter.setNewData(data);
    }

    /**
     * 按最新成交价量排序大-小
     *
     * @param data
     */
    private void returnNewPriceList(ArrayList<MainData> data) {
        Collections.sort(data, new Comparator<MainData>() {

            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getNewPrice() == null) {
                    return -1;
                }
                if (o1.getNewPrice() == null) {
                    return 1;
                }
                return o2.getNewPrice().compareTo(o1.getNewPrice()) > 0 ? 1 : -1;
            }
        });
        Collections.reverse(data);
        adapter.setNewData(data);
    }


    /**
     * A-B名称比较
     *
     * @param data
     */
    private void returnNameList(ArrayList<MainData> data) {
        Collections.sort(data, new Comparator<MainData>() {
            @Override
            public int compare(MainData o1, MainData o2) {
                return o1.getDelKey().compareTo(o2.getDelKey());
            }
        });
        adapter.setNewData(data);
    }

    /**
     * B-A名称比较
     *
     * @param data
     */
    private void returnNameList1(ArrayList<MainData> data) {
        Collections.sort(data, new Comparator<MainData>() {
            @Override
            public int compare(MainData o1, MainData o2) {
                return o2.getDelKey().compareTo(o1.getDelKey());
            }
        });
        adapter.setNewData(data);
    }


    /**
     * 按成交量排序小-大
     *
     * @param data
     */
    private void returnNuberList(ArrayList<MainData> data) {
        if (data != null) {
            Collections.sort(data, new Comparator<MainData>() {

                @Override
                public int compare(MainData o1, MainData o2) {
                    if (o2.getDealNum() == null) {
                        return -1;
                    }
                    if (o1.getDealNum() == null) {
                        return 1;
                    }
                    if (o2.getNewPrice() == null) {
                        return -1;
                    }
                    if (o1.getNewPrice() == null) {
                        return 1;
                    }
                    return o2.getDealNum().multiply(o2.getNewPrice()).compareTo(o1.getDealNum().multiply(o1.getNewPrice())) > 0 ? 1 : -1;
                }
            });
            Collections.reverse(data);
            adapter.setNewData(data);
        }
    }

    /**
     * 按成交量排序大-小
     *
     * @param data
     */
    private void returnNuberList1(ArrayList<MainData> data) {


        Collections.sort(data, new Comparator<MainData>() {

            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getDealNum() == null) {
                    return -1;
                }
                if (o1.getDealNum() == null) {
                    return 1;
                }
                if (o2.getNewPrice() == null) {
                    return -1;
                }
                if (o1.getNewPrice() == null) {
                    return 1;
                }
                return o2.getDealNum().multiply(o2.getNewPrice()).compareTo(o1.getDealNum().multiply(o1.getNewPrice())) > 0 ? 1 : -1;

            }
        });
        adapter.setNewData(data);
    }

    /**
     * 按涨排序大
     *
     * @param data
     */
    private void returnUPList(ArrayList<MainData> data) {


        Collections.sort(data, new Comparator<MainData>() {
            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getNewPrice() == null) {
                    return 1;
                }
                if (o1.getNewPrice() == null) {
                    return -1;
                }
                if (o1.getLastPrice() == null) {
                    return -1;
                }
                if (o2.getLastPrice() == null) {
                    return 1;
                }
                try {
                    BigDecimal money1 = o1.getNewPrice().divide(o1.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                    newData11 = money1.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                } catch (Exception e) {
                    newData11 = new BigDecimal(0);
                }

                try {
                    BigDecimal money2 = o2.getNewPrice().divide(o2.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                    newData22 = money2.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                } catch (Exception e) {
                    newData22 = new BigDecimal(0);
                }
                return newData11.compareTo(newData22) > 0 ? 1 : -1;

            }
        });

        adapter.setNewData(data);

    }


    /**
     * 按跌幅排序大-小
     *
     * @param data
     */
    private void returnDownList(ArrayList<MainData> data) {

        Collections.sort(data, new Comparator<MainData>() {
            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getNewPrice() == null) {
                    return -1;
                }
                if (o1.getNewPrice() == null) {
                    return 1;
                }
                if (o1.getLastPrice() == null) {
                    return 1;
                }
                if (o2.getLastPrice() == null) {
                    return -1;
                }
                BigDecimal money1 = o1.getNewPrice().divide(o1.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                newData1 = money1.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

                BigDecimal money2 = o2.getNewPrice().divide(o2.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                newData2 = money2.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

                return newData2.compareTo(newData1) > 0 ? 1 : -1;
            }
        });
        adapter.setNewData(data);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/ALL_ALL").subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    @Override
    public void onResume() {
        super.onResume();


        //首页list集合
        getDealPairList("CNYE");
        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }

    @Override
    public void onRefresh() {


        //首页list集合
        getDealPairList("CNYE");
        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }
}
