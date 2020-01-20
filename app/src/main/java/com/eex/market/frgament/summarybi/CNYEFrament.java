package com.eex.market.frgament.summarybi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.market.adpater.BilistAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
 * @Package: com.overthrow.market.frgament.summarybi
 * @ClassName: CNYEFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/27 15:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/27 15:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * CNYE
 */
public class CNYEFrament extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private ArrayList<MainList> lists = new ArrayList<>();
    private String dealPair = "";
    private ArrayList<MainData> data = new ArrayList<>();
    private BilistAdapter adapter;


    @Override
    protected void lazyLoad() {

        //首页list集合
        net();
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {
        //首页list集合
        net();
    }

    /**
     * 首页list集合
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pricingCoin", "CNYE");
        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    try {
                        if (data.isStauts() == true) {
                            dealPair = "";
                            lists.clear();
                            lists.addAll(data.getData());
                            for (int i = 0; i < lists.size(); i++) {
                                dealPair += lists.get(i).getDealPair() + ",";
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

    /**
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
                        data.addAll(arrayListData.getData());
                        adapter.notifyDataSetChanged();

                    } else {
                        t(arrayListData.getMsg());

                    }


                }, throwable -> {


                });
    }

    @Override
    protected void initUiAndListener() {

        swipeRefresh.setOnRefreshListener(this);

        adapter = new BilistAdapter(R.layout.item_summary, data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = data.get(position).getDelKey() + "/" + lists.get(position).getPricingCoin();
                String data1 = data.get(position).getDelKey();
                int i = data.get(position).getDelKey().indexOf("_");
                //交易币.get(position).getCoinCode()
                String JYBi = data.get(position).getDelKey().substring(0, i);
                //定价币
                String DjBi = data.get(position).getDelKey().substring(data.get(position).getDelKey().indexOf("_")+1);


                intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("data", data1);
                intent.putExtra("JYBi", JYBi);
                intent.putExtra("DJBi", DjBi);
                getActivity().setResult(2000,intent);
                getActivity(). finish();

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_usdt_summary;
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
    public void onRefresh() {

        //首页list集合
        net();
    }



}
