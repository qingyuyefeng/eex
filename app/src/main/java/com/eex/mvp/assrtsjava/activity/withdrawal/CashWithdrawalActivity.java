package com.eex.mvp.assrtsjava.activity.withdrawal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.assets.bean.FunCode;
import com.eex.assets.bean.Personal;
import com.eex.assets.bean.Sever;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.util.TimeCount;
import com.eex.common.view.EmptyView;
import com.eex.common.view.NewComTitleBar;
import com.eex.domin.entity.bankcards.BankCardBean;
import com.eex.extensions.RxExtensionKt;
import com.eex.home.activity.home.PhoneNameActivity;
import com.eex.navigation.Navigator;

import java.lang.annotation.Native;
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
 * @Package: com.overthrow.mvp.assrtsjava.activity.withdrawal
 * @ClassName: CashWithdrawalActivity
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/26 11:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/26 11:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * CashWithdrawalAdapter
 */

public class CashWithdrawalActivity extends BaseActivity
    implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

  @BindView(R.id.comtitlebar)
  NewComTitleBar comtitlebar;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  @BindView(R.id.Cash_Choicebank)
  LinearLayout CashChoicebank;
  @BindView(R.id.Cash_Choicebank_bakk)
  TextView CashChoicebankBakk;
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
  @BindView(R.id.Cash_withdrawal)
  Button CashWithdrawal;
  @BindView(R.id.swipeRefresh)
  SwipeRefreshLayout swipeRefresh;

  private int mLastCheckedPosition = -1;

  private String isType = "phone";
  private TimeCount time;

  private ArrayList<FunCode> list = new ArrayList<FunCode>();
  private ArrayList<Personal> personals = new ArrayList<Personal>();
  private CashWithdrawalAdapter adapter;
  private Sever sever;

  private String cardBank;
  private String subBank;
  private String surname;
  private String givename;
  private String bankCardNo;
  private String id;

  @Override
  protected int getLayoutId() {
    return R.layout.re_activity_cash_withdrawal;
  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    comtitlebar.setTitle(getActivity().getResources().getString(R.string.Cash_withdrawal));
    net();
    init();
    getMoneyData();
    GetServiceMoney();
  }

  private void init() {

    rechargeAmount.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
          BigDecimal b1 = new BigDecimal(rechargeAmount.getText().toString());
          BigDecimal ServiceMoney = sever.getServiceMoney();
          BigDecimal b3 = b1.multiply(
              new BigDecimal(new java.text.DecimalFormat("#.000").format(ServiceMoney)));
          BigDecimal B4 = b1.subtract(b3);
          rechargeFee.setText(b3.setScale(2, BigDecimal.ROUND_DOWN).toPlainString().toString());
          rechargeAccount.setText(B4.setScale(2, BigDecimal.ROUND_DOWN).toPlainString().toString());

          //                    tx_severMoney.setText(getResources().getString(R.string.serds)+b3.setScale(3,BigDecimal.ROUND_CEILING)+"元");
        } catch (Exception e) {

        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  /**
   * 获取提现币种
   */
  @SuppressLint("CheckResult")
  private void net() {

    HashMap<String, String> hashMap = new HashMap<>();

    RxExtensionKt.filterResult(ApiFactory.getInstance().getWithdrawFunCode(kv.decodeString("tokenId"), hashMap))
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          list.clear();
          if (data.getData() != null) {
            list.addAll(data.getData());
//            FunCode RNB = new FunCode();
//            RNB.setCurrencyStr("人民币");
//            RNB.setCurrency("CNYE");
//            list.add(0, RNB);
          }

          adapter.notifyDataSetChanged();
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  @Override
  protected void initUiAndListener() {

    SwipeUtil.init(swipeRefresh);
    swipeRefresh.setOnRefreshListener(this);

    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    adapter = new CashWithdrawalAdapter(list);
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(this);

    setItemChecked(0);
    recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
      @Override
      public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        setItemChecked(position);
        rechargeAmountName.setText(list.get(position).getCurrency());
        rechargeFeeName.setText(
            list.get(position).getChargeFee().multiply(new BigDecimal("100")).toString() + list.get(
                position).getCurrency());
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @OnClick({
      R.id.comtitlebar, R.id.Cash_Choicebank, R.id.Cash_Choicebank_bakk, R.id.recharge_amount,
      R.id.recharge_amount_name, R.id.recharge_fee, R.id.recharge_fee_name, R.id.recharge_payment,
      R.id.recharge_payment_name, R.id.recharge_account, R.id.recharge_account_name,
      R.id.Cash_withdrawal
  })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.comtitlebar:
        break;
      case R.id.Cash_Choicebank:
      case R.id.Cash_Choicebank_bakk:
        Navigator.INSTANCE.toBankCardsManagerActivity(getActivity(), 1, 1);

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
      case R.id.Cash_withdrawal:

        if (CashChoicebankBakk.getText().toString().trim().equals("")) {
          t("请选择收款账户");
          return;
        }

        if (rechargeAmount.getText().toString().trim().equals("")) {
          t("请输入提现金额");
          return;
        }
        if (Double.parseDouble(rechargeAmount.getText().toString().trim()) < 500) {

          t("请输入大于500的金额");
          return;
        }

        //if (new BigDecimal(rechargePayment.getText().toString().trim()).compareTo(new BigDecimal(rechargeAmount.getText().toString().trim()))>1){
        //
        //}
        if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
          showPopueWindow();
        } else {
          intent.setClass(getActivity(), PhoneNameActivity.class);
          startActivity(intent);
        }
        break;
    }
  }

  private void showPopueWindow() {
    View popView = View.inflate(getActivity(), R.layout.popupwindow_timoney, null);
    TextView txvew_phone = (TextView) popView.findViewById(R.id.txvew_phone);
    TextView textview_Email = (TextView) popView.findViewById(R.id.textview_Email);
    EditText edt_YZM = (EditText) popView.findViewById(R.id.edt_YZM);
    Button btn_YZM = (Button) popView.findViewById(R.id.btn_YZM);
    Button button = (Button) popView.findViewById(R.id.button);
    //获取屏幕宽高
    int weight = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

    final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
    popupWindow.setAnimationStyle(R.style.anim_popup_dir);
    popupWindow.setFocusable(true);
    //点击外部popueWindow消失
    popupWindow.setOutsideTouchable(true);

    if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
      isType = "phone";
      txvew_phone.setText("手机");
      btn_YZM.setVisibility(View.VISIBLE);
    } else if (kv.decodeInt("googleState") == 1) {
      isType = "google";
      txvew_phone.setText("谷歌");
      btn_YZM.setVisibility(View.GONE);
    }
    //            else {
    //                isType = "email";
    //                txvew_phone.setText("邮箱");
    //                btn_YZM.setVisibility(View.VISIBLE);
    //            }

    btn_YZM.setOnClickListener(new View.OnClickListener() {
      @SuppressLint("CheckResult")
      @Override
      public void onClick(View v) {

        if (isType.equals("phone")) {
          //电话发送提币验证码
          HashMap<String, String> requestParam = new HashMap<>();
          requestParam.put("sendType", "sms_take_money");
          requestParam.put("phone", kv.decodeString("phone"));

          ApiFactory.getInstance()
              .email(kv.decodeString("tokenId"), requestParam)
              .compose(RxSchedulers.io_main())
              .subscribe(data -> {
                if (data.isStauts() == true) {
                  //构造CountDownTimer对象
                  time = new TimeCount(getActivity(), btn_YZM, "重新获取", 60000, 1000);
                  time.start();
                  Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                  t(data.getMsg());
                }
              }, throwable -> {

              });
        } else if (isType.equals("email")) {
          //邮箱发送提币验证码
          HashMap<String, String> requestParam = new HashMap<>();
          requestParam.put("sendType", "5");
          requestParam.put("email", kv.decodeString("email"));

          ApiFactory.getInstance()
              .email(kv.decodeString("tokenId"), requestParam)
              .compose(RxSchedulers.io_main())
              .subscribe(data -> {
                if (data.isStauts() == true) {
                  //构造CountDownTimer对象
                  time = new TimeCount(getActivity(), btn_YZM, "重新获取", 60000, 1000);
                  time.start();
                  Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                  t(data.getMsg());
                }
              }, throwable -> {

              });
        }
      }
    });
    //提现
    button.setOnClickListener(new View.OnClickListener() {
      @SuppressLint("CheckResult")
      @Override
      public void onClick(View v) {
        if (edt_YZM.getText().toString().trim().equals("")) {
          t("请输入验证码");
          return;
        }
        HashMap<String, String> requestParam = new HashMap<>();
        try {
          if (isType.equals("phone")) {
            //1手机
            requestParam.put("checkType", "1");
            requestParam.put("phoneoremail", kv.decodeString("phone"));
            //银行名称
            requestParam.put("bankName", cardBank);
            //                            requestParam.put("userBankCardNo", subBank);//银行卡号
            //银行卡号
            requestParam.put("bankCardId", id);
            //姓
            requestParam.put("lastName", surname);
            //名
            requestParam.put("firstName", givename);
            //金额
            requestParam.put("dealAmount", rechargeAmount.getText().toString().trim());
            requestParam.put("code", edt_YZM.getText().toString().trim());
          } else {
            //银行名称
            requestParam.put("bankName", cardBank);
            //                            requestParam.put("userBankCardNo", subBank);//银行卡号
            //银行卡号
            requestParam.put("bankCardId", id);

            //姓
            requestParam.put("lastName", surname);
            //名
            requestParam.put("firstName", givename);
            //金额
            requestParam.put("dealAmount", rechargeAmount.getText().toString().trim());
            requestParam.put("checkType", "2");
            requestParam.put("code", edt_YZM.getText().toString().trim());
            requestParam.put("googleKey", kv.decodeString("googleKey"));

            ApiFactory.getInstance()
                .extract(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                  if (data.isStauts() == true) {

                    //可用余额
                    getMoneyData();
                    rechargeAmount.setText("");
                    Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                  } else {
                    t(data.getMsg());
                  }
                }, throwable -> {

                });
          }
        } catch (Exception e) {
          t("请绑定邮箱或电话");
        }
        //银行名称
        requestParam.put("bankName", cardBank);
        //银行卡号
        requestParam.put("bankCardId", id);
        //姓
        requestParam.put("lastName", surname);
        //名
        requestParam.put("firstName", givename);
        //金额
        requestParam.put("dealAmount", rechargeAmount.getText().toString().trim());
        requestParam.put("code", edt_YZM.getText().toString().trim());

        ApiFactory.getInstance()
            .extract(kv.decodeString("tokenId"), requestParam)
            .compose(RxSchedulers.io_main())
            .subscribe(data -> {
              if (data.isStauts() == true) {

                //可用余额
                getMoneyData();
                rechargeAmount.setText("");
                Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
              } else {
                t(data.getMsg());
              }
            }, throwable -> {

            });
      }
    });

    popupWindow.dismiss();
    //popupWindow出现屏幕变为半透明
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = 1.0f;
    getWindow().setAttributes(lp);
    popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 50);
    BackgroudAlpha((float) 0.5);
    popupWindow.setOnDismissListener(new popupwindowdismisslistener());
  }

  private void BackgroudAlpha(float alpha) {
    // TODO Auto-generated method stub
    WindowManager.LayoutParams l = getWindow().getAttributes();
    l.alpha = alpha;
    getWindow().setAttributes(l);
  }

  /**
   * 点击其他部分popwindow消失时，屏幕恢复透明度
   */
  class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

    @Override
    public void onDismiss() {
      BackgroudAlpha((float) 1);
    }
  }

  @SuppressLint("CheckResult")
  private void GetServiceMoney() {

    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .cnyaccount(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          if (data.getData() != null) {

            sever = data.getData();
            BigDecimal ServiceMoney = data.getData().getServiceMoney();
          } else {
            t(data.getMsg());
          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  /**
   * 可用余额
   */
  @SuppressLint("CheckResult")
  private void getMoneyData() {
    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .accountinfo(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          if (data.getData() != null) {
            personals = data.getData().getUserCoinAccount();
            rechargePayment.setText(
                data.getData().getUseMoneyCny().setScale(2, BigDecimal.ROUND_DOWN).toString());
          } else {
            t(data.getMsg());
          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  @Override
  public void onRefresh() {
    net();
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (data != null) {
        BankCardBean bankCardBean = (BankCardBean) data.getSerializableExtra("bean");
        cardBank = bankCardBean.getBankName();
        subBank = bankCardBean.getBankChildName();
        surname = bankCardBean.getSurname();
        bankCardNo = bankCardBean.getBankCardNo();
        givename = bankCardBean.getGivename();
        id = bankCardBean.getId();

        CashChoicebankBakk.setText(cardBank + bankCardNo);
      }
    }
  }
}
