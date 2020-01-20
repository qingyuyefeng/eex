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
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.market.adpater.BilistAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * @ClassName: KlineSearchActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/27 15:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/27 15:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KlineSearchActivity extends BaseActivity {

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


    private ArrayList<MainList> lists = new ArrayList<>();
    private String dealPair = "";
    private ArrayList<MainData> data = new ArrayList<>();
    private BilistAdapter adapter;
    private ArrayList<MainData> NewdataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kline_search;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        //首页list
        net();


    }

    private void init() {

        headerCampaignSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!headerCampaignSearchEditText.getText().toString().trim().equals("")) {
                    if (data == null || data.size() == 0) {

                        Toast.makeText(KlineSearchActivity.this, "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {

                        NewdataList.clear();
                        String sq = headerCampaignSearchEditText.getText().toString().trim();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getDelKey().contains(sq.toUpperCase())) {
                                NewdataList.add(data.get(i));
                            }
                            setView(NewdataList);
                        }


                    }

                } else {
                    NewdataList.clear();
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 首页list
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pricingCoin", "USDT");
        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    try {
                        if (data.isStauts() == true) {
                            dealPair = "";
                            lists.clear();
                            lists.addAll(data.getData());
                            for (int i = 0; i < lists.size(); i++) {

                                dealPair += lists.get(i).getDealPair() + ",";

                            }
                            getIndexMaketList(dealPair);

                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {

                });

    }

    /**
     * @param dealPair
     */
    @SuppressLint("CheckResult")
    private void getIndexMaketList(String dealPair) {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("delkeys", dealPair);

        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {

                    if (arrayListData != null) {

                        data.clear();
                        data.addAll(arrayListData.getData());
                        init();
                    } else {
                        t(arrayListData.getMsg());

                    }


                }, throwable -> {


                });
    }


    @Override
    protected void initUiAndListener() {
//
//        adapter = new BilistAdapter(R.layout.item_summary,data);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        adapter.setEmptyView(new EmptyView(getActivity()));
//        adapter.setOnItemClickListener(this);
//        adapter.setOnLoadMoreListener(this, recyclerView);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                String name = lists.get(position).getCoinCode() + "/" + lists.get(position).getPricingCoin();
//                String data1 = data.get(position).getDelKey();
//                //交易币
//                String JYBi = lists.get(position).getCoinCode();
//                //定价币
//                String DjBi = lists.get(position).getPricingCoin();
//                intent = new Intent(getActivity(), DonationActivity.class);
//
//                Intent intent = new Intent();
//                intent.putExtra("name",name);
//                intent.putExtra("data",data);
//                intent.putExtra("JYBi",JYBi);
//                intent.putExtra("DJBi",DjBi);
//                setResult(2000,intent);
//                finish();
//            }
//        });
//
    }

    @OnClick({R.id.header_return_image, R.id.header_return_RL, R.id.header_campaign_search_editText, R.id.main_header_campaign_search1, R.id.main_header_search_qingkong, R.id.ll_base_toolber, R.id.tra_main_header})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_return_image:
                break;
            case R.id.header_return_RL:
                break;
            case R.id.header_campaign_search_editText:


                //首页list
                net();

                break;
            case R.id.main_header_campaign_search1:
                break;
            case R.id.main_header_search_qingkong:
                finish();
                break;
            case R.id.ll_base_toolber:
                break;
            case R.id.tra_main_header:
                break;

            default:
                break;
        }
    }


    private void setView(final List<MainData> newdataList) {

        adapter = new BilistAdapter(R.layout.item_summary, newdataList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = newdataList.get(position).getDelKey() + "/" + lists.get(position).getPricingCoin();
                String data1 = newdataList.get(position).getDelKey();
                int j = newdataList.get(position).getDelKey().indexOf("_");
                //交易币.get(position).getCoinCode()
                String JYBi = newdataList.get(position).getDelKey().substring(0, j);
                //定价币
                String DjBi = newdataList.get(position).getDelKey().substring(newdataList.get(position).getDelKey().indexOf("_") + 1);

                intent = new Intent(KlineSearchActivity.this, DonationActivity.class);

                intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("data", data1);
                intent.putExtra("JYBi", JYBi);
                intent.putExtra("DJBi", DjBi);
                setResult(2000, intent);
                finish();

            }
        });

    }

//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//
//                String name = lists.get(position).getCoinCode() + "/" + lists.get(position).getPricingCoin();
//                String data1 = data.get(position).getDelKey();
//                //交易币
//                String JYBi = lists.get(position).getCoinCode();
//                //定价币
//                String DjBi = lists.get(position).getPricingCoin();
//                intent = new Intent(getActivity(), DonationActivity.class);
//
//                intent.putExtra("BiName", name);
//                intent.putExtra("BinameData", data1);
//                intent.putExtra("JYB", JYBi);
//                intent.putExtra("DJB", DjBi);
//                getActivity().startActivity(intent);
//                getActivity().finish();
//            }
//        });
//    }


}
