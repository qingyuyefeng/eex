package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.adapter.SecondKillAdapter;
import com.eex.home.bean.SecondkillLsitvo;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: SecondkillActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 17:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 17:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 秒杀专区
 */
public class SecondkillActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SecondkillActivity";
    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * recyclerView
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    /**
     * 下拉刷新
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    private int mShowType = 1;

    private SecondKillAdapter adapter;
    private ArrayList<SecondkillLsitvo> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_secondkill;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle("秒杀专区");


        //获取秒杀列表
        getPage();

        comtitlebar.setImageView(R.drawable.cq_recharge_zd);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kv.decodeString("tokenId") == null) {
                    t(getActivity().getResources().getString(R.string.Please_log));
                } else {
                    //是否实名
                    isName();
                }
            }


        });
    }

    /**
     * 获取秒杀列表
     */
    @SuppressLint("CheckResult")
    private void getPage() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageSize", "10");
        hashMap.put("pageNo", mShowType + "");
        ApiFactory.getInstance()
                .getPage(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {
                        if (pageData.getData().getPageNo() == 1) {
                            list.clear();
                            list.addAll(pageData.getData().getResultList());
                            adapter.notifyDataSetChanged();
                        } else if (pageData.getData().getPageNo() != 1) {

                            list.clear();
                            list.addAll(pageData.getData().getResultList());
                            adapter.notifyDataSetChanged();
                        }


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void isName() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNo", mShowType + "");
        ApiFactory.getInstance()
                .getauthstauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (pageData.getCode() == 10002 || pageData.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("type", "1");
                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }
                    if (pageData.getCode() == 200) {
                        try {
                            if (pageData.getData().getLevel().equals(1)) {
                                t("请实名认证等级2后操作");
                            } else if (pageData.getData().getLevel().equals(2)) {
                                if (kv.decodeString("phone") != null) {
                                    intent.setClass(getApplicationContext(), SecondRecordListActivity.class);
                                    startActivity(intent);
                                } else {
                                    t("请绑定手机号后操作");
                                }
                            } else if (pageData.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null) {
                                    intent.setClass(getApplicationContext(), SecondRecordListActivity.class);
                                    startActivity(intent);
                                } else {
                                    t("请绑定手机号后操作");
                                }
                            }
                        } catch (Exception e) {
                            t(getActivity().getResources().getString(R.string.isname));
                        }

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

        adapter = new SecondKillAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        // 设置item及item中控件的点击事件
        adapter.setOnItemClickListener(ItemClickListener);

    }

    /**
     * item＋item里的控件点击监听事件
     */
    private SecondKillAdapter.OnItemClickListener ItemClickListener = new SecondKillAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {

            switch (v.getId()) {
                case R.id.item_secodkill:
                    mShowType++;
                    SwipeUtil.loadCompleted(swipeRefresh);
                    //获取秒杀列表
                    getPage();
                    adapter.notifyDataSetChanged();
                    adapter.loadMoreComplete();

                    break;

                case R.id.btn_go:
                    if (kv.decodeString("tokenId") == null) {
                        t(getActivity().getResources().getString(R.string.Please_log));
                    } else {

                        DataSM(list, position);
                    }

                    break;

                default:
                    break;
            }

        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

    /**
     * 是否实名
     *
     * @param list
     * @param position
     */
    @SuppressLint("CheckResult")
    private void DataSM(ArrayList<SecondkillLsitvo> list, int position) {
        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getauthstauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {

                        try {
                            if (pageData.getData().getLevel().equals(1)) {
                                t("请实名认证等级2后操作");
                            } else if (pageData.getData().getLevel().equals(2)) {
                                if (kv.decodeString("phone") != null && kv.decodeString("phone").equals("")) {
                                    intent.putExtra("id", list.get(position).getId() + "");
                                    intent.putExtra("Biname", list.get(position).getCoinName() + "");
                                    intent.putExtra("ImgUrl", list.get(position).getImgUrl() + "");
                                    intent.setClass(getApplicationContext(), SecondkillDetailsActivity.class);
                                    startActivity(intent);
                                } else {
                                    t("请绑定手机号后操作");
                                }
                            } else if (pageData.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null && kv.decodeString("phone").equals("")) {
                                    intent.putExtra("id", list.get(position).getId() + "");
                                    intent.putExtra("Biname", list.get(position).getCoinName() + "");
                                    intent.putExtra("ImgUrl", list.get(position).getImgUrl() + "");
                                    intent.setClass(getApplicationContext(), SecondkillDetailsActivity.class);
                                    startActivity(intent);
                                } else {
                                    t("请绑定手机号后操作");
                                }
                            }
                        } catch (Exception e) {
                            t(getActivity().getResources().getString(R.string.isname));
                        }


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
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

    @Override
    public void onRefresh() {
        //获取秒杀列表
        getPage();
    }
}
