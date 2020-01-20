package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.assets.adpater.AearchListAdapter;
import com.eex.assets.bean.Bilistdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
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
 * @ClassName: AearchListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 13:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 13:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AearchListActivity extends BaseActivity {


    private static final String TAG = "AearchListActivity";
    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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


    private ArrayList<Bilistdata> list = new ArrayList<>();
    private ArrayList<Bilistdata> newList = new ArrayList<Bilistdata>();
    private AearchListAdapter adapter;


    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aearch_list;
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
                    if (list == null || list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        newList = new ArrayList<Bilistdata>();
                        newList.clear();
                        String sq = headerCampaignSearchEditText.getText().toString().trim();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().contains(sq.toUpperCase())) {
                                newList.add(list.get(i));
                            }
                            setView(newList);
                        }

                    }

                } else {
                    newList = new ArrayList<Bilistdata>();
                    newList.clear();
                    setView(newList);
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
        hashMap.put("type", "0");

        ApiFactory.getInstance()
                .depositcoincoinlist(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    list = new ArrayList<Bilistdata>();
                    if (data.getData() != null) {
                        list.clear();
                        list.addAll(data.getData());

                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    @Override
    protected void initUiAndListener() {


    }

    private void setView(ArrayList<Bilistdata> newList) {


        adapter = new AearchListAdapter(R.layout.item_rechargba, newList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent();
                intent.putExtra("name", newList.get(position).getCoinCode());
                intent.putExtra("id", newList.get(position).getId());
                Log.e(TAG, "onItemClick: " + getIntent().getStringExtra("Type"));
                if (getIntent().getStringExtra("Type") != null) {//跳转币账户
                    if (getIntent().getStringExtra("Type").equals("BiListData")) {
                        intent.putExtra("BiListData", newList.get(position).getCoinCode());
                        intent.putExtra("BiAddreiss", newList.get(position).getCoinId());
                        intent.setClass(AearchListActivity.this, CurrencyAddresListActivity.class);
                        startActivity(intent);
                    }
                    if (getIntent().getStringExtra("Type").equals("CurrencyAddress")) {
                        intent.putExtra("currencyType", newList.get(position).getCoinCode());
                        getActivity().setResult(5000, intent);
                        getActivity().finish();
                    }

                } else {//跳转冲币选币界面

                    intent.setClass(mContext, RechargeMoneyBActivity.class);
                    mContext.startActivity(intent);
                }


//                //跳转冲币界面
//                if (type == null) {
//                    intent.setClass(getActivity(), RechargeMoneyBActivity.class);
//                    mContext.startActivity(intent);
//                } else {
//                    //跳转币账户
//                    if (getIntent().getStringExtra("Type").equals("BiListData")) {
//                        intent.putExtra("BiListData", newList.get(position).getCoinCode());
//                        intent.putExtra("BiAddreiss", newList.get(position).getCoinId());
//                        intent.setClass(AearchListActivity.this, CurrencyAddresListActivity.class);
//                        mContext.startActivity(intent);
//                    }
//
//                    if (getIntent().getStringExtra("Type").equals("CurrencyAddress")) {
//                        intent.putExtra("currencyType", newList.get(position).getCoinCode());
//                        setResult(5000, intent);
//                        finish();
//                    }
//                }
            }
        });
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
}
