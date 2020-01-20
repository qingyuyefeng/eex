package com.eex.domin.entity.bonds

enum class BondDealType(val value: Int,val desc:String) {
  ALL(0,"全部"),
  CALL_OPTION(1,"买涨"),
  PUT_OPTION(2,"卖跌");

  companion object {
    @JvmStatic
    fun from(value: Int): BondDealType {
      for (type in values()) {
        if (value == type.value) {
          return type
        }
      }
      return ALL
    }
  }
}