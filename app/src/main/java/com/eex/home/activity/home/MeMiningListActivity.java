package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.ADate;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.MeMiningAdapter;
import com.eex.home.bean.getTotle;

import org.feezu.liuli.timeselector.TimeSelector;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: MeMiningListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 20:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 20:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 我的挖矿
 */
public class MeMiningListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


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

    private String patten1 = "yyyy-MM-dd";
    private String time = "";

    private ArrayList<getTotle> list = new ArrayList<>();
    private MeMiningAdapter adapter;
    private String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_mining_list;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("我的挖矿");

        //获取我的挖矿列表
        getTotleOrePoolRecordList("");

        comtitlebar.setImageView(R.drawable.cq_recharge_zd);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeSelector timeSelector = new TimeSelector(MeMiningListActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        //获取我的挖矿列表
                        getTotleOrePoolRecordList(time);
                    }
                }, "2018-01-01 00:00", ADate.getDateToString(pattern));
                //设置不循环,true循环
                timeSelector.setIsLoop(false);
                //只显示 年月日
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
            }


        });

    }

    /**
     * 获取我的挖矿列表
     *
     * @param data
     */
    @SuppressLint("CheckResult")
    private void getTotleOrePoolRecordList(String data) {
        HashMap<String, String> hashMap = new HashMap<>();

        if (!data.equals("")) {
            time = data;
            //开始时间
            hashMap.put("begintime", data + ":00");
            //结束时间
            hashMap.put("endtime", data.replace("00:00", "23:59:59"));
        } else {
            hashMap.put("startTime", ADate.getDateToString(patten1) + " 00:00:00");
            hashMap.put("endTime", ADate.getDateToString(patten1) + " 23:59:59");

        }
        hashMap.put("pageSize", "100");
        hashMap.put("pageNo", "1");

        ApiFactory.getInstance()
                .trademining(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {
                        ArrayList<getTotle> getTotles = pageData.getData().getResultList();

                        list.clear();
                        list.addAll(getTotles);
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

        adapter = new MeMiningAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (!time.equals("")) {
                    intent.putExtra("endTime", time.replace("00:00", "23:59:59"));
                    intent.putExtra("startTime", time + ":00");
                } else {
                    ADate.getDateToString(patten1);
                    intent.putExtra("endTime", ADate.getDateToString(patten1) + " 23:59:59");
                    intent.putExtra("startTime", ADate.getDateToString(patten1) + " 00:00:00");
                }
                intent.putExtra("coin", list.get(position).getDealpair());
                intent.setClass(MeMiningListActivity.this, MeMiningListDetailsActivity.class);
                startActivity(intent);

            }
        });
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
        //获取我的挖矿列表
        getTotleOrePoolRecordList("");
    }
}
