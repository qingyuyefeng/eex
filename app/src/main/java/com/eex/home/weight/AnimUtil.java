package com.eex.home.weight;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

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
 * @ClassName: AnimUtil
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 17:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 17:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 动画工具类
 */
public class AnimUtil {

    private ValueAnimator valueAnimator;
    private UpdateListener updateListener;
    private EndListener endListener;
    private long duration;
    private float start;
    private float end;
    private Interpolator interpolator = new LinearInterpolator();

    public AnimUtil() {
        // 默认动画时常1s
        duration = 1000;
        start = 0.0f;
        end = 1.0f;
        // 匀速的插值器
        interpolator = new LinearInterpolator();
    }


    public void setDuration(int timeLength) {
        duration = timeLength;
    }

    public void setValueAnimator(float start, float end, long duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void startAnimator() {
        if (valueAnimator != null) {
            valueAnimator = null;
        }
        valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (updateListener == null) {
                    return;
                }

                float cur = (float) valueAnimator.getAnimatedValue();
                updateListener.progress(cur);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (endListener == null) {
                    return;
                }
                endListener.endUpdate(animator);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        valueAnimator.start();
    }

    public void addUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void addEndListner(EndListener endListener) {
        this.endListener = endListener;
    }

    public interface EndListener {
        void endUpdate(Animator animator);
    }

    public interface UpdateListener {
        void progress(float progress);
    }
}
