package com.eex.market.frgament.donation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.CommonUtil;
import com.eex.common.websocket.RxWebSocket;
import com.eex.common.websocket.WebSocketSubscriber2;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.bean.MainData;
import com.eex.home.bean.MainList;
import com.eex.home.weight.MyDialog;
import com.eex.home.weight.Utils;
import com.eex.market.adpater.NewDealAdapter;
import com.eex.market.adpater.PurchaseFramentBuyAdapter;
import com.eex.market.adpater.purchaseFramentAdapter;
import com.eex.market.bean.Buy;
import com.eex.market.bean.CnySize;
import com.eex.market.bean.PurchaseDatalIst;
import com.eex.market.bean.PurchaseDta;
import com.eex.market.bean.Sell;
import com.eex.market.bean.bIDataVO;
import com.eex.market.frgament.text.DepthDataBean;
import com.eex.market.frgament.text.DepthMapView;
import com.eex.market.weight.DecimalDigitsInputFilter;
import com.eex.market.weight.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;


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
 * @Package: com.overthrow.market.frgament
 * @ClassName: MarketSellFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 17:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 17:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 卖出
 */
public class MarketSellFragment extends BaseFragment {


    /**
     * 限单价
     */
    @BindView(R.id.tx_Namexianjia)
    TextView txNamexianjia;
    /**
     * 市价单
     */
    @BindView(R.id.tx_Nameshijia)
    TextView txNameshijia;
    /**
     * 止盈止损
     */
    @BindView(R.id.tx_NameZhiyin)
    TextView txNameZhiyin;
    /**
     * 溢价交易
     */
    @BindView(R.id.tx_premium)
    TextView txPremium;
    /**
     *
     */
    @BindView(R.id.LL_Fixed_price)
    LinearLayout LLFixedPrice;
    /**
     *
     */
    @BindView(R.id.XJPriceBtnSub)
    Button XJPriceBtnSub;
    /**
     *
     */
    @BindView(R.id.edt_XDPrice)
    EditText edtXDPrice;
    /**
     *
     */
    @BindView(R.id.XJPriceBtnAdd)
    Button XJPriceBtnAdd;
    /**
     *
     */
    @BindView(R.id.TXRMBPrice)
    TextView TXRMBPrice;
    /**
     *
     */
    @BindView(R.id.XJNuberBtnSub)
    Button XJNuberBtnSub;
    /**
     *
     */
    @BindView(R.id.edt_XDnuber)
    EditText edtXDnuber;
    /**
     *
     */
    @BindView(R.id.XJNuberBtnAdd)
    Button XJNuberBtnAdd;
    /**
     *
     */
    @BindView(R.id.btnXD1)
    Button btnXD1;
    /**
     *
     */
    @BindView(R.id.btnXD2)
    Button btnXD2;
    /**
     *
     */
    @BindView(R.id.btnXD3)
    Button btnXD3;
    /**
     *
     */
    @BindView(R.id.btnXD4)
    Button btnXD4;
    /**
     *
     */
    @BindView(R.id.edt_XJMoney)
    EditText edtXJMoney;
    /**
     *
     */
    @BindView(R.id.edit_premium)
    EditText editpremium;
    /**
     *
     */
    @BindView(R.id.LLMoney)
    LinearLayout LLMoney;
    /**
     *
     */
    @BindView(R.id.LLXJD)
    LinearLayout LLXJD;
    /**
     *
     */
    @BindView(R.id.LLSJ)
    LinearLayout LLSJ;
    /**
     *
     */
    @BindView(R.id.SJNuberBtnSub)
    Button SJNuberBtnSub;
    /**
     *
     */
    @BindView(R.id.edt_SJnuber)
    EditText edtSJnuber;
    /**
     *
     */
    @BindView(R.id.SJNuberBtnAdd)
    Button SJNuberBtnAdd;
    /**
     *
     */
    @BindView(R.id.SJBtn1)
    Button SJBtn1;
    /**
     *
     */
    @BindView(R.id.SJBtn2)
    Button SJBtn2;
    /**
     *
     */
    @BindView(R.id.SJBtn3)
    Button SJBtn3;
    /**
     *
     */
    @BindView(R.id.SJBtn4)
    Button SJBtn4;
    /**
     *
     */
    @BindView(R.id.LLSJD)
    LinearLayout LLSJD;
    /**
     *
     */
    @BindView(R.id.ZYZSCfBtnPriceSub)
    Button ZYZSCfBtnPriceSub;
    /**
     *
     */
    @BindView(R.id.EDT_ZYZSCFPrice)
    EditText EDTZYZSCFPrice;
    /**
     *
     */
    @BindView(R.id.ZYZSCfBtnPriceAdd)
    Button ZYZSCfBtnPriceAdd;
    /**
     *
     */
    @BindView(R.id.TXZSZYMoneyPrice)
    TextView TXZSZYMoneyPrice;
    /**
     *
     */
    @BindView(R.id.ZYZSWtBtnPriceSub)
    Button ZYZSWtBtnPriceSub;
    /**
     *
     */
    @BindView(R.id.edt_ZYZSPrice)
    EditText edtZYZSPrice;
    /**
     *
     */
    @BindView(R.id.ZYZSWtBtnPriceAdd)
    Button ZYZSWtBtnPriceAdd;
    /**
     *
     */
    @BindView(R.id.ZSZYMoneyWTPrice)
    TextView ZSZYMoneyWTPrice;
    /**
     *
     */
    @BindView(R.id.ZYZSWtBtnNuberSub)
    Button ZYZSWtBtnNuberSub;
    /**
     *
     */
    @BindView(R.id.edt_ZYZSnuber)
    EditText edtZYZSnuber;
    /**
     *
     */
    @BindView(R.id.ZYZSWtBtnNuberAdd)
    Button ZYZSWtBtnNuberAdd;
    /**
     *
     */
    @BindView(R.id.ZYZSBtn1)
    Button ZYZSBtn1;
    /**
     *
     */
    @BindView(R.id.ZYZSBtn2)
    Button ZYZSBtn2;
    /**
     *
     */
    @BindView(R.id.ZYZSBtn3)
    Button ZYZSBtn3;
    /**
     *
     */
    @BindView(R.id.ZYZSBtn4)
    Button ZYZSBtn4;
    /**
     *
     */
    @BindView(R.id.LLZYZSD)
    LinearLayout LLZYZSD;
    /**
     * 溢价单
     */
    @BindView(R.id.LLYJD_premium)
    LinearLayout LLYJDPremium;
    /**
     * 溢价单
     */
    @BindView(R.id.LLYJpremium)
    LinearLayout LLYJpremium;

    /**
     * 委托数量
     */
    @BindView(R.id.edt_YJNnuber)
    EditText edtYJNnuber;


    /**
     * 25%
     */
    @BindView(R.id.YJNBtn1)
    Button YJNBtn1;
    /**
     * 50%
     */
    @BindView(R.id.YJNBtn2)
    Button YJNBtn2;

    /**
     * 75%
     */
    @BindView(R.id.YJNBtn3)
    Button YJNBtn3;

    /**
     * 100%
     */
    @BindView(R.id.YJNBtn4)
    Button YJNBtn4;


    /**
     *
     */
    @BindView(R.id.tx_usdt)
    TextView txUsdt;
    /**
     *
     */
    @BindView(R.id.tx_cny)
    TextView txCny;
    /**
     *
     */
    @BindView(R.id.btn_sell)
    Button btnBuy;
    /**
     *
     */
    @BindView(R.id.TypeText)
    TextView TypeText;
    /**
     *
     */
    @BindView(R.id.LLbuysell)
    LinearLayout LLbuysell;
    /**
     *
     */
    @BindView(R.id.depth_view)
    DepthMapView depthView;
    /**
     *
     */
    @BindView(R.id.listview_sell1)
    ListView listviewSell1;
    /**
     * 最新成交价点击
     */
    @BindView(R.id.textviewPurMoney1)
    TextView textviewPurMoney1;
    /**
     *
     */
    @BindView(R.id.textviewPurMoney2)
    TextView textviewPurMoney2;
    /**
     *
     */
    @BindView(R.id.listview_buy1)
    ListView listviewBuy1;
    /**
     * 切换和资金流向
     */
    @BindView(R.id.tx_type)
    TextView txType;
    /**
     * 切换和资金流向
     */
    @BindView(R.id.MoneyWater)
    TextView MoneyWater;
    /**
     *
     */
    @BindView(R.id.img_type)
    ImageView imgType;
    /**
     *
     */
    @BindView(R.id.rlgone)
    RelativeLayout rlgone;
    /**
     *
     */
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    /**
     *
     */
    @BindView(R.id.listview)
    ListView recyclerView;
    /**
     *
     */
    @BindView(R.id.LLList)
    LinearLayout LLList;
    /**
     *
     */
    @BindView(R.id.webDialog)
    WebView webDialog;
    /**
     *
     */
    Unbinder unbinder;


