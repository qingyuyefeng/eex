package com.eex.domin.entity.attorney

enum class AttorneyStatus constructor(val value:Int) {
  UN_TRADE(1),
  PART_TRADE(2),
  DONE(3),
  PART_CANCEL(4),
  CANCEL(5);

  companion object {
    @JvmStatic
    fun from(value: Int): AttorneyStatus {
      for (status in values()) {
        if (value == status.value) {
          return status
        }
      }
      return UN_TRADE
    }
  }
}

fun AttorneyStatus.isIn(vararg statusList:AttorneyStatus) = statusList.contains(this)