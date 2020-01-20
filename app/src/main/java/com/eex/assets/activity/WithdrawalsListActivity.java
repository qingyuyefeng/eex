package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.eex.R;
import com.eex.assets.adpater.WithdrawalsListAdapter;
import com.eex.assets.bean.ResultListdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

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
 * @ClassName: WithdrawalsListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 11:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 11:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 提现记录
 */
public class WithdrawalsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.listView)
    ListView listView;
    /**
     *
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    private ArrayList<ResultListdata> listdata = new ArrayList<>();
    private WithdrawalsListAdapter adapter;
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
        return R.layout.activity_withdrawals_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("提现记录");

        net();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hasMap = new HashMap<>();
        hasMap.put("pageNo", p.toString());
        hasMap.put("pageSize", size.toString());
        hasMap.put("dealType", "1");

        ApiFactory.getInstance()
                .extractpage(kv.decodeString("tokenId"),hasMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(WithdrawalsListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(WithdrawalsListActivity.this, "登陆超时请重新登陆", Toast.LENGTH_SHORT).show();
                    }

                    if (data.getData() != null) {
                        if (p == 1) {
                            listdata = new ArrayList<>();
                        }
                        ArrayList<ResultListdata> list = data.getData().getResultList();
                        if (list != null) {
                            listdata.addAll(list);
                        }

                        setlistView(listdata);
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    private void setlistView(ArrayList<ResultListdata> listdata) {


        listView.setFocusable(false);
        adapter = new WithdrawalsListAdapter(WithdrawalsListActivity.this, listdata);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        p = p.intValue() + 1;
    }

    @Override
    protected void initUiAndListener() {
        swipeRefresh.setOnRefreshListener(this);
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
