package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.adpater.RechargeBAListAdapter;
import com.eex.assets.bean.Bilistdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

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
 * @Package: com.overthrow.mine.activity
 * @ClassName: SearchTheCoinActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/8/30 10:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/30 10:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SearchTheCoinActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
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
    @BindView(R.id.search_include)
    LinearLayout searchInclude;
    /**
     *
     */
    @BindView(R.id.listView)
    ListView listView;


    private boolean include = true;

    private RechargeBAListAdapter adapter;
    private ArrayList<Bilistdata> list = new ArrayList<>();
    private ArrayList<Bilistdata> newList = new ArrayList<Bilistdata>();


    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searchthecoin;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle(getActivity().getResources().getString(R.string.xunibi));
        type = getIntent().getStringExtra("Type");

        comtitlebar.setImageView(R.drawable.sousuo2x);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (include == true) {
                    searchInclude.setVisibility(View.GONE);
                    include = false;
                } else {
                    include = true;
                    searchInclude.setVisibility(View.VISIBLE);
                }
            }
        });


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
                        Toast.makeText(SearchTheCoinActivity.this, "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
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

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "0");
        ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(SearchTheCoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SearchTheCoinActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }

                    if (data.getData() != null) {
                        list.clear();
                        list = data.getData();
                        setView(list);
                    } else {

                    }
                }, throwable -> {

                });

    }

    private void setView(ArrayList<Bilistdata> list) {
        listView.setFocusable(false);
        adapter = new RechargeBAListAdapter(SearchTheCoinActivity.this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


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

    @OnClick({R.id.comtitlebar, R.id.header_return_image, R.id.header_return_RL, R.id.header_campaign_search_editText, R.id.main_header_campaign_search1, R.id.main_header_search_qingkong, R.id.ll_base_toolber, R.id.search_include})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
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

                if (include == false) {
                    searchInclude.setVisibility(View.GONE);
                    include = true;
                } else {
                    include = false;
                    searchInclude.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_base_toolber:
                break;
            case R.id.search_include:
                break;
            default:
                break;
        }
    }
}
