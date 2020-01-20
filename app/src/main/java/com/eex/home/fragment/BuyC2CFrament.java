package com.eex.home.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.CommonUtil;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.home.activity.home.C2CSetMoneyActivity;
import com.eex.home.activity.home.PhoneNameActivity;
import com.eex.home.activity.home.PurchaseCurrencyActivity;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.adapter.C2CAdapter;
import com.eex.home.adapter.HorizListAdapter;
import com.eex.home.bean.Advertisingvo;
import com.eex.home.bean.CoinfigList;
import com.eex.home.weight.MyDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
 * @Package: com.overthrow.home.fragment
 * @ClassName: BuyC2CFrament
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 17:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 17:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BuyC2CFrament extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

  private static final String TAG = "BuyC2CFrament";

  /**
   *
   */
  @BindView(R.id.horrecyclerView)
  RecyclerView horrecyclerView;
  /**
   *
   */
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  /**
   *
   */
  @BindView(R.id.swipeRefresh)
  SwipeRefreshLayout swipeRefresh;
  Unbinder unbinder;

  private int pageNo = 1;
  private int pageSize = 15;

  public static C2CAdapter ListAdapter;
  /**
   * 买入
   */
  public static ArrayList<Advertisingvo> listC2CBuy = new ArrayList<>();
  private ArrayList<Advertisingvo> advertisingvos = new ArrayList<>();

  private ArrayList<CoinfigList> list = new ArrayList<>();
  private static ArrayList<CoinfigList> list1 = new ArrayList<>();
  private HorizListAdapter adapter;

  private String name;

  private int mLastCheckedPosition = -1;

  /**
   * dialog
   */
  private TextView Tltle;
  private Button btnDimss;
  private Button btnSell;
  private View view;
  private View view1;

  /**
   * mMyDialog
   */
  private MyDialog mMyDialog;
  private Advertisingvo advertisingvo;

  @Override
  protected void lazyLoad() {
    //c2c获取法币交易列表
    getCoinTradeConfigList();
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

          SwipeUtil.loadCompleted(swipeRefresh);
          if (arrayListData == null) {
            Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
          } else {
            list.clear();
            if (arrayListData.getData() != null) {
              list.addAll(arrayListData.getData());
              /**
               * 分页查询上架广告
               */
              list1.clear();
              for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getBuyStatus() == 1) {
                  list1.add(list.get(i));
                }
              }
              setHozriView();
              if (list1 != null && list1.size() != 0) {
                name = list1.get(0).getTradeCoin();
                if (!name.equals("") && name != null) {
                  getAdvertisingPage(name);
                }
              }
            }
          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  private void setHozriView() {

    adapter = new HorizListAdapter(list1);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    horrecyclerView.setLayoutManager(mLayoutManager);
    horrecyclerView.setAdapter(adapter);
    setItemChecked(0);
    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
          case R.id.button:
            pageNo = 1;
            setItemChecked(position);
            name = list1.get(position).getTradeCoin();
            getAdvertisingPage(list1.get(position).getTradeCoin());
            break;

          default:

            break;
        }
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
   * c2c广告列表
   */
  @SuppressLint("CheckResult")
  private void getAdvertisingPage(String name) {

    HashMap<String, String> hashMap = new HashMap<>();

    hashMap.put("pageSize", pageSize + "");
    hashMap.put("pageNo", pageNo + "");
    hashMap.put("tradeCoin", name);
    hashMap.put("tradeType", "1");
    final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
    ApiFactory.getInstance()
        .getAdvertisingPage(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          dialog.dismiss();
          SwipeUtil.loadCompleted(swipeRefresh);
          if (data.getData().getPageNo() > 1) {
            advertisingvos.clear();
            advertisingvos.addAll(data.getData().getResultList());
            ListAdapter.addData(advertisingvos);
          } else {
            if (data.isStauts() == false) {
              Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
              return;
            }
            listC2CBuy.clear();
            if (data.getData().getResultList() != null) {
              listC2CBuy.addAll(data.getData().getResultList());
              pageNo = 2;
            }

            Collections.sort(listC2CBuy, new Comparator<Advertisingvo>() {
              @Override
              public int compare(Advertisingvo o1, Advertisingvo o2) {

                if (o1.getMerchantStatus() == 2) {
                  return 1;
                }
                if (o1.getMerchantStatus() == 1) {
                  return -1;
                }
                return o2.getMerchantStatus().compareTo(o1.getMerchantStatus()) < 0 ? 1 : -1;
              }
            });

            Collections.reverse(listC2CBuy);
            setListView(listC2CBuy);
          }
        }, throwable -> {
          dialog.dismiss();
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  private void setListView(final List<Advertisingvo> listC2CBuy) {

    ListAdapter = new C2CAdapter(R.layout.item_c2c_frament, listC2CBuy);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    ListAdapter.setEmptyView(new EmptyView(getActivity()));
    recyclerView.setAdapter(ListAdapter);

    recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
      @Override
      public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
        if (CommonUtil.isFastClick()) {
          switch (view.getId()) {
            case R.id.btn_buy:
              if (kv.decodeString("tokenId") == null) {
                Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
              } else {
                view1 = getActivity().getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                Tltle = (TextView) view1.findViewById(R.id.tltle);
                btnDimss = (Button) view1.findViewById(R.id.btn_dimss);
                btnSell = (Button) view1.findViewById(R.id.btn_sell);
                //是否实名
                authStauts(position);
              }
              break;

            default:

              break;
          }
        }
      }
    });
  }

  @Override
  protected void initUiAndListener() {

    SwipeUtil.init(swipeRefresh);
    swipeRefresh.setOnRefreshListener(this);
  }

  /**
   * 是否实名
   */
  @SuppressLint("CheckResult")
  private void authStauts(final int position) {
    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .authStauts(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          if (data.getCode() == 10002 || data.getCode() == 10001) {
            intent = new Intent();
            intent.putExtra("flage", "2");
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent);
            t(getActivity().getResources().getString(R.string.loginno));
          }

          if (data.getData() != null) {

            try {
              if (data.getData().getLevel().equals(3)) {
                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                  //c2c查询用户是否绑定支付方式
                  merchdealaccount(position);
                } else {
                  View view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                  Tltle = (TextView) view.findViewById(R.id.tltle);
                  btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                  btnSell = (Button) view.findViewById(R.id.btn_sell);
                  mMyDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
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
                      intent.setClass(getActivity(), PhoneNameActivity.class);
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
                mMyDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
                mMyDialog.setCancelable(true);
                if (data.getData().getLevel().equals(2)) {
                  if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                    //c2c查询用户是否绑定支付方式
                    merchdealaccount(position);
                  } else {
                    view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                    Tltle = (TextView) view.findViewById(R.id.tltle);
                    btnDimss = (Button) view.findViewById(R.id.btn_dimss);
                    btnSell = (Button) view.findViewById(R.id.btn_sell);
                    mMyDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
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
                        intent.setClass(getActivity(), PhoneNameActivity.class);
                        startActivity(intent);
                        mMyDialog.dismiss();
                      }
                    });
                  }
                }
              }
            } catch (Exception e) {
              Toast.makeText(getActivity(), getResources().getString(R.string.isname),
                  Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  /**
   * c2c查询用户是否绑定支付方式
   */
  @SuppressLint("CheckResult")
  private void merchdealaccount(int position) {

    HashMap<String, String> hashMap = new HashMap();
    ApiFactory.getInstance()
        .merchdealaccount(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null && data.getData().size() != 0) {
            //C2C购买币种页面
            intent.putExtra("id", listC2CBuy.get(position).getId());
            intent.setClass(getActivity(), PurchaseCurrencyActivity.class);
            startActivity(intent);
          } else {
            view = getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
            Tltle = (TextView) view.findViewById(R.id.tltle);
            btnDimss = (Button) view.findViewById(R.id.btn_dimss);
            btnSell = (Button) view.findViewById(R.id.btn_sell);
            mMyDialog = new MyDialog(getActivity(), 0, 0, view, R.style.DialogTheme);
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
                intent.setClass(getActivity(), C2CSetMoneyActivity.class);
                startActivity(intent);
                mMyDialog.dismiss();
              }
            });
          }
        }, throwable -> {

        });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_c2c_buysell;
  }

  @Override
  public void onRefresh() {
    pageNo = 1;
    //c2c获取法币交易列表
    getCoinTradeConfigList();
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
}



