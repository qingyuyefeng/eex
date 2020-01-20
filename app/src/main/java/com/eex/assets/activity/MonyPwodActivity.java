package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
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
 * @Package: com.overthrow.assets.activity
 * @ClassName: MonyPwodActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 10:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 10:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 交易密码
 */
public class MonyPwodActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.yuansMoneyPassword)
    EditText yuansMoneyPassword;
    /**
     *
     */
    @BindView(R.id.ck_money_1)
    CheckBox ckMoney1;
    /**
     *
     */
    @BindView(R.id.money_1)
    LinearLayout money1;
    /**
     *
     */
    @BindView(R.id.llyuans)
    LinearLayout llyuans;
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
    @BindView(R.id.next_btn)
    Button nextBtn;


    private TimeCount time;
    private String isType = "phone";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mony_pwod;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("交易密码");

        if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord") == "") {
            llyuans.setVisibility(View.GONE);
        } else {
            llyuans.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.comtitlebar, R.id.yuansMoneyPassword, R.id.ck_money_1, R.id.money_1, R.id.llyuans, R.id.MoneyPassword, R.id.money_ck2, R.id.money_LL_ck2, R.id.newMoneyPassword, R.id.money_ck3, R.id.money_LL_ck3, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.yuansMoneyPassword:
                break;
            case R.id.ck_money_1:
                if (ckMoney1.isChecked()) {
                    yuansMoneyPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    yuansMoneyPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.money_1:
                break;
            case R.id.llyuans:
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
            case R.id.next_btn:

                if (MoneyPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(MonyPwodActivity.this, "密码输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!MoneyPassword.getText().toString().trim().equals(newMoneyPassword.getText().toString().trim())) {
                    Toast.makeText(MonyPwodActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog();

                break;
            default:
                break;
        }
    }

    private void Dialog() {

        final Dialog dialog = new Dialog(MonyPwodActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(MonyPwodActivity.this).inflate(R.layout.dialog_timoney, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final TextView txvew_phone = (TextView) dialogView.findViewById(R.id.txvew_phone);
        final EditText edt_YZM = (EditText) dialogView.findViewById(R.id.edt_YZM);
        final Button btn_YZM = (Button) dialogView.findViewById(R.id.btn_YZM);
        Button button1 = (Button) dialogView.findViewById(R.id.button);
        if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
            isType = "phone";
            txvew_phone.setText("手机");
            btn_YZM.setVisibility(View.VISIBLE);
        } else if (kv.decodeInt("googleState") == 1) {
            isType = "google";
            txvew_phone.setText("谷歌");
            btn_YZM.setVisibility(View.GONE);
        } else {
            isType = "email";
            txvew_phone.setText("邮箱");
            btn_YZM.setVisibility(View.VISIBLE);
        }
        dialog.show();
        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                HashMap<String, String> requestParam = new HashMap<>();
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(MonyPwodActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kv.decodeString("accountPassWord") == null || kv.decodeString("accountPassWord").equals("")) {
                    requestParam.put("accountPwd", Utils.md5(MoneyPassword.getText().toString().trim() + "hello, moto"));
                } else {
                    if (yuansMoneyPassword.getText().toString().trim().equals("")) {
                        Toast.makeText(MonyPwodActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    requestParam.put("accountPwd", Utils.md5(yuansMoneyPassword.getText().toString().trim() + "hello, moto"));
                    requestParam.put("newAccountPwd", Utils.md5(MoneyPassword.getText().toString().trim() + "hello, moto"));
                }
                if (isType.equals("phone")) {
                    requestParam.put("checkType", "1");
                } else if (isType.equals("google")) {
                    requestParam.put("checkType", "2");
                } else {
                    requestParam.put("checkType", "3");
                }
                requestParam.put("code", edt_YZM.getText().toString().trim());

                ApiFactory.getInstance()
                        .updateaccount(kv.decodeString("tokenId"),requestParam)
                        .compose(RxSchedulers.io_main())
                        .subscribe(data -> {

                            if (data.isStauts() == true) {
                                //这个理要更新交易密码

                                finish();
                                Toast.makeText(MonyPwodActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MonyPwodActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {

                        });

            }
        });
        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (isType.equals("phone")) {
                    HashMap<String, String> requestParam = new HashMap<>();
                    requestParam.put("sendType", "sms_take_money");
                    requestParam.put("phone",kv.decodeString("phone"));
                    requestParam.put("userName", kv.decodeString("username"));

                    ApiFactory.getInstance()
                            .send(kv.decodeString("tokenId"),requestParam)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {

                                if (data.isStauts() == true) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(MonyPwodActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                    time.start();
                                    Toast.makeText(MonyPwodActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MonyPwodActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {

                            });

                } else if (isType.equals("email")) {

                    //邮箱发送登录验证码
                    HashMap<String, String> requestParam = new HashMap<>();
                    requestParam.put("sendType", "3");
                    requestParam.put("email",  kv.decodeString("email"));
                    requestParam.put("userName", kv.decodeString("username"));

                    ApiFactory.getInstance()
                            .email(kv.decodeString("tokenId"),requestParam)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {
                                if (data.isStauts() == true) {
                                    //构造CountDownTimer对象
                                    time = new TimeCount(MonyPwodActivity.this, btn_YZM, "重新获取", 60000, 1000);
                                    time.start();
                                    Toast.makeText(MonyPwodActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MonyPwodActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {

                            });
                }

            }
        });
    }
}
