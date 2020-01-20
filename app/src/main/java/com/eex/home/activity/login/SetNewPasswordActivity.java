package com.eex.home.activity.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.CommonUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.home.weight.PwdCheckUtil;
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
 * @Package: com.overthrow.home.activity.login
 * @ClassName: SetNewPasswordActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/10 12:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/10 12:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 重置密码
 */
public class SetNewPasswordActivity extends BaseActivity {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 输入新密码
     */
    @BindView(R.id.login_username_edit)
    EditText loginUsernameEdit;
    /**
     * 查看密码
     */
    @BindView(R.id.login_ckbx)
    CheckBox loginCkbx;
    /**
     *
     */
    @BindView(R.id.ll_ckb)
    LinearLayout llCkb;
    /**
     *
     */
    @BindView(R.id.username_ll)
    LinearLayout usernameLl;
    /**
     * 再次输入密码
     */
    @BindView(R.id.login_userpaswod_edit)
    EditText loginUserpaswodEdit;
    /**
     * 查看密码
     */
    @BindView(R.id.login_ckbx2)
    CheckBox loginCkbx2;
    /**
     *
     */
    @BindView(R.id.ll_ckb2)
    LinearLayout llCkb2;
    /**
     *
     */
    @BindView(R.id.username_llzc)
    LinearLayout usernameLlzc;
    /**
     * 下一步
     */
    @BindView(R.id.login_login_button)
    Button loginLoginButton;

    private String password;
    private String surePassword;

    private String tel;
    private String code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_new_password;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.new_password));

        if (getIntent().getStringExtra("tel") != null) {
            tel = getIntent().getStringExtra("tel");
        }
        if (getIntent().getStringExtra("code") != null) {
            code = getIntent().getStringExtra("code");
        }
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

    @OnClick({R.id.comtitlebar, R.id.login_username_edit, R.id.login_ckbx, R.id.ll_ckb, R.id.username_ll, R.id.login_userpaswod_edit, R.id.login_ckbx2, R.id.ll_ckb2, R.id.username_llzc, R.id.login_login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.login_username_edit:
                password = loginUsernameEdit.getText().toString().trim();

                break;
            case R.id.login_ckbx:
                if (loginCkbx.isChecked()) {
                    loginUsernameEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    loginUsernameEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.ll_ckb:
                break;
            case R.id.username_ll:
                break;
            case R.id.login_userpaswod_edit:
                surePassword = loginUserpaswodEdit.getText().toString().trim();
                break;
            case R.id.login_ckbx2:
                if (loginCkbx2.isChecked()) {
                    loginUserpaswodEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    loginUserpaswodEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.ll_ckb2:
                break;
            case R.id.username_llzc:
                break;
            case R.id.login_login_button:
                password = loginUsernameEdit.getText().toString().trim();
                surePassword = loginUserpaswodEdit.getText().toString().trim();

                //判断密码是否为null
                if (TextUtils.isEmpty(password)) {
                    t(getActivity().getResources().getString(R.string.please_input_need_password));
                    return;
                }

                if (password.length() < 6 || password.length() > 30) {
                    t(getActivity().getResources().getString(R.string.Please_enter2));
                    return;
                }

                if (!PwdCheckUtil.isLetterDigit(surePassword)) {
                    t(getActivity().getResources().getString(R.string.Please_enter1));
                    return;
                }

                if (surePassword.length() < 6 || surePassword.length() > 30) {
                    t(getActivity().getResources().getString(R.string.Please_enter2));
                    return;
                }


                //判断输入密码和输入的确认密码一样
                if (password.equals(surePassword)) {
                    registersure(password);

                } else {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.two_passwords_not_same));

                }

                break;
            default:
                break;
        }
    }

    /**
     * 重置密码
     *
     * @param password
     */
    @SuppressLint("CheckResult")
    private void registersure(String password) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("newPwd", Utils.md5( password + "hello, moto"));

        boolean status = tel.contains("@");
        if (status) {
            hashMap.put("checkType", "3");
            hashMap.put("phoneoremail", tel);
        } else {
            hashMap.put("checkType", "1");
            hashMap.put("phoneoremail", "+86 " + tel);
        }
        hashMap.put("code", code);
        ApiFactory.getInstance()
                .resetpwd(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.getCode() == 200) {

                        intent.putExtra("type", "1");
                        intent.setClass(SetNewPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        t(getActivity().getResources().getString(R.string.registerNo));
                    }

                }, throwable -> {

                });

    }
}
