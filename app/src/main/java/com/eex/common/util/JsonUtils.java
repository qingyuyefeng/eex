package com.eex.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.eex.EEXApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;


/**
 * 封装JSON转换类，目前使用GSON
 * 作者：Administrator on 2016/8/18 0018 13:38
 * 邮箱：yantianfeng@seemmo.com
 *
 * @author ytf
 */
public class JsonUtils {
    private static Gson mGson = new Gson();


    public static Gson getInstance(){
        return mGson;
    }

    /**
     * 通过字符串及类获取对象实例
     *
     * @param json     JSON字符串
     * @param classOfT 类定义
     * @param <T>      泛型类型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return mGson.fromJson(json, classOfT);
    }

    /**
     * 通过字符串及类型获取对象实例
     *
     * @param json    JSON字符串
     * @param typeOfT type定义  如new TypeToken<TestGeneric<String>>(){}.getType();
     * @param <T>     泛型类型
     * @return
     */
    public static  <T> T fromJson(String json, Type typeOfT)  throws JsonSyntaxException {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return mGson.fromJson(json, typeOfT);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src 源对象
     * @return JSON字符串，如果对象为空则返回null
     */
    public static String toJson(Object src) {
        if (src == null) {
            return null;
        }
        return mGson.toJson(src);
    }

    public static class SpUtil {

        private static String name = "sp_config.cfg";
        private static SharedPreferences sp;


        private static Context mContext = EEXApp.Companion.getInstance();

        public static Context getContext() {
            return EEXApp.Companion.getContext();
        }

        public static void saveInt(String key, int value) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putInt(key, value).commit();
        }

        public static void saveInt(Context ctx, String key, int value) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putInt(key, value).commit();
        }

        public static int getInt(String key) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getInt(key, -1);
        }

        public static int getInt(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getInt(key, -1);
        }

        public static int getInt(Context ctx, String key, int defValue) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getInt(key, defValue);
        }

        public static void saveBoolean(String key, boolean value) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putBoolean(key, value).commit();
        }

        public static void saveBoolean(Context ctx, String key, boolean value) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putBoolean(key, value).commit();
        }

        public static boolean getBoolean(String key) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getBoolean(key, false);
        }

        public static boolean getBoolean(String key,boolean isTrue) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getBoolean(key, isTrue);
        }

        public static boolean getBoolean(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getBoolean(key, false);
        }

        public static boolean getBoolean(Context ctx, String key, boolean defValue) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getBoolean(key, defValue);
        }

        public static void saveLong(Context ctx, String key, long value) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putLong(key, value).commit();
        }

        public static void saveLong( String key, long value) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putLong(key, value).commit();
        }

        public static long getLong(Context ctx, String key, long defValue) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getLong(key, defValue);
        }

        public static long getLong(String key, long defValue) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getLong(key, defValue);
        }

        public static long getLong(String key) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getLong(key, -1);
        }

        public static void saveFloat(Context ctx, String key, float value) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putFloat(key, value).commit();
        }

        public static float getFloat(String key) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getFloat(key, -1);
        }

        public static float getFloat(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getFloat(key, -1);
        }

        public static float getFloat(Context ctx, String key, float defValue) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getFloat(key, defValue);
        }

        public static synchronized void saveString(String key, String value) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putString(key, value).commit();
        }

        public static synchronized void saveString(Context ctx, String key, String value) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().putString(key, value).commit();
        }

        public static String getString(String key) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getString(key, "");
        }

        public static String getString(String key, String defValue) {
            if (sp == null) {
                sp = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getString(key, defValue);
        }

        public static String getString(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getString(key, "");
        }

        public static String getString(Context ctx, String key, String defValue) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            return sp.getString(key, defValue);
        }

        public static void remove(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            sp.edit().remove(key).commit();
        }

        /**
         * 保存序列化过的对象
         *
         * @param key
         * @param obj
         */
        public static void saveObj(Context ctx, String key, Object obj) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            if (obj == null) return;
            if (!(obj instanceof Serializable)) {
                throw new IllegalArgumentException("The object should implements Serializable!");
            }

            //1.write obj to bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);

                //2.convert obj to string via Base64
                byte[] bytes = Base64.encode(baos.toByteArray(), Base64.DEFAULT);

                //3.save string
                saveString(ctx, key, new String(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * 读取序列化过的对象
         *
         * @param key
         * @return
         */
        public static Object getObj(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
            }
            //1.get string
            String string = sp.getString(key, null);

            if (TextUtils.isEmpty(string)) return null;

            //2.decode string
            byte[] bytes = Base64.decode(string, Base64.DEFAULT);

            //3.read bytes to Object
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}
