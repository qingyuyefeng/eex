package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;

import com.eex.R;
import com.eex.assets.weight.ZXingUtils;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;

import com.eex.home.weight.Utils;

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
 * @ClassName: GooleActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 10:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GooleActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.img_Zxing)
    ImageView imgZxing;
    /**
     *
     */
    @BindView(R.id.text_title)
    TextView textTitle;
    /**
     *
     */
    @BindView(R.id.edt_goole)
    EditText edtGoole;
    /**
     *
     */
    @BindView(R.id.text_fz)
    TextView textFz;
    /**
     *
     */
    @BindView(R.id.ll_miyao)
    LinearLayout llMiyao;
    /**
     *
     */
    @BindView(R.id.edt_Loginpwd)
    EditText edtLoginpwd;
    /**
     *
     */
    @BindView(R.id.ll_Google)
    LinearLayout llGoogle;
    /**
     *
     */
    @BindView(R.id.edt_YZM)
    EditText edtYZM;
    /**
     *
     */
    @BindView(R.id.tx1)
    TextView tx1;
    /**
     *
     */
    @BindView(R.id.tx2)
    TextView tx2;
    /**
     *
     */
    @BindView(R.id.tx3)
    TextView tx3;
    /**
     *
     */
    @BindView(R.id.tx4)
    TextView tx4;
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

    private String str = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goole;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle(getActivity().getResources().getString(R.string.gooleRZ));

        net();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .creategoogle(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        String name = data.getData().getUserName();
                        boolean status = name.contains("+86 ");
                        if (status) {
                            str = name.substring(3);
                        }

                        try {
                            if (str.equals("")) {
                                Log.e("DAWQWWQWQ", "otpauth://totp/" + data.getData().getCompany() + ":" + data.getData().getUserName() + "?secret=" + data.getData().getGoogleKey());
                                Bitmap imgbitmap = ZXingUtils.createQRImage("otpauth://totp/" + data.getData().getCompany() + ":" + data.getData().getUserName() + "?secret=" + data.getData().getGoogleKey(), imgZxing.getWidth(), imgZxing.getHeight());
                                imgZxing.setImageBitmap(imgbitmap);
                                edtGoole.setText(data.getData().getGoogleKey());
                            } else {
                                Bitmap imgbitmap = ZXingUtils.createQRImage("otpauth://totp/" + data.getData().getCompany() + ":" + str.toString().trim() + "?secret=" + data.getData().getGoogleKey(), imgZxing.getWidth(), imgZxing.getHeight());
                                imgZxing.setImageBitmap(imgbitmap);
                                edtGoole.setText(data.getData().getGoogleKey());
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });
    }

    @Override
    protected void initUiAndListener() {
        try {
            if (kv.decodeString("tokenId") != null) {
                if (kv.decodeInt("googleState") == 1) {
                    //已经绑定
                    btnOK.setText(getResources().getString(R.string.gooleLC6));
                    llGoogle.setVisibility(View.VISIBLE);
                    llMiyao.setVisibility(View.GONE);

                    imgZxing.setVisibility(View.GONE);
                    textTitle.setVisibility(View.GONE);
                    tx1.setVisibility(View.GONE);
                    tx2.setVisibility(View.GONE);
                    tx3.setVisibility(View.GONE);
                    tx4.setVisibility(View.GONE);

                } else {
                    //未绑定
                    btnOK.setText(getResources().getString(R.string.gooleLC5));
                    llGoogle.setVisibility(View.GONE);
                    llMiyao.setVisibility(View.VISIBLE);

                    imgZxing.setVisibility(View.VISIBLE);
                    textTitle.setVisibility(View.VISIBLE);
                    tx1.setVisibility(View.VISIBLE);
                    tx2.setVisibility(View.VISIBLE);
                    tx3.setVisibility(View.VISIBLE);
                    tx4.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            btnOK.setText(getResources().getString(R.string.gooleBD));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.img_Zxing, R.id.text_title, R.id.edt_goole, R.id.text_fz, R.id.ll_miyao, R.id.edt_Loginpwd, R.id.ll_Google, R.id.edt_YZM, R.id.tx1, R.id.tx2, R.id.tx3, R.id.tx4, R.id.btn_OK, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.img_Zxing:
                break;
            case R.id.text_title:
                break;
            case R.id.edt_goole:
                break;
            case R.id.text_fz:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(edtGoole.getText().toString().trim());
                Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_miyao:
                break;
            case R.id.edt_Loginpwd:
                break;
            case R.id.ll_Google:
                break;
            case R.id.edt_YZM:
                break;
            case R.id.tx1:
                break;
            case R.id.tx2:
                break;
            case R.id.tx3:
                break;
            case R.id.tx4:
                break;
            case R.id.btn_OK:
                if (kv.decodeInt("googleState") == 0) {
                    //绑定google
                    BindGoogle();
                } else {
                    //解绑google
                    NoBindGoogle();
                }
                break;
            case R.id.textView:
                break;
            default:
                break;
        }
    }

    /**
     * 取消绑定
     */
    @SuppressLint("CheckResult")
    private void NoBindGoogle() {

        if (edtYZM.getText().toString().trim().equals("")) {
            Toast.makeText(GooleActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtLoginpwd.getText().toString().trim().equals("")) {
            Toast.makeText(GooleActivity.this, "请输入登录密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("codes", edtYZM.getText().toString().trim());
        hashMap.put("googleKey", kv.decodeString("googleKey"));

        Log.e("GOOOC", "NoBindGoogle: " + kv.decodeString("googleKey"));
        hashMap.put("password", Utils.md5(edtLoginpwd.getText().toString().trim() + "hello, moto"));
        ApiFactory.getInstance()
                .googlevalidate(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //存储User数据
                        MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
                        kv.encode("googleKey", "");

                        finish();
                    } else {
                        t(data.getMsg());
                    }
                });

    }

    /**
     * 绑定google
     */
    @SuppressLint("CheckResult")
    private void BindGoogle() {
        if (edtYZM.getText().toString().trim().equals("")) {
            Toast.makeText(GooleActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtGoole.getText().toString().trim().equals("")) {
            Toast.makeText(GooleActivity.this, "请输入谷歌Key", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("codes", edtYZM.getText().toString().trim());
        requestParam.put("savedSecret", edtGoole.getText().toString().trim());
        ApiFactory.getInstance()
                .validate(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts()) {
                        Toast.makeText(GooleActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                        //存储User数据
                        MMKV kv = MMKV.mmkvWithID(UserConstants.USER_DAO, MMKV.MULTI_PROCESS_MODE);
                        kv.encode("googleKey", data.getData().getGoogleKey());

                        finish();
                    } else {
                        Toast.makeText(GooleActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });
    }
}
