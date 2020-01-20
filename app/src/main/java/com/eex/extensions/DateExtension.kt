package com.eex.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun String.addYear(year:Int):String{
  if (this.isNullOrEmpty()) {
    return  ""
  }
  val calendar = Calendar.getInstance()
  calendar.time = StringToDate(this, "yyyy-MM-dd")
  calendar.add(Calendar.YEAR,year)
  return longToStr(calendar.timeInMillis, "yyyy-MM-dd")
}

fun StringToDate(
  dateStr: String,
  format: String
): Date? {
  val localSimpleDateFormat = SimpleDateFormat(
      format
  )
  var localDate: Date? = null
  try {
    localDate = localSimpleDateFormat.parse(dateStr)
  } catch (localParseException: ParseException) {
    localParseException.printStackTrace()
  }
  return localDate
}

fun longToStr(
  time: Long,
  format: String
): String {
  val dataFormat = SimpleDateFormat(
      format
  )
  val date = Date()
  date.time = time
  return dataFormat.format(date)
}

fun Long.toDate():String = longToStr(this, "yyyy-MM-dd")

fun Long.toFullDate():String = longToStr(this, "yy-MM-dd HH:mm:ss")
