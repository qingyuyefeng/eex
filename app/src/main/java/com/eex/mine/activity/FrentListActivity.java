package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.mine.adpater.FrenListAdapter;
import com.eex.mine.bean.Frient;

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
 * @ClassName: FrentListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 11:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 11:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 * 已邀请朋友
 */
public class FrentListActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_me)
    TextView txMe;
    /**
     *
     */
    @BindView(R.id.tx_tofrent)
    TextView txTofrent;
    /**
     *
     */
    @BindView(R.id.frentRecyClerView)
    ListView frentRecyClerView;
    /**
     *
     */
    private String UsName;


    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 100;
    private ArrayList<Frient> list = new ArrayList<Frient>();
    private FrenListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_frent_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (getIntent().getStringExtra("UsName") != null) {
            UsName = getIntent().getStringExtra("UsName");
            txMe.setText(CommonUtil.phoneView(UsName));
        }

        comtitlebar.setTitle("已邀请朋友");

        net();
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hasMap = new HashMap<String, String>();
        hasMap.put("pageSize", size.toString());
        hasMap.put("pageNo", p.toString());
        hasMap.put("userName",UsName);

        ApiFactory.getInstance()
                .viewinvitefriend(kv.decodeString("tokenId"),hasMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData().getResultList() != null) {
                        list.clear();
                        list.addAll(data.getData().getResultList());
                        setView(list);
                    }


                }, throwable -> {

                });
    }

    private void setView(ArrayList<Frient> list) {
        adapter = new FrenListAdapter(getActivity(),list);
        frentRecyClerView.setAdapter(adapter);
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

    @OnClick({R.id.comtitlebar, R.id.tx_me, R.id.tx_tofrent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_me:
                break;
            case R.id.tx_tofrent:
                break;
                default:break;
        }
    }
}
