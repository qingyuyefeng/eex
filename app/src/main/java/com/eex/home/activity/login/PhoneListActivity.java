package com.eex.home.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.PhoneList;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.PhoneListAdapter;
import com.eex.home.bean.PhoneListBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
 * @Package: com.overthrow.home.activity
 * @ClassName: PhoneListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/6 13:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 13:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 获取手机号码归属地
 */
public class PhoneListActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PhoneListAdapter adapter;
    private ArrayList<PhoneListBean> list = new ArrayList<>();

    private String name;
    private String code;


    @Override
    protected int getLayoutId() {

        return R.layout.activity_phone_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.area_selection));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            list = new ArrayList<>();
            list.clear();
            JSONArray phone = new JSONArray(PhoneList.phone);
            for (int i = 0; i < phone.length(); i++) {
                JSONObject jsonObject2 = phone.getJSONObject(i);
                PhoneListBean phoneListBean = new PhoneListBean();
                phoneListBean.setName(jsonObject2.getString("name"));
                phoneListBean.setCode(jsonObject2.getString("code"));
                list.add(phoneListBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new PhoneListAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                name = list.get(position).getName();
                code = list.get(position).getCode();
                intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("code", code);
                setResult(1000, intent);
                finish();
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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
