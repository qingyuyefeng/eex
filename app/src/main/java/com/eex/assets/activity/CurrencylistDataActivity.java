package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.eex.R;
import com.eex.assets.adpater.RechargeMoneyListAdapter;
import com.eex.assets.bean.CBlistData;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;

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
 * @Package: com.overthrow.assets.activity
 * @ClassName: CurrencylistDataActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 11:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 11:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 提币记录
 */
public class CurrencylistDataActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "CurrencylistDataActivit";
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

    private ArrayList<CBlistData> list = new ArrayList<>();
    private RechargeMoneyListAdapter adapter;

    private String name;
    private String type = "dange";
    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 12;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_currencylist_data;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle(getActivity().getResources().getString(R.string.tibijilu));
        if (getIntent().getStringExtra("coin") != null) {
            name = getIntent().getStringExtra("coin");
        }

        net();
        Log.e(TAG, "进来: " + kv.decodeString("tokenId") );

        comtitlebar.setRightText("全部");
        comtitlebar.setRightText("全部", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "quanbu";
                net();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("pageNo", p.toString());
        requestParam.put("pageSize", size.toString());
        requestParam.put("operateType", "2");

        if (type.equals("dange")) {
            requestParam.put("coinCode", name);
        }

        ApiFactory.getInstance()
                .coinaddrpage(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (data.getData() != null) {
                        list.clear();
                        list.addAll(data.getData().getResultList());

                        adapter.notifyDataSetChanged();
                    }
                    else {
                        t(data.getMsg());
                    }

                }, throwable -> {
                    Log.e(TAG, "net: " + throwable.toString() );
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }



    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


        adapter = new RechargeMoneyListAdapter(list);
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
        net();
    }
}
