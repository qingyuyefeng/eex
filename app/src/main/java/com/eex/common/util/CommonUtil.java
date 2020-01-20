package com.eex.common.util;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.eex.EEXApp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2017/11/11.
 */

public class CommonUtil {

    // 定义保存的文件的名称
    private static final String languageName = "sharedfile";

    private static Toast mToast;
    private static Context mContext = EEXApp.Companion.getInstance();

    public static Context getContext() {
        return EEXApp.Companion.getContext();
    }

    public static void showSingleToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }


    public static int getStatusHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 模拟删除键
     */
    public static void deleteText(EditText mEdit) {
        int keyCode = KeyEvent.KEYCODE_DEL;
        KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        KeyEvent keyEventUp = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
        mEdit.onKeyDown(keyCode, keyEventDown);
        mEdit.onKeyUp(keyCode, keyEventUp);
    }

    //复制到前剪切板
    public static void copyToPhone(Context context, String summary, String url) {
        ClipData myClip = ClipData.newPlainText(summary, url);
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        myClipboard.setPrimaryClip(myClip);
    }

    /**
     * 关闭流
     *
     * @param stream
     */
    public static void closeSteam(Closeable stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    /**
     * dip转为PX
     */
    public static int dip2px(float dipValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.2f);
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 需要权限 就返回true
     *
     * @return
     */
    public static boolean checkVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static int getVersionCode() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
//        String version = packInfo.versionName;
        int code = packInfo.versionCode;
        return code;
    }


    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @return
     * @获取随机数
     */
    public static double random(double max, double min) {
        Random rand = new Random();
        return rand.nextDouble() * (max - min) + min;
    }

    public static String getTimeSS(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date(time));
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 获取tabview
     */
    public static View getTabView(XTabLayout tabLayout, int index) {
        XTabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab == null) {
            return null;
        }
        View tabView = null;
        Field view = null;
        try {
            view = XTabLayout.Tab.class.getDeclaredField("mView");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        view.setAccessible(true);
        try {
            tabView = (View) view.get(tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tabView;
    }

    public static String getJson(Context context, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm", Locale.getDefault());
        return format.format(new Date(time));
    }

    /**
     * 手机号码转*号
     *
     * @param phone
     * @return
     */
    public static String phoneView(String phone) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 9) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return "号码为空";
    }

    /**
     * 动态获取拨打电话权限
     *
     * @param phone
     * @param context
     */
    public static void call_phone(String phone, Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //首先判断否获取了权限
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                //让用户手动授权
                Toast.makeText(context, "请授权!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            context.startActivity(intent);
        }


    }


    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        Pattern pattern = Pattern
                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        if (id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 小数位数的保留（直接截取）
     */
    public static double cutNumber(double oldNumber, int digit) {
        return ((int) (oldNumber * Math.pow(10, digit))) / Math.pow(10, digit);
    }
}

