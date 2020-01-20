package com.eex.home.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.home.fragment.MeC2COrderList.C2CFramentAll;
import com.eex.home.fragment.MeC2COrderList.C2CFramentCancelled;
import com.eex.home.fragment.MeC2COrderList.C2CFramentCompleted;
import com.eex.home.fragment.MeC2COrderList.C2CFramentProcessing;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: MeC2COrderListActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 17:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 17:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 我的的订单
 *
 *
 */
public class MeC2COrderListActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.completed)
    ComTitleBar completed;
    /**
     *
     */
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    /**
     *
     */
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    private String[] titles = {"进行中",
            "已完成",
            "已取消",
            "全部",
    };
    private ArrayList<Fragment> fragments = new ArrayList<>();



    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_c2_corder_list;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        completed.setTitle("C2C订单");


    }

    @Override
    protected void initUiAndListener() {

        int flag = 0;
        int index = 0;
        //进行中
        fragments.add(new C2CFramentProcessing());
        //已完成
        fragments.add(new C2CFramentCompleted());
        //已取消
        fragments.add(new C2CFramentCancelled());
        //全部
        fragments.add(new C2CFramentAll());

        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments, flag == 0 ? titles : titles));
            xTablayout.setupWithViewPager(viewPager);
        }

        xTablayout.getTabAt(index).select();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.completed)
    public void onViewClicked() {
        finish();
    }
}
