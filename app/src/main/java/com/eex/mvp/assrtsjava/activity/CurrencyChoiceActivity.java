package com.eex.mvp.assrtsjava.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.assets.bean.Bilistdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.NewComTitleBar;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.assrtsjava.adapter.CurrencySelectionAdapter;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: CurrencyChoiceActivity
 * @Description: 币种选择
 * @Author: hcj
 * @CreateDate: 2019/12/24 15:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 15:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CurrencyChoiceActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.Chargebar)
    NewComTitleBar Chargebar;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_back)
    TextView searchBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.AssetsChargeMoney)
    LinearLayout AssetsChargeMoney;

    private ArrayList<Bilistdata> list = new ArrayList<>();
    private ArrayList<Bilistdata> mNewList = new ArrayList<>();

    private CurrencySelectionAdapter adapter;


    private String CurrencyChoice;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_assets_charge_money;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        CurrencyChoice = getIntent().getStringExtra("CurrencyChoice");

        if (CurrencyChoice.equals("1")) {
            Chargebar.setTitle(getActivity().getResources().getString(R.string.AssetsChargeMone_choice));
        } else if (CurrencyChoice.equals("2")) {
            Chargebar.setTitle(getActivity().getResources().getString(R.string.Currency_selection));


        }
        init();
        net();
    }

    private void init() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!searchEdit.getText().toString().trim().equals("")) {
                    if (list == null || list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    } else {

                        mNewList.clear();
                        String sq = searchEdit.getText().toString().trim();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().contains(sq.toUpperCase())) {
                                mNewList.add(list.get(i));
                            }
                            setView();
                        }

                    }

                } else {
                    mNewList = new ArrayList<Bilistdata>();
                    mNewList.clear();
                    setView();
                }

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @SuppressLint("CheckResult")
    private void net() {
//        HashMap<String, String> hashMap = new HashMap<>();
//        ApiFactory.getInstance()
//                .accountinfo(kv.decodeString("tokenId"), hashMap)
//                .compose(RxSchedulers.io_main())
//                .subscribe(data -> {
//                    if (data.getData() != null) {
//                        Log.e("null", "net: " + data.getData().toString());
//
//                        list.clear();
//                        list.addAll(data.getData().getUserCoinAccount());
//                        adapter.notifyDataSetChanged();
//
//
//                    } else {
//                        t(data.getMsg());
//                    }
//
//                }, throwable -> {
//
//                    Log.e("TAG", "net: " + throwable.toString());
//
//                });


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "1");
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    dialog.dismiss();

                    if (data.getData() != null) {
                        list.clear();
                        list.addAll(data.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {
                    dialog.dismiss();

                });
    }


    @Override
    protected void initUiAndListener() {

        adapter = new CurrencySelectionAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

//        adapter = new CurrencyChoiceAdapter();
//        RecyclerViewUtil.initNoDecoration(getActivity(), recyclerView.getRecycler(), adapter);
//        list = recyclerView.sortData(newlist);
//        recyclerView.initData(list);
//        adapter.initData(list);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.Chargebar, R.id.search_edit, R.id.search_back, R.id.recyclerView, R.id.AssetsChargeMoney})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Chargebar:
                break;
            case R.id.search_edit:
                net();
                break;
            case R.id.search_back:
                finish();
                break;
            case R.id.recyclerView:
                break;
            case R.id.AssetsChargeMoney:
                break;
        }
    }

    private void setView() {
        adapter = new CurrencySelectionAdapter(mNewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //充币
                if (CurrencyChoice.equals("1")) {

                    intent = new Intent(getActivity(), CurrencyRechargeActivity.class);
                    intent.putExtra("coinCode", mNewList.get(position).getCoinCode());
                    startActivity(intent);
                    //提币
                } else if (CurrencyChoice.equals("2")) {
                    isName(position);


                }
            }
        });


    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        //充币
        if (CurrencyChoice.equals("1")) {

            intent = new Intent(getActivity(), CurrencyRechargeActivity.class);
            intent.putExtra("coinCode", list.get(position).getCoinCode());
            startActivity(intent);
            //提币
        } else if (CurrencyChoice.equals("2")) {
            isName(position);
            //划转
        }
    }

    @SuppressLint("CheckResult")
    private void isName(int position) {

        HashMap<String, String> hashMap = new HashMap<>();
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    try {
                        if (data.getData().getLevel().equals(1)) {
                            t("请完成实名等级2认证后操作");

                        } else if (data.getData().getLevel().equals(2)) {
                            intent.putExtra("coinCode", mNewList.get(position).getCoinCode());
                            intent.putExtra("currencyRate", mNewList.get(position).getCurrencyRate().stripTrailingZeros().toPlainString());
                            intent.putExtra("fixedRate", mNewList.get(position).getFixedRate().stripTrailingZeros().toPlainString());
                            intent.putExtra("coinMax", mNewList.get(position).getMaxNum().stripTrailingZeros().toPlainString());
                            intent.putExtra("coinMin", mNewList.get(position).getMinNum().stripTrailingZeros().toPlainString());
                            intent.setClass(getActivity(), WithdrawMoneyActivity.class);
                            getActivity().startActivity(intent);
                        } else if (data.getData().getLevel().equals(3)) {
                            if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                intent.putExtra("coinCode", mNewList.get(position).getCoinCode() + "");
                                intent.putExtra("currencyRate", mNewList.get(position).getCurrencyRate().stripTrailingZeros().toPlainString() + "");
                                intent.putExtra("fixedRate", mNewList.get(position).getFixedRate().stripTrailingZeros().toPlainString() + "");
                                intent.putExtra("coinMax", mNewList.get(position).getMaxNum().stripTrailingZeros().toPlainString() + "");
                                intent.putExtra("coinMin", mNewList.get(position).getMinNum().stripTrailingZeros().toPlainString() + "");
                                intent.setClass(getActivity(), WithdrawMoneyActivity.class);
                                getActivity().startActivity(intent);
                            } else {
                                t("请绑定手机号后操作");
                            }
                        } else {
                            t("请绑定手机号后操作");
                        }

                    } catch (Exception e) {
                        t("实名审核中请稍后再试");
                    }
                });
    }
}
