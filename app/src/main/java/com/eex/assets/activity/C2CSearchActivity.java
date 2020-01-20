package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.assets.adpater.C2CSearchAdapter;
import com.eex.assets.bean.CtcaccountList;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.EmptyView;


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
 * @Package: com.overthrow.assets.activity
 * @ClassName: C2CSearchActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 14:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 14:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CSearchActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    /**
     *
     */
    @BindView(R.id.header_return_image)
    ImageView headerReturnImage;
    /**
     *
     */
    @BindView(R.id.header_return_RL)
    RelativeLayout headerReturnRL;
    /**
     *
     */
    @BindView(R.id.header_campaign_search_editText)
    EditText headerCampaignSearchEditText;
    /**
     *
     */
    @BindView(R.id.main_header_campaign_search1)
    ImageView mainHeaderCampaignSearch1;
    /**
     *
     */
    @BindView(R.id.main_header_search_qingkong)
    TextView mainHeaderSearchQingkong;
    /**
     *
     */
    @BindView(R.id.ll_base_toolber)
    LinearLayout llBaseToolber;
    /**
     *
     */
    @BindView(R.id.tra_main_header)
    LinearLayout traMainHeader;
    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private ArrayList<CtcaccountList> ctcaccountLists = new ArrayList<>();
    private ArrayList<CtcaccountList> newList = new ArrayList<>();
    private C2CSearchAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2_csearch;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        net();


        init();
    }

    private void init() {
        headerCampaignSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!headerCampaignSearchEditText.getText().toString().trim().equals("")) {
                    if (ctcaccountLists == null || ctcaccountLists.size() == 0) {
                        Toast.makeText(C2CSearchActivity.this, "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        newList.clear();
                        String sq = headerCampaignSearchEditText.getText().toString().trim();
                        for (int i = 0; i < ctcaccountLists.size(); i++) {
                            if (ctcaccountLists.get(i).getCoinCode().contains(sq.toUpperCase())) {
                                newList.add(ctcaccountLists.get(i));
                            }
                            setView();
                        }

                    }

                } else {

                    newList.clear();
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

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .listctcaccount(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        ctcaccountLists.clear();
                        ctcaccountLists.addAll(data.getData().getList());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {


    }

    private void setView() {
        adapter = new C2CSearchAdapter(newList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intent.putExtra("coin", newList.get(position).getCoinCode());
                intent.putExtra("imgurl", newList.get(position).getImgUrl());
                intent.putExtra("totalAssets", newList.get(position).getTotalAssets().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("usableMoney", newList.get(position).getUsableMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("frozenMoney", newList.get(position).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.setClass(getActivity(), C2CDetailsActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.header_return_image, R.id.header_return_RL, R.id.header_campaign_search_editText, R.id.main_header_campaign_search1, R.id.main_header_search_qingkong, R.id.ll_base_toolber})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_return_image:
                break;
            case R.id.header_return_RL:
                break;
            case R.id.header_campaign_search_editText:

                net();
                break;
            case R.id.main_header_campaign_search1:
                break;
            case R.id.main_header_search_qingkong:
                finish();
                break;
            case R.id.ll_base_toolber:
                break;


            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("coin", newList.get(position).getCoinCode());
                intent.putExtra("imgurl", newList.get(position).getImgUrl());
                intent.putExtra("totalAssets", newList.get(position).getTotalAssets().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("usableMoney", newList.get(position).getUsableMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("frozenMoney", newList.get(position).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.setClass(getActivity(), C2CDetailsActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }


}
