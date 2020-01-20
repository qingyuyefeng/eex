package com.eex.mvp.transaction

import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.transaction.spot.IndustryNew
import com.eex.domin.entity.transaction.spot.PieChart
import com.eex.domin.entity.transaction.spot.TransactionRecord
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class SpotTradeContract {
  interface View : BaseView {
    fun setPairName(pair: String)
    fun clearInput()
    fun validTradeButton()
    fun inValidTradeButton()
    fun showStoplossInputBox()
    fun hideStoplossInputBox()
    fun updateTradeTypeTitle(title: String)
    fun setPriceInputHint(hint: String)
    fun disablePriceInput()
    fun enablePriceInput()
    fun setBuyButton()
    fun showUsableWhileBuy(quantity: String)
    fun setSellButton()
    fun showUsableWhileSell(quantity: String)
    fun setTriggerValuation(price: String)
    fun setAttorneyValuation(price: String)
    fun setAttorneyPriceInput(price: String)
    fun setAttornetPriceSelection()
    fun setTriggerPriceInput(price: String)
    fun setTriggerPriceSelection()
    fun setAttorneyNumInput(num: String)
    fun setAttorneyNumSelection()
    fun setDealTotalAmount(amount: String)
    fun switchPercent(index: Int)
    fun refreshRecentRecords(records: List<TransactionRecord>)
    fun refreshBuys(buys: List<TransactionRecord>)
    fun refreshSells(sells: List<TransactionRecord>)
    fun refreshNews(news: List<IndustryNew>)
    fun updateLatestDealAmount(amount: String)
    fun updateMarketPrice(amount: String)
    fun updateLatestIndexCnyAmount(amount: String)
    fun drawDepthMap(
      buys: List<TransactionRecord>,
      sells: List<TransactionRecord>
    )

    fun drawPieChart(pieChart: PieChart)

    fun showBuys(shown: Boolean)
    fun showSells(shown: Boolean)
    fun setCurrentDealTypeFiltrate(windowType: DealType)
    fun setCurrentWindowScaleFiltrate(scale: Int)
  }

  interface Presenter : BasePresenter<View>

  interface FragmentWrapperView : BaseView {
    fun renderViewPager(pair: String)
    fun renderFail()
    fun updatePair(
      pair: String
    )
    fun updateFuturePair(
      futerspair: String
    )
    fun select(index: Int)
  }

  interface FragmentWrapperPresenter : BasePresenter<FragmentWrapperView>

  interface ActivityView : BaseView

  interface ActivityPresenter : BasePresenter<ActivityView>
}
