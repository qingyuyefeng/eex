package com.eex.domin.entity.bonds

enum class BondDelegationType(val value:Int) {
  ALL(0),MARKET_PRICE(1),TRIGGER(2);

  companion object {
    @JvmStatic
    fun from(value: Int): BondDelegationType {
      for (type in values()) {
        if (value == type.value) {
          return type
        }
      }
      return ALL
    }
  }
}