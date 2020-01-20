package com.eex.domin.entity.transaction.currency

enum class PayType constructor(val value: Int) {
  BANK_CARD(1),
  ALIPAY(2),
  WEIXIN(3);

  companion object {
    @JvmStatic
    fun from(value: Int): PayType {
      for (type in values()) {
        if (value == type.value) {
          return type
        }
      }
      return BANK_CARD
    }
  }
}