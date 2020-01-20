package com.eex.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.eex.R;

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
 * @ProjectName: MiningMachine
 * @Package: xinweilai.com.miningmachine.ectention.widgt
 * @ClassName: EmptyView
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/3/29 23:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/29 23:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EmptyView extends FrameLayout {
    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context)
                .inflate(R.layout.empty_view, this);
    }
}
