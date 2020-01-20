package com.eex.domin.entity.transaction

enum class DealWay constructor(val value: Int,val desc:String) {
  LIMIT(1,"限价交易"),
  MARKET(2,"市价交易"),
  PREMIUM(3,"溢价交易"),
  STOPLOSS(4,"止盈止损");

  companion object {
    @JvmStatic
    fun from(value: Int): DealWay {
      for (way in values()) {
        if (value == way.value) {
          return way
        }
      }
      return LIMIT
    }
  }
}