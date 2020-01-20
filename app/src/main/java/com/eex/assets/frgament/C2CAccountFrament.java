package com.eex.assets.frgament;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.activity.C2CSearchActivity;
import com.eex.assets.adpater.CommMoneyC2CAdapter;
import com.eex.assets.bean.CtcaccountList;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;

import com.eex.common.view.EmptyView;
import com.eex.home.activity.home.C2CSetMoneyActivity;
import com.eex.home.activity.login.LoginActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
 * @Package: com.overthrow.assets.frgament
 * @ClassName: C2CAccountFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 10:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 10:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CAccountFrament extends BaseFragment {


    /**
     *
     */
    @BindView(R.id.Recharge)
    LinearLayout Recharge;
    /**
     *
     */
    @BindView(R.id.suochang)
    LinearLayout suochang;
    /**
     *
     */
    @BindView(R.id.tx_btc)
    TextView txBtc;
    /**
     *
     */
    @BindView(R.id.tx_RMB)
    TextView txRMB;
    /**
     *
     */
    @BindView(R.id.tx_USD)
    TextView txUSD;
    /**
     *
     */
    @BindView(R.id.c2csearch)
    TextView c2csearch;
    /**
     *
     */
    @BindView(R.id.ckbName)
    CheckBox ckbName;
    /**
     *
     */
    @BindView(R.id.txCkb)
    TextView txCkb;
    /**
     *
     */
    @BindView(R.id.LL_ckb)
    LinearLayout LLCkb;
    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     *
     */
    Unbinder unbinder;


    private ArrayList<CtcaccountList> list = new ArrayList<>();
    /**
     * 用户拥有的币种
     */
    private ArrayList<CtcaccountList> Newdata = new ArrayList<>();

    private CommMoneyC2CAdapter adapter;

    private Integer type = 1;

    @Override
    protected void lazyLoad() {
        net();
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();

    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    dialog.dismiss();
                    if (data.getData() != null) {

                        txRMB.setText("￥" + data.getData().getCnyeTotalAssets().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        txBtc.setText(data.getData().getBtcTotalAssets().setScale(5, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());


                        list.clear();
                        list.addAll(data.getData().getList());

                        adapter.notifyDataSetChanged();

                        Newdata.clear();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getUsableMoney().compareTo(new BigDecimal("0.0")) == 1) {
                                Newdata.add(list.get(i));
                            }
                        }

                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {
                    dialog.dismiss();

                });
    }


    @Override
    protected void initUiAndListener() {


        adapter = new CommMoneyC2CAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));


    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.Recharge, R.id.suochang, R.id.tx_btc, R.id.tx_RMB, R.id.tx_USD, R.id.c2csearch, R.id.ckbName, R.id.txCkb, R.id.LL_ckb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Recharge:

                DataSM();
                break;
            case R.id.suochang:
                break;
            case R.id.tx_btc:
                break;
            case R.id.tx_RMB:
                break;
            case R.id.tx_USD:
                break;
            case R.id.c2csearch:
                intent.setClass(getContext(), C2CSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ckbName:
                if (Newdata != null || Newdata.size() != 0 && list != null || list.size() != 0) {
                    if (type == 1) {
                        adapter = new CommMoneyC2CAdapter(Newdata);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        ckbName.setChecked(true);
                        txCkb.setText("隐藏小额资产");
                        type = 2;
                    } else {
                        adapter = new CommMoneyC2CAdapter(list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        ckbName.setChecked(false);
                        txCkb.setText("隐藏小额资产");
                        type = 1;
                    }
                }
                break;
            case R.id.txCkb:
                break;
            case R.id.LL_ckb:

                if (Newdata != null || Newdata.size() != 0 && list != null || list.size() != 0) {
                    if (type == 1) {
                        adapter = new CommMoneyC2CAdapter(Newdata);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        ckbName.setChecked(true);
                        txCkb.setText("隐藏小额资产");
                        type = 2;
                    } else {
                        adapter = new CommMoneyC2CAdapter(list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        ckbName.setChecked(false);
                        txCkb.setText("隐藏小额资产");
                        type = 1;
                    }
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void DataSM() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        intent.putExtra("flage", "2");
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(1)) {
                                Toast.makeText(getActivity(), "请完成实名等级2认证后操作", Toast.LENGTH_SHORT).show();

                            } else if (data.getData().getLevel().equals(2)) {
                                intent.setClass(getActivity(), C2CSetMoneyActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(3)) {

                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    intent.setClass(getActivity(), C2CSetMoneyActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (data.getData().getRemark() != null) {
                                    Toast.makeText(getActivity(), "实名审核已拒绝，请重新进行实名认证", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "实名审核待审核，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (kv.decodeString("tokenId") != null) {
            ckbName.setChecked(false);
            //个人中心
            net();
        }

    }
}
