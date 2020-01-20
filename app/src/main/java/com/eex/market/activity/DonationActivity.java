package com.eex.market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.market.frgament.donation.CurrentEntrustmentFrament;
import com.eex.market.frgament.donation.HistoricalEntrustmentFrament;
import com.eex.market.frgament.donation.MarketBuyFragment;
import com.eex.market.frgament.donation.MarketSellFragment;
import com.eex.market.frgament.donation.TransactionSummaryFragment;

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
 * @ClassName: DonationActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 16:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 16:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DonationActivity extends BaseActivity implements XTabLayout.OnTabSelectedListener {


    private static final String TAG = "DonationActivity";

    /**
     *
     */
    @BindView(R.id.title_dontion)
    TextView titleDontion;
    /**
     *
     */
    @BindView(R.id.sousuo_tv)
    TextView sousuoTv;
    /**
     *
     */
    @BindView(R.id.sousuo_image)
    ImageView sousuoImage;
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

    private String[] titles = {"买入",
            "卖出",
            "当前委托",
            "历史委托",

    };

    private String[] titles1 = {"买入",
            "卖出",
            "当前委托",
            "创新版汇总",

    };
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static String BiName = "BTC/USDT";
    public static String BinameData = "BTC_USDT";
    public static String JYB = "BTC";
    public static String DJB = "USDT";

    private int flag = 0;
    private int index = 0;
    private int other;

    private String dealPair = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_donation;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (getIntent().getStringExtra("KlinBiname") != null) {

            BinameData = getIntent().getStringExtra("KlinBiname");
            JYB = getIntent().getStringExtra("JYBi");
            DJB = getIntent().getStringExtra("DJBi");
            BiName = BinameData.replace("_", "/");
            sousuoTv.setText(BiName);

        }

        other = getIntent().getIntExtra("other", 0);

        Log.e(TAG, "传过来的值: " + other);

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //买入
                    MarketBuyFragment.name = BinameData.replace("_", "/");
                    MarketBuyFragment.name1 = BinameData;
                    MarketBuyFragment.BINAME = JYB;
                    MarketBuyFragment.BINAMEDATA = DJB;
                    MarketBuyFragment.OTHER = other;

                    //卖入
                   /* MarketSaleFragment.Companion.setName(BinameData.replace("_", "/"));
                    MarketSaleFragment.Companion.setName1(BinameData);
                    MarketSaleFragment.Companion.setBINAME(JYB);
                    MarketSaleFragment.Companion.setBINAMEDATA(DJB);*/
//                    MarketSaleFragment.OTHER = other;
                    MarketSellFragment.name = BinameData.replace("_", "/");
                    MarketSellFragment.name1 = BinameData;
                    MarketSellFragment.BINAME = JYB;
                    MarketSellFragment.BINAMEDATA = DJB;
                    MarketSellFragment.OTHER = other;


                }
            });
        }


        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (BinameData.equals("GLS_USDT") || BinameData.equals("AJA_USDT")||BinameData.equals("VZCC_USDT")) {
                        //买入
                        fragments.add(new MarketBuyFragment());
                        //卖出
                        fragments.add(new MarketSellFragment());
                        //当前委托
                        fragments.add(new CurrentEntrustmentFrament());
                        //创新版成交汇总
                        fragments.add(new TransactionSummaryFragment());

                        if (viewPager.getAdapter() == null) {
                            viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments, flag == 0 ? titles1 : titles1));
                            xTablayout.setupWithViewPager(viewPager);
                        }
                    } else {
                        //买入
                        fragments.add(new MarketBuyFragment());
                        //卖出
                        fragments.add(new MarketSellFragment());
                        //当前委托
                        fragments.add(new CurrentEntrustmentFrament());
                        //历史委托
                        fragments.add(new HistoricalEntrustmentFrament());
                        if (viewPager.getAdapter() == null) {
                            viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments, flag == 0 ? titles : titles));
                            xTablayout.setupWithViewPager(viewPager);
                        }
                    }

                    xTablayout.getTabAt(index).select();


                }
            });
        }




    }

    @Override
    protected void initUiAndListener() {


    }


    @OnClick({R.id.sousuo_tv, R.id.sousuo_image, R.id.title_dontion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_dontion:

                finish();
                break;
            case R.id.sousuo_tv:


//                MarketBuyFragment.XDPrice.setText("");
//                MarketBuyFragment.XDnuber.setText("");
//                MarketBuyFragment.XJMoney.setText("");
//                MarketBuyFragment.SJnuber.setText("");
//                MarketBuyFragment.ZYZSCFPrice.setText("");
//                MarketBuyFragment.ZYZSPrice.setText("");
//                MarketBuyFragment.ZYZSnuber.setText("");
//
//                MarketSaleFragment.XDPrice.setText("");
//                MarketSaleFragment.XDnuber.setText("");
//                MarketSaleFragment.XJMoney.setText("");
//                MarketSaleFragment.SJnuber.setText("");
//                MarketSaleFragment.ZYZSCFPrice.setText("");
//                MarketSaleFragment.ZYZSPrice.setText("");
//                MarketSaleFragment.ZYZSnuber.setText("");
//
//                intent.setClass(getActivity(), SummaryBiActivity.class);
//                startActivityForResult(intent, 1000);

                break;
            case R.id.sousuo_image:

                // JYBi
                intent.putExtra("JYBi", JYB);
                //DJBi
                intent.putExtra("DJBi", DJB);
                // BTC/USDT
                intent.putExtra("Biname", BiName);
                //BTC_USDT
                intent.putExtra("BinameData", BinameData);
                intent.setClass(DonationActivity.this, KLineActivity.class);
                startActivity(intent);

                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2000) {
            if (data != null) {
                BiName = data.getStringExtra("name");
                sousuoTv.setText(BiName);
                BinameData = data.getStringExtra("data");
                //BTC
                JYB = data.getStringExtra("JYBi");
                //USDT
                DJB = data.getStringExtra("DJBi");
                //FUN/USDT---FUN_USDT

                //FUN/USDT
                MarketBuyFragment.name = BiName;
                //FUN_USDT
                MarketBuyFragment.name1 = BinameData;
                //BTC
                MarketBuyFragment.BINAME = JYB;
                //USDT
                MarketBuyFragment.BINAMEDATA = DJB;
                //
                MarketBuyFragment.OTHER = other;


              /*  MarketSaleFragment.Companion.setName(BiName);
                //FUN/USDT
                //FUN_USDT
                MarketSaleFragment.Companion.setName1(BinameData);
                //BTC
                MarketSaleFragment.Companion.setBINAME(JYB);
                //USDT
                MarketSaleFragment.Companion.setBINAMEDATA(DJB);*/
                //
//                MarketSaleFragment.OTHER = other;
                MarketSellFragment.name = BinameData.replace("_", "/");
                MarketSellFragment.name1 = BinameData;
                MarketSellFragment.BINAME = JYB;
                MarketSellFragment.BINAMEDATA = DJB;
                MarketSellFragment.OTHER = other;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onTabSelected(XTabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == 3) {
//            if (UserUtil.getTokenId() == null) {
//                Toast.makeText(DonationActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
//
//            }

        }
    }

    @Override
    public void onTabUnselected(XTabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(XTabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == 3) {
//            if (UserUtil.getTokenId() == null) {
//                Toast.makeText(DonationActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
//
//            }

        }
    }
}
