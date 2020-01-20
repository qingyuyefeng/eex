package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
 * @Package: com.overthrow.assets.activity
 * @ClassName: RechargeBAListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 11:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 11:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 充值虚拟币
 */
public class RechargeBAListActivity extends BaseActivity {


    private static final String TAG = "RechargeBAListActivity";
    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.listView)
    ListView listView;


    private String type;
    private int pageSize = 15;
    private int pageNo = 1;
    private RechargeBAListAdapter adapter;


    private ArrayList<Bilistdata> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_balist;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (getIntent().getStringExtra("Type") != null) {
            type = getIntent().getStringExtra("Type");
            comtitlebar.setTitle(getActivity().getResources().getString(R.string.xunibi));

            Log.e(TAG, "refreshData: "+ "1111111111111111111"+type );
        }


        comtitlebar.setTitle(getActivity().getResources().getString(R.string.xinibicngz));
        type = getIntent().getStringExtra("Type");
        Log.e(TAG, "refreshData: "+ "22222222222222222222222"+type );


        comtitlebar.setImageView(R.drawable.sousuo2x);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RechargeBAListActivity.this, AearchListActivity.class);
                startActivity(intent);
            }
        });

        net();
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "0");
        ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeBAListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RechargeBAListActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
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
        adapter = new RechargeBAListAdapter(RechargeBAListActivity.this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void initUiAndListener() {

        listView.setFocusable(false);
        adapter = new RechargeBAListAdapter(RechargeBAListActivity.this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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


}