    private String LLtype = "1";

    private List<Sell> sellList = new ArrayList<>();
    private List<Buy> buyList = new ArrayList<>();

    private List<Sell> sells = new ArrayList<>();
    private List<Buy> buys = new ArrayList<>();

    /**
     * 用户可用币种数量
     */
    private String Nube;


    private BigDecimal pricNuber;
    private BigDecimal quantityNuber;
    private BigDecimal newprice = BigDecimal.ZERO;
    private Integer type = 1;
    private String PurName = "";
    public static String BINAME = "BTC";
    public static String name = null;
    public static String name1 = "BTC_USDT";
    public static String BINAMEDATA = "USDT";


    private ArrayList<PurchaseDatalIst> datalIsts = new ArrayList<PurchaseDatalIst>();
    private NewDealAdapter newDealAdapter;
    private PurchaseDta root;
    private purchaseFramentAdapter adapter;
    private PurchaseFramentBuyAdapter buyAdapter;


    private ArrayList<MainList> mainLists = new ArrayList<>();
    private BigDecimal MIN = BigDecimal.ZERO;
    private BigDecimal MAX = BigDecimal.ZERO;

    private String MoneyType = "YES";
    public static String Type = "YES";
    private ArrayList<CnySize> list = new ArrayList<CnySize>();

    private ArrayList<MainData> mainData = new ArrayList<>();

    /**
     * dialog
     */
    private View view1;
    private TextView Tltle;
    private Button btnDimss;
    private Button btnSell;
    /**
     * mMyDialog
     */
    private MyDialog mMyDialog;


    public static EditText XDPrice;
    public static EditText XDnuber;
    public static EditText XJMoney;
    public static EditText SJnuber;
    public static EditText ZYZSCFPrice;
    public static EditText ZYZSPrice;
    public static EditText ZYZSnuber;
    public static EditText YJSnuber;


    private String ktYong = "";
    public static int OTHER;

    /**
     * 饼状图 穿的参数
     */
    private String CashFlow;

    @Override
    protected void lazyLoad() {

        if (name1.equals("GLS_USDT") || name1.equals("AJA_USDT")||name1.equals("VZCC_USDT")) {
            LLXJD.setVisibility(View.GONE);
            LLSJD.setVisibility(View.GONE);
            LLZYZSD.setVisibility(View.GONE);
            LLYJDPremium.setVisibility(View.VISIBLE);
            txPremium.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
            txNamexianjia.setVisibility(View.GONE);
            txNameshijia.setVisibility(View.GONE);
            txNameZhiyin.setVisibility(View.GONE);
            txPremium.setVisibility(View.VISIBLE);
            LLtype = "4";
        }
        staticdata();
//        sellState 为1 是限制卖出
        sellState1();
        sellState2();
        sellState3();


        getID();
        //获取最新成交价
        net();
        //长连接
        SocketData();
        //交易大厅List
        getTickMaket();
        //获取系统小数位数
        getBiListData();

        //币资产信息
        WebSocket();

        if (kv.decodeString("tokenId") != null) {
            //获取用户可用币数量
            getMoney();
        }
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        if (name1.equals("GLS_USDT") || name1.equals("AJA_USDT")||name1.equals("VZCC_USDT")) {

            LLXJD.setVisibility(View.GONE);
            LLSJD.setVisibility(View.GONE);
            LLZYZSD.setVisibility(View.GONE);
            LLYJDPremium.setVisibility(View.VISIBLE);
            txPremium.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
            txNamexianjia.setVisibility(View.GONE);
            txNameshijia.setVisibility(View.GONE);
            txNameZhiyin.setVisibility(View.GONE);
            txPremium.setVisibility(View.VISIBLE);
            LLtype = "4";
        }
        staticdata();
//        sellState 为1 是限制卖出
        sellState1();
        sellState2();
        sellState3();


        getID();
        //获取最新成交价
        net();
        //长连接
        SocketData();
        //交易大厅List
        getTickMaket();
        //获取系统小数位数
        getBiListData();

        //币资产信息
        WebSocket();

        if (kv.decodeString("tokenId") != null) {
            //获取用户可用币数量
            getMoney();
        }
    }

    @SuppressLint("CheckResult")
    private void staticdata() {
        ApiFactory.getInstance()
                .staticdata()
                .compose(RxSchedulers.io_main())
                .subscribe(coindata -> {
                    if (coindata != null) {

                        for (int i = 0; i < coindata.getList().size(); i++) {
                            //取得最后一个/的下标
                            int index = coindata.getList().get(i).toString().lastIndexOf("=");
                            //将字符串转为字符数组
                            char[] ch = coindata.getList().get(i).toString().toCharArray();
                            //根据 copyValueOf(char[] data, int offset, int count) 取得最后一个字符串
                            String informationId = String.copyValueOf(ch, index + 1, ch.length - index - 2);

                            String informationId1 = coindata.getList().get(i).toString().substring(1, coindata.getList().get(i).toString().indexOf("="));

                            if (BINAME.equals(informationId1)) {
                                CashFlow = informationId;
                            }
                        }
//                        Log.e(TAG, "卖: " + CashFlow );
                    }
                }, throwable -> {

                });
    }

