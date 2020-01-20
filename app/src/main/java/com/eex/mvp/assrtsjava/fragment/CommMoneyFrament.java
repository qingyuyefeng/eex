package com.eex.mvp.assrtsjava.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.assets.bean.Personal;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.view.EmptyView;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.assrtsjava.activity.Capitalflow.CapitalFlowActivity;
import com.eex.mvp.assrtsjava.activity.CommMoneySeachActivity;
import com.eex.mvp.assrtsjava.activity.CurrencyChoiceActivity;
import com.eex.mvp.assrtsjava.activity.Recharge.RechargeActivity;
import com.eex.mvp.assrtsjava.activity.TransferActivity;
import com.eex.mvp.assrtsjava.activity.withdrawal.CashWithdrawalActivity;
import com.eex.mvp.assrtsjava.adapter.TradingAccountAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * @Package: com.overthrow.mvp.assrtsjava.fragment
 * @ClassName: CommMoneyFrament
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 13:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 13:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * CommMoneyDetailsActivity
 */

public class CommMoneyFrament extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

  Unbinder unbinder;
  @BindView(R.id.assets_money)
  TextView assetsMoney;
  @BindView(R.id.assets_about_money)
  TextView assetsAboutMoney;
  @BindView(R.id.assets_linr)
  LinearLayout assetsLinr;
  @BindView(R.id.assets_available)
  TextView assetsAvailable;
  @BindView(R.id.assets_freeze)
  TextView assetsFreeze;
  @BindView(R.id.assets_recharge_coin)
  TextView assetsRechargeCoin;
  @BindView(R.id.assets_withdrawal_records)
  TextView assetsWithdrawalRecords;
  @BindView(R.id.assets_top_up)
  LinearLayout assetsTopUp;
  @BindView(R.id.assets_withdrawal)
  LinearLayout assetsWithdrawal;
  @BindView(R.id.assets_charge_money)
  LinearLayout assetsChargeMoney;
  @BindView(R.id.assets_mention_money)
  LinearLayout assetsMentionMoney;
  @BindView(R.id.assets_transfer)
  LinearLayout assetsTransfer;
  @BindView(R.id.assets_seach)
  LinearLayout assetsSeach;
  @BindView(R.id.assets_Hide_check)
  CheckBox assetsHideCheck;
  @BindView(R.id.assets_Hide_text)
  TextView assetsHideText;
  @BindView(R.id.assets_Hide_small)
  LinearLayout assetsHideSmall;
  @BindView(R.id.assets_sorting_img)
  ImageView assetsSortingImg;
  @BindView(R.id.assets_sorting)
  LinearLayout assetsSorting;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  private TradingAccountAdapter adapter;
  private ArrayList<Personal> list = new ArrayList<>();
  private ArrayList<Personal> mNewList = new ArrayList<Personal>();

  /**
   * 隐藏小额资产
   */
  private Integer type = 1;
  /**
   * 排序
   */
  private Integer imgType = 1;

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    assetsLinr.setVisibility(View.VISIBLE);
  }

  @SuppressLint("CheckResult")
  private void net() {

    HashMap<String, String> hashMap = new HashMap<>();
    final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .accountinfo(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          dialog.dismiss();
          if (data.getData() != null) {

            assetsAvailable.setText(
                data.getData().getUseMoneyCny().setScale(2, BigDecimal.ROUND_CEILING).toString());
            assetsFreeze.setText(data.getData()
                .getFrozenMoneyCny()
                .setScale(2, BigDecimal.ROUND_CEILING)
                .toString());

            assetsMoney.setText(
                data.getData().getTotalMoneyBtc().setScale(5, BigDecimal.ROUND_DOWN).doubleValue()
                    + "");

            assetsAboutMoney.setText("≈" + data.getData()
                .getTotalMoneyCny()
                .setScale(2, BigDecimal.ROUND_DOWN)
                .stripTrailingZeros()
                .toPlainString() + "CNY");
            //                        txUSD.setText("$" + data.getData().getTotalMoneyUsdt().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

            list.clear();
            list = data.getData().getUserCoinAccount();
            adapter.notifyDataSetChanged();
            assetsSortingImg.setImageResource(R.mipmap.assets_sorting);
            imgType = 1;
            for (int i = 0; i < list.size(); i++) {
              if (list.get(i).getTotalMoeny().compareTo(new BigDecimal("0.0")) == 1) {
                mNewList.add(list.get(i));
              }
            }

            setviewtext();
          }
        }, throwable -> {
          dialog.dismiss();
        });
  }

  private void setviewtext() {
    adapter = new TradingAccountAdapter(list);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(this);
    adapter.setEmptyView(new EmptyView(getActivity()));
  }

  @Override
  protected void initUiAndListener() {

    adapter = new TradingAccountAdapter(list);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(this);
    adapter.setEmptyView(new EmptyView(getActivity()));
  }

  @Override
  public void onResume() {
    super.onResume();
    if (getUserVisibleHint() && !isHidden()) {
      net();
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.re_fragment_commmoney;
  }

  @Override
  protected void lazyLoad() {
    net();
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

  @OnClick({
      R.id.assets_money, R.id.assets_about_money, R.id.assets_available, R.id.assets_freeze,
      R.id.assets_recharge_coin, R.id.assets_withdrawal_records, R.id.assets_top_up,
      R.id.assets_withdrawal, R.id.assets_charge_money, R.id.assets_mention_money,
      R.id.assets_transfer, R.id.assets_seach, R.id.assets_Hide_small, R.id.assets_sorting
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      //Btc 余额
      case R.id.assets_money:
        break;
      //约等于RMB
      case R.id.assets_about_money:
        break;
      //可用余额
      case R.id.assets_available:
        break;
      //冻结余额
      case R.id.assets_freeze:
        break;
      // 充值/充币记录
      case R.id.assets_recharge_coin:
        intent = new Intent(getActivity(), CapitalFlowActivity.class);
        intent.putExtra("flag", 0);
        startActivity(intent);

        break;
      // 全部的充值提现记录
      case R.id.assets_withdrawal_records:
        intent = new Intent(getActivity(), CapitalFlowActivity.class);
        intent.putExtra("flag", 0);
        startActivity(intent);
        break;
      //充值
      case R.id.assets_top_up:
        intent = new Intent(getActivity(), RechargeActivity.class);
        startActivity(intent);

        break;
      //提现
      case R.id.assets_withdrawal:

        intent = new Intent(getActivity(), CashWithdrawalActivity.class);
        startActivity(intent);

        break;
      //充币
      case R.id.assets_charge_money:

        intent = new Intent(getActivity(), CurrencyChoiceActivity.class);
        intent.putExtra("CurrencyChoice", "1");
        startActivity(intent);
        break;
      //提币
      case R.id.assets_mention_money:
        intent = new Intent(getActivity(), CurrencyChoiceActivity.class);
        intent.putExtra("CurrencyChoice", "2");
        startActivity(intent);
        break;
      //划转
      case R.id.assets_transfer:

        intent = new Intent(getActivity(), TransferActivity.class);
        startActivity(intent);
        break;
      //搜索
      case R.id.assets_seach:

        intent = new Intent(getActivity(), CommMoneySeachActivity.class);
        startActivity(intent);
        break;
      //隐藏小额币种
      case R.id.assets_Hide_small:
        if (type == 1) {
          type = 2;
          adapter = new TradingAccountAdapter(mNewList);
          recyclerView.setAdapter(adapter);
          adapter.notifyDataSetChanged();
          assetsHideCheck.setChecked(true);
          assetsHideText.setTextColor(getActivity().getResources().getColor(R.color.main_title1));
        } else {
          type = 1;
          adapter = new TradingAccountAdapter(list);
          recyclerView.setAdapter(adapter);
          adapter.notifyDataSetChanged();
          assetsHideCheck.setChecked(false);
          assetsHideText.setTextColor(getActivity().getResources().getColor(R.color.text_color));
        }

        break;
      //排序
      case R.id.assets_sorting:
        if (imgType == 2) {
          assetsSortingImg.setImageResource(R.mipmap.assets_sorting);
          Collections.sort(list, new Comparator<Personal>() {
            @Override
            public int compare(Personal o1, Personal o2) {
              return o1.getCoinCode().compareTo(o2.getCoinCode());
            }
          });
          adapter.setNewData(list);
          imgType = 1;
        } else {
          assetsSortingImg.setImageResource(R.mipmap.assets_z_sorting);
          Collections.sort(list, new Comparator<Personal>() {
            @Override
            public int compare(Personal o1, Personal o2) {
              return o2.getCoinCode().compareTo(o1.getCoinCode());
            }
          });
          adapter.setNewData(list);
          imgType = 2;
        }

        break;

      default:
        break;
    }
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

  }
}