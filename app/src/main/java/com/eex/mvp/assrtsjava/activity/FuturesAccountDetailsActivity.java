package com.eex.mvp.assrtsjava.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.assets.bean.Bilistdata;
import com.eex.assets.bean.CtcaccountList;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.NewComTitleBar;
import com.eex.constant.Keys;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.assrtsjava.bean.Assets;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mine.financialcenter.FinancialCenterActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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
 * @Package: com.overthrow.mvp.assrtsjava.activity.Capitalflow
 * @ClassName: FuturesAccountDetailsActivity
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/25 18:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 18:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class FuturesAccountDetailsActivity extends BaseActivity  implements SwipeRefreshLayout.OnRefreshListener{

  @BindView(R.id.accountdetailsbar)
  NewComTitleBar accountdetailsbar;
  @BindView(R.id.account_details_img)
  ImageView accountDetailsImg;
  @BindView(R.id.account_details)
  TextView accountDetails;
  @BindView(R.id.account_details_bi)
  TextView accountDetailsBi;
  @BindView(R.id.account_details_Total)
  TextView accountDetailsTotal;
  @BindView(R.id.assets_available1)
  TextView assetsAvailable1;
  @BindView(R.id.assets_freeze1)
  TextView assetsFreeze1;
  @BindView(R.id.account_details_equivalent)
  TextView accountDetailsEquivalent;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  @BindView(R.id.assets_Charge_money)
  LinearLayout assetsChargeMoney;
  @BindView(R.id.assets_Mention_money)
  LinearLayout assetsMentionMoney;
  @BindView(R.id.assets_transfer)
  LinearLayout assetsTransfer;
  @BindView(R.id.account_details_financial)
  LinearLayout accountDetailsFinancial;
  @BindView(R.id.swipeRefresh)
  SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.accountdetails)
  LinearLayout accountdetails;

  private ArrayList<Personal> list = new ArrayList<Personal>();
  /**
   * 全部数据
   */
  private ArrayList<Assets> ctcaccountLists = new ArrayList();

  private String coin = "";
  private String imgurl = "";
  private String totalAssets = "";
  private String usableMoney = "";
  private String frozenMoney = "";
  private BigDecimal CurrencyRate, FixedRate, MaxNum, MinNum;
  private ArrayList<Bilistdata> bilistdata = new ArrayList<>();

  @Override
  protected int getLayoutId() {
    return R.layout.re_activity_account_details;
  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    if (getIntent().getStringExtra("coin") != null) {
      coin = getIntent().getStringExtra("coin");
      imgurl = getIntent().getStringExtra("imgurl");
      totalAssets = getIntent().getStringExtra("totalAssets");
      usableMoney = getIntent().getStringExtra("usableMoney");
      frozenMoney = getIntent().getStringExtra("frozenMoney");
    }
    accountdetailsbar.setTitle(coin);
    accountDetails.setText(coin);
    accountDetailsBi.setText(coin);

    //        Glide.with(mContext).load(WPConfig.PicBaseUrl + imgurl + "").into(accountDetailsImg);

    assetsChargeMoney.setVisibility(View.GONE);
    assetsMentionMoney.setVisibility(View.GONE);
  }

  @Override
  protected void onResume() {
    super.onResume();
    /**
     *
     获取资金账户币种
     */
    getziJinData();
    /**
     *
     获取期约资金账户币种详情
     */
    getData();
  }

  @SuppressLint("CheckResult")
  private void getziJinData() {
    HashMap<String, String> hashMap = new HashMap<>();

    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .accountinfo(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          if (data.getData() != null) {
            list = data.getData().getUserCoinAccount();
            for (int i = 0; i < list.size(); i++) {
              if (list.get(i).getCoinCode().equals(coin)) {
                assetsTransfer.setVisibility(View.VISIBLE);
                return;
              } else {
                assetsTransfer.setVisibility(View.GONE);
              }
            }
          } else {
            t(data.getMsg());
          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  @SuppressLint("CheckResult")
  private void getData() {
    HashMap<String, String> hashMap = new HashMap<>();
    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .assets(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          if (data.getData() != null) {

            ctcaccountLists.clear();
            ctcaccountLists.addAll(data.getData());
            for (int i = 0; i < ctcaccountLists.size(); i++) {
              if (coin.equals(ctcaccountLists.get(i).getCoinCode())) {
                accountDetailsEquivalent.setText(
                    data.getData().get(i).getCnye().toString());
                totalAssets = ctcaccountLists.get(i)
                    .getTotal()
                    .setScale(8, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros()
                    .toPlainString();
                usableMoney = ctcaccountLists.get(i)
                    .getUsableMoney()
                    .setScale(8, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros()
                    .toPlainString();
                frozenMoney = ctcaccountLists.get(i)
                    .getFrozenMoney()
                    .setScale(8, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros()
                    .toPlainString();

                accountDetailsTotal.setText(totalAssets);
                assetsAvailable1.setText(usableMoney);
                assetsFreeze1.setText(frozenMoney);
              }
            }
          } else {
            t(data.getMsg());
          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  @Override
  protected void initUiAndListener() {
    SwipeUtil.init(swipeRefresh);
    swipeRefresh.setOnRefreshListener(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @OnClick({
      R.id.accountdetailsbar, R.id.account_details_img, R.id.account_details,
      R.id.account_details_bi, R.id.account_details_Total, R.id.assets_available1,
      R.id.assets_freeze1, R.id.account_details_equivalent, R.id.recyclerView,
      R.id.assets_Charge_money, R.id.assets_Mention_money, R.id.assets_transfer,
      R.id.account_details_financial, R.id.swipeRefresh, R.id.accountdetails
  })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.accountdetailsbar:
        finish();
        break;
      case R.id.account_details_img:
        break;
      case R.id.account_details:
        break;
      case R.id.account_details_bi:
        break;
      case R.id.account_details_Total:
        break;
      case R.id.assets_available1:
        break;
      case R.id.assets_freeze1:
        break;
      case R.id.account_details_equivalent:
        break;//            充币
      case R.id.assets_Charge_money:
        //                authStauts();
        break;
      //            提币
      case R.id.assets_Mention_money:
        //                isName();
        break;
      //            划转
      case R.id.assets_transfer:

        intent.putExtra("type", "1");
        intent.putExtra("coinCode", coin);
        intent.putExtra("UsableMoney", usableMoney);
        intent.setClass(getActivity(), FuturesChargeMoneActivity.class);
        startActivity(intent);
        break;
      //            理财
      case R.id.account_details_financial:

        intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Keys.PARAM_PAIR, "ETH_USDT");
        bundle.putInt(Keys.MAIN_SELECT, 2);
        bundle.putInt(Keys.TRANS_SELECT, 1);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
        break;
    }
  }

  @SuppressLint("CheckResult")
  private void isName() {

    HashMap<String, String> hashMap = new HashMap<>();

    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .authStauts(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null) {

            try {
              if (data.getData().getLevel().equals(3)) {
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                  //
                  getBiListData();
                } else {
                  Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                }
              } else if (data.getData().getLevel().equals(2)) {
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                  //
                  getBiListData();
                } else {
                  Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                }
              } else if (data.getData().getLevel().equals(1)) {

                Toast.makeText(getActivity(), "请完成实名等级2后操作", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
              }
            } catch (Exception e) {
              Toast.makeText(getActivity(), "实名审核中请稍后再试", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  @SuppressLint("CheckResult")
  private void getBiListData() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("type", "1");
    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .depositcoin(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null) {
            bilistdata = data.getData();

            for (int i = 0; i < bilistdata.size(); i++) {
              if (bilistdata.get(i).getCoinCode().equals(coin)) {
                CurrencyRate = bilistdata.get(i).getCurrencyRate();
                FixedRate = bilistdata.get(i).getFixedRate();
                MaxNum = bilistdata.get(i).getMaxNum();
                MinNum = bilistdata.get(i).getMinNum();
              }
            }

            intent.putExtra("coinCode", coin);
            intent.putExtra("currencyRate", CurrencyRate.stripTrailingZeros().toPlainString());
            intent.putExtra("fixedRate", FixedRate.stripTrailingZeros().toPlainString());
            intent.putExtra("coinMax", MaxNum.stripTrailingZeros().toPlainString());
            intent.putExtra("coinMin", MinNum.stripTrailingZeros().toPlainString());
            intent.setClass(getActivity(), WithdrawMoneyActivity.class);
            startActivity(intent);
          }
        }, throwable -> {

        });
  }

  @SuppressLint("CheckResult")
  private void authStauts() {
    HashMap<String, String> hashMap = new HashMap<>();

    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .authStauts(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null) {

            try {
              if (data.getData().getLevel().equals(3)) {
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {

                  intent.putExtra("name", coin);
                  intent.setClass(getActivity(), CurrencyRechargeActivity.class);
                  startActivity(intent);
                } else {
                  Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                }
              } else if (data.getData().getLevel().equals(2)) {
                intent.putExtra("name", coin);
                intent.setClass(getActivity(), CurrencyRechargeActivity.class);
                startActivity(intent);
              } else if (data.getData().getLevel().equals(1)) {

                intent.putExtra("name", coin);
                intent.setClass(getActivity(), CurrencyRechargeActivity.class);
                startActivity(intent);
              } else {
                Toast.makeText(getActivity(), "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
              }
            } catch (Exception e) {
              Toast.makeText(getActivity(), "实名审核中请稍后再试", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  @Override public void onRefresh() {
    /**
     *
     获取资金账户币种
     */
    getziJinData();
    /**
     *
     获取c2c资金账户币种详情
     */
    getData();
  }
}
