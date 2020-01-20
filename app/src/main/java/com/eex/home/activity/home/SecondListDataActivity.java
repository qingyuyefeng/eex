package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.SecondlistData;
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
 * @ClassName: SecondListDataActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 10:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 10:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 解除锁定
 */
public class SecondListDataActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.textView3)
    TextView textView3;
    /**
     *
     */
    @BindView(R.id.tx_Biname)
    TextView txBiname;
    /**
     *
     */
    @BindView(R.id.tx_BiNuber)
    TextView txBiNuber;
    /**
     *
     */
    @BindView(R.id.BiPany)
    TextView BiPany;
    /**
     *
     */
    @BindView(R.id.tx_WholeMoney)
    TextView txWholeMoney;
    /**
     *
     */
    @BindView(R.id.tx_marketDPrice)
    TextView txMarketDPrice;
    /**
     *
     */
    @BindView(R.id.tx_marketZPrice)
    TextView txMarketZPrice;
    /**
     *
     */
    @BindView(R.id.tx_Time)
    TextView txTime;
    /**
     *
     */
    @BindView(R.id.tx_meMoney)
    TextView txMeMoney;
    /**
     *
     */
    @BindView(R.id.btn_qiang)
    Button btnQiang;
    /**
     *
     */
    private String id;
    private String NewPrice;
    private String type;
    private String time1;
    private TimeCount time;

    /**
     *
     */
    private Dialog dialog;
    private AlertDialog alertDialog;

    private SecondlistData list;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_second_list_data;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("解除锁定");

        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().getStringExtra("time") != null) {
            time1 = getIntent().getStringExtra("time");
        }
        if (getIntent().getStringExtra("newPrice") != null) {
            NewPrice = getIntent().getStringExtra("newPrice");
        }
        if (getIntent().getStringExtra("type") != null) {
            type = getIntent().getStringExtra("type");
        }

        //根据ID查询配置
        findSecondKillRecordById();
    }

    /**
     * 根据ID查询配置
     */
    @SuppressLint("CheckResult")
    private void findSecondKillRecordById() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .findSecondKillRecordById(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getData() != null) {

                        list = pageData.getData();
                        txBiname.setText(pageData.getData().getCoinCode());
                        txBiNuber.setText(pageData.getData().getSecKillNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + pageData.getData().getCoinCode());
                        BiPany.setText(pageData.getData().getSecKillValue().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        txWholeMoney.setText(pageData.getData().getSecKillSum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        txMarketDPrice.setText(pageData.getData().getNewPrice().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        txMarketZPrice.setText(pageData.getData().getPriceSum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        txTime.setText(pageData.getData().getEndDay());
                        if (pageData.getData().getEndDay().equals("已到期")) {
                            btnQiang.setText("到期解冻");
                            txMeMoney.setText("到账资金" + pageData.getData().getSecKillNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + pageData.getData().getCoinCode());
                        } else {
                            btnQiang.setText("强制解冻");
                            txMeMoney.setText("到账资金" + pageData.getData().getSecKillSum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        }

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });

    }

    @Override
    protected void initUiAndListener() {

        if (type.equals("1") && time1.equals("已到期")) {
            btnQiang.setText("到期解冻");
        } else if (type.equals("3") && time1.equals("已到期")) {
            btnQiang.setText("到期解冻");
        } else if (type.equals("5") && time1.equals("已到期")) {
            btnQiang.setText("到期解冻");
        } else {
            btnQiang.setText("强制解冻");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.btn_qiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.btn_qiang:

                if (btnQiang.getText().toString().trim().equals("到期解冻")) {
                    //验证码 交易密码
                    Dialog();
                } else {
                    Titledialog();
                }
                break;
            default:
                break;
        }
    }


    private void Dialog() {

        dialog = new Dialog(SecondListDataActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(SecondListDataActivity.this).inflate(R.layout.dialog_secondkill, null);
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
        final EditText moneyPword = (EditText) dialogView.findViewById(R.id.moneyPword);
        moneyPword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        final EditText edt_YZM = (EditText) dialogView.findViewById(R.id.edt_YZM);
        final EditText edtGoogle = (EditText) dialogView.findViewById(R.id.edtGoogle);


        final Button btn_YZM = (Button) dialogView.findViewById(R.id.btn_YZM);
        Button button = (Button) dialogView.findViewById(R.id.button);

        LinearLayout LL_google = (LinearLayout) dialogView.findViewById(R.id.LL_google);

        //判断是否有谷歌验证
        if (kv.decodeInt("googleState") != 1) {
            LL_google.setVisibility(View.GONE);
        }
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<>();
                if (kv.decodeInt("googleState") == 1) {
                    if (edtGoogle.getText().toString().trim().equals("")) {
                        Toast.makeText(SecondListDataActivity.this, "请输入谷歌验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        hashMap.put("googleKey", kv.decodeString("googleKey"));
                        hashMap.put("googleCode", edtGoogle.getText().toString().trim());
                    }
                }
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(SecondListDataActivity.this, "请输入手机验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (moneyPword.getText().toString().trim().equals("")) {
                    Toast.makeText(SecondListDataActivity.this, "请输入交易密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                hashMap.put("accountPassWord", Utils.md5(moneyPword.getText().toString().trim() + "hello, moto"));
                hashMap.put("id", id);
                hashMap.put("phone",kv.decodeString("phone"));
                hashMap.put("marketValue", NewPrice);
                hashMap.put("smsCode", edt_YZM.getText().toString().trim());

                ApiFactory.getInstance()
                        .unLockSecondOrder(hashMap)
                        .compose(RxSchedulers.io_main())
                        .subscribe(pageData -> {

                            if (pageData.getData() != null) {

                                t(pageData.getMsg());
                                dialog.dismiss();
                                finish();
                            } else {
                                t(pageData.getMsg());
                            }
                        }, throwable -> {

                        });

            }
        });


        //获取验证码
        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("sendType", "sms_take_money");
                hashMap.put("phone", kv.decodeString("phone"));
                hashMap.put("userName", kv.decodeString("username"));

                ApiFactory.getInstance()
                        .send(kv.decodeString("tokenId"), hashMap)
                        .compose(RxSchedulers.<Data>io_main())
                        .subscribe(data -> {
                            if (data.isStauts() == true) {
                                //构造CountDownTimer对象
                                time = new TimeCount(SecondListDataActivity.this, btn_YZM, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                                time.start();
                            } else {
                                t(data.getMsg());
                            }
                        });
            }
        });

    }


    private void Titledialog() {

        //创建AlertDialog的构造器的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondListDataActivity.this);
        //设置构造器标题
        //builder.setTitle("提示");
        //构造器对应的图标
        //构造器内容,为对话框设置文本项(之后还有列表项的例子)
        if (list.getNewPrice().equals(0)) {
            builder.setMessage("强制解除秒杀订单,按照当前的市价将会失去" + (BigDecimal.ZERO.subtract(list.getSecKillValue())).multiply(list.getSecKillNum()).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT,解除后系统将直接将" + list.getSecKillSum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT" + "退回到您的资金账户中");
        } else {
            builder.setMessage("强制解除秒杀订单,按照当前的市价将会失去" + (list.getNewPrice().subtract(list.getSecKillValue())).multiply(list.getSecKillNum()).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT,解除后系统将直接将" + list.getSecKillSum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT" + "退回到您的资金账户中");

        }
        //为构造器设置确定按钮,第一个参数为按钮显示的文本信息，第二个参数为点击后的监听事件，用匿名内部类实现
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                //第一个参数dialog是点击的确定按钮所属的Dialog对象,第二个参数which是按钮的标示值
            }
        });
        //为构造器设置一个比较中性的按钮，比如忽略、稍后提醒等
        builder.setNeutralButton("确定解除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Dialog();
            }
        });
        //利用构造器创建AlertDialog的对象,实现实例化
        alertDialog = builder.create();
        alertDialog.show();
    }
}
