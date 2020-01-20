package com.eex.mine.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.mine.frgament.CurrentFrament;
import com.eex.mine.frgament.HistoryFrament;
import com.eex.mine.frgament.LatesDealFrament;
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
 * @Package: com.overthrow.mine.activity
 * @ClassName: EntrustActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/4 14:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/4 14:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EntrustActivity extends BaseActivity {

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


    private String[] titles = {"当前委托",
            "历史委托",
            "成交记录",
    };
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {

        return R.layout.activity_entrust;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle(getActivity().getResources().getString(R.string.weotuo));
    }

    @Override
    protected void initUiAndListener() {

        int flag = 0;
        int index = 0;

        //当前
        fragments.add(new CurrentFrament());
        //历史
        fragments.add(new HistoryFrament());
        //最新成交记录
        fragments.add(new LatesDealFrament());


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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
