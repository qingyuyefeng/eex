package com.eex.home.weight;

import android.graphics.Bitmap;

import com.eex.EEXApp;
import com.squareup.picasso.Transformation;

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
 * @ClassName: ImageTransform
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageTransform implements Transformation {

    private String Key = "ImageTransform";

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = EEXApp.Companion.getContext().getResources().getDisplayMetrics().widthPixels;
        if (source.getWidth() == 0) {
            return source;
        }

        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        if (targetHeight != 0 && targetWidth != 0) {
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        } else {
            return source;
        }
    }

    @Override
    public String key() {
        return Key;
    }
}
