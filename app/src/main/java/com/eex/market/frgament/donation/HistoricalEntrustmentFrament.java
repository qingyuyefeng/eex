package com.eex.market.frgament.donation;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.EmptyView;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.weight.AnimUtil;
import com.eex.market.adpater.HistoryFramentAdapter;
import com.eex.market.adpater.StopHistAdapter;
import com.eex.market.bean.Delegation;
import com.eex.market.bean.Stoploss;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
 * @Package: com.overthrow.market.frgament
 * @ClassName: HistoricalEntrustmentFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 16:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 历史委托
 */
public class HistoricalEntrustmentFrament extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
    @BindView(R.id.tx_Name)
    TextView txName;
    /**
     *
     */
    @BindView(R.id.img_dialog_type)
    ImageView imgDialogType;
    /**
     *
     */
    @BindView(R.id.LL_Fixed_price)
    LinearLayout LLFixedPrice;
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
    /**
     *
     */
    Unbinder unbinder;


    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 50;

    private ArrayList<Delegation> list = new ArrayList<>();
    private HistoryFramentAdapter adapter;

    private ArrayList<Stoploss> data = new ArrayList<>();
    private StopHistAdapter stopHistAdapter;

    private View view;
    private PopupWindow mPopupWindow;
    private AnimUtil animUtil;
    private String LLtype = "1";

    @Override
    protected void lazyLoad() {

        if (kv.decodeString("username") == null) {

            intent.putExtra("flage", "2");
            intent.setClass(getContext(), LoginActivity.class);
            startActivity(intent);

        }
        //我的委托-当前委托
        net();
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (kv.decodeString("username") == null) {

            intent.putExtra("flage", "2");
            intent.setClass(getContext(), LoginActivity.class);
            startActivity(intent);

        }
        //我的委托-当前委托
        net();

    }

    /**
     * 我的委托-当前委托
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("pageSize", size.toString());
        hashMap.put("pageNo", p.toString());
        hashMap.put("delStatus", "3,4,5");

        ApiFactory.getInstance()
                .getDelegationList(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getCode() == 200) {

                        list.clear();
                        list.addAll(pageData.getData().getResultList());
                        adapter.notifyDataSetChanged();

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


        adapter = new HistoryFramentAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        stopHistAdapter = new StopHistAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entrusment_listview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tx_Name, R.id.img_dialog_type, R.id.LL_Fixed_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tx_Name:
                break;
            case R.id.img_dialog_type:
                break;
            case R.id.LL_Fixed_price:

                showPop();

                break;
            default:
                break;

        }
    }

    /**
     *
     */
    private void showPop() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_frament, null);
        mPopupWindow = new PopupWindow(getActivity());
        animUtil = new AnimUtil();
        // 设置布局文件
        mPopupWindow.setContentView(view);
        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
//        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(LLFixedPrice, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        //限单价
        TextView tv_1 = (TextView) view.findViewById(R.id.tv_1);
        //市价单
        TextView tv_2 = (TextView) view.findViewById(R.id.tv_2);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = 20;
                p = 1;
                LLtype = "1";
                txName.setText("委托类型：普通委托");
                mPopupWindow.dismiss();
                //普通委托
                net();
            }
        });
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = 20;
                p = 1;
                LLtype = "2";
                txName.setText("委托类型：止盈止损");
                mPopupWindow.dismiss();
                //止盈止损
                init();
            }
        });

    }

    /**
     * 当前委托记录止盈止损
     */
    @SuppressLint("CheckResult")
    private void init() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageSize", size.toString());
        hashMap.put("pageNo", p.toString());
        hashMap.put("delStatus", "0");
        ApiFactory.getInstance()
                .getStoplossList(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getCode() == 200) {

                        data.clear();
                        data.addAll(pageData.getData().getResultList());
                        stopHistAdapter.notifyDataSetChanged();

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    @Override
    public void onRefresh() {
        page++;
        size=size+10;
        net();

    }

    @Override
    public void onResume() {
        super.onResume();
        net();
    }
}
