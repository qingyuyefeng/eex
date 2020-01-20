package com.eex.mine.activity;

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
import com.eex.mine.adpater.DetileAdapter;
import com.eex.mine.bean.Detllett;

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
 * @Package: com.overthrow.mine.activity
 * @ClassName: DetileActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 10:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 10:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 返佣详情
 */
public class DetileActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
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

    private ArrayList<Detllett> list = new ArrayList<>();

    private DetileAdapter adapter;

    private String id = null;
    /**
     * 如果为空,则是第一级查询详情,不为空就为第二级查看详情
     */
    private String Type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detile;
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("返佣详情");
        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().getStringExtra("Type") != null) {
            Type = getIntent().getStringExtra("Type");
        }

        net();

    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .viewcharges(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {
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

        adapter = new DetileAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(this));

    }

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        net();
    }
}
