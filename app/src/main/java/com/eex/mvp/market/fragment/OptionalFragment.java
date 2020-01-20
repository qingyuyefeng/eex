package com.eex.mvp.market.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.constant.Keys;
import com.eex.home.bean.MainData;

import com.eex.market.adpater.ReclierViewNewAdapter;
import com.eex.market.bean.MeChoiceListvo;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mainpage.MainContract;

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
 * @ProjectName: Futures
 * @Package: com.futures.market.fragment.market
 * @ClassName: OptionalFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/9 16:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/9 16:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 自选
 */
public class OptionalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemClickListener {

    /***
     *
     * 名称
     */
    @BindView(R.id.market_name)
    LinearLayout marketName;
    /***
     *
     *
     */
    @BindView(R.id.market_biname)
    TextView marketBiName;
    /***
     *
     *
     */
    @BindView(R.id.market_volume)
    TextView marketVolume;
    /***
     *
     * 名称image
     */
    @BindView(R.id.market_name_image)
    ImageView marketNameImage;

    /***
     *
     *最新价格名称image
     */
    @BindView(R.id.market_latest_price_image)
    ImageView marketLatestPriceImage;
    /***
     * 最新价格
     *
     */
    @BindView(R.id.market_latest_price)
    LinearLayout marketLatestPrice;
    /***
     *
     *
     */
    @BindView(R.id.market_bilatest_price)
    TextView marketBiLatestPrice;
    /***
     *浮动率image
     *
     */
    @BindView(R.id.market_floating_image)
    ImageView marketFloatingImage;
    /***
     * 浮动率
     *
     */
    @BindView(R.id.market_floating)
    LinearLayout marketFloating;
    /***
     *
     *
     */
    @BindView(R.id.market_bifloating)
    TextView marketBiFloating;

    /***
     *涨跌幅 image
     *
     */
    @BindView(R.id.market_applies_image)
    ImageView marketAppliesImage;
    /***
     * 涨跌幅
     *
     */
    @BindView(R.id.market_applies)
    LinearLayout marketApplies;

    /***
     *
     *
     */
    @BindView(R.id.market_biapplies)
    TextView marketBiApplies;

    /***
     *recyclerView
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /***
     *
     *
     */
    Unbinder unbinder;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    /**
     *
     */
    @BindView(R.id.container)
    FrameLayout container;


    private String dealPair = "";
    private String MoneyType = "NO";

    //上下状态
    private String Type = "down";
    //上下切换按钮
    private String isType = "成交量";
    //button状态
    private String ButtonType = "自选";

    private ArrayList<MeChoiceListvo> list = new ArrayList<>();
    private ArrayList<MainData> data = new ArrayList<>();
    private ArrayList<MainData> mainLists = new ArrayList<>();

    private ReclierViewNewAdapter adapter;

    private BigDecimal newData1, newData11;
    private BigDecimal newData2, newData22;
    private BigDecimal newData3, newData33;
    private BigDecimal newData4, newData44;

    private String ckType1 = "2";
    private String ckType2 = "1";
    private String ckType3 = "1";
    private String ckType4 = "1";


    @Override
    protected void refreshData(Bundle savedInstanceState) {

        //首页list集合
        getDealPairList();

        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }


