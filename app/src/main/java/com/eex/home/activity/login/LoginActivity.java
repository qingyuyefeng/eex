package com.eex.home.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.base.UserConstants;
import com.eex.common.util.SPHelper;
import com.eex.common.util.SharedPreferencesUtils;
import com.eex.common.util.TimeCount;
import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.LoginUser;
import com.eex.home.bean.User;
import com.eex.home.weight.Utils;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
 * @ClassName: LoginActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/4/29 16:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/29 16:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LoginActivity extends BaseActivity {

    // 定义保存的文件的名称
    private static final String languageName = "sharedfile";
    private static final String TAG = "LoginActivity";
    /**
     * 头部标题
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;

    /**
     * 查询手机号区号
     */
    @BindView(R.id.ll_Phone)
    LinearLayout llPhone;
    /**
     * 查看号码归属地
     */
    @BindView(R.id.logiin_chaina)
    TextView logiinChaina;
    /**
     * +86
     */
    @BindView(R.id.login_call_numbe)
    TextView loginCallNumbe;

    /**
     * 输入账号
     */
    @BindView(R.id.login_username)
    EditText loginUsername;
    /**
     * 输入面膜
     */
    @BindView(R.id.login_password)
    EditText loginPassword;
    /**
     * 查看输入的密码
     */
    @BindView(R.id.login_password_chexkBox)
    CheckBox loginPasswordChexkBox;

    /**
     * 登陆
     */
    @BindView(R.id.login_button)
    Button loginButton;
    /**
     * 忘记密码
     */
    @BindView(R.id.login_forget)
    TextView loginForget;
    /**
     * 注册账号
     */
    @BindView(R.id.login_register)
    TextView loginRegister;


    private Intent intent = new Intent();

    private long mExitTime;
    private String isType;


    /**
     * 账户id
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String userPhone;
    /**
     * 短信验证码
     */
    private String passcode;

    /**
     * 验证码剩余时间
     */
    private TimeCount time;

    private JsonObject jsonObject;

    private int READ_PHONE_STATE = 1031;

    private TextView textGoole;
    private TextView txvew_phone;
    private TextView textview_Email;
    private EditText edt_YZM;
    private Button btn_YZM;
    private Button button1;
    private String phone_YZM;

    public String flage;

    /**
     * 用于保存账号密码
     */

    private Boolean isChed = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle("登录");
        init();

        flage = getIntent().getStringExtra("flage");


        comtitlebar.setRightText("CN", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //设置语言
                luanage("cn");
                languages();
            }
        });
    }

    /**
     * 设置语言
     *
     * @param en
     */
    private void luanage(String en) {

        String language = SharedPreferencesUtils.getLungData(languageName, "");
        if (language.equals("")) {
            switchLanguage(en);
        }


    }

    /**
     * 语言设置
     */
    private void languages() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getActivity().getResources().getString(R.string.login_Please));
        final String items[] = {getActivity().getResources().getString(R.string.lan_chinese), getActivity().getResources().getString(R.string.lan_en)};
