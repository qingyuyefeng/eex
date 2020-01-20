package com.eex.home.weight;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

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
 * @ClassName: HtmlUtils
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HtmlUtils {

    public static Spanned getHtml(Context context, TextView textView, String string) {
//        textView.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
        //设置超链接可以打开网页//click must
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return Html.fromHtml(string, new URLImageGetter(textView, context), new URLTagHandler(context));
    }
}
