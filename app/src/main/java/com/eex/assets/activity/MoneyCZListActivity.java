package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import com.eex.R;

import com.eex.assets.adpater.MoneyCZListAdapter;
import com.eex.assets.bean.getFunCode;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.activity.home.PhoneNameActivity;

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
 * @ClassName: MoneyCZListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 14:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 14:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 充值
 */
public class MoneyCZListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.LL_CNTE)
    LinearLayout LLCNTE;
    /**
     *
     */
    @BindView(R.id.MoneyRecyclerView)
    RecyclerView recyclerView;

    private MoneyCZListAdapter adapter;
    private ArrayList<getFunCode> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_list2;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle(getActivity().getResources().getString(R.string.chongzi));
        //
        net();
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .getFunCode(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {

                    if (arrayListData != null) {
                        list.clear();
                        list.addAll(arrayListData.getData());

                        setView(list);
                    }

                }, throwable -> {

                });
    }

    private void setView(ArrayList<getFunCode> list) {

        adapter = new MoneyCZListAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));


        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if (kv.decodeString("tokenId") == null) {
                    Toast.makeText(MoneyCZListActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                //人名币充值
                if (position == 0) {

                    intent.setClass(MoneyCZListActivity.this, RechargeActivity.class);
                    startActivity(intent);
                } else {//其他币充值
                    try {
                        if (kv.decodeString("phone")== null) {
                            Toast.makeText(MoneyCZListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(MoneyCZListActivity.this, PhoneNameActivity.class);
                            startActivity(intent1);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(MoneyCZListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(MoneyCZListActivity.this, PhoneNameActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    //法币代码
                    intent.putExtra("currency", list.get(position).getCurrency());
                    //法币中文名称
                    intent.putExtra("currencyStr", list.get(position).getCurrencyStr());
                    //充值手续费
                    intent.putExtra("chargeFee", list.get(position).getChargeFee().stripTrailingZeros().toPlainString());
                    //最大限额
                    intent.putExtra("chargeMaxNum", list.get(position).getChargeMaxNum().stripTrailingZeros().toPlainString());
                    //充值最小限额
                    intent.putExtra("chargeMinNum", list.get(position).getWithdrawMinNum().stripTrailingZeros().toPlainString());
                    intent.setClass(MoneyCZListActivity.this, ForeignCurrencyRechargeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void initUiAndListener() {

        adapter = new MoneyCZListAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if (kv.decodeString("tokenId") == null) {
                    Toast.makeText(MoneyCZListActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                //人名币充值
                if (position == 0) {

                    intent.setClass(MoneyCZListActivity.this, RechargeActivity.class);
                    startActivity(intent);
                } else {//其他币充值
                    try {
                        if (kv.decodeString("phone") == null) {
                            Toast.makeText(MoneyCZListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(MoneyCZListActivity.this, PhoneNameActivity.class);
                            startActivity(intent1);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(MoneyCZListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(MoneyCZListActivity.this, PhoneNameActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    //法币代码
                    intent.putExtra("currency", list.get(position).getCurrency());
                    //法币中文名称
                    intent.putExtra("currencyStr", list.get(position).getCurrencyStr());
                    //充值手续费
                    intent.putExtra("chargeFee", list.get(position).getChargeFee().stripTrailingZeros().toPlainString());
                    //最大限额
                    intent.putExtra("chargeMaxNum", list.get(position).getChargeMaxNum().stripTrailingZeros().toPlainString());
                    //充值最小限额
                    intent.putExtra("chargeMinNum", list.get(position).getWithdrawMinNum().stripTrailingZeros().toPlainString());
                    intent.setClass(MoneyCZListActivity.this, ForeignCurrencyRechargeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.LL_CNTE})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.LL_CNTE:
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if (kv.decodeString("tokenId") == null) {
                    Toast.makeText(MoneyCZListActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                //人名币充值
                if (position == 0) {

                    intent.setClass(MoneyCZListActivity.this, RechargeActivity.class);
                    startActivity(intent);
                } else {//其他币充值
                    try {
                        if (kv.decodeString("phone") == null) {
                            Toast.makeText(MoneyCZListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(MoneyCZListActivity.this, PhoneNameActivity.class);
                            startActivity(intent1);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(MoneyCZListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(MoneyCZListActivity.this, PhoneNameActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    //法币代码
                    intent.putExtra("currency", list.get(position).getCurrency());
                    //法币中文名称
                    intent.putExtra("currencyStr", list.get(position).getCurrencyStr());
                    //充值手续费
                    intent.putExtra("chargeFee", list.get(position).getChargeFee().stripTrailingZeros().toPlainString());
                    //最大限额
                    intent.putExtra("chargeMaxNum", list.get(position).getChargeMaxNum().stripTrailingZeros().toPlainString());
                    //充值最小限额
                    intent.putExtra("chargeMinNum", list.get(position).getWithdrawMinNum().stripTrailingZeros().toPlainString());
                    intent.setClass(MoneyCZListActivity.this, ForeignCurrencyRechargeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        net();
    }
}
