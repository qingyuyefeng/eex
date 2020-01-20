package com.eex.market.frgament.donation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.market.activity.DonationActivity;
import com.eex.market.adpater.TransactionSummaryAdapter;
import com.eex.market.bean.DealSummaryPage;

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
 * @Package: com.overthrow.market.frgament.donation
 * @ClassName: TransactionSummaryFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/10/14 15:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/14 15:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 创新版成交汇总
 */
public class TransactionSummaryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    /**
     *
     */
    Unbinder unbinder;

    private ArrayList<DealSummaryPage> list = new ArrayList<>();
    private TransactionSummaryAdapter adapter;

    @Override
    protected void lazyLoad() {
        net();

    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();
    }


    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", DonationActivity.JYB + "");
        hashMap.put("fixPriceCoinCode", DonationActivity.DJB + "");

        ApiFactory.getInstance()
                .getDelegationDealSummaryPage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getCode() == 200) {
                        list.clear();
                        list.addAll(data.getData().getResultList());
                        adapter.notifyDataSetChanged();
                    } else {

                    }

                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                });

    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


        adapter = new TransactionSummaryAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_summary;
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
        net();
    }
}
