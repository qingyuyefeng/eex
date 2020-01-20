package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;

import com.eex.common.view.ComTitleBar;
import com.eex.home.bean.SecondkillDetails;

import net.tsz.afinal.FinalBitmap;

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
 * @ClassName: SecondkillDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 11:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 11:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 秒杀详情
 *
 */
public class SecondkillDetailsActivity extends BaseActivity {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 币种图片
     */
    @BindView(R.id.img_biName)
    ImageView imgBiName;
    /**
     * 币种
     */
    @BindView(R.id.Biname)
    TextView Biname;
    /**
     * 币种说明
     */
    @BindView(R.id.tx_biname)
    TextView txBiname;
    /**
     * 秒杀单价
     */
    @BindView(R.id.secodkill_singlePrice)
    TextView secodkillSinglePrice;
    /**
     * 几折
     */
    @BindView(R.id.tx_zhekou)
    TextView txZhekou;
    /**
     * 市场价格
     */
    @BindView(R.id.secodkill_marketPrice)
    TextView secodkillMarketPrice;
    /**
     * 锁定日期
     */
    @BindView(R.id.secodkill_Nuber)
    TextView secodkillNuber;
    /**
     * 秒杀倒计时
     */
    @BindView(R.id.downTime)
    TextView downTime;
    /**
     * 秒杀剩余数量
     */
    @BindView(R.id.KillNuber)
    TextView KillNuber;
    /**
     * 抢购进度
     */
    @BindView(R.id.pb_progressbar)
    ProgressBar pbProgressbar;
    /**
     * 已抢购0
     */
    @BindView(R.id.tx_yigou)
    TextView txYigou;
    /**
     * 抢购数量-
     */
    @BindView(R.id.btn_jian)
    Button btnJian;
    /**
     * 数量
     */
    @BindView(R.id.edt_nuber)
    EditText edtNuber;
    /**
     * 抢购数量+
     */
    @BindView(R.id.btn_jia)
    Button btnJia;
    /**
     * 冻结时间1
     */
    @BindView(R.id.ckbox)
    CheckBox ckbox;
    /**
     * 冻结时间2
     */
    @BindView(R.id.tx_leb)
    TextView txLeb;
    /**
     * 立即抢购
     */
    @BindView(R.id.btn_go)
    Button btnGo;


    /**
     *
     */
    private String BIiname;
    private String id;
    private String ImgUrl;
    private FinalBitmap fb;

    /**
     * day:天
     * hour:时
     * minute:分
     * second:秒
     */
    private CountDownTimer data;
    private long day;
    private long hour;
    private long minute;
    private long second;


    /**
     *
     */
    private String number;
    private String reduction;
    private SecondkillDetails list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_secondkill_details;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("秒杀详情");

        if (getIntent().getStringExtra("Biname") != null) {
            BIiname = getIntent().getStringExtra("Biname");
        }
        if (getIntent().getStringExtra("id") != null) {
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().getStringExtra("ImgUrl") != null) {
            ImgUrl = getIntent().getStringExtra("ImgUrl");
        }

