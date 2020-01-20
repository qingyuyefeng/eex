package com.eex.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by xy on 15/12/23.
 */
public class SharedPreferencesUtils {

    private static final String KEY = "7ebit";
    public static String KEY_IF_LOGIN = "key_if_login";
    private static Context mContext;

    public static void config(Context context) {
        mContext = context;
    }

    public static String getShareData(String key) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }


    public static String getLungData(String key, String faillValue) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, faillValue);
    }


    public static int getIntShareData(String key, int defValue) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static long getLongShareData(String key, long defValue) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static boolean getBooleanShareData(String key, boolean defValue) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }


//    public static double getDoubleShareData(String key, double defValue) {
//
//        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
//        return sp.getDouble(key, defValue);
//    }


    public static void putShareData(String key, String value) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(key, value);
        et.commit();
    }

    public static void putIntShareData(String key, int value) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putInt(key, value);
        et.commit();
    }

    public static void putLongShareData(String key, long value) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putLong(key, value);
        et.commit();
    }

    public static void putBooleanShareData(String key, boolean value) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putBoolean(key, value);
        et.commit();
    }

//      public static void putDoubleShareData(String key, double value) {
//
//        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
//        SharedPreferences.Editor et = sp.edit();
//        et.putDouble(key, value);
//        et.commit();
//    }


    public static void putSetData(String key, Set value) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putStringSet(key, value);
        et.commit();
    }

    public static Set<String> getSetData(String key) {
        Set<String> set = new HashSet<>();
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getStringSet(key, set);
    }

    public static Set getLinckedSetData(String key) {

        LinkedHashSet<String> set = new LinkedHashSet<>();
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getStringSet(key, set);
    }

    public static void remove(String key) {

        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.remove(key);
        et.commit();
    }

}
