package com.eex.extensions

import org.jetbrains.annotations.NotNull

fun String.withDefault(def: String) = if (isNullOrEmpty()) def else this

fun String.resetIf(
  @NotNull predicate: String,
  resultSetter: () -> String = {""}
) = if (isNullOrEmpty() || this == predicate) resultSetter() else this

fun String.maskPhone(): String = replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")

fun String.obtainMinCalculateScale(): String{
  var newPrice = ""
  var length = 0
  if (this != "") {
    //是小数
    if (contains(".")) {
      newPrice = substring(indexOf(".") + 1)
      length = newPrice.length
    }
  }

  if (length == 1) {
    return "0.1"
  }
  if (length == 2) {
    return "0.01"
  }
  if (length == 3) {
    return "0.001"
  }
  if (length == 4) {
    return "0.0001"
  }
  if (length == 5) {
    return "0.00001"
  }
  if (length == 6) {
    return "0.000001"
  }
  if (length == 7) {
    return "0.0000001"
  }
  if (length == 8) {
    return "0.00000001"
  }
  if (length == 9) {
    return "0.000000001"
  }
  if (length == 10) {
    return "0.0000000001"
  }
  if (length == 11) {
    return "0.00000000001"
  }
  if (length == 12) {
    return "0.000000000001"
  }
  if (length == 13) {
    return "0.0000000000001"
  }
  return if (length == 14) {
    "0.00000000000001"
  } else {
    "1"
  }
}