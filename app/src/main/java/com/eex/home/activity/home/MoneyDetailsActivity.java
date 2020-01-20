package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.MoneyDetailsAdapter;

import com.eex.home.bean.JiecDeltaleMoneyData;

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
 * @ClassName: MoneyDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 理财明细
 */
public class MoneyDetailsActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.btn_one)
    Button btnOne;
    /**
     *
     */
    @BindView(R.id.btn_two)
    Button btnTwo;
    /**
     *
     */
    @BindView(R.id.tx_downtime)
    TextView txDowntime;
    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private String id;

    private CountDownTimer data;
    private long day;
    private long hour;
    private long minute;
    private long second;


    private MoneyDetailsAdapter adapter;
    private ArrayList<JiecDeltaleMoneyData> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_details;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.stroedetails));


        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }

        //解仓列表详情
        findLockMoneyDetail();

        //根据ID查询解仓信息
        findLockingMoneyById();

    }


    /**
     * 解仓列表详情
     */
    @SuppressLint("CheckResult")
    private void findLockMoneyDetail() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lockRecordId", id);
        hashMap.put("startTime", btnOne.getText().toString().trim());
        hashMap.put("endTime", btnTwo.getText().toString().trim());

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .findLockMoneyDetail(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    dialog.dismiss();

                    if (pageData.getData()!= null) {

                        list.clear();
                        list.addAll(pageData.getData().getResultList());

                        adapter.notifyDataSetChanged();

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    dialog.dismiss();

                });
    }


    @Override
    protected void initUiAndListener() {


        adapter = new MoneyDetailsAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
    }

    /**
     * 根据ID查询解仓信息
     */
    @SuppressLint("CheckResult")
    private void findLockingMoneyById() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiFactory.getInstance()
                .findLockingMoneyById(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getData() != null) {

                        long time = pageData.getData().getLockEndTimeStamp();
                        final long NowTime = time - getCurTimeLong();
                        if (pageData.getData().getState().equals("3")) {
                            txDowntime.setText(0 + "天" + 0 + "时" + 0 + "分" + 0 + "秒");
                        } else {
                            if (NowTime <= 0) {
                                txDowntime.setText(0 + "天" + 0 + "时" + 0 + "分" + 0 + "秒");
                            } else {
                                TimeDown(NowTime);
                            }
                        }

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    private void TimeDown(long time) {

        data = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //天
                day = millisUntilFinished / (1000 * 60 * 60 * 24);
                //时
                hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                //分
                minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                //秒
                second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                try {
                    txDowntime.setText(day + "天" + hour + "时" + minute + "分" + second + "秒");

                } catch (Exception e) {

                }
            }

            @Override
            public void onFinish() {

            }
        };
        data.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.btn_one:
                //时间选择
                getTime(1);
                break;
            case R.id.btn_two:
                //时间选择
                getTime(2);
                break;
            default:
                break;
        }
    }

    /**
     * 时间选择
     *
     * @param i
     */
    private void getTime(int i) {
        TimeSelector timeSelector = new TimeSelector(MoneyDetailsActivity.this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                if (i == 1) {
                    btnOne.setText(time);
                    findLockMoneyDetail();
                } else if (i == 2) {
                    btnTwo.setText(time);
                    findLockMoneyDetail();
                }

            }
        }, "2010-10-01 00:00", "2050-12-31 23:59:59");
        //设置不循环,true循环
        timeSelector.setIsLoop(false);
        //只显示 年月日
        timeSelector.setMode(TimeSelector.MODE.YMD);
        timeSelector.show();
    }


    /**
     * 获取系统时间戳
     *
     * @return
     */
    public long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }


}
