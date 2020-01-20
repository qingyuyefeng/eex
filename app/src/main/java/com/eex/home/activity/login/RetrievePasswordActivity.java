package com.eex.home.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
 * @ClassName: RetrievePasswordActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/6 17:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 17:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 忘记密码
 */
public class RetrievePasswordActivity extends BaseActivity {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 电话号码归属地
     */
    @BindView(R.id.guojia_name)
    TextView guojiaName;
    /**
     * +86
     */
    @BindView(R.id.tx_name_nuber)
    TextView txNameNuber;


    /**
     * 隐藏地区和手机区号
     */
    @BindView(R.id.ll_Phone)
    LinearLayout llPhone;
    /**
     *
     */
    @BindView(R.id.ll_Email)
    LinearLayout llEmail;
    /**
     * 验证方式
     */
    @BindView(R.id.tx_isname)
    TextView txIsname;
    /**
     * 向下
     */
    @BindView(R.id.img_ck)
    ImageView imgCk;
    /**
     * 账号
     */
    @BindView(R.id.login_username_edit)
    EditText loginUsernameEdit;

    /**
     * 验证码
     */
    @BindView(R.id.login_userpaswod_edit)
    EditText loginUserpaswodEdit;
    /**
     * 验证码
     */
    @BindView(R.id.btn_YZM)
    Button btnYZM;
    /**
     * 下一步
     */
    @BindView(R.id.login_login_button)
    Button loginLoginButton;

    private String name;
    private String code;

    /**
     * 验证码时间
     */
    private TimeCount time;
    /**
     * 自定义字段 手机号码
     */
    private String Ecall;
    /**
     * 自定义字段 短信验证码
     */
    private String verifyCode;
    /**
     * 1代表手机
     * 2代表邮箱
     */
    private int ckeckType = 3;
    private String type = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrievepassword;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle(getActivity().getResources().getString(R.string.Retrievepword));


        getId();

    }


    protected void getId() {

        loginUsernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginUsernameEdit.getText().toString().trim().length() >= 4 && Utils.isInteger(loginUsernameEdit.getText().toString().trim()) == true) {
                    llPhone.setVisibility(View.VISIBLE);
                } else {
                    llPhone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.guojia_name, R.id.tx_name_nuber, R.id.ll_Phone, R.id.ll_Email, R.id.login_username_edit, R.id.login_userpaswod_edit, R.id.btn_YZM, R.id.login_login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.guojia_name:
                break;
            case R.id.tx_name_nuber:
                break;
            case R.id.ll_Phone:
                intent.setClass(RetrievePasswordActivity.this, PhoneListActivity.class);
                startActivityForResult(intent, 2000);
                break;

            case R.id.ll_Email:
                imgCk.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cq_topres));
                showPopueWindow();
                break;
            case R.id.login_username_edit:
                Ecall = loginUsernameEdit.getText().toString().trim();


                break;

            case R.id.login_userpaswod_edit:
                break;
            case R.id.btn_YZM:
                Ecall = loginUsernameEdit.getText().toString().trim();

                if (TextUtils.isEmpty(Ecall)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_account));
                    return;
                }
                //邮箱
                if (type.equals("emal")) {
                    ckeckType = 3;
                    Ecall = loginUsernameEdit.getText().toString().trim();
                    //判断邮箱号是否为null
                    VerifyCode(Ecall, ckeckType);
                    //电话
                } else if (type.equals("phone")) {
                    ckeckType = 1;
                    Ecall = loginUsernameEdit.getText().toString().trim();
                    //判断手机号码是否为null
                    if (TextUtils.isEmpty(Ecall)) {
                        CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.please_input_phone));
                        return;
                    }
                    VerifyCode(Ecall, ckeckType);
                } else {
                    t(getActivity().getResources().getString(R.string.please_choose_type));
                }

                break;
            case R.id.login_login_button:

                //获取邮箱账号
                Ecall = loginUsernameEdit.getText().toString().trim();
                //验证码
                verifyCode = loginUserpaswodEdit.getText().toString().trim();

                //判断短信验证码是否为null
                if (TextUtils.isEmpty(verifyCode)) {
                    CommonUtil.showSingleToast(getActivity().getResources().getString(R.string.login_yzm));
                    return;
                }

                putnet(Ecall, verifyCode);
                break;
            default:
                break;
        }
    }

    /**
     * 修改密码
     */
    @SuppressLint("CheckResult")
    private void putnet(String ecall, String verifyCode) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (type.equals("emal")) {
            hashMap.put("phoneoremail", loginUsernameEdit.getText().toString().trim() + " " + ecall);
        } else {
            hashMap.put("phoneoremail", txNameNuber.getText().toString().trim() + " " + ecall + "");
        }
        hashMap.put("code", verifyCode + "");
        hashMap.put("checkType", ckeckType + "");
        hashMap.put("restType", "9");

        ApiFactory.getInstance()
                .checkcode(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {

                    if (data.isStauts() == true) {
                        intent.putExtra("code", verifyCode);
                        intent.putExtra("tel", ecall);
                        startActivity(intent.setClass(RetrievePasswordActivity.this, SetNewPasswordActivity.class));
                        finish();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }


    /**
     * PopueWindow()
     */
    private void showPopueWindow() {
        View popView = View.inflate(getActivity(), R.layout.retrieve_dialog, null);

        LinearLayout ll_Email = (LinearLayout) popView.findViewById(R.id.ll_Email);
        LinearLayout ll_phone = (LinearLayout) popView.findViewById(R.id.ll_phone);


        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);


        ll_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCk.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cq_dowres));
                type = "emal";
                txIsname.setText(getActivity().getResources().getString(R.string.email_type));
                popupWindow.dismiss();
            }
        });
        ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCk.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cq_dowres));
                type = "phone";
                txIsname.setText(getActivity().getResources().getString(R.string.phone_type));
                popupWindow.dismiss();

            }
        });


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
     * 获取手机号码和邮箱验证
     *
     * @param ecall
     */
    @SuppressLint("CheckResult")
    private void VerifyCode(String ecall, int ckeckType) {

        if (ckeckType == 3) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("sendType", "1");
            hashMap.put("email", ecall + "");
            ApiFactory.getInstance()
                    .email1(hashMap)
                    .compose(RxSchedulers.<Data>io_main())
                    .subscribe(data -> {
                        if (data.isStauts() == true) {
                            //构造CountDownTimer对象
                            time = new TimeCount(RetrievePasswordActivity.this, btnYZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                            time.start();
                        } else {
                            t(data.getMsg());
                        }
                    }, throwable -> {

                    });

        } else if (ckeckType == 1) {

            HashMap<String, String> hashMap = new HashMap<String, String>();

            hashMap.put("sendType", "sms_find_login_pw");
            hashMap.put("phone", txNameNuber.getText().toString().trim()+" "+Ecall);
            ApiFactory.getInstance()
                    .send1(hashMap)
                    .compose(RxSchedulers.<Data>io_main())
                    .subscribe(data -> {
                        if (data.isStauts() == true) {
                            //构造CountDownTimer对象
                            time = new TimeCount(RetrievePasswordActivity.this, btnYZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                            time.start();
                        } else {
                            t(data.getMsg());
                        }
                    }, throwable -> {

                    });
        }
    }
}
