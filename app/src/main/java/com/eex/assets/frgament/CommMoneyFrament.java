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
import com.eex.assets.activity.CommSearchActivity;
import com.eex.assets.activity.RechargeMoneyActivity;
import com.eex.assets.adpater.CommonwealFragmentAdapter;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;

import com.eex.common.view.EmptyView;
import com.eex.home.activity.home.ConducMoneyActivity;
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
 * @ClassName: CommMoneyFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 10:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 10:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommMoneyFrament extends BaseFragment {


    /**
     *
     */
    @BindView(R.id.Recharge)
    LinearLayout Recharge;
    /**
     *
     */
    @BindView(R.id.Money)
    LinearLayout Money;
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
    @BindView(R.id.myzoe_hotTextview)
    TextView myzoeHotTextview;
    /**
     *
     */
    @BindView(R.id.myzoe_bigTietview)
    TextView myzoeBigTietview;
    /**
     *
     */
    @BindView(R.id.serBi)
    TextView serBi;
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

    private ArrayList<Personal> list = new ArrayList<>();
    private ArrayList<Personal> mNewList = new ArrayList<Personal>();
    private CommonwealFragmentAdapter adapter;
    private Integer type = 1;

    @Override
    protected void lazyLoad() {
        net();
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();
    }


    /**
     *
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    dialog.dismiss();
                    if (data.getData() != null) {

                        myzoeHotTextview.setText("可用CNYE:" + data.getData().getUseMoneyCny().setScale(2, BigDecimal.ROUND_CEILING).toString());
                        myzoeBigTietview.setText("冻结CNYE:" + data.getData().getFrozenMoneyCny().setScale(2, BigDecimal.ROUND_CEILING).toString());
                        txBtc.setText(data.getData().getTotalMoneyBtc().setScale(5, BigDecimal.ROUND_DOWN).doubleValue() + "BTC");
                        txRMB.setText("¥" + data.getData().getTotalMoneyCny().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        txUSD.setText("$" + data.getData().getTotalMoneyUsdt().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

                        list.clear();
                        list = data.getData().getUserCoinAccount();
//
//                        MMKV kv = MMKV.mmkvWithID("Personal", MMKV.MULTI_PROCESS_MODE);
//
//                        for (int i = 0; i < list.size(); i++) {
//
//                            kv.encode("coinCode", list.get(i).getCoinCode());
//                            kv.encode("coinName", list.get(i).getCoinName());
//                            kv.encode("imgUrl", list.get(i).getImgUrl());
//                        }
                        setviewtext();


                    }

                }, throwable -> {
                    dialog.dismiss();

                });


    }

    private void setviewtext() {


        mNewList.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTotalMoeny().compareTo(new BigDecimal("0.0")) == 1) {
                mNewList.add(list.get(i));
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonwealFragmentAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));


    }


    @Override
    protected void initUiAndListener() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_commmoney;
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


    @OnClick({R.id.Recharge, R.id.Money, R.id.suochang, R.id.tx_btc, R.id.tx_RMB, R.id.tx_USD, R.id.myzoe_hotTextview, R.id.myzoe_bigTietview, R.id.serBi, R.id.ckbName, R.id.txCkb, R.id.LL_ckb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Recharge:
                intent.setClass(getActivity(), RechargeMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.Money:
                break;
            case R.id.suochang:
                if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord").equals("")) {
                    Toast.makeText(getActivity(), "请设置交易密码后再操作", Toast.LENGTH_SHORT).show();
                } else {
                    //是否实名
                    authStauts();
                }
                break;
            case R.id.tx_btc:
                break;
            case R.id.tx_RMB:
                break;
            case R.id.tx_USD:
                break;
            case R.id.myzoe_hotTextview:
                break;
            case R.id.myzoe_bigTietview:
                break;
            case R.id.serBi:
                intent.setClass(getContext(), CommSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ckbName:
                if (type == 1) {
                    adapter = new CommonwealFragmentAdapter(mNewList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ckbName.setChecked(true);
                    txCkb.setText("隐藏小额资产");
                    type = 2;
                } else {
                    adapter = new CommonwealFragmentAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ckbName.setChecked(false);
                    txCkb.setText("隐藏小额资产");
                    type = 1;
                }
                break;
            case R.id.txCkb:
                break;
            case R.id.LL_ckb:
                if (type == 1) {
                    adapter = new CommonwealFragmentAdapter(mNewList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ckbName.setChecked(true);
                    txCkb.setText("隐藏小额资产");
                    type = 2;
                } else {
                    adapter = new CommonwealFragmentAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ckbName.setChecked(false);
                    txCkb.setText("隐藏小额资产");
                    type = 1;
                }
                break;


            default:
                break;
        }
    }

    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void authStauts() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 1002) {
                        intent.putExtra("flage", "2");
                        intent.setClass(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    if (data.getData() != null) {

                        try {
                            if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    intent.setClass(getActivity(), ConducMoneyActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }

                            } else if (data.getData().getLevel().equals(2)) {
                                intent.setClass(getActivity(), ConducMoneyActivity.class);
                                startActivity(intent);
                            } else if (data.getData().getLevel().equals(1)) {
                                Toast.makeText(getActivity(), "请实名认证等级2后操作", Toast.LENGTH_SHORT).show();
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
