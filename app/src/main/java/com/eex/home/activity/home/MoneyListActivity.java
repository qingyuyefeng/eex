package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.activity.login.LoginActivity;

import com.eex.home.adapter.MoneyListAdapter;

import com.eex.home.bean.MoneySearc;
import com.eex.home.bean.PaningMoneyDetails;
import com.eex.home.weight.TopMiddlePopup;
import com.eex.home.weight.TopPopupVO;
import com.eex.home.weight.TopRigitMiddlePopup;

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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.activity.home
 * @ClassName: MoneyListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 20:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 20:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 理财记录
 */
public class MoneyListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_BiName)
    TextView txBiName;
    /**
     *
     */
    @BindView(R.id.img_biname)
    ImageView imgBiname;
    /**
     *
     */
    @BindView(R.id.ll_Biname)
    LinearLayout llBiname;
    /**
     *
     */
    @BindView(R.id.text_type)
    TextView textType;
    /**
     *
     */
    @BindView(R.id.img_type)
    ImageView imgType;
    /**
     *
     */
    @BindView(R.id.ll_type)
    LinearLayout llType;
    /**
     *
     */
    @BindView(R.id.ll_base_toolber)
    LinearLayout llBaseToolber;
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
    public static int screenW, screenH;

    private ArrayList<MoneySearc> list = new ArrayList<>();

    private TopMiddlePopup middlePopup;
    private TopRigitMiddlePopup popup;

    public static ImageView img_biname, img_type;
    public static TextView tx_BiName;
    public static TextView text_type;

    private List<TopPopupVO> top;


    private int pageNo = 1;
    private int pageSize = 15;

    private ArrayList<PaningMoneyDetails> paningMoneyDetails = new ArrayList<>();
    private MoneyListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        tx_BiName = txBiName;
        img_type = imgType;
        text_type = textType;
        img_biname = imgBiname;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.financial_records));

        //获取屏幕的宽和高
        getScreenPixels();
        //列表查询可理财的币种更新后接口
        getLockMoneyDetailCoin();
        //右边下拉列表
        net();
        //获取列表记录
        getMoneyList();
    }


    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

    @SuppressLint("CheckResult")
    private void getLockMoneyDetailCoin() {
        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getLockMoneyDetailCoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getCode() == 10002 || pageData.getCode() == 10001) {

                        intent.putExtra("type", "2");
                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }
                    if (pageData.getData() != null) {

                        list = pageData.getData();
                        MoneySearc zj = new MoneySearc();
                        zj.setCoinCode("全部");
                        zj.setId("0");
                        zj.setImgUrl("");
                        list.add(0, zj);


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    private void net() {

        top = new ArrayList<TopPopupVO>();
        TopPopupVO bean = new TopPopupVO();
        bean.setName("全部");
        top.add(bean);
        TopPopupVO bean1 = new TopPopupVO();
        bean1.setName("已锁仓");
        top.add(bean1);
        TopPopupVO bean2 = new TopPopupVO();
        bean2.setName("解仓待审核");
        top.add(bean2);
        TopPopupVO bean3 = new TopPopupVO();
        bean3.setName("已解仓");
        top.add(bean3);
        TopPopupVO bean4 = new TopPopupVO();
        bean4.setName("解仓审核不通过");
        top.add(bean4);
        TopPopupVO bean5 = new TopPopupVO();
        bean5.setName("部分已解仓");
        top.add(bean5);
    }


    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


        adapter = new MoneyListAdapter(paningMoneyDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {

                    //理财明细
                    case R.id.item_linear:
                        intent.putExtra("id", paningMoneyDetails.get(position).getId());
                        intent.setClass(MoneyListActivity.this, MoneyDetailsActivity.class);
                        startActivity(intent);
                        break;

                    //解仓
                    case R.id.btn_putType:
                        intent.putExtra("id", paningMoneyDetails.get(position).getId().toString());
                        intent.setClass(MoneyListActivity.this, StorehouseActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        //获取列表记录
        getMoneyList();
    }

    /**
     * 获取列表记录
     */
    @SuppressLint("CheckResult")
    private void getMoneyList() {

        HashMap<String, String> hashMap = new HashMap<>();

        if (txBiName.getText().toString().trim().equals("全部")) {

        } else {
            hashMap.put("coinCode", txBiName.getText().toString().trim());
        }
        hashMap.put("pageNo", pageNo + "");
        hashMap.put("pageSize", pageSize + "");
        String type = textType.getText().toString().trim();
        Log.e("name", txBiName.getText().toString().trim() + "---" + type);
        if (type.equals("全部")) {

        } else if (type.equals("已锁仓")) {
            hashMap.put("state", "1");
        } else if (type.equals("解仓待审核")) {
            hashMap.put("state", "2");
        } else if (type.equals("解仓审核不通过")) {
            hashMap.put("state", "4");
        } else if (type.equals("已解仓")) {
            hashMap.put("state", "3");
        } else if (type.equals("部分已解仓")) {
            hashMap.put("state", "5");
        }
        ApiFactory.getInstance()
                .findLockingRecordPage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (pageData.getData().getPageNo() > 1) {
                        paningMoneyDetails.clear();
                        paningMoneyDetails.addAll(pageData.getData().getResultList());
                        adapter.addData(paningMoneyDetails);
                        adapter.notifyDataSetChanged();
                    }

                    if (pageData.getData().getResultList() != null) {
                        paningMoneyDetails.clear();
                        paningMoneyDetails.addAll(pageData.getData().getResultList());
                        adapter.notifyDataSetChanged();

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    @Override
    public void onRefresh() {

        //获取屏幕的宽和高
        getScreenPixels();
        //列表查询可理财的币种更新后接口
        getLockMoneyDetailCoin();
        //右边下拉列表
        net();
        //获取列表记录
        getMoneyList();

    }

    @OnClick({R.id.comtitlebar, R.id.ll_Biname, R.id.ll_type, R.id.ll_base_toolber})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.ll_Biname:
                middlePopup = new TopMiddlePopup(MoneyListActivity.this, screenW, screenH, onItemClickListener, list, 3);
                middlePopup.show(llBiname);
                break;
            case R.id.ll_type:
                popup = new TopRigitMiddlePopup(MoneyListActivity.this, screenW, screenH, onItemClickListener1, (ArrayList<TopPopupVO>) top, 3);
                popup.show(llType);
                break;
            case R.id.ll_base_toolber:
                break;
            default:
                break;
        }
    }

    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            txBiName.setText(list.get(position).getCoinCode());
            getMoneyList();
            middlePopup.dismiss();
        }
    };
    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener1 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            textType.setText(top.get(position).getName());
            getMoneyList();
            popup.dismiss();
        }
    };


}
