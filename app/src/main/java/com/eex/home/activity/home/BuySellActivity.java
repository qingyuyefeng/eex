package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.bean.Advertisingvo;
import com.eex.home.bean.CoinfigList;
import com.eex.home.fragment.BuyC2CFrament;
import com.eex.home.fragment.SellC2CFrament;
import com.eex.home.weight.AnimUtil;
import com.eex.home.weight.MyDialog;
import java.util.ArrayList;
import java.util.HashMap;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: BuySellActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 15:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 15:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BuySellActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

  /**
   *
   */
  @BindView(R.id.comtitlebar)
  RelativeLayout comtitlebar;
  /**
   *
   */
  @BindView(R.id.rb_buy)
  RadioButton rbBuy;
  /**
   *
   */
  @BindView(R.id.rb_sell)
  RadioButton rbSell;
  /**
   *
   */
  @BindView(R.id.rb_group)
  RadioGroup rbGroup;
  /**
   *
   */
  @BindView(R.id.Img1)
  ImageView Img1;
  /**
   *
   */
  @BindView(R.id.LLme)
  LinearLayout LLme;
  /**
   *
   */
  @BindView(R.id.LL_ppowd)
  LinearLayout LLPpowd;
  /**
   *
   */
  @BindView(R.id.ll_base_toolber)
  LinearLayout llBaseToolber;
  /**
   *
   */
  @BindView(R.id.tra_myzoe_header)
  LinearLayout traMyzoeHeader;
  /**
   *
   */
  @BindView(R.id.container1)
  FrameLayout container1;
  /**
   *
   */
  @BindView(R.id.img_Release)
  ImageView imgRelease;
  /**
   *
   */
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  /**
   * 买入
   */
  private ArrayList<CoinfigList> listBuy = new ArrayList<>();
  /**
   * 卖入
   */
  private ArrayList<CoinfigList> listSell = new ArrayList<>();

  /**
   * 买入
   */
  private ArrayList<Advertisingvo> advertisingvBuy = new ArrayList<>();
  /**
   * 卖入
   */
  private ArrayList<Advertisingvo> advertisingvSell = new ArrayList<>();

  ArrayList<Fragment> fragments = new ArrayList<>();
  ArrayList<RadioButton> views = new ArrayList<>();

  /***
   *
   */
  private PopupWindow mPopupWindow;

  /**
   * 动画工具类
   */
  private AnimUtil animUtil;

  private String type = "buy";

  /**
   * dialog
   */
  private TextView Tltle;
  private Button btnDimss;
  private Button btnSell;

  private View view;

  /**
   * mMyDialog
   */
  private MyDialog mMyDialog;

  private boolean recy = false;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_buy_sell;
  }

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    //c2c获取法币交易列表
    getCoinTradeConfigList();
  }

  /**
   * c2c获取法币交易列表
   */
  @SuppressLint("CheckResult")
  private void getCoinTradeConfigList() {

    HashMap<String, String> hashMap = new HashMap<>();
    ApiFactory.getInstance()
        .getCoinTradeConfigList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {

          if (arrayListData.getData() != null) {
            /**
             * 分页查询上架广告
             */
            //买入
            listBuy.clear();
            listBuy.addAll(arrayListData.getData());

            //卖入
            listSell.clear();
            listSell.addAll(arrayListData.getData());

            for (int i = 0; i < listBuy.size(); i++) {
              if (listBuy.get(i).getBuyStatus() == 1) {
                listSell.add(listBuy.get(i));
              }
            }

            if (listSell == null || listSell.size() == 0) {
              imgRelease.setVisibility(View.GONE);
            } else {
              imgRelease.setVisibility(View.VISIBLE);
            }
          } else {
            t(arrayListData.getMsg());
          }
        }, throwable -> {

        });
  }

  @Override
  protected void initUiAndListener() {

    initRadioButton();
    //买入
    fragments.add(new BuyC2CFrament());
    //卖出
    fragments.add(new SellC2CFrament());
    switchFragment(fragments.get(0));
  }

  private void initRadioButton() {
    views.add(rbBuy);
    views.add(rbSell);
    rbGroup.setOnCheckedChangeListener(this);
  }

  private void switchFragment(Fragment fragment) {

    FragmentManager fragmentManager = getSupportFragmentManager();
    for (Fragment fragment1 : fragments) {
      if (fragment1.isAdded()) {
        fragmentManager.beginTransaction().hide(fragment1).commit();
      }
    }

    if (fragment.isAdded()) {
      fragmentManager.beginTransaction().show(fragment).commit();
    } else {
      fragmentManager.beginTransaction().add(R.id.container1, fragment).commit();
    }
  }

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {

    switch (checkedId) {
      case R.id.rb_buy:
        switchFragment(fragments.get(0));
        break;
      case R.id.rb_sell:
        switchFragment(fragments.get(1));
        break;
      default:
        switchFragment(fragments.get(0));
        break;
    }
  }

  public void select(int index) {
    views.get(index).performClick();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @OnClick({
      R.id.comtitlebar, R.id.LLme, R.id.LL_ppowd, R.id.ll_base_toolber, R.id.tra_myzoe_header,
      R.id.img_Release
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.comtitlebar:
        finish();
        break;

      case R.id.LLme:
        if (kv.decodeString("tokenId") == null) {
          t("请登录后操作");
        } else {
          intent.setClass(BuySellActivity.this, MeC2COrderListActivity.class);
          startActivity(intent);
        }
        break;
      case R.id.LL_ppowd:
        if (recy = false) {
          recyclerView.setVisibility(View.GONE);
        } else {
          recy = true;
          recyclerView.setVisibility(View.VISIBLE);
          showPop();
        }
        break;
      case R.id.ll_base_toolber:
        break;
      case R.id.tra_myzoe_header:
        break;

      case R.id.img_Release:
        Release();

        break;
      default:
        break;
    }
  }

  /**
   * 判断是否是 是商家，才能发布广告
   */
  @SuppressLint("CheckResult")
  private void Release() {
    HashMap<String, String> hashMap = new HashMap<>();
    ApiFactory.getInstance()
        .ctcUserInfo(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getCode() == 200) {
            if (data.getData().getMerchantStatus() == 2) {
              View v = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
              Tltle = (TextView) v.findViewById(R.id.tltle);
              btnDimss = (Button) v.findViewById(R.id.btn_dimss);
              btnSell = (Button) v.findViewById(R.id.btn_sell);

              if (kv.decodeString("tokenId") != null) {
                //是否实名是否实名
                authStauts();
              } else {
                t("请登录后操作");
              }
            } else {
              Toast.makeText(BuySellActivity.this, "您未申请商家不能发布广告", Toast.LENGTH_SHORT).show();
            }
          } else {

          }
        }, throwable -> {

        });
  }

  /**
   * 是否实名
   */
  @SuppressLint("CheckResult")
  private void authStauts() {
    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .authStauts(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          if (data.getCode() == 10002 || data.getCode() == 10001) {
            intent = new Intent();
            intent.putExtra("type", "1");
            intent.setClass(BuySellActivity.this, LoginActivity.class);
            startActivity(intent);
            t(getActivity().getResources().getString(R.string.loginno));
          }

          if (data.getData() != null) {

            try {
              if (data.getData().getLevel().equals(3)) {
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                  //c2c查询用户是否绑定支付方式
                  merchdealaccount();
                } else {
                  view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                  Tltle = (TextView) view.findViewById(R.id.tltle);
                  btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                  btnSell = (Button) view.findViewById(R.id.btn_sell);
                  mMyDialog = new MyDialog(BuySellActivity.this, 0, 0, view, R.style.DialogTheme);
                  mMyDialog.setCancelable(true);
                  Tltle.setText("请绑定手机号后交易");
                  mMyDialog.show();
                  btnDimss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      mMyDialog.dismiss();
                    }
                  });
                  btnSell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //手机认证
                      intent.setClass(BuySellActivity.this, PhoneNameActivity.class);
                      startActivity(intent);
                      mMyDialog.dismiss();
                    }
                  });
                }
              } else {
                view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                Tltle = (TextView) view.findViewById(R.id.tltle);
                btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                btnSell = (Button) view.findViewById(R.id.btn_sell);
                mMyDialog = new MyDialog(BuySellActivity.this, 0, 0, view, R.style.DialogTheme);
                mMyDialog.setCancelable(true);
                if (data.getData().getLevel().equals(2)) {
                  if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                    //c2c查询用户是否绑定支付方式
                    merchdealaccount();
                  } else {
                    view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                    Tltle = (TextView) view.findViewById(R.id.tltle);
                    btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                    btnSell = (Button) view.findViewById(R.id.btn_sell);
                    mMyDialog = new MyDialog(BuySellActivity.this, 0, 0, view, R.style.DialogTheme);
                    mMyDialog.setCancelable(true);
                    Tltle.setText("请绑定手机号后交易");
                    mMyDialog.show();
                    btnDimss.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                        mMyDialog.dismiss();
                      }
                    });
                    btnSell.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                        //手机认证
                        intent.setClass(BuySellActivity.this, PhoneNameActivity.class);
                        startActivity(intent);
                        mMyDialog.dismiss();
                      }
                    });
                  }
                } else {
                  view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                  Tltle = (TextView) view.findViewById(R.id.tltle);
                  btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                  btnSell = (Button) view.findViewById(R.id.btn_sell);
                  mMyDialog = new MyDialog(BuySellActivity.this, 0, 0, view, R.style.DialogTheme);
                  mMyDialog.setCancelable(true);
                  Tltle.setText("请完成实名等级2认证");
                  mMyDialog.show();
                  btnDimss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      mMyDialog.dismiss();
                    }
                  });
                  btnSell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if (kv.decodeString("phone") != null && !kv.decodeString("phone")
                          .equals("")) {

                        //实名认证
                        intent.setClass(BuySellActivity.this, RealNameActivity.class);
                        startActivity(intent);
                        mMyDialog.dismiss();
                      } else {
                        mMyDialog.dismiss();
                        view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                        Tltle = (TextView) view.findViewById(R.id.tltle);
                        btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                        btnSell = (Button) view.findViewById(R.id.btn_sell);
                        mMyDialog =
                            new MyDialog(BuySellActivity.this, 0, 0, view, R.style.DialogTheme);
                        mMyDialog.setCancelable(true);
                        Tltle.setText("请绑定手机号后交易");
                        mMyDialog.show();
                        btnDimss.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                            mMyDialog.dismiss();
                          }
                        });
                        btnSell.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(BuySellActivity.this, PhoneNameActivity.class);
                            startActivity(intent);
                            mMyDialog.dismiss();
                          }
                        });
                      }
                    }
                  });
                }
              }
            } catch (Exception e) {
              Toast.makeText(BuySellActivity.this, getResources().getString(R.string.isname),
                  Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  /**
   * c2c查询用户是否绑定支付方式
   */
  @SuppressLint("CheckResult")
  private void merchdealaccount() {

    HashMap<String, String> hashMap = new HashMap();
    ApiFactory.getInstance()
        .merchdealaccount(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null && data.getData().size() != 0) {
            //发布广告
            intent.setClass(BuySellActivity.this, AdvertiseActivity.class);
            startActivity(intent);
          } else {
            view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
            Tltle = (TextView) view.findViewById(R.id.tltle);
            btnDimss = (Button) view.findViewById(R.id.btn_dimss);
            btnSell = (Button) view.findViewById(R.id.btn_sell);
            mMyDialog = new MyDialog(BuySellActivity.this, 0, 0, view, R.style.DialogTheme);
            mMyDialog.setCancelable(true);
            Tltle.setText("请设置收付款方式");
            mMyDialog.show();
            btnDimss.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                mMyDialog.dismiss();
              }
            });
            btnSell.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                //收付款设置
                intent.setClass(BuySellActivity.this, C2CSetMoneyActivity.class);
                startActivity(intent);
                mMyDialog.dismiss();
              }
            });
          }
        }, throwable -> {

        });
  }

  /**
   *
   */
  private void showPop() {

    View view = LayoutInflater.from(this).inflate(R.layout.pop_add, null);
    mPopupWindow = new PopupWindow(this);
    animUtil = new AnimUtil();
    // 设置布局文件
    mPopupWindow.setContentView(view);
    // 为了避免部分机型不显示，我们需要重新设置一下宽高
    mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    // 设置pop透明效果
    mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
    // 设置pop出入动画
    mPopupWindow.setAnimationStyle(R.style.pop_add);
    // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
    mPopupWindow.setFocusable(true);
    // 设置pop可点击，为false点击事件无效，默认为true
    mPopupWindow.setTouchable(true);
    // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
    mPopupWindow.setOutsideTouchable(true);
    // 相对于 + 号正下面，同时可以设置偏移量
    mPopupWindow.showAsDropDown(LLPpowd, -100, 0);
    // 设置pop关闭监听，用于改变背景透明度
    TextView tv_1 = (TextView) view.findViewById(R.id.tv_1);
    tv_1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (type.equals("buy")) {
          //                    加载买入数据
          if (BuyC2CFrament.listC2CBuy != null && BuyC2CFrament.listC2CBuy.size() != 0) {
            BuyC2CFrament.ListAdapter.setNewData(BuyC2CFrament.listC2CBuy);

            recyclerView.setAdapter(BuyC2CFrament.ListAdapter);
          }
        } else {
          //                    加载卖入数据
          if (SellC2CFrament.listC2Sell != null && SellC2CFrament.listC2Sell.size() != 0) {
            SellC2CFrament.ListAdapter.setNewData(SellC2CFrament.listC2Sell);
            recyclerView.setAdapter(SellC2CFrament.ListAdapter);
          }
        }
      }
    });

    TextView tv_2 = (TextView) view.findViewById(R.id.tv_2);
    tv_2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (type.equals("buy")) {
          advertisingvBuy = new ArrayList<Advertisingvo>();
          advertisingvBuy.clear();
          if (BuyC2CFrament.listC2CBuy != null && BuyC2CFrament.listC2CBuy.size() != 0) {
            for (int i = 0; i < BuyC2CFrament.listC2CBuy.size(); i++) {
              if (BuyC2CFrament.listC2CBuy.get(i).getMerchantStatus() == 2) {
                advertisingvBuy.add(BuyC2CFrament.listC2CBuy.get(i));
              }
            }

            BuyC2CFrament.ListAdapter.setNewData(advertisingvBuy);
            recyclerView.setAdapter(BuyC2CFrament.ListAdapter);
          }
        } else {
          advertisingvSell = new ArrayList<Advertisingvo>();
          advertisingvSell.clear();
          if (SellC2CFrament.listC2Sell != null && SellC2CFrament.listC2Sell.size() != 0) {
            for (int i = 0; i < SellC2CFrament.listC2Sell.size(); i++) {
              if (SellC2CFrament.listC2Sell.get(i).getMerchantStatus() == 2) {
                advertisingvSell.add(SellC2CFrament.listC2Sell.get(i));
              }
            }

            SellC2CFrament.ListAdapter.setNewData(advertisingvSell);
            recyclerView.setAdapter(SellC2CFrament.ListAdapter);
          }
        }
      }
    });
  }
}
