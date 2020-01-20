package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.mine.adpater.FrentMeAdapter;
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
 * @ClassName: FrentActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 10:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 10:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 已邀请朋友
 */
public class FrentActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    @BindView(R.id.frentRecyClerView)
    ListView frentRecyClerView;

    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 100;
    private ArrayList<Frient> list = new ArrayList<Frient>();
    private FrentMeAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_frent;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("已邀请朋友");

        net();
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hasMap = new HashMap<String, String>();
        hasMap.put("pageSize", size.toString());
        hasMap.put("pageNo", p.toString());
        hasMap.put("userName", kv.decodeString("username"));

        ApiFactory.getInstance()
                .viewinvitefriend(kv.decodeString("tokenId"), hasMap)
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

        adapter = new FrentMeAdapter(getActivity(), list);
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

    @OnClick({R.id.comtitlebar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;

            default:
                break;
        }
    }
}
