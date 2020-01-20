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
import com.eex.assets.adpater.AearchCurrAdapter;
import com.eex.assets.bean.Bilistdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.EmptyView;
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
 * @Package: com.overthrow.assets.activity
 * @ClassName: AearchCurrActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 12:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 12:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AearchCurrActivity extends BaseActivity {


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
    /**
     *
     */
    private AearchCurrAdapter adapter;
    private ArrayList<Bilistdata> list = new ArrayList<>();
    private ArrayList<Bilistdata> newList = new ArrayList<Bilistdata>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_aearch_curr;
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
                        Toast.makeText(AearchCurrActivity.this, "网络繁忙请稍后再试", Toast.LENGTH_SHORT).show();
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

    private void setView(ArrayList<Bilistdata> newList) {

        adapter = new AearchCurrAdapter(R.layout.item_rechargba, newList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                isName(position);
            }
        });
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "1");

        ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {


                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(AearchCurrActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(AearchCurrActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }


                    list = new ArrayList<>();
                    if (data.getData() != null) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @SuppressLint("CheckResult")
    private void isName(int position) {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == false) {
                    } else {

                        try {
                            if (data.getData().getLevel().equals(1)) {
                                Toast.makeText(getActivity(), "请完成实名等级2认证后操作", Toast.LENGTH_SHORT).show();

                            } else if (data.getData().getLevel().equals(2)) {
                                intent.putExtra("Bname", newList.get(position).getCoinCode());
                                intent.putExtra("currencyRate", newList.get(position).getCurrencyRate().stripTrailingZeros().toPlainString());
                                intent.putExtra("fixedRate", newList.get(position).getFixedRate().stripTrailingZeros().toPlainString());
                                intent.putExtra("coinMax", newList.get(position).getMaxNum().stripTrailingZeros().toPlainString());
                                intent.putExtra("coinMin", newList.get(position).getMinNum().stripTrailingZeros().toPlainString());
                                intent.setClass(getActivity(), CurrencyActivity.class);
                                getActivity().startActivity(intent);
                            } else if (data.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    intent.putExtra("Bname", newList.get(position).getCoinCode() + "");
                                    intent.putExtra("currencyRate", newList.get(position).getCurrencyRate().stripTrailingZeros().toPlainString() + "");
                                    intent.putExtra("fixedRate", newList.get(position).getFixedRate().stripTrailingZeros().toPlainString() + "");
                                    intent.putExtra("coinMax", newList.get(position).getMaxNum().stripTrailingZeros().toPlainString() + "");
                                    intent.putExtra("coinMin", newList.get(position).getMinNum().stripTrailingZeros().toPlainString() + "");
                                    intent.setClass(AearchCurrActivity.this, CurrencyActivity.class);
                                    getActivity().startActivity(intent);
                                } else {
                                    Toast.makeText(AearchCurrActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AearchCurrActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }

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
