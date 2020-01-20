package com.eex.home.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.CommonUtil;
import com.eex.common.util.TimeCount;
import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.Graphics;
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
 * @ClassName: PhoneRegisterActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/6 11:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 11:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 手机注册
 */
public class PhoneRegisterActivity extends BaseActivity {

    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 国家
     */
    @BindView(R.id.guojia_name)
    TextView guojiaName;
    /**
     * +86
     */
    @BindView(R.id.tx_name_nuber)
    TextView txNameNuber;
    /**
     * 管理号码归属地
     */
    @BindView(R.id.LL_PHONE)
    LinearLayout LLPHONE;
    /**
     * 管理号码归属地
     */
    @BindView(R.id.ll_Phone)
    LinearLayout llPhone;
    /**
     * 输入电话号码
     */
    @BindView(R.id.login_username_edit)
    EditText loginUsernameEdit;
    /**
     * 管理输入电话号码
     */
    @BindView(R.id.username_ll)
    LinearLayout usernameLl;
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
     * 管理再次输入密码
     */
    @BindView(R.id.username_llzc)
    LinearLayout usernameLlzc;
    /**
     *
     */
    @BindView(R.id.graphics_linear)
    LinearLayout graphicsinear;

    /**
     *
     */
    @BindView(R.id.graphics_YZM_edit)
    EditText graphicsYZMEdit;

    /**
     *
     */
    @BindView(R.id.graphics_image)
    ImageView graphicsImage;
    /**
     * 输入验证码
     */
    @BindView(R.id.login_userYZM_edit)
    EditText loginUserYZMEdit;
    /**
     * 验证码
     */
    @BindView(R.id.btn_YZM)
    Button btnYZM;
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
     * 已有账号，返回登录
     */
    @BindView(R.id.NOpaswd)
    TextView NOpaswd;
    /**
     * 邮箱注册
     */
    @BindView(R.id.putUser)
    TextView putUser;

    /**
     *
     */
    private String name;
    private String code;
    private TimeCount time;


    /**
     * 自定义字段 手机号码
     */
    private String call;
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
     * 图形验证码
     */
    private String verkey;
    /**
     * 自定义字段 推荐码
     */
    private String referralCode;

