package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * @ClassName: RechargeMoneyListDataActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 10:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 10:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 充币记录
 */
public class RechargeMoneyListDataActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.img_biname)
    ImageView imgBiname;
    /**
     *
     */
    @BindView(R.id.txType)
    TextView txType;
    /**
     *
     */
    @BindView(R.id.LLType)
    LinearLayout LLType;
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


    private String namel;
    private String type = "dange";

    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 12;

    private RechargeMoneyListAdapter adapter;
    private ArrayList<CBlistData> list = new ArrayList<CBlistData>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_money_list_data;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("充币记录");
        comtitlebar.setRightText("全部");
        if (getIntent().getStringExtra("coin") != null) {
            namel = getIntent().getStringExtra("coin");
        }

        //
        net();

        comtitlebar.setRightText("全部", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "quanbu";
                p = 1;
                net();
            }
        });
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNo", p.toString());
        hashMap.put("pageSize", size.toString());
        hashMap.put("operateType", "1");
        if (type.equals("dange")) {
            hashMap.put("coinCode", namel);
        }

        ApiFactory.getInstance()
                .coinaddrpage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData().getResultList() != null) {

                        list.clear();
                        list.addAll(data.getData().getResultList());

                        adapter.notifyDataSetChanged();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {
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

    @OnClick({R.id.comtitlebar, R.id.img_biname, R.id.txType, R.id.LLType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.img_biname:
                break;
            case R.id.txType:
                break;
            case R.id.LLType:
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
