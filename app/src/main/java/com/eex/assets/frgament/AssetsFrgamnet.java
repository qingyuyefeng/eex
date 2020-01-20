package com.eex.assets.frgament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseFragment;
import com.eex.common.view.ViewPagerFragmentAdapter;

import java.util.ArrayList;

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
 * @Package: com.overthrow.assets.frgament
 * @ClassName: AssetsFrgamnet
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/23 13:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 13:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AssetsFrgamnet extends BaseFragment {


    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    private String[] titles = {"交易账户", "C2C账户"};
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void lazyLoad() {


    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        int flag = 0;
        int index = 0;

        //交易账户
        fragments.add(new CommMoneyFrament());
        //c2c账户
        fragments.add(new C2CAccountFrament());


        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), fragments, flag == 0 ? titles : titles));
            xTablayout.setupWithViewPager(viewPager);
        }

        xTablayout.getTabAt(index).select();
    }

    @Override
    protected void initUiAndListener() {



    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tra_commonwealfruit;
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


}
