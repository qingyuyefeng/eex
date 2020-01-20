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
import com.eex.market.adpater.KlineAdapter;
import com.eex.market.adpater.SellAdapter;
import com.eex.market.bean.Buy;
import com.eex.market.bean.PurchaseDta;
import com.eex.market.bean.Sell;
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
 * @ClassName: SellFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 14:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 14:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 委托订单
 */
public class SellFragment extends BaseFragment {

    private static final String TAG = "SellFragment";

    /**
     *
     */
    @BindView(R.id.listBuy)
    RecyclerView recyclerViewBuy;
    /**
     *
     */
    @BindView(R.id.listSell)
    RecyclerView recyclerViewSell;
    /**
     *
     */
    Unbinder unbinder;

    public static String BinameData;

    private SellAdapter sellAdapter;
    private List<Sell> sellList = new ArrayList<>();
    private KlineAdapter klineAdapter;
    private List<Buy> buyList = new ArrayList<>();

    @Override
    protected void lazyLoad() {

        recyclerViewBuy.setFocusable(false);
        recyclerViewSell.setFocusable(false);
        //交易大厅List
        net();
        //
        WebSocket();
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {



        recyclerViewBuy.setFocusable(false);
        recyclerViewSell.setFocusable(false);
        //交易大厅List
        net();
        //
        WebSocket();
    }


    /**
     * 交易大厅List
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("delkey", BinameData);
        ApiFactory.getInstance()
                .getTickMaket(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == true) {
                        if (data.getData().getSell() != null) {
                            sellList.clear();
                            sellList.addAll(data.getData().getSell());


                            sellAdapter.notifyDataSetChanged();

                        }

                        if (data.getData().getBuy() != null) {
                            buyList.clear();
                            buyList.addAll(data.getData().getBuy());



                            klineAdapter.notifyDataSetChanged();
                        }

                    } else {
                        t(data.getMsg());
                    }


                }, throwable -> {

                });


    }




    private void WebSocket() {

        RxWebSocket.get(WPConfig.INSTANCE.getBaseUrl() + "websocket/" + BinameData)
                .compose(RxSchedulers.io_main())
                .subscribe(new WebSocketSubscriber2<Root<PurchaseDta>>() {
                    @Override
                    protected void onMessage(Root<PurchaseDta> purchaseDtaRoot) {
                        if (purchaseDtaRoot != null) {

                            if (purchaseDtaRoot.getData().getSell() != null) {

                                sellList.clear();
                                sellList.addAll(purchaseDtaRoot.getData().getSell());



                                sellAdapter.notifyDataSetChanged();
                            }


                            if (purchaseDtaRoot.getData().getBuy() != null) {
                                buyList.clear();
                                buyList.addAll(purchaseDtaRoot.getData().getBuy());



                                klineAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Log.e("TAG", "onMessage: " + purchaseDtaRoot.getMsg());
                        }
                    }


                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连1");

                    }

                });
    }


    @Override
    protected void initUiAndListener() {

        sellAdapter = new SellAdapter(sellList);
        recyclerViewSell.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSell.setAdapter(sellAdapter);
        sellAdapter.setEmptyView(new EmptyView(getActivity()));


        klineAdapter = new KlineAdapter(buyList);
        recyclerViewBuy.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBuy.setAdapter(klineAdapter);
        klineAdapter.setEmptyView(new EmptyView(getActivity()));



    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entrust_sell;
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
}
