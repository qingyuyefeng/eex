package com.eex.mvp.transaction

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.MotionEvent
import android.widget.EditText
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.Tab
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.dialogs.StoplossAlertDialog
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.transaction.DealWay.LIMIT
import com.eex.domin.entity.transaction.DealWay.MARKET
import com.eex.domin.entity.transaction.DealWay.STOPLOSS
import com.eex.domin.entity.transaction.spot.IndustryNew
import com.eex.domin.entity.transaction.spot.PieChart
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.domin.entity.transaction.spot.Spot.FOCUS.MARKET_PRICE
import com.eex.domin.entity.transaction.spot.Spot.FOCUS.TRIGGER_PRICE
import com.eex.domin.entity.transaction.spot.TransactionRecord
import com.eex.extensions.toast
import com.eex.market.frgament.text.DepthDataBean
import com.eex.mvp.MVPBaseFragment
import com.eex.mvp.UpdateParamView
import com.eex.mvp.mainpage.MainContract
import com.eex.mvp.transaction.SpotTradeContract.View
import com.eex.navigation.Navigator
import com.eex.popup.DealTypeWindow
import com.eex.popup.LocationUtils
import com.eex.popup.TradeTypeWindow
import com.eex.popup.WindowScaleWindow
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.link184.kidadapter.setUp
import com.link184.kidadapter.simple.SingleKidAdapter
import kotlinx.android.synthetic.main.fragment_spot_transaction.attach_type
import kotlinx.android.synthetic.main.fragment_spot_transaction.btn_buy_or_sale
import kotlinx.android.synthetic.main.fragment_spot_transaction.btn_deal_type_filtrate
import kotlinx.android.synthetic.main.fragment_spot_transaction.btn_scale_filtrate
import kotlinx.android.synthetic.main.fragment_spot_transaction.content_container
import kotlinx.android.synthetic.main.fragment_spot_transaction.deal_type
import kotlinx.android.synthetic.main.fragment_spot_transaction.depth_view
import kotlinx.android.synthetic.main.fragment_spot_transaction.industry_news
import kotlinx.android.synthetic.main.fragment_spot_transaction.latest_deal_container
import kotlinx.android.synthetic.main.fragment_spot_transaction.market_buys
import kotlinx.android.synthetic.main.fragment_spot_transaction.market_sells
import kotlinx.android.synthetic.main.fragment_spot_transaction.pie_chart_container
import kotlinx.android.synthetic.main.fragment_spot_transaction.recent_records
import kotlinx.android.synthetic.main.fragment_spot_transaction.stoploss_input_box
import kotlinx.android.synthetic.main.fragment_spot_transaction.trade_type
import kotlinx.android.synthetic.main.fragment_spot_transaction.tv_current_delegation
import kotlinx.android.synthetic.main.fragment_spot_transaction.tv_deal_type_filtrate
import kotlinx.android.synthetic.main.fragment_spot_transaction.tv_history_delegation
import kotlinx.android.synthetic.main.fragment_spot_transaction.tv_latest_amount
import kotlinx.android.synthetic.main.fragment_spot_transaction.tv_latest_index_amount
import kotlinx.android.synthetic.main.fragment_spot_transaction.tv_scale_filtrate
import kotlinx.android.synthetic.main.item_industry_new.view.tv_news_content
import kotlinx.android.synthetic.main.item_industry_new.view.tv_news_time
import kotlinx.android.synthetic.main.item_industry_new.view.tv_news_title
import kotlinx.android.synthetic.main.item_industry_new.view.tv_source
import kotlinx.android.synthetic.main.item_new_deal.view.money
import kotlinx.android.synthetic.main.item_new_deal.view.nuber
import kotlinx.android.synthetic.main.item_new_deal.view.time
import kotlinx.android.synthetic.main.view_pie_chart.pie_chart
import kotlinx.android.synthetic.main.view_pie_chart.tv_big_inflow
import kotlinx.android.synthetic.main.view_pie_chart.tv_big_outflow
import kotlinx.android.synthetic.main.view_pie_chart.tv_middle_inflow
import kotlinx.android.synthetic.main.view_pie_chart.tv_middle_outflow
import kotlinx.android.synthetic.main.view_pie_chart.tv_small_inflow
import kotlinx.android.synthetic.main.view_pie_chart.tv_small_outflow
import kotlinx.android.synthetic.main.view_transaction_limit_price.btn_num_add
import kotlinx.android.synthetic.main.view_transaction_limit_price.btn_num_subtract
import kotlinx.android.synthetic.main.view_transaction_limit_price.btn_price_add
import kotlinx.android.synthetic.main.view_transaction_limit_price.btn_price_subtract
import kotlinx.android.synthetic.main.view_transaction_limit_price.edit_num
import kotlinx.android.synthetic.main.view_transaction_limit_price.edit_price
import kotlinx.android.synthetic.main.view_transaction_limit_price.fix_coin_valuation
import kotlinx.android.synthetic.main.view_transaction_limit_price.percents
import kotlinx.android.synthetic.main.view_transaction_limit_price.tv_deal_amount
import kotlinx.android.synthetic.main.view_transaction_limit_price.usable_amount
import kotlinx.android.synthetic.main.view_transaction_stoploss.btn_trigger_add
import kotlinx.android.synthetic.main.view_transaction_stoploss.btn_trigger_subtract
import kotlinx.android.synthetic.main.view_transaction_stoploss.edit_trigger_price
import kotlinx.android.synthetic.main.view_transaction_stoploss.trigger_amount_valuation
import kotlinx.android.synthetic.main.view_transaction_title.btn_index_market
import kotlinx.android.synthetic.main.view_transaction_title.tv_pair
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import java.math.BigDecimal
import java.util.ArrayList

