package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.home.weight.Utils;
import com.eex.mvp.usercenter.PhoneListActivity;
import com.tencent.mmkv.MMKV;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: PhoneNameActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 11:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 11:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 手机认证
 */
public class PhoneNameActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.textview_PhoneTitle)
    TextView textviewPhoneTitle;
    /**
     *
     */
    @BindView(R.id.real_Phone_LL)
    LinearLayout realPhoneLL;
    /**
     *
     */
    @BindView(R.id.guojia_name)
    TextView guojiaName;
    /**
     *
     */
    @BindView(R.id.tx_name_nuber)
    TextView txNameNuber;
    /**
     *
     */
    @BindView(R.id.LL_PHONE)
    LinearLayout LLPHONE;
    /**
     *
     */
    @BindView(R.id.ll_Phone)
    LinearLayout llPhone;
    /**
     * 输入手机号码
     */
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    /**
     *
     */
    @BindView(R.id.edt_YZM)
    EditText edtYZM;
    /**
     *
     */
    @BindView(R.id.btn_YZM)
    Button btnYZM;
    /**
     *
     */
    @BindView(R.id.btn_OK)
    Button btnOK;
    /**
     *
     */
    @BindView(R.id.textView)
    View textView;


    /**
     *
     */
    private String name;
    private String code;

    /**
     * 短信验证码
     */
    private String passcode;
    private String Ecall;


    private TimeCount time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_name;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("手机认证");
        getID();

    }

    private void getID() {

        try {
            if (kv.decodeString("tokenId")!=null){
                if (kv.decodeString("phone").equals("")){
                    btnOK.setText(getResources().getString(R.string.bind_phone));

                }else {
                    btnOK.setText(getResources().getString(R.string.jiebang));
                    edtPhone.setEnabled(false);
                    realPhoneLL.setEnabled(false);
                    edtPhone.setText(kv.decodeString("phone"));
                }
            }
        }catch (Exception e){
            btnOK.setText(getResources().getString(R.string.bind_phone));
            edtPhone.setClickable(true);
        }
    }

    @Override
    protected void initUiAndListener() {


    }

    @OnClick({R.id.comtitlebar, R.id.edt_phone, R.id.real_Phone_LL, R.id.LL_PHONE, R.id.ll_Phone, R.id.btn_YZM, R.id.btn_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.edt_phone:
                edtPhone.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (edtPhone.getText().toString().trim().length() >= 4 && Utils.isInteger(edtPhone.getText().toString().trim()) == true) {
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

            case R.id.real_Phone_LL:
                break;


            case R.id.LL_PHONE:
                break;
            case R.id.ll_Phone:
                intent.setClass(PhoneNameActivity.this, PhoneListActivity.class);
                startActivityForResult(intent, 2000);

                break;

            //获取验证码
            case R.id.btn_YZM:

                //手机号码
                Ecall = edtPhone.getText().toString().trim();

                //判断手机验证码是否为null
                if (TextUtils.isEmpty(Ecall)) {
                    Toast.makeText(PhoneNameActivity.this, R.string.please_input_phone, Toast.LENGTH_SHORT).show();
                } else {
                    if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.bind_phone))) {
                        //绑定手机号获取验证码
                        send("sms_bind_phone");
                    } else if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.jiebang))) {
                        //解绑定手机号获取验证码
                        unbundling("sms_cancel_bind_phone");
                    }

                }

                break;
            case R.id.btn_OK:

                //手机号码
                Ecall = edtPhone.getText().toString().trim();
                //验证码
                passcode = edtYZM.getText().toString().trim();

                if (TextUtils.isEmpty(Ecall)) {
                    Toast.makeText(this, R.string.please_input_account, Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断验证码是否为null
                if (TextUtils.isEmpty(passcode)) {
                    t(getActivity().getResources().getString(R.string.login_yzm));
                    return;
                }


                if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.bind_phone))) {
                    //绑定手机
                    bindphone(Ecall, passcode);
                } else if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.jiebang))) {
                    //解绑手机
                    cenelbindphone(Ecall, passcode);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 绑定手机
     *
     * @param ecall
     * @param passcode
     */
    @SuppressLint("CheckResult")
    private void bindphone(String ecall, String passcode) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobilePhone", txNameNuber.getText().toString().trim() + " " + ecall);
        hashMap.put("username", kv.decodeString("username"));
        hashMap.put("code", passcode + "");

        ApiFactory.getInstance()
                .bindphone(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {

                        //存储User数据
                        MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
                        kv.encode("phone", txNameNuber.getText().toString().trim() + " " + ecall);
                        t(data.getMsg());
                        finish();
                    } else {
                        t(data.getMsg());
                    }
                });

    }

    /**
     * 解绑手机
     */
    @SuppressLint("CheckResult")
    private void cenelbindphone(String ecall, String passcode) {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobilePhone", txNameNuber.getText().toString().trim() + " " + ecall);
        hashMap.put("username", kv.decodeString("username"));
        hashMap.put("code", passcode + "");

        ApiFactory.getInstance()
                .cenelbindphone(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {

                        //存储User数据
                        MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
                        kv.encode("phone", "");
                        t(data.getMsg());
                        finish();
                    } else {
                        t(data.getMsg());
                    }
                });

    }


    /**
     * 绑定手机号获取验证码
     *
     * @param sms_bind_phone
     */
    @SuppressLint("CheckResult")
    private void send(String sms_bind_phone) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", txNameNuber.getText().toString().trim() + " " + edtPhone.getText().toString().trim());
        hashMap.put("sendType", "sms_take_phone");

        ApiFactory.getInstance()
                .send1(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(PhoneNameActivity.this, btnYZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                });

    }

    /**
     * 解绑定手机号获取验证码
     *
     * @param sms_cancel_bind_phone
     */
    @SuppressLint("CheckResult")
    private void unbundling(String sms_cancel_bind_phone) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", edtPhone.getText().toString().trim());
        hashMap.put("sendType", "sms_cancel_bind_phone");

        ApiFactory.getInstance()
                .send1(hashMap)
                .compose(RxSchedulers.<Data>io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(PhoneNameActivity.this, btnYZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                });
    }

    /**
     * 手机号码归属地 通用回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (data != null) {
                name = data.getStringExtra("name");
                code = data.getStringExtra("code");
                guojiaName.setText(name);
                txNameNuber.setText(code);
            }

        }
    }
}
