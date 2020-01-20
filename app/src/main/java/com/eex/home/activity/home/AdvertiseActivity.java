package com.eex.home.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.home.fragment.PurchaseFrament;
import com.eex.home.fragment.SellFrament;

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
 * @ClassName: AdvertiseActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 16:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 16:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 发布广告
 */
public class AdvertiseActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
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

    String[] titles = {"购买", "出售"};
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertise;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle("发布广告");

        comtitlebar.setRightText("我的广告", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(AdvertiseActivity.this,MyAdverActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initUiAndListener() {

        //购买
        fragments.add(new PurchaseFrament());
        //出售
        fragments.add(new SellFrament());
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        xTablayout.setupWithViewPager(viewPager);

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
}
