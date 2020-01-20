/**
 * 版权：Hillsun Cloud Commerce Technology Co. Ltd
 * 作者: cc
 * 创建日期:2014-10-11
 */
package com.eex.common.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;

/**
 * 功能说明 作者: cc 创建日期：2014-10-11下午3:37:01 示例：
 */
public class StringUtil {
    public static final String SYSTEM_FLAG = "android";
    public static final String FILE_SPLIT_FLAG = "/";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String COLON1 = "：";
    public static final String WRAP = "\n";
    public static final String PERCENT_SIGN = "%";
    public static final String ASTERISK = "*";
    public static final String IMAGE_CACHE_DIR = "";
    public static final String SEMICOLON = ";";
    public static final String PERCENT_SIGN1 = "¥";
    public static final String UP = "↑";
    public static final String DOWN = "↓";

    public static final String KEY_MER_ITEM = "MERITEM";

    public static final String KEY_MER_TYPE_ID = "itemId";
    public static final String KEY_MER_TYPE_TEXT = "itemText";

    private final char asterisk = '*';
    /**
     * 手机号码
     */
    public static final String SJH_001 = "^((13[0-9])|(145)|(147)|(15[^4,\\D])|(18[^4,\\D]))\\d{8}$";

    /**
     * 支付宝账户
     */
    public static final String ZFB_ZH_001 = "^(([a-z0-9A-Z]+[_|\\-|\\.]?)+[a-z0-9A-Z]?@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{1,})|(((13[0-9])|(145)|(147)|(15[^4,\\D])|(18[^4,\\D]))\\d{8})$";
    /**
     * 支付宝密码
     */
    public static final String ZFB_MM_001 = "^(((?=.*[0-9].*)(?=.*[A-Z].*))|((?=.*[A-Z].*)(?=.*[a-z].*))|((?=.*[0-9].*)(?=.*[a-z].*))){6,16}$";
    /**
     * 单例对象
     */
    private static StringUtil uniqueInstance = null;

