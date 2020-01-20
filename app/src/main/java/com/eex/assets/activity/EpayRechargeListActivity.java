package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import com.eex.R;
import com.eex.assets.adpater.EpayRechargeAdapter;
import com.eex.assets.bean.FunCode;
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
 * @ClassName: EpayRechargeListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EpayRechargeListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.MoneyRecyclerView)
    RecyclerView recyclerView;

    private ArrayList<FunCode> list = new ArrayList<FunCode>();
    private EpayRechargeAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_epay_recharge_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.tixian));
        //获取提现币种
        net();
    }

    /**
     * 获取提现币种
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getWithdrawFunCode( kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                        list.clear();
                    if (data.getData() != null) {
                        list.addAll(data.getData());
                        FunCode RNB = new FunCode();
                        RNB.setCurrencyStr("人民币");
                        RNB.setCurrency("CNYE");
                        list.add(0, RNB);
                    }

                    setView(list);
                }, throwable -> {

                });

    }

    private void setView(ArrayList<FunCode> list) {

        recyclerView.setLayoutManager(new LinearLayoutManager(EpayRechargeListActivity.this));
        adapter = new EpayRechargeAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        adapter.setEmptyView(new EmptyView(EpayRechargeListActivity.this));

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                Intent intent = new Intent();
                if ( kv.decodeString("tokenId") == null) {
                    Toast.makeText(EpayRechargeListActivity.this,
                            "请登录后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                //人名币提现
                if (position == 0) {
                    intent.setClass(EpayRechargeListActivity.this, WithdrawalsActivity.class);
                    startActivity(intent);
                    //其他币提现
                } else {
                    try {
                        if (kv.decodeString("phone") == null) {
                            Toast.makeText(EpayRechargeListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(EpayRechargeListActivity.this, PhoneNameActivity.class);
                            startActivity(intent);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(EpayRechargeListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(EpayRechargeListActivity.this, PhoneNameActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    try {
                        if (kv.decodeString("accountPassWord") == null) {
                            Toast.makeText(EpayRechargeListActivity.this, "请设置交易密码后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(EpayRechargeListActivity.this, MonyPwodActivity.class);
                            startActivity(intent1);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(EpayRechargeListActivity.this, "请设置交易密码后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(EpayRechargeListActivity.this, MonyPwodActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    //法币代码
                    intent.putExtra("currency", list.get(position).getCurrency());
                    //法币中文名称
                    intent.putExtra("currencyStr", list.get(position).getCurrencyStr());
                    //提现手续费
                    intent.putExtra("withdrawFee", list.get(position).getWithdrawFee().stripTrailingZeros().toPlainString());
                    //提现最大限额
                    intent.putExtra("withdrawMaxNum", list.get(position).getWithdrawMaxNum().stripTrailingZeros().toPlainString());
                    //提现最小限额
                    intent.putExtra("withdrawMinNum", list.get(position).getWithdrawMinNum().stripTrailingZeros().toPlainString());

                    intent.setClass(EpayRechargeListActivity.this, EpayRechargeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initUiAndListener() {

        recyclerView.setLayoutManager(new LinearLayoutManager(EpayRechargeListActivity.this));
        adapter = new EpayRechargeAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setEmptyView(new EmptyView(EpayRechargeListActivity.this));
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if ( kv.decodeString("tokenId") == null) {
                    Toast.makeText(EpayRechargeListActivity.this,
                            "请登录后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                //人名币提现
                if (position == 0) {
                    intent.setClass(EpayRechargeListActivity.this, WithdrawalsActivity.class);
                    startActivity(intent);
                    //其他币提现
                } else {
                    try {
                        if (kv.decodeString("phone") == null) {
                            Toast.makeText(EpayRechargeListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(EpayRechargeListActivity.this, PhoneNameActivity.class);
                            startActivity(intent);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(EpayRechargeListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(EpayRechargeListActivity.this, PhoneNameActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    try {
                        if (kv.decodeString("accountPassWord") == null) {
                            Toast.makeText(EpayRechargeListActivity.this, "请设置交易密码后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(EpayRechargeListActivity.this, MonyPwodActivity.class);
                            startActivity(intent1);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(EpayRechargeListActivity.this, "请设置交易密码后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(EpayRechargeListActivity.this, MonyPwodActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    //法币代码
                    intent.putExtra("currency", list.get(position).getCurrency());
                    //法币中文名称
                    intent.putExtra("currencyStr", list.get(position).getCurrencyStr());
                    //提现手续费
                    intent.putExtra("withdrawFee", list.get(position).getWithdrawFee().stripTrailingZeros().toPlainString());
                    //提现最大限额
                    intent.putExtra("withdrawMaxNum", list.get(position).getWithdrawMaxNum().stripTrailingZeros().toPlainString());
                    //提现最小限额
                    intent.putExtra("withdrawMinNum", list.get(position).getWithdrawMinNum().stripTrailingZeros().toPlainString());

                    intent.setClass(EpayRechargeListActivity.this, EpayRechargeActivity.class);
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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if ( kv.decodeString("tokenId") == null) {
                    Toast.makeText(EpayRechargeListActivity.this,
                            "请登录后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                    //人名币提现
                if (position == 0) {
                    intent.setClass(EpayRechargeListActivity.this, WithdrawalsActivity.class);
                    startActivity(intent);
                    //其他币提现
                } else {
                    try {
                        if (kv.decodeString("phone") == null) {
                            Toast.makeText(EpayRechargeListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(EpayRechargeListActivity.this, PhoneNameActivity.class);
                            startActivity(intent);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(EpayRechargeListActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(EpayRechargeListActivity.this, PhoneNameActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    try {
                        if (kv.decodeString("accountPassWord") == null) {
                            Toast.makeText(EpayRechargeListActivity.this, "请设置交易密码后操作", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.setClass(EpayRechargeListActivity.this, MonyPwodActivity.class);
                            startActivity(intent1);
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(EpayRechargeListActivity.this, "请设置交易密码后操作", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(EpayRechargeListActivity.this, MonyPwodActivity.class);
                        startActivity(intent1);
                        return;
                    }
                    //法币代码
                    intent.putExtra("currency", list.get(position).getCurrency());
                    //法币中文名称
                    intent.putExtra("currencyStr", list.get(position).getCurrencyStr());
                    //提现手续费
                    intent.putExtra("withdrawFee", list.get(position).getWithdrawFee().stripTrailingZeros().toPlainString());
                    //提现最大限额
                    intent.putExtra("withdrawMaxNum", list.get(position).getWithdrawMaxNum().stripTrailingZeros().toPlainString());
                    //提现最小限额
                    intent.putExtra("withdrawMinNum", list.get(position).getWithdrawMinNum().stripTrailingZeros().toPlainString());

                    intent.setClass(EpayRechargeListActivity.this, EpayRechargeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}
