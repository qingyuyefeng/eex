package com.eex.mvp.assrtsjava.activity.Recharge;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.activity.EpayWebViewActivity;
import com.eex.assets.bean.ExchangeRate;
import com.eex.assets.bean.getFunCode;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.common.view.NewComTitleBar;
import com.eex.extensions.RxExtensionKt;
import com.eex.mvp.assrtsjava.activity.Recharge.adapter.RechargeAdapter;

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
 * @Package: com.overthrow.mvp.assrtsjava.activity.Recharge
 * @ClassName: RechargeActivity
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/25 19:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/25 19:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * RechargeAdapter
 */

public class RechargeActivity extends BaseActivity
    implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

  @BindView(R.id.comtitlebar)
  NewComTitleBar comtitlebar;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  @BindView(R.id.recharge_amount)
  EditText rechargeAmount;
  @BindView(R.id.recharge_amount_name)
  TextView rechargeAmountName;
  @BindView(R.id.recharge_fee)
  TextView rechargeFee;
  @BindView(R.id.recharge_fee_name)
  TextView rechargeFeeName;
  @BindView(R.id.recharge_payment)
  TextView rechargePayment;
  @BindView(R.id.recharge_payment_name)
  TextView rechargePaymentName;
  @BindView(R.id.recharge_account)
  TextView rechargeAccount;
  @BindView(R.id.recharge_account_name)
  TextView rechargeAccountName;
  @BindView(R.id.recharge_limit)
  TextView rechargeLimit;
  @BindView(R.id.recharge_rate)
  TextView rechargeRate;
  @BindView(R.id.ckb_put)
  CheckBox ckbput;
  @BindView(R.id.recharge_details)
  TextView rechargeDetails;
  @BindView(R.id.recharge_Immediate)
  Button rechargeImmediate;
  @BindView(R.id.swipeRefresh)
  SwipeRefreshLayout swipeRefresh;

  private ArrayList<getFunCode> list = new ArrayList<>();
  private ArrayList<getFunCode> list1 = new ArrayList<>();
  private RechargeAdapter adapter;

  private ExchangeRate getData;

  private String chargeMaxNum;
  private String chargeMinNum;
  private String currency;
  private String currencyStr;

  private int mLastCheckedPosition = -1;

  @Override
  protected int getLayoutId() {
    return R.layout.re_activity_recharge;
  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    comtitlebar.setTitle(getActivity().getResources().getString(R.string.recharge));

    net();
  }

  @SuppressLint("CheckResult")
  private void net() {
    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .getFunCode(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          if (arrayListData != null) {
            list.clear();
            list.addAll(arrayListData.getData());

            adapter.notifyDataSetChanged();
          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  @Override
  protected void initUiAndListener() {
    SwipeUtil.init(swipeRefresh);
    swipeRefresh.setOnRefreshListener(this);

    adapter = new RechargeAdapter(list);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    recyclerView.setAdapter(adapter);
    setItemChecked(0);

    recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
      @Override
      public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        setItemChecked(position);
        rechargeAmountName.setText(list.get(position).getCurrency());
        rechargeFeeName.setText(
            list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString() + list.get(
                position).getCurrency());
        rechargePaymentName.setText(list.get(position).getCurrency());
        //rechargeAccountName.setText(list.get(position).getCurrency());

        chargeMaxNum = list.get(position).getChargeMaxNum().toString();
        chargeMinNum = list.get(position).getChargeMinNum().toString();
        currency = list.get(position).getCurrency().toString();
        currencyStr = list.get(position).getCurrencyStr().toString();
        //充值手续费
        rechargeFee.setText(
            list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString() + "%");

        rechargeLimit.setText(list.get(position).getChargeMinNum()
            + list.get(position).getCurrency()
            + "-"
            + list.get(position).getChargeMaxNum()
            + list.get(position).getCurrency());

        rechargeAmount.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!rechargeAmount.equals("")) {
              //                            BigDecimal severMoney = list.get(position).getChargeFee().multiply(new BigDecimal(rechargeAmount.getText().toString().trim()));
              rechargeFee.setText(list.get(position)
                  .getChargeFee()
                  .multiply(new BigDecimal(rechargeAmount.getText().toString().trim()))
                  .setScale(3, BigDecimal.ROUND_DOWN)
                  .toPlainString()
                  .toString()+ "%");

              rechargeFeeName.setText( list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString() +list.get(position).getCurrency().toString());
              BigDecimal Reat1 = new BigDecimal(rechargeAmount.getText().toString().trim()).add(
                  new BigDecimal(list.get(position).getChargeFee().toString()).multiply(
                      new BigDecimal(rechargeAmount.getText().toString().trim())));
              rechargePayment.setText(
                  Reat1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
              rechargePaymentName.setText(list.get(position).getCurrency().toString());

              if (getData != null) {
                BigDecimal Reat =
                    new BigDecimal(rechargeAmount.getText().toString().trim()).multiply(
                        new BigDecimal(getData.getRefePrice()));
                rechargeAccount.setText(
                    Reat.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                rechargeAccountName.setText(list.get(position).getCurrency().toString());
              } else {
                rechargeAccountName.setText(list.get(position).getCurrency().toString());
              }
            }
          }

          @Override
          public void afterTextChanged(Editable s) {

          }
        });

        getExchangeRate(list.get(position).getCurrency());
      }
    });
  }

  private void setItemChecked(int position) {

    if (mLastCheckedPosition == position) {
      return;
    }
    adapter.mBooleanArray.put(position, true);
    if (mLastCheckedPosition > -1) {
      adapter.mBooleanArray.put(mLastCheckedPosition, false);
      adapter.notifyItemChanged(mLastCheckedPosition);
    }
    adapter.notifyDataSetChanged();
    mLastCheckedPosition = position;
  }

  /**
   * 获取指定汇率
   */
  @SuppressLint("CheckResult")
  private void getExchangeRate(String currency) {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("currency", currency);
    ApiFactory.getInstance()
        .getExchangeRate(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(exchangeRateData -> {

          if (exchangeRateData.getData() != null) {
            getData = exchangeRateData.getData();
            rechargeRate.setText("1" + exchangeRateData.getData().getCode() + "≈" + exchangeRateData
                .getData()
                .getRefePrice() + "CNYE");
          } else {
            t(exchangeRateData.getMsg());
          }
        }, throwable -> {

        });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @OnClick({
      R.id.comtitlebar, R.id.recharge_amount, R.id.recharge_amount_name, R.id.recharge_fee,
      R.id.recharge_fee_name,
      R.id.recharge_payment, R.id.recharge_payment_name, R.id.recharge_account,
      R.id.recharge_account_name,
      R.id.recharge_limit, R.id.recharge_rate, R.id.ckb_put, R.id.recharge_details,
      R.id.recharge_Immediate
  })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.comtitlebar:
        finish();
        break;

      case R.id.recharge_amount:
        break;
      case R.id.recharge_amount_name:
        break;
      case R.id.recharge_fee:
        break;
      case R.id.recharge_fee_name:
        break;
      case R.id.recharge_payment:
        break;
      case R.id.recharge_payment_name:
        break;
      case R.id.recharge_account:
        break;
      case R.id.recharge_account_name:
        break;
      case R.id.recharge_limit:
        break;
      case R.id.recharge_rate:
        break;

      case R.id.ckb_put:

        break;
      case R.id.recharge_details:
        if (ckbput.isChecked()) {
          ckbput.setChecked(false);
        } else {
          ckbput.setChecked(true);
        }
        break;
      case R.id.recharge_Immediate:

        putMoney();
        break;
    }
  }

  @SuppressLint("CheckResult")
  private void putMoney() {
    if (rechargeAmount.equals("")) {
      t("请输入充值金额");
      return;
    }

    if (new BigDecimal(rechargeAmount.getText().toString().trim()).compareTo(
        new BigDecimal(chargeMaxNum)) == 1) {
      t("单笔最多充值" + chargeMaxNum + currency);
      return;
    }

    if (new BigDecimal(rechargeAmount.getText().toString().trim()).compareTo(
        new BigDecimal(chargeMinNum)) == -1) {
      t("单笔最少充值" + chargeMinNum + currency);
      return;
    }
    if (ckbput.isChecked() == false) {
      t("请勾选充值须知");
      return;
    }

    HashMap<String, String> hasMap = new HashMap<>();
    hasMap.put("dealAmount", rechargeAmount.getText().toString().trim());
    hasMap.put("currency", currency);
    hasMap.put("url", WPConfig.INSTANCE.getBaseUrl() + "");
    RxExtensionKt.filterResult(ApiFactory.getInstance()
        .remittanceother(kv.decodeString("tokenId"), hasMap))
        .compose(RxSchedulers.io_main())
        .subscribe(remittanceData -> {

          if (remittanceData.getData() != null) {
            intent.putExtra("PAYEE_ACCOUNT", remittanceData.getData().getPayee_account().trim());
            intent.putExtra("PAYEE_NAME", remittanceData.getData().getPayee_name());
            intent.putExtra("PAYMENT_AMOUNT",
                remittanceData.getData().getPayment_amount().toString());
            intent.putExtra("PAYMENT_UNITS", remittanceData.getData().getPayment_units());
            intent.putExtra("PAYMENT_ID", remittanceData.getData().getCharacter_encoding());
            intent.putExtra("STATUS_URL", remittanceData.getData().getStatus_url());
            intent.putExtra("PAYMENT_URL", remittanceData.getData().getPayment_url());
            intent.putExtra("NOPAYMENT_URL", remittanceData.getData().getNopayment_url());
            intent.putExtra("BAGGAGE_FIELDS", remittanceData.getData().getBaggage_fields());
            intent.putExtra("INTERFACE_LANGUAGE", remittanceData.getData().getInterface_language());
            intent.putExtra("CHARACTER_ENCODING", remittanceData.getData().getCharacter_encoding());
            intent.putExtra("V2_HASH", remittanceData.getData().getV2_hash());
            intent.putExtra("url", remittanceData.getData().getUrl());
            intent.setClass(getActivity(), EpayWebViewActivity.class);
            startActivity(intent);
            finish();
          } else {
            t(remittanceData.getMsg());
          }
        }, throwable -> {

        });
  }

  @Override
  public void onRefresh() {

    net();
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    setItemChecked(position);
    chargeMaxNum = list.get(position).getChargeMaxNum().toString();
    chargeMinNum = list.get(position).getChargeMinNum().toString();
    currency = list.get(position).getCurrency().toString();
    currencyStr = list.get(position).getCurrencyStr().toString();

    rechargeAmountName.setText(list.get(position).getCurrency());
    rechargeFeeName.setText( list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString() +list.get(position).getCurrency());
    rechargePaymentName.setText(list.get(position).getCurrency());
    rechargeAccountName.setText(list.get(position).getCurrency());
    //充值手续费
    rechargeFee.setText(
        list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString()+"%");

    rechargeLimit.setText(
        list.get(position).getChargeMinNum() + list.get(position).getCurrency() + "-" + list.get(
            position).getChargeMaxNum() + list.get(position).getCurrency());

    rechargeAmount.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!rechargeAmount.equals("")) {
          //                            BigDecimal severMoney = list.get(position).getChargeFee().multiply(new BigDecimal(rechargeAmount.getText().toString().trim()));
          rechargeFee.setText(list.get(position)
              .getChargeFee()
              .multiply(new BigDecimal(rechargeAmount.getText().toString().trim()))
              .setScale(3, BigDecimal.ROUND_DOWN)
              .toPlainString()
              .toString() +"%");
          rechargeFeeName.setText( list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString() +list.get(position).getCurrency().toString());
          BigDecimal Reat1 = new BigDecimal(rechargeAmount.getText().toString().trim()).add(
              new BigDecimal(list.get(position).getChargeFee().toString()).multiply(
                  new BigDecimal(rechargeAmount.getText().toString().trim())));
          rechargePayment.setText(
              Reat1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
          rechargePaymentName.setText(list.get(position).getCurrency().toString());
          if (getData != null) {
            BigDecimal Reat = new BigDecimal(rechargeAmount.getText().toString().trim()).multiply(
                new BigDecimal(getData.getRefePrice()));
            rechargeAccount.setText(
                Reat.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
            rechargeAccountName.setText(list.get(position).getCurrency().toString());
          } else {
            rechargeAccountName.setText(list.get(position).getCurrency().toString());
          }
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    getExchangeRate(list.get(position).getCurrency());
  }
}
