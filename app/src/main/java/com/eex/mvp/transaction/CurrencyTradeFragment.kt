package com.eex.mvp.transaction

import android.content.Context
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.Tab
import com.eex.R
import com.eex.R.style
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.domin.entity.transaction.currency.PayType.ALIPAY
import com.eex.domin.entity.transaction.currency.PayType.BANK_CARD
import com.eex.domin.entity.transaction.currency.PayType.WEIXIN
import com.eex.extensions.toast
import com.eex.home.weight.MyDialog
import com.eex.mvp.UpdateParamView
import com.eex.mvp.refresh.ItemLayoutContainer
import com.eex.mvp.refresh.RefreshBaseFragment
import com.eex.navigation.Navigator
import com.eex.popup.InstantBuyPoupWindow
import com.eex.popup.PairsWindow
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.android.synthetic.main.item_currency_transaction.btn_buy_or_sale
import kotlinx.android.synthetic.main.item_currency_transaction.price
import kotlinx.android.synthetic.main.item_currency_transaction.rate
import kotlinx.android.synthetic.main.item_currency_transaction.tag_alipay
import kotlinx.android.synthetic.main.item_currency_transaction.tag_bank_card
import kotlinx.android.synthetic.main.item_currency_transaction.tag_weixin
import kotlinx.android.synthetic.main.item_currency_transaction.tv_certified_tag
import kotlinx.android.synthetic.main.item_currency_transaction.tv_limit
import kotlinx.android.synthetic.main.item_currency_transaction.tv_name
import kotlinx.android.synthetic.main.item_currency_transaction.tv_trade_num
import java.math.BigDecimal

