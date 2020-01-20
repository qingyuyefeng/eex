package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eex.R;
import com.eex.assets.adpater.CurrencyAddresListAdapter;
import com.eex.assets.bean.AddressListData;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;

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
 * @ClassName: CurrencyAddresListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 16:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 16:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 钱包地址
 */
public class CurrencyAddresListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.title_LL)
    LinearLayout titleLL;
    /**
     *
     */
    @BindView(R.id.addBank_LL)
    LinearLayout addBankLL;
    /**
     *
     */
    @BindView(R.id.listView)
    ListView listView;

    /**
     *
     */
    @BindView(R.id.tx_noData)
    TextView txNoData;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private ArrayList<AddressListData> listData = new ArrayList<>();
    private CurrencyAddresListAdapter addresListAdapter;

    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 10;

    private String data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_currency_addres_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.qianbaodd));

        if (getIntent().getStringExtra("type") != null) {
            data = getIntent().getStringExtra("type");
        }

        net();

    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("pageSize", size.toString());
        requestParam.put("pageNo", p.toString());
        ApiFactory.getInstance()
                .coinaddress(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {
                        listData.clear();
                        listData.addAll(data.getData().getResultList());
                        setView(listData);
                    } else {
                        t(data.getMsg());
                    }


                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        net();
    }

    private void setView(ArrayList<AddressListData> listData) {

        addresListAdapter = new CurrencyAddresListAdapter(listData, CurrencyAddresListActivity.this);
        listView.setAdapter(addresListAdapter);
        addresListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.title_LL, R.id.addBank_LL, R.id.tx_noData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.title_LL:
                break;
            case R.id.addBank_LL:
                intent.setClass(CurrencyAddresListActivity.this, CurrencyAddressMoneyActivity.class);

                startActivity(intent);
                break;
            case R.id.tx_noData:
                break;

            default:
                break;
        }
    }


    @Override
    public void onRefresh() {
        net();
    }
}
