package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.TimeCount;

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
 * @Package: com.overthrow.mine.activity
 * @ClassName: MeMoneyPwordActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/5 16:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/5 16:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 交易密码
 */
public class MeMoneyPwordActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.MoneyPassword)
    EditText MoneyPassword;
    /**
     *
     */
    @BindView(R.id.money_ck2)
    CheckBox moneyCk2;
    /**
     *
     */
    @BindView(R.id.money_LL_ck2)
    LinearLayout moneyLLCk2;
    /**
     *
     */
    @BindView(R.id.newMoneyPassword)
    EditText newMoneyPassword;
    /**
     *
     */
    @BindView(R.id.money_ck3)
    CheckBox moneyCk3;
    /**
     *
     */
    @BindView(R.id.money_LL_ck3)
    LinearLayout moneyLLCk3;
    /**
     *
     */
    @BindView(R.id.codeNuber)
    EditText codeNuber;
    /**
     *
     */
    @BindView(R.id.btnGetCode)
    Button btnGetCode;
    /**
     *
     */
    @BindView(R.id.next_btn)
    Button nextBtn;


    private TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_money_pword;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("交易密码");
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

    @OnClick({R.id.comtitlebar, R.id.MoneyPassword, R.id.money_ck2, R.id.money_LL_ck2, R.id.newMoneyPassword, R.id.money_ck3, R.id.money_LL_ck3, R.id.codeNuber, R.id.btnGetCode, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.MoneyPassword:
                break;
            case R.id.money_ck2:
                if (moneyCk2.isChecked()) {
                    MoneyPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    MoneyPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.money_LL_ck2:
                break;
            case R.id.newMoneyPassword:
                break;
            case R.id.money_ck3:
                if (moneyCk3.isChecked()) {
                    newMoneyPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    newMoneyPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.money_LL_ck3:
                break;
            case R.id.codeNuber:
                break;
            case R.id.btnGetCode:
                //获取验证码
                getCode();
                break;
            case R.id.next_btn:
                //提交新密码
                PutPassword();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    @SuppressLint("CheckResult")
    private void getCode() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sendType", "sms_take_money");
        hashMap.put("phone", kv.decodeString("phone"));
        hashMap.put("userName", kv.decodeString("username"));
        ApiFactory.getInstance()
                .send(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        //构造CountDownTimer对象
                        time = new TimeCount(MeMoneyPwordActivity.this, btnGetCode, "重新获取", 60000, 1000);
                        time.start();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());

                    }
                }, throwable -> {

                });
    }

    /**
     * 提交新密码
     */
    @SuppressLint("CheckResult")
    private void PutPassword() {

        if (MoneyPassword.getText().toString().trim().equals("")) {
            Toast.makeText(MeMoneyPwordActivity.this, "请输入交易密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newMoneyPassword.getText().toString().trim().equals("")) {
            Toast.makeText(MeMoneyPwordActivity.this, "请再次输入交易密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newMoneyPassword.getText().toString().trim().equals(MoneyPassword.getText().toString().trim())) {
            Toast.makeText(MeMoneyPwordActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newMoneyPassword.getText().toString().trim().length() < 6) {
            Toast.makeText(MeMoneyPwordActivity.this, "交易密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newMoneyPassword.getText().toString().trim().length() > 32) {
            Toast.makeText(MeMoneyPwordActivity.this, "交易密码不能大于32位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!PwdCheckUtil.isLetterDigit(MoneyPassword.getText().toString().trim())) {
            Toast.makeText(MeMoneyPwordActivity.this, "请输入包含数字和字母且大于6小于32位的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (codeNuber.getText().toString().trim().equals("")) {
            Toast.makeText(MeMoneyPwordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("newAccountPwd", Utils.md5(newMoneyPassword.getText().toString().trim() + "hello, moto"));
        hashMap.put("code", codeNuber.getText().toString().trim());

        ApiFactory.getInstance()
                .findaccount(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        t(data.getMsg());
                        finish();
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }
}