class CurrencyTradeFragment : RefreshBaseFragment<
    Currency,
    CurrencyItem,
    CurrencyTradeContract.View<CurrencyItem>,
    CurrencyTradePresenter>(), CurrencyTradeContract.View<CurrencyItem>, UpdateParamView {
  override fun getRecyclerView(): RecyclerView = recycler_view

  override fun itemLayoutId(): Int = R.layout.item_currency_transaction

  override fun obtainItemCls(): Class<*> = CurrencyItem::class.java

  override fun genLayoutContainer(containerView: android.view.View): ItemLayoutContainer<CurrencyItem> =
    object : ItemLayoutContainer<CurrencyItem>(containerView) {
      override fun bindItem(item: CurrencyItem) {
        tv_name.text = item.name
        tv_certified_tag.visibility = if (item.certified) View.VISIBLE else View.GONE
        tv_trade_num.text =
          "数量${item.tradeNum.stripTrailingZeros().toPlainString()} ${item.tradeUnit}"
        tv_limit.text =
          "限额${item.least.stripTrailingZeros().toPlainString()}-${item.most.stripTrailingZeros().toPlainString()} ${item.tradeUnit}"
        rate.text = "${item.dealCount}/${item.dealCompleteRate}%"
        price.text = "¥${(item.boost * item.tradePrice).setScale(
            2, BigDecimal.ROUND_HALF_UP
        ).stripTrailingZeros().toPlainString()}"
        tag_bank_card.visibility =
          if (item.payTypes.contains(BANK_CARD)) View.VISIBLE else View.GONE
        tag_weixin.visibility = if (item.payTypes.contains(WEIXIN)) View.VISIBLE else View.GONE
        tag_alipay.visibility = if (item.payTypes.contains(ALIPAY)) View.VISIBLE else View.GONE
        if (item.dealType == BUY) {
          btn_buy_or_sale.text = "买入"
          btn_buy_or_sale.setBackgroundResource(R.drawable.corner_solid_3300a546)
          btn_buy_or_sale.setTextColor(
              ResourcesCompat.getColor(resources, R.color.color_00a546, null)
          )
        } else {
          btn_buy_or_sale.text = "卖出"
          btn_buy_or_sale.setBackgroundResource(R.drawable.backbtn)
          btn_buy_or_sale.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        }
        btn_buy_or_sale.setOnClickListener {
          if (presenter.isLogin) {
            presenter.trade(item.id)
          } else {
            toast { "请登录后操作" }
          }
        }
      }
    }

  override fun onNewParams(param: Bundle?) {
    presenter.onNewParams(param)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setPairName(presenter.pair)
    tv_pair.setOnClickListener {
      if (presenter.currencies.isNotEmpty()) {
        PairsWindow(
            activity as Context, presenter.currencies, presenter.pair
        ) { coin ->
          presenter.pair = coin
        }.showAsDropDown(it)
      }
    }
    deal_type.addTab(deal_type.newTab().setText("购买"))
    deal_type.addTab(deal_type.newTab().setText("出售"))
    deal_type.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: Tab?) {
      }

      override fun onTabUnselected(tab: Tab?) {
      }

      override fun onTabSelected(tab: Tab?) {
        tab?.apply {
          if (position == 0) {
            presenter.dealType = BUY
          } else {
            presenter.dealType = SALE
          }
        }
      }
    })
    deal_type.getTabAt(0)
        ?.select()
    tv_publish.setOnClickListener {
      presenter.publish()
    }
    btn_order.setOnClickListener {
      Navigator.toLegalOrderActivity(activity!!)
    }
    btn_buy_instant.setOnClickListener {
      InstantBuyPoupWindow(
          activity as Context
      ) { dealType, payType, tradeAmount ->
        //todo 极速交易
      }.showAsDropDown(title_container)
    }
    tv_sort.setOnClickListener {
      presenter.sort()
    }
    swipe_refresh.setOnRefreshListener {
      onRefresh()
    }
    presenter.loadAvailableCurrencies()
  }

  override fun setPairName(pair: String) {
    tv_pair.text = pair.replace("_", "/")
  }

  override fun lightSort(light: Boolean) {
    if (light) {
      tv_sort.setTextColor(ResourcesCompat.getColor(resources, R.color.color_0080ff, null))
    } else {
      tv_sort.setTextColor(ResourcesCompat.getColor(resources, R.color.color_8fa2cc, null))
    }
  }

  override fun refreshList(items: List<CurrencyItem>) {
    adapter update {
      replaceAllItems(items.toMutableList(), TYPE_ITEM)
    }
  }

  override fun showBindPhoneDialog() {
    val view = layoutInflater.inflate(R.layout.dialog_2cbuysell, null)
    if (presenter.hasPhoneCertify) {
      Navigator.toMeMoneyPwordActivity(this)
    } else {
      val dialog = MyDialog(activity, 0, 0, view, style.DialogTheme)
      val title = view.findViewById(R.id.tltle) as TextView
      val btnDismiss = view.findViewById(R.id.btn_dimss) as Button
      val btnSell = view.findViewById(R.id.btn_sell) as Button
      dialog.setCancelable(true)
      title.text = "请绑定手机号后交易"
      dialog.show()
      btnDismiss.setOnClickListener { dialog.dismiss() }
      btnSell.setOnClickListener {
        Navigator.toPhoneOrEmailActivity(activity!!)
        dialog.dismiss()
      }
    }
  }

  override fun showRealNameDialog() {
    val view = layoutInflater.inflate(R.layout.dialog_2cbuysell, null)
    val title = view.findViewById(R.id.tltle) as TextView
    val btnDismiss = view.findViewById(R.id.btn_dimss) as Button
    val btnSell = view.findViewById(R.id.btn_sell) as Button
    val dialog = MyDialog(activity, 0, 0, view, style.DialogTheme)
    dialog.setCancelable(true)
    title.text = "请完成实名等级2认证"
    dialog.show()
    btnDismiss.setOnClickListener { dialog.dismiss() }
    btnSell.setOnClickListener {
      Navigator.toRealNameActivity(activity!!)
      dialog.dismiss()
    }
  }

  override fun showBindPayTypeDialog() {
    val view = layoutInflater.inflate(R.layout.dialog_2cbuysell, null)
    val title = view.findViewById(R.id.tltle) as TextView
    val btnDismiss = view.findViewById(R.id.btn_dimss) as Button
    val btnSell = view.findViewById(R.id.btn_sell) as Button
    val dialog = MyDialog(activity, 0, 0, view, style.DialogTheme)
    dialog.setCancelable(true)
    title.text = "请设置收付款方式"
    dialog.show()
    btnDismiss.setOnClickListener { dialog.dismiss() }
    btnSell.setOnClickListener {
      Navigator.toLegalMethodActivity(activity!!)
      dialog.dismiss()
    }
  }

  override fun toPurchaseCurrency(id: String) {
    Navigator.toBuyCurrency(activity as Context, id)
  }

  override fun toSellCurrency(id: String) {
    Navigator.toSellCurrency(activity as Context, id)
  }

  override fun toPublish() {
    Navigator.toPublish(activity as Context)
  }

  override fun showRefreshing() {
    if (!swipe_refresh.isRefreshing) {
      swipe_refresh.isRefreshing = true
    }
  }

  override fun hideRefreshing() {
    swipe_refresh.isRefreshing = false
  }

  override fun showEmpty() {
    recycler_view.visibility = View.GONE
    empty.visibility = View.VISIBLE
  }

  override fun hideEmpty() {
    recycler_view.visibility = View.VISIBLE
    empty.visibility = View.GONE
  }

  override val layoutId: Int
    get() = R.layout.fragment_currency
}