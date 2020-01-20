package com.eex.notice.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.notice.adpater.IndustryAdapter;
import com.eex.notice.bean.IndustryBean;

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
 * @Package: com.overthrow.notice.activity
 * @ClassName: IndustryActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/5 14:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/5 14:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class IndustryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

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


    /**
     * 自定义字段
     */
    private String id;
    private String name;

    private IndustryAdapter adapter;
    private ArrayList<IndustryBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("id") != null) {

            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
        }
        comtitlebar.setTitle(name);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_industry;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("categoryId", id + "");
        hashMap.put("pageSize", 25 + "");
        hashMap.put("pageNo", page + "");
        hashMap.put("website", "cn");

        ApiFactory.getInstance()
                .listpage(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    swipeRefresh.setRefreshing(false);
                    if (data.getCode() == 200) {

                        list.clear();
                        list.addAll(data.getData().getListData());
                        adapter.notifyDataSetChanged();

                    } else {
                        t(data.getMsg());
                    }


                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {

        adapter = new IndustryAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

    }

    @OnClick({R.id.comtitlebar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        net();
    }
}
