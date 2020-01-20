package com.eex.mvp.assrtsjava.fragment;

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

import com.eex.constant.Keys;
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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.fragment
 * @ClassName: AssetsFragment
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 13:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 13:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class AssetsFragment extends BaseFragment {

  @BindView(R.id.asset_xtab)
  XTabLayout assetXtab;
  @BindView(R.id.view_pager)
  ViewPager viewPager;
  Unbinder unbinder;
  private ArrayList<Fragment> fragments = new ArrayList<>();
  private int index = 0;

  @Override
  protected void refreshData(Bundle savedInstanceState) {

  }

  @Override
  protected void initUiAndListener() {
    int flag = 0;
    if (context != null) {
      String[] titles = {
          context.getResources().getString(R.string.assets_account),
          context.getResources().getString(R.string.assets_c2c_account),
          context.getResources().getString(R.string.assets_futures)
      };

      //交易账户
      fragments.add(new CommMoneyFrament());
      //c2c账户
      fragments.add(new C2CAccountFrament());

      fragments.add(new FuturesAccountFragment());

      if (viewPager.getAdapter() == null) {
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), fragments,
            flag == 0 ? titles : titles));
        assetXtab.setupWithViewPager(viewPager);
      }
      assetXtab.getTabAt(index).select();
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_assets_swipe_refresh;
  }

  @Override
  protected void lazyLoad() {
    if (getArguments() != null) {
      index = getArguments().getInt(Keys.TRANS_SELECT,0);
      assetXtab.getTabAt(index).select();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
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
