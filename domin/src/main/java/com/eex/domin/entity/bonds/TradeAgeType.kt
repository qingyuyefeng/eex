package com.eex.domin.entity.bonds

enum class TradeAgeType(val value: Int) {
  ALL(-1),
  DAILY(0),
  NEXT_DAY(1);

  companion object {
    @JvmStatic
    fun from(value: Int): TradeAgeType {
      for (type in values()) {
        if (value == type.value) {
          return type
        }
      }
      return ALL
    }
  }
}