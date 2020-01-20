package com.eex.home.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.CommonUtil;
import com.eex.common.util.TimeCount;
import com.eex.common.view.ComTitleBar;
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
 * @Package: com.overthrow.home.activity
 * @ClassName: RegisterActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/6 16:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 16:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 邮箱注册
 */
public class RegisterActivity extends BaseActivity {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 邮箱账号
     */
    @BindView(R.id.login_username_edit)
    EditText loginUsernameEdit;
    /**
     * 输入密码
     */
    @BindView(R.id.login_userpaswod_edit)
    EditText loginUserpaswodEdit;
    /**
     * 再次输入密码
     */
    @BindView(R.id.new_login_userpaswod_edit)
    EditText newLoginUserpaswodEdit;

    /**
     * 输入验证码
     */
    @BindView(R.id.login_userYZM_edit)
    EditText loginUserYZMEdit;
    /**
     * 获取短信
     */
    @BindView(R.id.btn)
    Button btn;
    /**
     * 推荐码
     */
    @BindView(R.id.login_userTJ_edit)
    EditText loginUserTJEdit;
    /**
     * 同意服务协议
     */
    @BindView(R.id.ck_register)
    CheckBox ckRegister;
    /**
     * 服务协议
     */
    @BindView(R.id.sever_textView)
    TextView severTextView;
    /**
     * 注册
     */
    @BindView(R.id.login_login_paword)
    Button loginLoginPaword;
    /**
     * 已有账号
     */
    @BindView(R.id.NOpaswd)
    TextView NOpaswd;
    /**
     * 手机注册
     */
    @BindView(R.id.putUser)
    TextView putUser;


    /**
     * 验证码时间
     */
    private TimeCount time;
    /**
     * 自定义字段 手机号码
     */
    private String Ecall;
    /**
     * 自定义字段 密码
     */
    private String password;
    /**
     * 自定义字段 确认密码
     */
    private String surePassword;
    /**
     * 自定义字段 短信验证码
     */
    private String verifyCode;
    /**
     * 自定义字段 推荐码
     */
    private String referralCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.register));
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

    @OnClick({R.id.comtitlebar, R.id.login_username_edit, R.id.login_userpaswod_edit, R.id.new_login_userpaswod_edit, R.id.login_userYZM_edit, R.id.btn, R.id.login_userTJ_edit, R.id.ck_register, R.id.sever_textView, R.id.login_login_paword, R.id.NOpaswd, R.id.putUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            //获取输入的邮箱账号
            case R.id.login_username_edit:
                Ecall = loginUsernameEdit.getText().toString().trim();
                break;

            //密码
            case R.id.login_userpaswod_edit:
                password = loginUserpaswodEdit.getText().toString().trim();
                break;
            //确认密码
            case R.id.new_login_userpaswod_edit:
                surePassword = newLoginUserpaswodEdit.getText().toString().trim();
                break;
            //验证码
            case R.id.login_userYZM_edit:
                verifyCode = loginUserYZMEdit.getText().toString().trim();
                break;
            //获取验证码
            case R.id.btn:

                Ecall = loginUsernameEdit.getText().toString().trim();
                //判断邮箱号是否为null
                if (TextUtils.isEmpty(Ecall)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_phone));
                    return;
                }
                VerifyCode(loginUsernameEdit.getText().toString().trim());
                break;
            //推荐码
            case R.id.login_userTJ_edit:
                referralCode = loginUserTJEdit.getText().toString().trim();
                break;
            case R.id.ck_register:
                break;
            //服务协议
            case R.id.sever_textView:
                intent.setClass(RegisterActivity.this, SeverActivity.class);
                startActivity(intent);
                break;
            //注册
            case R.id.login_login_paword:
                //获取邮箱账号
                Ecall = loginUsernameEdit.getText().toString().trim();
                //密码
                password = loginUserpaswodEdit.getText().toString().trim();
                //确认密码
                surePassword = newLoginUserpaswodEdit.getText().toString().trim();
                //验证码
                verifyCode = loginUserYZMEdit.getText().toString().trim();
                //推荐码
                referralCode = loginUserTJEdit.getText().toString().trim();


                //判断邮箱账号是否为null
                if (TextUtils.isEmpty(Ecall)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_phone));
                    return;
                }
                //判断密码是否为null
                if (TextUtils.isEmpty(password)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_need_password));
                    return;
                }
                //判断确认密码是否为null
                if (TextUtils.isEmpty(surePassword)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_confirm_password));
                    return;
                }
                //判断短信验证码是否为null
                if (TextUtils.isEmpty(verifyCode)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.login_yzm));
                    return;
                }


                //是否同意了服务协议
                if (ckRegister.isChecked() == false) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_check_agreement));
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(this, R.string.please_input_need_password, Toast.LENGTH_SHORT).show();
                    return;
                }


                //判断输入密码和输入的确认密码一样
                if (password.equals(surePassword)) {
                    registersure(Ecall, password, verifyCode, referralCode);
                } else {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.two_passwords_not_same));

                }

                break;

            //已有账号，返回登录
            case R.id.NOpaswd:
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;

            //邮箱注册
            case R.id.putUser:
                intent.setClass(RegisterActivity.this, PhoneRegisterActivity.class);
                startActivity(intent);
                finish();
                break;


            default:
                break;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 获取邮箱验证码
     *
     * @param ecall
     */
    @SuppressLint("CheckResult")
    private void VerifyCode(String ecall) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("sendType", "4");
        hashMap.put("email", loginUsernameEdit.getText().toString().trim() + "");
        Log.e("TAG", "VerifyCode: " + loginUsernameEdit.getText().toString().trim());
        ApiFactory.getInstance()
                .email1(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(RegisterActivity.this, btn, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }


    /**
     * 邮箱注册
     *
     * @param ecall
     * @param password
     * @param verifyCode
     * @param referralCode
     */
    @SuppressLint("CheckResult")
    private void registersure(String ecall, String password, String verifyCode, String referralCode) {


        HashMap<String, String> hashMap = new HashMap<String, String>();

        if (referralCode.equals("")) {

        } else {
            // 推荐码
            hashMap.put("invateCode", referralCode + "");
        }
        // 电话
        hashMap.put("username", ecall + "");
        //密码
        hashMap.put("password", Utils.md5(password + "hello, moto"));
        // 验证码

        hashMap.put("code", verifyCode);

        ApiFactory.getInstance()
                .regist(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.getCode() == 200) {

                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        t(getActivity().getResources().getString(R.string.registerNo));

                    }

                }, throwable -> {
                    t(getActivity().getResources().getString(R.string.wangl));
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
