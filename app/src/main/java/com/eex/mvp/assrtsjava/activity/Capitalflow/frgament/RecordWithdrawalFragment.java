package com.eex.mvp.assrtsjava.activity.Capitalflow.frgament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.assets.bean.CBlistData;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.assrtsjava.activity.Capitalflow.adapter.CoinRecordAdapter;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity.Capitalflow.frgament
 * @ClassName: RecordWithdrawalFragment
 * @Description: 提币记录
 * @Author: hcj
 * @CreateDate: 2019/12/25 14:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 14:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class RecordWithdrawalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;

    private CoinRecordAdapter adapter;
    private ArrayList<CBlistData> list = new ArrayList<CBlistData>();


    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 12;

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNo", p.toString());
        hashMap.put("pageSize", size.toString());
        hashMap.put("operateType", "2");
        hashMap.put("coinCode", "quanbu");


        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .coinaddrpage(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (data.getData() != null) {
                        list.clear();
                        list.addAll(data.getData().getResultList());

                        adapter.notifyDataSetChanged();
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {
                    Log.e(TAG, "net: " + throwable.toString());
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    protected void initUiAndListener() {
        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new CoinRecordAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.re_fragment_coinrecord;
    }

    @Override
    protected void lazyLoad() {

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
        size += 10;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
