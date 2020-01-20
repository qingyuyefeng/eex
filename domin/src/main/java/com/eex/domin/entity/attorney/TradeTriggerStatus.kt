package com.eex.domin.entity.attorney

enum class TradeTriggerStatus constructor(val value: Int) {
  UN_TRIGGER(0),
  SUCCESS(1),
  FAIL(2),
  CANCEL(3);

  companion object {
    @JvmStatic
    fun from(value: Int): TradeTriggerStatus {
      for (status in values()) {
        if (value == status.value) {
          return status
        }
      }
      return UN_TRIGGER
    }
  }
}
