package com.eex.common.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

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
 * @Package: com.overthrow.common.view
 * @ClassName: ViewPagerFragmentAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 10:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 10:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {


    private final String[] titls;
    private final ArrayList<Fragment> fragments;

    public ViewPagerFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titls) {
        super(fm);
        this.titls = titls;
        this.fragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titls[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
