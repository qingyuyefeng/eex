package com.eex.market.frgament.donation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.EmptyView;
import com.eex.home.weight.AnimUtil;
import com.eex.market.adpater.CurrentFramentAdapter;
import com.eex.market.adpater.StopAdapter;
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
 * @ClassName: CurrentEntrustmentFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 16:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 当前委托
 */
public class CurrentEntrustmentFrament extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {


    private static final String TAG = "CurrentEntrustmentFrame";
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
    private CurrentFramentAdapter adapter;

    private ArrayList<Stoploss> data = new ArrayList<>();
    private StopAdapter stopAdapter;

    /**
     * PopupWindow
     */
    private View view;
    private PopupWindow mPopupWindow;
    private AnimUtil animUtil;

    private String LLtype = "1";

    private View popView;
    private Button sure;
    private Button cancel;
    private int weight;
    private int height;
    private PopupWindow popupWindow;

    @Override
    protected void lazyLoad() {
        //我的委托-当前委托
        net();

    }
    @Override
    protected void refreshData(Bundle savedInstanceState) {

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
        hashMap.put("delStatus", "1,2");

        ApiFactory.getInstance()
                .getDelegationList(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData().getResultList() != null) {
                        list.clear();
                        list.addAll(pageData.getData().getResultList());
                        adapter.notifyDataSetChanged();

                    } else {

                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new CurrentFramentAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setEmptyView(new EmptyView(getActivity()));


        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tx_no1:

                        popView = View.inflate(getActivity(), R.layout.popwind_theorder, null);

                        sure = (Button) popView.findViewById(R.id.button_sure);
                        cancel = (Button) popView.findViewById(R.id.button_cancel);

                        //获取屏幕宽高
                        weight = getResources().getDisplayMetrics().widthPixels;
                        height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

                        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
                        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
                        popupWindow.setFocusable(true);
                        //点击外部popueWindow消失
                        popupWindow.setOutsideTouchable(true);


                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });

                        sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("order", list.get(position).getOrderNo() + "");
                                hashMap.put("coinCode", list.get(position).getCoinCode() + "");
                                hashMap.put("fixPriceCoinCode", list.get(position).getFixPriceCoinCode() + "");


                                ApiFactory.getInstance()
                                        .cancelDelegation(kv.decodeString("tokenId"), hashMap)
                                        .compose(RxSchedulers.io_main())
                                        .subscribe(data -> {

                                            if (data.isStauts() == true) {
                                                list.remove(position);
                                                adapter.notifyItemRemoved(position);
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                                popupWindow.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }, throwable -> {

                                        });
                            }
                        });

                        popupWindow.dismiss();

                        //popupWindow出现屏幕变为半透明
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getActivity().getWindow().setAttributes(lp);
                        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 50);
                        BackgroudAlpha((float) 0.5);
                        popupWindow.setOnDismissListener(new popupwindowdismisslistener());
                        break;

                    default:
                        break;

                }
            }
        });

    }


    /**
     * item＋item里的控件点击监听事件
     * <p>
     * position - 1 :是因为AlertDialog 从第一个开始算 不是从0开始， 所以必须-1 不减就会报数据下标越界错误
     */
    private StopAdapter.OnItemClickListener ItemClickListenerdata = new StopAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()) {
                case R.id.tx_no:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getActivity().getResources().getString(R.string.dangqianwt)).setMessage(getActivity().getResources().getString(R.string.isyeswt)).setNegativeButton(getStateView().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @SuppressLint("CheckResult")
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {

                            try {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("orderNo", list.get(position).getOrderNo());

                                ApiFactory.getInstance()
                                        .cancelStoploss(kv.decodeString("tokenId"), hashMap)
                                        .compose(RxSchedulers.io_main())
                                        .subscribe(data -> {

                                            if (data.isStauts() == true) {
                                                list.remove(position);
                                                Log.e(TAG, "onClick: " + "111111111111111111111");
                                                stopAdapter.notifyItemRemoved(position);
                                                stopAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }, throwable -> {

                                        });
                            } catch (Exception e) {

                            }


                        }
                    }).setPositiveButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };


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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_frament, null);
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

                        if (p == 1) {
                            data = new ArrayList<>();
                            data.clear();
                        }
                        if (pageData.getData().getResultList() != null) {
                            data.clear();
                            data.addAll(pageData.getData().getResultList());
                        }

                        setViewInit(data);
                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    private void setViewInit(ArrayList<Stoploss> data) {

        stopAdapter = new StopAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(stopAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        stopAdapter.setEmptyView(new EmptyView(getActivity()));


        // 设置item及item中控件的点击事件
        stopAdapter.setOnItemClickListener(ItemClickListenerdata);
    }


    @Override
    public void onRefresh() {
        page++;

        net();

    }

    @Override
    public void onResume() {
        super.onResume();
        net();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tx_no1:

                View popView = View.inflate(getActivity(), R.layout.popwind_theorder, null);


                Button sure = (Button) popView.findViewById(R.id.button_sure);
                Button cancel = (Button) popView.findViewById(R.id.button_cancel);

                //获取屏幕宽高
                int weight = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

                final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
                popupWindow.setAnimationStyle(R.style.anim_popup_dir);
                popupWindow.setFocusable(true);
                //点击外部popueWindow消失
                popupWindow.setOutsideTouchable(true);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("order", list.get(position).getOrderNo() + "");
                        hashMap.put("coinCode", list.get(position).getCoinCode() + "");
                        hashMap.put("fixPriceCoinCode", list.get(position).getFixPriceCoinCode() + "");


                        ApiFactory.getInstance()
                                .cancelDelegation(kv.decodeString("tokenId"), hashMap)
                                .compose(RxSchedulers.io_main())
                                .subscribe(data -> {

                                    if (data.isStauts() == true) {
                                        list.remove(position);
                                        adapter.notifyItemRemoved(position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                        popupWindow.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }, throwable -> {

                                });
                    }
                });

                popupWindow.dismiss();

                //popupWindow出现屏幕变为半透明
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
                popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 50);
                BackgroudAlpha((float) 0.5);
                popupWindow.setOnDismissListener(new popupwindowdismisslistener());
                break;

            default:
                break;
        }

    }

    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = getActivity().getWindow().getAttributes();
        l.alpha = alpha;
        getActivity().getWindow().setAttributes(l);
    }

    /**
     * 点击其他部分popwindow消失时，屏幕恢复透明度
     */
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            BackgroudAlpha((float) 1);
        }
    }
}
