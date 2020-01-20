package com.eex.home.weight;

import android.content.Context;
import android.widget.EditText;

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
 * @ClassName: PwdCheckUtil
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/10 13:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/10 13:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PwdCheckUtil {

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        //定义一个boolean值，用来表示是否包含字母或数字
        boolean isLetterOrDigit = false;
        for (int i = 0; i < str.length(); i++) {
                //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isLetterOrDigit(str.charAt(i))) {
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }

    /**
     * 规则2：至少包含大小写字母及数字中的两种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLetter = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
                //用char包装类中的判断字母的方法判断每一个字符
            } else if (Character.isLetter(str.charAt(i))) {
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * 规则3：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLowerCase = false;
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
                //用char包装类中的判断字母的方法判断每一个字符
            } else if (Character.isLowerCase(str.charAt(i))) {
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }

    /**
     * 判断EditText输入的数字、中文还是字母方法
     */
    public static void whatIsInput(Context context, EditText edInput) {
        String txt = edInput.getText().toString();

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if (m.matches()) {
        }
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(txt);
        if (m.matches()) {
        }
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(txt);
        if (m.matches()) {
        }
    }

}
