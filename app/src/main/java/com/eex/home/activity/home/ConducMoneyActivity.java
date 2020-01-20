package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.adapter.ConducMoneyAdapter;
import com.eex.home.bean.ConducMoney;
import com.eex.home.bean.ConducMoneydetails;
import com.eex.home.bean.YesListData;
import com.eex.home.weight.Utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @ClassName: ConducMoneyActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 17:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 17:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * <p>
 * 锁仓理财
 */
public class ConducMoneyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 产品名称
     */
    @BindView(R.id.tx_Biname)
    TextView txBiname;
    /**
     * 产品
     */
    @BindView(R.id.ll_noneyBi)
    LinearLayout llNoneyBi;
    /**
     * 请输入理财数量
     */
    @BindView(R.id.edt_BiNuber)
    EditText edtBiNuber;
    /**
     * 可用
     */
    @BindView(R.id.tx_Kyong)
    TextView txKyong;
    /**
     * 限额
     */
    @BindView(R.id.tx_Djie)
    TextView txDjie;
    /**
     * 请选择理财周期
     */
    @BindView(R.id.tx_MoneyTime)
    TextView txMoneyTime;
    /**
     * 理财周期
     */
    @BindView(R.id.ll_dialog)
    LinearLayout llDialog;
    /**
     * 固定日利率
     */
    @BindView(R.id.tx_day_fee)
    TextView txDayFee;
    /**
     * 日收益
     */
    @BindView(R.id.tx_persionMoney)
    TextView txPersionMoney;
    /**
     * 活动日利率
     */
    @BindView(R.id.tx_HDfee)
    TextView txHDfee;
    /**
     * 活动日期2018
     */
    @BindView(R.id.tx_feeTime)
    TextView txFeeTime;
    /**
     * ck1
     */
    @BindView(R.id.ck1)
    CheckBox ck1;
    /**
     * 到期后自动将本次理财本金和利息继续续约
     */
    @BindView(R.id.ll_ck)
    LinearLayout llCk;
    /**
     * ck2
     */
    @BindView(R.id.ck2)
    CheckBox ck2;
    /**
     * 到期后自动将本次理财本金继续续约
     */
    @BindView(R.id.ll_ck1)
    LinearLayout llCk1;
    /**
     * ck3
     */
    @BindView(R.id.ck3)
    CheckBox ck3;
    /**
     * 到期后不自动续约
     */
    @BindView(R.id.ll_ck2)
    LinearLayout llCk2;
    /**
     * 提示
     */
    @BindView(R.id.txnie)
    TextView txnie;
    /**
     * 确定锁仓
     */
    @BindView(R.id.btn_putMoney)
    Button btnPutMoney;
    /**
     *
     */
    @BindView(R.id.leverage_three)
    TextView leverageThree;
    /**
     *
     */
    @BindView(R.id.Financial_number)
    TextView FinancialNumber;
    /**
     *
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    private ArrayList<YesListData> list = new ArrayList<>();
    private ArrayList<ConducMoneydetails> conList = new ArrayList<>();

    private ConducMoneyAdapter adapter;
    /**
     * starTime :活动开始时间
     * endTime :活动结束时间
     */
    private String CycleID;
    private String MoneyNuber;

    private String starTime;
    private String endTime;

    private String starTime1;
    private String endTime1;

    private String currencyType;


    private String MoneyPword;

    private String conducMonies;

    private int Type = 0;

    private String nuber = "0";

    private String coinCode;


    /**
     * 活动利率
     */
    private BigDecimal ActiveRate = new BigDecimal(0);
    /**
     * 活动开始时间
     */
    private String ActiveStartTime = null;
    /**
     * 活动结束时间
     */
    private String ActiveEndTime = null;
    /**
     * 固定日利率
     */
    private BigDecimal FixedDailyRate = new BigDecimal("0");

    private Dialog dialog;

    private BigDecimal shou;
    private BigDecimal xin;
    private BigDecimal bi;
    private BigDecimal suoc;
    private BigDecimal profit;

    private ConducMoney<ConducMoneydetails> conducMoneydetails;

    //声明一个子线程
    private Thread newThread;

    private long TotalPrice;

    private boolean cabin = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_conduc_money;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.moneysuo));
        comtitlebar.setImageView(R.drawable.rili);

        //锁仓查询理财币种
        getLockMoneyCoin();

        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setClass(ConducMoneyActivity.this, MoneyListActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 锁仓查询理财币种
     */
    @SuppressLint("CheckResult")
    private void getLockMoneyCoin() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .getLockMoneyCoin(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getData() != null) {
                        list.clear();
                        list.addAll(pageData.getData());
                        if (list != null && list.size() != 0) {
                            txBiname.setText(list.get(0).getCoinCode());
                            //提现验证码提交接口
                            depositcoin();
                            //查询理财配置
                            getLockingConfigByCoinCode(list);
                        }

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }


    /**
     * 提现验证码提交接口
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void depositcoin() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", txBiname.getText().toString().trim());
        ApiFactory.getInstance()
                .getcoinaccountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getCode() == 10002 || pageData.getCode() == 10001) {
                        intent = new Intent();
                        intent.putExtra("type", "2");
                        intent.setClass(ConducMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (pageData.getData() != null) {

                        txKyong.setText("可用:" + pageData.getData().getCoinMoney().setScale(6, BigDecimal.ROUND_DOWN) + pageData.getData().getCoinCode());

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    /**
     * 查询理财配置
     *
     * @param list
     */
    @SuppressLint("CheckResult")
    private void getLockingConfigByCoinCode(ArrayList<YesListData> list) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", list.get(0).getCoinCode());
        ApiFactory.getInstance()
                .getLockingConfigByCoinCode(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (pageData.getData() != null) {
                        conducMoneydetails = pageData.getData();

                        coinCode = pageData.getData().getCoinCode();
                        conducMonies = pageData.getData().getId();
                        conList.clear();
                        conList.addAll(pageData.getData().getCycleConfigVOList());


                        //用自己i选择币种
                        setBiNewView(pageData.getData());

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    private void init() {


        edtBiNuber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    if (conducMoneydetails == null) {

                    } else {
                        if (leverageThree.equals("0")) {
                            leverageThree.setText("杠杆" + "X" + "0");
                            FinancialNumber.setText(Integer.valueOf(edtBiNuber.getText().toString().trim()));
                        } else {
                            leverageThree.setText("杠杆" + "X" + conducMoneydetails.getLevers() + "");
                            FinancialNumber.setText(conducMoneydetails.getLevers() * Long.parseLong(edtBiNuber.getText().toString().trim()) + conducMoneydetails.getCoinCode());
                            TotalPrice = Long.parseLong(edtBiNuber.getText().toString().trim());

                        }
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }


    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.comtitlebar, R.id.ll_noneyBi, R.id.edt_BiNuber, R.id.ll_dialog, R.id.ll_ck, R.id.ll_ck1, R.id.ll_ck2, R.id.btn_putMoney})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.ll_noneyBi:
                intent.setClass(ConducMoneyActivity.this, YesBiListActivity.class);
                startActivityForResult(intent, 2000);
                break;
            case R.id.edt_BiNuber:
                break;
            case R.id.ll_dialog:
                if (edtBiNuber.getText().toString().trim().equals("")) {
                    Toast.makeText(ConducMoneyActivity.this, "请输入理财数量", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txBiname.getText().toString().trim().equals("")) {
                    Toast.makeText(ConducMoneyActivity.this, "请选择币种", Toast.LENGTH_SHORT).show();
                    return;
                }


                Dialog();

                break;
            case R.id.ll_ck:
                Type = 1;
                ck1.setChecked(true);
                ck2.setChecked(false);
                ck3.setChecked(false);
                break;
            case R.id.ll_ck1:
                Type = 2;
                ck1.setChecked(false);
                ck2.setChecked(true);
                ck3.setChecked(false);
                break;
            case R.id.ll_ck2:
                Type = 3;
                ck1.setChecked(false);
                ck2.setChecked(false);
                ck3.setChecked(true);
                break;
            //确定锁仓
            case R.id.btn_putMoney:

                if (cabin == true) {

                    if (txBiname.getText().toString().trim().equals("")) {
                        Toast.makeText(ConducMoneyActivity.this, "请选择币种", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (edtBiNuber.getText().toString().trim().equals("")) {
                        Toast.makeText(ConducMoneyActivity.this, "请输入理财数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (txMoneyTime.getText().toString().trim().equals("请选择理财周期")) {
                        Toast.makeText(ConducMoneyActivity.this, "请选择理财周期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Type == 0) {
                        Toast.makeText(ConducMoneyActivity.this, "请选择续约规则", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    DataSM();
                } else {
                    return;
                }

                break;
            default:
                break;
        }
    }

    /**
     * 确定锁仓
     */
    @SuppressLint("CheckResult")
    private void DataSM() {
        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getauthstauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getData() != null) {

                        if (pageData.getCode() == 10002 || pageData.getCode() == 10001) {

                            intent.putExtra("type", "1");
                            intent.setClass(ConducMoneyActivity.this, LoginActivity.class);
                            startActivity(intent);
                            t(getActivity().getResources().getString(R.string.loginno));
                        }
                        try {
                            if (pageData.getData().getLevel().equals(1)) {
                                t("请实名认证等级2后操作");
                            } else if (pageData.getData().getLevel().equals(2)) {
                                if (kv.decodeString("phone") != null) {
                                    //验证交易密码
                                    dialog();
                                } else {
                                    t("请绑定手机号后操作");
                                }

                            } else if (pageData.getData().getLevel().equals(3)) {
                                if (kv.decodeString("phone") != null) {
                                    //验证交易密码
                                    dialog();
                                } else {
                                    t("请绑定手机号后操作");
                                }
                            }
                        } catch (Exception e) {
                            t(getActivity().getResources().getString(R.string.isname));
                        }

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 2000) {
                currencyType = data.getStringExtra("name");
                txBiname.setText(currencyType);

                //提现验证码提交接口
                getcoinaccountinfo();
                //获取用户自己选择的币种理财配置
                getFinancingNewTime();
            }
        }
    }


    /**
     * 提现验证码提交接口
     */
    @SuppressLint("CheckResult")
    private void getcoinaccountinfo() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", currencyType);
        ApiFactory.getInstance()
                .getcoinaccountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.getCode() == 10002 || pageData.getCode() == 10001) {

                        intent.putExtra("type", "1");
                        intent.setClass(ConducMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        t(getActivity().getResources().getString(R.string.loginno));
                    }

                    if (pageData.getData() != null) {

                        txKyong.setText("可用:" + pageData.getData().getCoinMoney().setScale(6, BigDecimal.ROUND_DOWN) + pageData.getData().getCoinCode());

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }


    /**
     *
     */
    @SuppressLint("CheckResult")
    private void getFinancingNewTime() {

        ActiveRate = new BigDecimal(0);
        ActiveStartTime = null;
        ActiveEndTime = null;
        BigDecimal FixedDailyRate = new BigDecimal("0");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", currencyType);
        ApiFactory.getInstance()
                .getLockingConfigByCoinCode(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {
                    if (pageData.getData() != null) {
                        conducMoneydetails = pageData.getData();

                        coinCode = pageData.getData().getCoinCode();
                        conducMonies = pageData.getData().getId();
                        conList.clear();
                        conList.addAll(pageData.getData().getCycleConfigVOList());

                        //用自己i选择币种
                        setBiNewView(pageData.getData());
                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    /**
     * 用自己i选择币种
     *
     * @param pageData
     */

    private void setBiNewView(ConducMoney<ConducMoneydetails> pageData) {


        for (int i = 0; i < conducMoneydetails.getCycleConfigVOList().size(); i++) {

            //已锁数量 大于等于 总锁仓数量  锁仓结束：
            if (conducMoneydetails.getCycleConfigVOList().get(i).getLockAlreadyCount().compareTo(conducMoneydetails.getCycleConfigVOList().get(i).getLockCount()) > -1) {

                cabin = false;
                btnPutMoney.setText("锁仓已结束");
                btnPutMoney.setBackgroundColor(getActivity().getResources().getColor(R.color.edt_gray));
            } else {
                try {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //结束时间 && 开始时间==null 立即锁仓
                    if (conducMoneydetails.getCycleConfigVOList().get(i).getActiveEndTime() == null && conducMoneydetails.getCycleConfigVOList().get(i).getActiveStartTime() == null) {
                        cabin = true;
                        btnPutMoney.setText("立即锁仓");
                        btnPutMoney.setBackgroundColor(getActivity().getResources().getColor(R.color.lime));
                        //超过活动结束时间
                    } else if (sdf.parse(getTime()).getTime() > sdf.parse(conducMoneydetails.getCycleConfigVOList().get(i).getActiveEndTime()).getTime()) {
                        cabin = false;
                        btnPutMoney.setText("锁仓已结束");
                        btnPutMoney.setBackgroundColor(getActivity().getResources().getColor(R.color.edt_gray));
                        //未到活动时间
                    } else if (sdf.parse(getTime()).getTime() < sdf.parse(conducMoneydetails.getCycleConfigVOList().get(i).getActiveStartTime()).getTime()) {
                        cabin = false;
                        btnPutMoney.setText("未开始");
                        btnPutMoney.setBackgroundColor(getActivity().getResources().getColor(R.color.btn_two));
                        //立即锁仓
                    } else {
                        cabin = true;
                        btnPutMoney.setText("立即锁仓");
                        btnPutMoney.setBackgroundColor(getActivity().getResources().getColor(R.color.lime));
                    }
                } catch (Exception e) {

                }
            }
        }


        init();
        MoneyNuber = edtBiNuber.getText().toString().trim();

        //限额
        txDjie.setText("限额:" + pageData.getMinNum().stripTrailingZeros().toPlainString() + "-" + pageData.getMaxNum().stripTrailingZeros().toPlainString() + pageData.getCoinCode());


        //请选择理财周期
        if (pageData.getCycleConfigVOList().get(0).getFinancialCycleYear() != 0) {
            CycleID = pageData.getCycleConfigVOList().get(0).getId();
            txMoneyTime.setText(pageData.getCycleConfigVOList().get(0).getFinancialCycleYear() + "");
        } else if (pageData.getCycleConfigVOList().get(0).getFinancialCycle() != 0) {
            CycleID = pageData.getCycleConfigVOList().get(0).getId();
            txMoneyTime.setText(pageData.getCycleConfigVOList().get(0).getFinancialCycle() + "");
        } else {
            CycleID = pageData.getCycleConfigVOList().get(0).getId();
            txMoneyTime.setText(pageData.getCycleConfigVOList().get(0).getFinancialCycleMonth() + "");
        }

        //到期后自动将本次理财本金和利息继续续约
        if (pageData.getReturnFeeType() == 2) {
            llCk.setVisibility(View.VISIBLE);
            txnie.setText("提示：确认" + pageData.getCoinCode() + "锁仓理财后,对应的本金和利息将在合约期内将被锁定,发起解仓后该货币可重新用于交易");
        } else {
            txnie.setText("提示：确认" + pageData.getCoinCode() + "锁仓理财后,对应的本金在合约期内将被锁定,利息每日发放到资金账户,发起解仓后该货币可重新用于交易");
            llCk.setVisibility(View.GONE);
        }
        //
        starTime = pageData.getCycleConfigVOList().get(0).getActiveStartTime();
        endTime = pageData.getCycleConfigVOList().get(0).getActiveEndTime();
        //


        //设置活动利率是否显示
        if (starTime == null || endTime == null) {
            //没有活动
            txDayFee.setText("固定日利率:" + pageData.getCycleConfigVOList().get(0).getFixedDailyRate().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_DOWN) + "%");
            txPersionMoney.setText("日收益:0" + pageData.getCoinCode());
            if (MoneyNuber.equals("")) {
            } else {
                if (pageData.getCycleConfigVOList().get(0).getActiveRate() == null) {
                    pageData.getCycleConfigVOList().get(0).setActiveRate(new BigDecimal("0"));
                }
                if (pageData.getCycleConfigVOList().get(0).getFixedDailyRate() == null) {
                    pageData.getCycleConfigVOList().get(0).setFixedDailyRate(new BigDecimal("0"));
                }
                BigDecimal fee = pageData.getCycleConfigVOList().get(0).getActiveRate().add(pageData.getCycleConfigVOList().get(0).getFixedDailyRate());
                BigDecimal fee1 = fee.multiply(new BigDecimal(MoneyNuber));
                txPersionMoney.setText("日收益:" + fee1.stripTrailingZeros().toPlainString() + pageData.getCoinCode());

            }

            txFeeTime.setVisibility(View.GONE);
            txHDfee.setVisibility(View.GONE);
        } else {
            //有活动
            txFeeTime.setVisibility(View.VISIBLE);
            txHDfee.setVisibility(View.VISIBLE);
            txHDfee.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            if (pageData.getCycleConfigVOList().get(0).getActiveStartTime().indexOf(" ") != -1) {
                starTime1 = pageData.getCycleConfigVOList().get(0).getActiveStartTime().substring(0, pageData.getCycleConfigVOList().get(0).getActiveStartTime().indexOf(" "));
                starTime1.replace("-", ".");
            } else {
                starTime1 = pageData.getCycleConfigVOList().get(0).getActiveStartTime();
                starTime1.replace("-", ".");
            }

            if (pageData.getCycleConfigVOList().get(0).getActiveEndTime().indexOf(" ") != -1) {
                endTime1 = pageData.getCycleConfigVOList().get(0).getActiveEndTime().substring(0, pageData.getCycleConfigVOList().get(0).getActiveEndTime().indexOf(" "));
                endTime1.replace("-", ".");
            } else {
                endTime1 = pageData.getCycleConfigVOList().get(0).getActiveEndTime();
                endTime1.replace("-", ".");
            }
            txHDfee.setText("固定日利率:" + pageData.getCycleConfigVOList().get(0).getFixedDailyRate().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_DOWN) + "%");
            txFeeTime.setText("活动日期 " + starTime1 + "--" + endTime1);
            txDayFee.setText("活动日利率:" + pageData.getCycleConfigVOList().get(0).getActiveRate().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_DOWN) + "%");

            if (MoneyNuber.equals("")) {
                txPersionMoney.setText("日收益:0" + pageData.getCoinCode());
            } else {
                BigDecimal fee = pageData.getCycleConfigVOList().get(0).getActiveRate();
                BigDecimal fee1 = fee.multiply(new BigDecimal(MoneyNuber));
                txPersionMoney.setText("日收益:" + fee1.stripTrailingZeros().toPlainString() + pageData.getCoinCode());
            }
        }


    }


    /**
     * 验证交易密码
     */
    private void dialog() {
        dialog = new Dialog(ConducMoneyActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(ConducMoneyActivity.this).inflate(R.layout.dialog_money, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final EditText edt_YZM = (EditText) dialogView.findViewById(R.id.edt_Pword);
        final Button btn_YZM = (Button) dialogView.findViewById(R.id.btn_yes);
        dialog.show();
        btn_YZM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_YZM.getText().toString().trim().equals("")) {
                    Toast.makeText(ConducMoneyActivity.this, "请输入交易密码", Toast.LENGTH_SHORT).show();
                } else {
                    //交易密码
                    MoneyPword = edt_YZM.getText().toString().trim();
                    edt_YZM.setText("");
                    //查询理财配置
                    saveLockingMoney();
                }

            }

        });
    }

    /**
     * 查询理财配置
     */
    @SuppressLint("CheckResult")
    private void saveLockingMoney() {

        HashMap<String, String> hashMap = new HashMap<>();
        //交易密码
        hashMap.put("accountPassWord", Utils.md5(MoneyPword + "hello, moto"));
        //理财币种
        hashMap.put("coinCode", txBiname.getText().toString().trim());
        //输入的币种数量
//        hashMap.put("coinMoney", String.valueOf(TotalPrice));
        hashMap.put("coinMoney", edtBiNuber.getText().toString().trim());
        //选择的周期
        hashMap.put("financialcycleConfigId", CycleID);
        //根据币种选择配置id
        hashMap.put("lockAssetsConfigId", conducMonies + "");
        //cekbox选项标
        hashMap.put("type", Type + "");

        ApiFactory.getInstance()
                .saveLockingMoney(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(pageData -> {

                    if (pageData.isStauts() == true) {

                        dialog.dismiss();
                        intent.setClass(ConducMoneyActivity.this, MoneyListActivity.class);
                        startActivity(intent);
                        t(pageData.getMsg());

                    } else {
                        t(pageData.getMsg());
                    }
                }, throwable -> {

                });
    }

    private void Dialog() {

        final Dialog dialog = new Dialog(ConducMoneyActivity.this, R.style.ActionSheetDialogStyle);
        View dialogView = LayoutInflater.from(ConducMoneyActivity.this).inflate(R.layout.dialog_condmoney, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);

        RecyclerView dialog_RecyclerView = (RecyclerView) dialogView.findViewById(R.id.dialog_RecyclerView);
        dialog_RecyclerView.setLayoutManager(new LinearLayoutManager(ConducMoneyActivity.this));
        adapter = new ConducMoneyAdapter(R.layout.item_conduc_money, conList);
        dialog_RecyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tx_day:

                        CycleID = conList.get(position).getId();
                        txMoneyTime.setText(conList.get(position).getFinancialCycle() + "天");
                        ActiveRate = conList.get(position).getActiveRate();
                        ActiveStartTime = conList.get(position).getActiveStartTime();
                        ActiveEndTime = conList.get(position).getActiveEndTime();
                        FixedDailyRate = conList.get(position).getFixedDailyRate();

                        if (conducMoneydetails.getCoinCodeProfit().equals("USDT")) {

                            if (leverageThree.equals("0")) {
                                leverageThree.setText("杠杆" + "X" + "0");
                                FinancialNumber.setText(Integer.valueOf(edtBiNuber.getText().toString().trim()));
                            } else {
                                leverageThree.setText("杠杆" + "X" + conducMoneydetails.getLevers() + "");
                                FinancialNumber.setText(conducMoneydetails.getLevers() * Long.parseLong(edtBiNuber.getText().toString().trim()) + conducMoneydetails.getCoinCode());
                                TotalPrice = Long.parseLong(edtBiNuber.getText().toString().trim());

                            }

                            if (edtBiNuber.getText().toString() == null) {
                                txPersionMoney.setText("日收益:" + "0" + conducMoneydetails.getCoinCodeProfit());
                                return;
                            } else {
                                //均价
                                BigDecimal avPrice;
                                //收益最新价
                                BigDecimal newPrice;

                                if (conducMoneydetails.getCoinCode().equals("USDT")) {
                                    avPrice = BigDecimal.valueOf(1);
                                } else {
                                    avPrice = conducMoneydetails.getCoinCodeIwm().getHigePrice().add(conducMoneydetails.getCoinCodeIwm().getFooPrice());
                                    avPrice = avPrice.divide(BigDecimal.valueOf(2), 8, BigDecimal.ROUND_DOWN);


                                }

                                if (conducMoneydetails.getCoinCodeProfit().equals("USDT")) {
                                    newPrice = BigDecimal.valueOf(1);
                                } else {
                                    newPrice = conducMoneydetails.getCoinCodeIwm().getNewPrice();
                                }

                                //new BigDecimal(edtBiNuber.getText().toString())
                                //收益率
                                try {
                                    if (conList.get(position).getActiveRate() != null) {
                                        shou = conList.get(position).getActiveRate();

                                    } else {
                                        shou = conList.get(position).getFixedDailyRate();
                                    }
                                } catch (Exception e) {

                                }

                                //倍数  写着好看 纯属没有用
                                BigDecimal beishu = new BigDecimal(conducMoneydetails.getLevers());
                                beishu.setScale(2, BigDecimal.ROUND_HALF_UP);

                                llCk.setVisibility(View.GONE);

                                profit = avPrice.multiply(new BigDecimal(TotalPrice)).multiply(new BigDecimal(conducMoneydetails.getLevers())).multiply(shou).divide(newPrice, 8, BigDecimal.ROUND_DOWN);
                                txPersionMoney.setText("日收益:" + profit.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + conducMoneydetails.getCoinCodeProfit());


                            }

                        } else {
                            if (ActiveStartTime == null || ActiveEndTime == null) {
                                //没有活动
                                txFeeTime.setVisibility(View.GONE);
                                txHDfee.setVisibility(View.GONE);
                                BigDecimal fee = conList.get(position).getFixedDailyRate();
                                nuber = edtBiNuber.getText().toString().trim();
                                if (nuber.equals("")) {
                                    nuber = "0";
                                }
                                BigDecimal fee1 = fee.multiply(new BigDecimal(nuber));
                                txPersionMoney.setText("日收益:" + fee1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coinCode);
                                txDayFee.setText("固定日利率:" + conList.get(position).getFixedDailyRate().stripTrailingZeros().toPlainString() + "%");

                            } else {
                                //有活动
                                txFeeTime.setVisibility(View.VISIBLE);
                                txHDfee.setVisibility(View.VISIBLE);
                                if (conList.get(position).getActiveStartTime().indexOf(" ") != -1) {
                                    starTime1 = conList.get(position).getActiveStartTime().substring(0, conList.get(position).getActiveStartTime().indexOf(" "));
                                    starTime1.replace("-", ".");
                                } else {
                                    starTime1 = conList.get(position).getActiveStartTime();
                                    starTime1.replace("-", ".");
                                }

                                if (conList.get(position).getActiveEndTime().indexOf(" ") != -1) {
                                    endTime1 = conList.get(position).getActiveEndTime().substring(0, conList.get(position).getActiveEndTime().indexOf(" "));
                                    endTime1.replace("-", ".");
                                } else {
                                    endTime1 = conList.get(position).getActiveEndTime();
                                    endTime1.replace("-", ".");
                                }
                                txHDfee.setText("固定日利率:" + conList.get(position).getFixedDailyRate().stripTrailingZeros().toPlainString() + "%");
                                txFeeTime.setText("活动日期 " + starTime1 + "--" + endTime1);
                                txDayFee.setText("活动日利率:" + conList.get(position).getActiveRate().stripTrailingZeros().toPlainString() + "%");
//                            if (text.equals("")) {
//                                tx_persionMoney.setText("日收益:0"+moneyVo.getData().getCoinCode());
//                            } else {
                                nuber = edtBiNuber.getText().toString().trim();
                                if (nuber.equals("")) {
                                    nuber = "0";
                                }
                                BigDecimal fee = conList.get(position).getActiveRate();
                                BigDecimal fee1 = fee.multiply(new BigDecimal(nuber));
                                txPersionMoney.setText("日收益:" + fee1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coinCode);
//                            }
                            }
                        }


                        dialog.dismiss();
                        break;
                    case R.id.tx_month:


                        CycleID = conList.get(position).getId();
                        txMoneyTime.setText(conList.get(position).getFinancialCycleMonth() + "个月");
                        ActiveRate = conList.get(position).getActiveRate();
                        ActiveStartTime = conList.get(position).getActiveStartTime();
                        ActiveEndTime = conList.get(position).getActiveEndTime();
                        FixedDailyRate = conList.get(position).getFixedDailyRate();

                        if (conducMoneydetails.getCoinCodeProfit().equals("USDT")) {

                            if (leverageThree.equals("0")) {
                                leverageThree.setText("杠杆" + "X" + "0");
                                FinancialNumber.setText(Integer.valueOf(edtBiNuber.getText().toString().trim()));
                            } else {
                                leverageThree.setText("杠杆" + "X" + conducMoneydetails.getLevers() + "");
                                FinancialNumber.setText(conducMoneydetails.getLevers() * Long.parseLong(edtBiNuber.getText().toString().trim()) + conducMoneydetails.getCoinCode());
                                TotalPrice = Long.parseLong(edtBiNuber.getText().toString().trim());

                            }

                            if (edtBiNuber.getText().toString() == null) {
                                txPersionMoney.setText("日收益:" + "0" + conducMoneydetails.getCoinCodeProfit());
                                return;
                            } else {
                                //均价
                                BigDecimal avPrice;
                                //收益最新价
                                BigDecimal newPrice;

                                if (conducMoneydetails.getCoinCode().equals("USDT")) {
                                    avPrice = BigDecimal.valueOf(1);
                                } else {
                                    avPrice = conducMoneydetails.getCoinCodeIwm().getHigePrice().add(conducMoneydetails.getCoinCodeIwm().getFooPrice());
                                    avPrice = avPrice.divide(BigDecimal.valueOf(2), 8, BigDecimal.ROUND_DOWN);
                                }

                                if (conducMoneydetails.getCoinCodeProfit().equals("USDT")) {
                                    newPrice = BigDecimal.valueOf(1);
                                } else {
                                    newPrice = conducMoneydetails.getCoinCodeIwm().getNewPrice();
                                }

                                //new BigDecimal(edtBiNuber.getText().toString())
                                //收益率
                                try {
                                    if (position == 0) {
                                        if (conList.get(0).getActiveRate() != null) {
                                            shou = conList.get(0).getActiveRate();
                                        } else {
                                            shou = conList.get(0).getFixedDailyRate();
                                        }
                                    } else {
                                        if (conList.get(1).getActiveRate() != null) {
                                            shou = conList.get(1).getActiveRate();
                                        } else {
                                            shou = conList.get(1).getFixedDailyRate();
                                        }
                                    }
                                } catch (Exception e) {

                                }

                                //倍数  写着好看 纯属没有用
                                BigDecimal beishu = new BigDecimal(conducMoneydetails.getLevers());
                                beishu.setScale(2, BigDecimal.ROUND_HALF_UP);

                                llCk.setVisibility(View.GONE);


                                profit = avPrice.multiply(new BigDecimal(TotalPrice)).multiply(new BigDecimal(conducMoneydetails.getLevers())).multiply(shou).divide(newPrice, 8, BigDecimal.ROUND_DOWN);
                                txPersionMoney.setText("日收益:" + profit.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + conducMoneydetails.getCoinCodeProfit());


                            }
                        } else {
                            if (ActiveStartTime == null || ActiveEndTime == null) {
                                //没有活动
                                txFeeTime.setVisibility(View.GONE);
                                txHDfee.setVisibility(View.GONE);
//                            if (text1.equals("")) {
//                                tx_persionMoney.setText("日收益:0"+moneyVo.getData().getCoinCode());
//                            } else {
                                nuber = edtBiNuber.getText().toString().trim();
                                if (nuber.equals("")) {
                                    nuber = "0";
                                }
                                BigDecimal fee = conList.get(position).getFixedDailyRate();
                                BigDecimal fee1 = fee.multiply(new BigDecimal(nuber));
                                txPersionMoney.setText("日收益:" + fee1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coinCode);
                                txDayFee.setText("固定日利率:" + conList.get(position).getFixedDailyRate().stripTrailingZeros().toPlainString() + "%");
//                            }

                            } else {
                                //有活动
                                txFeeTime.setVisibility(View.VISIBLE);
                                txHDfee.setVisibility(View.VISIBLE);
                                if (conList.get(position).getActiveStartTime().indexOf(" ") != -1) {
                                    starTime1 = conList.get(position).getActiveStartTime().substring(0, conList.get(position).getActiveStartTime().indexOf(" "));
                                    starTime1.replace("-", ".");
                                } else {
                                    starTime1 = conList.get(position).getActiveStartTime();
                                    starTime1.replace("-", ".");
                                }

                                if (conList.get(position).getActiveEndTime().indexOf(" ") != -1) {
                                    endTime1 = conList.get(position).getActiveEndTime().substring(0, conList.get(position).getActiveEndTime().indexOf(" "));
                                    endTime1.replace("-", ".");
                                } else {
                                    endTime1 = conList.get(position).getActiveEndTime();
                                    endTime1.replace("-", ".");
                                }
                                txHDfee.setText("固定日利率:" + conList.get(position).getFixedDailyRate().stripTrailingZeros().toPlainString() + "%");
                                txFeeTime.setText("活动日期 " + starTime1 + "--" + endTime1);
                                txDayFee.setText("活动日利率:" + conList.get(position).getActiveRate().stripTrailingZeros().toPlainString() + "%");

                                nuber = edtBiNuber.getText().toString().trim();
                                if (nuber.equals("")) {
                                    nuber = "0";
                                }
                                BigDecimal fee = conList.get(position).getActiveRate();
                                BigDecimal fee1 = fee.multiply(new BigDecimal(nuber));
                                txPersionMoney.setText("日收益:" + fee1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coinCode);
//                            }
                            }
                        }


                        dialog.dismiss();
                        break;
                    case R.id.tx_year:


                        CycleID = conList.get(position).getId();
                        txMoneyTime.setText(conList.get(position).getFinancialCycleYear() + "年");
                        ActiveRate = conList.get(position).getActiveRate();
                        ActiveStartTime = conList.get(position).getActiveStartTime();
                        ActiveEndTime = conList.get(position).getActiveEndTime();
                        FixedDailyRate = conList.get(position).getFixedDailyRate();
                        if (conducMoneydetails.getCoinCodeProfit().equals("USDT")) {
                            if (edtBiNuber.getText().toString() == null) {

                                if (leverageThree.equals("0")) {
                                    leverageThree.setText("杠杆" + "X" + "0");
                                    FinancialNumber.setText(Integer.valueOf(edtBiNuber.getText().toString().trim()));
                                } else {
                                    leverageThree.setText("杠杆" + "X" + conducMoneydetails.getLevers() + "");
                                    FinancialNumber.setText(conducMoneydetails.getLevers() * Long.parseLong(edtBiNuber.getText().toString().trim()) + conducMoneydetails.getCoinCode());
                                    TotalPrice = Long.parseLong(edtBiNuber.getText().toString().trim());

                                }

                                txPersionMoney.setText("日收益:" + "0" + conducMoneydetails.getCoinCodeProfit());
                                return;
                            } else {
                                //均价
                                BigDecimal avPrice;
                                //收益最新价
                                BigDecimal newPrice;

                                if (conducMoneydetails.getCoinCode().equals("USDT")) {
                                    avPrice = BigDecimal.valueOf(1);
                                } else {
                                    avPrice = conducMoneydetails.getCoinCodeIwm().getHigePrice().add(conducMoneydetails.getCoinCodeIwm().getFooPrice());
                                    avPrice = avPrice.divide(BigDecimal.valueOf(2), 8, BigDecimal.ROUND_DOWN);
                                }

                                if (conducMoneydetails.getCoinCodeProfit().equals("USDT")) {
                                    newPrice = BigDecimal.valueOf(1);
                                } else {
                                    newPrice = conducMoneydetails.getCoinCodeIwm().getNewPrice();
                                }

                                //new BigDecimal(edtBiNuber.getText().toString())
                                //收益率
                                try {
                                    if (position == 0) {
                                        if (conList.get(0).getActiveRate() != null) {
                                            shou = conList.get(0).getActiveRate();
                                        } else {
                                            shou = conList.get(0).getFixedDailyRate();
                                        }
                                    } else {
                                        if (conList.get(1).getActiveRate() != null) {
                                            shou = conList.get(1).getActiveRate();
                                        } else {
                                            shou = conList.get(1).getFixedDailyRate();
                                        }
                                    }
                                } catch (Exception e) {

                                }

                                //倍数  写着好看 纯属没有用
                                BigDecimal beishu = new BigDecimal(conducMoneydetails.getLevers());
                                beishu.setScale(2, BigDecimal.ROUND_HALF_UP);

                                llCk.setVisibility(View.GONE);


                                profit = avPrice.multiply(new BigDecimal(TotalPrice)).multiply(new BigDecimal(conducMoneydetails.getLevers())).multiply(shou).divide(newPrice, 8, BigDecimal.ROUND_DOWN);
                                txPersionMoney.setText("日收益:" + profit.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + conducMoneydetails.getCoinCodeProfit());


                            }

                        } else {
                            if (ActiveStartTime == null || ActiveEndTime == null) {
                                //没有活动
                                txFeeTime.setVisibility(View.GONE);
                                txHDfee.setVisibility(View.GONE);
                                nuber = edtBiNuber.getText().toString().trim();
                                if (nuber.equals("")) {
                                    nuber = "0";
                                }
                                BigDecimal fee = conList.get(position).getFixedDailyRate();
                                BigDecimal fee1 = fee.multiply(new BigDecimal(nuber));
                                txPersionMoney.setText("日收益:" + fee1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coinCode);
                                txDayFee.setText("固定日利率:" + conList.get(position).getFixedDailyRate().stripTrailingZeros().toPlainString() + "%");


                            } else {
                                //有活动
                                txFeeTime.setVisibility(View.VISIBLE);
                                txHDfee.setVisibility(View.VISIBLE);
                                if (conList.get(position).getActiveStartTime().indexOf(" ") != -1) {
                                    starTime1 = conList.get(position).getActiveStartTime().substring(0, conList.get(position).getActiveStartTime().indexOf(" "));
                                    starTime1.replace("-", ".");
                                } else {
                                    starTime1 = conList.get(position).getActiveStartTime();
                                    starTime1.replace("-", ".");
                                }

                                if (conList.get(position).getActiveEndTime().indexOf(" ") != -1) {
                                    endTime1 = conList.get(position).getActiveEndTime().substring(0, conList.get(position).getActiveEndTime().indexOf(" "));
                                    endTime1.replace("-", ".");
                                } else {
                                    endTime1 = conList.get(position).getActiveEndTime();
                                    endTime1.replace("-", ".");
                                }
                                txHDfee.setText("固定日利率:" + conList.get(position).getFixedDailyRate().stripTrailingZeros().toPlainString() + "%");
                                txFeeTime.setText("活动日期 " + starTime1 + "--" + endTime1);
                                txDayFee.setText("活动日利率:" + conList.get(position).getActiveRate().stripTrailingZeros().toPlainString() + "%");

                                nuber = edtBiNuber.getText().toString().trim();
                                if (nuber.equals("")) {
                                    nuber = "0";
                                }
                                BigDecimal fee = conList.get(position).getActiveRate();
                                BigDecimal fee1 = fee.multiply(new BigDecimal(nuber));
                                txPersionMoney.setText("日收益:" + fee1.setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + coinCode);

                            }
                        }

                        dialog.dismiss();
                        break;
                    default:
                        break;
                }


            }
        });
        dialog.show();
    }

    @Override
    public void onRefresh() {
        //锁仓查询理财币种
        getLockMoneyCoin();
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    private String getTime() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        return date;
    }
}

