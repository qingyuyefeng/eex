package com.eex.domin.entity.attorney

import com.eex.domin.entity.attorney.AttorneyStatus.UN_TRADE
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.TradeTriggerStatus.UN_TRIGGER

data class AttorneyItem(
  val id:String = "",
  val tradeUnit: String = "",
  val fixedUnit: String = "",
  val time: String = "",
  val surplusQuantity: Double = 0.00,
  val totalQuantity: Double = 0.00,
  val dealQuantity:Double = 0.00,
  val dealWay:Int = 0,
  val price: Double = 0.00,
  val status: AttorneyStatus = UN_TRADE,
  val triggerStatus:TradeTriggerStatus = UN_TRIGGER,
  val dealType: DealType = BUY
)