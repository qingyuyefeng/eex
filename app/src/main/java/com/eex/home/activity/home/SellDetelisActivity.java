package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.util.CommonUtil;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.Advertisingvo;
import com.eex.home.weight.MyDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
 * @ClassName: SellDetelisActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 16:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 16:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SellDetelisActivity extends BaseActivity {



    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.name_T)
    TextView nameT;
    /**
     *
     */
    @BindView(R.id.name)
    TextView name;
    /**
     *
     */
    @BindView(R.id.img_type)
    ImageView imgType;
    /**
     *
     */
    @BindView(R.id.weixin)
    ImageView weixin;
    /**
     *
     */
    @BindView(R.id.zhifubao)
    ImageView zhifubao;
    /**
     *
     */
    @BindView(R.id.yinhangka)
    ImageView yinhangka;
    /**
     *
     */
    @BindView(R.id.nuberset)
    TextView nuberset;
    /**
     *
     */
    @BindView(R.id.Unit_Price)
    TextView UnitPrice;
    /**
     *
     */
    @BindView(R.id.nuber)
    TextView nuber;
    /**
     *
     */
    @BindView(R.id.yijia)
    TextView yijia;
    /**
     *
     */
    @BindView(R.id.edt_zongjia)
    EditText edtZongjia;
    /**
     *
     */
    @BindView(R.id.edt_nuber)
    EditText edtNuber;
    /**
     *
     */
    @BindView(R.id.biname)
    TextView biname;
    /**
     *
     */
    @BindView(R.id.zongjiage)
    TextView zongjiage;
    /**
     *
     */
    @BindView(R.id.BTN_put)
    Button BTNPut;


    private MyDialog mMyDialog;
    private String id;

    private String dataTime = "";
    /**
     * 标记edittext不会死循环
     */
    private Boolean flag = false;

    private Advertisingvo advertisingvo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sell_detelis;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("卖出");

        TimeCountJava timeCountJava = new TimeCountJava(60000, 1000);
        timeCountJava.start();

        TextWatcher1 textWatcher1 = new TextWatcher1();
        TextWatcher2 textWatcher2 = new TextWatcher2();
        edtZongjia.addTextChangedListener(textWatcher1);
        edtNuber.addTextChangedListener(textWatcher2);

        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }


        //
        net(id);
    }

    @SuppressLint("CheckResult")
    private void net(String id) {
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", id);

        final ProgressDialog dialog = ProgressDialog.show(SellDetelisActivity.this, "提示", "正在加载中...");
        ApiFactory.getInstance()
                .getAdvertising(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    dialog.dismiss();
                    if (data.getData() != null) {
                        advertisingvo = data.getData();
                        setView(advertisingvo);
                    } else {
                        Toast.makeText(SellDetelisActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    dialog.dismiss();
                });

    }

    private void setView(Advertisingvo advertisingvo) {

        comtitlebar.setTitle("卖出" + advertisingvo.getTradeCoin());

        try {

            if (advertisingvo.getMerchantStatus() == 1) {
                imgType.setVisibility(View.GONE);
            } else {
                imgType.setVisibility(View.VISIBLE);
            }

            if (advertisingvo.getAccountType().size() != 0) {
                if (advertisingvo.getAccountType().get(0) == 1) {
                    yinhangka.setVisibility(View.VISIBLE);
                }
                if (advertisingvo.getAccountType().get(0) == 2) {
                    zhifubao.setVisibility(View.VISIBLE);
                }
                if (advertisingvo.getAccountType().get(0) == 3) {
                    weixin.setVisibility(View.VISIBLE);
                }

                try {
                    if (advertisingvo.getAccountType().get(1) == 1) {
                        yinhangka.setVisibility(View.VISIBLE);
                    }
                    if (advertisingvo.getAccountType().get(1) == 2) {
                        zhifubao.setVisibility(View.VISIBLE);
                    }
                    if (advertisingvo.getAccountType().get(1) == 3) {
                        weixin.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {

                }

                try {
                    if (advertisingvo.getAccountType().get(2) == 1) {
                        yinhangka.setVisibility(View.VISIBLE);
                    }
                    if (advertisingvo.getAccountType().get(2) == 2) {
                        zhifubao.setVisibility(View.VISIBLE);
                    }
                    if (advertisingvo.getAccountType().get(2) == 3) {
                        weixin.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {

                }

            }
            biname.setText(advertisingvo.getTradeCoin());
            nameT.setText(advertisingvo.getMerchName().substring(0, 1));
            name.setText(advertisingvo.getMerchName());
            if (advertisingvo.getTradeOKCount() == 0 || advertisingvo.getTradeCount() == 0) {
                nuberset.setText("交易次数" + advertisingvo.getTradeCount() + "/成交次数" + advertisingvo.getTradeOKCount() + "/成家率" + "0%");
            } else {
                DecimalFormat df = new DecimalFormat("0.00");
                nuberset.setText("交易次数" + advertisingvo.getTradeCount() + "/成交次数" + advertisingvo.getTradeOKCount() + "/成家率" + df.format((float) advertisingvo.getTradeOKCount() / advertisingvo.getTradeCount() * 100) + "%");

            }
            BigDecimal NUBER = advertisingvo.getTradePrice().multiply(advertisingvo.getPremium());
            UnitPrice.setText(NUBER.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString() + "CNY");
            nuber.setText(advertisingvo.getTradeNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + advertisingvo.getTradeCoin());
            yijia.setText(advertisingvo.getMinTradeNum().stripTrailingZeros().toPlainString() + "-" + advertisingvo.getMaxTradeNum().stripTrailingZeros().toPlainString() + "CNY");


        } catch (Exception e) {

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

    @OnClick({R.id.comtitlebar, R.id.name_T, R.id.name, R.id.img_type, R.id.weixin, R.id.zhifubao, R.id.yinhangka, R.id.nuberset, R.id.Unit_Price, R.id.nuber, R.id.yijia, R.id.edt_zongjia, R.id.edt_nuber, R.id.biname, R.id.zongjiage, R.id.BTN_put})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.name_T:
                break;
            case R.id.name:
                break;
            case R.id.img_type:
                break;
            case R.id.weixin:
                break;
            case R.id.zhifubao:
                break;
            case R.id.yinhangka:
                break;
            case R.id.nuberset:
                break;
            case R.id.Unit_Price:
                break;
            case R.id.nuber:
                break;
            case R.id.yijia:
                break;
            case R.id.edt_zongjia:
                break;
            case R.id.edt_nuber:
                break;
            case R.id.biname:
                break;
            case R.id.zongjiage:
                break;
            case R.id.BTN_put:

                try {

                    if (new BigDecimal(edtNuber.getText().toString()).compareTo(advertisingvo.getTradeNum().setScale(6, BigDecimal.ROUND_DOWN)) > -1) {
                        Toast.makeText(SellDetelisActivity.this, "请重新输入卖出数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    putSure();

                } catch (Exception e) {
                }

                break;

            default:
                break;
        }
    }

    private void putSure() {


        if (edtZongjia.getText().toString().trim().equals("")) {
            Toast.makeText(SellDetelisActivity.this, "请输入卖出总金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtNuber.getText().toString().trim().equals("")) {
            Toast.makeText(SellDetelisActivity.this, "请输入卖出数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (new BigDecimal(edtZongjia.getText().toString().trim()).compareTo(advertisingvo.getMinTradeNum()) == -1) {
            Toast.makeText(SellDetelisActivity.this, "最小卖出总价不能小于" + advertisingvo.getMinTradeNum().stripTrailingZeros().toPlainString() + "CNY", Toast.LENGTH_SHORT).show();
            return;
        }
        if (new BigDecimal(edtZongjia.getText().toString().trim()).compareTo(advertisingvo.getMaxTradeNum()) == 1) {
            Toast.makeText(SellDetelisActivity.this, "最大卖出总价不能大于" + advertisingvo.getMaxTradeNum().stripTrailingZeros().toPlainString() + "CNY", Toast.LENGTH_SHORT).show();
            return;
        }
        View view1 = getLayoutInflater().inflate(R.layout.dialog_sell, null);
        TextView txNAME = (TextView) view1.findViewById(R.id.txNAME);
        txNAME.setText("请确认价格再下单，下单后此交易的" + advertisingvo.getTradeCoin() + "将托管，请放心出售");
        TextView RedPrice = (TextView) view1.findViewById(R.id.RedPrice);
        TextView nuber = (TextView) view1.findViewById(R.id.nuber);
        TextView newPrice = (TextView) view1.findViewById(R.id.newPrice);
        String price = edtZongjia.getText().toString().trim();
        RedPrice.setText(price + "CNY");
        String nuber1 = edtNuber.getText().toString().trim();
        nuber.setText(nuber1 + advertisingvo.getTradeCoin());
        BigDecimal strPrice = advertisingvo.getTradePrice().multiply(advertisingvo.getPremium());
        newPrice.setText(strPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "CNY");
        mMyDialog = new MyDialog(SellDetelisActivity.this, 0, 0, view1, R.style.DialogTheme);
        mMyDialog.setCancelable(true);
        mMyDialog.show();
        Button btn_dimss = (Button) view1.findViewById(R.id.btn_dimss);
        btn_dimss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDialog.dismiss();
            }
        });

        Button btn_sell = (Button) view1.findViewById(R.id.btn_sell);
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataTime.equals("60min")) {
                    mMyDialog.dismiss();
                    Toast.makeText(SellDetelisActivity.this, "订单确认时间为1分钟,您已超时,请刷新重试", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                    if (CommonUtil.isFastClick()) {
                        PutBuyData();
                    }
                }
            }


        });
    }


    @SuppressLint("CheckResult")
    private void PutBuyData() {

        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("sellOrderNo", advertisingvo.getId());
        requestParam.put("dealCNYE", edtZongjia.getText().toString().trim());
        edtZongjia.setText("");

        ApiFactory.getInstance()
                .shopsell(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == false) {
                        Toast.makeText(SellDetelisActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("OrderId", data.getData().getId());
                        intent.setClass(SellDetelisActivity.this, WaitPayActivity.class);
                        startActivity(intent);
                        finish();
                        mMyDialog.dismiss();
                        Toast.makeText(SellDetelisActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                });
    }

    class TimeCountJava extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */


        public TimeCountJava(long millisInFuture, long countDownInterval) {
            //参数依次为总时长,和计时的时间间隔
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            dataTime = "60min";
        }
    }


    class TextWatcher1 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (flag) {
                flag = false;
                String price = edtZongjia.getText().toString().trim();
                if (!price.equals("")) {
                    BigDecimal str = new BigDecimal(price).divide(advertisingvo.getTradePrice().multiply(advertisingvo.getPremium()), 10, BigDecimal.ROUND_HALF_UP);
                    edtNuber.setText(str.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
                    zongjiage.setText(price);
                } else {
                    edtNuber.setText("");
                    zongjiage.setText("");
                }
            } else {
                flag = true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    class TextWatcher2 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (flag) {
                flag = false;
                String nuber = edtNuber.getText().toString().trim();
                if (!nuber.equals("")) {
                    BigDecimal danjia = advertisingvo.getTradePrice().multiply(advertisingvo.getPremium());
                    BigDecimal b2 = new BigDecimal(nuber).multiply(danjia.setScale(2, BigDecimal.ROUND_HALF_UP));
                    edtZongjia.setText(b2.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
                    String price = edtZongjia.getText().toString().trim();
                    zongjiage.setText(price);
                } else {
                    edtZongjia.setText("");
                    zongjiage.setText("");
                }

            } else {
                flag = true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
