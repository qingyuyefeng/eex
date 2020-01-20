package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.assets.adpater.RechargeLsitDadaAdapter;
import com.eex.assets.bean.ResultListdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * @ClassName: RechargeListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 15:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 15:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RechargeListActivity extends BaseActivity implements  BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.recyclerView)
    ListView recyclerView;



    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 12;
    private ArrayList<ResultListdata> listdata = new ArrayList<>();
    private RechargeLsitDadaAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.congzjl));
        net();
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNo", p.toString());
        hashMap.put("dealType", "0");
        hashMap.put("pageSize", size.toString());
        ApiFactory.getInstance()
                .extractpage(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageListData -> {


                    if (pageListData.getCode() == 10002 || pageListData.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RechargeListActivity.this, "登陆超时请重新登陆", Toast.LENGTH_SHORT).show();
                    }
                    if (pageListData.getData() != null) {
                        if (p == 1) {
                            listdata = new ArrayList<>();
                        }
                        List<ResultListdata> memactivity_uncompleted_list2 = pageListData.getData().getResultList();
                        if (memactivity_uncompleted_list2 != null) {
                            listdata.addAll(memactivity_uncompleted_list2);
                        }

                        setlistView(listdata);
                    }
                }, throwable -> {


                });
    }

    private void setlistView(ArrayList<ResultListdata> listdata) {
        recyclerView.setFocusable(false);
        adapter = new RechargeLsitDadaAdapter(RechargeListActivity.this, listdata);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // 只有固定ListView的高度，让其不自动调整调整，就不会与ScrollView冲突,不然就会出现只有listview能上下滑动而scrollview中的其他linerlayout不动的情况

        p = p.intValue() + 1;
    }

    @Override
    protected void initUiAndListener() {

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {
        net();
    }
}