    private Graphics graphics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_register;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.register));
        graphics();
    }

    @SuppressLint("CheckResult")
    private void graphics() {

        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .captcha(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    graphics = data.getData();
                    if (data.getData() != null) {
                        //将Base64编码字符串解码成Bitmap
                        byte[] decodedString = Base64.decode(data.getData().getImage().split(",")[1], Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        //设置ImageView图片
                        graphicsImage.setImageBitmap(decodedByte);

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }

    @Override
    protected void initUiAndListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            name = data.getStringExtra("name");
            code = data.getStringExtra("code");
            guojiaName.setText(name);
            txNameNuber.setText(code);
        }
    }

    @OnClick({R.id.comtitlebar, R.id.guojia_name, R.id.tx_name_nuber, R.id.LL_PHONE, R.id.ll_Phone, R.id.login_username_edit, R.id.graphics_linear, R.id.username_ll, R.id.login_userpaswod_edit, R.id.new_login_userpaswod_edit, R.id.username_llzc, R.id.login_userYZM_edit, R.id.btn_YZM, R.id.login_userTJ_edit, R.id.ck_register, R.id.sever_textView, R.id.login_login_paword, R.id.NOpaswd, R.id.putUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.guojia_name:
                break;
            case R.id.tx_name_nuber:
                break;
            case R.id.LL_PHONE:
                break;
            case R.id.ll_Phone:
                intent.setClass(PhoneRegisterActivity.this, PhoneListActivity.class);
                startActivityForResult(intent, 2000);
                break;
            //获取注册手机号码
            case R.id.login_username_edit:

                call = loginUsernameEdit.getText().toString().trim();

                loginUsernameEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (loginUsernameEdit.getText().toString().trim().length() >= 4 && Utils.isInteger(loginUsernameEdit.getText().toString().trim()) == true) {
                            graphicsinear.setVisibility(View.VISIBLE);
                            llPhone.setVisibility(View.VISIBLE);
                        } else {
                            llPhone.setVisibility(View.GONE);
                            graphicsinear.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                break;
            case R.id.username_ll:
                break;
            //密码
            case R.id.login_userpaswod_edit:
                password = loginUserpaswodEdit.getText().toString().trim();

                break;
            //确认密码
            case R.id.new_login_userpaswod_edit:
                surePassword = newLoginUserpaswodEdit.getText().toString().trim();
                break;
            case R.id.username_llzc:

                break;
            //获取图形验证码
            case R.id.graphics_linear:

                graphics();
                break;

            //图形验证码
            case R.id.graphics_YZM_edit:
                verkey = graphicsYZMEdit.getText().toString().trim();

                break;
            //验证码
            case R.id.login_userYZM_edit:
                verifyCode = loginUserYZMEdit.getText().toString().trim();
                break;
            //获取验证码
            case R.id.btn_YZM:

                call = loginUsernameEdit.getText().toString().trim();
                verkey = graphicsYZMEdit.getText().toString().trim();
                //判断手机号码是否为null
                if (TextUtils.isEmpty(call)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_phone));
                    return;
                }
                if (graphicsinear.getVisibility() == View.VISIBLE && TextUtils.isEmpty(verkey)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_image_code));
                    return;
                }


                VerifyCode(call, verkey);

                break;
            //推荐码
            case R.id.login_userTJ_edit:
                referralCode = loginUserTJEdit.getText().toString().trim();
                break;
            case R.id.ck_register:
                break;
            //服务协议
            case R.id.sever_textView:
                intent.setClass(PhoneRegisterActivity.this, SeverActivity.class);
                startActivity(intent);
                break;

            //注册
            case R.id.login_login_paword:

                //获取注册手机号码
                call = loginUsernameEdit.getText().toString().trim();
                //密码
                password = loginUserpaswodEdit.getText().toString().trim();
                //确认密码
                surePassword = newLoginUserpaswodEdit.getText().toString().trim();
                //验证码
                verifyCode = loginUserYZMEdit.getText().toString().trim();
                //推荐码
                referralCode = loginUserTJEdit.getText().toString().trim();


                //判断手机号码是否为null
                if (TextUtils.isEmpty(call)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_phone));
                    return;
                }
                //判断密码是否为null
                if (TextUtils.isEmpty(password) || password.length() < 6) {
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
                if (!ckRegister.isChecked()) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_check_agreement));
                    return;
                }

                //判断输入密码和输入的确认密码一样
                if (password.equals(surePassword)) {
                    registersure(call, password, verifyCode, referralCode);
                } else {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.two_passwords_not_same));

                }

                break;

            //已有账号，返回登录
            case R.id.NOpaswd:
                intent.setClass(PhoneRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;
            //邮箱注册
            case R.id.putUser:
                intent.setClass(PhoneRegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }


    /**
     * 获取短信验证码
     *
     * @param call
     */
    @SuppressLint("CheckResult")
    private void VerifyCode(String call, String verkey) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("sendType", "sms_regist");
        hashMap.put("phone", txNameNuber.getText().toString().trim() + " " + call);
        hashMap.put("verCode", verkey);
        hashMap.put("verKey", graphics.getKey() + "");
        ApiFactory.getInstance()
                .send1(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(PhoneRegisterActivity.this, btnYZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    /**
     * 注册
     *
     * @param call
     * @param password
     * @param verifyCode
     * @param referralCode
     */
    @SuppressLint("CheckResult")
    private void registersure(String call, String password, String verifyCode, String referralCode) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        if (referralCode.equals("")) {

        } else {
            // 推荐码
            hashMap.put("invateCode", referralCode + "");
        }
        // 电话
        hashMap.put("username", txNameNuber.getText().toString().trim() + " " + call + "");
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
                        intent.setClass(PhoneRegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        t(getActivity().getResources().getString(R.string.registerNo));
                    }

                }, throwable -> {

                });
    }

}
