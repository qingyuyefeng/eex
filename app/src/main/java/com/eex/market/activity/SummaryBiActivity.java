package com.eex.market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.market.frgament.summarybi.CNYEFrament;
import com.eex.market.frgament.summarybi.EBTFragment;
import com.eex.market.frgament.summarybi.USDTSummaryFragment;

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
 * @Package: com.overthrow.market.activity
 * @ClassName: SummaryBiActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/27 15:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/27 15:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 虚拟币选择
 */
public class SummaryBiActivity extends BaseActivity {


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


    private String[] titles = {"USDT", "EBT", "CNYE",};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private String BiName = "BTC/USDT";
    private String BinameData = "BTC_USDT";
    private String JYB = "BTC";
    private String DJB = "USDT";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_summary_bi;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("虚拟币选择");


        comtitlebar.setImageView(R.drawable.sousuo2x);

        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(SummaryBiActivity.this, KlineSearchActivity.class);
                startActivityForResult(intent, 2000);
            }
        });

        net();
    }

    private void net() {


    }

    @Override
    protected void initUiAndListener() {


        //USDT
        fragments.add(new USDTSummaryFragment());
        //EBT
        fragments.add(new EBTFragment());
        //CNYE
        fragments.add(new CNYEFrament());

        viewPager.setAdapter(new ViewPagerFragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles));
        xTablayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2000){
            if (data!=null) {

                BiName = data.getStringExtra("name");
                BinameData = data.getStringExtra("data");
                //BTC
                JYB = data.getStringExtra("JYBi");
                //USDT
                DJB = data.getStringExtra("DJBi");


                intent.putExtra("name",BiName);
                intent.putExtra("data",BinameData);
                intent.putExtra("JYBi",JYB);
                intent.putExtra("DJBi",DJB);
                setResult(2000,intent);
                finish();
            }
        }
    }


}
