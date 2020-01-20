package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.home.bean.MiningInfo;
import com.eex.home.bean.WelfareCountAbout;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;



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
 * @ClassName: MiningDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 17:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 17:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 分红挖矿详情
 */
public class MiningDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 0USDT
     */
    @BindView(R.id.tx_time_red)
    TextView txTimeRed;
    /**
     * 24h待分红累计
     */
    @BindView(R.id.LL_fenRed)
    LinearLayout LLFenRed;
    /**
     * 24h待分红累计 数据
     */
    @BindView(R.id.time_dan)
    TextView timeDan;
    /**
     * 每日香港时间12:00更新分红 数据
     */
    @BindView(R.id.tx_upDay)
    TextView txUpDay;
    /**
     * EBT流通量 数据
     */
    @BindView(R.id.ebt_nuber)
    TextView ebtNuber;
    /**
     * EBT矿池量
     */
    @BindView(R.id.tx_MineralName)
    TextView txMineralName;
    /**
     * EBT矿池量 数据
     */
    @BindView(R.id.tx_MineralMoney)
    TextView txMineralMoney;
    /**
     * EBT当前算法
     */
    @BindView(R.id.CalculationName)
    TextView CalculationName;
    /**
     * EBT当前算法数据
     */
    @BindView(R.id.tx_CalculationMoney)
    TextView txCalculationMoney;
    /**
     * EBT当日产量
     */
    @BindView(R.id.tx_oneday_nuber)
    TextView txOnedayNuber;
    /**
     * EBT当日产量数据
     */
    @BindView(R.id.tx_tx_nuberMoney)
    TextView txTxNuberMoney;
    /**
     * 我的分红
     */
    @BindView(R.id.btn_Red)
    Button btnRed;
    /**
     * 我的挖矿
     */
    @BindView(R.id.Btn_mining)
    Button BtnMining;
    /**
     * 下拉刷新
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    /**
     * EBT最低限制额度
     */
    private String ebtMinHoldNum = "";

    private Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "miningsocket").subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }

        Disposable disposable2 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinwelfaresocket").subscribe();
        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mining_details;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle("分红挖矿详情");

        //矿池量
        //当前算法
        //当日产量
        getMiningInfo();
        //矿池量
        //当前算法
        //当日产量
        initWebsocket();
        //24h待分红累计约
        //24h单币分红约
        coinwelfare();
        //24h待分红累计约
        //24h单币分红约
        coinwelfareWebsocket();
        //获取昨日累计分红
        lastwelfare();

    }


    /**
     * 矿池量
     * 当前算法
     * 当日产量
     */
    @SuppressLint("CheckResult")
    private void getMiningInfo() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .getMiningInfo(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {
                        //当日产量
                        txOnedayNuber.setText(pageData.getData().getCoin() + getActivity().getResources().getString(R.string.Daily_output));
                        txTxNuberMoney.setText(pageData.getData().getDaymining().setScale(4, BigDecimal.ROUND_DOWN) + getActivity().getResources().getString(R.string.gold_extention));
                        //当前算法
                        CalculationName.setText(pageData.getData().getCoin() + getActivity().getResources().getString(R.string.The_current_algorithm));
                        txCalculationMoney.setText(pageData.getData().getComputing().setScale(4, BigDecimal.ROUND_DOWN) + getActivity().getResources().getString(R.string.gold_extention));
                        //矿池量
                        txMineralName.setText(pageData.getData().getCoin() + getActivity().getResources().getString(R.string.The_current_algorithm));
                        txMineralMoney.setText(pageData.getData().getMiningNum().setScale(4, BigDecimal.ROUND_DOWN) + getActivity().getResources().getString(R.string.gold_extention));


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    /**
     * 矿池量
     * 当前算法
     * 当日产量
     */
    @SuppressLint("CheckResult")
    private void initWebsocket() {

        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "miningsocket")
                .compose(RxSchedulers.io_main())
                .subscribe(new WebSocketSubscriber2<MiningInfo>() {
                               @Override
                               protected void onMessage(MiningInfo pageData) {
                                   if (pageData != null) {

                                       //当日产量
                                       txOnedayNuber.setText(pageData.getCoin() + getActivity().getResources().getString(R.string.Daily_output));
                                       txTxNuberMoney.setText(pageData.getDaymining().setScale(4, BigDecimal.ROUND_DOWN) + getActivity().getResources().getString(R.string.gold_extention));
                                       //当前算法
                                       CalculationName.setText(pageData.getCoin() + getActivity().getResources().getString(R.string.The_current_algorithm));
                                       txCalculationMoney.setText(pageData.getComputing().setScale(4, BigDecimal.ROUND_DOWN) + getActivity().getResources().getString(R.string.gold_extention));
                                       //矿池量
                                       txMineralName.setText(pageData.getCoin() + getActivity().getResources().getString(R.string.The_current_algorithm));
                                       txMineralMoney.setText(pageData.getMiningNum().setScale(4, BigDecimal.ROUND_DOWN) + getActivity().getResources().getString(R.string.gold_extention));
                                   } else {

                                   }

                               }

                               @Override
                               protected void onReconnect() {
                                   Log.e("MainActivity", "重连");

                               }

                           }
                );


    }

    /**
     * 24h待分红累计约
     * 24h单币分红约
     */
    @SuppressLint("CheckResult")
    private void coinwelfare() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .coinwelfare(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {
                        txTimeRed.setText(pageData.getData().getWelfareCountAbout() + "USDT");
                        BigDecimal bj = new BigDecimal(pageData.getData().getHoldWelfareCountAbout());
                        timeDan.setText(bj.setScale(4, BigDecimal.ROUND_DOWN) + "USDT");
                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    /**
     * 24h待分红累计约
     * 24h单币分红约
     */
    private void coinwelfareWebsocket() {

        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinwelfaresocket")
                .compose(RxSchedulers.io_main())
                .subscribe(new WebSocketSubscriber2<WelfareCountAbout>() {
                    @Override
                    protected void onMessage(WelfareCountAbout pageData) {
                        try {
                            if (pageData != null) {
                                txTimeRed.setText(pageData.getWelfareCountAbout() + "USDT" + "");
                                BigDecimal bj = new BigDecimal(pageData.getHoldWelfareCountAbout());
                                timeDan.setText(bj.setScale(4, BigDecimal.ROUND_DOWN) + "USDT" + "");
                            }
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连1");

                    }

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
                        txUpDay.setText(pageData.getData().getLastWelfare() + "USDT");

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

    }

    @OnClick({R.id.comtitlebar, R.id.LL_fenRed, R.id.btn_Red, R.id.Btn_mining})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;

            //24h待分红累计
            case R.id.LL_fenRed:
                intent.setClass(getApplicationContext(), Accumulative24HListActivity.class);
                startActivity(intent);
                break;
            //我的分红
            case R.id.btn_Red:
                if (kv.decodeString("tokenId") == null) {
                    t(getActivity().getResources().getString(R.string.Please_log));
                } else {
                    intent.setClass(getApplicationContext(), CentsRedActivity.class);
                    startActivity(intent);
                }
                break;
            //我的挖矿
            case R.id.Btn_mining:

                if (kv.decodeString("tokenId") == null) {
                    t(getActivity().getResources().getString(R.string.Please_log));
                } else {
                    intent.setClass(getApplicationContext(), MeMiningListActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

        //矿池量
        //当前算法
        //当日产量
        getMiningInfo();
        //矿池量
        //当前算法
        //当日产量
        initWebsocket();
        //24h待分红累计约
        //24h单币分红约
        coinwelfare();
        //24h待分红累计约
        //24h单币分红约
        coinwelfareWebsocket();
        //获取昨日累计分红
        lastwelfare();
    }
}