    /**
     * 
     * 功能说明：获取StringUtil单例对象实例 作者：cc 创建日期:2014-10-13 参数：
     * 
     * @return 单例对象 示例：HillBuyGetuiUtil.getInstance()
     */
    public static StringUtil getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new StringUtil();
        }
        return uniqueInstance;
    }

    public static String gettextView(String phone) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 5 && i <= 8) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    };
    /**
     * 
     * 功能说明：给文本加颜色和下划线
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-11-17
     * <p>
     * 参数：
     * 
     * @param stringId
     *            文本string的基础信息
     * @param colorId
     *            颜色ID
     * @param value
     *            替换值
     * @param c
     *            内容
     * @param isUnderLine
     *            是否加下划线
     * @return SpannableStringBuilder 对象 示例：
     */
    public SpannableStringBuilder getFormatString(int stringId, int colorId, String value, Context c,
                                                  boolean isUnderLine) {
        SpannableStringBuilder style = null;
        if (stringId != 0 && colorId != 0 && value != null) {
            String basicString = c.getResources().getString(stringId);
            String formatString = String.format(basicString, value);
            int begin = basicString.indexOf(PERCENT_SIGN);
            int end = begin + value.length();
            style = new SpannableStringBuilder(formatString);
            style.setSpan(new ForegroundColorSpan(colorId), begin, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
            if (isUnderLine) {
                style.setSpan(new UnderlineSpan(), begin, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的下划线
            }
        } else {
            style = new SpannableStringBuilder("");
        }
        return style;
    }

    /**
     * 
     * 功能说明：给文本加下划线
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-11-17
     * <p>
     * 参数：
     * 
     * @param value
     *            文本值
     * @param c
     *            上下文内容
     * @param isUnderLine
     *            是否加载下划线
     * @return 示例：
     */
    public SpannableStringBuilder getFormatString(String value, Context c, boolean isUnderLine) {
        SpannableStringBuilder style = null;
        if (value != null) {
            style = new SpannableStringBuilder(value);
            if (isUnderLine) {
                style.setSpan(new UnderlineSpan(), 0, value.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的下划线
            }
        } else {
            style = new SpannableStringBuilder("");
        }
        return style;
    }

    /**
     * 
     * 功能说明：给文本加颜色
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-11-17
     * <p>
     * 参数：
     * 
     * @param stringId
     *            string.xml的基础信息
     * @param colorId
     *            颜色ID
     * @param value
     *            替换值
     * @param c
     *            上下文内容
     * @return 示例：
     */
    public SpannableStringBuilder getFormatString(int stringId, int colorId, String value, Context c) {
        SpannableStringBuilder style = null;
        if (stringId != 0 && colorId != 0 && value != null) {
            String basicString = c.getResources().getString(stringId);
            String formatString = String.format(basicString, value);
            int begin = basicString.indexOf(PERCENT_SIGN);
            int end = begin + value.length();
            style = new SpannableStringBuilder(formatString);
            style.setSpan(new ForegroundColorSpan(colorId), begin, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        } else if (stringId != 0 && value != null && colorId == 0) {
            String basicString = c.getResources().getString(stringId);
            String formatString = String.format(basicString, value);
            style = new SpannableStringBuilder(formatString);
        } else {
            style = new SpannableStringBuilder("");
        }
        return style;
    }

/*    public SpannableStringBuilder getFormatString2(int stringId, String value, Context c) {
        SpannableStringBuilder style = null;
        String basicString = c.getResources().getString(stringId);
        String formatString = String.format(basicString, value);
        int begin = formatString.indexOf(PERCENT_SIGN1);
        int end = formatString.length();
        style = new SpannableStringBuilder(formatString);
        style.setSpan(new ForegroundColorSpan(c.getResources().getColor(R.color.orange)), begin, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        return style;
    }*/

    /**
     * 
     * 功能说明：替换指定的、连续的位置的字符为*
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-11-20
     * <p>
     * 参数：
     * 
     * @param str
     *            字符串
     * @param startPoistion
     *            开始位置
     * @param endPistion
     *            结束位置
     * @return replaceAsterisk("abcdefg",2,4); 替换cde为* 示例：
     */
    public String replaceAsterisk(String str, int startPoistion, int endPistion) {
        char tmpChar[] = str.toCharArray();
        int start = startPoistion - 1;
        int end = endPistion + 1;
        for (int i = 0; i < tmpChar.length; i++) {
            if (i > start && i < end) {
                tmpChar[i] = asterisk;
            }
        }
        return new String(tmpChar);
    }

    /**
     * 
     * 功能说明：替换文本
     * <p>
     * 作者：cc
     * <p>
     * 创建日期:2014-12-1
     * <p>
     * 参数：
     * 
     * @param stringId
     *            初始文本在String.xml 中的ID
     * @param value
     *            替换值
     * @param c
     *            上下文内容
     * @return 示例：
     *//*
    public SpannableStringBuilder getFormatString(int stringId, String value, Context c) {
        SpannableStringBuilder style = null;
        if (stringId != 0 && value != null) {
            String basicString = c.getResources().getString(stringId);
            String formatString = String.format(basicString, value);
            style = new SpannableStringBuilder(formatString);
        } else {
            style = new SpannableStringBuilder("");
        }
        return style;
    }

    public SpannableString getTextImage(String name, Resources resources,int num) {
        if (!TextUtils.isEmpty(name)) {
            Drawable drawable = resources.getDrawable(R.drawable.right_arrow);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //要让图片替代指定的文字就要用ImageSpan  
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            if (name.length() > num) {
                name = name.substring(0,num) + "...[-icon-]";
            } else {
                name = name + "[-icon-]";
            }
            //需要处理的文本，[smile]是需要被替代的文本  
            SpannableString spannable = new SpannableString(name);
            //开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）  
            //最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12  
            spannable.setSpan(span, name.indexOf("[-icon-]"), name.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannable;
        }else{
            return null;
        }
       
    }*/

    /**
     * 是不是emoji表情
     * 
     * @param codePoint
     * @return
     */
    public boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

}
