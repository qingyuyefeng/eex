package com.eex.mine.frgament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;
import com.eex.market.bean.Delegation;
import com.eex.mine.adpater.TransactionAdapter;

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
 * @Package: com.overthrow.mine.frgament
 * @ClassName: CurrentFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 15:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 15:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 当前委托
 */
public class CurrentFrament extends BaseFragment  implements  SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    /**
     * 从第多少条开始
     */
    private Integer p = 1;
    /**
     * 每页多少条数据
     */
    private Integer size = 50;
    private TransactionAdapter adapter;

    private ArrayList<Delegation> list = new ArrayList<>();

    @Override
    protected void lazyLoad() {
        if (kv.decodeString("tokenId") != null) {
            net();
        }

    }



    @Override
    protected void refreshData(Bundle savedInstanceState) {
        if (kv.decodeString("tokenId") != null) {
            net();
        }
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("pageSize", size.toString());
        requestParam.put("pageNo", p.toString());
        requestParam.put("delStatus", "1,2");
        ApiFactory.getInstance()
                .getDelegationList(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData().getResultList() != null) {
                        if (p == 1) {
                            list.clear();
                            list.addAll(data.getData().getResultList());
                        }

                        // 大于页数值允许加载更多
                        if (list.size() >= size) {

                        }


                        setlistView(list);
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                    SwipeUtil.loadCompleted(swipeRefresh);
                });

    }

    private void setlistView(ArrayList<Delegation> list) {

        listView.setFocusable(false);
        adapter = new TransactionAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        p = p.intValue() + 1;
    }

    @Override
    protected void initUiAndListener() {
        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tra_myzoe_mycampaign_radiogroup_listview;
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

        if (kv.decodeString("tokenId") != null) {
            size=size+10;
            net();
        }
    }
}
