package com.eex.mine.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eex.EEXApp;
import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SPHelper;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 * @Package: com.overthrow.mine.activity
 * @ClassName: NewSetActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/4 15:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/4 15:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 退出登录
 */
public class NewSetActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.Edition)
    TextView Edition;
    /**
     *
     */
    @BindView(R.id.btn_OutLogin)
    Button btnOutLogin;

    //app的版本号
    private String version = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_set;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("退出登录");

        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Edition.setText("V" + version);
    }

    @Override
    protected void initUiAndListener() {

    }

    @OnClick({R.id.comtitlebar, R.id.Edition, R.id.btn_OutLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.Edition:
                break;
            case R.id.btn_OutLogin:

                kv.removeValueForKey("tokenId");
                kv.removeValueForKey("username");
                kv.removeValueForKey("password");
                kv.removeValueForKey("accountPassWord");
                kv.removeValueForKey("status");
                kv.removeValueForKey("email");
                kv.removeValueForKey("realName");
                kv.removeValueForKey("salt");
                kv.removeValueForKey("userCode");
                kv.removeValueForKey("hasEmail");
                kv.removeValueForKey("hasPhone");
                kv.removeValueForKey("phone");
                kv.removeValueForKey("invateCode");
                kv.removeValueForKey("googleState");
                kv.removeValueForKey("examState");
                kv.removeValueForKey("googleKey");
                kv.removeValueForKey("merchant");
                EEXApp.Companion.finishAllActivity();

                //清除账号密码
                SPHelper.init(getActivity()).put("fisrt", "no");
                SPHelper.init(getActivity()).put("remember", false);
                SPHelper.init(getActivity()).put("username", "");
                SPHelper.init(getActivity()).put("password", "");


                intent = new Intent(NewSetActivity.this, LoginActivity.class);
                //切换账号登录时，换另外一张表
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
