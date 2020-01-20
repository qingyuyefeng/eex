package com.eex.mvp.market.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.EmptyView;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.constant.Keys;
import com.eex.home.bean.MainData;
import com.eex.market.weight.GlobalParms;
import com.eex.mvp.mainpage.MainActivity;
import com.eex.mvp.mainpage.MainContract;
import com.eex.mvp.market.bean.DealPairList;
import com.eex.mvp.market.bean.dealCoinDTOList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

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
 * @Package: com.overthrow.mvp.market.fragment
 * @ClassName: FuturesFragment
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 9:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 9:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class FuturesFragment extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

  @BindView(R.id.futures_recyclerView)
  RecyclerView futuresRecyclerView;
  @BindView(R.id.container)
  FrameLayout container;
  @BindView(R.id.market_biname)
  TextView marketBiname;
  @BindView(R.id.market_name_image)
  ImageView marketNameImage;
  @BindView(R.id.market_name)
  LinearLayout marketName;
  @BindView(R.id.market_bilatest_price)
  TextView marketBilatestPrice;
  @BindView(R.id.market_volume)
  TextView marketVolume;
  @BindView(R.id.market_latest_price_image)
  ImageView marketLatestPriceImage;
  @BindView(R.id.market_latest_price)
  LinearLayout marketLatestPrice;
  @BindView(R.id.market_bifloating)
  TextView marketBifloating;
  @BindView(R.id.market_floating_image)
  ImageView marketFloatingImage;
  @BindView(R.id.market_floating)
  LinearLayout marketFloating;
  @BindView(R.id.market_biapplies)
  TextView marketBiapplies;
  @BindView(R.id.market_applies_image)
  ImageView marketAppliesImage;
  @BindView(R.id.market_applies)
  LinearLayout marketApplies;
  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;
  @BindView(R.id.cnye)
  LinearLayout cnye;
  @BindView(R.id.swipeRefresh)
  SwipeRefreshLayout swipeRefresh;
  Unbinder unbinder;

  private String dealPair = "";
  private String MoneyType = "NO";

  //上下状态
  private String Type = "down";
  //上下切换按钮
  private String isType = "成交量";
  //button状态
  private String ButtonType = "期货";

  private ArrayList<DealPairList> list = new ArrayList<>();
  private ArrayList<DealPairList> list1 = new ArrayList<>();
  private ArrayList<MainData> data = new ArrayList<>();
  private ArrayList<dealCoinDTOList> mdata = new ArrayList<>();
  private ArrayList<String> newlist = new ArrayList<>();
  private FuturesMarketAdapter adapter;
  private FuturesAdapter futuresAdapter;

  private BigDecimal newData1, newData11;
  private BigDecimal newData2, newData22;
  private BigDecimal newData3, newData33;
  private BigDecimal newData4, newData44;

  private boolean typeAd = true;

  private String ckType1 = "2";
  private String ckType2 = "1";
  private String ckType3 = "1";
  private String ckType4 = "1";

  private int mLastCheckedPosition = -1;

  @Override
  protected void refreshData(Bundle savedInstanceState) {

    net();
  }

  @SuppressLint("CheckResult")
  private void net() {

    HashMap<String, String> hashMap = new HashMap<>();
    ApiFactory.getInstance()
        .getfutureDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          SwipeUtil.loadCompleted(swipeRefresh);
          try {
            if (data.isStauts() == true) {
              dealPair = "";
              list.clear();
              list.addAll(data.getData());
              futuresAdapter.notifyDataSetChanged();

              list1.clear();
              for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDealCoinDTOList() != null) {
                  list1.add(list.get(i));
                }
              }

              for (int i = 0; i < list.get(0).getDealCoinDTOList().size(); i++) {
                if (!list.get(0).getDealCoinDTOList().equals("")) {
                  dealPair += list.get(0).getDealCoinDTOList().get(i).getDealPair() + ",";
                }
              }

              getIndexMaketList(dealPair);
              Log.e(TAG, "net: " + dealPair);
            } else {
              t(data.getMsg());
            }
          } catch (Exception e) {

          }
        }, throwable -> {
          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  @SuppressLint("CheckResult")
  private void getIndexMaketList(String dealPair) {

    HashMap<String, String> hashMap = new HashMap<>();

    hashMap.put("delkeys", dealPair);

    ApiFactory.getInstance()
        .getIndexMaketList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {

          SwipeUtil.loadCompleted(swipeRefresh);
          if (arrayListData.getCode() == 200) {
            data.clear();

            //kv.decodeString("list", data.);
            Collections.sort(data, new Comparator<MainData>() {
              @Override
              public int compare(MainData o1, MainData o2) {
                if (o2.getDealNum() == null) {
                  return -1;
                }
                if (o1.getDealNum() == null) {
                  return 1;
                }
                if (o2.getNewPrice() == null) {
                  return -1;
                }
                if (o1.getNewPrice() == null) {
                  return 1;
                }

                return o2.getDealNum()
                    .multiply(o2.getNewPrice())
                    .compareTo(o1.getDealNum().multiply(o1.getNewPrice())) > 0 ? 1 : -1;
              }
            });

            data.addAll(arrayListData.getData());

            adapter.notifyDataSetChanged();
          } else {
            t(arrayListData.getMsg());
          }
        }, throwable -> {

          SwipeUtil.loadCompleted(swipeRefresh);
        });
  }

  /**
   * 获取实时价格
   */
  private void webSoket() {

    RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "futurewebsocket/ALL_ALL_INDEX_PRICE")
        .compose(RxSchedulers.io_main())
        .subscribe(new WebSocketSubscriber2<MainData>() {
          @Override
          protected void onMessage(MainData pageData) {
            MoneyType = "NO";
            if (pageData != null) {
              try {
                for (int i = 0; i < data.size(); i++) {
                  if (data.get(i).getDelKey().equals(pageData.getDelKey())) {
                    data.get(i).setDealNum(pageData.getDealNum());
                    data.get(i).setDelKey(pageData.getDelKey());
                    data.get(i).setFooPrice(pageData.getFooPrice());
                    data.get(i).setHigePrice(pageData.getHigePrice());
                    data.get(i).setNewPrice(pageData.getNewPrice());
                    data.get(i).setUsdtCny(pageData.getUsdtCny());
                    data.get(i).setLastPrice(pageData.getLastPrice());
                  }
                }

                try {
                  inviewdata(ButtonType, data);
                  adapter.setNewData(data);
                } catch (Exception e) {

                }
              } catch (Exception e) {
                Log.e("USDTtype", "无更新");
              }
            }
          }

          @Override
          protected void onReconnect() {
            Log.d("USDTFragment", "开始+1");
          }
        });
  }

  /**
   * 在webSoket 根据需求来 排序
   */
  private void inviewdata(String buttonType, ArrayList<MainData> data) {
    if (ButtonType.equals("自选")) {
      if (isType.equals("名称")) {
        if (Type.equals("down")) {
          returnNameList1(data);
        } else {
          returnNameList(data);
        }
        return;
      }

      if (isType.equals("成交量")) {
        if (Type.equals("down")) {
          //成交量从大到小
          returnNuberList1(data);
        } else {
          //成交量从小到大
          returnNuberList(data);
        }
        return;
      }
      if (isType.equals("最新价格")) {
        if (Type.equals("down")) {
          returnNewPriceList(data);
        } else {
          returnNewPriceList1(data);
        }
        return;
      }

      if (isType.equals("浮动率")) {
        if (Type.equals("down")) {
          Floatingrate(data);
        } else {
          Floatingrate1(data);
        }
        return;
      }

      if (isType.equals("涨跌幅")) {
        if (Type.equals("down")) {
          returnUPList(data);
        } else {
          returnDownList(data);
        }
        return;
      }
      return;
    }
    //USDT
    if (ButtonType.equals("USDT")) {
      if (isType.equals("名称")) {
        if (Type.equals("down")) {
          returnNameList(data);
        } else {
          returnNameList1(data);
        }
        return;
      }
      if (isType.equals("成交量")) {
        if (Type.equals("down")) {
          returnNuberList1(data);
        } else {
          returnNuberList(data);
        }
        return;
      }
      if (isType.equals("最新价格")) {
        if (Type.equals("down")) {
          returnNewPriceList(data);
        } else {
          returnNewPriceList1(data);
        }
        return;
      }
      if (isType.equals("浮动率")) {
        if (Type.equals("down")) {
          Floatingrate(data);
        } else {
          Floatingrate1(data);
        }
        return;
      }
      if (isType.equals("涨跌幅")) {
        if (Type.equals("down")) {
          returnUPList(data);
        } else {
          returnDownList(data);
        }
        return;
      }
      return;
    }
    //期货
    if (ButtonType.equals("期货")) {
      if (isType.equals("名称")) {
        if (Type.equals("down")) {
          returnNameList(data);
        } else {
          returnNameList1(data);
        }
        return;
      }
      if (isType.equals("成交量")) {
        if (Type.equals("down")) {
          returnNuberList1(data);
        } else {
          returnNuberList(data);
        }
        return;
      }
      if (isType.equals("最新价格")) {
        if (Type.equals("down")) {
          returnNewPriceList(data);
        } else {
          returnNewPriceList1(data);
        }
        return;
      }
      if (isType.equals("浮动率")) {
        if (Type.equals("down")) {
          Floatingrate(data);
        } else {
          Floatingrate1(data);
        }
        return;
      }
      if (isType.equals("涨跌幅")) {
        if (Type.equals("down")) {
          returnUPList(data);
        } else {
          returnDownList(data);
        }
        return;
      }
      return;
    }
  }

  /**
   * 浮动率
   * <p>
   * 小 到 大
   */
  private void Floatingrate(ArrayList<MainData> data) {
    Collections.sort(data, new Comparator<MainData>() {
      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getHigePrice() == null) {
          return 1;
        }
        if (o1.getHigePrice() == null) {
          return -1;
        }
        if (o1.getFooPrice() == null) {
          return -1;
        }
        if (o2.getFooPrice() == null) {
          return 1;
        }

        try {
          //          (最高价 - 最低价)/最低价 * 100%
          //((high-low)/low*100)
          BigDecimal high = o1.getHigePrice();
          BigDecimal low = o1.getFooPrice();
          BigDecimal s1 = high.subtract(low);
          BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
          BigDecimal s3 = s2.multiply(new BigDecimal(100));
          newData33 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        } catch (Exception e) {
          newData33 = new BigDecimal(0);
        }
        try {
          //          (最高价 - 最低价)/最低价 * 100%
          //((high-low)/low*100)
          BigDecimal high = o2.getHigePrice();
          BigDecimal low = o2.getFooPrice();
          BigDecimal s1 = high.subtract(low);
          BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
          BigDecimal s3 = s2.multiply(new BigDecimal(100));
          newData44 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        } catch (Exception e) {
          newData44 = new BigDecimal(0);
        }

        return newData33.compareTo(newData44) > 0 ? 1 : -1;
      }
    });
    adapter.setNewData(data);
  }

  /**
   * 浮动率  大 到 小
   */
  private void Floatingrate1(ArrayList<MainData> data) {

    Collections.sort(data, new Comparator<MainData>() {
      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getHigePrice() == null) {
          return -1;
        }
        if (o1.getHigePrice() == null) {
          return 1;
        }
        if (o1.getFooPrice() == null) {
          return 1;
        }
        if (o2.getFooPrice() == null) {
          return -1;
        }

        try {
          //          (最高价 - 最低价)/最低价 * 100%
          //((high-low)/low*100)
          BigDecimal high = o1.getHigePrice();
          BigDecimal low = o1.getFooPrice();
          BigDecimal s1 = high.subtract(low);
          BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
          BigDecimal s3 = s2.multiply(new BigDecimal(100));
          newData3 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        } catch (Exception e) {
          newData3 = new BigDecimal(0);
        }
        try {
          //          (最高价 - 最低价)/最低价 * 100%
          //((high-low)/low*100)
          BigDecimal high = o2.getHigePrice();
          BigDecimal low = o2.getFooPrice();
          BigDecimal s1 = high.subtract(low);
          BigDecimal s2 = s1.divide(low, 2, BigDecimal.ROUND_HALF_UP);
          BigDecimal s3 = s2.multiply(new BigDecimal(100));
          newData4 = s3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        } catch (Exception e) {
          newData4 = new BigDecimal(0);
        }

        return newData3.compareTo(newData4) > 0 ? 1 : -1;
      }
    });
    adapter.setNewData(data);
  }

  /**
   * 按最新成交价量排序小-大
   */
  private void returnNewPriceList1(ArrayList<MainData> data) {
    Collections.sort(data, new Comparator<MainData>() {

      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getDealNum() == null) {
          return -1;
        }
        if (o1.getDealNum() == null) {
          return 1;
        }
        return o2.getDealNum().compareTo(o1.getDealNum()) > 0 ? 1 : -1;
      }
    });
    adapter.setNewData(data);
  }

  /**
   * 按最新成交价量排序大-小
   */
  private void returnNewPriceList(ArrayList<MainData> data) {
    Collections.sort(data, new Comparator<MainData>() {

      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getDealNum() == null) {
          return -1;
        }
        if (o1.getDealNum() == null) {
          return 1;
        }
        return o2.getDealNum().compareTo(o1.getDealNum()) > 0 ? 1 : -1;
      }
    });
    Collections.reverse(data);
    adapter.setNewData(data);
  }

  /**
   * A-B名称比较
   */
  private void returnNameList(ArrayList<MainData> data) {
    Collections.sort(data, new Comparator<MainData>() {
      @Override
      public int compare(MainData o1, MainData o2) {
        return o1.getDelKey().compareTo(o2.getDelKey());
      }
    });
    adapter.setNewData(data);
  }

  /**
   * B-A名称比较
   */
  private void returnNameList1(ArrayList<MainData> data) {
    Collections.sort(data, new Comparator<MainData>() {
      @Override
      public int compare(MainData o1, MainData o2) {
        return o2.getDelKey().compareTo(o1.getDelKey());
      }
    });
    adapter.setNewData(data);
  }

  /**
   * 按成交量排序小-大
   */
  private void returnNuberList(ArrayList<MainData> data) {
    if (data != null) {
      Collections.sort(data, new Comparator<MainData>() {

        @Override
        public int compare(MainData o1, MainData o2) {
          if (o2.getDealNum() == null) {
            return -1;
          }
          if (o1.getDealNum() == null) {
            return 1;
          }
          if (o2.getNewPrice() == null) {
            return -1;
          }
          if (o1.getNewPrice() == null) {
            return 1;
          }
          return o2.getDealNum()
              .multiply(o2.getNewPrice())
              .compareTo(o1.getDealNum().multiply(o1.getNewPrice())) > 0 ? 1 : -1;
        }
      });
      Collections.reverse(data);

      adapter.setNewData(data);
    }
  }

  /**
   * 按成交量排序大-小
   */
  private void returnNuberList1(ArrayList<MainData> data) {

    Collections.sort(data, new Comparator<MainData>() {

      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getDealNum() == null) {
          return -1;
        }
        if (o1.getDealNum() == null) {
          return 1;
        }
        if (o2.getNewPrice() == null) {
          return -1;
        }
        if (o1.getNewPrice() == null) {
          return 1;
        }
        return o2.getDealNum()
            .multiply(o2.getNewPrice())
            .compareTo(o1.getDealNum().multiply(o1.getNewPrice())) > 0 ? 1 : -1;
      }
    });
    adapter.setNewData(data);
  }

  /**
   * 按涨排序大
   */
  private void returnUPList(ArrayList<MainData> data) {

    Collections.sort(data, new Comparator<MainData>() {
      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getNewPrice() == null) {
          return 1;
        }
        if (o1.getNewPrice() == null) {
          return -1;
        }
        if (o1.getLastPrice() == null) {
          return -1;
        }
        if (o2.getLastPrice() == null) {
          return 1;
        }
        try {
          BigDecimal money1 =
              o1.getNewPrice().divide(o1.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
          newData11 = money1.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
        } catch (Exception e) {
          newData11 = new BigDecimal(0);
        }

        try {
          BigDecimal money2 =
              o2.getNewPrice().divide(o2.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
          newData22 = money2.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
        } catch (Exception e) {
          newData22 = new BigDecimal(0);
        }
        return newData11.compareTo(newData22) > 0 ? 1 : -1;
      }
    });

    adapter.setNewData(data);
  }

  /**
   * 按跌幅排序大-小
   */
  private void returnDownList(ArrayList<MainData> data) {

    Collections.sort(data, new Comparator<MainData>() {
      @Override
      public int compare(MainData o1, MainData o2) {
        if (o2.getNewPrice() == null) {
          return -1;
        }
        if (o1.getNewPrice() == null) {
          return 1;
        }
        if (o1.getLastPrice() == null) {
          return 1;
        }
        if (o2.getLastPrice() == null) {
          return -1;
        }
        BigDecimal money1 =
            o1.getNewPrice().divide(o1.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
        newData1 = money1.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

        BigDecimal money2 =
            o2.getNewPrice().divide(o2.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
        newData2 = money2.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

        return newData2.compareTo(newData1) > 0 ? 1 : -1;
      }
    });
    adapter.setNewData(data);
  }

  @Override
  protected void initUiAndListener() {
    SwipeUtil.init(swipeRefresh);
    swipeRefresh.setOnRefreshListener(this);

    futuresAdapter = new FuturesAdapter(list);

    futuresRecyclerView.setHasFixedSize(true);
    futuresRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    futuresRecyclerView.setAdapter(futuresAdapter);
    setItemChecked(0);
    futuresRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
      @Override
      public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        setItemChecked(position);
        mdata.clear();
        mdata.addAll(list.get(position).getDealCoinDTOList());
        dealPair = "";
        for (int i = 0; i < mdata.size(); i++) {
          dealPair += mdata.get(i).getDealPair() + ",";
        }
        getIndexMaketList(dealPair);
      }
    });

    adapter = new FuturesMarketAdapter(data);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(this);
    adapter.setEmptyView(new EmptyView(getActivity()));

    recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
      @Override
      public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.PARAM_PAIR_DATA, data.get(position).getDelKey());
        if (getActivity() instanceof MainActivity) {
          Activity activity = (MainActivity) getActivity();
          bundle.putInt(Keys.TRANS_SELECT, 1);
          ((MainContract.View) activity).selectTab(2, bundle);
        }
      }
    });
    webSoket();
  }

  private void setItemChecked(int position) {

    if (mLastCheckedPosition == position) {
      return;
    }
    futuresAdapter.mBooleanArray.put(position, true);
    if (mLastCheckedPosition > -1) {
      futuresAdapter.mBooleanArray.put(mLastCheckedPosition, false);
      futuresAdapter.notifyItemChanged(mLastCheckedPosition);
    }
    futuresAdapter.notifyDataSetChanged();
    mLastCheckedPosition = position;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.re_futures_fragment;
  }

  @Override
  protected void lazyLoad() {

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

  @Override
  public void onDestroy() {
    super.onDestroy();

    Disposable disposable1 =
        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/ALL_ALL").subscribe();
    if (disposable1 != null && !disposable1.isDisposed()) {
      disposable1.dispose();
    }
  }

  @Override
  public void onResume() {
    super.onResume();

    //首页list集合
    net();

    //
    if (MoneyType.equals("YES")) {
      webSoket();
    }
  }

  @Override
  public void onRefresh() {
    //首页list集合
    net();
    setItemChecked(0);
    //
    if (MoneyType.equals("YES")) {
      webSoket();
    }
  }

  @OnClick({
      R.id.market_name, R.id.market_volume, R.id.market_latest_price, R.id.market_floating,
      R.id.market_applies
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.market_name:

        isType = "名称";
        //A--B
        if (ckType1.equals("1")) {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

          //图标隐藏
          marketNameImage.setVisibility(View.VISIBLE);
          marketNameImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_top));

          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.GONE);
          ckType1 = "2";
          returnNameList(data);
          Type = "down";
          //B--A
        } else {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

          //图标隐藏
          marketNameImage.setVisibility(View.VISIBLE);
          marketNameImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_buttom));
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.GONE);

          ckType1 = "1";
          returnNameList1(data);
          Type = "up";
        }

        break;

      case R.id.market_volume:

        isType = "成交量";
        //A--B
        if (ckType1.equals("1")) {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

          //图标隐藏
          marketNameImage.setVisibility(View.VISIBLE);
          marketNameImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_top));
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.GONE);
          ckType1 = "2";
          returnNuberList(data);
          Type = "down";
          //B--A
        } else {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

          //图标隐藏
          marketNameImage.setVisibility(View.VISIBLE);
          marketNameImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_buttom));
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.GONE);

          ckType1 = "1";
          returnNuberList1(data);
          Type = "up";
        }

        break;

      case R.id.market_latest_price:
        isType = "最新价格";
        if (ckType3.equals("1")) {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.text_color));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          //图标隐藏
          marketNameImage.setVisibility(View.GONE);
          marketLatestPriceImage.setVisibility(View.VISIBLE);
          marketLatestPriceImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_top));
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.GONE);

          returnNewPriceList(data);
          Type = "down";
          ckType3 = "2";
          //小-大
        } else {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.text_color));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

          //图标隐藏
          marketNameImage.setVisibility(View.GONE);
          marketLatestPriceImage.setVisibility(View.VISIBLE);
          marketLatestPriceImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_buttom));
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.GONE);

          ckType3 = "1";
          returnNewPriceList1(data);
          Type = "up";
        }
        break;
      case R.id.market_floating:
        isType = "浮动率";
        //大-小
        if (ckType2.equals("1")) {

          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));

          //图标隐藏
          marketNameImage.setVisibility(View.GONE);
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.VISIBLE);
          marketFloatingImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_top));
          marketAppliesImage.setVisibility(View.GONE);

          ckType2 = "2";
          returnNuberList(data);
          Type = "up";

          //小到大
        } else {

          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          //图标隐藏
          marketNameImage.setVisibility(View.GONE);
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.VISIBLE);
          marketFloatingImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_buttom));
          marketAppliesImage.setVisibility(View.GONE);
          ckType2 = "1";
          returnNuberList1(data);
          Type = "down";
        }

        break;
      case R.id.market_applies:
        isType = "涨跌幅";
        //涨
        if (ckType4.equals("1")) {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          //图标隐藏
          marketNameImage.setVisibility(View.GONE);
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.VISIBLE);
          marketAppliesImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_top));
          ckType4 = "2";
          returnUPList(data);
          Type = "down";
          //跌
        } else {
          //字体color
          marketBiname.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketVolume.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBilatestPrice.setTextColor(
              getActivity().getResources().getColor(R.color.main_title2));
          marketBifloating.setTextColor(getActivity().getResources().getColor(R.color.main_title2));
          marketBiapplies.setTextColor(getActivity().getResources().getColor(R.color.text_color));
          //图标隐藏
          marketNameImage.setVisibility(View.GONE);
          marketLatestPriceImage.setVisibility(View.GONE);
          marketFloatingImage.setVisibility(View.GONE);
          marketAppliesImage.setVisibility(View.VISIBLE);
          marketAppliesImage.setImageDrawable(
              getActivity().getResources().getDrawable(R.mipmap.market_buttom));
          ckType4 = "1";
          returnDownList(data);
          Type = "up";
        }
        break;

      default:
        break;
    }
  }

  @Override
  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    Bundle bundle = new Bundle();
    bundle.putString(Keys.PARAM_PAIR_DATA, data.get(position).getDelKey());
    if (getActivity() instanceof MainActivity) {
      Activity activity = (MainActivity) getActivity();
      bundle.putInt(Keys.TRANS_SELECT, 1);
      ((MainContract.View) activity).selectTab(2, bundle);
    }
  }
}
