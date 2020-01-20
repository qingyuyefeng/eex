package com.eex.domin.entity.bonds

enum class BondsType(
  val value: Int,
  val desc: String
) {
  HOLD(1, "持仓"),
  ACCOUNT(2, "结算"),
  CANCEL(3, "撤单"),
  DELEGATION(4, "委托");

  companion object {
    @JvmStatic
    fun from(value: Int): BondsType {
      for (type in values()) {
        if (value == type.value) {
          return type
        }
      }
      return HOLD
    }
  }
}