package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.MeMiningListDetailsAdapter;
import com.eex.home.bean.getOrePool;

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
 * @Package: com.overthrow.home.activity
 * @ClassName: MeMiningListDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 9:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 9:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 挖矿详情
 */
public class MeMiningListDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * recyclerView
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     * 下拉刷新
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    private String coin;
    private String startTime;
    private String endTime;


    private MeMiningListDetailsAdapter adapter;

    private ArrayList<getOrePool> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_mining_list_details;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("挖矿详情");

        if (getIntent().getStringExtra("coin") != null) {
            coin = getIntent().getStringExtra("coin");
        }
        if (getIntent().getStringExtra("startTime") != null) {
            startTime = getIntent().getStringExtra("startTime");
        }
        if (getIntent().getStringExtra("endTime") != null) {
            endTime = getIntent().getStringExtra("endTime");
        }

        //挖矿详情
        getOrePoolRecordList();

    }

    /**
     * 挖矿详情
     */
    @SuppressLint("CheckResult")
    private void getOrePoolRecordList() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("dealpair", coin + "");
        hashMap.put("pageSize", "100");
        hashMap.put("begintime", startTime + "");
        hashMap.put("endtime", endTime + "");
        hashMap.put("pageNo", "1");

        ApiFactory.getInstance()
                .getOrePoolRecordList(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData != null) {
                        list.clear();
                        list.addAll(pageData.getData().getResultList());
                        adapter.notifyDataSetChanged();
                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new MeMiningListDetailsAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        //挖矿详情
        getOrePoolRecordList();
    }
}
