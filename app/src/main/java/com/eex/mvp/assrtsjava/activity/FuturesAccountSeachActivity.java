package com.eex.mvp.assrtsjava.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.EmptyView;
import com.eex.common.view.NewComTitleBar;
import com.eex.mvp.assrtsjava.adapter.FuturesAccountAdapter;
import com.eex.mvp.assrtsjava.bean.Assets;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: FuturesAccountSeachActivity
 * @Description: 期约账户
 * @Author: hcj
 * @CreateDate: 2019/12/24 15:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 15:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * FuturesAccountDetailsActivity
 */

public class FuturesAccountSeachActivity extends BaseActivity {


    @BindView(R.id.search_comm_titile)
    NewComTitleBar searchCommTitile;
    @BindView(R.id.search_comm_edit)
    EditText searchCommEdit;
    @BindView(R.id.search_comm_back)
    TextView searchCommBack;
    @BindView(R.id.search_comm_recyclerview)
    RecyclerView searchCommRecyclerview;
    @BindView(R.id.search_comm)
    LinearLayout searchComm;


    private ArrayList<Assets> list = new ArrayList<>();
    private List<Assets> mNewList = new ArrayList<Assets>();
    private FuturesAccountAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_commseach;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        net();
        init();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .assets(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getData() != null) {

                        list.clear();
                        list = data.getData();
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {


                });
    }


    private void init() {
        searchCommEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!searchCommEdit.getText().toString().trim().equals("")) {
                    if (list == null || list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    } else {

                        mNewList.clear();
                        String sq = searchCommEdit.getText().toString().trim();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCoinCode().contains(sq.toUpperCase())) {
                                mNewList.add(list.get(i));
                            }
                            setView();
                        }

                    }

                } else {
                    mNewList = new ArrayList<Assets>();
                    mNewList.clear();
                    setView();
                }

            }




            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void setView() {
        adapter = new FuturesAccountAdapter(mNewList);
        searchCommRecyclerview.setHasFixedSize(true);
        searchCommRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        searchCommRecyclerview.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
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

    @OnClick({R.id.search_comm_titile, R.id.search_comm_edit, R.id.search_comm_back, R.id.search_comm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_comm_titile:
                break;
            case R.id.search_comm_edit:
                net();
                break;
            case R.id.search_comm_back:
                finish();
                break;

            case R.id.search_comm:
                break;
        }
    }
}