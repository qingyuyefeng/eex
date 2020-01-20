package com.eex.common.base;

import android.text.TextUtils;
import android.util.Log;

import com.eex.common.util.LogUtils;

import io.reactivex.Observer;

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
 * @Package: xinweilai.com.miningmachine.common.base
 * @ClassName: ComObserver
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/3/22 11:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/22 11:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public abstract class ComObserver<T> implements Observer<Data<T>> {


    private static final String TAG = "ComObserver";


    @Override
    public void onNext(Data<T> data) {
        LogUtils.i("打印data：" + data.isStauts() + "");
        if (!TextUtils.isEmpty(data.getMsg())) {
//            CommonUtil.showSingleToast(data.getMsg());
        }
//        LogUtils.i("打印data：" + data.isStauts() + "");
        if (data.isStauts()) {
            onSuccess(data.getData());
        } else {
            onFaild(data.getCode());
        }
        onFinal();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Log.e(TAG, "onError: "+"打印出来吧"+e.getMessage());
        onFaild(-200);
        onFinal();
    }

    @Override
    public void onComplete() {
        LogUtils.i("打印data：" + "1111111111111111111");
    }

    public abstract void onSuccess(T t);

    public abstract void onFinal();

    void onFaild(int code) {
    }
}

