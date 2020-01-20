package com.eex.market.frgament.donation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.eex.R
import com.eex.WPConfig
import com.eex.common.api.ApiFactory
import com.eex.common.api.RxSchedulers
import com.eex.common.base.BaseFragment
import com.eex.common.base.Data
import com.eex.common.util.CommonUtil
import com.eex.common.websocket.RxWebSocket
import com.eex.common.websocket.WebSocketInfo
import com.eex.common.websocket.WebSocketSubscriber2
import com.eex.home.activity.login.LoginActivity
import com.eex.home.bean.MainData
import com.eex.home.bean.MainList
import com.eex.home.weight.MyDialog
import com.eex.home.weight.Utils
import com.eex.market.adpater.JustOneAdapter
import com.eex.market.adpater.NewDealAdapter
import com.eex.market.adpater.PurchaseFramentBuyAdapter
import com.eex.market.adpater.purchaseFramentAdapter
import com.eex.market.bean.Buy
import com.eex.market.bean.CnySize
import com.eex.market.bean.Money
import com.eex.market.bean.PurchaseDatalIst
import com.eex.market.bean.PurchaseDta
import com.eex.market.bean.Sell
import com.eex.market.bean.bIDataVO
import com.eex.market.frgament.text.DepthDataBean
import com.eex.market.weight.BuyMoney
import com.eex.market.weight.DecimalDigitsInputFilter
import com.eex.market.weight.Root
import kotlinx.android.synthetic.main.fragment_market_sale.listview
import kotlinx.android.synthetic.main.fragment_market_sale.listview2
import kotlinx.android.synthetic.main.header_market_sale.EDT_ZYZSCFPrice
import kotlinx.android.synthetic.main.header_market_sale.LLMoney
import kotlinx.android.synthetic.main.header_market_sale.LLSJ
import kotlinx.android.synthetic.main.header_market_sale.LLSJD
import kotlinx.android.synthetic.main.header_market_sale.LLXJD
import kotlinx.android.synthetic.main.header_market_sale.LLYJD_premium
import kotlinx.android.synthetic.main.header_market_sale.LLYJpremium
import kotlinx.android.synthetic.main.header_market_sale.LLZYZSD
import kotlinx.android.synthetic.main.header_market_sale.LL_Fixed_price
import kotlinx.android.synthetic.main.header_market_sale.LLbuysell
import kotlinx.android.synthetic.main.header_market_sale.MoneyWater
import kotlinx.android.synthetic.main.header_market_sale.SJBtn1
import kotlinx.android.synthetic.main.header_market_sale.SJBtn2
import kotlinx.android.synthetic.main.header_market_sale.SJBtn3
import kotlinx.android.synthetic.main.header_market_sale.SJBtn4
import kotlinx.android.synthetic.main.header_market_sale.SJNuberBtnAdd
import kotlinx.android.synthetic.main.header_market_sale.SJNuberBtnSub
import kotlinx.android.synthetic.main.header_market_sale.TXRMBPrice
import kotlinx.android.synthetic.main.header_market_sale.TXZSZYMoneyPrice
import kotlinx.android.synthetic.main.header_market_sale.TypeText
import kotlinx.android.synthetic.main.header_market_sale.XJNuberBtnAdd
import kotlinx.android.synthetic.main.header_market_sale.XJNuberBtnSub
import kotlinx.android.synthetic.main.header_market_sale.XJPriceBtnAdd
import kotlinx.android.synthetic.main.header_market_sale.XJPriceBtnSub
import kotlinx.android.synthetic.main.header_market_sale.YJNBtn1
import kotlinx.android.synthetic.main.header_market_sale.YJNBtn2
import kotlinx.android.synthetic.main.header_market_sale.YJNBtn3
import kotlinx.android.synthetic.main.header_market_sale.YJNBtn4
import kotlinx.android.synthetic.main.header_market_sale.ZSZYMoneyWTPrice
import kotlinx.android.synthetic.main.header_market_sale.ZYZSBtn1
import kotlinx.android.synthetic.main.header_market_sale.ZYZSBtn2
import kotlinx.android.synthetic.main.header_market_sale.ZYZSBtn3
import kotlinx.android.synthetic.main.header_market_sale.ZYZSBtn4
import kotlinx.android.synthetic.main.header_market_sale.ZYZSCfBtnPriceAdd
import kotlinx.android.synthetic.main.header_market_sale.ZYZSCfBtnPriceSub
import kotlinx.android.synthetic.main.header_market_sale.ZYZSWtBtnNuberAdd
import kotlinx.android.synthetic.main.header_market_sale.ZYZSWtBtnNuberSub
import kotlinx.android.synthetic.main.header_market_sale.ZYZSWtBtnPriceAdd
import kotlinx.android.synthetic.main.header_market_sale.ZYZSWtBtnPriceSub
import kotlinx.android.synthetic.main.header_market_sale.btnXD1
import kotlinx.android.synthetic.main.header_market_sale.btnXD2
import kotlinx.android.synthetic.main.header_market_sale.btnXD3
import kotlinx.android.synthetic.main.header_market_sale.btnXD4
import kotlinx.android.synthetic.main.header_market_sale.btn_sell
import kotlinx.android.synthetic.main.header_market_sale.depth_view
import kotlinx.android.synthetic.main.header_market_sale.edt_SJnuber
import kotlinx.android.synthetic.main.header_market_sale.edt_XDPrice
import kotlinx.android.synthetic.main.header_market_sale.edt_XDnuber
import kotlinx.android.synthetic.main.header_market_sale.edt_XJMoney
import kotlinx.android.synthetic.main.header_market_sale.edt_YJNnuber
import kotlinx.android.synthetic.main.header_market_sale.edt_ZYZSPrice
import kotlinx.android.synthetic.main.header_market_sale.edt_ZYZSnuber
import kotlinx.android.synthetic.main.header_market_sale.img_type
import kotlinx.android.synthetic.main.header_market_sale.listview_buy1
import kotlinx.android.synthetic.main.header_market_sale.listview_sell1
import kotlinx.android.synthetic.main.header_market_sale.ll_title
import kotlinx.android.synthetic.main.header_market_sale.rlgone
import kotlinx.android.synthetic.main.header_market_sale.textviewPurMoney1
import kotlinx.android.synthetic.main.header_market_sale.textviewPurMoney2
import kotlinx.android.synthetic.main.header_market_sale.tx_NameZhiyin
import kotlinx.android.synthetic.main.header_market_sale.tx_Nameshijia
import kotlinx.android.synthetic.main.header_market_sale.tx_Namexianjia
import kotlinx.android.synthetic.main.header_market_sale.tx_cny
import kotlinx.android.synthetic.main.header_market_sale.tx_premium
import kotlinx.android.synthetic.main.header_market_sale.tx_type
import kotlinx.android.synthetic.main.header_market_sale.tx_usdt
import java.math.BigDecimal
import java.util.Collections
import java.util.HashMap

/**
 * @Description: 市场卖出fragment
 * @Author: xq
 * @CreateDate: 2019/11/11 18:03
 */

class MarketSaleFragment : BaseFragment() {

  companion object {
    var name1 = "BTC_USDT"
    var BINAME = "BTC"
    var name: String? = null
    var BINAMEDATA = "USDT"
    var Type = "YES"
//        var OTHER = 0
  }

  private val list = ArrayList<CnySize>()

  private val datalIsts = ArrayList<PurchaseDatalIst>()
  private var mainData = ArrayList<MainData>()
  private var LLtype = "1"
  private var MoneyType = "YES"
  private var ktYong = ""

  private var type = 1

  private val sellList = ArrayList<Sell>()
  private val buyList = ArrayList<Buy>()

  private val sells = ArrayList<Sell>()
  private val buys = ArrayList<Buy>()
  private val mainLists = ArrayList<MainList>()

  private var pricNuber: BigDecimal? = null
  private var quantityNuber: BigDecimal? = null
  private var MIN = BigDecimal.ZERO
  private var MAX = BigDecimal.ZERO

  private var newDealAdapter: NewDealAdapter? = null
  private var adapter: purchaseFramentAdapter? = null
  private var buyAdapter: PurchaseFramentBuyAdapter? = null

  private var PurName = ""
  private var root: PurchaseDta? = null
  private var newprice: BigDecimal? = BigDecimal.ZERO
  /**
   * 用户可用币种数量
   */
  private var Nube: String = ""
  /**
   * 饼状图 穿的参数
   */
  private var CashFlow: String? = null

  /**
   * dialog
   */
  private var view1: View? = null
  private var Tltle: TextView? = null
  private var btnDimss: Button? = null
  private var btnSell: Button? = null
  /**
   * mMyDialog
   */
  private var mMyDialog: MyDialog? = null

  override fun lazyLoad() {
  }

  @SuppressLint("CheckResult")
  private fun staticdata() {
    ApiFactory.getInstance()
        .staticdata()
        .compose(RxSchedulers.io_main())
        .subscribe({ coindata ->
          if (coindata != null) {

            for (i in 0 until coindata.list.size) {
              //取得最后一个/的下标
              val index = coindata.list.get(i)
                  .toString()
                  .lastIndexOf("=")
              //将字符串转为字符数组
              val ch = coindata.list.get(i)
                  .toString()
                  .toCharArray()
              //根据 copyValueOf(char[] data, int offset, int count) 取得最后一个字符串
              val informationId = String(ch, index + 1, ch.size - index - 2)

              val informationId1 = coindata.list.get(i)
                  .toString()
                  .substring(1, coindata.list.get(i).toString().indexOf("="))

              if (BINAME == informationId1) {
                CashFlow = informationId
              }
            }
            //                        Log.e(TAG, "卖: " + CashFlow );
          }
        }, { throwable ->

        })
  }

