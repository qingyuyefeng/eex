package com.eex.market.frgament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;

import com.eex.R;
import com.eex.common.base.BaseFragment;

import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.market.activity.MeChoiceActivity;
import com.eex.market.activity.SearchActivity;
import com.eex.market.frgament.market.CNYEFragment;
import com.eex.market.frgament.market.InnovationSectionFragment;
import com.eex.market.frgament.market.OptionalFragment;
import com.eex.market.frgament.market.PAXFragment;
import com.eex.market.frgament.market.USDTFragment;

import java.util.ArrayList;

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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.market.frgament
 * @ClassName: MarketFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/23 13:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 13:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MarketFragment extends BaseFragment {


    Unbinder unbinder;
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


    private String[] titles = {"自选", "PAX", "USDT", "CNYE", "创新版"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;

    @Override
    protected void lazyLoad() {



    }




    @Override
    protected void refreshData(Bundle savedInstanceState) {
        sousuoTv.setText(getActivity().getResources().getString(R.string.shichang_register));


        int flag = 2;
        int index = 2;


        //自选
        fragments.add(new OptionalFragment());
        //PAX
        fragments.add(new PAXFragment());
        //USDT
        fragments.add(new USDTFragment());
        //CNYE
        fragments.add(new CNYEFragment());

        //创新版交易区  这个x需要手动添加数据  后面4个 都是
        fragments.add(new InnovationSectionFragment());


        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), fragments, flag == 2 ? titles : titles));
            xTablayout.setupWithViewPager(viewPager);
        }

        xTablayout.getTabAt(index).select();
    }


    @Override
    protected void initUiAndListener() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_trading_marke;
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


    @OnClick({R.id.sousuo_iv, R.id.sousuo_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //收藏列表
            case R.id.sousuo_iv:
                if (kv.decodeString("tokenId") == null) {
                    Toast.makeText(getContext(), "请登录后操作", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(getContext(), MeChoiceActivity.class);
                    startActivity(intent);
                }
                break;
            //搜索
            case R.id.sousuo_image:
                intent.setClass(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
