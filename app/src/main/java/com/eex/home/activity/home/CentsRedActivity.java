package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.ADate;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.adapter.CentsRedAdapter;
import com.eex.home.bean.CentsRed;

import org.feezu.liuli.timeselector.TimeSelector;

import java.math.BigDecimal;
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
 * @ClassName: CentsRedActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 20:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 20:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 我的分红
 */
public class CentsRedActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

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
     * 持有EBT数量高于1000才能参与分红
     */
    @BindView(R.id.tx_ebtNuber)
    TextView txEbtNuber;
    /**
     * 下拉刷新
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    /**
     * EBT最低限制额度
     */
    private String ebtMinHoldNum = "";
    /**
     * 时间格式
     */
    private String pattern = "yyyy-MM-dd HH:mm:ss";
    private String patten1 = "yyyy-MM-dd";

    private ArrayList<CentsRed> list = new ArrayList<>();
    private CentsRedAdapter adapter;
    private String tda = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_cents_red;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("我的分红");


        //获取我的分紅列表
        getwelfarerecord("");
        //获取昨日累计分红
        lastwelfare();
        comtitlebar.setImageView(R.drawable.rili);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeSelector timeSelector = new TimeSelector(CentsRedActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tda = time;
                        getMyMoneyData(time);
                    }


                }, "2018-01-01 00:00", ADate.getDateToString(pattern));

                //设置不循环,true循环
                timeSelector.setIsLoop(false);
                // 显示 年月日时分（默认）
//        timeSelector.setMode(TimeSelector.MODE.YMDHM);
                //只显示 年月日
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
            }

        });

    }

    /**
     * 获取我的分紅列表
     */
    @SuppressLint("CheckResult")
    private void getwelfarerecord(final String data) {

        HashMap<String, String> hashMap = new HashMap<>();

        if (!data.equals("")) {
            //开始时间
            hashMap.put("currentDate", data.replace("00:00", "").trim());
        } else {
            hashMap.put("currentDate", ADate.getDateToString(patten1));

        }
        hashMap.put("pageSize", "100");
        hashMap.put("pageNo", "1");
        ApiFactory.getInstance()
                .getwelfarerecord(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getCode() == 10002) {

                        t(pageData.getMsg());
                        intent = new Intent();
                        intent.putExtra("type", "1");
                        intent.setClass(CentsRedActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return;

                    } else if (pageData != null) {
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


    /**
     * 获取昨日累计分红
     */
    @SuppressLint("CheckResult")
    private void lastwelfare() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .lastwelfare(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {
                        ebtMinHoldNum = pageData.getData().getEbtMinHoldNum();
                        txEbtNuber.setText("持有EBT数量高于" + ebtMinHoldNum + "才能参与分红");

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    /**
     * @param data
     */
    @SuppressLint("CheckResult")
    private void getMyMoneyData(final String data) {
        HashMap<String, String> hashMap = new HashMap<>();

        if (!data.equals("")) {
            //开始时间
            hashMap.put("currentDate", data.replace("00:00", "").trim());
        } else {
            hashMap.put("currentDate", ADate.getDateToString(patten1));

        }
        hashMap.put("pageSize", "100");
        hashMap.put("pageNo", "1");
        ApiFactory.getInstance()
                .getwelfarerecord(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getCode() == 10002) {
                        t(pageData.getMsg());
                        intent = new Intent();
                        intent.putExtra("type", "1");
                        intent.setClass(CentsRedActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return;

                    } else if (pageData != null) {
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

        adapter = new CentsRedAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                intent = new Intent();
                intent.putExtra("coinCode", list.get(position).getCoinCode());
                if (list.get(position).getCoinWelfareNum().toString().equals("0E-10")) {
                    intent.putExtra("coinWelfareNum", "0");
                } else {
                    intent.putExtra("coinWelfareNum", list.get(position).getCoinWelfareNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                }
                intent.putExtra("coinWelfareLimit", list.get(position).getCoinWelfareLimit().setScale(10, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                if (list.get(position).getCoinTotalWelfare().toString().equals("0E-10")) {
                    intent.putExtra("coinTotalWelfare", "0");
                } else {
                    intent.putExtra("coinTotalWelfare", list.get(position).getCoinTotalWelfare().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                }
                intent.putExtra("lockWelfareNum", list.get(position).getLockWelfareNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("coinCodeLimit", list.get(position).getCoinCodeLimit().setScale(10, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("lockTotalWelfare", list.get(position).getLockTotalWelfare().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("welfareTotal", list.get(position).getWelfareTotal().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("createTime", list.get(position).getCreateTime() + "");
                intent.setClass(getApplicationContext(), CentsRedDetailsActivity.class);
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

    @OnClick({R.id.comtitlebar, R.id.tx_ebtNuber})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_ebtNuber:
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

        //获取我的分紅列表
        getwelfarerecord("");
        //获取昨日累计分红
        lastwelfare();
    }
}
