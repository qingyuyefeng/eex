package com.eex.home.fragment.MeC2COrderList;

import android.annotation.SuppressLint;
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
import com.eex.home.activity.home.WaitPayActivity;
import com.eex.home.adapter.C2CMeFramentAdapter;
import com.eex.home.bean.OrderDetail;

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
 * @Package: com.overthrow.home.fragment.MeC2COrderList
 * @ClassName: C2CFramentCancelled
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 9:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 9:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 已取消
 */
public class C2CFramentCancelled extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

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
    Unbinder unbinder;


    private int pageNo = 1;
    private int pageSize = 15;

    private ArrayList<OrderDetail> list = new ArrayList<>();
    private ArrayList<OrderDetail> list1 = new ArrayList<>();
    private C2CMeFramentAdapter adapter;


    @Override
    protected void lazyLoad() {
        //c2c查询用户订单列表
        net();
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

//c2c查询用户订单列表
        net();
    }

    /**
     * c2c查询用户订单列表
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNo", pageNo + "");
        hashMap.put("pageSize", pageSize + "");
        hashMap.put("state", "3");

        ApiFactory.getInstance()
                .getOrderDetailList(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (data.getData() != null) {
                        list = new ArrayList<OrderDetail>();
                        if (data.getData().getPageNo() > 1) {

                            list1 = new ArrayList<OrderDetail>();
                            list1.clear();
                            list1.addAll(data.getData().getResultList());
                            adapter.addData(list1);
                        }else {
                            if (data.getData().getResultList()!=null){
                                list.addAll(data.getData().getResultList());
                            }
                            setView(list);
                        }


                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    private void setView(ArrayList<OrderDetail> list) {
        adapter = new C2CMeFramentAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                intent.putExtra("OrderId", list.get(position).getId());
                intent.setClass(getActivity(), WaitPayActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initUiAndListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new C2CMeFramentAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(context));

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter.setOnLoadMoreListener(this, recyclerView);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c2c_buysell_list;
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

        //c2c查询用户订单列表
        net();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {
        //c2c查询用户订单列表
        net();
    }

    @Override
    public void onResume() {
        super.onResume();

        //c2c查询用户订单列表
        net();
    }
}
