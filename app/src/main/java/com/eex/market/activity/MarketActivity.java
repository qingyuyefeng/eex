package com.eex.market.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.eex.R;
import com.eex.common.base.BaseActivity;

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
 * @Package: com.overthrow.market.activity
 * @ClassName: MarketActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/20 14:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/20 14:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MarketActivity  extends BaseActivity {


    public static void start(Context context, int flag) {
        Intent intent = new Intent(context, MarketActivity.class);
        intent.putExtra("flag", flag);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

    }

    @Override
    protected void initUiAndListener() {

    }
}