        //获取秒杀配置
        findSecondKillOrderConfigById();
    }

    /**
     * 获取秒杀配置
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void findSecondKillOrderConfigById() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .findSecondKillOrderConfigById(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    if (pageData.getData() != null) {

                        list = pageData.getData();
                        //币种说明
                        txBiname.setText("(" + BIiname + ")");
                        //几折
                        txZhekou.setText(pageData.getData().getSecKillDiscount().multiply(new BigDecimal(10)).stripTrailingZeros().toPlainString() + "折");
                        //币种图片
                        fb = FinalBitmap.create(getApplicationContext());
                        fb.configLoadingImage(R.drawable.iconjiazaishibai);
                        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
                        fb.display(imgBiName, WPConfig.PicBaseUrl + ImgUrl + "");
                        //冻结时间
                        String str3 = String.format("我已知解冻时间为:<font color=\"#ff4329\">" + pageData.getData().getUnlockTime() + "</font>,如果提前解冻只能到账<font color=#ff4329>" + pageData.getData().getSecKillValue().multiply(new BigDecimal(edtNuber.getText().toString().trim())).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT</font>,合约期满则可到账<font color=\"#ff4329\">" + edtNuber.getText().toString().trim() + pageData.getData().getCoinCode() + "</font>");
                        txLeb.setText(Html.fromHtml(str3));
                        //已抢购0
                        txYigou.setText("已抢购" + (pageData.getData().getSecKillCurrentNum().divide(pageData.getData().getSecKillTotalNum(), 10, BigDecimal.ROUND_CEILING)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN) + "%");
                        //抢购进度
                        pbProgressbar.setMax(pageData.getData().getSecKillTotalNum().intValue());
                        pbProgressbar.setProgress(pageData.getData().getSecKillCurrentNum().intValue());
                        //秒杀剩余数量
                        KillNuber.setText("秒杀剩余数量:" + pageData.getData().getSecKillCurrentNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "/" + pageData.getData().getSecKillTotalNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        //锁定期
                        secodkillNuber.setText("锁定期: " + pageData.getData().getLockTime() + "天");
                        //市场价格
                        secodkillMarketPrice.setText("市场价格: " + pageData.getData().getMarketValue().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        //币种
                        Biname.setText(pageData.getData().getCoinCode());
                        //秒杀单价
                        secodkillSinglePrice.setText("秒杀单价: " + pageData.getData().getSecKillValue().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "USDT");
                        //

                        data = new CountDownTimer(pageData.getData().getSecKillEndTimeMil() - pageData.getData().getCurrentTimeMillis(), 1000) {
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
                                downTime.setText("秒杀倒计时:" + hour + ":" + minute + ":" + second);
                            }

                            @Override
                            public void onFinish() {
                                downTime.setText("秒杀倒计时:" + "00:00:00");
                            }
                        };
                        data.start();
                        //
                        //
                        //
                        //
                        //


                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

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
    }

    @OnClick({R.id.comtitlebar, R.id.btn_jian, R.id.btn_jia, R.id.ckbox, R.id.btn_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            //-
            case R.id.btn_jian:
                reduction = edtNuber.getText().toString().trim();
                if (Float.parseFloat(reduction) - 1 < 0) {

                } else {
                    Float money1 = Float.parseFloat(reduction) - 1;
                    edtNuber.setText(money1.toString());
                }
                break;
            //+
            case R.id.btn_jia:
                if (edtNuber.getText().toString().trim().equals("")) {
                    edtNuber.setText(0 + 1);
                } else {
                    number = edtNuber.getText().toString().trim();
                    Float money = Float.parseFloat(number) + 1;
                    edtNuber.setText(money.toString());
                }

                break;

            //立即抢购
            case R.id.btn_go:


                number = edtNuber.getText().toString().trim();
                //判断用户输入是否为null
                if (TextUtils.isEmpty(number)) {
                    CommonUtil.showSingleToast("请输入数量");
                    return;
                }
                if (Float.parseFloat(edtNuber.getText().toString().trim()) <= 0) {
                    t("请选择抢购数量");
                    return;
                }

                if (ckbox.isChecked() == false) {
                    t("请勾选抢购须知");
                    return;
                }

                saveSecondOrder(number);
                break;

            default:
                break;
        }
    }

    /**
     * 秒杀提交
     *
     * @param number
     */
    @SuppressLint("CheckResult")
    private void saveSecondOrder(String number) {


        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("secKillOrderConfigId", id);
        hashMap.put("secKillNum", number);
        edtNuber.setText("0");
        ApiFactory.getInstance()
                .saveSecondOrder(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getCode() == 200) {
                        t(pageData.getMsg());
                        intent.putExtra("type", "成功");
                        intent.putExtra("code", "200");
                        intent.putExtra("nuber", list.getSecKillValue().multiply(new BigDecimal(number)).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        intent.setClass(getApplicationContext(), SecondkillTypeActivity.class);
                        startActivityForResult(intent, 2000);

                    } else if (pageData.getCode() == 4012) {
                        t(pageData.getMsg());
                        intent.putExtra("type", "失败");
                        intent.putExtra("code", "4012");
                        intent.putExtra("nuber", list.getSecKillValue().multiply(new BigDecimal(number)).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        intent.setClass(SecondkillDetailsActivity.this, SecondkillTypeActivity.class);
                        startActivityForResult(intent, 2000);

                    } else if (pageData.getCode() == 4013) {
                        t(pageData.getMsg());
                        intent.putExtra("type", "失败");
                        intent.putExtra("code", "4013");
                        intent.putExtra("nuber", list.getSecKillValue().multiply(new BigDecimal(number)).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        intent.setClass(SecondkillDetailsActivity.this, SecondkillTypeActivity.class);
                        startActivityForResult(intent, 2000);
                    } else if (pageData.getCode() == 4014) {
                        t(pageData.getMsg());
                    } else if (pageData.getCode() == 4015) {
                        t(pageData.getMsg());
                        intent.putExtra("type", "余额不足");
                        intent.putExtra("code", "4015");
                        intent.putExtra("nuber", list.getSecKillValue().multiply(new BigDecimal(number)).setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        intent.setClass(SecondkillDetailsActivity.this, SecondkillTypeActivity.class);
                        startActivityForResult(intent, 2000);
                    } else if (pageData.getCode() == 4016) {
                        t(pageData.getMsg());

                    } else if (pageData.getCode() == 4017) {
                        t(pageData.getMsg());
                        intent.putExtra("type", "失败");
                        intent.putExtra("code", "4017");
                        intent.setClass(SecondkillDetailsActivity.this, SecondkillTypeActivity.class);
                        startActivityForResult(intent, 2000);
                    } else if (pageData.getCode() == 4018) {
                        t(pageData.getMsg());
                    } else if (pageData.getCode() == 4019) {
                        t(pageData.getMsg());
                        intent.putExtra("type", "失败");
                        intent.putExtra("code", "4019");
                        intent.setClass(SecondkillDetailsActivity.this, SecondkillTypeActivity.class);
                        startActivityForResult(intent, 2000);
                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            finish();
        }
    }
}
