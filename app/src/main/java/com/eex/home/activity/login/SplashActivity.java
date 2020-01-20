package com.eex.home.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.UserConstants;
import com.eex.common.util.SpUtils;
import com.eex.mvp.mainpage.MainActivity;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * @Package: com.overthrow.home.activity.login
 * @ClassName: SplashActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/26 17:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/26 17:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.splash_image)
    ImageView splashImage;
    private boolean isFirst;

    private AnimationDrawable animationDrawable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {

        String dir = getFilesDir().getAbsolutePath() + "/mmkv_user";
        String rootDir = MMKV.initialize(dir);
        System.out.println("mmkv root: " + rootDir);

        //如果主目录未初始化抛出异常
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
        }



//        net();



        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, "first_open");
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, AdvertisingPageActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }

        }, 2000);


    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {


        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance().
                guide(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() !=null){

                    }else {
//                     t("1111111111111111111");
                    }
                },throwable -> {

                });



    }


    private void enterHomeActivity() {
        //启动MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //关闭当前活动
        finish();
    }


    @Override
    protected void initUiAndListener() {
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.come_flash);
        splashImage.setBackground(animationDrawable);
        animationDrawable.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(animationDrawable != null){
            animationDrawable.stop();
        }
    }
}
