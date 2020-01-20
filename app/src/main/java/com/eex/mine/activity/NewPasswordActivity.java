package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.weight.Utils;

import java.util.HashMap;

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
 * @ClassName: NewPasswordActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 15:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 15:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewPasswordActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.Edit_phone)
    EditText EditPhone;
    /**
     *
     */
    @BindView(R.id.btn_new_psword)
    Button btnNewPsword;
    /**
     *
     */
    @BindView(R.id.edit_YZM)
    EditText editYZM;
    /**
     *
     */
    @BindView(R.id.OriginalPassword)
    EditText OriginalPassword;
    /**
     *
     */
    @BindView(R.id.newPassword)
    EditText newPassword;
    /**
     *
     */
    @BindView(R.id.NEWnewPassword)
    EditText NEWnewPassword;
    /**
     *
     */
    @BindView(R.id.next_btn)
    Button nextBtn;
    /**
     *
     */
    @BindView(R.id.textView9)
    TextView textView9;


    private TimeCount time;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_new_password;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.xiugai));

    }

    @Override
    protected void initUiAndListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.Edit_phone, R.id.btn_new_psword, R.id.edit_YZM, R.id.OriginalPassword, R.id.newPassword, R.id.NEWnewPassword, R.id.next_btn, R.id.textView9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.Edit_phone:
                break;
            case R.id.btn_new_psword:
                getYZM();
                break;
            case R.id.edit_YZM:
                break;
            case R.id.OriginalPassword:
                break;
            case R.id.newPassword:
                break;
            case R.id.NEWnewPassword:
                break;
            case R.id.next_btn:
                getData();
                break;
            case R.id.textView9:
                break;
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void getData() {
        if (OriginalPassword.getText().toString().trim().equals("")) {
            Toast.makeText(NewPasswordActivity.this, R.string.yuans, Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.getText().toString().trim().equals("")) {
            Toast.makeText(NewPasswordActivity.this, R.string.xianmima, Toast.LENGTH_SHORT).show();
            return;
        }
        if (NEWnewPassword.getText().toString().trim().equals("")) {
            Toast.makeText(NewPasswordActivity.this, R.string.zaici, Toast.LENGTH_SHORT).show();
            return;
        }
        if (NEWnewPassword.getText().toString().trim().equals(newPassword.getText().toString().trim())) {
        } else {
            Toast.makeText(NewPasswordActivity.this, R.string.buyiang, Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("password", Utils.md5(OriginalPassword.getText().toString().trim() + "hello, moto"));
        requestParam.put("newPwd", Utils.md5(newPassword.getText().toString().trim() + "hello, moto"));

        ApiFactory.getInstance()
                .updatepwd(kv.decodeString("tokenId"),requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == true) {
                        intent.putExtra("type", "1");
                        intent.setClass(NewPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());

                    }
                }, throwable -> {

                });

    }

    @SuppressLint("CheckResult")
    private void getYZM() {
        HashMap<String, String> requestParam = new HashMap<>();
        if (EditPhone.getText().toString().trim().equals("")) {
            Toast.makeText(NewPasswordActivity.this, R.string.please_input_phone, Toast.LENGTH_SHORT);
            return;
        }

        ApiFactory.getInstance()
                .send(kv.decodeString("tokenId"),requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        //构造CountDownTimer对象
                        time = new TimeCount(NewPasswordActivity.this, btnNewPsword, "重新获取", 60000, 1000);
                        time.start();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });
    }
}
