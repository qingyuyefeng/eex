package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.Storehouse;
import com.eex.home.weight.Utils;

import java.math.BigDecimal;
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
 * @ClassName: StorehouseActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 21:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 21:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 解仓
 */
public class StorehouseActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_name)
    TextView txName;
    /**
     *
     */
    @BindView(R.id.tx_tiem)
    TextView txTiem;
    /**
     *
     */
    @BindView(R.id.tx_meMoney)
    TextView txMeMoney;
    /**
     *
     */
    @BindView(R.id.tx_fee)
    TextView txFee;
    /**
     *
     */
    @BindView(R.id.edt_nuber)
    EditText edtNuber;
    /**
     *
     */
    @BindView(R.id.tx_max)
    TextView txMax;
    /**
     *
     */
    @BindView(R.id.tx_whole)
    TextView txWhole;
    /**
     *
     */
    @BindView(R.id.tx_fee1)
    TextView txFee1;
    /**
     *
     */
    @BindView(R.id.btn_put)
    Button btnPut;

    /**
     *
     */
    private CountDownTimer data;
    private long day;
    private long hour;
    private long minute;
    private long second;


    /**
     *
     */
    private String id;
    private String lockOverplusMoney;
    private Dialog dialog;
    private String MoneyPword;

    private Storehouse list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_storehouse;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle(getActivity().getResources().getString(R.string.moneyjIE));

        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }

        //
        findLockingMoneyById();
    }

    @SuppressLint("CheckResult")
    private void findLockingMoneyById() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .findLockingMoneyById(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    dialog.dismiss();
                    if (pageData.getData() != null) {

                        list = pageData.getData();
                        txName.setText(pageData.getData().getCoinCode());
                        long NowTime = pageData.getData().getEndDay() - getCurTimeLong();
                        if (NowTime > 0) {
                            btnPut.setText("强制解仓");
                        } else if (NowTime <= 0) {
                            btnPut.setText("确定解仓");
                        }
                        data = new CountDownTimer(NowTime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                //天
                                day = millisUntilFinished / (1000 * 60 * 60 * 24);
                                //时
                                hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                                //分
                                minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                                //秒
                                second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                                try {
                                    txTiem.setText(day + "天" + hour + "时" + minute + "分" + second + "秒" + "");
                                } catch (Exception e) {
                                }
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        data.start();
                        if (pageData.getData().getMinLockNum().compareTo(pageData.getData().getLockOverplusMoney()) == 1) {
                            edtNuber.setClickable(false);
                            edtNuber.setFocusable(false);
                            edtNuber.setText(pageData.getData().getLockOverplusMoney().stripTrailingZeros().toPlainString());
                        }
                        lockOverplusMoney = pageData.getData().getLockOverplusMoney().stripTrailingZeros().toPlainString();
                        txMeMoney.setText(pageData.getData().getLockOverplusMoney().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + pageData.getData().getCoinCode());
                        if (pageData.getData().getFee() != null) {
                            if (pageData.getData().getCoinCodeProfit().equals("USDT")) {
                                txFee.setText(pageData.getData().getFee().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + pageData.getData().getCoinCodeProfit());
                            } else {
                                txFee.setText(pageData.getData().getFee().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + pageData.getData().getCoinCode());

                            }

                        } else {
                            txFee.setText("");
                        }
                        edtNuber.setHint("请输入解仓数量(不得低于" + pageData.getData().getMinLockNum().intValue() + pageData.getData().getCoinCode() + ")");
                        txMax.setText("最多可解仓" + pageData.getData().getLockOverplusMoney().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + pageData.getData().getCoinCode());

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                    dialog.dismiss();
                });
    }

    /**
     * 获取系统时间戳
     *
     * @return
     */
    public long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
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

    @OnClick({R.id.comtitlebar, R.id.edt_nuber, R.id.btn_put, R.id.tx_whole})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_whole:
                edtNuber.setText(lockOverplusMoney);
                break;
            case R.id.edt_nuber:

                edtNuber.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (list.getFeeDeductionType() == 1) {
                            String nuber = edtNuber.getText().toString().trim();
                            if (nuber.equals("")) {
                                nuber = "0";
                            }
                            BigDecimal feeDa = (list.getLockRateFee().multiply(new BigDecimal(nuber)).add(list.getLockFixedFee()));

                            txFee1.setText("解仓手续费:" + feeDa.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + list.getCoinCode());

                        } else {
                            txFee1.setText("解仓手续费:" + list.getServiceFee().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + list.getCoinCode());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case R.id.btn_put:
                //验证交易密码
                dialog();
                break;
            default:
                break;
        }
    }

    /**
     * 验证交易密码
     */
    private void dialog() {


        dialog = new Dialog(StorehouseActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(StorehouseActivity.this).inflate(R.layout.dialog_money, null);
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
        final EditText edt_YZM = (EditText) dialogView.findViewById(R.id.edt_Pword);
        edt_YZM.setTransformationMethod(PasswordTransformationMethod.getInstance());
        final Button btn_YZM = (Button) dialogView.findViewById(R.id.btn_yes);
        dialog.show();
        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(StorehouseActivity.this, "请输入交易密码", Toast.LENGTH_SHORT).show();
                } else {
                    MoneyPword = edt_YZM.getText().toString().trim();

                    if (edtNuber.getText().toString().trim().equals("")) {
                        Toast.makeText(StorehouseActivity.this, "请填写数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    BigDecimal nuber = new BigDecimal(edtNuber.getText().toString().trim());
                    if (nuber.compareTo(list.getMinLockNum()) == -1) {
                        Toast.makeText(StorehouseActivity.this, "最低可解仓" + list.getMinLockNum().stripTrailingZeros().toPlainString() + list.getCoinCode(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (nuber.compareTo(list.getMaxLockNum()) == 1) {
                        Toast.makeText(StorehouseActivity.this, "最多可解仓" + list.getMaxLockNum().stripTrailingZeros().toPlainString() + list.getCoinCode(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //确定解仓
                    openLockingMoney();
                }

            }
        });
    }

    /**
     * 确定解仓
     */
    @SuppressLint("CheckResult")
    private void openLockingMoney() {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accountPassWord", Utils.md5(MoneyPword + "hello, moto"));
        hashMap.put("id", id);
        hashMap.put("coinMoney", edtNuber.getText().toString().trim());
        ApiFactory.getInstance()
                .openLockingMoney(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.isStauts() == true) {


                        dialog.dismiss();
                        t(pageData.getMsg());
                        finish();

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }
}
