package com.eex.domin.entity.transaction.spot

import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BOTH
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.transaction.DealWay
import com.eex.domin.entity.transaction.DealWay.LIMIT
import com.eex.domin.entity.transaction.spot.Spot.FOCUS.*
import java.math.BigDecimal

data class Spot(
  val index:Int = 0,
  val pair: String = "",
  val futerspair: String = "BTC_USDT",
  val pairs:List<String> = listOf(),
  val windowScale: Int = 5,
  val tradeUnit: String = "",
  val fixedUnit: String = "",
  val quantityMin: BigDecimal = BigDecimal(0),
  val quantityMax: BigDecimal = BigDecimal(0),
  val priceScale: Int = 0,
  val numScale: Int = 0,

  val dealType: DealType = BUY,
  val windowType: DealType = BOTH,
  val tradeType: DealWay = LIMIT,
  val isBuyOrSellValid: Boolean = true,
  val recentRecords: MutableList<TransactionRecord> = mutableListOf(),
  val marketBuyInfos: MutableList<TransactionRecord> = mutableListOf(),
  val marketSellInfos: MutableList<TransactionRecord> = mutableListOf(),
  val latestDealAmount: BigDecimal = BigDecimal(0),
  val latestIndexCnyAmount: BigDecimal = BigDecimal(0),
  /**
   * true-卖出时显示前币可用数量,false-买入时显示后币可用数量
   */
  val updateCoinQuantity: Boolean = false,
  /**
   * 交易币，即交易对前币可用数量，卖出时展示
   */
  val usableCoinQuantity: BigDecimal = BigDecimal(0),
  /**
   * 定价币，即交易对后币可用数量，买入时展示
   */
  val usableFixCoinQuantity: BigDecimal = BigDecimal(0),
  val news: List<IndustryNew> = listOf(),
  /**
   * 当前焦点
   */
  val focus:FOCUS = MARKET_PRICE,
  /**
   * 触发价估值
   */
  val triggerValuation: BigDecimal = BigDecimal(0),
  /**
   * 委托价估值
   */
  val attorneyValuation: BigDecimal = BigDecimal(0),
  /**
   * 成交价
   */
  val dealTotalAmount: BigDecimal = BigDecimal(0),
  /**
   * 委托价格
   */
  val attorneyPrice: BigDecimal = BigDecimal(0),
  /**
   * 委托数量
   */
  val attorneyNum: BigDecimal = BigDecimal(0),
  /**
   * 触发价
   */
  val triggerPrice: BigDecimal = BigDecimal(0),
  /**
   * 百分比
   */
  val percent: BigDecimal = BigDecimal(0.25),
  /**
   * 饼图
   */
  var pieChart: PieChart = PieChart()
){
  enum class FOCUS{
    IDLE,MARKET_PRICE,TRIGGER_PRICE,TRIGGER_NUM;
  }
}