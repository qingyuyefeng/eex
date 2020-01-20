package com.eex.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by Lee on 2017/11/1.
 */

public class SPHelper {
    private static SharedPreferences sp;
    private static final String MySharePreferenceName = "MySharePreferenceName";
    private static SPHelper helper;

    private SPHelper() {
    }

    public static SPHelper init(Context context) {
        if (helper == null) {
            helper = new SPHelper();
            sp = context.getApplicationContext().getSharedPreferences(MySharePreferenceName, Context.MODE_PRIVATE);
        }
        return helper;
    }

    public void put(String key, Object object) {
        if (object instanceof Integer) {
            sp.edit().putInt(key, (Integer) object).apply();
        } else if (object instanceof String) {
            sp.edit().putString(key, (String) object).apply();
        } else if (object instanceof Long) {
            sp.edit().putLong(key, (Long) object).apply();
        } else if (object instanceof Float) {
            sp.edit().putFloat(key, (Float) object).apply();
        } else if (object instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) object).apply();
        } else {
            sp.edit().putString(key, new Gson().toJson(object)).apply();
        }
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public Long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public Float getFloat(String key) {
        return sp.getFloat(key, 0);
    }

    public Boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }
}
