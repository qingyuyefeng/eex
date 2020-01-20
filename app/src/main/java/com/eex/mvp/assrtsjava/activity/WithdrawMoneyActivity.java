
package com.eex.mvp.assrtsjava.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
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
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.TimeCount;
import com.eex.common.view.NewComTitleBar;
import com.eex.domin.entity.moneyaddress.Address;
import com.eex.domin.entity.moneyaddress.AddressBean;
import com.eex.extensions.RxExtensionKt;
import com.eex.home.bean.BiUserData;
import com.eex.navigation.Navigator;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: WithdrawMoneyActivity
 * @Description: 提币
 * @Author: hcj
 * @CreateDate: 2019/12/24 21:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 21:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class WithdrawMoneyActivity extends BaseActivity {


    @BindView(R.id.comtitlebar)
    NewComTitleBar comtitlebar;
    @BindView(R.id.chargemone_name)
    TextView chargemoneName;
    @BindView(R.id.Withdraw_linea)
    LinearLayout WithdrawLinea;
    @BindView(R.id.Withdraw_copy)
    EditText WithdrawCopy;
    @BindView(R.id.withdraw_er)
    ImageView withdrawEr;
    @BindView(R.id.withdraw_addrees)
    ImageView withdrawAddrees;
    @BindView(R.id.Withdraw_30)
    EditText Withdraw30;
    @BindView(R.id.assets_available1)
    TextView assetsAvailable1;
    @BindView(R.id.Capital_edtoMuber)
    TextView CapitalEdtoMuber;
    @BindView(R.id.Capital_name)
    TextView CapitalName;
    @BindView(R.id.assets_freeze1)
    TextView assetsFreeze1;
    @BindView(R.id.Withdraw_charge_Frozen)
    TextView WithdrawChargeFrozen;
    @BindView(R.id.Withdraw_charge_name)
    TextView WithdrawChargeName;
    @BindView(R.id.Withdraw_charge)
    TextView WithdrawCharge;
    @BindView(R.id.Withdraw_charge_munber)
    TextView WithdrawChargeMunber;
    @BindView(R.id.Withdraw_charge_set)
    TextView WithdrawChargeSet;
    @BindView(R.id.Withdraw_301)
    TextView Withdraw301;
    @BindView(R.id.Withdraw_1000)
    TextView Withdraw1000;
    @BindView(R.id.Withdraw_no)
    TextView WithdrawNo;

    @BindView(R.id.Capital_money)
    Button CapitalMoney;


    private String coinCode, currencyRate, fixedRate, coinMax, coinMin;
    private BiUserData bidata;
    private int REQUEST_CODE_SCAN = 111;

    private String isType = "phone";
    private TimeCount time;
    /**
     * 1代表是这个币0不是
     */
    private int tag = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_withdraw_money;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {
        comtitlebar.setTitle(getActivity().getResources().getString(R.string.assets_Mention_money));
        if (getIntent().getStringExtra("coinCode") != null) {
            coinCode = getIntent().getStringExtra("coinCode");
            fixedRate = getIntent().getStringExtra("fixedRate");
            currencyRate = getIntent().getStringExtra("currencyRate");
            coinMax = getIntent().getStringExtra("coinMax");
            coinMin = getIntent().getStringExtra("coinMin");
        }
        chargemoneName.setText(coinCode);
        CapitalName.setText(coinCode);
        WithdrawChargeName.setText(coinCode);
        WithdrawChargeSet.setText(coinCode);

        if (coinCode.equals("XLM") || coinCode.equals("XRP")) {
            tag = 1;

        } else {
            tag = 0;

        }

        Withdraw301.setText(getActivity().getResources().getString(R.string.Withdraw_30) + coinCode);
        Withdraw1000.setText(getActivity().getResources().getString(R.string.Withdraw_1000) + coinCode);
        WithdrawNo.setText(getActivity().getResources().getString(R.string.Withdraw_no)+coinCode + getResources().getString(R.string.Withdraw_no1) + coinCode + getResources().getString(R.string.Withdraw_no2));

        net();
        init();


    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hasMap = new HashMap<>();
        hasMap.put("coinCode", coinCode);
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .getaccountinfo1(kv.decodeString("tokenId"), hasMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    bidata = data.getData();
                    if (data.getData() != null) {
                        CapitalEdtoMuber.setText(data.getData().getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        BigDecimal d = data.getData().getFrozenMoney();
                        WithdrawChargeFrozen.setText(d.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    private void init() {

        Withdraw30.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                try {
                    BigDecimal b1 = new BigDecimal(currencyRate);
                    BigDecimal b2 = new BigDecimal(Withdraw30.getText().toString().trim());
                    BigDecimal b3 = b1.multiply(b2);
                    BigDecimal b4 = b3.add(new BigDecimal(fixedRate));
                    WithdrawChargeMunber.setText(b4.stripTrailingZeros().toPlainString());
                } catch (Exception e) {

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }


    @OnClick({R.id.comtitlebar, R.id.chargemone_name, R.id.Withdraw_linea, R.id.Withdraw_copy, R.id.withdraw_er, R.id.withdraw_addrees, R.id.Withdraw_30, R.id.assets_available1, R.id.Capital_edtoMuber, R.id.Capital_name, R.id.assets_freeze1, R.id.Withdraw_charge_Frozen, R.id.Withdraw_charge_name, R.id.Withdraw_charge, R.id.Withdraw_charge_munber, R.id.Withdraw_charge_set, R.id.Capital_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.chargemone_name:
                break;
            case R.id.Withdraw_linea:

                intent = new Intent(getActivity(), CurrencyChoiceActivity.class);
                intent.putExtra("CurrencyChoice", "2");
                startActivity(intent);
                break;
            case R.id.Withdraw_copy:
                break;
            case R.id.withdraw_er:
                intent = new Intent(getActivity(), CaptureActivity.class);
                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                 * 也可以不传这个参数
                 * 不传的话  默认都为默认不震动  其他都为true
                 * */

                /*ZxingConfig是配置类
                 *可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */

                ZxingConfig config = new ZxingConfig();
                //底部布局（包括闪光灯和相册）
                config.setShowbottomLayout(true);
                //是否播放提示音
                config.setPlayBeep(true);
                //是否震动
                config.setShake(true);
                //是否显示相册
                config.setShowAlbum(true);
                //是否显示闪光灯
                config.setShowFlashLight(true);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);

                break;
            case R.id.withdraw_addrees:
                Navigator.INSTANCE.toMoneyAddressActivity(this, 1, 1);
                break;
            case R.id.Withdraw_30:
                break;
            case R.id.assets_available1:
                break;
            case R.id.Capital_edtoMuber:
                break;
            case R.id.Capital_name:
                break;
            case R.id.assets_freeze1:
                break;
            case R.id.Withdraw_charge_Frozen:
                break;
            case R.id.Withdraw_charge_name:
                break;
            case R.id.Withdraw_charge:
                break;
            case R.id.Withdraw_charge_munber:
                break;
            case R.id.Withdraw_charge_set:
                break;
            case R.id.Capital_money:


                if (Withdraw30.getText().toString().trim().equals("")) {
                    t("请输入正确提币数量");
                    return;
                }
                try {
                    if (Double.parseDouble(Withdraw30.getText().toString().trim()) > Double.parseDouble(bidata.getCoinMoney().stripTrailingZeros().toPlainString())) {
                        t("转出数量不能大于可用数量");
                        return;
                    }
                    if (Double.parseDouble(Withdraw30.getText().toString().trim()) < Double.parseDouble(coinMin)) {
                        t("最小提币数量不能小于" + coinMin);
                        return;
                    }
                    if (Double.parseDouble(Withdraw30.getText().toString().trim()) > Double.parseDouble(coinMax)) {
                        t("最大提币数量不能大于" + coinMax);
                        return;
                    }

                    if (Double.parseDouble(Withdraw30.getText().toString().trim()) < 0) {
                        t("请输入提币数量");
                    }
                } catch (Exception e) {

                }


                if (WithdrawCopy.getText().toString().trim().equals("")) {
                    t("请选择钱包地址");
                }

                View popView = View.inflate(getActivity(), R.layout.re_popupwindow_time, null);

                TextView txvewphone = popView.findViewById(R.id.txvew_phone);
                EditText edtYZM = popView.findViewById(R.id.edt_YZM);
                Button btnYZM = popView.findViewById(R.id.btn_YZM);
                Button button = popView.findViewById(R.id.button);

                //获取屏幕宽高
                int weight = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;

                final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
                popupWindow.setAnimationStyle(R.style.anim_popup_dir);
                //这里必须设置为true才能点击区域外或者消失
                popupWindow.setFocusable(true);
                //这个控制PopupWindow内部控件的点击事件
                popupWindow.setTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.update();

                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                    isType = "phone";
                    txvewphone.setText("手机验证码");
                    btnYZM.setVisibility(View.VISIBLE);
                }
                if (kv.decodeInt("googleState") == 1) {
                    isType = "google";
                    txvewphone.setText("Google身份验证器");
                    btnYZM.setVisibility(View.GONE);

                }

                btnYZM.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onClick(View v) {

                        if (isType.equals("phone")) {
                            //电话发送提币验证码
                            HashMap<String, String> requestParam = new HashMap<>();
                            requestParam.put("sendType", "sms_take_money");
                            requestParam.put("phone", kv.decodeString("phone"));

                            RxExtensionKt.filterResult(ApiFactory.getInstance()
                                    .send(kv.decodeString("tokenId"), requestParam))
                                    .compose(RxSchedulers.io_main())
                                    .subscribe(data -> {

                                        if (data.getCode() == 200) {
                                            //构造CountDownTimer对象
                                            time = new TimeCount(getActivity(), btnYZM, "重新获取", 60000, 1000);
                                            time.start();
                                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                        } else {

                                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }, throwable -> {

                                    });


                        } else if (isType.equals("email")) {
                            //邮箱发送提币验证码
                            HashMap<String, String> requestParam = new HashMap<>();
                            requestParam.put("sendType", "5");
                            requestParam.put("email", kv.decodeString("email"));


                            RxExtensionKt.filterResult(ApiFactory.getInstance()
                                    .email(kv.decodeString("tokenId"), requestParam))
                                    .compose(RxSchedulers.io_main())
                                    .subscribe(data -> {

                                        if (data.getCode() == 200) {
                                            //构造CountDownTimer对象
                                            time = new TimeCount(getActivity(), btnYZM, "重新获取", 60000, 1000);
                                            time.start();
                                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }, throwable -> {

                                    });
                        }
                    }
                });

                //体现
                button.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onClick(View v) {
                        if (edtYZM.getText().toString().trim().equals("")) {
                            t("请输入验证码");
                            return;
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        try {

                            if (isType.equals("phone")) {
                                //1手机，3邮箱,2谷歌
                                hashMap.put("checkType", "1");
                                hashMap.put("phoneoremail", kv.decodeString("phone") + "");
                            } else if (isType.equals("google")) {
                                hashMap.put("checkType", "2");
                                hashMap.put("googleKey", kv.decodeString("googleKey"));
                            } else {
                                hashMap.put("checkType", "3");
                                hashMap.put("phoneoremail", kv.decodeString("email") + "");
                            }
                            Double sever = Double.parseDouble(fixedRate) + (Double.parseDouble(Withdraw30.getText().toString().trim()) * Double.parseDouble(currencyRate));
                            WithdrawChargeMunber.setText(sever.toString());
                            //提币手续费
                            hashMap.put("serviceHarge", sever.toString().trim());
                            //虚拟货币类型
                            hashMap.put("coinCode", bidata.getCoinCode());
                            //钱包地址 11111111111111111111111111111111111111111111
                            if (tag == 1) {
                                hashMap.put("walletAddress", coinCode + "_" + Withdraw30.getText().toString().trim());
                            } else {
                                hashMap.put("walletAddress", coinCode + "");
                            }
                            //提币数量
                            hashMap.put("coinMoney", Withdraw30.getText().toString().trim());
                            //验证码（Google身份验证器）
                            hashMap.put("code", edtYZM.getText().toString().trim() + "");

                            ApiFactory.getInstance().coinextract(kv.decodeString("tokenId"), hashMap)
                                    .compose(RxSchedulers.io_main())
                                    .subscribe(new Consumer<Response<Data>>() {
                                        @Override
                                        public void accept(Response<Data> data) throws Exception {

                                            //替换原来的tokenId
                                            String str = data.raw().headers("authorization").toString().substring(1, data.raw().headers("authorization").toString().length() - 1);
                                            kv.encode("tokenId", str);

                                            if (data.body().getCode() == 200) {

                                                popupWindow.dismiss();
                                                finish();
                                                t(data.body().getMsg());
                                            } else {
                                                popupWindow.dismiss();
                                                t(data.body().getMsg());
                                            }

                                        }


                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {

                                        }
                                    });

                        } catch (Exception e) {
                            t("请绑定邮箱或电话");
                        }

                    }

                });

                popupWindow.dismiss();

                //popupWindow出现屏幕变为半透明
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
                popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 50);
                BackgroudAlpha((float) 0.5);
                popupWindow.setOnDismissListener(new popupwindowdismisslistener());

                break;
        }
    }


    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = getActivity().getWindow().getAttributes();
        l.alpha = alpha;
        getActivity().getWindow().setAttributes(l);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                WithdrawCopy.setText(content);
            }
        }


        if (requestCode == 1 && data != null) {

            AddressBean addressBean = (AddressBean) data.getSerializableExtra("bean");

            WithdrawCopy.setText(addressBean.getWalletAddress());


        }
    }
}
