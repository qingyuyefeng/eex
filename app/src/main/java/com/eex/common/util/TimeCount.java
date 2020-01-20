package com.eex.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

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
 * @Package: xinweilai.com.miningmachine.common.util
 * @ClassName: TimeCount
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/4/18 17:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/18 17:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TimeCount extends CountDownTimer {

    private Button button;

    private String text;

    private Context context;

    public TimeCount(Context context, Button button, String text, long millisInFuture, long countDownInterval) {
        //参数依次为总时长,和计时的时间间隔
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.text = text;
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    //计时完毕时触发
    public void onFinish() {
        button.setText(text);
        button.setClickable(true);
        button.setBackground(context.getResources().getDrawable(R.color.trans));
    }

    @SuppressLint("NewApi")
    @Override
    //计时过程显示
    public void onTick(long millisUntilFinished) {
        button.setClickable(false);
        button.setText(context.getResources().getString(R.string.login_Please_wait_for) + millisUntilFinished / 1000 + context.getResources().getString(R.string.login_seconds));
        button.setBackground(context.getResources().getDrawable(R.color.trans));
    }
}