  /**
   * sellState 为1 是限制卖出
   */
  @SuppressLint("CheckResult")
  private fun sellState1() {
    val hashMap = HashMap<String, String>()
    hashMap["pricingCoin"] = "PAX"
    ApiFactory.getInstance()
        .getDealPairList(hashMap)
        .compose<Data<ArrayList<MainList>>>(RxSchedulers.io_main<Data<ArrayList<MainList>>>())
        .subscribe({ data ->
          try {
            if (data.isStauts) {

              for (i in 0 until data.data.size) {
                if (data.data.get(i).dealPair == name1) {
                  if (data.data.get(i).sellState == 1) {
                    btn_sell.setBackgroundColor(CommonUtil.getColor(R.color.grey))
                    btn_sell.isEnabled = false
                  }

                }
              }

            } else {
              t(data.msg)
            }
          } catch (e: Exception) {

          }

        }, { throwable ->

        })
  }

  /**
   * USDT  sellState 为1 是限制卖出
   */
  @SuppressLint("CheckResult")
  private fun sellState2() {
    val hashMap = HashMap<String, String>()
    hashMap["pricingCoin"] = "USDT"
    ApiFactory.getInstance()
        .getDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe({ data ->
          try {
            if (data.isStauts) {

              for (i in 0 until data.data.size) {

                if (data.data[i].dealPair == name1) {
                  if (data.data[i].sellState == 1) {
                    btn_sell.setBackgroundColor(CommonUtil.getColor(R.color.grey))
                    btn_sell.isEnabled = false
                  }

                }
              }

            } else {
              t(data.msg)
            }
          } catch (e: Exception) {

          }

        }, { throwable ->

        })
  }

  /**
   * CNYE 为1 是限制卖出
   */
  @SuppressLint("CheckResult")
  private fun sellState3() {
    val hashMap = HashMap<String, String>()
    hashMap["pricingCoin"] = "CNYE"
    ApiFactory.getInstance()
        .getDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe({ data ->
          try {
            if (data.isStauts) {

              for (i in 0 until data.data.size) {
                if (data.data[i].dealPair == name1) {
                  if (data.data[i].sellState == 1) {
                    btn_sell.setBackgroundColor(CommonUtil.getColor(R.color.grey))
                    btn_sell.isEnabled = false
                  }

                }
              }

            } else {
              t(data.msg)
            }
          } catch (e: Exception) {

          }

        }, { throwable ->

        })
  }

