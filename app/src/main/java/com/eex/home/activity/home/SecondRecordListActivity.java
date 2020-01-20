package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.adapter.SecondRecordListAdapter;
import com.eex.home.bean.MoneySearc;
import com.eex.home.bean.Page;
import com.eex.home.bean.SecondKillRecord;
import com.eex.home.bean.SecondRecordlist;
import com.eex.home.weight.LiftPopup;
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
 * @ClassName: SecondRecordListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 11:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 11:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SecondRecordListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     *
     */
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

    private SecondRecordListAdapter adapter;
    private ArrayList<MoneySearc> list = new ArrayList<>();
    private ArrayList<SecondKillRecord> secondKillRecords = new ArrayList<>();
    private ArrayList<SecondRecordlist> recordlists = new ArrayList<>();

    private LiftPopup liftPopup;
    private TopRigitMiddlePopup popup;

    public static ImageView img_biname, img_type;
    public static TextView tx_BiName;
    public static TextView text_type;

    private List<TopPopupVO> top;


    private int pageNo = 1;
    private int pageSize = 15;
    private int mShowType = 1;

    private ArrayList<Page> data = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("秒杀记录");

        //获取屏幕的宽和高
        getScreenPixels();
        //获取秒杀记录
        getSecondKillRecordPage();
        //右边下拉列表
        net();
        //
        getBiListData();
    }

    @SuppressLint("CheckResult")
    private void getBiListData() {
        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getAllCoinCode(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data1 -> {

                    if (data1.getData()!=null){
                        recordlists.clear();
                        recordlists.addAll(data1.getData());
                        SecondRecordlist one = new SecondRecordlist();
                        one.setCoinCode("全部币种");
                        recordlists.add(0, one);
                    }else {
                        t(data1.getMsg());
                    }
                }, throwable -> {

                });
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

    /**
     * 获取秒杀记录
     */
    @SuppressLint("CheckResult")
    private void getSecondKillRecordPage() {

        HashMap<String, String> hashMap = new HashMap<>();

        if (txBiName.getText().toString().trim().equals("全部币种")) {

        } else {
            hashMap.put("coinCode", txBiName.getText().toString().trim());
        }
        hashMap.put("pageNo", pageNo + "");
        hashMap.put("pageSize", pageSize + "");
        String type = textType.getText().toString().trim();

        if (type.equals("全部状态")) {

        } else if (type.equals("已锁定")) {
            hashMap.put("state", "1");
        } else if (type.equals("已解除锁定")) {
            hashMap.put("state", "4");
        } else if (type.equals("待审核")) {
            hashMap.put("state", "3");
        } else if (type.equals("审核不通过")) {
            hashMap.put("state", "5");
        }
        ApiFactory.getInstance()
                .getSecondKillRecordPage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getData().getPageNo() * pageSize >= pageData.getData().getTotalCount()) {
                        SwipeUtil.loadCompleted(swipeRefresh);
                    } else {
                        pageNo++;
                        SwipeUtil.loadCompleted(swipeRefresh);
                    }
                    if (pageData.getData().getPageNo() > 1) {
                        secondKillRecords.clear();
                        secondKillRecords.addAll(pageData.getData().getResultList());
                        adapter.addData(secondKillRecords);
                    }

                    if (pageData.getData().getResultList() != null) {


                        secondKillRecords.clear();
                        secondKillRecords.addAll(pageData.getData().getResultList());
                        pageNo = 2;


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }


    /**
     * 右边下拉列表
     */
    private void net() {
        top = new ArrayList<TopPopupVO>();
        TopPopupVO bean = new TopPopupVO();
        bean.setName("全部状态");
        top.add(bean);
        TopPopupVO bean1 = new TopPopupVO();
        bean1.setName("已锁定");
        top.add(bean1);
        TopPopupVO bean3 = new TopPopupVO();
        bean3.setName("待审核");
        top.add(bean3);
        TopPopupVO bean4 = new TopPopupVO();
        bean4.setName("已解除锁定");
        top.add(bean4);
        TopPopupVO bean5 = new TopPopupVO();
        bean5.setName("审核不通过");
        top.add(bean5);
    }

    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


        adapter = new SecondRecordListAdapter(secondKillRecords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (kv.decodeString("accountPassWord") == null) {
                    t("请设置交易密码后操作");
                } else {
                    intent.putExtra("type", secondKillRecords.get(position).getState() + "");
                    intent.putExtra("time", secondKillRecords.get(position).getEndDay());
                    intent.putExtra("id", secondKillRecords.get(position).getId());
                    intent.putExtra("newPrice", secondKillRecords.get(position).getNewPrice().stripTrailingZeros().toPlainString());
                    intent.setClass(getApplicationContext(), SecondListDataActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


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

    @OnClick({R.id.comtitlebar, R.id.ll_Biname, R.id.ll_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.ll_Biname:
                liftPopup = new LiftPopup(SecondRecordListActivity.this, screenW, screenH, onItemClickListener, recordlists, 3);
                liftPopup.show(llBiname);

                break;
            case R.id.ll_type:
                popup = new TopRigitMiddlePopup(SecondRecordListActivity.this, screenW, screenH, onItemClickListener1, (ArrayList<TopPopupVO>) top, 3);
                popup.show(llType);


                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取秒杀记录
        getSecondKillRecordPage();
    }


    @Override
    public void onRefresh() {
        mShowType = 1;
        getSecondKillRecordPage();
    }

    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            tx_BiName.setText(recordlists.get(position).getCoinCode());
            getMoneyList();
            liftPopup.dismiss();
        }
    };


    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener1 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("--onItemClickListener--:");
            textType.setText(top.get(position).getName());
            getMoneyList();
            popup.dismiss();
        }

    };

    @SuppressLint("CheckResult")
    private void getMoneyList() {
        HashMap<String, String> hashMap = new HashMap<>();

        if (txBiName.getText().toString().trim().equals("全部币种")) {

        } else {
            hashMap.put("coinCode", txBiName.getText().toString().trim());
        }
        hashMap.put("pageNo", "1");
        hashMap.put("pageSize", "100");
        String type = textType.getText().toString().trim();

        if (type.equals("全部状态")) {

        } else if (type.equals("已锁定")) {
            hashMap.put("state", "1");
        } else if (type.equals("已解除锁定")) {
            hashMap.put("state", "4");
        } else if (type.equals("待审核")) {
            hashMap.put("state", "3");
        } else if (type.equals("审核不通过")) {
            hashMap.put("state", "5");
        }
        ApiFactory.getInstance()
                .getSecondKillRecordPage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getData().getResultList() != null) {
                        secondKillRecords.clear();
                        secondKillRecords.addAll(pageData.getData().getResultList());

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });

    }
}
