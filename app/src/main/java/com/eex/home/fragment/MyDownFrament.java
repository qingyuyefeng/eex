package com.eex.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;

import com.eex.home.activity.home.EditMeActivity;
import com.eex.home.adapter.MyAdverAdapter;
import com.eex.home.bean.AdvertisingUser;
import com.eex.home.weight.MyDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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
 * @Package: com.overthrow.home.fragment
 * @ClassName: MyDownFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/30 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/30 16:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MyDownFrament extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    /**
     *
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     *
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    Unbinder unbinder;


    private int pageNo = 1;
    private int pageSize = 15;

    private ArrayList<AdvertisingUser> list = new ArrayList<>();
    private ArrayList<AdvertisingUser> list1 = new ArrayList<>();
    private MyAdverAdapter adapter;

    private MyDialog mMyDialog;
    private MyDialog mMyDialog1;


    @Override
    protected void lazyLoad() {

        net();
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        net();
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageSize", pageSize + "");
        hashMap.put("pageNo", pageNo + "");
        hashMap.put("shelfStatus", "0");

        ApiFactory.getInstance()
                .AdvertisingUserPage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data != null) {

                        list = new ArrayList<AdvertisingUser>();
                        list.clear();
                        if (data.getData().getPageNo() > 1) {
                            list1 = new ArrayList<AdvertisingUser>();
                            list1.clear();
                            list1.addAll(data.getData().getResultList());
                            adapter.addData(list1);
                        } else {

                            if (data.getData().getResultList() != null && data.getData().getResultList().size() != 0) {
                                list.addAll(data.getData().getResultList());
                                pageNo = 2;
                            }
                        }

                        setViewList(list);
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    private void setViewList(ArrayList<AdvertisingUser> list) {
        adapter = new MyAdverAdapter(R.layout.item_down_baner, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                Intent inetent = new Intent();
                switch (view.getId()) {
                    //编辑
                    case R.id.LLEditors:
                        inetent.putExtra("type", "sell");
                        if (list.get(position).getTradeType() == 1) {
                            inetent.putExtra("getTradeType", "卖");
                        } else {
                            inetent.putExtra("getTradeType", "买");
                        }
                        inetent.putExtra("MarginCoin", list.get(position).getMarginCoin());
                        inetent.putExtra("sever", list.get(position).getServiceRate().stripTrailingZeros().toPlainString());
                        inetent.putExtra("Margin", list.get(position).getMargin().stripTrailingZeros().toPlainString());
                        inetent.putExtra("id", list.get(position).getId());
                        inetent.putExtra("coin", list.get(position).getTradeCoin());
                        inetent.putExtra("reark", list.get(position).getRemark());
                        inetent.putExtra("price", list.get(position).getTradePrice().multiply(list.get(position).getPremium()).setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
                        inetent.setClass(getContext(), EditMeActivity.class);
                        startActivity(inetent);
                        break;
                    //上架
                    case R.id.LLLower:
                        View view1 = getActivity().getLayoutInflater().inflate(R.layout.dialog_me_down_up, null);
                        mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
                        mMyDialog.setCancelable(true);
                        mMyDialog.show();
                        Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
                        Button btn_yes = (Button) view1.findViewById(R.id.btn_yes);
                        btn_dimss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMyDialog.dismiss();
                            }
                        });
                        btn_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //c2c上架广告
                                UpBaner(list.get(position).getId(), position);
                                mMyDialog.dismiss();
                            }
                        });

                        break;
                    //删除
                    case R.id.LLdelete:
                        View view2 = getActivity().getLayoutInflater().inflate(R.layout.dialog_me_down_setele, null);
                        mMyDialog1 = new MyDialog(getActivity(), 0, 0, view2, R.style.DialogTheme);
                        mMyDialog1.setCancelable(true);
                        mMyDialog1.show();
                        Button btn_dimss1 = (Button) view2.findViewById(R.id.btn_dimss);
                        Button btn_yes1 = (Button) view2.findViewById(R.id.btn_yes);
                        btn_dimss1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMyDialog1.dismiss();
                            }
                        });
                        btn_yes1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //c2c删除广告
                                delete(list.get(position).getId(), position);
                                mMyDialog1.dismiss();
                            }
                        });

                        break;

                    default:
                        break;
                }
            }
        });
    }

    /**
     * c2c上架广告
     *
     * @param id
     * @param position
     */
    @SuppressLint("CheckResult")
    private void UpBaner(String id, int position) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiFactory.getInstance()
                .shelfAdvertising(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getCode() == 200) {
                        list.remove(position);
                        adapter.setNewData(list);
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }

    /**
     * c2c删除广告
     *
     * @param id
     * @param position
     */
    @SuppressLint("CheckResult")
    private void delete(String id, int position) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiFactory.getInstance()
                .deleteAdvertising(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getCode() == 200) {
                        list.remove(position);
                        adapter.setNewData(list);
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    protected void initUiAndListener() {
        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_my_up;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        net();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        net();
    }
}
