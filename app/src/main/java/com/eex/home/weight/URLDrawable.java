package com.eex.home.weight;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

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
 * @ClassName: URLDrawable
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class URLDrawable extends BitmapDrawable {
    private Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