    /**
     * sellState 为1 是限制卖出
     */
    @SuppressLint("CheckResult")
    private void sellState1() {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pricingCoin", "PAX");
        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    try {
                        if (data.isStauts() == true) {

                            for (int i = 0; i < data.getData().size(); i++) {
                                if (data.getData().get(i).getDealPair().equals(name1)) {
                                    if (data.getData().get(i).getSellState() == 1) {
                                        btnBuy.setBackgroundColor(CommonUtil.getColor(R.color.grey));
                                        btnBuy.setEnabled(false);
                                    }

                                }
                            }

                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {

                });
    }

    /**
     * USDT  sellState 为1 是限制卖出
     */
    @SuppressLint("CheckResult")
    private void sellState2() {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pricingCoin", "USDT");
        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    try {
                        if (data.isStauts() == true) {

                            for (int i = 0; i < data.getData().size(); i++) {


                                if (data.getData().get(i).getDealPair().equals(name1)) {
                                    if (data.getData().get(i).getSellState() == 1) {
                                        btnBuy.setBackgroundColor(CommonUtil.getColor(R.color.grey));
                                        btnBuy.setEnabled(false);
                                    }

                                }
                            }

                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {

                });
    }

    /**
     * CNYE 为1 是限制卖出
     */
    @SuppressLint("CheckResult")
    private void sellState3() {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pricingCoin", "CNYE");
        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    try {
                        if (data.isStauts() == true) {

                            for (int i = 0; i < data.getData().size(); i++) {
                                if (data.getData().get(i).getDealPair().equals(name1)) {
                                    if (data.getData().get(i).getSellState() == 1) {
                                        btnBuy.setBackgroundColor(CommonUtil.getColor(R.color.grey));
                                        btnBuy.setEnabled(false);
                                    }

                                }
                            }

                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {

                });
    }

    private void getID() {


        XDPrice = edtXDPrice;
        XDnuber = edtXDnuber;
        XJMoney = edtXJMoney;
        SJnuber = edtSJnuber;
        YJSnuber = edtYJNnuber;
        ZYZSCFPrice = EDTZYZSCFPrice;
        ZYZSPrice = edtZYZSPrice;
        ZYZSnuber = edtZYZSnuber;

        webDialog.loadUrl("file:///android_asset/pie-nest.html");
        webDialog.getSettings().setJavaScriptEnabled(true);
        webDialog.getSettings().setBuiltInZoomControls(false);
        webDialog.getSettings().setSupportZoom(false);
        webDialog.getSettings().setDisplayZoomControls(false);
        //水平不显示
        webDialog.setHorizontalScrollBarEnabled(false);
        //垂直不显示
        webDialog.setVerticalScrollBarEnabled(false);

        txType.setTextColor(CommonUtil.getColor(R.color.appbar_background3));

        listviewBuy1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (LLtype.equals("1")) {
                    edtXDPrice.setText(buyList.get(position).getDelAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString());
                } else if (LLtype.equals("3")) {
                    edtZYZSPrice.setText(buyList.get(position).getDelAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString());
                }
            }
        });


        listviewSell1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (LLtype.equals("1")) {
                    edtXDPrice.setText(sellList.get(position).getDelAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString());
                } else if (LLtype.equals("3")) {
                    edtZYZSPrice.setText(sellList.get(position).getDelAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString());
                }
            }
        });

        edtXDPrice.addTextChangedListener(new TextWatcher() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                //输入过程中执行该方法", "文字变化
                try {
                    if (edtXDPrice.getText().toString().trim().equals("")) {
                        TXRMBPrice.setText("估值 ￥0");
                        edtXJMoney.setText("");
                    } else {
                        BigDecimal b1 = new BigDecimal(edtXDPrice.getText().toString().trim());
                        BigDecimal RMB = mainData.get(0).getUsdtCny();
                        BigDecimal USDT = datalIsts.get(0).getDealPrice();
                        BigDecimal NewGuzi = RMB.divide(USDT, 10, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_DOWN);
                        TXRMBPrice.setText("估值 ￥" + NewGuzi.multiply(b1).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        BigDecimal b2 = new BigDecimal(edtXDnuber.getText().toString().trim());
                        BigDecimal b3 = b1.multiply(b2);
                        //成交价
                        edtXJMoney.setText(b3.toString());
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听
                //输入前确认执行该方法", "开始输入

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                //输入结束执行该方法", "输入结束"
            }
        });


        edtXDnuber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                //输入过程中执行该方法", "文字变化

//                if (edtXDnuber.getText().toString().trim() >ktYong)
                try {
                    BigDecimal b1 = new BigDecimal(edtXDPrice.getText().toString().trim());
                    BigDecimal b2 = new BigDecimal(edtXDnuber.getText().toString().trim());
                    BigDecimal b3 = b1.multiply(b2);
                    edtXJMoney.setText(b3.toString());
                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听
                //输入前确认执行该方法", "开始输入

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                //输入结束执行该方法", "输入结束"

            }
        });


        EDTZYZSCFPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (EDTZYZSCFPrice.getText().toString().trim().equals("")) {
                        TXZSZYMoneyPrice.setText("估值 ￥0");
                    } else {
                        BigDecimal b1 = new BigDecimal(EDTZYZSCFPrice.getText().toString().trim());
                        BigDecimal RMB = mainData.get(0).getUsdtCny();
                        BigDecimal USDT = datalIsts.get(0).getDealPrice();
                        BigDecimal NewGuzi = RMB.divide(USDT, 10, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_DOWN);
                        TXZSZYMoneyPrice.setText("估值 ￥" + NewGuzi.multiply(b1).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtZYZSPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (edtZYZSPrice.getText().toString().trim().equals("")) {
                        ZSZYMoneyWTPrice.setText("估值 ￥0");
                    } else {
                        BigDecimal b1 = new BigDecimal(edtZYZSPrice.getText().toString().trim());
                        BigDecimal RMB = mainData.get(0).getUsdtCny();
                        BigDecimal USDT = datalIsts.get(0).getDealPrice();
                        BigDecimal NewGuzi = RMB.divide(USDT, 10, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_DOWN);
                        ZSZYMoneyWTPrice.setText("估值 ￥" + NewGuzi.multiply(b1).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void WebSocket() {

        try {
            RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinsocket/" + kv.decodeString("id"))
                    .compose(RxSchedulers.io_main())
                    .subscribe(new WebSocketSubscriber2<bIDataVO>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected void onMessage(bIDataVO bIDataVO) {

                            Log.e("Sell", "onMessage: " + bIDataVO.toString());
                            MoneyType = "NO";
                            if (bIDataVO != null) {
                                try {
                                    if (bIDataVO.getCoinCode().equals(BINAME)) {
                                        ktYong = bIDataVO.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString() + "";
                                        txUsdt.setText(bIDataVO.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString() + bIDataVO.getCoinCode() + "");
//                                        txUsdt.setText(bIDataVO.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + bIDataVO.getCoinCode() + "");
                                    }
                                    if (bIDataVO.getCoinCode().equals(BINAMEDATA)) {
                                        Nube = bIDataVO.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

                                        txCny.setText(bIDataVO.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + bIDataVO.getCoinCode() + "");
                                    }
                                } catch (Exception e) {

                                }

                            } else {

//                                Toast.makeText(getActivity(), "用户数据获取失败!", Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        protected void onReconnect() {

                            MoneyType = "YES";
                        }

                    });

        } catch (Exception e) {

        }
    }


    /**
     * 获取最新成交价
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("dealPair", name1);
        hashMap.put("size", "10");

        ApiFactory.getInstance()
                .TradingHall(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {


                    if (data != null) {
                        datalIsts.clear();
                        datalIsts.addAll(data.getData());
                        setView(datalIsts);
                        newDealAdapter.notifyDataSetChanged();
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }

    private void setView(ArrayList<PurchaseDatalIst> datalIsts) {

        if (getActivity() != null) {
            if (datalIsts != null && datalIsts.size() != 0) {
                newprice = datalIsts.get(0).getDealPrice().setScale(8, BigDecimal.ROUND_DOWN);
                textviewPurMoney1.setText(newprice.stripTrailingZeros().toPlainString());
            } else {
                textviewPurMoney1.setText("0");
            }

            newDealAdapter = new NewDealAdapter(datalIsts, getActivity());
            newDealAdapter.setDeviceList((ArrayList<PurchaseDatalIst>) datalIsts);
            recyclerView.setAdapter(newDealAdapter);
            newDealAdapter.notifyDataSetChanged();
            CommonUtil.setListViewHeightBasedOnChildren(recyclerView);


        }
    }

    /**
     * 交易大厅List
     */
    @SuppressLint("CheckResult")
    private void getTickMaket() {

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PurName = name1;

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("delkey", name1);

                    ApiFactory.getInstance()
                            .getTickMaket(hashMap)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {

                                root = data.getData();
                                if (data.isStauts() == true) {

                                    try {

                                        if (root.getListOrder().equals("")) {
                                        } else {
                                            if (root.getListOrder().get(root.getListOrder().size() - 1).getDealAmount() != null) {
                                                newprice = root.getListOrder().get(root.getListOrder().size() - 1).getDealAmount();
                                                textviewPurMoney1.setText(newprice.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());


                                            }
                                        }
                                    } catch (Exception e) {

                                    }


                                    if (data.getData().getSell() != null) {
                                        sellList.clear();
                                        sellList.addAll(data.getData().getSell());
                                        if (data.getData().getSell() != null && data.getData().getSell().size() != 0) {
                                            sellList.addAll(data.getData().getSell());
                                            if (data.getData().getSell().size() > 10) {
                                                sells.addAll(data.getData().getSell().subList(0, 10));
                                            } else {
                                                sells.addAll(data.getData().getSell());
                                            }

                                        }

                                    }

                                    if (data.getData().getBuy() != null) {
                                        buyList.clear();
                                        buyList.addAll(data.getData().getBuy());

                                        if (data.getData().getBuy() != null && data.getData().getBuy().size() != 0) {
                                            if (data.getData().getBuy().size() > 10) {
                                                buyList.addAll(data.getData().getBuy().subList(0, 10));
                                            } else {
                                                buyList.addAll(data.getData().getBuy());
                                            }
                                            buys.addAll(data.getData().getBuy());

                                        }


                                    }

                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Collections.reverse(buyList);
                                                Collections.reverse(sellList);
                                                updataView();
                                                //画深度图
                                                drowHightView();
                                                webDialog.loadUrl("javascript:getdata('" + BINAME + "')");
                                            }
                                        });
                                    }

                                } else {
                                    t(data.getMsg());
                                }


                            }, throwable -> {

                            });

                }
            });
        }
    }


    /**
     * 卖入adapter
     * purchaseFramentAdapter
     * <p>
     * 卖入和买入adapter
     * <p>
     * 买入adapter   PurchaseFramentBuyAdapter
     */
    private void updataView() {


        //卖入

        //Collections.reverse(sellmList);

        if (getActivity() != null) {
            adapter = new purchaseFramentAdapter(getActivity(), sellList);
            listviewSell1.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listviewSell1.requestLayout();
            try {
                View listItem = adapter.getView(0, null, listviewSell1);
                listItem.measure(0, 0);
                int totalHei = (listItem.getMeasuredHeight() + listviewSell1.getDividerHeight()) * 10;
                listviewSell1.getLayoutParams().height = totalHei;
            } catch (Exception e) {


            }
        }

//        买入

        if (getActivity() != null) {
            buyAdapter = new PurchaseFramentBuyAdapter(getActivity(), buys);
            listviewBuy1.setAdapter(buyAdapter);
            buyAdapter.notifyDataSetChanged();
            listviewBuy1.requestLayout();
            try {
                View listItem1 = adapter.getView(0, null, listviewBuy1);
                listItem1.measure(0, 0);
                int totalHei1 = (listItem1.getMeasuredHeight() + listviewBuy1.getDividerHeight()) * 10;
                listviewBuy1.getLayoutParams().height = totalHei1;
            } catch (Exception e) {

            }
        }


    }


    /**
     * 画深度图
     */
    private void drowHightView() {

        final List<DepthDataBean> listDepthBuy = new ArrayList<>();
        final List<DepthDataBean> listDepthSell = new ArrayList<>();
        DepthDataBean obj;
        DepthDataBean obj1;
        String price;
        String volume;
        try {
            if (buys != null && buys.size() > 0) {
                for (int i = 0; i < buys.size(); i++) {
                    obj = new DepthDataBean();
                    obj1 = new DepthDataBean();
                    price = buys.get(i).getDelAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString();
                    volume = buys.get(i).getResidueNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString();
                    obj.setVolume(Float.valueOf(volume));
                    obj.setPrice(Float.valueOf(price));
                    obj1.setVolume(Float.valueOf(volume));
                    obj1.setPrice(Float.valueOf(price));
                    listDepthBuy.add(obj);

                }
            }

            if (sellList != null && sellList.size() > 0) {
                for (int i = 0; i < sellList.size(); i++) {
                    obj = new DepthDataBean();
                    obj1 = new DepthDataBean();
                    price = sellList.get(i).getDelAmount().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString();
                    volume = sellList.get(i).getResidueNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).toString();
                    obj.setVolume(Float.valueOf(volume));
                    obj.setPrice(Float.valueOf(price));
                    obj1.setVolume(Float.valueOf(volume));
                    obj1.setPrice(Float.valueOf(price));
                    listDepthSell.add(obj);


                }
            }

            depthView.setData(listDepthBuy, listDepthSell);
        } catch (Exception e) {

        }

    }

    @Override
    protected void initUiAndListener() {


        if (getActivity() != null) {
            if (datalIsts != null && datalIsts.size() != 0) {
                newprice = datalIsts.get(0).getDealPrice().setScale(8, BigDecimal.ROUND_DOWN);
                textviewPurMoney1.setText(newprice.stripTrailingZeros().toPlainString());
            } else {
                textviewPurMoney1.setText("0");
            }

            newDealAdapter = new NewDealAdapter(datalIsts, getActivity());
            newDealAdapter.setDeviceList((ArrayList<PurchaseDatalIst>) datalIsts);
            recyclerView.setAdapter(newDealAdapter);
            newDealAdapter.notifyDataSetChanged();
            CommonUtil.setListViewHeightBasedOnChildren(recyclerView);

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market_sell;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @SuppressLint("SetTextI18n")
    @OnClick({R.id.tx_Namexianjia, R.id.tx_Nameshijia, R.id.tx_NameZhiyin, R.id.tx_premium, R.id.LL_Fixed_price, R.id.XJPriceBtnSub, R.id.edt_XDPrice, R.id.XJPriceBtnAdd,
            R.id.TXRMBPrice, R.id.XJNuberBtnSub, R.id.edt_XDnuber, R.id.XJNuberBtnAdd, R.id.btnXD1, R.id.btnXD2, R.id.btnXD3, R.id.btnXD4, R.id.edt_XJMoney,
            R.id.LLMoney, R.id.LLXJD, R.id.LLSJ, R.id.SJNuberBtnSub, R.id.edt_SJnuber, R.id.SJNuberBtnAdd, R.id.SJBtn1, R.id.SJBtn2, R.id.SJBtn3, R.id.SJBtn4,
            R.id.LLSJD, R.id.ZYZSCfBtnPriceSub, R.id.EDT_ZYZSCFPrice, R.id.ZYZSCfBtnPriceAdd, R.id.TXZSZYMoneyPrice, R.id.ZYZSWtBtnPriceSub, R.id.edt_ZYZSPrice,
            R.id.ZYZSWtBtnPriceAdd, R.id.ZSZYMoneyWTPrice, R.id.ZYZSWtBtnNuberSub, R.id.edt_ZYZSnuber, R.id.ZYZSWtBtnNuberAdd, R.id.ZYZSBtn1, R.id.ZYZSBtn2,
            R.id.ZYZSBtn3, R.id.ZYZSBtn4, R.id.LLZYZSD, R.id.tx_usdt, R.id.tx_cny, R.id.btn_sell, R.id.TypeText, R.id.LLbuysell, R.id.textviewPurMoney1,
            R.id.textviewPurMoney2, R.id.tx_type, R.id.MoneyWater, R.id.img_type, R.id.rlgone, R.id.ll_title, R.id.LLList, R.id.LLYJD_premium, R.id.LLYJpremium,
            R.id.edt_YJNnuber, R.id.YJNBtn1, R.id.YJNBtn2, R.id.YJNBtn3, R.id.YJNBtn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //限单价
            case R.id.tx_Namexianjia:
                edtSJnuber.setText("");

                EDTZYZSCFPrice.setText("");
                edtZYZSPrice.setText("");
                edtZYZSnuber.setText("");
                edtYJNnuber.setText("");
                txNamexianjia.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                txNameshijia.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txNameZhiyin.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txPremium.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                LLXJD.setVisibility(View.VISIBLE);
                LLSJD.setVisibility(View.GONE);
                LLZYZSD.setVisibility(View.GONE);
                LLYJDPremium.setVisibility(View.GONE);

                LLtype = "1";

                break;
            //市价单
            case R.id.tx_Nameshijia:
                edtXDPrice.setText("");
                edtXDnuber.setText("");
                edtXJMoney.setText("");

                EDTZYZSCFPrice.setText("");
                edtZYZSPrice.setText("");
                edtZYZSnuber.setText("");

                edtYJNnuber.setText("");
                txNamexianjia.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txNameshijia.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                txNameZhiyin.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txPremium.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                LLXJD.setVisibility(View.GONE);
                LLSJD.setVisibility(View.VISIBLE);
                LLZYZSD.setVisibility(View.GONE);
                LLYJDPremium.setVisibility(View.GONE);
                LLtype = "2";
                break;
            //止盈止损
            case R.id.tx_NameZhiyin:

                edtXDPrice.setText("");
                edtXDnuber.setText("");
                edtXJMoney.setText("");

                edtSJnuber.setText("");
                edtYJNnuber.setText("");
                txNamexianjia.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txNameshijia.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txNameZhiyin.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                txPremium.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                LLXJD.setVisibility(View.GONE);
                LLSJD.setVisibility(View.GONE);
                LLZYZSD.setVisibility(View.VISIBLE);
                LLYJDPremium.setVisibility(View.GONE);
                LLtype = "3";

                break;
            case R.id.tx_premium:
                edtXDPrice.setText("");
                edtXDnuber.setText("");
                edtXJMoney.setText("");

                EDTZYZSCFPrice.setText("");
                edtZYZSPrice.setText("");
                edtZYZSnuber.setText("");
                edtYJNnuber.setText("");
                txNamexianjia.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txNameshijia.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txNameZhiyin.setTextColor(getActivity().getResources().getColor(R.color.background_baise));
                txPremium.setTextColor(getActivity().getResources().getColor(R.color.appbar_background3));
                LLXJD.setVisibility(View.GONE);
                LLSJD.setVisibility(View.GONE);
                LLZYZSD.setVisibility(View.GONE);
                LLYJDPremium.setVisibility(View.VISIBLE);
                LLtype = "4";
                break;
            case R.id.LL_Fixed_price:
                break;
            //限单价价格减
            case R.id.XJPriceBtnSub:
                if (!edtXDPrice.getText().toString().trim().equals("")) {
                    if (new BigDecimal(edtXDPrice.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 0) {
                        edtXDPrice.setText("0");
                    }
                    if (new BigDecimal(edtXDPrice.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal price = new BigDecimal(edtXDPrice.getText().toString().trim());
                        BigDecimal subPrice = price.subtract(new BigDecimal(XJPriceSubMethod(edtXDPrice.getText().toString().trim())));
                        edtXDPrice.setText(subPrice.setScale(pricNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                }

                break;
            case R.id.edt_XDPrice:


                break;

            //限单价价格加
            case R.id.XJPriceBtnAdd:
                if (!edtXDnuber.getText().toString().trim().equals("")) {
                    BigDecimal price = new BigDecimal(edtXDnuber.getText().toString().trim());
                    BigDecimal subPrice = price.add(new BigDecimal(XJPriceSubMethod(edtXDnuber.getText().toString().trim())));
                    edtXDnuber.setText(subPrice.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    edtXDnuber.setText("1");
                }

                break;
            case R.id.TXRMBPrice:
                break;

            //限单价数量减
            case R.id.XJNuberBtnSub:
                if (!edtXDnuber.getText().toString().trim().equals("")) {
                    if (new BigDecimal(edtXDnuber.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 0) {
                        edtXDnuber.setText("0");
                    }
                    if (new BigDecimal(edtXDnuber.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal price = new BigDecimal(edtXDnuber.getText().toString().trim());
                        BigDecimal subPrice = price.subtract(new BigDecimal(XJPriceSubMethod(edtXDnuber.getText().toString().trim())));
                        edtXDnuber.setText(subPrice.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                }
                break;
            case R.id.edt_XDnuber:

                break;
            //限单价数量加
            case R.id.XJNuberBtnAdd:

                if (!edtXDnuber.getText().toString().trim().equals("")) {
                    BigDecimal price = new BigDecimal(edtXDnuber.getText().toString().trim());
                    BigDecimal subPrice = price.add(new BigDecimal(XJPriceSubMethod(edtXDnuber.getText().toString().trim())));
                    edtXDnuber.setText(subPrice.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    edtXDnuber.setText("1");
                }
                break;
            //25%
            case R.id.btnXD1:
                if (kv.decodeString("tokenId") != null) {
                    btnXD1.setBackgroundResource(R.drawable.backbtn);
                    btnXD2.setBackgroundResource(R.drawable.btn_grd);
                    btnXD3.setBackgroundResource(R.drawable.btn_grd);
                    btnXD4.setBackgroundResource(R.drawable.btn_grd);
                    if (edtXDPrice.getText().toString().trim() != null && !edtXDPrice.getText().toString().trim().equals("")) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube);
                            BigDecimal newNuber = NuberType.multiply(new BigDecimal("0.25"));
                            edtXDnuber.setText(newNuber.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {

                        }
                    } else {
                        edtXDnuber.setText("0");
                    }

                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }


                break;

            //50%
            case R.id.btnXD2:
                if (kv.decodeString("tokenId") != null) {
                    btnXD1.setBackgroundResource(R.drawable.btn_grd);
                    btnXD2.setBackgroundResource(R.drawable.backbtn);
                    btnXD3.setBackgroundResource(R.drawable.btn_grd);
                    btnXD4.setBackgroundResource(R.drawable.btn_grd);
                    if (edtXDPrice.getText().toString().trim() != null && !edtXDPrice.getText().toString().trim().equals("")) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube);
                            BigDecimal newNuber1 = NuberType.multiply(new BigDecimal("0.50"));

                            edtXDnuber.setText(newNuber1.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

                        } catch (Exception e) {

                        }
                    } else {
                        edtXDnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            //75%

            case R.id.btnXD3:
                if (kv.decodeString("tokenId") != null) {
                    btnXD1.setBackgroundResource(R.drawable.btn_grd);
                    btnXD2.setBackgroundResource(R.drawable.btn_grd);
                    btnXD3.setBackgroundResource(R.drawable.backbtn);
                    btnXD4.setBackgroundResource(R.drawable.btn_grd);
                    if (edtXDPrice.getText().toString().trim() != null && !edtXDPrice.getText().toString().trim().equals("")) {
                        try {

                            BigDecimal NuberType = new BigDecimal(Nube);
                            BigDecimal newNuber2 = NuberType.multiply(new BigDecimal("0.75"));

                            edtXDnuber.setText(newNuber2.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {

                        }
                    } else {
                        edtXDnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            //100%
            case R.id.btnXD4:

                Log.e(TAG, "onViewClicked: " + "  //100%");
                if (kv.decodeString("tokenId") != null) {
                    btnXD1.setBackgroundResource(R.drawable.btn_grd);
                    btnXD2.setBackgroundResource(R.drawable.btn_grd);
                    btnXD3.setBackgroundResource(R.drawable.btn_grd);
                    btnXD4.setBackgroundResource(R.drawable.backbtn);
                    if (edtXDPrice.getText().toString().trim() != null && !edtXDPrice.getText().toString().trim().equals("")) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube);
                            BigDecimal newNuber3 = NuberType.multiply(new BigDecimal("1"));
                            edtXDnuber.setText(newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

                        } catch (Exception e) {

                        }

                    } else {
                        edtXDnuber.setText("0");
                    }

//                    btnXD1.setBackgroundResource(R.drawable.btn_grd);
//                    btnXD2.setBackgroundResource(R.drawable.btn_grd);
//                    btnXD3.setBackgroundResource(R.drawable.btn_grd);
//                    btnXD4.setBackgroundResource(R.drawable.backbtn);
//                    if (edtXDPrice.getText().toString().trim() != null && !edtXDPrice.getText().toString().trim().equals("")) {
//                        BigDecimal NuberType = new BigDecimal(Nube).divide(new BigDecimal(edtXDPrice.getText().toString().trim()), 10, BigDecimal.ROUND_HALF_UP);
//                        BigDecimal newNuber3 = NuberType.multiply(new BigDecimal("1"));
//                        edtXDnuber.setText(newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
//                    } else {
//                        edtXDnuber.setText("0");
//                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edt_XJMoney:
                break;
            case R.id.LLMoney:
                break;
            case R.id.LLXJD:
                break;
            case R.id.LLSJ:
                break;
            //市价数量减
            case R.id.SJNuberBtnSub:
                if (!edtSJnuber.getText().toString().trim().equals("")) {
                    if (new BigDecimal(edtSJnuber.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 0) {
                        edtSJnuber.setText("0");
                    }
                    if (new BigDecimal(edtSJnuber.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal price = new BigDecimal(edtSJnuber.getText().toString().trim());
                        BigDecimal subPrice = price.subtract(new BigDecimal(XJPriceSubMethod(edtSJnuber.getText().toString().trim())));
                        edtSJnuber.setText(subPrice.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                }

                break;
            case R.id.edt_SJnuber:
                break;
            //市价数量加
            case R.id.SJNuberBtnAdd:
                if (!edtSJnuber.getText().toString().trim().equals("")) {
                    BigDecimal price = new BigDecimal(edtSJnuber.getText().toString().trim());
                    BigDecimal subPrice = price.add(new BigDecimal(XJPriceSubMethod(edtSJnuber.getText().toString().trim())));
                    edtSJnuber.setText(subPrice.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    edtSJnuber.setText("1");
                }

                break;
            case R.id.SJBtn1:
                if (kv.decodeString("tokenId") != null) {
                    SJBtn1.setBackgroundResource(R.drawable.backbtn);
                    SJBtn2.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn3.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn4.setBackgroundResource(R.drawable.btn_grd);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber1 = NuberType.multiply(new BigDecimal("0.25"));
                            if (newNuber1.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(ktYong)) == 1) {
                                edtSJnuber.setText(ktYong);
                            } else {
                                edtSJnuber.setText(newNuber1.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        edtSJnuber.setText("0");

                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SJBtn2:
                if (kv.decodeString("tokenId") != null) {
                    SJBtn1.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn2.setBackgroundResource(R.drawable.backbtn);
                    SJBtn3.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn4.setBackgroundResource(R.drawable.btn_grd);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber2 = NuberType.multiply(new BigDecimal("0.50"));
                            if (newNuber2.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(ktYong)) == 1) {
                                edtSJnuber.setText(ktYong);
                            } else {
                                edtSJnuber.setText(newNuber2.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        edtSJnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SJBtn3:
                if (kv.decodeString("tokenId") != null) {
                    SJBtn1.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn2.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn3.setBackgroundResource(R.drawable.backbtn);
                    SJBtn4.setBackgroundResource(R.drawable.btn_grd);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber3 = NuberType.multiply(new BigDecimal("0.75"));
                            if (newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(ktYong)) == 1) {
                                edtSJnuber.setText(ktYong);
                            } else {
                                edtSJnuber.setText(newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        edtSJnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SJBtn4:
                Log.e(TAG, "onViewClicked: " + "市价 100");
                if (kv.decodeString("tokenId") != null) {
                    SJBtn1.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn2.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn3.setBackgroundResource(R.drawable.btn_grd);
                    SJBtn4.setBackgroundResource(R.drawable.backbtn);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber4 = NuberType.multiply(new BigDecimal("1"));
                            if (newNuber4.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(ktYong)) == 1) {
                                edtSJnuber.setText(ktYong);
                            } else {
                                edtSJnuber.setText(newNuber4.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                            }
                        } catch (Exception e) {

                        }
                    } else {

                        edtSJnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.LLSJD:
                break;
            //触发价格减
            case R.id.ZYZSCfBtnPriceSub:

                if (!EDTZYZSCFPrice.getText().toString().trim().equals("")) {
                    if (new BigDecimal(EDTZYZSCFPrice.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 0) {
                        EDTZYZSCFPrice.setText("0");
                    }
                    if (new BigDecimal(EDTZYZSCFPrice.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal price = new BigDecimal(EDTZYZSCFPrice.getText().toString().trim());
                        BigDecimal subPrice = price.subtract(new BigDecimal(XJPriceSubMethod(EDTZYZSCFPrice.getText().toString().trim())));
                        EDTZYZSCFPrice.setText(subPrice.setScale(pricNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                }

                break;
            case R.id.EDT_ZYZSCFPrice:

                break;

            ////触发价格加
            case R.id.ZYZSCfBtnPriceAdd:
                if (!EDTZYZSCFPrice.getText().toString().trim().equals("")) {
                    BigDecimal price = new BigDecimal(EDTZYZSCFPrice.getText().toString().trim());
                    BigDecimal subPrice = price.add(new BigDecimal(XJPriceSubMethod(EDTZYZSCFPrice.getText().toString().trim())));
                    EDTZYZSCFPrice.setText(subPrice.setScale(pricNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    EDTZYZSCFPrice.setText("1");
                }

                break;
            case R.id.TXZSZYMoneyPrice:
                break;

            ////委托价格减
            case R.id.ZYZSWtBtnPriceSub:
                if (!edtZYZSPrice.getText().toString().trim().equals("")) {
                    if (new BigDecimal(edtZYZSPrice.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 0) {
                        edtZYZSPrice.setText("0");
                    }
                    if (new BigDecimal(edtZYZSPrice.getText().toString().trim()).compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal price = new BigDecimal(edtZYZSPrice.getText().toString().trim());
                        BigDecimal subPrice = price.subtract(new BigDecimal(XJPriceSubMethod(edtZYZSPrice.getText().toString().trim())));
                        edtZYZSPrice.setText(subPrice.setScale(pricNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    }

                }

                break;
            case R.id.edt_ZYZSPrice:

                break;
            case R.id.ZYZSWtBtnPriceAdd:


                break;
            case R.id.ZSZYMoneyWTPrice:
                break;
            case R.id.ZYZSWtBtnNuberSub:
                break;
            case R.id.edt_ZYZSnuber:
                break;
            case R.id.ZYZSWtBtnNuberAdd:
                if (!edtZYZSnuber.getText().toString().trim().equals("")) {
                    BigDecimal price = new BigDecimal(edtZYZSnuber.getText().toString().trim());
                    BigDecimal subPrice = price.add(new BigDecimal(XJPriceSubMethod(edtZYZSnuber.getText().toString().trim())));
                    edtZYZSnuber.setText(subPrice.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                } else {
                    edtZYZSnuber.setText("1");
                }

                break;
            case R.id.ZYZSBtn1:
                break;
            case R.id.ZYZSBtn2:
                break;
            case R.id.ZYZSBtn3:
                break;
            case R.id.ZYZSBtn4:
                break;
            case R.id.LLZYZSD:
                break;
            case R.id.tx_usdt:
                break;


            //溢价单
            case R.id.LLYJD_premium:
                break;
            //溢价单
            case R.id.LLYJpremium:
                break;
            // 溢价单 -委托数量
            case R.id.edt_YJNnuber:
                break;
            //  溢价单   25%
            case R.id.YJNBtn1:
                if (kv.decodeString("tokenId") != null) {
                    YJNBtn1.setBackgroundResource(R.drawable.backbtn);
                    YJNBtn2.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn3.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn4.setBackgroundResource(R.drawable.btn_grd);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber4 = new BigDecimal(ktYong).multiply(new BigDecimal("0.25"));
                            edtYJNnuber.setText(newNuber4.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {

                        }
//                        edtYJNnuber.setText(newNuber1.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {
                        edtYJNnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }

                break;
            //  溢价单   50%
            case R.id.YJNBtn2:
                if (kv.decodeString("tokenId") != null) {
                    YJNBtn1.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn2.setBackgroundResource(R.drawable.backbtn);
                    YJNBtn3.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn4.setBackgroundResource(R.drawable.btn_grd);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
//                            BigDecimal newNuber2 = NuberType.multiply(new BigDecimal("0.50"));
                            BigDecimal newNuber4 = new BigDecimal(ktYong).multiply(new BigDecimal("0.50"));
                            edtYJNnuber.setText(newNuber4.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {

                        }
//                        edtYJNnuber.setText(newNuber2.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {
                        edtYJNnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            //   溢价单   75%
            case R.id.YJNBtn3:

                if (kv.decodeString("tokenId") != null) {
                    YJNBtn1.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn2.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn3.setBackgroundResource(R.drawable.backbtn);
                    YJNBtn4.setBackgroundResource(R.drawable.btn_grd);

                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
//                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber4 = new BigDecimal(ktYong).multiply(new BigDecimal("0.75"));
                            edtYJNnuber.setText(newNuber4.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {

                        }
//                        edtYJNnuber.setText(newNuber3.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    } else {
                        edtYJNnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            //   溢价单  100%
            case R.id.YJNBtn4:

                Log.e(TAG, "onViewClicked: " + "溢价单  100%");
                if (kv.decodeString("tokenId") != null) {
                    YJNBtn1.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn2.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn3.setBackgroundResource(R.drawable.btn_grd);
                    YJNBtn4.setBackgroundResource(R.drawable.backbtn);
                    if (newprice != null && newprice.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            BigDecimal NuberType = new BigDecimal(Nube).divide(newprice, 10, BigDecimal.ROUND_HALF_UP);
                            BigDecimal newNuber4 = new BigDecimal(ktYong).multiply(new BigDecimal("1"));
                            edtYJNnuber.setText(newNuber4.setScale(quantityNuber.intValue(), BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {

                        }
                    } else {
                        edtYJNnuber.setText("0");
                    }
                } else {
                    Toast.makeText(getActivity(), "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.tx_cny:
                break;
            //买入
            case R.id.btn_sell:
                if (kv.decodeString("tokenId") != null) {

                    if (LLtype.equals("1")) {
                        //限单价
                        sellData();
                    } else if (LLtype.equals("2")) {
                        //市价
                        sellData();
                    } else if (LLtype.equals("3")) {
                        if (EDTZYZSCFPrice.getText().toString().trim().equals("")) {
                            Toast.makeText(getActivity(), "请输入止损止盈价格", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (edtZYZSPrice.getText().toString().trim().equals("")) {
                            Toast.makeText(getActivity(), "请输入委托价格", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (edtZYZSnuber.getText().toString().trim().equals("")) {
                            Toast.makeText(getActivity(), "请输入委托数量", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (datalIsts == null || datalIsts.size() == 0) {
                            Toast.makeText(getActivity(), "暂无最新成交价，无法进行止盈止损交易", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        view1 = getActivity().getLayoutInflater().inflate(R.layout.dialog_c2cbuysell, null);
                        Tltle = (TextView) view1.findViewById(R.id.tltle);
                        btnDimss = (Button) view1.findViewById(R.id.btn_dimss);
                        btnSell = (Button) view1.findViewById(R.id.btn_sell);
                        if (datalIsts.get(0).getDealPrice().compareTo(new BigDecimal(EDTZYZSCFPrice.getText().toString().trim())) == 1) {
                            mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
                            mMyDialog.setCancelable(true);
                            Tltle.setText("当最新价格小于或等于" + EDTZYZSCFPrice.getText().toString().trim() + BINAMEDATA + "时,那么将会以" + edtZYZSPrice.getText().toString().trim() + BINAMEDATA + "买入" + edtZYZSnuber.getText().toString().trim() + BINAME);
                            mMyDialog.show();
                            btnDimss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMyDialog.dismiss();
                                }
                            });
                            btnSell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //止盈止盈
                                    StopLoss();
                                    mMyDialog.dismiss();
                                }
                            });

                        } else if (datalIsts.get(0).getDealPrice().compareTo(new BigDecimal(EDTZYZSCFPrice.getText().toString().trim())) == -1) {
                            mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
                            mMyDialog.setCancelable(true);
                            Tltle.setText("当最新价格大于或等于" + EDTZYZSCFPrice.getText().toString().trim() + BINAMEDATA + "时,那么将会以" + edtZYZSPrice.getText().toString().trim() + BINAMEDATA + "买入" + edtZYZSnuber.getText().toString().trim() + BINAME);
                            mMyDialog.show();
                            btnDimss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMyDialog.dismiss();
                                }
                            });
                            btnSell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //止盈止盈
                                    StopLoss();
                                    mMyDialog.dismiss();
                                }
                            });
                        } else {
                            mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
                            mMyDialog.setCancelable(true);
                            Tltle.setText("当最新价格大于或等于" + EDTZYZSCFPrice.getText().toString().trim() + BINAMEDATA + "时,那么将会以" + edtZYZSPrice.getText().toString().trim() + BINAMEDATA + "买入" + edtZYZSnuber.getText().toString().trim() + BINAME);
                            mMyDialog.show();
                            btnDimss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMyDialog.dismiss();
                                }
                            });
                            btnSell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //止盈止盈
                                    StopLoss();
                                    mMyDialog.dismiss();
                                }


                            });
                        }

                    } else if (LLtype.equals("4")) {

                        //溢价交易
                        sellData();

                    }
                } else {
                    Toast.makeText(getActivity(), "请登陆", Toast.LENGTH_SHORT).show();
                    intent.setClass(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }
                break;

            case R.id.TypeText:
                break;
            case R.id.LLbuysell:
                break;


            //最新成交价点击
            case R.id.textviewPurMoney1:
                try {
                    String newMoney = newprice.toString();
                    if (LLtype.equals("1")) {
                        edtXDPrice.setText(newMoney);
                    } else if (LLtype.equals("3")) {
                        edtZYZSPrice.setText(newMoney);
                    }
                } catch (Exception e) {
                }

                break;
            case R.id.textviewPurMoney2:
                break;

            //切换和资金流向
            case R.id.tx_type:
                txType.setTextColor(getResources().getColor(R.color.appbar_background3));
                MoneyWater.setTextColor(getResources().getColor(R.color.background_baise));
                LLList.setVisibility(View.VISIBLE);
                webDialog.setVisibility(View.GONE);
                break;
            //切换和资金流向
            case R.id.MoneyWater:

                MoneyWater.setTextColor(getResources().getColor(R.color.appbar_background3));
                txType.setTextColor(getResources().getColor(R.color.background_baise));
                webDialog.loadUrl("javascript:getdata('" + BINAME + "')");

                LLList.setVisibility(View.GONE);
                webDialog.setVisibility(View.VISIBLE);
                break;
            case R.id.img_type:
                break;
            case R.id.rlgone:
                if (type == 1) {
                    recyclerView.setVisibility(View.GONE);
                    webDialog.setVisibility(View.GONE);
                    type = 2;
                    imgType.setImageResource(R.drawable.cq_dow);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    webDialog.setVisibility(View.VISIBLE);
                    type = 1;
                    imgType.setImageResource(R.drawable.cq_upper);
                }

                break;
            case R.id.ll_title:
                break;

            case R.id.LLList:
                break;
            case R.id.webDialog:
                break;

            default:
                break;
        }
    }

    /**
     * 限单价
     * 市价
     */
    @SuppressLint("CheckResult")
    private void sellData() {
        try {
            if (LLtype.equals("2")) {
                if (edtSJnuber.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入市价数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(edtSJnuber.getText().toString().trim()).compareTo(MIN) == -1) {
                    Toast.makeText(getActivity(), "最小委托数量不能低于" + MIN.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(edtSJnuber.getText().toString().trim()).compareTo(MAX) == 1) {
                    Toast.makeText(getActivity(), "最大委托数量不能大于" + MAX.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (LLtype.equals("1")) {
                if (edtXDPrice.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入委托价格", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtXDnuber.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入委托数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(edtXDnuber.getText().toString().trim()).compareTo(MIN) == -1) {
                    Toast.makeText(getActivity(), "最小委托数量不能低于" + MIN.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(edtXDnuber.getText().toString().trim()).compareTo(MAX) == 1) {
                    Toast.makeText(getActivity(), "最大委托数量不能大于" + MAX.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT).show();
                    return;
                }

            } else if (LLtype.equals("4")) {

                if (edtYJNnuber.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入市价数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(edtYJNnuber.getText().toString().trim()).compareTo(MIN) == -1) {
                    Toast.makeText(getActivity(), "最小委托数量不能低于" + MIN.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new BigDecimal(edtYJNnuber.getText().toString().trim()).compareTo(MAX) == 1) {
                    Toast.makeText(getActivity(), "最大委托数量不能大于" + MAX.stripTrailingZeros().toPlainString(), Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            HashMap<String, String> hashMap = new HashMap<>();
            if (LLtype.equals("1")) {
                //限价
                hashMap.put("delWay", "1");
                //委托数量
                hashMap.put("delNum", edtXDnuber.getText().toString().trim());
                //委托价格
                hashMap.put("delAmount", edtXDPrice.getText().toString().trim());
            } else if (LLtype.equals("2")) {
                //市价
                hashMap.put("delWay", "2");
                hashMap.put("delNum", edtSJnuber.getText().toString().trim());

            } else if (LLtype.equals("4")) {
                hashMap.put("delWay", "3");
                hashMap.put("delNum", edtYJNnuber.getText().toString().trim());
            }

            //交易币
            hashMap.put("coinCode", BINAME);
            //定价币
            hashMap.put("fixPriceCoinCode", BINAMEDATA);
            //(1 买 2 卖)
            hashMap.put("dealType", "2");


            ApiFactory.getInstance()
                    .creatDelegation(kv.decodeString("tokenId"), hashMap)
                    .compose(RxSchedulers.io_main())
                    .subscribe(data -> {

                        if (data.getCode() == 10002 || data.getCode() == 10001) {
                            intent.putExtra("flage", "2");
                            intent.setClass(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "登陆超时请重新登陆", Toast.LENGTH_SHORT).show();
                        }
                        if (data.getCode() == 200) {

                            edtZYZSPrice.setText("");
                            edtZYZSnuber.setText("");
                            Toast.makeText(getActivity(), "委托成功", Toast.LENGTH_SHORT).show();
                        } else {
                            edtZYZSPrice.setText("");
                            edtZYZSnuber.setText("");
                            t(data.getMsg());
                        }
                    }, throwable -> {

                    });

        } catch (Exception e) {

        }
    }


    /**
     * 止盈止盈
     */
    @SuppressLint("CheckResult")
    private void StopLoss() {

        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("coinCode", BINAME);
            hashMap.put("fixPriceCoinCode", BINAMEDATA);
            hashMap.put("dealType", "1");
            hashMap.put("delAmount", edtZYZSPrice.getText().toString().trim());
            hashMap.put("delNum", edtZYZSnuber.getText().toString().trim());
            BigDecimal tri = new BigDecimal(EDTZYZSCFPrice.getText().toString().trim());
            hashMap.put("triggerPrice", tri.setScale(pricNuber.intValue(), BigDecimal.ROUND_HALF_UP).toString());

            if (datalIsts.get(0).getDealPrice().compareTo(new BigDecimal(EDTZYZSCFPrice.getText().toString().trim())) == 1) {
                hashMap.put("triggerType", "2");
            } else if (datalIsts.get(0).getDealPrice().compareTo(new BigDecimal(EDTZYZSCFPrice.getText().toString().trim())) == -1) {
                hashMap.put("triggerType", "1");
            } else {
                hashMap.put("triggerType", "1");
            }

            ApiFactory.getInstance()
                    .creatStoploss(kv.decodeString("tokenId"), hashMap)
                    .compose(RxSchedulers.io_main())
                    .subscribe(data -> {

                        if (data.getCode() == 200) {
                            edtZYZSPrice.setText("");
                            edtZYZSnuber.setText("");
                            EDTZYZSCFPrice.setText("");
                            edtXJMoney.setText("");
                            t(data.getMsg());
                        } else {

                            t(data.getMsg());
                        }

                    }, throwable -> {

                    });

        } catch (Exception e) {

        }
    }

    /**
     * 小数最后一位相加相减
     *
     * @param price
     * @return
     */
    private String XJPriceSubMethod(String price) {
        String NewPrice = "";
        int Length = 0;
        if (!price.equals("")) {
            //是小数
            if (price.contains(".")) {
                NewPrice = price.substring(price.indexOf(".") + 1);
                Length = NewPrice.length();
            }

        }

        if (Length == 1) {
            return "0.1";
        }
        if (Length == 2) {
            return "0.01";
        }
        if (Length == 3) {
            return "0.001";
        }
        if (Length == 4) {
            return "0.0001";
        }
        if (Length == 5) {
            return "0.00001";
        }
        if (Length == 6) {
            return "0.000001";
        }
        if (Length == 7) {
            return "0.0000001";
        }
        if (Length == 8) {
            return "0.00000001";
        }
        if (Length == 9) {
            return "0.000000001";
        }
        if (Length == 10) {
            return "0.0000000001";
        }
        if (Length == 11) {
            return "0.00000000001";
        }
        if (Length == 12) {
            return "0.000000000001";
        }
        if (Length == 13) {
            return "0.0000000000001";
        }
        if (Length == 14) {
            return "0.00000000000001";
        } else {
            return "1";
        }


    }


    @Override
    public void onResume() {
        super.onResume();

        //获取系统小数位数
        getBiListData();

//
        putData(BINAME + "_" + BINAMEDATA);

        //获取最新成交价
        net();

        //交易大厅List
        getTickMaket();

        if (kv.decodeString("tokenId") != null) {
            //获取用户可用币数量
            getMoney();
        }

        if (Type.equals("NO")) {
            //长连接
            SocketData();
        }


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //
            putData(BINAME + "_" + BINAMEDATA);
            //获取最新成交价
            net();
            //交易大厅List
            getTickMaket();
            //长连接
            SocketData();
        }
    }

    /**
     * 长连接
     * <p>
     * 更新买卖
     */
    private void SocketData() {

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/" + name1)
                                .compose(RxSchedulers.io_main())
                                .subscribe(new WebSocketSubscriber2<Root<PurchaseDta>>() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    protected void onMessage(Root<PurchaseDta> root) {

                                        Type = "YES";
                                        if (root.getData().getBuy() != null || root.getData().getSell() != null) {

                                            try {

                                                if (root.getData().getListOrder().equals("")) {
                                                } else {
                                                    if (root.getData().getListOrder().get(root.getData().getListOrder().size() - 1).getDealAmount() != null) {
                                                        newprice = root.getData().getListOrder().get(root.getData().getListOrder().size() - 1).getDealAmount();
                                                        textviewPurMoney1.setText(newprice.setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());

                                                    }
                                                }
                                            } catch (Exception e) {
                                            }
                                            //买入
                                            buyList.clear();
                                            buys.clear();

                                            if (root.getData().getBuy() != null && root.getData().getBuy().size() != 0) {
                                                if (root.getData().getBuy().size() > 10) {
                                                    buyList.addAll(root.getData().getBuy().subList(0, 10));
                                                } else {
                                                    buyList.addAll(root.getData().getBuy());
                                                }
                                                buys.addAll(root.getData().getBuy());
                                            }


                                            sellList.clear();
                                            sells.clear();

                                            if (root.getData().getSell() != null && root.getData().getSell().size() != 0) {
                                                if (root.getData().getSell().size() >= 10) {
                                                    sells.addAll(root.getData().getSell().subList(0, 10));
                                                } else {
                                                    sells.addAll(root.getData().getSell());
                                                }

                                                sellList.addAll(sells);
                                                Collections.reverse(sellList);
                                            }
                                            if (getActivity() != null) {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        //买卖
                                                        updataView();

                                                        //深度图
                                                        drowHightView();
                                                    }
                                                });

                                            }
                                            try {
                                                if (root.getData().getListOrder() != null && root.getData().getListOrder().size() != 0) {

                                                    list.clear();
                                                    list.addAll(root.getData().getListOrder());
                                                    //更新最新成交记录
                                                    setNewMoney(list);
                                                }
                                            } catch (Exception e) {
                                                //try
                                            }

                                        } else {

//                                            Toast.makeText(getActivity(), "用户数据获取失败!", Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                    @Override
                                    protected void onReconnect() {
                                        MoneyType = "YES";
                                    }

                                });

                    } catch (Exception e) {


                    }

                }
            });

        }
    }

    /**
     * 获取系统小数位数
     */
    @SuppressLint("CheckResult")
    private void getBiListData() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .getDealPairList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    try {
                        if (data.isStauts() == true) {
                            mainLists.clear();
                            mainLists.addAll(data.getData());

                            for (int i = 0; i < mainLists.size(); i++) {
                                String name = data.getData().get(i).getDealPair();
                                if (name.equals(BINAME + "_" + BINAMEDATA)) {
                                    pricNuber = data.getData().get(i).getPriceReservation();
                                    quantityNuber = data.getData().get(i).getQuantityRetention();
                                    MIN = data.getData().get(i).getMinNum();
                                    MAX = data.getData().get(i).getMaxNum();
                                }
                            }

                            //限制Edt输入位数
                            edtXDPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(pricNuber.intValue())});
                            edtXDnuber.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(quantityNuber.intValue())});
                            edtXJMoney.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(pricNuber.intValue())});

                            edtSJnuber.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(quantityNuber.intValue())});

                            edtYJNnuber.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(quantityNuber.intValue())});

                            EDTZYZSCFPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(pricNuber.intValue())});
                            edtZYZSPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(pricNuber.intValue())});
                            edtZYZSnuber.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(quantityNuber.intValue())});
                        } else {
                            t(data.getMsg());
                        }
                    } catch (Exception e) {

                    }

                }, throwable -> {

                });
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void putData(String s) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("delkeys", s);
        ApiFactory.getInstance()
                .getIndexMaketList(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {

                    if (arrayListData.getData() != null) {

                        mainData = arrayListData.getData();
                        try {
                            textviewPurMoney2.setText("¥" + arrayListData.getData().get(0).getUsdtCny().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
                        } catch (Exception e) {
                            textviewPurMoney2.setText("¥0");
                        }

                    } else {
                        t(arrayListData.getMsg());

                    }


                }, throwable -> {


                });
    }

    /**
     * 获取用户可用币数量
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void getMoney() {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("coinCodes", name1);

        ApiFactory.getInstance()
                .account(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(arrayListData -> {

                    if (arrayListData.getData() != null) {
                        try {
                            ktYong = arrayListData.getData().get(0).getData().getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString();
                            txUsdt.setText(arrayListData.getData().get(0).getData().getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString() + BINAME);
                            if (BINAMEDATA.equals("CNYE")) {
                                Nube = arrayListData.getData().get(1).getData().getUsableCNY().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

                                txCny.setText(arrayListData.getData().get(1).getData().getUsableCNY().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString() + "CNYE");
                            } else {
                                Nube = arrayListData.getData().get(0).getData().getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

                                txCny.setText(arrayListData.getData().get(1).getData().getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString() + BINAMEDATA);
                            }
                        } catch (Exception e) {
                            txUsdt.setText(0 + BINAME);
                            txCny.setText(0 + BINAMEDATA);
                        }
                    }

                }, throwable -> {

                });
    }

    /**
     * 更新最新成交记录
     *
     * @param list
     */
    private void setNewMoney(ArrayList<CnySize> list) {

        if (list != null && !list.isEmpty()) {
            for (CnySize cnySize : list) {
                PurchaseDatalIst purchaseDatalIst = new PurchaseDatalIst();
                purchaseDatalIst.setDealTime(cnySize.getDealDate());
                purchaseDatalIst.setDealNum(cnySize.getDealNum());
                purchaseDatalIst.setDealPrice(cnySize.getDealAmount());
                try {
                    datalIsts.add(0, purchaseDatalIst);
                } catch (Exception e) {

                }

            }
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (datalIsts != null && datalIsts.size() != 0) {
                    newDealAdapter = new NewDealAdapter(datalIsts, getActivity());
                    newDealAdapter.setDeviceList((ArrayList<PurchaseDatalIst>) datalIsts);
                    recyclerView.setAdapter(newDealAdapter);
                    newDealAdapter.notifyDataSetChanged();
                    Utils.setListViewHeightBasedOnChildren(recyclerView);
                }
            }
        });

    }


    @Override
    public void onStop() {
        super.onStop();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/" + name1).subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }


        Disposable disposable2 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinsocket/" + kv.decodeString("id")).subscribe();
        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
        }
    }

    @Override
    public void onPause() {
        super.onPause();


        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/" + name1).subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }

        Disposable disposable2 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinsocket/" + kv.decodeString("id")).subscribe();
        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        Disposable disposable1 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "websocket/" + name1).subscribe();
        if (disposable1 != null && !disposable1.isDisposed()) {
            disposable1.dispose();
        }


        Disposable disposable2 = RxWebSocket.get(WPConfig.INSTANCE.getWSUrl() + "coinsocket/" + kv.decodeString("id")).subscribe();
        if (disposable2 != null && !disposable2.isDisposed()) {
            disposable2.dispose();
        }

    }
}
