package com.eex.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.view.EmptyView;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.home.adapter.ReclierViewAdapter;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.home.bean.UstilMainData;
import com.eex.market.weight.GlobalParms;
import com.eex.market.activity.DonationActivity;

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
 * @Package: com.overthrow.home.fragment
 * @ClassName: MainUPFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 9:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 9:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 24h涨幅榜
 */
public class MainUPFrament extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {


    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.tx_gengduo)
    TextView txGengduo;
    /**
     *
     */
    @BindView(R.id.linear_gengduo)
    LinearLayout linearGengduo;

    Unbinder unbinder;

    private ArrayList<MainList> list = new ArrayList<>();
    private ArrayList<MainData> data = new ArrayList<>();

    private ReclierViewAdapter adapter;

    private String dealPair = "";
    /**
     * RecyclerView滑动监听开关
     */
    private boolean reckviewTouth = true;

    private String MoneyType = "NO";

    private MainData mainData;

    private ArrayList<MainData> datalist = new ArrayList<MainData>();
    private FragmentTransaction fragmentTransaction;


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
    public void onDestroy() {
        super.onDestroy();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/ALL_ALL").subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }

    }

    @Override
    protected void lazyLoad() {

        //首页list集合
        getDealPairList();
        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }


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
     */
    @SuppressLint("CheckResult")
    private void getDealPairList() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == true) {
                        dealPair = "";
                        list.clear();
                        list.addAll(data.getData());
                        for (int i = 0; i < list.size(); i++) {
                                dealPair += list.get(i).getDealPair() + ",";
                        }
                        getIndexMaketList(dealPair);
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

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
                    if (arrayListData.getCode() == 200) {


                        data.clear();

                        for (int i = 0; i < arrayListData.getData().size(); i++) {
                            String text = arrayListData.getData().get(i).getDelKey();
                            String text3 = text.substring(text.indexOf("_"));
                            if (text3.equals("_USDT")) {
                                if (arrayListData.getData().get(i).getNewPrice() != null && arrayListData.getData().get(i).getLastPrice() != null) {
                                    BigDecimal money = arrayListData.getData().get(i).getNewPrice().divide(arrayListData.getData().get(i).getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                                    BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                                    if (newData.compareTo(BigDecimal.ZERO) == 1) {
                                        data.add(arrayListData.getData().get(i));
                                    }
                                }
                            }

                        }

                        Collections.sort(data, new Comparator<MainData>() {
                            @Override
                            public int compare(MainData o1, MainData o2) {
                                BigDecimal money1 = o1.getNewPrice().divide(o1.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                                BigDecimal newData1 = money1.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

                                BigDecimal money2 = o2.getNewPrice().divide(o2.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                                BigDecimal newData2 = money2.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                                try {
                                    return newData2.compareTo(newData1) > 0 ? 1 : -1;

                                } catch (Exception e) {
                                    return 0;
                                }

                            }
                        });

                        if (data.size() > 10) {
                            datalist.addAll(data.subList(0, 10));
                        } else {
                            datalist.addAll(data);
                        }


                        adapter.notifyDataSetChanged();
                        UstilMainData.saveMainData(mainData);


                    } else {
                        t(arrayListData.getMsg());

                    }


                }, throwable -> {


                });
    }

    @Override
    protected void initUiAndListener() {

        //    //禁止recyclerView 滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };


        adapter = new ReclierViewAdapter(datalist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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

                        intent.setClass(getContext(), DonationActivity.class);
                        startActivity(intent);
                        reckviewTouth = true;
                        break;
                    default:
                        break;
                }
            }
        });
        //
        webSoket();

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
                                        //data.get(i).setDealDate(pageData.getDealDate());
                                        data.get(i).setDelKey(pageData.getDelKey());
                                        data.get(i).setFooPrice(pageData.getFooPrice());
                                        data.get(i).setHigePrice(pageData.getHigePrice());
                                        data.get(i).setNewPrice(pageData.getNewPrice());
                                        data.get(i).setUsdtCny(pageData.getUsdtCny());
                                        data.get(i).setLastPrice(pageData.getLastPrice());

                                    }

                                }
                                if (reckviewTouth) {
                                    adapter.setNewData(data);
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_performer;
    }

    @OnClick({R.id.tx_gengduo, R.id.linear_gengduo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tx_gengduo:

                GlobalParms.SchangeFragment.changge(1);

                break;

            case R.id.linear_gengduo:


                GlobalParms.SchangeFragment.changge(1);

                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {

        //首页list集合
        getDealPairList();
        //
        if (MoneyType.equals("YES")) {
            webSoket();
        }
    }
}