    /**
     * 首页list集合
     *
     * @param
     */
    @SuppressLint("CheckResult")
    private void getDealPairList() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageSize", "100");
        hashMap.put("pageNo", "1");
        ApiFactory.getInstance()
                .getDealPairCollectionList(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageListData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    try {
                        if (pageListData.isStauts() == true) {
                            dealPair = "";
                            mainLists.clear();
                            list.clear();
                            list.addAll(pageListData.getData().getResultList());
                            for (int i = 0; i < list.size(); i++) {
                                dealPair += list.get(i).getDealpear() + ",";
                            }
                            getIndexMaketList(dealPair);

                        } else {
                            adapter = new ReclierViewNewAdapter(data);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(adapter);
                            adapter.setEmptyView(new EmptyView(getActivity()));

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
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {
                    dialog.dismiss();
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (arrayListData.getCode() == 200) {
                        data.clear();

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
                    dialog.dismiss();
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    /**
     * 获取实时价格ButtonType
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

                                try {
                                    inviewdata(ButtonType, data);
                                    adapter.setNewData(data);
                                } catch (Exception e) {

                                }


                            } catch (Exception e) {
                                Log.e("USDTtype", "无更新");
                            }
                        }

                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("USDTFragment", "开始+1");

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
            if (isType.equals("最新价格")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }

            if (isType.equals("浮动率")) {
                if (Type.equals("down")) {
                    Floatingrate(data);
                } else {
                    Floatingrate1(data);
                }
                return;
            }


            if (isType.equals("涨跌幅")) {
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
            if (isType.equals("最新价格")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }
            if (isType.equals("浮动率")) {
                if (Type.equals("down")) {
                    Floatingrate(data);
                } else {
                    Floatingrate1(data);
                }
                return;
            }
            if (isType.equals("涨跌幅")) {
                if (Type.equals("down")) {
                    returnUPList(data);
                } else {
                    returnDownList(data);
                }
                return;
            }
            return;
        }
        //期货
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
            if (isType.equals("最新价格")) {
                if (Type.equals("down")) {
                    returnNewPriceList(data);
                } else {
                    returnNewPriceList1(data);
                }
                return;
            }
            if (isType.equals("浮动率")) {
                if (Type.equals("down")) {
                    Floatingrate(data);
                } else {
                    Floatingrate1(data);
                }
                return;
            }
            if (isType.equals("涨跌幅")) {
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

    /**
     * 浮动率
     * <p>
     * 小 到 大
     *
     * @param data
     */
    private void Floatingrate(ArrayList<MainData> data) {
        Collections.sort(data, new Comparator<MainData>() {
            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getHigePrice() == null) {
                    return 1;
                }
                if (o1.getHigePrice() == null) {
                    return -1;
                }
                if (o1.getFooPrice() == null) {
                    return -1;
                }
                if (o2.getFooPrice() == null) {
                    return 1;
                }

                try {
                    //          (最高价 - 最低价)/最低价 * 100%
                    //((high-low)/low*100)
                    BigDecimal high = o1.getHigePrice();
                    BigDecimal low = o1.getFooPrice();
                    BigDecimal s1 = high.subtract(low);
                    BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal s3 = s2.multiply(new BigDecimal(100));
                    newData33 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                } catch (Exception e) {
                    newData33 = new BigDecimal(0);
                }
                try {
                    //          (最高价 - 最低价)/最低价 * 100%
                    //((high-low)/low*100)
                    BigDecimal high = o2.getHigePrice();
                    BigDecimal low = o2.getFooPrice();
                    BigDecimal s1 = high.subtract(low);
                    BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal s3 = s2.multiply(new BigDecimal(100));
                    newData44 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                } catch (Exception e) {
                    newData44 = new BigDecimal(0);
                }


                return newData33.compareTo(newData44) > 0 ? 1 : -1;
            }
        });
        adapter.setNewData(data);
    }

    /**
     * 浮动率  大 到 小
     *
     * @param data
     */
    private void Floatingrate1(ArrayList<MainData> data) {

        Collections.sort(data, new Comparator<MainData>() {
            @Override
            public int compare(MainData o1, MainData o2) {
                if (o2.getHigePrice() == null) {
                    return -1;
                }
                if (o1.getHigePrice() == null) {
                    return 1;
                }
                if (o1.getFooPrice() == null) {
                    return 1;
                }
                if (o2.getFooPrice() == null) {
                    return -1;
                }

                try {
                    //          (最高价 - 最低价)/最低价 * 100%
                    //((high-low)/low*100)
                    BigDecimal high = o1.getHigePrice();
                    BigDecimal low = o1.getFooPrice();
                    BigDecimal s1 = high.subtract(low);
                    BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal s3 = s2.multiply(new BigDecimal(100));
                    newData3 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                } catch (Exception e) {
                    newData3 = new BigDecimal(0);
                }
                try {
                    //          (最高价 - 最低价)/最低价 * 100%
                    //((high-low)/low*100)
                    BigDecimal high = o2.getHigePrice();
                    BigDecimal low = o2.getFooPrice();
                    BigDecimal s1 = high.subtract(low);
                    BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal s3 = s2.multiply(new BigDecimal(100));
                    newData4 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                } catch (Exception e) {
                    newData4 = new BigDecimal(0);
                }

                return newData3.compareTo(newData4) > 0 ? 1 : -1;

            }
        });
        adapter.setNewData(data);
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
                if (o2.getDealNum() == null) {
                    return -1;
                }
                if (o1.getDealNum() == null) {
                    return 1;
                }
                return o2.getDealNum().compareTo(o1.getDealNum()) > 0 ? 1 : -1;
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
                if (o2.getDealNum() == null) {
                    return -1;
                }
                if (o1.getDealNum() == null) {
                    return 1;
                }
                return o2.getDealNum().compareTo(o1.getDealNum()) > 0 ? 1 : -1;
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
    protected void initUiAndListener() {


        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


        adapter = new ReclierViewNewAdapter(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setEmptyView(new EmptyView(getActivity()));


        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Keys.PARAM_PAIR, data.get(position).getDelKey());
                if (getActivity() instanceof MainActivity) {
                    Activity activity = (MainActivity) getActivity();
                    bundle.getInt(Keys.TRANS_SELECT,0);
                    ((MainContract.View) activity).selectTab(2, bundle);
                }
            }
        });
        webSoket();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market_public;
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

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.market_name, R.id.market_volume, R.id.market_latest_price, R.id.market_floating, R.id.market_applies})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.market_name:

