package com.eex.market.frgament.KLine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.view.EmptyView;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.market.adpater.DealNewAdapter;
import com.eex.market.bean.CnySize;
import com.eex.market.bean.PurchaseDatalIst;
import com.eex.market.bean.PurchaseDta;
import com.eex.market.weight.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
 * @ClassName: NewDealFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 最近成交
 */
public class NewDealFrament extends BaseFragment {


    public static String BinameData;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private ArrayList<PurchaseDatalIst> datalIsts = new ArrayList<>();
    private List<CnySize> cnySizes = new ArrayList<>();

    private DealNewAdapter adapter;

    public String Type = "YES";

    @Override
    protected void lazyLoad() {

        //K线数据
        net();
        //K线数据
        WebSocket();
        recyclerView.setFocusable(false);
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {

        //K线数据
        net();
        //K线数据
        WebSocket();
        recyclerView.setFocusable(false);
    }

    /**
     * K线数据
     */
    private void WebSocket() {

        RxWebSocket.get(WPConfig.INSTANCE.getBaseUrl() + "websocket/" + BinameData)
                .compose(RxSchedulers.io_main())
                .subscribe(new WebSocketSubscriber2<Root<PurchaseDta>>() {
                    @Override
                    protected void onMessage(Root<PurchaseDta> purchaseDtaRoot) {

                        try {
                            if (purchaseDtaRoot != null && purchaseDtaRoot.getData().getListOrder().size() != 0) {
                                cnySizes.clear();
                                cnySizes.addAll(purchaseDtaRoot.getData().getListOrder());
                                //更新最新成交记录
                                setNewMoney(cnySizes);
                            }
                        } catch (Exception e) {

                        }
                    }


                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连1");

                    }

                });
    }

    /**
     * 更新最新成交记录
     *
     * @param cnySizes
     */
    private void setNewMoney(List<CnySize> cnySizes) {

        if (cnySizes != null && !cnySizes.isEmpty()) {
            for (CnySize cnySize : cnySizes) {
                PurchaseDatalIst purchaseDatalIst = new PurchaseDatalIst();
                purchaseDatalIst.setDealTime(cnySize.getDealDate());
                purchaseDatalIst.setDealNum(cnySize.getDealNum());
                purchaseDatalIst.setDealPrice(cnySize.getDealAmount());
                try {
                    datalIsts.add(0, purchaseDatalIst);
                } catch (Exception e) {

                }
            }
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (datalIsts != null && datalIsts.size() != 0) {
                    adapter = new DealNewAdapter(datalIsts);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * K线数据
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dealPair", BinameData);
        hashMap.put("size", "10");
        ApiFactory.getInstance()
                .TradingHall(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data != null) {
                        datalIsts.clear();
                        datalIsts.addAll(data.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }

    @Override
    protected void initUiAndListener() {

        adapter = new DealNewAdapter(datalIsts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_deal;
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
        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getBaseUrl() + "websocket/" + BinameData).subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        net();
        if (Type.equals("NO")) {
            WebSocket();
        }
    }
}