class SpotTradeFragment : MVPBaseFragment<Spot, View, SpotTradePresenter>(),
    View, UpdateParamView {
  lateinit var buyAdapter: MarketTradeAdapter
  lateinit var sellAdapter: MarketTradeAdapter
  lateinit var dealAdapter: SingleKidAdapter<TransactionRecord>
  lateinit var newsAdapter: SingleKidAdapter<IndustryNew>

  private val magicHelper = FragmentContainerHelper()

  override val layoutId: Int
    get() = R.layout.fragment_spot_transaction

  override fun onNewParams(param: Bundle?) {
    presenter.onNewParams(param)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initViews()
    content_container.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
          CommonUtil.hideKeyboard(activity!!)
          false
        }
      }
      false
    }
  }

  private fun initViews() {
    setPairName(presenter.pairName)
    tv_pair.setOnClickListener {
      (activity as MainContract.View).selectTab(1, null)
    }
    trade_type.setOnClickListener {
      TradeTypeWindow(
          activity as Context, listOf(LIMIT, MARKET, STOPLOSS), presenter.tradeType
      ) { type ->
        presenter.tradeType = type
      }.showAsDropDown(it)
    }
    btn_deal_type_filtrate.setOnClickListener {
      val pop = DealTypeWindow(
          activity as Context, DealType.values().toList(), presenter.windowType
      ) { type ->
        presenter.windowType = type
      }
      val windowLoc = LocationUtils.calculatePopWindowPos(it, pop.menuView)
      pop.showAtLocation(it, Gravity.TOP or Gravity.START, windowLoc[0], windowLoc[1])
    }
    btn_index_market.setOnClickListener {
      val bundle = Bundle()
      bundle.putString("pair",presenter.pairName)
      bundle.putString("curPrice",presenter.attorneyPrice)
      Navigator.toCoinTradingDetailActivity(activity!!,bundle)
    }
    btn_scale_filtrate.setOnClickListener {
      val pop = WindowScaleWindow(
          activity as Context, listOf(5, 6, 7, 8), presenter.windowScale
      ) { scale ->
        presenter.windowScale = scale
      }
      val windowLoc = LocationUtils.calculatePopWindowPos(it, pop.menuView)
      pop.showAtLocation(it, Gravity.TOP or Gravity.START, windowLoc[0], windowLoc[1])
    }
    deal_type.addTab(deal_type.newTab().setText("买入"))
    deal_type.addTab(deal_type.newTab().setText("卖出"))
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
    tv_current_delegation.setOnClickListener {
      Navigator.toCoinMoneyOrdersActivity(activity!!)
    }
    tv_history_delegation.setOnClickListener {
      Navigator.toCoinMoneyOrdersActivity(activity!!, 1)
    }
    val safeClipInput: (EditText, TextWatcher, () -> Unit) -> Unit = { edit, watcher, executor ->
      edit.removeTextChangedListener(watcher)
      executor()
      edit.addTextChangedListener(watcher)
    }
    val clipInput: (EditText, Editable?, Int, Int) -> Unit = { edit, s, start, end ->
      s?.delete(start - 1, end)
      edit.text = s
      edit.setSelection(end - 1)
    }
    edit_trigger_price.addTextChangedListener(object : TextChangeWatcher() {
      override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
      ) {
        presenter.triggerPrice = edit_trigger_price.text.toString()
            .trim()
        tempInput = s?.toString() ?: ""
      }

      override fun afterTextChanged(s: Editable?) {
        val selectionStart = edit_trigger_price.selectionStart
        val selectionEnd = edit_trigger_price.selectionEnd
        if (presenter.beyondPriceLimit(tempInput)) {
          safeClipInput(edit_trigger_price, this) {
            clipInput(edit_trigger_price, s, selectionStart, selectionEnd)
            presenter.triggerPrice = edit_trigger_price.text.toString()
                .trim()
          }
        }
      }
    })
    val editPriceChangeListener = object : TextChangeWatcher() {
      override fun onTextChanged(
        price: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Int
      ) {
        presenter.attorneyPrice = price.toString()
            .trim()
        tempInput = price?.toString() ?: ""
      }

      override fun afterTextChanged(s: Editable?) {
        val selectionStart = edit_price.selectionStart
        val selectionEnd = edit_price.selectionEnd
        if (presenter.beyondPriceLimit(tempInput)) {
          safeClipInput(edit_price, this) {
            clipInput(edit_price, s, selectionStart, selectionEnd)
            presenter.attorneyPrice = edit_price.text.toString()
                .trim()
          }
        }
      }
    }

    edit_price.addTextChangedListener(editPriceChangeListener)
    val editNumChangeListener = object : TextChangeWatcher() {
      override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
      ) {
        presenter.attorneyNum = edit_num.text.toString()
            .trim()
        tempInput = s?.toString() ?: ""
      }

      override fun afterTextChanged(s: Editable?) {
        val selectionStart = edit_num.selectionStart
        val selectionEnd = edit_num.selectionEnd
        if (presenter.beyondNumLimit(tempInput)) {
          safeClipInput(edit_num, this) {
            clipInput(edit_num, s, selectionStart, selectionEnd)
            presenter.attorneyNum = edit_num.text.toString()
                .trim()
          }
        }
      }
    }

    edit_num.addTextChangedListener(editNumChangeListener)
    edit_price.setOnTouchListener { v, event ->
      if (event.action == MotionEvent.ACTION_DOWN) {
        presenter.focus = MARKET_PRICE
      }
      false
    }
    edit_trigger_price.setOnTouchListener { v, event ->
      if (event.action == MotionEvent.ACTION_DOWN) {
        presenter.focus = TRIGGER_PRICE
      }
      false
    }
    val percentTitles = arrayOf("25%", "50%", "75%", "100%")
    percents.setBackgroundResource(R.drawable.round_indicator_bg)
    val commonNavigator = CommonNavigator(activity)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
      override fun getCount() = percentTitles.size

      override fun getTitleView(
        context: Context,
        index: Int
      ): IPagerTitleView {
        val clipPagerTitleView = ClipPagerTitleView(context)
        clipPagerTitleView.text = percentTitles[index]
        clipPagerTitleView.textSize = UIUtil.dip2px(context, 10.0)
            .toFloat()
        clipPagerTitleView.textColor =
          ResourcesCompat.getColor(resources, R.color.color_4d6599, null)
        clipPagerTitleView.clipColor = Color.WHITE
        clipPagerTitleView.setOnClickListener {
          presenter.percent = BigDecimal((index + 1).toDouble() / 4.00)
        }
        return clipPagerTitleView
      }

      override fun getIndicator(context: Context): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        val navigatorHeight = context.resources.getDimension(R.dimen.layout_20dp)
        val borderWidth = UIUtil.dip2px(context, 1.0)
            .toFloat()
        val lineHeight = navigatorHeight - 2 * borderWidth
        indicator.lineHeight = lineHeight
        indicator.roundRadius = lineHeight / 2
        indicator.yOffset = borderWidth
        indicator.setColors(ResourcesCompat.getColor(resources, R.color.color_0080ff, null))
        return indicator
      }
    }
    percents.navigator = commonNavigator
    val titleContainer = commonNavigator.titleContainer
    percents.layoutParams.width = LocationUtils.measureWindowWidth(titleContainer)
    magicHelper.attachMagicIndicator(percents)

    btn_trigger_subtract.setOnClickListener {
      presenter.subtractTriggerPrice()
    }
    btn_trigger_add.setOnClickListener {
      presenter.addTriggerPrice()
    }
    btn_price_subtract.setOnClickListener {
      presenter.subtractAttorneyPrice()
    }
    btn_price_add.setOnClickListener {
      presenter.addAttorneyPrice()
    }
    btn_num_subtract.setOnClickListener {
      presenter.subtractAttorneyNum()
    }
    btn_num_add.setOnClickListener {
      presenter.addAttorneyNum()
    }
    btn_buy_or_sale.setOnClickListener {
      if (!presenter.isLogin) {
        toast { "请登陆" }
        Navigator.toLoginActivity(activity!!)
        return@setOnClickListener
      }
      if (presenter.tradeType == STOPLOSS) {
        if (edit_trigger_price.text.toString().trim().isEmpty()) {
          toast { "请输入触发价格" }
          return@setOnClickListener
        }
        if (edit_price.text.toString().trim().isEmpty()) {
          toast { "请输入委托价格" }
          return@setOnClickListener
        }
        if (edit_num.text.toString().trim().isEmpty()) {
          toast { "请输入委托数量" }
          return@setOnClickListener
        }
        if (presenter.beyondDealPrice) {
          StoplossAlertDialog(
              activity as Context,
              """
      当最新价格大于或等于
      ${presenter.triggerPrice}${presenter.fixedUnit}时,那么将会以
      ${presenter.attorneyPrice}${presenter.fixedUnit}买入
      ${presenter.attorneyNum}${presenter.tradeUnit}          
              """.trimIndent().replace("\n", "").replace("\r", "")
          ) {
            presenter.newStoploss()
          }.show()
        } else {
          StoplossAlertDialog(
              activity as Context,
              """
      当最新价格小于或等于
      ${presenter.triggerPrice}${presenter.fixedUnit}时,那么将会以
      ${presenter.attorneyPrice}${presenter.fixedUnit}买入
      ${presenter.attorneyNum}${presenter.tradeUnit}          
              """.trimIndent().replace("\n", "").replace("\r", "")
          ) {
            presenter.newStoploss()
          }.show()
        }
      } else {
        if (edit_price.text.toString().trim().isEmpty()) {
          toast { "请输入委托价格" }
          return@setOnClickListener
        }
        if (edit_num.text.toString().trim().isEmpty()) {
          toast { "请输入委托数量" }
          return@setOnClickListener
        }
        presenter.newAttorney()
      }
    }
    market_buys.layoutManager = LinearLayoutManager(activity!!, OrientationHelper.VERTICAL,false)
    buyAdapter = MarketTradeAdapter(activity!!, BUY) { position ->
      if (presenter.tradeType != MARKET) {
        setAttorneyPriceInput(
            presenter.buys[position].price.toPlainString()
        )
      }
    }
    market_buys.adapter = buyAdapter
    market_sells.layoutManager = LinearLayoutManager(activity!!, OrientationHelper.VERTICAL,true)
    sellAdapter = MarketTradeAdapter(activity!!, SALE) { position ->
      if (presenter.tradeType != MARKET) {
        setAttorneyPriceInput(
            presenter.sells[position].price.toPlainString()
        )
      }
    }
    market_sells.adapter = sellAdapter
    attach_type.addTab(attach_type.newTab().setText("最新成交"))
    attach_type.addTab(attach_type.newTab().setText("图表数据"))
    attach_type.addTab(attach_type.newTab().setText("行业快讯"))

    attach_type.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: Tab?) {
      }

      override fun onTabUnselected(tab: Tab?) {
      }

      override fun onTabSelected(tab: Tab?) {
        tab?.apply {
          when (position) {
            0 -> {
              latest_deal_container.visibility = android.view.View.VISIBLE
              pie_chart_container.visibility = android.view.View.GONE
              industry_news.visibility = android.view.View.GONE
            }
            1 -> {
              latest_deal_container.visibility = android.view.View.GONE
              pie_chart_container.visibility = android.view.View.VISIBLE
              industry_news.visibility = android.view.View.GONE
            }
            2 -> {
              latest_deal_container.visibility = android.view.View.GONE
              pie_chart_container.visibility = android.view.View.GONE
              industry_news.visibility = android.view.View.VISIBLE
              presenter.getIndustryNews()
            }
          }
        }
      }
    })
    attach_type.getTabAt(0)
        ?.select()
    dealAdapter = recent_records.setUp<TransactionRecord> {
      withLayoutManager {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      }
      withLayoutResId(R.layout.item_new_deal)
      withItems(mutableListOf())
      bind { item ->
        time.text = item.time
        money.text = item.price.setScale(5, BigDecimal.ROUND_DOWN)
            .stripTrailingZeros()
            .toPlainString()
        nuber.text = item.num.setScale(5, BigDecimal.ROUND_DOWN)
            .stripTrailingZeros()
            .toPlainString()
        if (item.beyondNext) {
          money.setTextColor(ResourcesCompat.getColor(resources, R.color.K_bul, null))
        } else {
          money.setTextColor(ResourcesCompat.getColor(resources, R.color.color_a50000, null))
        }
      }
    }
    newsAdapter = industry_news.setUp<IndustryNew> {
      withLayoutManager {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      }
      withLayoutResId(R.layout.item_industry_new)
      withItems(mutableListOf())
      bind { item ->
        tv_news_title.text = item.title
        tv_news_content.text = item.content
        tv_source.text = item.source
        tv_news_time.text = item.time
        setOnClickListener {
          Navigator.toNewsDetail(activity as Context, item.id)
        }
      }
    }
    presenter.reload()
  }

  override fun setPairName(pair: String) {
    tv_pair.text = pair
  }

  override fun clearInput() {
    edit_trigger_price.setText("")
    edit_price.setText("")
    edit_num.setText("")
    percents.navigator.onPageSelected(0)
  }

  override fun setCurrentDealTypeFiltrate(windowType: DealType) {
    tv_deal_type_filtrate.text = windowType.desc
  }

  override fun setCurrentWindowScaleFiltrate(scale: Int) {
    tv_scale_filtrate.text = "${scale}位小数"
  }

  override fun drawDepthMap(
    buys: List<TransactionRecord>,
    sells: List<TransactionRecord>
  ) {
    val listDepthBuy = ArrayList<DepthDataBean>()
    val listDepthSell = ArrayList<DepthDataBean>()
    var obj: DepthDataBean
    var obj1: DepthDataBean
    var price: String
    var volume: String
    try {
      if (buys.isNotEmpty()) {
        for (i in buys.indices) {
          obj = DepthDataBean()
          obj1 = DepthDataBean()
          price = buys[i]
              .price
              .setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          volume = buys[i]
              .num
              .setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          obj.volume = java.lang.Float.valueOf(volume)
          obj.price = java.lang.Float.valueOf(price)
          obj1.volume = java.lang.Float.valueOf(volume)
          obj1.price = java.lang.Float.valueOf(price)
          listDepthBuy.add(obj)
        }
      }
      if (sells.isNotEmpty()) {
        for (i in sells.indices) {
          obj = DepthDataBean()
          obj1 = DepthDataBean()
          price = sells[i]
              .price
              .setScale(4, BigDecimal.ROUND_HALF_DOWN)
              .toString()
          volume = sells[i]
              .num
              .setScale(4, BigDecimal.ROUND_HALF_DOWN)
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

  override fun switchPercent(index: Int) {
    magicHelper.handlePageSelected(index)
  }

  override fun validTradeButton() {
    btn_buy_or_sale.isEnabled = true
  }

  override fun inValidTradeButton() {
    btn_buy_or_sale.isEnabled = false
  }

  override fun setBuyButton() {
    btn_buy_or_sale.text = "买入"
    btn_buy_or_sale.setBackgroundResource(R.drawable.btn_solid_buy)
  }

  override fun showUsableWhileBuy(quantity: String) {
    usable_amount.text = "可用 $quantity ${presenter.fixedUnit}"
  }

  override fun setSellButton() {
    btn_buy_or_sale.text = "卖出"
    btn_buy_or_sale.setBackgroundResource(R.drawable.btn_solid_sell)
  }

  override fun showUsableWhileSell(quantity: String) {
    usable_amount.text = "可用 $quantity ${presenter.tradeUnit}"
  }

  override fun showStoplossInputBox() {
    stoploss_input_box.visibility = android.view.View.VISIBLE
  }

  override fun hideStoplossInputBox() {
    stoploss_input_box.visibility = android.view.View.GONE
  }

  override fun updateTradeTypeTitle(title: String) {
    trade_type.text = title
  }

  override fun setTriggerValuation(price: String) {
    trigger_amount_valuation.text = "≈$price ${presenter.fixedUnit}"
  }

  override fun setAttorneyValuation(price: String) {
//    fix_coin_valuation.text = "≈$price ${presenter.fixedUnit}"
    fix_coin_valuation.text = "≈$price CNY"
  }

  override fun setPriceInputHint(hint: String) {
    edit_price.hint = hint
  }

  override fun enablePriceInput() {
    edit_price.isEnabled = true
    btn_price_subtract.isClickable = true
    btn_price_add.isClickable = true
  }

  override fun disablePriceInput() {
    edit_price.isEnabled = false
    btn_price_subtract.isClickable = false
    btn_price_add.isClickable = false
  }

  override fun setAttorneyPriceInput(price: String) {
    when (presenter.focus) {
      MARKET_PRICE -> edit_price.setText(price)
      TRIGGER_PRICE -> edit_trigger_price.setText(price)
    }
  }

  override fun setAttornetPriceSelection() {
    edit_price.setSelection(edit_price.text.toString().length)
  }

  override fun setTriggerPriceInput(price: String) {
    edit_trigger_price.setText(price)
  }

  override fun setTriggerPriceSelection() {
    edit_trigger_price.setSelection(edit_trigger_price.text.toString().length)
  }

  override fun setAttorneyNumInput(num: String) {
    edit_num.setText(num)
  }

  override fun setAttorneyNumSelection() {
    edit_num.setSelection(edit_num.text.toString().length)
  }

  override fun setDealTotalAmount(amount: String) {
    tv_deal_amount.setText(amount)
  }

  override fun refreshRecentRecords(records: List<TransactionRecord>) {
    dealAdapter.plusAssign(records.toMutableList())
  }

  override fun refreshBuys(buys: List<TransactionRecord>) {
    buyAdapter.setNewData(buys, presenter.windowScale)
  }

  override fun refreshSells(sells: List<TransactionRecord>) {
    sellAdapter.setNewData(sells, presenter.windowScale)
  }

  override fun showBuys(shown: Boolean) {
    market_buys.visibility = if (shown) android.view.View.VISIBLE else android.view.View.GONE
    buyAdapter.notifyDataSetChanged()
  }

  override fun showSells(shown: Boolean) {
    market_sells.visibility = if (shown) android.view.View.VISIBLE else android.view.View.GONE
    sellAdapter.notifyDataSetChanged()
  }

  override fun refreshNews(news: List<IndustryNew>) {
    newsAdapter.plusAssign(news.toMutableList())
  }

  override fun updateLatestDealAmount(amount: String) {
    tv_latest_amount.text = amount
  }

  override fun updateMarketPrice(amount: String) {
    edit_price.setText(amount)
  }

  override fun updateLatestIndexCnyAmount(amount: String) {
    tv_latest_index_amount.text = "≈$amount"
  }

  override fun drawPieChart(pieChart: PieChart) {
    tv_big_inflow.text = getPercent(pieChart.bigInflowPercent)
    tv_middle_inflow.text = getPercent(pieChart.middleInflowPercent)
    tv_small_inflow.text = getPercent(pieChart.smallInflowPercent)
    tv_big_outflow.text = getPercent(pieChart.bigOutflowPercent)
    tv_middle_outflow.text = getPercent(pieChart.middleOutflowPercent)
    tv_small_outflow.text = getPercent(pieChart.smallOutflowPercent)

    val pieData = listOf<PieEntry>(
        PieEntry(getFormatPercent(pieChart.smallOutflowPercent).toFloat(), ""),
        PieEntry(getFormatPercent(pieChart.middleOutflowPercent).toFloat(), ""),
        PieEntry(getFormatPercent(pieChart.bigOutflowPercent).toFloat(), ""),
        PieEntry(getFormatPercent(pieChart.bigInflowPercent).toFloat(), ""),
        PieEntry(getFormatPercent(pieChart.middleInflowPercent).toFloat(), ""),
        PieEntry(getFormatPercent(pieChart.smallInflowPercent).toFloat(), "")
    )
    val colorData = listOf<Int>(
        ResourcesCompat.getColor(resources, R.color.color_70E8A0, null),
        ResourcesCompat.getColor(resources, R.color.color_27D270, null),
        ResourcesCompat.getColor(resources, R.color.color_00a546, null),
        ResourcesCompat.getColor(resources, R.color.color_a50000, null),
        ResourcesCompat.getColor(resources, R.color.color_DF4141, null),
        ResourcesCompat.getColor(resources, R.color.color_E28383, null)
    )
    val dataSet = PieDataSet(pieData, "")
    dataSet.colors = colorData
    val pie = PieData(dataSet)
    pie.setDrawValues(false)
    pie_chart.description.isEnabled = false
    pie_chart.data = pie
    pie_chart.centerText = generateCenterSpannableText(
        getPercent(pieChart.totalOutflowPercent),
        getPercent(pieChart.totalInflowPercent)
    )
    pie_chart.transparentCircleRadius = 0f
    pie_chart.legend.isEnabled = false
    pie_chart.invalidate()
  }

  private fun generateCenterSpannableText(
    outflow: String,
    inflow: String
  ): SpannableString {
    val displayText = "流出\n$outflow\n$inflow\n流入"
    val s = SpannableString(displayText)
    s.setSpan(
        ForegroundColorSpan(ResourcesCompat.getColor(resources, R.color.color_00a546, null)), 0,
        displayText.indexOf("%") + 1, 0
    )
    s.setSpan(
        ForegroundColorSpan(ResourcesCompat.getColor(resources, R.color.color_a50000, null)),
        displayText.indexOf("%") + 1, displayText.length, 0
    )
    return s
  }

  private fun getFormatPercent(percent: BigDecimal): BigDecimal {
    return (percent * BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN)
  }

  private fun getPercent(percent: BigDecimal): String {
    return "${getFormatPercent(percent).toPlainString()}%"
  }

}