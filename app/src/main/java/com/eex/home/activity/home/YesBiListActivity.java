package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.YesAdapter;
import com.eex.home.bean.YesListData;

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
 * @ClassName: YesBiListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 理财币种
 */
public class YesBiListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     * 顶部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * recyclerView
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private YesAdapter adapter;
    private ArrayList<YesListData> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yes_bi_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle(getActivity().getResources().getString(R.string.moneysuolIST));

        //锁仓查询理财币种
        getLockMoneyCoin();
    }

    /**
     * 锁仓查询理财币种
     */
    @SuppressLint("CheckResult")
    private void getLockMoneyCoin() {
        HashMap<String, String> hashMap = new HashMap<>();

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .getLockMoneyCoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    dialog.dismiss();
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {

                        list.clear();
                        list.addAll(pageData.getData());
                        adapter.notifyDataSetChanged();


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    dialog.dismiss();
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new YesAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                String name = list.get(position).getCoinCode();
                intent.putExtra("name", name);
                setResult(2000, intent);
                finish();
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
        //锁仓查询理财币种
        getLockMoneyCoin();
    }
}
