package com.eex.mvp.assrtsjava.activity.Capitalflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.NewComTitleBar;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.mvp.assrtsjava.activity.Capitalflow.frgament.CoinRecordFragment;
import com.eex.mvp.assrtsjava.activity.Capitalflow.frgament.RechargeRecordFrgament;
import com.eex.mvp.assrtsjava.activity.Capitalflow.frgament.RecordWithdrawalFragment;
import com.eex.mvp.assrtsjava.activity.Capitalflow.frgament.WithdrawalsRecordFragment;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: CapitalFlowActivity
 * @Description: 资金流动
 * @Author: hcj
 * @CreateDate: 2019/12/25 14:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 14:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CapitalFlowActivity extends BaseActivity {

    @BindView(R.id.comtitlebar)
    NewComTitleBar comtitlebar;
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ArrayList<Fragment> fragments = new ArrayList<>();


    private int flag = 0;
    private int index = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_capital_flow;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.Capital_flow));
        flag = getIntent().getIntExtra("flag", 0);
        flag = index;
    }

    @Override
    protected void initUiAndListener() {


        if (mContext != null) {
            String[] titles = {getActivity().getResources().getString(R.string.Recharge_record),
                    getActivity().getResources().getString(R.string.Withdrawals_record),
                    getActivity().getResources().getString(R.string.Coin_record),
                    getActivity().getResources().getString(R.string.Record_withdrawal),};
//                    getActivity().getResources().getString(R.string.Transfer_record)


            if (flag == 0) {

                //充值记录
                fragments.add(new RechargeRecordFrgament());
            }
            //提现记录
            fragments.add(new RecordWithdrawalFragment());
            //充币记录
            fragments.add(new CoinRecordFragment());
            //提币记录
            fragments.add(new WithdrawalsRecordFragment());
//            //划转记录
//            fragments.add(new TransferRecordFragment());



            if (viewPager.getAdapter() == null) {
                viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments, flag == 0 ? titles : titles));
                xTablayout.setupWithViewPager(viewPager);
            }

            xTablayout.getTabAt(index).select();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.comtitlebar)
    public void onClick() {
        finish();
    }
}
