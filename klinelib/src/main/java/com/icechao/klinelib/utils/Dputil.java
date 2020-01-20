package com.icechao.klinelib.utils;

import android.content.Context;
import java.sql.Date;
import java.text.SimpleDateFormat;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.icechao.klinelib.utils
 * @FileName     : Dputil.java
 * @Author       : chao
 * @Date         : 2019/4/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/

public class Dputil {
    static public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    static public int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat   sf = new SimpleDateFormat("yyyy/MM/dd");
        return sf.format(d);
    }
}