                isType = "名称";
                //A--B
                if (ckType1.equals("1")) {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

                    //图标隐藏
                    marketNameImage.setVisibility(View.VISIBLE);
                    marketNameImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_top));

                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.GONE);
                    ckType1 = "2";
                    returnNameList(data);
                    Type = "down";
                    //B--A
                } else {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

                    //图标隐藏
                    marketNameImage.setVisibility(View.VISIBLE);
                    marketNameImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_buttom));
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.GONE);

                    ckType1 = "1";
                    returnNameList1(data);
                    Type = "up";
                }

                break;


            case R.id.market_volume:

                isType = "成交量";
                //A--B
                if (ckType1.equals("1")) {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

                    //图标隐藏
                    marketNameImage.setVisibility(View.VISIBLE);
                    marketNameImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_top));
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.GONE);
                    ckType1 = "2";
                    returnNuberList(data);
                    Type = "down";
                    //B--A
                } else {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

                    //图标隐藏
                    marketNameImage.setVisibility(View.VISIBLE);
                    marketNameImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_buttom));
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.GONE);

                    ckType1 = "1";
                    returnNuberList1(data);
                    Type = "up";
                }

                break;


            case R.id.market_latest_price:
                isType = "最新价格";
                if (ckType3.equals("1")) {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    //图标隐藏
                    marketNameImage.setVisibility(View.GONE);
                    marketLatestPriceImage.setVisibility(View.VISIBLE);
                    marketLatestPriceImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_top));
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.GONE);

                    returnNewPriceList(data);
                    Type = "down";
                    ckType3 = "2";
                    //小-大
                } else {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

                    //图标隐藏
                    marketNameImage.setVisibility(View.GONE);
                    marketLatestPriceImage.setVisibility(View.VISIBLE);
                    marketLatestPriceImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_buttom));
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.GONE);


                    ckType3 = "1";
                    returnNewPriceList1(data);
                    Type = "up";
                }
                break;
            case R.id.market_floating:
                isType = "浮动率";
                //大-小
                if (ckType2.equals("1")) {

                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));


                    //图标隐藏
                    marketNameImage.setVisibility(View.GONE);
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.VISIBLE);
                    marketFloatingImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_top));
                    marketAppliesImage.setVisibility(View.GONE);


                    ckType2 = "2";
                    returnNuberList(data);
                    Type = "up";

                    //小到大
                } else {

                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    //图标隐藏
                    marketNameImage.setVisibility(View.GONE);
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.VISIBLE);
                    marketFloatingImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_buttom));
                    marketAppliesImage.setVisibility(View.GONE);
                    ckType2 = "1";
                    returnNuberList1(data);
                    Type = "down";
                }

                break;
            case R.id.market_applies:
                isType = "涨跌幅";
                //涨
                if (ckType4.equals("1")) {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    //图标隐藏
                    marketNameImage.setVisibility(View.GONE);
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.VISIBLE);
                    marketAppliesImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_top));
                    ckType4 = "2";
                    returnUPList(data);
                    Type = "down";
                    //跌
                } else {
                    //字体color
                    marketBiName.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiLatestPrice.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiFloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
                    marketBiApplies.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    //图标隐藏
                    marketNameImage.setVisibility(View.GONE);
                    marketLatestPriceImage.setVisibility(View.GONE);
                    marketFloatingImage.setVisibility(View.GONE);
                    marketAppliesImage.setVisibility(View.VISIBLE);
                    marketAppliesImage.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.market_buttom));
                    ckType4 = "1";
                    returnDownList(data);
                    Type = "up";
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "futurewebsocket/ALL_ALL_INDEX_PRICE").subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //首页list集合
        getDealPairList();


        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }

    @Override
    public void onRefresh() {
        //首页list集合
        getDealPairList();


        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.PARAM_PAIR, data.get(position).getDelKey());
        if (getActivity() instanceof MainActivity) {
            Activity activity = (MainActivity) getActivity();
            bundle.getInt(Keys.TRANS_SELECT,0);
            ((MainContract.View) activity).selectTab(2, bundle);
        }
    }
}
