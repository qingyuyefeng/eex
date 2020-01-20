package com.eex.domin.entity.attorney

enum class DealType constructor(val value: String,val desc:String) {
  BOTH("","买卖单"),
  BUY("1","买单"),
  SALE("2","卖单");

  companion object {
    @JvmStatic
    fun from(value: String): DealType {
      for (type in values()) {
        if (value == type.value) {
          return type
        }
      }
      return BOTH
    }
  }
}