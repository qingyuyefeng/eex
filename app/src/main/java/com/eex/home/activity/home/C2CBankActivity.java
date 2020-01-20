package com.eex.home.activity.home;

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
 * @ClassName: C2CBankActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 17:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 17:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 银行转账
 */
public class C2CBankActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.edtName)
    EditText edtName;
    /**
     *
     */
    @BindView(R.id.edtBank)
    EditText edtBank;
    /**
     *
     */
    @BindView(R.id.edtBankAddress)
    EditText edtBankAddress;
    /**
     *
     */
    @BindView(R.id.edtBankBranch)
    EditText edtBankBranch;
    /**
     *
     */
    @BindView(R.id.edtBnakNuber)
    EditText edtBnakNuber;
    /**
     *
     */
    @BindView(R.id.codeType)
    TextView codeType;
    /**
     *
     */
    @BindView(R.id.edtCode)
    EditText edtCode;
    /**
     *
     */
    @BindView(R.id.btnCode)
    Button btnCode;
    /**
     *
     */
    @BindView(R.id.BtnPut)
    Button BtnPut;

    /**
     *
     */
    private String userName;
    private String accountNo;
    private String childBankName;
    private String bankAddress;
    private String bankName;
    private String id = "";

    /**
     * 验证码时间
     */
    private TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2_cbank;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("银行转账");

        if (getIntent().getStringExtra("bankName") != null && !getIntent().getStringExtra("bankName").equals("")) {
            accountNo = getIntent().getStringExtra("accountNo");
            userName = getIntent().getStringExtra("userName");
            bankName = getIntent().getStringExtra("bankName");
            childBankName = getIntent().getStringExtra("childBankName");
            bankAddress = getIntent().getStringExtra("bankAddress");
            id = getIntent().getStringExtra("id");
        }


        if (accountNo != null && !accountNo.equals("")) {
            edtName.setText(userName);
            edtBank.setText(bankName);
            edtBankAddress.setText(bankAddress);
            edtBankBranch.setText(childBankName);
            edtBnakNuber.setText(accountNo);
        }

        if (kv.decodeString("phone") == null || kv.decodeString("phone").equals("")) {
            codeType.setText("邮箱验证码:");
        } else {
            codeType.setText("短信验证码:");
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

    @OnClick({R.id.comtitlebar, R.id.edtName, R.id.edtBank, R.id.edtBankAddress, R.id.edtBankBranch, R.id.edtBnakNuber, R.id.edtCode, R.id.btnCode, R.id.BtnPut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.edtName:
                break;
            case R.id.edtBank:
                break;
            case R.id.edtBankAddress:
                break;
            case R.id.edtBankBranch:
                break;
            case R.id.edtBnakNuber:
                break;
            case R.id.edtCode:
                break;
            //验证码
            case R.id.btnCode:


                if (kv.decodeString("tokenId")!= null) {
                    if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                        //短信验证码
                        GetCode();
                    } else if ( kv.decodeString("email") != null && ! kv.decodeString("email").equals("")) {
                        //邮箱发送登录验证码
                        getEmailCode();
                    }
                } else {
                    Toast.makeText(C2CBankActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.BtnPut:


                if (edtName.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CBankActivity.this, "请填写真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtBank.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CBankActivity.this, "请填写开户银行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtBankAddress.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CBankActivity.this, "请填写开户行所在地", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtBankBranch.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CBankActivity.this, "请填写开户支行", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtBnakNuber.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CBankActivity.this, "请填写银行卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtCode.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CBankActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                //确认提交
                net();

                break;
            default:
                break;
        }
    }


    /**
     * 短信验证码
     */
    @SuppressLint("CheckResult")
    private void GetCode() {
        if (kv.decodeString("phone")== null && kv.decodeString("phone") == "") {
            Toast.makeText(C2CBankActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sendType", "sms_take_money");
        hashMap.put("phone", kv.decodeString("phone"));
        ApiFactory.getInstance()
                .send(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(C2CBankActivity.this, btnCode, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    /**
     * 邮箱发送登录验证码
     */
    @SuppressLint("CheckResult")
    private void getEmailCode() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sendType", "3");
        hashMap.put("email",  kv.decodeString("email"));
        hashMap.put("userName", kv.decodeString("username"));
        ApiFactory.getInstance()
                .email(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(C2CBankActivity.this, btnCode, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }


    /**
     * 确认提交
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        if (!id.equals("")) {
            hashMap.put("id", id);
        }
        hashMap.put("accountType", "1");
        hashMap.put("userName", edtName.getText().toString().trim());
        hashMap.put("code", edtCode.getText().toString().trim());
        hashMap.put("accountNo", edtBnakNuber.getText().toString().trim());
        hashMap.put("bankName", edtBank.getText().toString().trim());
        hashMap.put("childBankName", edtBankBranch.getText().toString().trim());
        hashMap.put("bankAddress", edtBankAddress.getText().toString().trim());

        ApiFactory.getInstance()
                .saveorupdate(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        finish();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }
}
