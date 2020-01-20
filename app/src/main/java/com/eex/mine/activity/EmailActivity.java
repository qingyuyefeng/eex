package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
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
 * @Package: com.overthrow.mine.activity
 * @ClassName: EmailActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 14:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 14:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EmailActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.edt_EMail)
    EditText edtEMail;
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
    TextView textView;

    private TimeCount time;
    private String phone;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.email_verification));

        try {
            if (kv.decodeString("tokenId") != null) {
                if ( kv.decodeString("email") != null && ! kv.decodeString("email").equals("")) {
                    btnOK.setText(getActivity().getResources().getString(R.string.jiebangyouxiang));
                    edtEMail.setText( kv.decodeString("email"));
                    edtEMail.setEnabled(false);
                } else {
                    btnOK.setText(getActivity().getResources().getString(R.string.bangd));

                }
            }
        } catch (Exception e) {
            btnOK.setText(getActivity().getResources().getString(R.string.bangd));
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

    @OnClick({R.id.comtitlebar, R.id.edt_EMail, R.id.edt_YZM, R.id.btn_YZM, R.id.btn_OK, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:

                finish();
                break;
            case R.id.edt_EMail:
                edtEMail.getText().toString().trim();
                break;
            case R.id.edt_YZM:
                break;
            //获取邮箱验证码
            case R.id.btn_YZM:
                if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.bangd))) {
                    getEmail("2",edtEMail.getText().toString().trim());
                } else if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.jiebangyouxiang))) {
                    getEmail("3",edtEMail.getText().toString().trim());
                }
                break;
            case R.id.btn_OK:
                if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.bangd))) {
                    putEmail();
                } else if (btnOK.getText().toString().trim().equals(getResources().getString(R.string.jiebangyouxiang))) {
                    RelieveEmainlData();
                }
                break;
            case R.id.textView:
                break;
            default:
                break;
        }
    }



    @SuppressLint("CheckResult")
    private void RelieveEmainlData() {
        if (edtEMail.getText().toString().trim().equals("")){
            Toast.makeText(EmailActivity.this,R.string.youxianfw,Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtYZM.getText().toString().trim().equals("")){
            Toast.makeText(EmailActivity.this,R.string.login_yzm,Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("email",edtEMail.getText().toString().trim());
        requestParam.put("code",edtYZM.getText().toString().trim());

        ApiFactory.getInstance()
                .cenelbind(kv.decodeString("tokenId"),requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode()==200){
                        //存储User数据
                        MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
                        kv.encode("email", "");
                        finish();
                        t(data.getMsg());
                    }else {
                        t(data.getMsg());
                    }
                },throwable -> {

                });

    }

    @SuppressLint("CheckResult")
    private void putEmail() {
        if (edtEMail.getText().toString().trim().equals("")){
            Toast.makeText(EmailActivity.this,R.string.youxianfw,Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtYZM.getText().toString().trim().equals("")){
            Toast.makeText(EmailActivity.this,R.string.login_yzm,Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("email",edtEMail.getText().toString().trim());
        requestParam.put("code",edtYZM.getText().toString().trim());
        ApiFactory.getInstance()
                .bindemail(kv.decodeString("tokenId"),requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode()==200){
                        //存储User数据
                        MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
                        kv.encode("email", edtEMail.getText().toString().trim());
                        finish();
                        t(data.getMsg());
                    }else {
                        t(data.getMsg());
                    }

                },throwable -> {

                });
    }

    /**
     * @param s
     */
        private void getEmail(String s, String email) {

        if (email.equals("")) {
            Toast.makeText(EmailActivity.this, R.string.youxianfw, Toast.LENGTH_SHORT).show();
        } else {
            getEmailData(s,email);

        }
    }

    @SuppressLint("CheckResult")
    private void getEmailData(String s,String email) {
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("email", edtEMail.getText().toString().trim());
        if (s.equals("2")) {
            //发送绑定邮箱验证码
            requestParam.put("sendType", "2");
        } else {
            //发送解绑邮箱验证码
            requestParam.put("sendType", "3");
        }

        ApiFactory.getInstance()
                .sendcode(kv.decodeString("tokenId"),requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 200) {
                        //构造CountDownTimer对象
                        time = new TimeCount(EmailActivity.this, btnYZM, "重新获取", 60000, 1000);
                        time.start();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }
}
