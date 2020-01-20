package com.eex.market.activity;

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
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.EmptyView;
import com.eex.home.bean.MainList;
import com.eex.market.adpater.SearchAdapter;

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
 * @Package: com.overthrow.market.activity
 * @ClassName: SearchActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 9:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 9:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 搜索
 */
public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private SearchAdapter adapter;

    public ArrayList<MainList> itemslist1 = new ArrayList<>();

    private ArrayList<MainList> newList = new ArrayList<>();
    private String BiName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
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
                    if (itemslist1 == null || itemslist1.size() == 0) {
                        Toast.makeText(SearchActivity.this, "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        newList.clear();
                        String sq = headerCampaignSearchEditText.getText().toString().trim();
                        for (int i = 0; i < itemslist1.size(); i++) {
                            if (itemslist1.get(i).getDealPair().contains(sq.toUpperCase())) {
                                newList.add(itemslist1.get(i));
                            }
                            setView(newList);
                        }

                    }

                } else {
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
        HashMap<String, String> hashMap = new HashMap<String, String>();

        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        BiName = "";
                        itemslist1.clear();
                        itemslist1.addAll(data.getData());


                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });


    }

    @Override
    protected void initUiAndListener() {


    }

    private void setView(ArrayList<MainList> newList) {

        adapter = new SearchAdapter(R.layout.item_search, newList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ckbox:
                        if (kv.decodeString("username") == null) {
                            Toast.makeText(SearchActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                        } else {
                            searchMoney(newList.get(position).getDealPair());
                        }

                        break;

                    default:
                        break;
                }
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("JYBi", newList.get(position).getCoinCode());
                intent.putExtra("DJBi", newList.get(position).getPricingCoin());
                // BTC/USDT
                intent.putExtra("Biname", newList.get(position).getDealPair().replace("_", "/"));
                //BTC_USDT
                intent.putExtra("KlinBiname", newList.get(position).getDealPair());

                intent.setClass(SearchActivity.this, DonationActivity.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("CheckResult")
    private void searchMoney(String dealPair) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("dealpear", dealPair);

        ApiFactory.getInstance()
                .saveOrUpdateDealPairCollection(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

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