//                final String items[] = {getActivity().getResources().getString(R.string.lan_chinese), getActivity().getResources().getString(R.string.lan_en), getActivity().getResources().getString(R.string.lan_ja), getActivity().getResources().getString(R.string.lan_ko)};
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String item = items[which];
                //中文
                if (item.equals(getActivity().getResources().getString(R.string.lan_chinese))) {
                    switchLanguage("cn");
                    //英语
                } else if (item.equals(getActivity().getResources().getString(R.string.lan_en))) {
                    switchLanguage("en");
                    //日语
                } else if (item.equals(getActivity().getResources().getString(R.string.lan_ja))) {
                    switchLanguage("ja");
                    //韩语
                } else if (item.equals(getActivity().getResources().getString(R.string.lan_ko))) {
                    switchLanguage("ko");


                }
                dialog.dismiss();
            }
        });

        builder.show();

    }


    /**
     * 切换语言
     *
     * @param language
     */
    private void switchLanguage(String language) {

        Locale myLocale = new Locale(language);
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = myLocale;
        //中文
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
            //英语
        } else if (language.equals("cn")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
            //日语
        } else if (language.equals("ja")) {
            config.locale = Locale.JAPANESE;
            //韩语
        } else if (language.equals("ko")) {
            config.locale = Locale.KOREAN;

        }
        resources.updateConfiguration(config, dm);
        //保存设置语言的类型
        SharedPreferencesUtils.putShareData(languageName, language);
        //更新语言后，destroy当前页面，重新绘制
        finish();
        Intent it = new Intent(LoginActivity.this, LoginActivity.class);
        //清空任务栈确保当前打开activit为前台任务栈栈顶
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);

    }

    /**
     * 存储账号密码
     */
    private void init() {


        username = loginUsername.getText().toString().trim();

        loginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginUsername.getText().toString().trim().length() >= 4 && Utils.isInteger(loginUsername.getText().toString().trim()) == true) {
                    llPhone.setVisibility(View.VISIBLE);
                } else {
                    llPhone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (SPHelper.init(LoginActivity.this).getBoolean("remember")) {
            SPHelper.init(LoginActivity.this).put("remember", true);
            String username = SPHelper.init(LoginActivity.this).getString("username");
            String password = SPHelper.init(LoginActivity.this).getString("password");
            loginUsername.setText(username);
            loginPassword.setText(password);
        }


    }


    @Override
    protected void initUiAndListener() {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int checkREAD_PHONE_STATE = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
//        int size = checkREAD_PHONE_STATE + checkSelfPermission;
//        String[] permissions = new String[Math.abs(size)];
        List<String> list = new ArrayList<String>();
        int size = 0;
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            size++;
        }
        if (checkREAD_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.READ_PHONE_STATE);
            size++;
        }
        String[] permissions = new String[list.size()];
        for (int i = 0; i < size; i++) {
            permissions[i] = list.get(i);
        }
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            size++;
        }
        if (checkREAD_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.READ_PHONE_STATE);
            size++;
        }
        if (permissions != null && permissions.length > 0) {
            ActivityCompat.requestPermissions(this, permissions, READ_PHONE_STATE);
        }

    }


    /**
     * 获取手机参数
     *
     * @return
     */
    private String getPhoneDetle() {
        //手机厂商
        String PhoneName = Utils.getDeviceBrand();
        //手机型号
        String PhoneMondle = Utils.getSystemModel();
        //系统版本号
        String Version = Utils.getSystemVersion();
        if (Version == null) {
            Version = "null";
        }
        if (PhoneMondle == null) {
            PhoneMondle = "null";
        }
        if (PhoneName == null) {
            PhoneName = "null";
        }
        return PhoneName + " " + PhoneMondle + " Android" + Version;
    }


    @OnClick({R.id.logiin_chaina, R.id.login_call_numbe, R.id.ll_Phone, R.id.login_username, R.id.login_password, R.id.login_password_chexkBox, R.id.login_button, R.id.login_forget, R.id.login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logiin_chaina:
                break;
            case R.id.login_call_numbe:
                break;
            case R.id.ll_Phone:
                intent.setClass(LoginActivity.this, PhoneListActivity.class);
                startActivityForResult(intent, 2000);
                break;


            case R.id.login_username:
                username = loginUsername.getText().toString().trim();

                loginUsername.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (loginUsername.getText().toString().trim().length() >= 4 && Utils.isInteger(loginUsername.getText().toString().trim()) == true) {
                            llPhone.setVisibility(View.VISIBLE);
                        } else {
                            llPhone.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                break;
            case R.id.login_password:
                password = loginPassword.getText().toString().trim();

                break;

            //查看密码
            case R.id.login_password_chexkBox:

                if (loginPasswordChexkBox.isChecked() == true) {
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;

            case R.id.login_button:
                username = loginUsername.getText().toString().trim();
                //判断手机号码是否为null
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, R.string.please_input_account, Toast.LENGTH_SHORT).show();
                    return;
                }
                password = loginPassword.getText().toString().trim();
                //判断密码是否为null
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.please_input_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                login();
                break;
            //忘记密码
            case R.id.login_forget:

                intent.setClass(LoginActivity.this, RetrievePasswordActivity.class);
                startActivityForResult(intent, 1000);

                break;
            //账号注册
            case R.id.login_register:
                intent.setClass(LoginActivity.this, PhoneRegisterActivity.class);
                startActivityForResult(intent, 1000);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String name = data.getStringExtra("name");
            String code = data.getStringExtra("code");
            logiinChaina.setText(name);
            loginCallNumbe.setText(code);
        }
    }

    /**
     * 登录
     */

    @SuppressLint("CheckResult")
    private void login() {

        HashMap<String, String> hasmap = new HashMap<String, String>();
        if (username.contains("@")) {
            //邮箱
            hasmap.put("username", username);
        } else {
            //电话
            hasmap.put("username", loginCallNumbe.getText().toString().trim() + " " + username);
        }
        //密码
        hasmap.put("password", Utils.md5(password + "hello, moto"));
        //登陆的类型
        hasmap.put("loginType", "2");

        ApiFactory.getInstance()
                .login(hasmap)
                .compose(RxSchedulers.<Data<User<LoginUser>>>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {

                        //登陆之后 保存账号密码 和其他的值
                        SPHelper.init(LoginActivity.this).put("fisrt", "no");
                        if (isChed == true) {
                            SPHelper.init(LoginActivity.this).put("remember", true);
                            SPHelper.init(LoginActivity.this).put("username", username);
                            SPHelper.init(LoginActivity.this).put("password", password);
                        } else {
                            SPHelper.init(LoginActivity.this).put("remember", false);
                            SPHelper.init(LoginActivity.this).put("username", "");
                            SPHelper.init(LoginActivity.this).put("password", "");
                        }
                        showPopueWindow(data.getData());

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {
                    Toast.makeText(LoginActivity.this, R.string.wangl, Toast.LENGTH_SHORT).show();
                });
    }


    private void showPopueWindow(User<LoginUser> loginUserUser) {

        View popView = View.inflate(LoginActivity.this, R.layout.popupwindow_time, null);

        textGoole = (TextView) popView.findViewById(R.id.txvew_goole);
        txvew_phone = (TextView) popView.findViewById(R.id.txvew_phone);
        textview_Email = (TextView) popView.findViewById(R.id.textview_Email);
        edt_YZM = (EditText) popView.findViewById(R.id.edt_YZM);
        btn_YZM = (Button) popView.findViewById(R.id.btn_YZM);
        button1 = (Button) popView.findViewById(R.id.button);

        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);


        if (loginUserUser.getPhone() != null && !loginUserUser.getPhone().equals("")) {
            isType = "phone";
            txvew_phone.setText(getActivity().getResources().getString(R.string.shoujihao));
            btn_YZM.setVisibility(View.VISIBLE);
        } else if (loginUserUser.getGoogleState() == 1) {
            isType = "google";
            txvew_phone.setText(getActivity().getResources().getString(R.string.goole));
            btn_YZM.setVisibility(View.GONE);
        } else {
            isType = "email";
            txvew_phone.setText(getActivity().getResources().getString(R.string.youxianfw1));
            btn_YZM.setVisibility(View.VISIBLE);
        }


        //获取验证码
        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap();

                if (isType.equals("phone")) {
                    hashMap.put("sendType", "sms_take_money");
                    hashMap.put("phone", loginUserUser.getPhone() + "");
                    hashMap.put("userName", loginUserUser.getUsername() + "");
                    ApiFactory.getInstance()
                            .send1(hashMap)
                            .compose(RxSchedulers.<Data>io_main())
                            .subscribe(data -> {
                                if (data.isStauts() == true) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(LoginActivity.this, btn_YZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                                    time.start();
                                } else {
                                    t(data.getMsg());
                                }
                            }, throwable -> {
                                t("错误！");
                            });
                } else if (isType.equals("email")) {
                    hashMap.put("sendType", "3");
                    hashMap.put("email", loginUserUser.getEmail() + "");
                    hashMap.put("userName", loginUserUser.getUsername() + "");


                    ApiFactory.getInstance()
                            .sendcode(loginUserUser.getTokenId(), hashMap)
                            .compose(RxSchedulers.<Data>io_main())
                            .subscribe(data -> {
                                if (data.isStauts() == true) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(LoginActivity.this, btn_YZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                                    time.start();
                                } else {
                                    t(data.getMsg());
                                }
                            }, throwable -> {
                                t("错误！");
                            });
                }


            }
        });

        //登录
        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {


                phone_YZM = edt_YZM.getText().toString().trim();

                HashMap<String, String> hashMap = new HashMap();
                //判断验证码是否为null
                if (TextUtils.isEmpty(phone_YZM)) {
                    t(getActivity().getResources().getString(R.string.login_yzm));
                    return;
                }
                //checkType ==1 手机号码登陆
                //checkType ==2  google邮箱登陆
                //checkType ==2 其他邮箱登陆
                if (isType.equals("phone")) {
                    //登陆类型
                    hashMap.put("checkType", "1");
                    //获取手机号码
                    hashMap.put("phoneoremail", loginUserUser.getPhone());
                } else if (isType.equals("google")) {
                    //登陆类型
                    hashMap.put("checkType", "2");
                    hashMap.put("googleKey", loginUserUser.getGoogleKey());
                } else if (isType.equals("email")) {
                    //登陆类型
                    hashMap.put("checkType", "3");
                    hashMap.put("phoneoremail", loginUserUser.getPhone());
                }

                //验证码
                hashMap.put("code", phone_YZM);
                //密码加密
                hashMap.put("password", Utils.md5(password + "hello, moto"));
                //登陆方式
                hashMap.put("loginType", "2");
                //登陆名称
                hashMap.put("username", loginUserUser.getUsername());
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在登录...");
                ApiFactory.getInstance()
                        .login(hashMap)
                        .compose(RxSchedulers.<Data<User<LoginUser>>>io_main())
                        .subscribe(data -> {

                            dialog.dismiss();
                            if (data.getCode() == 200) {


                            } else {
                                t(data.getMsg());

                            }

                        }, throwable -> {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, R.string.wangl, Toast.LENGTH_SHORT).show();
                        });


            }


        });

        popupWindow.dismiss();

        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 50);
        BackgroudAlpha((float) 0.5);
        popupWindow.setOnDismissListener(new popupwindowdismisslistener());

    }

    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = this.getWindow().getAttributes();
        l.alpha = alpha;
        getWindow().setAttributes(l);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /**
     * 点击其他部分popwindow消失时，屏幕恢复透明度
     */
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            BackgroudAlpha((float) 1);
        }
    }


    /**
     * 获取当前APP版本
     *
     * @return
     */
    public String getVersion() {
        try {
            String pkName = LoginActivity.this.getPackageName();
            String versionName = LoginActivity.this.getPackageManager().getPackageInfo(pkName, 0).versionName;
            int versionCode = LoginActivity.this.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getActivity().getResources().getString(R.string.Versionen_unbekannt);
        }
    }

    /**
     * 获取手机型号系统版本
     *
     * @param id
     */
    @SuppressLint("CheckResult")
    private void Puttranslate(String id) {

        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("appVersion", getVersion());
        hashMap.put("id", id);
        hashMap.put("system", getPhoneDetle());
        ApiFactory.getInstance()
                .addAppVersionDetail(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {

                }, throwable -> {

                });

    }

}