package com.eex.market.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.market.adpater.MeChoiceAdapter;

import com.eex.market.bean.MeChoiceListvo;

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
 * @Package: com.overthrow.market.activity
 * @ClassName: MeChoiceActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/21 9:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 9:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 收藏列表
 */
public class MeChoiceActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.ll)
    LinearLayout ll;
    /**
     *
     */
    @BindView(R.id.reckeView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.ck_lonc)
    CheckBox ckLonc;
    /**
     *
     */
    @BindView(R.id.LL_ck)
    LinearLayout LLCk;
    /**
     *
     */
    @BindView(R.id.LL_delete)
    LinearLayout LLDelete;
    /**
     *
     */
    @BindView(R.id.ll1)
    LinearLayout ll1;

    /**
     * @return
     */


    private ArrayList<MeChoiceListvo> list = new ArrayList<>();

    private MeChoiceAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_choice;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        net();
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageSize", "100");
        hashMap.put("pageNo", "1");
        ApiFactory.getInstance()
                .getDealPairCollectionList(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    try {
                        if (data.isStauts() == true) {

                            list.clear();
                            list.addAll(data.getData().getResultList());
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setCktype(false);
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {

                });

    }

    /**
     * @param dealPair
     */
    private void getIndexMaketList(String dealPair) {

    }

    @Override
    protected void initUiAndListener() {

        adapter = new MeChoiceAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (list.get(position).isCktype()) {
                    list.get(position).setCktype(false);
                } else {
                    list.get(position).setCktype(true);
                }
                adapter.setNewData(list);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.ll, R.id.ck_lonc, R.id.LL_ck, R.id.LL_delete, R.id.ll1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:

                finish();
                break;
            case R.id.ll:
                break;
            case R.id.ck_lonc:
                break;
            case R.id.LL_ck:
                break;
            case R.id.LL_delete:
                break;
            case R.id.ll1:
                break;

            default:
                break;
        }
    }
}
