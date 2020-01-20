package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.GetCoinTradeConfigListAdapter;
import com.eex.home.bean.CoinfigList;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: GetCoinTradeConfigListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/18 14:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 14:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 交易币种
 */
public class GetCoinTradeConfigListActivity extends BaseActivity {

    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    @BindView(R.id.coin_List)
    RecyclerView recyclerView;


    private String type;

    private ArrayList<CoinfigList> list = new ArrayList<>();
    private ArrayList<CoinfigList> list1 = new ArrayList<>();
    private ArrayList<CoinfigList> list2 = new ArrayList<>();
    private GetCoinTradeConfigListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_coin_trade_config_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("交易币种");
        type = getIntent().getStringExtra("type");

        //c2c获取法币交易列表
        getCoinTradeConfigList();
    }

    /**
     * c2c获取法币交易列表
     */
    @SuppressLint("CheckResult")
    private void getCoinTradeConfigList() {

        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getCoinTradeConfigList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {

                    if (arrayListData.getData() != null) {
                        list.clear();
                        list.addAll(arrayListData.getData());
                        setHozriView(list);

                    } else {
                        t(arrayListData.getMsg());
                    }

                }, throwable -> {

                });

    }

    private void setHozriView(ArrayList<CoinfigList> list) {

        list1 = new ArrayList<>();
        list1.clear();
        list2 = new ArrayList<>();
        list2.clear();
        for (int i = 0;i<list.size();i++){
            if (list.get(i).getBuyStatus()==1){
                list1.add(list.get(i));
            }
            if (list.get(i).getSellStatus()==1){
                list2.add(list.get(i));
            }
        }


        if (type.equals("buy")) {
            adapter = new GetCoinTradeConfigListAdapter(list1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.setEmptyView(new EmptyView(getActivity()));
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    intent.putExtra("coin", list1.get(position).getTradeCoin());
                    setResult(2000, intent);
                    finish();
                }
            });
        } else if (type.equals("sell")) {
            adapter = new GetCoinTradeConfigListAdapter(list2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.setEmptyView(new EmptyView(getActivity()));
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    intent.putExtra("coin", list2.get(position).getTradeCoin());
                    setResult(2000, intent);
                    finish();
                }
            });
        }
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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //c2c获取法币交易列表
        getCoinTradeConfigList();
    }


}