  private fun getID() {

    tx_type.setTextColor(CommonUtil.getColor(R.color.appbar_background3))

    listview_buy1.onItemClickListener =
      AdapterView.OnItemClickListener { parent, view, position, id ->
        if (LLtype == "1") {
          edt_XDPrice.setText(
              buyList.get(position).delAmount.setScale(4, BigDecimal.ROUND_HALF_DOWN).toString()
          )
        } else if (LLtype == "3") {
          edt_ZYZSPrice.setText(
              buyList.get(position).delAmount.setScale(4, BigDecimal.ROUND_HALF_DOWN).toString()
          )
        }
      }


    listview_sell1.onItemClickListener =
      AdapterView.OnItemClickListener { parent, view, position, id ->
        if (LLtype == "1") {
          edt_XDPrice.setText(
              sellList.get(position).delAmount.setScale(4, BigDecimal.ROUND_HALF_DOWN).toString()
          )
        } else if (LLtype == "3") {
          edt_ZYZSPrice.setText(
              sellList.get(position).delAmount.setScale(4, BigDecimal.ROUND_HALF_DOWN).toString()
          )
        }
      }

    edt_XDPrice.addTextChangedListener(object : TextWatcher {

      @SuppressLint("SetTextI18n")
      override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {
        // 输入的内容变化的监听
        //输入过程中执行该方法", "文字变化
        try {
          if (edt_XDPrice.text.toString().trim({ it <= ' ' }) == "") {
            TXRMBPrice.text = "估值 ￥0"
            edt_XJMoney.setText("")
          } else {
            val b1 = BigDecimal(edt_XDPrice.text.toString().trim({ it <= ' ' }))
            val RMB = mainData.get(0)
                .usdtCny
            val USDT = datalIsts.get(0)
                .dealPrice
            val NewGuzi = RMB.divide(USDT, 10, BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_DOWN)
            TXRMBPrice.text = "估值 ￥" + NewGuzi.multiply(b1).setScale(
                2, BigDecimal.ROUND_DOWN
            ).stripTrailingZeros().toPlainString()
            val b2 = BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' }))
            val b3 = b1.multiply(b2)
            //成交价
            edt_XJMoney.setText(b3.toString())
          }

        } catch (e: Exception) {

        }

      }

      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {
        // 输入前的监听
        //输入前确认执行该方法", "开始输入

      }

      override fun afterTextChanged(s: Editable) {
        // 输入后的监听
        //输入结束执行该方法", "输入结束"
      }
    })


    edt_XDnuber.addTextChangedListener(object : TextWatcher {

      override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {
        // 输入的内容变化的监听
        //输入过程中执行该方法", "文字变化

        //                if (edtXDnuber.getText().toString().trim() >ktYong)
        try {
          val b1 = BigDecimal(edt_XDPrice.text.toString().trim({ it <= ' ' }))
          val b2 = BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' }))
          val b3 = b1.multiply(b2)
          edt_XJMoney.setText(b3.toString())
        } catch (e: Exception) {

        }

      }

      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {
        // 输入前的监听
        //输入前确认执行该方法", "开始输入

      }

      override fun afterTextChanged(s: Editable) {
        // 输入后的监听
        //输入结束执行该方法", "输入结束"

      }
    })


    EDT_ZYZSCFPrice.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {

      }

      @SuppressLint("SetTextI18n")
      override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {
        try {
          if (EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }) == "") {
            TXZSZYMoneyPrice.text = "估值 ￥0"
          } else {
            val b1 = BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
            val RMB = mainData.get(0)
                .usdtCny
            val USDT = datalIsts.get(0)
                .dealPrice
            val NewGuzi = RMB.divide(USDT, 10, BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_DOWN)
            TXZSZYMoneyPrice.text = "估值 ￥" + NewGuzi.multiply(b1).setScale(
                2, BigDecimal.ROUND_DOWN
            ).stripTrailingZeros().toPlainString()
          }

        } catch (e: Exception) {

        }

      }

      override fun afterTextChanged(s: Editable) {

      }
    })


    edt_ZYZSPrice.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {

      }

      @SuppressLint("SetTextI18n")
      override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {
        try {
          if (edt_ZYZSPrice.text.toString().trim({ it <= ' ' }) == "") {
            ZSZYMoneyWTPrice.text = "估值 ￥0"
          } else {
            val b1 = BigDecimal(edt_ZYZSPrice.text.toString().trim({ it <= ' ' }))
            val RMB = mainData.get(0)
                .usdtCny
            val USDT = datalIsts.get(0)
                .dealPrice
            val NewGuzi = RMB.divide(USDT, 10, BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_DOWN)
            ZSZYMoneyWTPrice.text = "估值 ￥" + NewGuzi.multiply(b1).setScale(
                2, BigDecimal.ROUND_DOWN
            ).stripTrailingZeros().toPlainString()
          }

        } catch (e: Exception) {

        }

      }

      override fun afterTextChanged(s: Editable) {

      }
    })

  }

  /**
   * 获取最新成交价
   */
  @SuppressLint("CheckResult", "SetTextI18n")
  private fun net() {
    val hashMap = HashMap<String, String>()
    hashMap["dealPair"] = name1
    hashMap["size"] = "10"

    ApiFactory.getInstance()
        .TradingHall(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe({ data ->

          if (data.isStauts) {
            datalIsts.clear()
            datalIsts.addAll(data.data)
            setView(datalIsts)
            newDealAdapter?.notifyDataSetChanged()
          } else {
            t(data?.msg)
          }

        }, { throwable ->

        })

  }

  private fun setView(datalIsts: ArrayList<PurchaseDatalIst>?) {

    if (activity != null) {
      if (datalIsts != null && datalIsts.size != 0) {
        newprice = datalIsts[0].dealPrice.setScale(8, BigDecimal.ROUND_DOWN)
        textviewPurMoney1.text = newprice!!.stripTrailingZeros()
            .toPlainString()
      } else {
        textviewPurMoney1.text = "0"
      }

      newDealAdapter = NewDealAdapter(datalIsts, activity)
      newDealAdapter?.setDeviceList(datalIsts)
      listview.adapter = newDealAdapter
      newDealAdapter?.notifyDataSetChanged()
//            CommonUtil.setListViewHeightBasedOnChildren(listview)
    }
  }

  private fun WebSocket() {

    try {
      RxWebSocket.get(WPConfig.WSUrl + "coinsocket/" + kv.decodeString("id"))
          .compose<WebSocketInfo>(RxSchedulers.io_main<WebSocketInfo>())
          .subscribe(object : WebSocketSubscriber2<bIDataVO>() {
            @SuppressLint("SetTextI18n")
            override fun onMessage(bIDataVO: bIDataVO?) {

              Log.e("Sell", "onMessage: " + bIDataVO!!.toString())
              MoneyType = "NO"
              if (bIDataVO != null) {
                try {
                  if (bIDataVO.coinCode == BINAME) {
                    ktYong = bIDataVO.coinMoney.setScale(
                        8, BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString().toString() + ""
                    tx_usdt.text = bIDataVO.coinMoney.setScale(
                        8, BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString().toString() + bIDataVO.coinCode + ""
                    //                                        txUsdt.setText(bIDataVO.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + bIDataVO.getCoinCode() + "");
                  }
                  if (bIDataVO.coinCode == BINAMEDATA) {
                    Nube = bIDataVO.coinMoney.setScale(8, BigDecimal.ROUND_DOWN)
                        .stripTrailingZeros()
                        .toPlainString()

                    tx_cny.text = bIDataVO.coinMoney.setScale(
                        8, BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString() + bIDataVO.coinCode + ""
                  }
                } catch (e: Exception) {

                }

              } else {

                //                                Toast.makeText(getActivity(), "用户数据获取失败!", Toast.LENGTH_SHORT).show();
              }
            }

            override fun onReconnect() {

              MoneyType = "YES"
            }

          })

    } catch (e: Exception) {

    }

  }

  /**
   * 长连接
   *
   *
   * 更新买卖
   */
  private fun SocketData() {

    if (activity != null) {
      activity!!.runOnUiThread {
        try {
          RxWebSocket.get(WPConfig.WSUrl + "websocket/" + name1)
              .compose(RxSchedulers.io_main())
              .subscribe(object : WebSocketSubscriber2<Root<PurchaseDta>>() {
                @SuppressLint("SetTextI18n")
                override fun onMessage(root: Root<PurchaseDta>) {

                  Type = "YES"
                  if (root.data.buy != null || root.data.sell != null) {

                    try {

                      if (root.data.listOrder.isEmpty()) {
                      } else {
                        if (root.data.listOrder[root.data.listOrder.size - 1].dealAmount != null) {
                          newprice = root.data.listOrder[root.data.listOrder.size - 1].dealAmount
                          textviewPurMoney1.text = newprice!!.setScale(8, BigDecimal.ROUND_DOWN)
                              .stripTrailingZeros()
                              .toPlainString()

                        }
                      }
                    } catch (e: Exception) {
                    }

                    //买入
                    buyList.clear()
                    buys.clear()

                    if (root.data.buy != null && root.data.buy.size != 0) {
                      if (root.data.buy.size > 10) {
                        buyList.addAll(root.data.buy.subList(0, 10))
                      } else {
                        buyList.addAll(root.data.buy)
                      }
                      buys.addAll(root.data.buy)
                    }


                    sellList.clear()
                    sells.clear()

                    if (root.data.sell != null && root.data.sell.size != 0) {
                      if (root.data.sell.size >= 10) {
                        sells.addAll(root.data.sell.subList(0, 10))
                      } else {
                        sells.addAll(root.data.sell)
                      }

                      sellList.addAll(sells)
                      Collections.reverse(sellList)
                    }
                    if (activity != null) {
                      activity!!.runOnUiThread {
                        //买卖
                        updataView()

                        //深度图
                        drowHightView()
                      }

                    }
                    try {
                      if (root.data.listOrder != null && root.data.listOrder.size != 0) {

                        list.clear()
                        list.addAll(root.data.listOrder)
                        //更新最新成交记录
                        setNewMoney(list)
                      }
                    } catch (e: Exception) {
                      //try
                    }

                  } else {

                    //                                            Toast.makeText(getActivity(), "用户数据获取失败!", Toast.LENGTH_SHORT).show();
                  }
                }

                override fun onReconnect() {
                  MoneyType = "YES"
                }

              })

        } catch (e: Exception) {

        }
      }

    }
  }

  /**
   * 卖入adapter
   * purchaseFramentAdapter
   *
   *
   * 卖入和买入adapter
   *
   *
   * 买入adapter   PurchaseFramentBuyAdapter
   */
  private fun updataView() {
    //卖入

    //Collections.reverse(sellmList);

    if (activity != null) {
      adapter = purchaseFramentAdapter(activity, sellList)
      listview_sell1.adapter = adapter
      adapter?.notifyDataSetChanged()
      listview_sell1.requestLayout()
      try {
        val listItem = adapter!!.getView(0, null, listview_sell1)
        listItem.measure(0, 0)
        val totalHei = (listItem.measuredHeight + listview_sell1.dividerHeight) * 10
        listview_sell1.layoutParams.height = totalHei
      } catch (e: Exception) {

      }

    }

    //        买入

    if (activity != null) {
      buyAdapter = PurchaseFramentBuyAdapter(activity, buys)
      listview_buy1.adapter = buyAdapter
      buyAdapter?.notifyDataSetChanged()
      listview_buy1.requestLayout()
      try {
        val listItem1 = adapter!!.getView(0, null, listview_buy1)
        listItem1.measure(0, 0)
        val totalHei1 = (listItem1.measuredHeight + listview_buy1.dividerHeight) * 10
        listview_buy1.layoutParams.height = totalHei1
      } catch (e: Exception) {

      }

    }

  }

  /**
   * 画深度图
   */
  private fun drowHightView() {

    val listDepthBuy = ArrayList<DepthDataBean>()
    val listDepthSell = ArrayList<DepthDataBean>()
    var obj: DepthDataBean
    var obj1: DepthDataBean
    var price: String
    var volume: String
    try {
      if (buys != null && buys.size > 0) {
        for (i in buys.indices) {
          obj = DepthDataBean()
          obj1 = DepthDataBean()
          price = buys[i].delAmount.setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          volume = buys[i].residueNum.setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          obj.volume = java.lang.Float.valueOf(volume)
          obj.price = java.lang.Float.valueOf(price)
          obj1.volume = java.lang.Float.valueOf(volume)
          obj1.price = java.lang.Float.valueOf(price)
          listDepthBuy.add(obj)

        }
      }

      if (sellList != null && sellList.size > 0) {
        for (i in sellList.indices) {
          obj = DepthDataBean()
          obj1 = DepthDataBean()
          price = sellList[i].delAmount.setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          volume = sellList[i].residueNum.setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          obj.volume = java.lang.Float.valueOf(volume)
          obj.price = java.lang.Float.valueOf(price)
          obj1.volume = java.lang.Float.valueOf(volume)
          obj1.price = java.lang.Float.valueOf(price)
          listDepthSell.add(obj)

        }
      }

      depth_view.setData(listDepthBuy, listDepthSell)
    } catch (e: Exception) {

    }

  }

  /**
   * 更新最新成交记录
   *
   * @param list
   */
  private fun setNewMoney(list: ArrayList<CnySize>?) {

    if (list != null && !list.isEmpty()) {
      for (cnySize in list) {
        val purchaseDatalIst = PurchaseDatalIst()
        purchaseDatalIst.dealTime = cnySize.dealDate
        purchaseDatalIst.dealNum = cnySize.dealNum
        purchaseDatalIst.dealPrice = cnySize.dealAmount
        try {
          datalIsts.add(0, purchaseDatalIst)
        } catch (e: Exception) {

        }

      }
    }

    activity!!.runOnUiThread {
      if (datalIsts != null && datalIsts.size != 0) {
        newDealAdapter = NewDealAdapter(datalIsts, activity)
        newDealAdapter?.setDeviceList(datalIsts)
        listview.adapter = newDealAdapter
        newDealAdapter?.notifyDataSetChanged()
        Utils.setListViewHeightBasedOnChildren(listview)
      }
    }

  }

  /**
   * 交易大厅List
   */
  @SuppressLint("CheckResult")
  private fun getTickMaket() {

    if (activity != null) {
      activity!!.runOnUiThread {
        PurName = name1

        val hashMap = HashMap<String, String>()
        hashMap["delkey"] = name1

        ApiFactory.getInstance()
            .getTickMaket(hashMap)
            .compose(RxSchedulers.io_main())
            .subscribe({ data ->

              root = data.data
              if (data.isStauts) {

                try {

                  if (root!!.listOrder.isEmpty()) {
                  } else {
                    if (root!!.listOrder.get(root!!.listOrder.size - 1).dealAmount != null) {
                      newprice = root!!.listOrder.get(root!!.listOrder.size - 1)
                          .dealAmount
                      textviewPurMoney1.text = newprice!!.setScale(8, BigDecimal.ROUND_DOWN)
                          .stripTrailingZeros()
                          .toPlainString()

                    }
                  }
                } catch (e: Exception) {

                }


                if (data.data.sell != null) {
                  sellList.clear()
                  sellList.addAll(data.data.sell)
                  if (data.data.sell != null && data.data.sell.size != 0) {
                    sellList.addAll(data.data.sell)
                    if (data.data.sell.size > 10) {
                      sells.addAll(data.data.sell.subList(0, 10))
                    } else {
                      sells.addAll(data.data.sell)
                    }

                  }

                }

                if (data.data.buy != null) {
                  buyList.clear()
                  buyList.addAll(data.data.buy)

                  if (data.data.buy != null && data.data.buy.size != 0) {
                    if (data.data.buy.size > 10) {
                      buyList.addAll(data.data.buy.subList(0, 10))
                    } else {
                      buyList.addAll(data.data.buy)
                    }
                    buys.addAll(data.data.buy)

                  }

                }

                if (activity != null) {
                  activity!!.runOnUiThread {
                    Collections.reverse(buyList)
                    Collections.reverse(sellList)
                    updataView()
                    //画深度图
                    drowHightView()
                    adapter2?.notifyDataSetChanged()
//                                        webDialog.loadUrl("javascript:getdata('$BINAME')")
                  }
                }

              } else {
                t(data.msg)
              }

            }, { throwable ->

            })
      }
    }
  }

  /**
   * 获取系统小数位数
   */
  @SuppressLint("CheckResult")
  private fun getBiListData() {

    val hashMap = HashMap<String, String>()

    ApiFactory.getInstance()
        .getDealPairList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe({ data ->
          try {
            if (data.isStauts) {
              mainLists.clear()
              mainLists.addAll(data.data)

              for (i in mainLists.indices) {
                val name = data.data[i].dealPair
                if (name == BINAME + "_" + BINAMEDATA) {
                  pricNuber = data.data[i].priceReservation
                  quantityNuber = data.data[i].quantityRetention
                  MIN = data.data[i].minNum
                  MAX = data.data[i].maxNum
                }
              }

              //限制Edt输入位数
              edt_XDPrice.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(pricNuber!!.toInt()))
              edt_XDnuber.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(quantityNuber!!.toInt()))
              edt_XJMoney.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(pricNuber!!.toInt()))

              edt_SJnuber.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(quantityNuber!!.toInt()))

              edt_YJNnuber.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(quantityNuber!!.toInt()))

              EDT_ZYZSCFPrice.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(pricNuber!!.toInt()))
              edt_ZYZSPrice.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(pricNuber!!.toInt()))
              edt_ZYZSnuber.filters =
                arrayOf<InputFilter>(DecimalDigitsInputFilter(quantityNuber!!.toInt()))
            } else {
              t(data.msg)
            }
          } catch (e: Exception) {

          }

        }, { throwable ->

        })
  }

  /**
   * 获取用户可用币数量
   */
  @SuppressLint("CheckResult", "SetTextI18n")
  private fun getMoney() {

    val hashMap = HashMap<String, String>()

    hashMap["coinCodes"] = name1

    ApiFactory.getInstance()
        .account(kv.decodeString("tokenId"), hashMap)
        .compose<Data<ArrayList<BuyMoney<Money>>>>(
            RxSchedulers.io_main<Data<ArrayList<BuyMoney<Money>>>>()
        )
        .subscribe({ arrayListData ->

          if (arrayListData.data != null) {
            try {
              ktYong = arrayListData.data[0].data.coinMoney.setScale(8, BigDecimal.ROUND_DOWN)
                  .stripTrailingZeros()
                  .toPlainString()
                  .toString()
              tx_usdt.text = arrayListData.data[0].data.coinMoney.setScale(
                  8, BigDecimal.ROUND_DOWN
              ).stripTrailingZeros().toPlainString().toString() + BINAME
              if (BINAMEDATA == "CNYE") {
                Nube = arrayListData.data[1].data.usableCNY.setScale(8, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros()
                    .toPlainString()

                tx_cny.text = arrayListData.data[1].data.usableCNY.setScale(
                    8, BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString().toString() + "CNYE"
              } else {
                Nube = arrayListData.data[0].data.coinMoney.setScale(8, BigDecimal.ROUND_DOWN)
                    .stripTrailingZeros()
                    .toPlainString()

                tx_cny.text = arrayListData.data[1].data.coinMoney.setScale(
                    8, BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString().toString() + BINAMEDATA
              }
            } catch (e: Exception) {
              tx_usdt.text = "0$BINAME"
              tx_cny.text = "0$BINAMEDATA"
            }

          }

        }, { throwable ->

        })
  }

  override fun refreshData(savedInstanceState: Bundle?) {
  }

  override fun onResume() {
    super.onResume()
    if (name1 == "GLS_USDT" || name1 == "AJA_USDT") {

      LLXJD.visibility = View.GONE
      LLSJD.visibility = View.GONE
      LLZYZSD.visibility = View.GONE
      LLYJD_premium.visibility = View.VISIBLE
      tx_premium.setTextColor(activity!!.resources.getColor(R.color.appbar_background3))
      tx_Namexianjia.visibility = View.GONE
      tx_Nameshijia.visibility = View.GONE
      tx_NameZhiyin.visibility = View.GONE
      tx_premium.visibility = View.VISIBLE
      LLtype = "4"
    }
    staticdata()
    sellState1()
    sellState2()
    sellState3()


    getID()
    //获取最新成交价
    net()
    //长连接
    SocketData()
    //交易大厅List
    getTickMaket()
    //获取系统小数位数
    getBiListData()

    //币资产信息
    WebSocket()

    if (kv.decodeString("tokenId") != null) {
      //获取用户可用币数量
      getMoney()
    }
    putData(BINAME + "_" + BINAMEDATA)
  }

  @SuppressLint("CheckResult", "SetTextI18n")
  private fun putData(s: String) {
    val hashMap = HashMap<String, String>()
    hashMap["delkeys"] = s
    ApiFactory.getInstance()
        .getIndexMaketList(hashMap)
        .compose(RxSchedulers.io_main())
        .subscribe({ arrayListData ->

          if (arrayListData.data != null) {

            mainData = arrayListData.data
            try {
              textviewPurMoney2.text = "¥" + arrayListData.data[0].usdtCny.setScale(
                  2, BigDecimal.ROUND_HALF_UP
              ).stripTrailingZeros().toPlainString()
            } catch (e: Exception) {
              textviewPurMoney2.text = "¥0"
            }

          } else {
            t(arrayListData.msg)

          }

        }, { throwable ->

        })
  }

  override fun initUiAndListener() {}

  override fun getLayoutId(): Int = R.layout.fragment_market_sale

  var adapter2: JustOneAdapter? = null

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    val view = LayoutInflater.from(context)
        .inflate(R.layout.header_market_sale, null)
    listview.addHeaderView(view)
    listview2.addHeaderView(view)

    adapter2 = JustOneAdapter(context, arrayListOf("file:///android_asset/pie-nest.html"))
    listview2.adapter = adapter2

    tx_Namexianjia.setOnClickListener(click)
    tx_Nameshijia.setOnClickListener(click)
    tx_NameZhiyin.setOnClickListener(click)
    tx_premium.setOnClickListener(click)
    LL_Fixed_price.setOnClickListener(click)
    XJPriceBtnSub.setOnClickListener(click)
    edt_XDPrice.setOnClickListener(click)
    XJPriceBtnAdd.setOnClickListener(click)
    TXRMBPrice.setOnClickListener(click)
    XJNuberBtnSub.setOnClickListener(click)
    edt_XDnuber.setOnClickListener(click)
    XJNuberBtnAdd.setOnClickListener(click)
    btnXD1.setOnClickListener(click)
    btnXD2.setOnClickListener(click)
    btnXD3.setOnClickListener(click)
    btnXD4.setOnClickListener(click)
    edt_XJMoney.setOnClickListener(click)
    LLMoney.setOnClickListener(click)
    LLXJD.setOnClickListener(click)
    LLSJ.setOnClickListener(click)
    SJNuberBtnSub.setOnClickListener(click)
    edt_SJnuber.setOnClickListener(click)
    SJNuberBtnAdd.setOnClickListener(click)
    SJBtn1.setOnClickListener(click)
    SJBtn2.setOnClickListener(click)
    SJBtn3.setOnClickListener(click)
    SJBtn4.setOnClickListener(click)
    LLSJD.setOnClickListener(click)
    ZYZSCfBtnPriceSub.setOnClickListener(click)
    EDT_ZYZSCFPrice.setOnClickListener(click)
    ZYZSCfBtnPriceAdd.setOnClickListener(click)
    TXZSZYMoneyPrice.setOnClickListener(click)
    ZYZSWtBtnPriceSub.setOnClickListener(click)
    edt_ZYZSPrice.setOnClickListener(click)
    ZYZSWtBtnPriceAdd.setOnClickListener(click)
    ZSZYMoneyWTPrice.setOnClickListener(click)
    ZYZSWtBtnNuberSub.setOnClickListener(click)
    edt_ZYZSnuber.setOnClickListener(click)
    ZYZSWtBtnNuberAdd.setOnClickListener(click)
    ZYZSBtn1.setOnClickListener(click)
    ZYZSBtn2.setOnClickListener(click)
    ZYZSBtn3.setOnClickListener(click)
    ZYZSBtn4.setOnClickListener(click)
    LLZYZSD.setOnClickListener(click)
    tx_usdt.setOnClickListener(click)
    tx_cny.setOnClickListener(click)
    btn_sell.setOnClickListener(click)
    TypeText.setOnClickListener(click)
    LLbuysell.setOnClickListener(click)
    textviewPurMoney1.setOnClickListener(click)
    textviewPurMoney2.setOnClickListener(click)
    tx_type.setOnClickListener(click)
    MoneyWater.setOnClickListener(click)
    img_type.setOnClickListener(click)
    rlgone.setOnClickListener(click)
    ll_title.setOnClickListener(click)
//        LLList.setOnClickListener(click) 切换时隐藏list或webview
    LLYJD_premium.setOnClickListener(click)
    LLYJpremium.setOnClickListener(click)
    edt_YJNnuber.setOnClickListener(click)
    YJNBtn1.setOnClickListener(click)
    YJNBtn2.setOnClickListener(click)
    YJNBtn3.setOnClickListener(click)
    YJNBtn4.setOnClickListener(click)
  }

  val click = object : View.OnClickListener {
    override fun onClick(v: View) {
      when (v.id) {

        //限单价
        R.id.tx_Namexianjia -> {
          edt_SJnuber.setText("")

          EDT_ZYZSCFPrice.setText("")
          edt_ZYZSPrice.setText("")
          edt_ZYZSnuber.setText("")
          edt_YJNnuber.setText("")
          tx_Namexianjia.setTextColor(activity!!.resources.getColor(R.color.appbar_background3))
          tx_Nameshijia.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_NameZhiyin.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_premium.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          LLXJD.visibility = View.VISIBLE
          LLSJD.visibility = View.GONE
          LLZYZSD.visibility = View.GONE
          LLYJD_premium.visibility = View.GONE

          LLtype = "1"
        }
        //市价单
        R.id.tx_Nameshijia -> {
          edt_XDPrice.setText("")
          edt_XDnuber.setText("")
          edt_XJMoney.setText("")

          EDT_ZYZSCFPrice.setText("")
          edt_ZYZSPrice.setText("")
          edt_ZYZSnuber.setText("")

          edt_YJNnuber.setText("")
          tx_Namexianjia.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_Nameshijia.setTextColor(activity!!.resources.getColor(R.color.appbar_background3))
          tx_NameZhiyin.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_premium.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          LLXJD.visibility = View.GONE
          LLSJD.visibility = View.VISIBLE
          LLZYZSD.visibility = View.GONE
          LLYJD_premium.visibility = View.GONE
          LLtype = "2"
        }
        //止盈止损
        R.id.tx_NameZhiyin -> {

          edt_XDPrice.setText("")
          edt_XDnuber.setText("")
          edt_XJMoney.setText("")

          edt_SJnuber.setText("")
          edt_YJNnuber.setText("")
          tx_Namexianjia.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_Nameshijia.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_NameZhiyin.setTextColor(activity!!.resources.getColor(R.color.appbar_background3))
          tx_premium.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          LLXJD.visibility = View.GONE
          LLSJD.visibility = View.GONE
          LLZYZSD.visibility = View.VISIBLE
          LLYJD_premium.visibility = View.GONE
          LLtype = "3"
        }
        R.id.tx_premium -> {
          edt_XDPrice.setText("")
          edt_XDnuber.setText("")
          edt_XJMoney.setText("")

          EDT_ZYZSCFPrice.setText("")
          edt_ZYZSPrice.setText("")
          edt_ZYZSnuber.setText("")
          edt_YJNnuber.setText("")
          tx_Namexianjia.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_Nameshijia.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_NameZhiyin.setTextColor(activity!!.resources.getColor(R.color.background_baise))
          tx_premium.setTextColor(activity!!.resources.getColor(R.color.appbar_background3))
          LLXJD.visibility = View.GONE
          LLSJD.visibility = View.GONE
          LLZYZSD.visibility = View.GONE
          LLYJD_premium.visibility = View.VISIBLE
          LLtype = "4"
        }
        R.id.LL_Fixed_price -> {
        }
        //限单价价格减
        R.id.XJPriceBtnSub -> if (edt_XDPrice.text.toString().trim({ it <= ' ' }) != "") {
          if (BigDecimal(edt_XDPrice.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 0
          ) {
            edt_XDPrice.setText("0")
          }
          if (BigDecimal(edt_XDPrice.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 1
          ) {
            val price = BigDecimal(edt_XDPrice.text.toString().trim({ it <= ' ' }))
            val subPrice = price.subtract(
                BigDecimal(XJPriceSubMethod(edt_XDPrice.text.toString().trim({ it <= ' ' })))
            )
            edt_XDPrice.setText(
                subPrice.setScale(
                    pricNuber!!.toInt(), BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString()
            )
          }

        }
        R.id.edt_XDPrice -> {
        }

        //限单价价格加
        R.id.XJPriceBtnAdd -> if (edt_XDnuber.text.toString().trim({ it <= ' ' }) != "") {
          val price = BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' }))
          val subPrice =
            price.add(BigDecimal(XJPriceSubMethod(edt_XDnuber.text.toString().trim({ it <= ' ' }))))
          edt_XDnuber.setText(
              subPrice.setScale(
                  quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
              ).stripTrailingZeros().toPlainString()
          )
        } else {
          edt_XDnuber.setText("1")
        }
        R.id.TXRMBPrice -> {
        }

        //限单价数量减
        R.id.XJNuberBtnSub -> if (edt_XDnuber.text.toString().trim({ it <= ' ' }) != "") {
          if (BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 0
          ) {
            edt_XDnuber.setText("0")
          }
          if (BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 1
          ) {
            val price = BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' }))
            val subPrice = price.subtract(
                BigDecimal(XJPriceSubMethod(edt_XDnuber.text.toString().trim({ it <= ' ' })))
            )
            edt_XDnuber.setText(
                subPrice.setScale(
                    quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString()
            )
          }

        }
        R.id.edt_XDnuber -> {
        }
        //限单价数量加
        R.id.XJNuberBtnAdd ->

          if (edt_XDnuber.text.toString().trim({ it <= ' ' }) != "") {
            val price = BigDecimal(edt_XDnuber.text.toString().trim({ it <= ' ' }))
            val subPrice = price.add(
                BigDecimal(XJPriceSubMethod(edt_XDnuber.text.toString().trim({ it <= ' ' })))
            )
            edt_XDnuber.setText(
                subPrice.setScale(
                    quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString()
            )
          } else {
            edt_XDnuber.setText("1")
          }
        //25%
        R.id.btnXD1 -> if (kv.decodeString("tokenId") != null) {
          btnXD1.setBackgroundResource(R.drawable.backbtn)
          btnXD2.setBackgroundResource(R.drawable.btn_grd)
          btnXD3.setBackgroundResource(R.drawable.btn_grd)
          btnXD4.setBackgroundResource(R.drawable.btn_grd)
          if (edt_XDPrice.text.toString().trim(
                  { it <= ' ' }) != null && edt_XDPrice.text.toString().trim({ it <= ' ' }) != "") {
            try {
              val NuberType = BigDecimal(Nube)
              val newNuber = NuberType.multiply(BigDecimal("0.25"))
              edt_XDnuber.setText(
                  newNuber.setScale(
                      quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                  ).stripTrailingZeros().toPlainString()
              )
            } catch (e: Exception) {

            }

          } else {
            edt_XDnuber.setText("0")
          }

        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }

        //50%
        R.id.btnXD2 -> if (kv.decodeString("tokenId") != null) {
          btnXD1.setBackgroundResource(R.drawable.btn_grd)
          btnXD2.setBackgroundResource(R.drawable.backbtn)
          btnXD3.setBackgroundResource(R.drawable.btn_grd)
          btnXD4.setBackgroundResource(R.drawable.btn_grd)
          if (edt_XDPrice.text.toString().trim(
                  { it <= ' ' }) != null && edt_XDPrice.text.toString().trim({ it <= ' ' }) != "") {
            try {
              val NuberType = BigDecimal(Nube)
              val newNuber1 = NuberType.multiply(BigDecimal("0.50"))

              edt_XDnuber.setText(
                  newNuber1.setScale(
                      quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                  ).stripTrailingZeros().toPlainString()
              )

            } catch (e: Exception) {

            }

          } else {
            edt_XDnuber.setText("0")
          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        //75%

        R.id.btnXD3 -> if (kv.decodeString("tokenId") != null) {
          btnXD1.setBackgroundResource(R.drawable.btn_grd)
          btnXD2.setBackgroundResource(R.drawable.btn_grd)
          btnXD3.setBackgroundResource(R.drawable.backbtn)
          btnXD4.setBackgroundResource(R.drawable.btn_grd)
          if (edt_XDPrice.text.toString().trim(
                  { it <= ' ' }) != null && edt_XDPrice.text.toString().trim({ it <= ' ' }) != "") {
            try {

              val NuberType = BigDecimal(Nube)
              val newNuber2 = NuberType.multiply(BigDecimal("0.75"))

              edt_XDnuber.setText(
                  newNuber2.setScale(
                      quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                  ).stripTrailingZeros().toPlainString()
              )
            } catch (e: Exception) {

            }

          } else {
            edt_XDnuber.setText("0")
          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        //100%
        R.id.btnXD4 -> {

          Log.e(TAG, "onViewClicked: " + "  //100%")
          if (kv.decodeString("tokenId") != null) {
            btnXD1.setBackgroundResource(R.drawable.btn_grd)
            btnXD2.setBackgroundResource(R.drawable.btn_grd)
            btnXD3.setBackgroundResource(R.drawable.btn_grd)
            btnXD4.setBackgroundResource(R.drawable.backbtn)
            if (edt_XDPrice.text.toString().trim { it <= ' ' } != null && edt_XDPrice.text.toString().trim(
                    { it <= ' ' }) != "") {
              try {
                val NuberType = BigDecimal(Nube)
                val newNuber3 = NuberType.multiply(BigDecimal("1"))
                edt_XDnuber.setText(
                    newNuber3.setScale(
                        quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString()
                )

              } catch (e: Exception) {

              }

            } else {
              edt_XDnuber.setText("0")
            }

            //                    btnXD1.setBackgroundResource(R.drawable.btn_grd);
            //                    btnXD2.setBackgroundResource(R.drawable.btn_grd);
            //                    btnXD3.setBackgroundResource(R.drawable.btn_grd);
            //                    btnXD4.setBackgroundResource(R.drawable.backbtn);
            //                    if (edtXDPrice.getText().toString().trim() != null && !edtXDPrice.getText().toString().trim().equals("")) {
            //                        BigDecimal NuberType = new BigDecimal(Nube).divide(new BigDecimal(edtXDPrice.getText().toString().trim()), 10, BigDecimal.ROUND_HALF_UP);
            //                        BigDecimal newNuber3 = NuberType.multiply(new BigDecimal("1"));
            //                        edtXDnuber.setText(newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
            //                    } else {
            //                        edtXDnuber.setText("0");
            //                    }
          } else {
            Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
                .show()
          }
        }
        R.id.edt_XJMoney -> {
        }
        R.id.LLMoney -> {
        }
        R.id.LLXJD -> {
        }
        R.id.LLSJ -> {
        }
        //市价数量减
        R.id.SJNuberBtnSub -> if (edt_SJnuber.text.toString().trim { it <= ' ' } != "") {
          if (BigDecimal(edt_SJnuber.text.toString().trim { it <= ' ' }).compareTo(
                  BigDecimal.ZERO
              ) == 0
          ) {
            edt_SJnuber.setText("0")
          }
          if (BigDecimal(edt_SJnuber.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 1
          ) {
            val price = BigDecimal(edt_SJnuber.text.toString().trim({ it <= ' ' }))
            val subPrice = price.subtract(
                BigDecimal(XJPriceSubMethod(edt_SJnuber.text.toString().trim({ it <= ' ' })))
            )
            edt_SJnuber.setText(
                subPrice.setScale(
                    quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString()
            )
          }

        }
        R.id.edt_SJnuber -> {
        }
        //市价数量加
        R.id.SJNuberBtnAdd -> if (edt_SJnuber.text.toString().trim({ it <= ' ' }) != "") {
          val price = BigDecimal(edt_SJnuber.text.toString().trim({ it <= ' ' }))
          val subPrice =
            price.add(BigDecimal(XJPriceSubMethod(edt_SJnuber.text.toString().trim({ it <= ' ' }))))
          edt_SJnuber.setText(
              subPrice.setScale(
                  quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
              ).stripTrailingZeros().toPlainString()
          )
        } else {
          edt_SJnuber.setText("1")
        }
        R.id.SJBtn1 -> if (kv.decodeString("tokenId") != null) {
          SJBtn1.setBackgroundResource(R.drawable.backbtn)
          SJBtn2.setBackgroundResource(R.drawable.btn_grd)
          SJBtn3.setBackgroundResource(R.drawable.btn_grd)
          SJBtn4.setBackgroundResource(R.drawable.btn_grd)
          if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
            try {
              val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
              val newNuber1 = NuberType.multiply(BigDecimal("0.25"))
              if (newNuber1.setScale(quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN).compareTo(
                      BigDecimal(ktYong)
                  ) == 1
              ) {
                edt_SJnuber.setText(ktYong)
              } else {
                edt_SJnuber.setText(
                    newNuber1.setScale(
                        quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString()
                )
              }
            } catch (e: Exception) {

            }

          } else {
            edt_SJnuber.setText("0")

          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        R.id.SJBtn2 -> if (kv.decodeString("tokenId") != null) {
          SJBtn1.setBackgroundResource(R.drawable.btn_grd)
          SJBtn2.setBackgroundResource(R.drawable.backbtn)
          SJBtn3.setBackgroundResource(R.drawable.btn_grd)
          SJBtn4.setBackgroundResource(R.drawable.btn_grd)
          if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
            try {
              val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
              val newNuber2 = NuberType.multiply(BigDecimal("0.50"))
              if (newNuber2.setScale(quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN).compareTo(
                      BigDecimal(ktYong)
                  ) == 1
              ) {
                edt_SJnuber.setText(ktYong)
              } else {
                edt_SJnuber.setText(
                    newNuber2.setScale(
                        quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString()
                )
              }
            } catch (e: Exception) {

            }

          } else {
            edt_SJnuber.setText("0")
          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        R.id.SJBtn3 -> if (kv.decodeString("tokenId") != null) {
          SJBtn1.setBackgroundResource(R.drawable.btn_grd)
          SJBtn2.setBackgroundResource(R.drawable.btn_grd)
          SJBtn3.setBackgroundResource(R.drawable.backbtn)
          SJBtn4.setBackgroundResource(R.drawable.btn_grd)
          if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
            try {
              val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
              val newNuber3 = NuberType.multiply(BigDecimal("0.75"))
              if (newNuber3.setScale(quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN).compareTo(
                      BigDecimal(ktYong)
                  ) == 1
              ) {
                edt_SJnuber.setText(ktYong)
              } else {
                edt_SJnuber.setText(
                    newNuber3.setScale(
                        quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString()
                )
              }
            } catch (e: Exception) {

            }

          } else {
            edt_SJnuber.setText("0")
          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        R.id.SJBtn4 -> {
          Log.e(TAG, "onViewClicked: " + "市价 100")
          if (kv.decodeString("tokenId") != null) {
            SJBtn1.setBackgroundResource(R.drawable.btn_grd)
            SJBtn2.setBackgroundResource(R.drawable.btn_grd)
            SJBtn3.setBackgroundResource(R.drawable.btn_grd)
            SJBtn4.setBackgroundResource(R.drawable.backbtn)
            if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
              try {
                val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
                val newNuber4 = NuberType.multiply(BigDecimal("1"))
                if (newNuber4.setScale(quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN).compareTo(
                        BigDecimal(ktYong)
                    ) == 1
                ) {
                  edt_SJnuber.setText(ktYong)
                } else {
                  edt_SJnuber.setText(
                      newNuber4.setScale(
                          quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                      ).stripTrailingZeros().toPlainString()
                  )
                }
              } catch (e: Exception) {

              }

            } else {

              edt_SJnuber.setText("0")
            }
          } else {
            Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
                .show()
          }
        }
        R.id.LLSJD -> {
        }
        //触发价格减
        R.id.ZYZSCfBtnPriceSub ->

          if (EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }) != "") {
            if (BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' })).compareTo(
                    BigDecimal.ZERO
                ) == 0
            ) {
              EDT_ZYZSCFPrice.setText("0")
            }
            if (BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' })).compareTo(
                    BigDecimal.ZERO
                ) == 1
            ) {
              val price = BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
              val subPrice = price.subtract(
                  BigDecimal(XJPriceSubMethod(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' })))
              )
              EDT_ZYZSCFPrice.setText(
                  subPrice.setScale(
                      pricNuber!!.toInt(), BigDecimal.ROUND_DOWN
                  ).stripTrailingZeros().toPlainString()
              )
            }

          }
        R.id.EDT_ZYZSCFPrice -> {
        }

        ////触发价格加
        R.id.ZYZSCfBtnPriceAdd -> if (EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }) != "") {
          val price = BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
          val subPrice = price.add(
              BigDecimal(XJPriceSubMethod(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' })))
          )
          EDT_ZYZSCFPrice.setText(
              subPrice.setScale(
                  pricNuber!!.toInt(), BigDecimal.ROUND_DOWN
              ).stripTrailingZeros().toPlainString()
          )
        } else {
          EDT_ZYZSCFPrice.setText("1")
        }
        R.id.TXZSZYMoneyPrice -> {
        }

        ////委托价格减
        R.id.ZYZSWtBtnPriceSub -> if (edt_ZYZSPrice.text.toString().trim({ it <= ' ' }) != "") {
          if (BigDecimal(edt_ZYZSPrice.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 0
          ) {
            edt_ZYZSPrice.setText("0")
          }
          if (BigDecimal(edt_ZYZSPrice.text.toString().trim({ it <= ' ' })).compareTo(
                  BigDecimal.ZERO
              ) == 1
          ) {
            val price = BigDecimal(edt_ZYZSPrice.text.toString().trim({ it <= ' ' }))
            val subPrice = price.subtract(
                BigDecimal(XJPriceSubMethod(edt_ZYZSPrice.text.toString().trim({ it <= ' ' })))
            )
            edt_ZYZSPrice.setText(
                subPrice.setScale(
                    pricNuber!!.toInt(), BigDecimal.ROUND_DOWN
                ).stripTrailingZeros().toPlainString()
            )
          }

        }
        R.id.edt_ZYZSPrice -> {
        }
        R.id.ZYZSWtBtnPriceAdd -> {
        }
        R.id.ZSZYMoneyWTPrice -> {
        }
        R.id.ZYZSWtBtnNuberSub -> {
        }
        R.id.edt_ZYZSnuber -> {
        }
        R.id.ZYZSWtBtnNuberAdd -> if (edt_ZYZSnuber.text.toString().trim({ it <= ' ' }) != "") {
          val price = BigDecimal(edt_ZYZSnuber.text.toString().trim({ it <= ' ' }))
          val subPrice = price.add(
              BigDecimal(XJPriceSubMethod(edt_ZYZSnuber.text.toString().trim({ it <= ' ' })))
          )
          edt_ZYZSnuber.setText(
              subPrice.setScale(
                  quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
              ).stripTrailingZeros().toPlainString()
          )
        } else {
          edt_ZYZSnuber.setText("1")
        }
        R.id.ZYZSBtn1 -> {
        }
        R.id.ZYZSBtn2 -> {
        }
        R.id.ZYZSBtn3 -> {
        }
        R.id.ZYZSBtn4 -> {
        }
        R.id.LLZYZSD -> {
        }
        R.id.tx_usdt -> {
        }

        //溢价单
        R.id.LLYJD_premium -> {
        }
        //溢价单
        R.id.LLYJpremium -> {
        }
        // 溢价单 -委托数量
        R.id.edt_YJNnuber -> {
        }
        //  溢价单   25%
        R.id.YJNBtn1 -> if (kv.decodeString("tokenId") != null) {
          YJNBtn1.setBackgroundResource(R.drawable.backbtn)
          YJNBtn2.setBackgroundResource(R.drawable.btn_grd)
          YJNBtn3.setBackgroundResource(R.drawable.btn_grd)
          YJNBtn4.setBackgroundResource(R.drawable.btn_grd)
          if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
            try {
              val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
              val newNuber4 = BigDecimal(ktYong).multiply(BigDecimal("0.25"))
              edt_YJNnuber.setText(
                  newNuber4.setScale(
                      quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                  ).stripTrailingZeros().toPlainString()
              )
            } catch (e: Exception) {

            }

            //                        edtYJNnuber.setText(newNuber1.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
          } else {
            edt_YJNnuber.setText("0")
          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        //  溢价单   50%
        R.id.YJNBtn2 -> if (kv.decodeString("tokenId") != null) {
          YJNBtn1.setBackgroundResource(R.drawable.btn_grd)
          YJNBtn2.setBackgroundResource(R.drawable.backbtn)
          YJNBtn3.setBackgroundResource(R.drawable.btn_grd)
          YJNBtn4.setBackgroundResource(R.drawable.btn_grd)
          if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
            try {
              val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
              //                            BigDecimal newNuber2 = NuberType.multiply(new BigDecimal("0.50"));
              val newNuber4 = BigDecimal(ktYong).multiply(BigDecimal("0.50"))
              edt_YJNnuber.setText(
                  newNuber4.setScale(
                      quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                  ).stripTrailingZeros().toPlainString()
              )
            } catch (e: Exception) {

            }

            //edtYJNnuber.setText(newNuber2.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
          } else {
            edt_YJNnuber.setText("0")
          }
        } else {
          Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
              .show()
        }
        //   溢价单   75%
        R.id.YJNBtn3 ->

          if (kv.decodeString("tokenId") != null) {
            YJNBtn1.setBackgroundResource(R.drawable.btn_grd)
            YJNBtn2.setBackgroundResource(R.drawable.btn_grd)
            YJNBtn3.setBackgroundResource(R.drawable.backbtn)
            YJNBtn4.setBackgroundResource(R.drawable.btn_grd)

            if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
              try {
                val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
                //                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                val newNuber4 = BigDecimal(ktYong).multiply(BigDecimal("0.75"))
                edt_YJNnuber.setText(
                    newNuber4.setScale(
                        quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString()
                )
              } catch (e: Exception) {

              }

              //edtYJNnuber.setText(newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
            } else {
              edt_YJNnuber.setText("0")
            }
          } else {
            Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
                .show()
          }
        //   溢价单  100%
        R.id.YJNBtn4 -> {

          Log.e(TAG, "onViewClicked: " + "溢价单  100%")
          if (kv.decodeString("tokenId") != null) {
            YJNBtn1.setBackgroundResource(R.drawable.btn_grd)
            YJNBtn2.setBackgroundResource(R.drawable.btn_grd)
            YJNBtn3.setBackgroundResource(R.drawable.btn_grd)
            YJNBtn4.setBackgroundResource(R.drawable.backbtn)
            if (newprice != null && newprice!!.compareTo(BigDecimal.ZERO) == 1) {
              try {
                val NuberType = BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP)
                val newNuber4 = BigDecimal(ktYong).multiply(BigDecimal("1"))
                edt_YJNnuber.setText(
                    newNuber4.setScale(
                        quantityNuber!!.toInt(), BigDecimal.ROUND_DOWN
                    ).stripTrailingZeros().toPlainString()
                )
              } catch (e: Exception) {

              }

            } else {
              edt_YJNnuber.setText("0")
            }
          } else {
            Toast.makeText(activity, "请登录后操作", Toast.LENGTH_SHORT)
                .show()
          }
        }

        R.id.tx_cny -> {
        }
        //买入
        R.id.btn_sell -> if (kv.decodeString("tokenId") != null) {

          if (LLtype == "1") {
            //限单价
            sellData()
          } else if (LLtype == "2") {
            //市价
            sellData()
          } else if (LLtype == "3") {
            if (EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }) == "") {
              Toast.makeText(activity, "请输入止损止盈价格", Toast.LENGTH_SHORT)
                  .show()
              return
            }
            if (edt_ZYZSPrice.text.toString().trim({ it <= ' ' }) == "") {
              Toast.makeText(activity, "请输入委托价格", Toast.LENGTH_SHORT)
                  .show()
              return
            }
            if (edt_ZYZSnuber.text.toString().trim({ it <= ' ' }) == "") {
              Toast.makeText(activity, "请输入委托数量", Toast.LENGTH_SHORT)
                  .show()
              return
            }
            if (datalIsts == null || datalIsts.size == 0) {
              Toast.makeText(activity, "暂无最新成交价，无法进行止盈止损交易", Toast.LENGTH_SHORT)
                  .show()
              return
            }
            view1 = activity!!.layoutInflater.inflate(R.layout.dialog_c2cbuysell, null)
            Tltle = view1!!.findViewById(R.id.tltle) as TextView
            btnDimss = view1!!.findViewById(R.id.btn_dimss) as Button
            btnSell = view1!!.findViewById(R.id.btn_sell) as Button
            if (datalIsts[0].dealPrice.compareTo(
                    BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
                ) == 1
            ) {
              mMyDialog = MyDialog(activity, 0, 0, view1, R.style.DialogTheme)
              mMyDialog!!.setCancelable(true)
              Tltle!!.text =
                "当最新价格小于或等于" + EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }) + BINAMEDATA +
                    "时,那么将会以" + edt_ZYZSPrice.getText().toString().trim(
                    { it <= ' ' }) + BINAMEDATA + "买入" +
                    edt_ZYZSnuber.getText().toString().trim({ it <= ' ' }) + BINAME
              mMyDialog!!.show()
              btnDimss!!.setOnClickListener(View.OnClickListener { mMyDialog?.dismiss() })
              btnSell!!.setOnClickListener(View.OnClickListener {
                //止盈止盈
                StopLoss()
                mMyDialog?.dismiss()
              })

            } else if (datalIsts[0].dealPrice.compareTo(
                    BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
                ) == -1
            ) {
              mMyDialog = MyDialog(activity, 0, 0, view1, R.style.DialogTheme)
              mMyDialog!!.setCancelable(true)
              Tltle!!.text =
                "当最新价格大于或等于" + EDT_ZYZSCFPrice.text.toString().trim { it <= ' ' } + BINAMEDATA +
                    "时,那么将会以" + edt_ZYZSPrice.text.toString().trim { it <= ' ' } + BINAMEDATA + "买入" + edt_ZYZSnuber.text.toString().trim(
                    { it <= ' ' }) + BINAME
              mMyDialog!!.show()
              btnDimss!!.setOnClickListener(View.OnClickListener { mMyDialog?.dismiss() })
              btnSell!!.setOnClickListener(View.OnClickListener {
                //止盈止盈
                StopLoss()
                mMyDialog?.dismiss()
              })
            } else {
              mMyDialog = MyDialog(activity, 0, 0, view1, R.style.DialogTheme)
              mMyDialog!!.setCancelable(true)
              Tltle!!.text =
                "当最新价格大于或等于" + EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }) + BINAMEDATA +
                    "时,那么将会以" + edt_ZYZSPrice.text.toString().trim(
                    { it <= ' ' }) + BINAMEDATA + "买入" + edt_ZYZSnuber.text.toString().trim(
                    { it <= ' ' }) + BINAME
              mMyDialog!!.show()
              btnDimss!!.setOnClickListener(View.OnClickListener { mMyDialog?.dismiss() })
              btnSell!!.setOnClickListener(View.OnClickListener {
                //止盈止盈
                StopLoss()
                mMyDialog?.dismiss()
              })
            }

          } else if (LLtype == "4") {

            //溢价交易
            sellData()

          }
        } else {
          Toast.makeText(activity, "请登陆", Toast.LENGTH_SHORT)
              .show()
          intent.setClass(activity!!, LoginActivity::class.java)
          activity!!.startActivity(intent)
        }

        R.id.TypeText -> {
        }
        R.id.LLbuysell -> {
        }

        //最新成交价点击
        R.id.textviewPurMoney1 -> try {
          val newMoney = newprice.toString()
          if (LLtype == "1") {
            edt_XDPrice.setText(newMoney)
          } else if (LLtype == "3") {
            edt_ZYZSPrice.setText(newMoney)
          }
        } catch (e: Exception) {
        }

        R.id.textviewPurMoney2 -> {
        }

        //切换到最近成交
        R.id.tx_type -> {
          tx_type.setTextColor(resources.getColor(R.color.appbar_background3))
          MoneyWater.setTextColor(resources.getColor(R.color.background_baise))
          ll_title.visibility = View.VISIBLE
          listview.visibility = View.VISIBLE
//                    webDialog.visibility = View.GONE
          listview2.visibility = View.GONE
        }
        //切换到资金流向
        R.id.MoneyWater -> {

          MoneyWater.setTextColor(resources.getColor(R.color.appbar_background3))
          tx_type.setTextColor(resources.getColor(R.color.background_baise))
          adapter2?.notifyDataSetChanged()
//                    webDialog.loadUrl("javascript:getdata('$BINAME')")

          ll_title.visibility = View.GONE
          listview.visibility = View.GONE
//                    webDialog.visibility = View.VISIBLE
          listview2.visibility = View.VISIBLE
        }
        R.id.img_type -> {
        }
        R.id.rlgone -> if (type == 1) {
          listview.setVisibility(View.GONE)
          listview2.visibility = View.GONE
          type = 2
          img_type.setImageResource(R.drawable.cq_dow)
        } else {
          listview.setVisibility(View.VISIBLE)
          listview2.visibility = View.VISIBLE
          type = 1
          img_type.setImageResource(R.drawable.cq_upper)
        }
        R.id.ll_title -> {
        }

        R.id.LLList -> {
        }
        R.id.webDialog -> {
        }

        else -> {
        }
      }
    }
  }

  /**
   * 限单价
   * 市价
   */
  @SuppressLint("CheckResult")
  private fun sellData() {
    try {
      if (LLtype == "2") {
        if (edt_SJnuber.text.toString().trim { it <= ' ' } == "") {
          Toast.makeText(activity, "请输入市价数量", Toast.LENGTH_SHORT)
              .show()
          return
        }
        if (BigDecimal(edt_SJnuber.text.toString().trim { it <= ' ' }).compareTo(MIN) == -1) {
          Toast.makeText(
              activity, "最小委托数量不能低于" + MIN.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT
          )
              .show()
          return
        }
        if (BigDecimal(edt_SJnuber.text.toString().trim { it <= ' ' }).compareTo(MAX) == 1) {
          Toast.makeText(
              activity, "最大委托数量不能大于" + MAX.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT
          )
              .show()
          return
        }
      } else if (LLtype == "1") {
        if (edt_XDPrice.text.toString().trim { it <= ' ' } == "") {
          Toast.makeText(activity, "请输入委托价格", Toast.LENGTH_SHORT)
              .show()
          return
        }
        if (edt_XDnuber.text.toString().trim { it <= ' ' } == "") {
          Toast.makeText(activity, "请输入委托数量", Toast.LENGTH_SHORT)
              .show()
          return
        }
        if (BigDecimal(edt_XDnuber.text.toString().trim { it <= ' ' }).compareTo(MIN) == -1) {
          Toast.makeText(
              activity, "最小委托数量不能低于" + MIN.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT
          )
              .show()
          return
        }
        if (BigDecimal(edt_XDnuber.text.toString().trim { it <= ' ' }).compareTo(MAX) == 1) {
          Toast.makeText(
              activity, "最大委托数量不能大于" + MAX.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT
          )
              .show()
          return
        }

      } else if (LLtype == "4") {

        if (edt_YJNnuber.text.toString().trim { it <= ' ' } == "") {
          Toast.makeText(activity, "请输入市价数量", Toast.LENGTH_SHORT)
              .show()
          return
        }
        if (BigDecimal(edt_YJNnuber.text.toString().trim { it <= ' ' }).compareTo(MIN) == -1) {
          Toast.makeText(
              activity, "最小委托数量不能低于" + MIN.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT
          )
              .show()
          return
        }
        if (BigDecimal(edt_YJNnuber.text.toString().trim { it <= ' ' }).compareTo(MAX) == 1) {
          Toast.makeText(
              activity, "最大委托数量不能大于" + MAX.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT
          )
              .show()
          return
        }

      }

      val hashMap = HashMap<String, String>()
      if (LLtype == "1") {
        //限价
        hashMap["delWay"] = "1"
        //委托数量
        hashMap["delNum"] = edt_XDnuber.text.toString()
            .trim { it <= ' ' }
        //委托价格
        hashMap["delAmount"] = edt_XDPrice.text.toString()
            .trim { it <= ' ' }
      } else if (LLtype == "2") {
        //市价
        hashMap["delWay"] = "2"
        hashMap["delNum"] = edt_SJnuber.text.toString()
            .trim { it <= ' ' }

      } else if (LLtype == "4") {
        hashMap["delWay"] = "3"
        hashMap["delNum"] = edt_YJNnuber.text.toString()
            .trim { it <= ' ' }
      }

      //交易币
      hashMap["coinCode"] = BINAME
      //定价币
      hashMap["fixPriceCoinCode"] = BINAMEDATA
      //(1 买 2 卖)
      hashMap["dealType"] = "2"


      ApiFactory.getInstance()
          .creatDelegation(kv.decodeString("tokenId"), hashMap)
          .compose(RxSchedulers.io_main())
          .subscribe({ data ->

            if (data.code == 10002 || data.code == 10001) {
              intent.putExtra("flage", "2")
              intent.setClass(activity!!, LoginActivity::class.java)
              startActivity(intent)
              Toast.makeText(activity, "登陆超时请重新登陆", Toast.LENGTH_SHORT)
                  .show()
            }
            if (data.code == 200) {

              edt_ZYZSPrice.setText("")
              edt_ZYZSnuber.setText("")
              Toast.makeText(activity, "委托成功", Toast.LENGTH_SHORT)
                  .show()
            } else {
              edt_ZYZSPrice.setText("")
              edt_ZYZSnuber.setText("")
              t(data.msg)
            }
          }, { throwable ->

          })

    } catch (e: Exception) {

    }

  }

  /**
   * 止盈止盈
   */
  @SuppressLint("CheckResult")
  private fun StopLoss() {

    try {
      val hashMap = HashMap<String, String>()
      hashMap["coinCode"] = BINAME
      hashMap["fixPriceCoinCode"] = BINAMEDATA
      hashMap["dealType"] = "1"
      hashMap["delAmount"] = edt_ZYZSPrice.text.toString()
          .trim({ it <= ' ' })
      hashMap["delNum"] = edt_ZYZSnuber.text.toString()
          .trim({ it <= ' ' })
      val tri = BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
      hashMap["triggerPrice"] = tri.setScale(pricNuber!!.toInt(), BigDecimal.ROUND_HALF_UP)
          .toString()

      if (datalIsts[0].dealPrice.compareTo(
              BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
          ) == 1
      ) {
        hashMap["triggerType"] = "2"
      } else if (datalIsts[0].dealPrice.compareTo(
              BigDecimal(EDT_ZYZSCFPrice.text.toString().trim({ it <= ' ' }))
          ) == -1
      ) {
        hashMap["triggerType"] = "1"
      } else {
        hashMap["triggerType"] = "1"
      }

      ApiFactory.getInstance()
          .creatStoploss(kv.decodeString("tokenId"), hashMap)
          .compose(RxSchedulers.io_main())
          .subscribe({ data ->

            if (data.code == 200) {
              edt_ZYZSPrice.setText("")
              edt_ZYZSnuber.setText("")
              EDT_ZYZSCFPrice.setText("")
              edt_XJMoney.setText("")
              t(data.msg)
            } else {

              t(data.msg)
            }

          }, { throwable ->

          })

    } catch (e: Exception) {

    }

  }

  /**
   * 小数最后一位相加相减
   *
   * @param price
   * @return
   */
  private fun XJPriceSubMethod(price: String): String {
    var NewPrice = ""
    var Length = 0
    if (price != "") {
      //是小数
      if (price.contains(".")) {
        NewPrice = price.substring(price.indexOf(".") + 1)
        Length = NewPrice.length
      }

    }

    if (Length == 1) {
      return "0.1"
    }
    if (Length == 2) {
      return "0.01"
    }
    if (Length == 3) {
      return "0.001"
    }
    if (Length == 4) {
      return "0.0001"
    }
    if (Length == 5) {
      return "0.00001"
    }
    if (Length == 6) {
      return "0.000001"
    }
    if (Length == 7) {
      return "0.0000001"
    }
    if (Length == 8) {
      return "0.00000001"
    }
    if (Length == 9) {
      return "0.000000001"
    }
    if (Length == 10) {
      return "0.0000000001"
    }
    if (Length == 11) {
      return "0.00000000001"
    }
    if (Length == 12) {
      return "0.000000000001"
    }
    if (Length == 13) {
      return "0.0000000000001"
    }
    return if (Length == 14) {
      "0.00000000000001"
    } else {
      "1"
    }

  }

  override fun onStop() {
    super.onStop()

    val disposable1 = RxWebSocket.get(WPConfig.WSUrl + "websocket/" + name1)
        .subscribe()
    if (disposable1 != null && !disposable1.isDisposed) {
      disposable1.dispose()
    }

    val disposable2 = RxWebSocket.get(WPConfig.WSUrl + "coinsocket/" + kv.decodeString("id"))
        .subscribe()
    if (disposable2 != null && !disposable2.isDisposed) {
      disposable2.dispose()
    }
  }

  override fun onPause() {
    super.onPause()

    val disposable1 = RxWebSocket.get(WPConfig.WSUrl + "websocket/" + name1)
        .subscribe()
    if (disposable1 != null && !disposable1.isDisposed) {
      disposable1.dispose()
    }

    val disposable2 = RxWebSocket.get(WPConfig.WSUrl + "coinsocket/" + kv.decodeString("id"))
        .subscribe()
    if (disposable2 != null && !disposable2.isDisposed) {
      disposable2.dispose()
    }

  }

  override fun onDestroy() {
    super.onDestroy()

    val disposable1 = RxWebSocket.get(WPConfig.WSUrl + "websocket/" + name1)
        .subscribe()
    if (disposable1 != null && !disposable1.isDisposed) {
      disposable1.dispose()
    }

    val disposable2 = RxWebSocket.get(WPConfig.WSUrl + "coinsocket/" + kv.decodeString("id"))
        .subscribe()
    if (disposable2 != null && !disposable2.isDisposed) {
      disposable2.dispose()
    }

  }
}