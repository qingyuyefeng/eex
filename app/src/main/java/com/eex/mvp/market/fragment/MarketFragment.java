package com.eex.mvp.market.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.ActivityTaskManager;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.update.DownloadDialog;
import com.eex.common.util.CommonUtil;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.constant.Keys;
import com.eex.home.weight.MyDialog;
import com.eex.mvp.market.activity.MarketSearchActivity;

import com.tencent.bugly.crashreport.CrashReport;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
 * @Package: com.overthrow.mvp.market.fragment
 * @ClassName: MarketFragment
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 9:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 9:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class MarketFragment extends BaseFragment {

    @BindView(R.id.sousuo_iv)
    ImageView sousuoIv;
    @BindView(R.id.sousuo_tv)
    TextView sousuoTv;
    @BindView(R.id.sousuo_image)
    ImageView sousuoImage;
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.market)
    LinearLayout market;
    Unbinder unbinder;

    private MyDialog mMyDialog;
    private AlertDialog.Builder mDialog;

    private String[] titles = {"自选", "USDT交易区", "期货交易区"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private  int index = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.re_frament_trading_marke;
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {

        sousuoTv.setText(getActivity().getResources().getString(R.string.market));
//        isVersion();

    }





    @Override
    protected void initUiAndListener() {

        int flag = 1;
        fragments.add(new OptionalFragment());
        fragments.add(new USDTFragment());
        fragments.add(new FuturesFragment());

        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), fragments, flag == 1 ? titles : titles));
            xTablayout.setupWithViewPager(viewPager);
        }

        xTablayout.getTabAt(index).select();
    }


    @Override
    protected void lazyLoad() {
        if (getArguments() != null) {
            index = getArguments().getInt(Keys.TRANS_SELECT,0);
            xTablayout.getTabAt(index).select();
        }
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

    @OnClick({R.id.sousuo_iv, R.id.sousuo_tv, R.id.sousuo_image, R.id.market})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sousuo_iv:

                break;
            case R.id.sousuo_tv:
                break;
            case R.id.sousuo_image:
                intent = new Intent(getActivity(), MarketSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.market:
                break;
        }
    }
}
