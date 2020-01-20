package com.eex.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.view.ViewPagerFragmentAdapter;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.home.activity.home.BuySellActivity;
import com.eex.home.activity.home.CTwoCMainActivity;
import com.eex.home.activity.home.ConducMoneyActivity;
import com.eex.home.activity.home.MiningDetailsActivity;
import com.eex.home.activity.home.NewsDetailsActivity;
import com.eex.home.activity.home.SecondkillActivity;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.home.bean.Subordinate;
import com.eex.home.bean.SubordinateUtils;
import com.eex.market.activity.DonationActivity;
import com.eex.notice.bean.IndustryBean;
import com.eex.notice.bean.IndustryBeanUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import io.reactivex.disposables.Disposable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.bakumon.library.adapter.SimpleBulletinAdapter;
import me.bakumon.library.view.BulletinView;

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
 * @ClassName: Homefragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/23 11:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 11:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomeFragment extends BaseFragment implements OnBannerListener {

  private static final String TAG = "Homefragment";

  /**
   * Banner 图
   */
  @BindView(R.id.home_banner)
  Banner homeBanner;
  /**
   * 公告轮播
   */
  @BindView(R.id.home_LampView)
  BulletinView homeLampView;
  /**
   * 即将开启
   */
  @BindView(R.id.home_stayMoney)
  TextView homeStayMoney;
  /**
   * 24h待分红累计约
   */
  @BindView(R.id.home_stayName)
  TextView homeStayName;
  /**
   * 即将开启
   */
  @BindView(R.id.home_singleMoney)
  TextView homeSingleMoney;
  /**
   * 24h单币分红约
   */
  @BindView(R.id.home_singleName)
  TextView homeSingleName;
  /**
   * 即将开启
   */
  @BindView(R.id.home_circulationMoney)
  TextView homeCirculationMoney;
  /**
   * EBT流通量
   */
  @BindView(R.id.circulationName)
  TextView circulationName;
  /**
   * 即将开启
   */
  @BindView(R.id.home_MineralMoney)
  TextView homeMineralMoney;
  /**
   * 矿池量
   */
  @BindView(R.id.home_MineralName)
  TextView homeMineralName;
  /**
   * 即将开启
   */
  @BindView(R.id.home_CalculationMoney)
  TextView homeCalculationMoney;
  /**
   * 当前算法
   */
  @BindView(R.id.home_CalculationName)
  TextView homeCalculationName;
  /**
   * 即将开启
   */
  @BindView(R.id.home_nuberMoney)
  TextView homeNuberMoney;
  /**
   * 当日产量
   */
  @BindView(R.id.home_nuberName)
  TextView homeNuberName;
  /**
   * 长连接
   */
  @BindView(R.id.home_Mining)
  LinearLayout homeMining;
  /**
   * c2c
   */
  @BindView(R.id.home_C2C)
  LinearLayout homeC2C;
  /**
   * 秒杀专区
   */
  @BindView(R.id.home_Secondkill)
  LinearLayout homeSecondkill;
  /**
   * 锁仓理财
   */
  @BindView(R.id.home_Money_int)
  LinearLayout homeMoneyInt;
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
  /**
   *
   */
  Unbinder unbinder;
  /**
   * btcName
   */
  @BindView(R.id.btc_name)
  TextView btcName;
  /**
   * btcNewPrice
   */
  @BindView(R.id.btc_newPrice)
  TextView btcNewPrice;
  /**
   * btcFooPrice
   */
  @BindView(R.id.btc_fooPrice)
  TextView btcFooPrice;
  /**
   * btcLastPrice
   */
  @BindView(R.id.btc_lastPrice)
  TextView btcLastPrice;
  /**
   * btcLinear
   */
  @BindView(R.id.btc_linear)
  LinearLayout btcLinear;
  /**
   * ethName
   */
  @BindView(R.id.eth_name)
  TextView ethName;
  /**
   * ethNewPrice
   */
  @BindView(R.id.eth_newPrice)
  TextView ethNewPrice;
  /**
   * ethFooPrice
   */
  @BindView(R.id.eth_fooPrice)
  TextView ethFooPrice;
  /**
   * ethLastPrice
   */
  @BindView(R.id.eth_lastPrice)
  TextView ethLastPrice;
  /**
   * ethLinear
   */
  @BindView(R.id.eth_linear)
  LinearLayout ethLinear;
  /**
   * ebxName
   */
  @BindView(R.id.ebx_name)
  TextView ebxName;
  /**
   * ebxNewPrice
   */
  @BindView(R.id.ebx_newPrice)
  TextView ebxNewPrice;
  /**
   * ebxFooPrice
   */
  @BindView(R.id.ebx_fooPrice)
  TextView ebxFooPrice;
  /**
   * ebxLastPrice
   */
  @BindView(R.id.ebx_lastPrice)
  TextView ebxLastPrice;
  /**
   * ebxLinear
   */
  @BindView(R.id.ebx_linear)
  LinearLayout ebxLinear;

  private Intent intent = new Intent();

  private String[] titles = { "24h交易排行榜", "24h涨幅榜" };
  private ArrayList<Fragment> fragments = new ArrayList<>();

  private String dealPair = "";
  private ArrayList<MainList> list = new ArrayList<>();

  private ArrayList<MainData> maindata = new ArrayList<>();

  @Override
  protected void lazyLoad() {

    //轮播图
    net();
    //公告轮播
    init();

    //首页list集合
    getDealPairList();
    //获取实时价格
    webSoket();
  }

  protected void refreshData(Bundle savedInstanceState) {

    //轮播图
    net();
    //公告轮播
    init();

    //首页list集合
    getDealPairList();
    //获取实时价格
    webSoket();
  }

  /**
   * 首页list集合
   */
  @SuppressLint("CheckResult")
  private void getDealPairList() {

    HashMap<String, String> hashMap = new HashMap<>();

    ApiFactory.getInstance()
        .getDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {
          try {
            if (data.isStauts() == true) {
              dealPair = "";
              list.clear();
              list.addAll(data.getData());
              for (int i = 0; i < list.size(); i++) {

                dealPair += list.get(i).getDealPair() + ",";
              }
              getIndexMaketList(dealPair);
            } else {
              t(data.getMsg());
            }
          } catch (Exception e) {

          }
        }, throwable -> {

        });
  }

  /***
   *
   * 获取交易对详情
   * @param dealPair
   */
  @SuppressLint("CheckResult")
  private void getIndexMaketList(String dealPair) {

    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("delkeys", dealPair);
    ApiFactory.getInstance()
        .getIndexMaketList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(arrayListData -> {

          if (arrayListData.getCode() == 200) {

            maindata.clear();
            maindata.addAll(arrayListData.getData());
            getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                for (int i = 0; i < arrayListData.getData().size(); i++) {

                  if (arrayListData.getData().get(i).getDelKey().equals("BTC_USDT")) {
                    btcName.setText(arrayListData.getData().get(i).getDelKey());

                    if (arrayListData.getData().get(i).getNewPrice() != null) {
                      btcNewPrice.setText(arrayListData.getData()
                          .get(i)
                          .getNewPrice()
                          .setScale(4, BigDecimal.ROUND_DOWN)
                          .toString());
                    } else {
                      btcNewPrice.setText("0");
                    }

                    try {
                      if (arrayListData.getData().get(i).getNewPrice() != null
                          && arrayListData.getData().get(i).getLastPrice() != null) {
                        BigDecimal money = arrayListData.getData()
                            .get(i)
                            .getNewPrice()
                            .divide(arrayListData.getData().get(i).getLastPrice(), 10,
                                BigDecimal.ROUND_HALF_EVEN);
                        BigDecimal newData =
                            money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                        if (newData.compareTo(BigDecimal.ZERO) == 1) {
                          btcFooPrice.setTextColor(
                              getActivity().getResources().getColor(R.color.lime));
                          btcFooPrice.setText(
                              "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                        } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                          btcFooPrice.setTextColor(
                              getActivity().getResources().getColor(R.color.redred));
                          btcFooPrice.setText(newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                        } else {

                          btcFooPrice.setText("0.00%");
                        }
                      } else {
                        btcFooPrice.setText("0");
                      }
                    } catch (Exception e) {

                    }

                    if (arrayListData.getData().get(i).getUsdtCny() != null) {
                      btcLastPrice.setText("≈" + arrayListData.getData()
                          .get(i)
                          .getUsdtCny()
                          .setScale(4, BigDecimal.ROUND_DOWN) + "CNY");
                    } else {
                      btcLastPrice.setText("0");
                    }
                  }

                  if (arrayListData.getData().get(i).getDelKey().equals("ETH_USDT")) {

                    ethName.setText(arrayListData.getData().get(i).getDelKey());

                    if (arrayListData.getData().get(i).getNewPrice() != null) {

                      ethNewPrice.setText(arrayListData.getData()
                          .get(i)
                          .getNewPrice()
                          .setScale(4, BigDecimal.ROUND_DOWN)
                          .toString());
                    } else {
                      ethNewPrice.setText("0");
                    }

                    try {
                      if (arrayListData.getData().get(i).getNewPrice() != null
                          && arrayListData.getData().get(i).getLastPrice() != null) {
                        BigDecimal money = arrayListData.getData()
                            .get(i)
                            .getNewPrice()
                            .divide(arrayListData.getData().get(i).getLastPrice(), 10,
                                BigDecimal.ROUND_HALF_EVEN);
                        BigDecimal newData =
                            money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                        if (newData.compareTo(BigDecimal.ZERO) == 1) {

                          ethFooPrice.setText(
                              "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                          ethFooPrice.setTextColor(
                              getActivity().getResources().getColor(R.color.lime));
                        } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                          ethFooPrice.setTextColor(
                              getActivity().getResources().getColor(R.color.redred));
                          ethFooPrice.setText(newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                        } else {

                          ethFooPrice.setText("0.00%");
                        }
                      } else {
                        ethFooPrice.setText("0");
                      }
                    } catch (Exception e) {

                    }

                    if (arrayListData.getData().get(i).getUsdtCny() != null) {
                      ethLastPrice.setText("≈" + arrayListData.getData()
                          .get(i)
                          .getUsdtCny()
                          .setScale(4, BigDecimal.ROUND_DOWN) + "CNY");
                    } else {
                      ethLastPrice.setText("0");
                    }
                  }

                  if (arrayListData.getData().get(i).getDelKey().equals("EBX_USDT")) {

                    ebxName.setText(arrayListData.getData().get(i).getDelKey());

                    if (arrayListData.getData().get(i).getNewPrice() != null) {

                      ebxNewPrice.setText(arrayListData.getData()
                          .get(i)
                          .getNewPrice()
                          .setScale(4, BigDecimal.ROUND_DOWN)
                          .toString());
                    } else {
                      ebxNewPrice.setText("0");
                    }

                    try {
                      if (arrayListData.getData().get(i).getNewPrice() != null
                          && arrayListData.getData().get(i).getLastPrice() != null) {
                        BigDecimal money = arrayListData.getData()
                            .get(i)
                            .getNewPrice()
                            .divide(arrayListData.getData().get(i).getLastPrice(), 10,
                                BigDecimal.ROUND_HALF_EVEN);
                        BigDecimal newData =
                            money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                        if (newData.compareTo(BigDecimal.ZERO) == 1) {

                          ebxFooPrice.setText(
                              "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                          ebxFooPrice.setTextColor(
                              getActivity().getResources().getColor(R.color.lime));
                        } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                          ebxFooPrice.setText(newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                          ebxFooPrice.setTextColor(
                              getActivity().getResources().getColor(R.color.redred));
                        } else {
                          ebxFooPrice.setText("0.00%");
                        }
                      } else {
                        ebxFooPrice.setText("0");
                      }
                    } catch (Exception e) {

                    }

                    if (arrayListData.getData().get(i).getUsdtCny() != null) {
                      ebxLastPrice.setText("≈" + arrayListData.getData()
                          .get(i)
                          .getUsdtCny()
                          .setScale(4, BigDecimal.ROUND_DOWN) + "CNY");
                    } else {
                      ebxLastPrice.setText("0");
                    }
                  }
                }
              }
            });
          } else {
            t(arrayListData.getMsg());
          }
        }, throwable -> {

        });
  }

  /**
   * 获取实时价格
   */
  private void webSoket() {

    RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/ALL_ALL")
        .compose(RxSchedulers.io_main())
        .subscribe(new WebSocketSubscriber2<MainData>() {
          @Override
          protected void onMessage(MainData pageData) {

            if (pageData != null) {

              //BTC_USDT
              try {
                if (pageData.getDelKey().equals("BTC_USDT")) {
                  btcName.setText(pageData.getDelKey() + "");
                  if (pageData.getNewPrice() != null) {

                    btcNewPrice.setText(
                        pageData.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
                  } else {
                    btcNewPrice.setText("0");
                  }
                  try {
                    if (pageData.getNewPrice() != null && pageData.getLastPrice() != null) {
                      BigDecimal money = pageData.getNewPrice()
                          .divide(pageData.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                      BigDecimal newData =
                          money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                      if (newData.compareTo(BigDecimal.ZERO) == 1) {
                        btcFooPrice.setTextColor(
                            getActivity().getResources().getColor(R.color.lime));
                        btcFooPrice.setText("+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                      } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                        btcFooPrice.setTextColor(
                            getActivity().getResources().getColor(R.color.redred));
                        btcFooPrice.setText(newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                      } else {

                        btcFooPrice.setText("0.00%");
                      }
                    } else {
                      btcFooPrice.setText("0");
                    }
                  } catch (Exception e) {

                  }

                  if (pageData.getUsdtCny() != null) {
                    btcLastPrice.setText(
                        "≈" + pageData.getUsdtCny().setScale(4, BigDecimal.ROUND_DOWN) + "CNY");
                  } else {
                    btcLastPrice.setText("0");
                  }
                }
              } catch (Exception e) {

              }

              //ETH_USDT
              try {
                if (pageData.getDelKey().equals("ETH_USDT")) {

                  ethName.setText(pageData.getDelKey() + "");
                  if (pageData.getNewPrice() != null) {

                    ethNewPrice.setText(
                        pageData.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
                  } else {
                    ethNewPrice.setText("0");
                  }

                  try {
                    if (pageData.getNewPrice() != null && pageData.getLastPrice() != null) {
                      BigDecimal money = pageData.getNewPrice()
                          .divide(pageData.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                      BigDecimal newData =
                          money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                      if (newData.compareTo(BigDecimal.ZERO) == 1) {
                        ethFooPrice.setTextColor(
                            getActivity().getResources().getColor(R.color.lime));
                        ethFooPrice.setText("+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                      } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                        ethFooPrice.setTextColor(
                            getActivity().getResources().getColor(R.color.redred));
                        ethFooPrice.setText(newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                      } else {

                        ethFooPrice.setText("0.00%");
                      }
                    } else {
                      ethFooPrice.setText("0");
                    }
                  } catch (Exception e) {

                  }

                  if (pageData.getUsdtCny() != null) {
                    ethLastPrice.setText(
                        "≈" + pageData.getUsdtCny().setScale(4, BigDecimal.ROUND_DOWN) + "CNY");
                  } else {
                    ethLastPrice.setText("0");
                  }
                }
              } catch (Exception e) {

              }

              //EBX_USDT
              try {

                if (pageData.getDelKey().equals("EBX_USDT")) {

                  ebxName.setText(pageData.getDelKey() + "");

                  if (pageData.getNewPrice() != null) {

                    ebxNewPrice.setText(
                        pageData.getNewPrice().setScale(4, BigDecimal.ROUND_DOWN).toString());
                  } else {
                    ebxNewPrice.setText("0");
                  }

                  try {
                    if (pageData.getNewPrice() != null && pageData.getLastPrice() != null) {
                      BigDecimal money = pageData.getNewPrice()
                          .divide(pageData.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                      BigDecimal newData =
                          money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                      if (newData.compareTo(BigDecimal.ZERO) == 1) {
                        ebxFooPrice.setTextColor(
                            getActivity().getResources().getColor(R.color.lime));
                        ebxFooPrice.setText("+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                      } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                        ebxFooPrice.setTextColor(
                            getActivity().getResources().getColor(R.color.redred));
                        ebxFooPrice.setText(newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                      } else {

                        ebxFooPrice.setText("0.00%");
                      }
                    } else {
                      ebxFooPrice.setText("0");
                    }
                  } catch (Exception e) {

                  }

                  if (pageData.getUsdtCny() != null) {
                    ebxLastPrice.setText(
                        "≈" + pageData.getUsdtCny().setScale(4, BigDecimal.ROUND_DOWN) + "CNY");
                  } else {
                    ebxLastPrice.setText("0");
                  }
                }
              } catch (Exception e) {

              }
            }
          }

          @Override
          protected void onReconnect() {
            Log.d("MainActivity", "重连1");
          }
        });
  }

  /**
   * 获取banner图
   */
  @SuppressLint("CheckResult")
  private void net() {
    HashMap<String, String> hashMap = new HashMap<>();

    String useragent = new WebView(getContext()).getSettings().getUserAgentString();
    ApiFactory.getInstance()
        .banner(useragent, hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(pageData -> {
          if (pageData != null) {
            List<Subordinate> beans = pageData.getData().getResultList();
            ArrayList<String> list = new ArrayList<>();

            for (int i = 0; i < beans.size(); i++) {
              list.add(beans.get(i).getPath());

              Subordinate subordinate = beans.get(i);
              SubordinateUtils.saveSubordinate(subordinate);

              try {
                homeBanner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                homeBanner.setImages(list);
                //banner设置方法全部调用完毕时最后调用
                homeBanner.start();
              } catch (Exception e) {

              }
            }
          } else {
            t(pageData.getMsg());
          }
        }, throwable -> {

        });
  }

  /**
   * 网络加载图片
   * 使用了Glide图片加载框架
   */
  private class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

      Glide.with(context).load((String) WPConfig.PicBaseUrl + path).into(imageView);
    }
  }

  /**
   * 获取公告
   */
  @SuppressLint("CheckResult")
  private void init() {
    HashMap<String, String> hashMap = new HashMap<>();
    //正式环境ID
    hashMap.put("categoryId", "bb3a00b13a64463eafcc35e4bba73bca");
    hashMap.put("pageSize", "20");
    hashMap.put("pageNo", "1");
    hashMap.put("website", "cn");

    ApiFactory.getInstance()
        .listpage(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null) {
            ArrayList<IndustryBean> information = data.getData().getListData();
            ArrayList<String> list = new ArrayList<>();

            for (int i = 0; i < information.size(); i++) {
              list.add(information.get(i).getTitle());
              IndustryBean industry = information.get(i);
              IndustryBeanUtils.saveIndustryBean(industry);
            }

            homeLampView.setAdapter(new SimpleBulletinAdapter(getContext(), list));
            homeLampView.setOnBulletinItemClickListener(
                new BulletinView.OnBulletinItemClickListener() {
                  @Override
                  public void onBulletinItemClick(int position) {

                    intent = new Intent(getActivity(), NewsDetailsActivity.class);
                    intent.putExtra("title", information.get(position).getTitle() + "");
                    intent.putExtra("content", information.get(position).getContent() + "");
                    startActivity(intent);
                  }
                });
          }
        }, throwable -> {

        });
  }

  @Override
  protected void initUiAndListener() {

    //24h交易排行榜
    fragments.add(new MainNuberFrament());
    //24h涨幅榜
    fragments.add(new MainUPFrament());

    viewPager.setAdapter(
        new ViewPagerFragmentAdapter(getChildFragmentManager(), fragments, titles));
    xTablayout.setupWithViewPager(viewPager);
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_home;
  }

  /**
   * Banner 点击事件
   */
  @Override
  public void OnBannerClick(int position) {

  }

  @OnClick({
      R.id.btc_linear, R.id.eth_linear, R.id.ebx_linear, R.id.home_Mining, R.id.home_C2C,
      R.id.home_Secondkill, R.id.home_Money_int
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {

      //BTC
      case R.id.btc_linear:
        for (int i = 0; i < maindata.size(); i++) {

          if (maindata.get(i).getDelKey().equals("BTC_USDT")) {
            //BTC
            intent.putExtra("JYBi",
                maindata.get(i).getDelKey().substring(0, maindata.get(i).getDelKey().indexOf("_")));
            //USDT
            intent.putExtra("DJBi", maindata.get(i).getDelKey().substring(maindata.get(i).getDelKey().indexOf("_") + 1,
                    maindata.get(i).getDelKey().length()));
            // BTC/USDT
            intent.putExtra("Biname", maindata.get(i).getDelKey().replace("_", "/"));
            //BTC_USDT
            intent.putExtra("KlinBiname", maindata.get(i).getDelKey());
            //
            intent.setClass(getContext(), DonationActivity.class);
            startActivity(intent);
          }
        }

        break;
      //ETH
      case R.id.eth_linear:
        for (int i = 0; i < maindata.size(); i++) {
          if (maindata.get(i).getDelKey().equals("ETH_USDT")) {
            //BTC
            intent.putExtra("JYBi",
                maindata.get(i).getDelKey().substring(0, maindata.get(i).getDelKey().indexOf("_")));
            //USDT
            intent.putExtra("DJBi", maindata.get(i)
                .getDelKey()
                .substring(maindata.get(i).getDelKey().indexOf("_") + 1,
                    maindata.get(i).getDelKey().length()));
            // BTC/USDT
            intent.putExtra("Biname", maindata.get(i).getDelKey().replace("_", "/"));
            //BTC_USDT
            intent.putExtra("KlinBiname", maindata.get(i).getDelKey());
            //
            intent.setClass(getContext(), DonationActivity.class);
            startActivity(intent);
          }
        }

        break;
      //EBX
      case R.id.ebx_linear:

        for (int i = 0; i < maindata.size(); i++) {
          if (maindata.get(i).getDelKey().equals("EBX_USDT")) {
            //BTC
            intent.putExtra("JYBi",
                maindata.get(i).getDelKey().substring(0, maindata.get(i).getDelKey().indexOf("_")));
            //USDT
            intent.putExtra("DJBi", maindata.get(i)
                .getDelKey()
                .substring(maindata.get(i).getDelKey().indexOf("_") + 1,
                    maindata.get(i).getDelKey().length()));
            // BTC/USDT
            intent.putExtra("Biname", maindata.get(i).getDelKey().replace("_", "/"));
            //BTC_USDT
            intent.putExtra("KlinBiname", maindata.get(i).getDelKey());
            //
            intent.setClass(getContext(), DonationActivity.class);
            startActivity(intent);
          }
        }
        break;

      //分红挖矿详情，原来是累计分红
      case R.id.home_Mining:
        intent.setClass(getActivity(), MiningDetailsActivity.class);
        startActivity(intent);
        break;
      // C2C
      case R.id.home_C2C:
        if (kv.decodeString("username") != null) {
          //是否答题通过
          //isAnswer();
          //test
          intent.setClass(getContext(), BuySellActivity.class);
          startActivity(intent);
        } else {
          t(getActivity().getResources().getString(R.string.Please_log));
        }

        break;
      //秒杀专区
      case R.id.home_Secondkill:
        intent.setClass(getActivity(), SecondkillActivity.class);
        startActivity(intent);
        break;
      case R.id.home_Money_int:

        if (kv.decodeString("username") == null) {
          t(getActivity().getResources().getString(R.string.Please_log));
        } else {
          intent.setClass(getActivity(), ConducMoneyActivity.class);
          startActivity(intent);
        }
        break;
      default:
        break;
    }
  }

  /**
   * 是否答题通过
   * c2c是否答题通过
   */
  @SuppressLint("CheckResult")
  private void isAnswer() {

    HashMap<String, String> hashMap = new HashMap();
    ApiFactory.getInstance()
        .ctcaccount(kv.decodeString("tokenId"), hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe(data -> {

          if (data.getData() != null) {
            if (data.getData().getExamState() == 1) {
              intent.setClass(getContext(), BuySellActivity.class);
              startActivity(intent);
            } else {
              intent.setClass(getContext(), CTwoCMainActivity.class);
              startActivity(intent);
            }
          } else {
            t(data.getMsg());
          }
        }, throwable -> {

        });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // TODO: inflate a fragment view
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initUiAndListener();
    refreshData(savedInstanceState);
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
        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "miningsocket").subscribe();
    if (disposable1 != null && !disposable1.isDisposed()) {
      disposable1.dispose();
    }

    Disposable disposable2 =
        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinwelfaresocket").subscribe();
    if (disposable2 != null && !disposable2.isDisposed()) {
      disposable2.dispose();
    }

    Disposable disposable3 =
        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/ALL_ALL").subscribe();
    if (disposable3 != null && !disposable3.isDisposed()) {
      disposable3.dispose();
    }
  }
}
