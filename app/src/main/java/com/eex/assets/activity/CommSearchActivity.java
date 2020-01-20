package com.eex.assets.activity;

import android.annotation.SuppressLint;
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
import com.eex.assets.adpater.CommSearchAdapter;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.EmptyView;

import java.math.BigDecimal;
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
 * @Package: com.overthrow.assets.activity
 * @ClassName: CommSearchActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 11:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 11:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommSearchActivity extends BaseActivity {

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

    private ArrayList<Personal> list = new ArrayList<>();
    private List<Personal> mNewList = new ArrayList<Personal>();
    private CommSearchAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comm_search;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        //
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

                        mNewList.clear();
                        String sq = headerCampaignSearchEditText.getText().toString().trim();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().contains(sq.toUpperCase())) {
                                mNewList.add(list.get(i));
                            }
                            setView();
                        }

                    }

                } else {
                    mNewList = new ArrayList<Personal>();
                    mNewList.clear();
                    setView();
                }

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     *
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {

                        list.clear();
                        list = data.getData().getUserCoinAccount();


                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {


                });


    }


    private void setView() {

        adapter = new CommSearchAdapter(R.layout.item_commonwealfragent, mNewList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                intent.putExtra("coin", mNewList.get(position).getCoinCode());
                intent.putExtra("dongjie", mNewList.get(position).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("imgAddress", mNewList.get(position).getImgUrl());
                intent.putExtra("coinName", mNewList.get(position).getCoinName());
                intent.putExtra("CoinMoney", mNewList.get(position).getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("FrozenMoney", mNewList.get(position).getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.putExtra("TotalMoeny", mNewList.get(position).getTotalMoeny().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                intent.setClass(getApplicationContext(), CurrencyDetailsActivity.class);
                startActivity(intent);

            }
        });

    }




    @Override
    protected void initUiAndListener() {


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
}
