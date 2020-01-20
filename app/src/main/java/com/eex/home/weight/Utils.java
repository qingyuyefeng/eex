package com.eex.home.weight;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.eex.common.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.weight
 * @ClassName: Utils
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/4/30 9:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/30 9:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Utils {

    /**
     * 判断字符串是否为数子
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(value);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 验证是否为手机号码(支持国际格式+86XXXXXXXX)
     * <p>
     * <p>
     * 移动号段：134（0~8）、135、136、137、138、139、147、150、151、152、157、158、159、
     * 187、188
     * <p>
     * <p>
     * 联通号段：130、131、132、155、156、185
     * <p>
     * <p>
     * 电信号段：133、153、180、189
     * <p>
     *
     * @return 成功返回true，失败false
     */

    public static void initAfterSetContentView(Activity activity, View titleViewGroup) {
        if (activity == null) {
            return;
        }

        if (titleViewGroup == null) {
            return;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
            int statusBarHeight = getStatusBarHeight(activity);
            titleViewGroup.setPadding(0, statusBarHeight, 0, 0);
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleViewGroup.getLayoutParams();
            params.height = 0;
            titleViewGroup.setLayoutParams(params);
        }
    }

    public static boolean isMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$"; // 验证手机号
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证身份证
     *
     * @param idCard 15或者18位，最后一位可能是字母或者数字
     * @return 成功返回true，失败false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证护照验证
     *
     * @param idCard 15或者18位，最后一位可能是字母或者数字
     * @return 成功返回true，失败false
     */
    public static boolean huzhaoIdCard(String idCard) {
        String re1 = "[a-zA-Z]{5,17}";
        String re2 = "[a-zA-Z0-9]{5,17}";
        if (Pattern.matches(re1, idCard) || Pattern.matches(re2, idCard)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证身份信息名称
     *
     * @param name 是否包含非法字符
     * @return 成功返回true，失败false
     */
    public static boolean matcherName(String name) {

        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.find();
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
     * 验证邮箱
     *
     * @param email 传入邮箱
     * @return 成功返回true，失败false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 功能说明：图片淡入显示效果 </p>作者：ts 创建日期:2014-10-14 参数：
     *
     * @param imageView 显示图片的组件
     * @param bitmap    图片资源 示例：
     */
    public static void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        // 也可以new BitmapDrawable(imageView.getResources(), bitmap)替换成new
        // BitmapDrawable(bitmap)，目的是为了将下载下来的bitmap转换成Drawable
        final TransitionDrawable transitionDrawable = new TransitionDrawable(
                new Drawable[]{new ColorDrawable(Color.TRANSPARENT),
                        new BitmapDrawable(imageView.getResources(), bitmap)});
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    /**
     * 功能说明：图片淡入显示效果 </p>作者：ts 创建日期:2014-10-14
     *
     * @param imageView 显示图片的组件
     * @param drawable  图片资源 示例：
     */
    public static void fadeInDisplay(ImageView imageView, Drawable drawable) {
        final TransitionDrawable transitionDrawable = new TransitionDrawable(
                new Drawable[]{new ColorDrawable(Color.TRANSPARENT), drawable});

        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);

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
     * 功能说明：Resource资源转成Bitmap </p>作者：ts 创建日期:2014-10-14 参数：
     *
     * @param context
     * @param picPath Resource类型的图片资源
     * @return mBitmap 返回Bitmap类型的图片资源
     */
    public static Bitmap ResToBitmap(Context context, int picPath) {
        InputStream is = context.getResources().openRawResource(picPath);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        return mBitmap;
    }

    /**
     * 功能说明：Resource资源转成Drawable </p>作者：ts 创建日期:2014-10-14 参数：
     *
     * @param context
     * @param picPath Resource类型的图片资源
     * @return drawable 返回drawable类型的图片资源
     */
    public static Drawable ResToDrawable(Context context, int picPath) {
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(picPath);
        return drawable;
    }

    /**
     * 功能说明：View转Bitmap
     * <p>
     * 作者：ts 创建日期:2014-10-17 参数：
     *
     * @param view 传入需要转型的view
     * @return 返回一个bitmap 示例：
     */
    public static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = null;
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width != 0 && height != 0) {
                bitmap = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.layout(0, 0, width, height);
                view.setBackgroundColor(Color.WHITE);
                view.draw(canvas);
            }
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;

    }

    /**
     * dip转px
     */
    public static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dip
     */
    public static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 功能说明：获取版本号 作者：ts 创建日期:2014-10-14 参数：
     *
     * @param context
     * @return 返回版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName + "";
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 功能说明： 简单的时间格式
     * <p>
     * 作者：ts 创建日期:2014-10-14 参数：
     *
     * @param time   需要格式化的时间
     * @param format 可为null或者“” 默认格式化的模版（"MM-dd HH:mm"）
     * @return 返回模版化后的时间
     */
    public static String getFormatTimeByCustom(long time, String format) {
        if (0 == time) {
            return "";
        }
        SimpleDateFormat mDateFormat = null;
        if (!TextUtils.isEmpty(format)) {
            mDateFormat = new SimpleDateFormat(format);
        } else {
            mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        }
        return mDateFormat.format(new Date(time));

    }

    /**
     * 功能说明：时间格式化 作者：ts 创建日期:2014-10-14 参数：
     *
     * @param unFormatTime 需要格式化的时间
     * @return xxxx年xx月xx日 xx:xx
     */
    public static String getFormatTime(String unFormatTime) {
        if (unFormatTime.contains("年") || unFormatTime.contains("月")
                || unFormatTime.contains("日") || unFormatTime.contains("星期")
                || unFormatTime.contains("周")) {
            return unFormatTime;
        } else {
            if (unFormatTime.length() >= 12) {
                return unFormatTime.substring(0, 4) + "年"
                        + unFormatTime.substring(4, 6) + "月"
                        + unFormatTime.substring(6, 8) + "日" + " "
                        + unFormatTime.substring(8, 10) + ":"
                        + unFormatTime.substring(10, 12);
            } else if (unFormatTime.length() >= 8) {
                return unFormatTime.substring(0, 4) + "年"
                        + unFormatTime.substring(4, 6) + "月"
                        + unFormatTime.substring(6, 8) + "日";
            } else {
                return unFormatTime;
            }
        }
    }

    /**
     * 功能说明：时间格式化 作者：ts 创建日期:2014-10-14 参数：
     *
     * @param unFormatTime 需要格式化的时间
     * @return xxxx-xx-xx xx:xx
     */
    public static String getFormatTimeByLine(String unFormatTime) {
        if (unFormatTime.contains("年") || unFormatTime.contains("月")
                || unFormatTime.contains("日") || unFormatTime.contains("星期")
                || unFormatTime.contains("周")) {
            return unFormatTime;
        } else {
            if (unFormatTime.length() >= 12) {
                return unFormatTime.substring(0, 4) + "-"
                        + unFormatTime.substring(4, 6) + "-"
                        + unFormatTime.substring(6, 8) + " "
                        + unFormatTime.substring(8, 10) + ":"
                        + unFormatTime.substring(10, 12);
            } else if (unFormatTime.length() >= 8) {
                return unFormatTime.substring(0, 4) + "-"
                        + unFormatTime.substring(4, 6) + "-"
                        + unFormatTime.substring(6, 8);
            } else {
                return unFormatTime;
            }
        }
    }

    /**
     * 功能说明：检查网络是否连接
     * <p>
     * 作者：ts 创建日期:2014-10-14 参数：
     *
     * @param context
     * @return true已经连接网络，false未连接网络
     */
    public static synchronized boolean isNetworkOK(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 功能说明：获取状态栏高度
     * <p>
     * 作者：ts 创建日期:2014-10-14 参数：
     *
     * @param context
     * @return 返回状态栏高度（int）
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;

//        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = mContext.getResources().getDimensionPixelSize(resourceId);
//        }
    }

    /**
     * 功能说明： 获取一张assets中的图片资源的方法
     * <p>
     * 作者：ts 创建日期:2014-10-17 参数：
     *
     * @param context
     * @param name    图片路径(例：文件夹/图片的名称.png)
     * @return 返回一个Bitmap类型 示例：
     */
    public static Bitmap getBitmap(Context context, String name) {
        Bitmap temp = null;
        try {
            InputStream is = context.getAssets().open(name);
            temp = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }


    /**
     * 功能说明：判断点击的点是否在矩形内的方法
     * <p>
     * 作者：ts 创建日期:2014-10-17 参数：
     *
     * @param x     点击的x坐标
     * @param y     点击的y坐标
     * @param left  矩形框的左边x坐标
     * @param top   矩形框的上边y坐标
     * @param right 矩形框的右边x坐标
     * @param down  矩形框的下边y坐标
     * @return 在矩形框内返回true，不在返回false 示例：
     */
    public static boolean isRect(float x, float y, float left, float top,
                                 float right, float down) {
        if (x > left && x < right && y > top && y < down) {
            return true;
        }

        return false;
    }

    private static Toast toast = null;

    /**
     * 功能说明：Toast显示提示
     * <p>
     * 作者：Ts 创建日期:2014-10-17 参数：
     *
     * @param context
     * @param text    显示的文本内容
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();

    }

    public static void showToast(Context context, String text, int gravity) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.setGravity(gravity, 0, 0);
        toast.show();

    }

    public static ProgressDialog myProgerssDialog = null;
    public static View contentView = null;

    /**
     * 功能说明：自定义滚动提示圈
     * <p>
     * 作者：ts 创建日期:2014-10-17 参数：
     *
     * @param context
     * @param message 显示的内容(为null或者“” 则显示“正在加载...”)
     */

//    public static void openPragressDialog(Context context, String message) {
//        openPragressDialog(context, message,
//                ((Activity) context).findViewById(R.id.ll_base_toolber));
//    }
//
//    public static void openPragressDialog(Context context, String message,
//                                          int color) {
//        openPragressDialog(context, message,
//                ((Activity) context).findViewById(R.id.ll_base_toolber), color);
//    }
//
//    public static void openPragressDialog(Context context, String message,
//                                          View view) {
//        openPragressDialog(context, message, (int) context.getResources()
//                .getDimension(R.dimen.activity_the_navigation_bar), Color.WHITE);
//    }
//
//    public static void openPragressDialog(Context context, String message,
//                                          View view, int color) {
//        openPragressDialog(context, message, (int) context.getResources()
//                .getDimension(R.dimen.activity_the_navigation_bar), color);
//    }
//
//    public static void openPragressDialog(Context context, String message,
//                                          int top, int color) {
//        if (contentView != null)
//            closePragressDialog();
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        contentView = inflater.inflate(R.layout.view_loading, null);
//        contentView.setBackgroundColor(color);
//
//        ImageView iv_loading = (ImageView) contentView
//                .findViewById(R.id.iv_loading);
//        iv_loading.setImageResource(R.drawable.pb_popwindow_loading);
//        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading
//                .getDrawable();
//        animationDrawable.start();
//        if (color == Color.TRANSPARENT) {
//            contentView.findViewById(R.id.tv_dialog_loading_message)
//                    .setVisibility(View.GONE);
//        } else {
//            if (message != null) {
//                ((TextView) contentView
//                        .findViewById(R.id.tv_dialog_loading_message))
//                        .setText(message);
//            }
//        }
//        FrameLayout.LayoutParams fParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        fParams.topMargin = top;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//            fParams.topMargin = (int) (top + 25 * context.getResources().getDisplayMetrics().density);
//        ((Activity) context).addContentView(contentView, fParams);
//    }
//
//    public static Dialog openLoadDialog(Context context, String message) {
//
//        Dialog pragressDialog = new Dialog(context, R.style.dialog_share);
//        pragressDialog.setContentView(R.layout.dialog_loading);
//        pragressDialog.setCanceledOnTouchOutside(true);
//        pragressDialog.setCancelable(false);
//        ImageView iv_loading = (ImageView) pragressDialog.findViewById(R.id.iv_loading);
//        iv_loading.setImageResource(R.drawable.pb_popwindow_loading);
//        AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
//        animationDrawable.start();
//        if (message != null)
//            ((TextView) pragressDialog.findViewById(R.id.tv_dialog_loading_message)).setText(message);
//        pragressDialog.show();
//        return pragressDialog;
//    }
//
//    public static void openErrorDialog(Context context, String message,
//                                       View.OnClickListener clickListener) {
//        openErrorDialog(context, message, (int) context.getResources()
//                        .getDimension(R.dimen.activity_the_navigation_bar),
//                clickListener);
//    }
//
//    public static void openErrorDialog(Context context, String message,
//                                       int top, View.OnClickListener clickListener) {
//        if (contentView != null)
//            closePragressDialog();
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        contentView = inflater.inflate(R.layout.view_error, null);
//        contentView.findViewById(R.id.bt_retry).setOnClickListener(
//                clickListener);
//        if (message != null) {
//            ((TextView) contentView
//                    .findViewById(R.id.tv_dialog_loading_message))
//                    .setText(message);
//        }
//        FrameLayout.LayoutParams fParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        fParams.topMargin = top;
//        ((Activity) context).addContentView(contentView, fParams);
//    }
//
//    public static void openErrorView(ListView mListView, String message,
//                                     View.OnClickListener clickListener) {
//        if (contentView != null)
//            closePragressDialog();
//        ViewGroup listGroup = (ViewGroup) mListView.getParent();
//        if (mListView.getEmptyView() != null) {
//            listGroup.removeView(mListView.getEmptyView());
//        }
//        LayoutInflater inflater = (LayoutInflater) mListView.getContext()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View emptyView = inflater.inflate(R.layout.view_error, null);
//        emptyView.findViewById(R.id.bt_retry).setOnClickListener(clickListener);
//        if (message != null) {
//            ((TextView) emptyView.findViewById(R.id.tv_dialog_loading_message))
//                    .setText(message);
//        }
//        FrameLayout.LayoutParams fParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        listGroup.addView(emptyView, fParams);
//        mListView.setEmptyView(emptyView);
//    }

    /**
     * 能刷星
     *
     * @param mListView
     * @param message
     */
//    public static void openNoDataView(ListView mListView, String message) {
//        if (contentView != null)
//            closePragressDialog();
//        ViewGroup listGroup = (ViewGroup) mListView.getParent();
//        if (mListView.getEmptyView() != null) {
//            listGroup.removeView(mListView.getEmptyView());
//        }
//        LayoutInflater inflater = (LayoutInflater) mListView.getContext()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View emptyView = inflater.inflate(R.layout.view_nowebdata, null);
//        if (message != null) {
//            ((TextView) emptyView
//                    .findViewById(R.id.ll_dialog_loading_nodatatextview))
//                    .setText(message);
//        }
//        FrameLayout.LayoutParams fParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        listGroup.addView(emptyView, fParams);
//        mListView.setEmptyView(emptyView);
//    }

    /**
     * 不能刷星
     *
     * @param message
     */
//    public static void openNoDataView2(Context context, String message, int top) {
//
//        if (contentView != null)
//            closePragressDialog();
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        contentView = inflater.inflate(R.layout.view_nowebdata, null);
//        if (message != null) {
//            ((TextView) contentView
//                    .findViewById(R.id.ll_dialog_loading_nodatatextview))
//                    .setText(message);
//        }
//        FrameLayout.LayoutParams fParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        Log.i("高", top + "");
//        fParams.topMargin = top;
//        ((Activity) context).addContentView(contentView, fParams);
//
//    }

    /**
     * 功能说明：关闭等待框
     * <p>
     * 作者：ts 创建日期:2014-10-17
     */

    public static void closePragressDialog() {
        if (contentView != null) {
            if (contentView.getParent() != null)
                ((ViewGroup) contentView.getParent()).removeView(contentView);
            contentView = null;
        }
    }

    /**
     * 功能说明：检查是否存在SD卡
     * <p>
     * 作者：ts 创建日期:2014-10-17 参数：
     *
     * @return true存在, false不存在
     */
    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 功能说明：计算缓存大小 作者：ts 创建日期:2014-11-19 参数：
     *
     * @param cachePath 缓存路径
     * @return 缓存大小
     */
    public static float getCacheSize(String cachePath) {
        File dir = new File(cachePath);
        File[] files = dir.listFiles();

        float dirSize = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(".0")
                    || files[i].getName().contains(".jpg")
                    || files[i].getName().contains(".png")) {
                dirSize += files[i].length();
            }
        }
        if (dirSize != 0) {
            dirSize = Float.parseFloat(getDecimalsDigits(
                    String.valueOf((dirSize / (1024.0 * 1024.0))), 2));
        }

        return dirSize;
    }

    /**
     * 功能说明：截取小数位数 作者：ts 创建日期:2014-12-3 参数：
     *
     * @param data   需要截取的数据
     * @param endPos 截取小数点后的位数 示例：
     */
    public static String getDecimalsDigits(String data, int endPos) {
        String[] newDatas = data.split("\\.");
        String result = null;
        if (newDatas[1].length() >= endPos) {
            result = newDatas[0] + "." + newDatas[1].substring(0, endPos);
        } else {
            result = newDatas[0] + "." + newDatas[1];
        }
        return result;
    }

    /**
     * 功能说明：获取listView中的某条Item 作者：ts 创建日期:2014-11-27 参数：
     *
     * @param pos      选择的位置
     * @param listView
     * @return 示例：
     */
    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    /**
     * 功能说明：验证手机号码规范
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-12-23
     * <p>
     * 参数：
     *
     * @param phoneNumber 手机号码
     *                    <p>
     * @return ture：有效手机号码 ;false:无效手机号码 示例：
     */
    public static boolean verifyPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile(StringUtil.SJH_001);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 功能说明：验证支付宝账户格式是否有效
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-12-23
     * <p>
     * 参数：
     *
     * @param aliPayAccount 支付宝账户
     *                      <p>
     * @return ture:有效格式的支付宝账户 ;false:无效格式的支付宝账户 示例：
     */
    public static boolean verifyAliPayAccount(String aliPayAccount) {
        Pattern p = Pattern.compile(StringUtil.ZFB_ZH_001);
        Matcher m = p.matcher(aliPayAccount);
        return m.matches();
    }

    /**
     * 功能说明：验证支付宝密码格式是否有效
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-12-23
     * <p>
     * 参数：
     *
     * @param pwd 支付宝密码
     *            <p>
     * @return ture:有效格式的支付宝密码 ;false:无效格式的支付宝密码 示例：
     */
    public static boolean verifyAliPayPassword(String pwd) {
        Pattern p = Pattern.compile("^(([a-z0-9A-Z]+[_|\\-|\\.]?)+[a-z0-9A-Z]?@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{1,})|(((13[0-9])|(145)|(147)|(15[^4,\\D])|(18[^4,\\D]))\\d{8})$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static boolean listIsValid(List list) {
        return list != null && list.size() > 0;
    }

    public static void savaData(String data, String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, data).commit();
    }

    public static String getData(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static String formatTime(String pattern, String content) {
        if (content == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.format(simpleDateFormat.parse(content));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取IEMI
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2015年5月12日
     * <p>
     * 参数：
     *
     * @param context
     * @return 示例：
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String Imei = tm.getDeviceId();
        return Imei;
    }

    /**
     * 获取版本号
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2015年5月12日
     * <p>
     * 参数：
     *
     * @param context
     * @return 示例：
     */
    public static String getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(
                    "com.cqt.cqtordermeal.util", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    public static SpannableStringBuilder setDifferentColor(Context ctx,
                                                           String originString, int start, int preColor, int sufColor) {
        SpannableStringBuilder builder = new SpannableStringBuilder(
                originString);
        ForegroundColorSpan graySpan = new ForegroundColorSpan(preColor);
        ForegroundColorSpan greebSpan = new ForegroundColorSpan(sufColor);
        builder.setSpan(graySpan, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(greebSpan, start, originString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static Spannable setDiffrentFontSize(int startFontSize,
                                                int endStartSize, int start, SpannableStringBuilder ssb) {
        Spannable wordtoSpan = ssb;
        wordtoSpan.setSpan(new AbsoluteSizeSpan(startFontSize), 0, start,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new AbsoluteSizeSpan(endStartSize), start,
                wordtoSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return wordtoSpan;
    }

    public static void restartApplication(Context context) {
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        System.exit(0);
    }

    public static boolean getNetState(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean listIsEmpty(List list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    public static float verifyFloat(String values) {
        if (TextUtils.isEmpty(values)) {
            return 0;
        }
        return Float.parseFloat(values);
    }

    // public static Bitmap Create2DCode(String str) throws WriterException {
    // // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
    // BitMatrix matrix = new MultiFormatWriter().encode(str,
    // BarcodeFormat.QR_CODE, 400, 400);
    // int width = matrix.getWidth();
    // int height = matrix.getHeight();
    // // 二维矩阵转为一维像素数组,也就是一直横着排了
    // int[] pixels = new int[width * height];
    // for (int y = 0; y < height; y++) {
    // for (int x = 0; x < width; x++) {
    // if (matrix.get(x, y)) {
    // pixels[y * width + x] = 0xff000000;
    // } else {
    // pixels[y * width + x] = 0xffffffff;
    // }
    //
    // }
    // }
    //
    // Bitmap bitmap = Bitmap.createBitmap(width, height,
    // Bitmap.Config.ARGB_8888);
    // // 通过像素数组生成bitmap,具体参考api
    // bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    // return bitmap;
    // }

    /**
     * 丫说本地图片 采用的尺寸压缩，之前采用的质量压缩
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(Bitmap bitmap, String url) {

        if (bitmap != null && !bitmap.isRecycled()) {// 清楚加载图片时的bitmap
            bitmap.recycle();
        }
        // bitmap = BitmapFactory.decodeStream(fis);

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        bitmap = BitmapFactory.decodeFile(url, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        return bitmap = BitmapFactory.decodeFile(url, newOpts);
    }

    @SuppressWarnings("resource")
    public static boolean compressImageFromFile(String srcPath, String newPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        float hh = 1024f;//
        float ww = 1024f;//
        // 最长宽度或高度1024
        float be = 1.0f;
        if (w > h && w > ww) {
            be = w / ww;
        } else if (w < h && h > hh) {
            be = h / hh;
        }
        if (be <= 0) {
            be = 1.0f;
        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Log.e("srcPath1", srcPath);
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(newPath);
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                while (baos.toByteArray().length / 1024 > 200) {
                    baos.reset();
                    options -= 10;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                }

                Log.e("srcPath2", srcPath);
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("srcPath3", srcPath);
        return false;
    }

    /**
     * 弹出分享dialog
     *
     * @param context
     * @param listener
     * @return
     */
//    public static Dialog showShareDialog(Context context, View.OnClickListener listener) {
//
//        final Dialog dialog = new Dialog(context, R.style.dialog_share);
//        View diglog_share = LayoutInflater.from(context).inflate(
//                R.layout.dialog_share_menu, null);
//        diglog_share.measure(0, 0);
//        dialog.setContentView(diglog_share);
//        dialog.setCanceledOnTouchOutside(true);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams layoutParams = window.getAttributes();
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        layoutParams.width = dm.widthPixels;
//        layoutParams.height = diglog_share.getMeasuredHeight();
//        window.setAttributes(layoutParams);
//        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.dialog_share);
//        dialog.findViewById(R.id.ll_share_wexin1).setOnClickListener(listener);
//        dialog.findViewById(R.id.ll_share_wexin2).setOnClickListener(listener);
//        dialog.findViewById(R.id.ll_share_qq).setOnClickListener(listener);
//        dialog.findViewById(R.id.ll_share_qzone).setOnClickListener(listener);
//        dialog.findViewById(R.id.ll_share_sina).setOnClickListener(listener);
//        dialog.findViewById(R.id.ib_share_cancel).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View arg0) {
//                        dialog.dismiss();
//                    }
//                });
//        dialog.show();
//        return dialog;
//    }


//    public static void clearUserData(Context context, boolean isShowDialog) {
//
//        User user = CacheManager.getInstance().get(CacheInfo.USER_INFO, User.class);
//        /**
//         * -- 网络请求
//         */
//        // 创建请求参数,这里为电话号码和手机验证码,
//        Map<String, String> requestParam = new HashMap<>();
//        requestParam.put("memid", user.getMemid().toString());// 手机号码
//        requestParam.put("appkey", user.getAppKey());
//        // 当网络请求开始时显示进度框
//        if (isShowDialog)
//            Utils.openPragressDialog(context, "", Color.TRANSPARENT);
//        VolleyUtil.getInstance().doPostStringRequest(context,
//                AppConstants.URL_LOGOUT, requestParam, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Utils.closePragressDialog();
//                        Log.d("TAG", "onResponse: response=" + response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Utils.closePragressDialog();
//                    }
//                });
//
//
//        // 清除个人运动设置信息
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putBoolean(
//                PreferenceUtil.IS_GETFRUIT_NOTIFICATION, true);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putBoolean(
//                PreferenceUtil.IS_RUN_BG_SERVICE, true);
//        // 清除个人运动信息
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putBoolean(
//                PreferenceUtil.ISSAVE_RUN_INFO, false);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putString(
//                PreferenceUtil.USER_SEX, "F");
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putInt(
//                PreferenceUtil.USER_WEIGHT, 70);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putInt(
//                PreferenceUtil.USER_HEIGHT, 170);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putInt(
//                PreferenceUtil.USER_AGE, 26);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putInt(
//                PreferenceUtil.USER_STEPSUM, 0);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putString(
//                PreferenceUtil.GET_FRUIT_TIME, "");
//        //
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putInt(
//                PreferenceUtil.USER_LASTDONEDAYS, 0);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putInt(
//                PreferenceUtil.USER_LASTDONEDAY, 5);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putFloat(
//                PreferenceUtil.USER_LASTDONEDAYDOUBLE, 1.5f);
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putBoolean(
//                PreferenceUtil.USER_DOWN_RUN_DATA, false);
//
//        // 清理签到和益知达人 数据
//        PreferenceUtil.getInstance(App.PREFERENCE_COMMONWEAL)
//                .putBoolean(PreferenceUtil.COMMONWEAL_DAYSIGN_ISSIGN,
//                        false);
//        // 退出运动服务
//        Intent intent = new Intent(context, MoveService.class);
//        context.stopService(intent);
//        // 清理运动缓存数据
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME).putObject(
//                PreferenceUtil.RUN_DATA, null);
//        //
//        RunDataHelper dataHelper = new RunDataHelper(context);
//        dataHelper.deleteUserData(user.getMemid().toString());
//
//        PreferenceUtil.getInstance(App.PREFERENCE_NAME)
//                .putBoolean(PreferenceUtil.KEY_IF_LOGIN,
//                        false);
//        CacheManager.getInstance().remove(CacheInfo.USER_INFO);
//    }

    /**
     * 退出登录操作
     * n=2{代表从个人中心跳转过来的,user还没有清空}
     * n=1{代表从登陆跳转过来的，user已经清空}
     *
     * @param context
     */
//    public static void userLogOut(Context context, boolean isShowDialog, int n) {
//        Intent intent = new Intent();
//        if (n == 2) {
//            clearUserData(context, isShowDialog);
//            intent.setClass(context, LoginActivity.class);
//            intent.putExtra("action", "finishAll");
//        } else {
//            intent.setClass(context, LoginActivity.class);
//            intent.putExtra("action", "finishAll");
//        }
//        context.startActivity(intent);
//    }


    /**
     * ----图片压缩处理
     *
     * @param oldFilePath
     * @param newFilePath
     * @throws IOException
     */
    public static void compressBmpToFile(String oldFilePath, String newFilePath) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个isjustdecodebounds很重要
        options.inJustDecodeBounds = true;
        // 获取到这个图片的原始宽度和高度
        int picWidth = options.outWidth;
        int picHeight = options.outHeight;
        // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
        options.inSampleSize = 1;
        // 根据屏的大小和图片大小计算出缩放比例
        if (picHeight > 1920 || picWidth > 1080) {
            final int heightRatio = Math.round((float) picHeight / (float) 800);
            final int widthRatio = Math.round((float) picWidth / (float) 480);
            options.inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // 这次再真正地生成一个有像素的，经过缩放了的bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(oldFilePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100; // 个人喜欢从80开始,
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length / 1024 > 300) {
            baos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        FileOutputStream fos = new FileOutputStream(new File(newFilePath));
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
    }

    public static String takePicturePath = null;
    public static String cutPicPath;

    /**
     * 裁剪图片
     */
    public static void cutPhotoZoom(Intent data, Activity activity) {
        String picPath = null;
        // data==null，表示选择拍照，拍照后返回的data为null，不能从data获取到拍照的图片路径，故只有takePicturePath记录路径
        if (null == data) {
            picPath = takePicturePath;
        } else {// 选择图库，可以从data获取到路径
            picPath = getSelectPicPath(data, activity);
        }
        if (picPath == null) {
            return;
        }
        Uri uri = Uri.fromFile(new File(picPath));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // 保存裁剪图片路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhiyuanjia/download/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 裁剪后图片保存地址
        cutPicPath = path + System.currentTimeMillis() + ".jpg";
        intent.putExtra("output", Uri.fromFile(new File(cutPicPath)));
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, 2);
    }


    /**
     * 选择图片后得到图片的路径
     *
     * @param data
     * @param activity
     * @return
     */

    public static String getSelectPicPath(Intent data, Activity activity) {

        Uri uri = data.getData();
        String[] projection = {MediaStore.Images.Media.DATA};
        ContentResolver mContentResolver = activity.getContentResolver();
        Cursor cursor = mContentResolver.query(uri, projection, null, null,
                null);
        int columnIndex = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String picPath = cursor.getString(columnIndex);

        return picPath;
    }
}
