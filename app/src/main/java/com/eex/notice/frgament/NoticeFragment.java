package com.eex.notice.frgament;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.base.Data;
import com.eex.common.util.SwipeUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.common.view.EmptyView;
import com.eex.notice.activity.IndustryActivity;
import com.eex.notice.activity.NoticeActivity;
import com.eex.notice.adpater.NoticeAdapter;
import com.eex.notice.bean.NoticeBean;

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
 * @Package: com.overthrow.notice.frgament
 * @ClassName: NoticeFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/23 11:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 11:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NoticeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "NoticeFragment";
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
    /**
     *
     */
    Unbinder unbinder;

    private NoticeAdapter adapter;
    private ArrayList<NoticeBean> list = new ArrayList<>();


    @Override
    protected void lazyLoad() {

    }



    @Override
    protected void refreshData(Bundle savedInstanceState) {

        net();

        comtitlebar.setBackGone();
        comtitlebar.setTitle("新闻公告");

    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("website", "cn");
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance().
                listtype(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.<Data<ArrayList<NoticeBean>>>io_main())
                .subscribe(data -> {
                    swipeRefresh.setRefreshing(false);
                    dialog.dismiss();
                    if (data.getCode() == 200) {


                        ArrayList<NoticeBean> noticeBeans = data.getData();
                        list.clear();
                        list.addAll(noticeBeans);


                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCategoryName().equals("官方公告") || list.get(i).getCategoryName().equals("关于我们") || list.get(i).getCategoryName().equals("帮助中心")) {
                                list.remove(i);
                            }

                        }
                        adapter.notifyDataSetChanged();

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {
                    swipeRefresh.setRefreshing(false);
                    dialog.dismiss();
                });
    }

    @Override
    protected void initUiAndListener() {
        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        adapter = new NoticeAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(new EmptyView(getActivity()));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                intent.putExtra("id", list.get(position).getId() + "");
                intent.putExtra("name", list.get(position).getCategoryName() + "");

                if (list.get(position).getCategoryName().equals("行业快讯")) {
                    intent.setClass(getActivity(), IndustryActivity.class);
                } else {
                    intent.setClass(getActivity(), NoticeActivity.class);
                }
                startActivity(intent);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice;
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

    @Override
    public void onRefresh() {
        net();
    }
}
