package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.adpater.CurrencyListAdapter;
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
 * @ClassName: CurrencyListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 11:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 11:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 转出虚拟币
 */
public class CurrencyListActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    @BindView(R.id.gridview)
    GridView gridview;
    private CurrencyListAdapter adapter;
    private ArrayList<Bilistdata> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_currency_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.zhuanchu));

        comtitlebar.setImageView(R.drawable.sousuo2x);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setClass(CurrencyListActivity.this, AearchCurrActivity.class);
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
        hashMap.put("type", "1");

        ApiFactory.getInstance()
                .depositcoin(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {


                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(CurrencyListActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }

                    if (data.getData() != null) {

                        list.clear();
                        list.addAll(data.getData());
                        setView(list);
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {


                });
    }

    private void setView(ArrayList<Bilistdata> list) {
        adapter = new CurrencyListAdapter(getActivity(), list);
        gridview.setAdapter(adapter);
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
}
