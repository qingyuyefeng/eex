package com.eex.home.weight;

import android.content.Context;

import com.eex.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


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
 * @ClassName: ImageLoad
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageLoad {
    public static void loadPlaceholder(Context context, String url, Target target) {

        Picasso picasso = new Picasso.Builder(context).loggingEnabled(true).build();
        picasso.load(url)
                .placeholder(R.drawable.moren)
                .error(R.drawable.moren)
                .transform(new ImageTransform())
//                .transform(new CompressTransformation())
                .into(target);
    }

}
