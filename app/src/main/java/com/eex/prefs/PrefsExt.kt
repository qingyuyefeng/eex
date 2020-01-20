package com.eex.prefs

import android.content.Context
import android.content.SharedPreferences
import com.eex.constant.Constants

fun Context.sharedPreferences(spName: String = Constants.SP_NAME): SharedPreferences =
        getSharedPreferences(spName, Context.MODE_PRIVATE)
