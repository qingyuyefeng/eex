package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

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
 * @ClassName: RealNameActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 14:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 14:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 实名认证
 */
public class RealNameActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.textname)
    TextView textname;
    /**
     *
     */
    @BindView(R.id.real_name_LL)
    LinearLayout realNameLL;
    /**
     *
     */
    @BindView(R.id.edt_name)
    EditText edtName;
    /**
     *
     */
    @BindView(R.id.edt_nameNew)
    EditText edtNameNew;
    /**
     *
     */
    @BindView(R.id.edt_ID)
    EditText edtID;
    /**
     *
     */
    @BindView(R.id.ll_shenfenza)
    LinearLayout llShenfenza;
    /**
     *
     */
    @BindView(R.id.edt_guojia)
    EditText edtGuojia;
    /**
     *
     */
    @BindView(R.id.ll_guojia)
    LinearLayout llGuojia;
    /**
     *
     */
    @BindView(R.id.tx_A)
    TextView txA;
    /**
     *
     */
    @BindView(R.id.edt_huzhao)
    EditText edtHuzhao;
    /**
     *
     */
    @BindView(R.id.ll_huzhao)
    LinearLayout llHuzhao;
    /**
     *
     */
    @BindView(R.id.TxType)
    TextView TxType;
    /**
     *
     */
    @BindView(R.id.next_btn)
    Button nextBtn;


    private String type = "0";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle("级别2认证");

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

    @OnClick({R.id.comtitlebar, R.id.real_name_LL, R.id.edt_name, R.id.edt_nameNew, R.id.edt_ID, R.id.ll_shenfenza, R.id.edt_guojia, R.id.ll_guojia, R.id.edt_huzhao, R.id.ll_huzhao, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.real_name_LL:
                //国家地区
                intent.setClass(RealNameActivity.this, RegionActicity.class);
                startActivityForResult(intent, 2000);
                break;
            case R.id.edt_name:
                break;
            case R.id.edt_nameNew:
                break;
            case R.id.edt_ID:
                break;
            case R.id.ll_shenfenza:
                break;
            case R.id.edt_guojia:
                break;
            case R.id.ll_guojia:
                break;
            case R.id.edt_huzhao:
                break;
            case R.id.ll_huzhao:
                break;
            case R.id.next_btn:

                if (edtName.getText().toString().trim().equals("")) {
                    Toast.makeText(RealNameActivity.this, R.string.lxing, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtNameNew.getText().toString().trim().equals("")) {
                    Toast.makeText(RealNameActivity.this, R.string.lming, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type == "0") {
                    //身份证验证
                    if (edtID.getText().toString().trim().equals("")) {
                        Toast.makeText(RealNameActivity.this, R.string.hengednz, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!isLegalId(edtID.getText().toString().trim())) {
                        Toast.makeText(RealNameActivity.this, R.string.zhengq, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    //护照验证
                    if (edtGuojia.getText().toString().trim().equals("")) {
                        Toast.makeText(RealNameActivity.this, R.string.guojia, Toast.LENGTH_SHORT).show();
                    }
                    if (edtHuzhao.getText().toString().trim().equals("")) {
                        Toast.makeText(RealNameActivity.this, R.string.huzhao, Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                //用户二级认证
                twolevelauth();
                break;
            default:
                break;
        }
    }



    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        if (id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *用户二级认证
     */
    @SuppressLint("CheckResult")
    private void twolevelauth() {
        HashMap<String, String> hashMap = new HashMap<>();
        if (type == "" || type == "0") {
            hashMap.put("contry","中国");
            hashMap.put("cardType", "0");
            hashMap.put("cardNo", edtID.getText().toString().trim());
        } else {
            hashMap.put("cardType", "1");
            hashMap.put("contry", edtGuojia.getText().toString().trim());
            hashMap.put("cardNo", edtHuzhao.getText().toString().trim());
        }
        hashMap.put("sex","2");
        hashMap.put("surname",edtName.getText().toString().trim());
        hashMap.put("givename",edtNameNew.getText().toString().trim());

        ApiFactory.getInstance()
                .twolevelauth(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts()==true){
                        //
                        intent.setClass(getApplicationContext(),TwoRealNameTypeActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                     t(data.getMsg());
                    }

                },throwable -> {

                });

    }


    /**
     * 国家及地区回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (data != null) {

                if (data.getStringExtra("name").equals(getResources().getString(R.string.dalu))) {
                    type = "0";
                    TxType.setText("信息一旦提交不可修改,请务必如实填写准确信息");
                    TxType.setTextColor(getResources().getColor(R.color.appbar_background3));
                    llShenfenza.setVisibility(View.VISIBLE);
                    llGuojia.setVisibility(View.GONE);
                    llHuzhao.setVisibility(View.GONE);
                    txA.setVisibility(View.GONE);
                } else {
                    TxType.setText("请确保您使用本人的身份信息进行验证,我们会保护您的个人信息安全.");
                    TxType.setTextColor(getResources().getColor(R.color.background_baise));
                    type = "1";
                    llGuojia.setVisibility(View.VISIBLE);
                    llHuzhao.setVisibility(View.VISIBLE);
                    txA.setVisibility(View.VISIBLE);
                    llShenfenza.setVisibility(View.GONE);

                }
                textname.setText(data.getStringExtra("name"));
            }

        }
    }



}
