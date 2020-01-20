package com.eex

import android.app.Activity
import timber.log.Timber
import java.util.LinkedList

class ActivityStack() {

  private val mList = LinkedList<Activity>()

  fun addActivity(activity: Activity) {
    mList.add(activity)
  }

  fun popActivity(activity: Activity) {
    mList.remove(activity)
  }

  fun current(): Activity? = try {
    mList.last()
  } catch (e:NoSuchElementException) {
    null
  }

  fun exit() {
    for (activity in mList) {
      try {
        activity.finish()
      } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
          Timber.e(e)
        }
      }
    }
  }
}