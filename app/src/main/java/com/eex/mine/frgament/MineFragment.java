package com.eex.mine.frgament;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.assets.activity.CurrencyAddresListActivity;
import com.eex.assets.activity.RechargeMoneyActivity;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseFragment;
import com.eex.common.util.SharedPreferencesUtils;
import com.eex.common.util.StringUtil;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.home.PhotoActivity;
import com.eex.home.activity.home.RealNameActivity;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.bean.SMdata;
import com.eex.home.bean.TwoRealName;
import com.eex.home.weight.MyDialog;
import com.eex.mine.activity.EntrustActivity;
import com.eex.mine.activity.NewsPersionActivity;
import com.eex.mine.activity.ReailNameTypeActivity;
import com.eex.mine.activity.ReceivablesActivity;
import com.eex.mine.activity.RechargeFYActivity;
import com.eex.mine.activity.SecondKillActivity;
import com.eex.mine.activity.noTradActivity;
import com.eex.mvp.mine.logout.LogoutActivity;
import com.eex.mvp.mine.security.SecurityActivity;

import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
 * @Package: com.overthrow.mine.frgament
 * @ClassName: MineFragment
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/23 13:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 13:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MineFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    // 定义保存的文件的名称
    private static final String languageName = "sharedfile";
    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.textview_Name)
    TextView textviewName;
    /**
     *
     */
    @BindView(R.id.imgview_type)
    ImageView imgviewType;


    /**
     *
     */
    @BindView(R.id.textview_NameType)
    TextView textviewNameType;

    /**
     *
     */
    @BindView(R.id.textview_NameType1)
    TextView textviewNameType1;
    /**
     *
     */
    @BindView(R.id.LL_Order)
    LinearLayout LLOrder;
    /**
     *
     */
    @BindView(R.id.LL_Money)
    LinearLayout LLMoney;
    /**
     *
     */
    @BindView(R.id.LL_Recharge)
    LinearLayout LLRecharge;
    /**
     *
     */
    @BindView(R.id.TXtype)
    TextView TXtype;
    /**
     *
     */
    @BindView(R.id.LLExplain)
    LinearLayout LLExplain;
    /**
     *
     */
    @BindView(R.id.txName)
    TextView txName;
    /**
     *
     */
    @BindView(R.id.LL_RealName)
    LinearLayout LLRealName;
    /**
     *
     */
    @BindView(R.id.LL_security)
    LinearLayout LLSecurity;
    /**
     *
     */
    @BindView(R.id.LL_Receivables)
    LinearLayout LLReceivables;
    /**
     *
     */
    @BindView(R.id.LL_WithdrawMoney)
    LinearLayout LLWithdrawMoney;
    /**
     *
     */
    @BindView(R.id.LL_Invitation)
    LinearLayout LLInvitation;
    /**
     *
     */
    @BindView(R.id.LL_Merchant)
    LinearLayout LLMerchant;
    /**
     * 切换语言
     */
    @BindView(R.id.LL_Language)
    LinearLayout LLLanguage;
    /**
     * 设置
     */
    @BindView(R.id.LL_set)
    LinearLayout LLSet;
    /**
     *
     */
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    Unbinder unbinder;


    public SMdata sMdata;
    public TwoRealName realName;

    private String typeImg = "";
    private MyDialog mMyDialog;


    @Override
    protected void lazyLoad() {


        if (kv.decodeString("tokenId") != null) {
            //获取用户信息
            getuserview();
            //获取用户状态
            UserType();
            //是否实名
            IsName();
            //获取认证提币限额
            getMeMoneyData();
        } else {
            txName.setText(getActivity().getResources().getString(R.string.sh3));
        }
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setBackGone();
        comtitlebar.setTitle("我的");

        if (kv.decodeString("tokenId") != null) {
            //获取用户信息
            getuserview();
            //获取用户状态
            UserType();
            //是否实名
            IsName();
            //获取认证提币限额
            getMeMoneyData();
        } else {
            txName.setText(getActivity().getResources().getString(R.string.sh3));
        }
    }

    /**
     * 获取用户信息
     */
    @SuppressLint("CheckResult")
    private void getuserview() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .accountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    SwipeUtil.loadCompleted(swipeRefresh);

                    if (data.getCode() == 1002) {
                        intent.putExtra("flage", "2");
                        intent.setClass(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    if (data.getData() != null) {
                        if (kv.decodeString("tokenId") == null) {
                            textviewName.setText(getResources().getString(R.string.zhanghu));
                        } else {
                            boolean status = kv.decodeString("username").contains("+86");
                            if (status) {
                                String userIdJiequ = kv.decodeString("username").replace("+86", "");
                                textviewName.setText(StringUtil.gettextView(userIdJiequ));
                            } else {
                                boolean status1 = kv.decodeString("username").contains("@");
                                if (status1) {
                                    textviewName.setText(kv.decodeString("username").replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4"));
                                }
                            }

                        }
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    /**
     * 获取用户状态
     */
    @SuppressLint("CheckResult")
    private void UserType() {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .usergrade(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);


                    if (data.getCode() == 10002) {
                        intent.putExtra("flage", "2");
                        intent.setClass(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    if (data.getData() != null) {


                        textviewNameType.setText(data.getData().getUserGradeName() + "");
                        //普通用户
                        if (data.getData().getUserGradeType() == 1) {
                            typeImg = "1";
                            textviewNameType1.setVisibility(View.GONE);
                            imgviewType.setImageResource(R.drawable.dengji1);
                            //商家
                        } else if (data.getData().getUserGradeType() == 2) {
                            typeImg = "2";
                            textviewNameType1.setVisibility(View.GONE);
                            imgviewType.setImageResource(R.drawable.shangjia1);
                            //项目认证方
                        } else {
                            typeImg = "3";
                            imgviewType.setImageResource(R.drawable.xiangmufang1);
                            textviewNameType1.setVisibility(View.VISIBLE);
                            textviewNameType1.setText("(" + data.getData().getRemark() + ")");
                        }
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void IsName() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    sMdata = data.getData();

//                    MMKV kv = MMKV.mmkvWithID("realName", MMKV.MULTI_PROCESS_MODE);
//                    kv.encode("authStatus", data.getData().getAuthStatus().MIN_VALUE);
//                    kv.encode("remark", data.getData().getRemark());
//                    kv.encode("level", data.getData().getLevel().MIN_VALUE);
//                    kv.encode("cardType", data.getData().getCardType().MIN_VALUE);
//                    kv.encode("surname", data.getData().getSurname());
//                    kv.encode("givename", data.getData().getGivename());
//                    kv.encode("cardNo", data.getData().getCardNo());


                    if (data.isStauts() == false) {
                        txName.setText(getActivity().getResources().getString(R.string.weishimin));
                    } else {

                        try {
                            if (data.getData().getLevel().equals(1)) {
                                TXtype.setText("已完成级别1认证");
                                txName.setText("继续认证级别2");
                                LLRealName.setEnabled(true);
                            } else if (data.getData().getLevel().equals(2)) {
                                if (data.getData().getRemark() == null || data.getData().getRemark().equals("")) {
                                    TXtype.setText("已完成级别2认证");
                                    txName.setText("继续认证级别3");
                                    LLRealName.setEnabled(true);
                                } else {
                                    TXtype.setText("实名等级3审核失败");
                                    txName.setText("继续认证级别3");
                                    LLRealName.setEnabled(true);
                                }
                            } else if (data.getData().getLevel().equals(3)) {
                                //待审核
                                if (data.getData().getAuthStatus().equals(0)) {

                                    //待审核
                                    LLRealName.setEnabled(false);
                                    TXtype.setText("级别3认证待审核");
                                    txName.setText("");
//                                }
                                    //审核通过
                                } else if (data.getData().getAuthStatus().equals(1)) {
                                    LLRealName.setEnabled(false);
                                    TXtype.setText("已完成级别3认证");
                                    txName.setVisibility(View.GONE);
                                    txName.setText("");
                                    //审核失败
                                } else {
                                    TXtype.setText("级别3认证不通过");
                                    txName.setText("重新认证");
                                    LLRealName.setEnabled(true);
                                }

                            }
                        } catch (Exception e) {

                        }
                    }
                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }

    /**
     * 获取认证提币限额
     */
    @SuppressLint("CheckResult")
    private void getMeMoneyData() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .levelconfig(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data.getData() != null) {
                        realName = data.getData();
                    }

                }, throwable -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                });
    }


    @Override
    protected void initUiAndListener() {

        SwipeUtil.init(swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_my_zoe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);


        return rootView;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.textview_Name, R.id.imgview_type, R.id.textview_NameType, R.id.textview_NameType1, R.id.LL_Order, R.id.LL_Money,
            R.id.LL_Recharge, R.id.TXtype, R.id.LLExplain, R.id.LL_RealName, R.id.LL_security, R.id.LL_Receivables,
            R.id.LL_WithdrawMoney, R.id.LL_Invitation, R.id.LL_Merchant, R.id.LL_Language, R.id.LL_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textview_Name:
                break;
            case R.id.imgview_type:
                if (typeImg.equals("1")) {
                    intent.putExtra("id", "b3a502283a964a2fa31f3b7b435fb17f");
                    intent.setClass(getContext(), NewsPersionActivity.class);
                    getContext().startActivity(intent);
                } else if (typeImg.equals("2")) {
                    intent.putExtra("id", "b3a502283a964a2fa31f3b7b435fb17f");
                    intent.setClass(getContext(), NewsPersionActivity.class);
                    getContext().startActivity(intent);
                }
                break;


            case R.id.textview_NameType:
                intent.putExtra("id", "b3a502283a964a2fa31f3b7b435fb17f");
                intent.setClass(getContext(), NewsPersionActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.textview_NameType1:
                break;
            //订单中心
            case R.id.LL_Order:
                //我的委托加成交记录
                intent.setClass(getActivity(), EntrustActivity.class);
                startActivity(intent);
                break;
            //理财中心
            case R.id.LL_Money:
                intent.setClass(getContext(), SecondKillActivity.class);
                startActivity(intent);
                break;
            //充值提现
            case R.id.LL_Recharge:
                intent.setClass(getActivity(), RechargeMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.TXtype:
                break;
            case R.id.LLExplain:

                View view1 = getActivity().getLayoutInflater().inflate(R.layout.dialog_my_zoe, null);
                if (sMdata == null) {
                    return;
                }
                if (sMdata != null) {
                    TextView TX1 = (TextView) view1.findViewById(R.id.TX1);
                    TextView TX2 = (TextView) view1.findViewById(R.id.TX2);
                    TextView TX3 = (TextView) view1.findViewById(R.id.TX3);
                    TextView tx_hoer = (TextView) view1.findViewById(R.id.tx_hoer);
                    Button btn_next = (Button) view1.findViewById(R.id.btn_next);
                    TX1.setBackgroundResource(R.drawable.myz_zoeckb);
                    TX2.setBackgroundResource(R.drawable.myz_grund);
                    TX3.setBackgroundResource(R.drawable.myz_grund);
                    if (sMdata.getLevel().equals(1)) {
                        btn_next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent();
                                intent1.setClass(getContext(), RealNameActivity.class);
                                getActivity().startActivity(intent1);
                            }
                        });
                    } else if (sMdata.getLevel().equals(2)) {
                        tx_hoer.setBackgroundResource(R.color.appbar_background3);
                        TX2.setBackgroundResource(R.drawable.myz_zoeckb);
                        TX3.setBackgroundResource(R.drawable.myz_grund);
                        btn_next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent();
                                intent1.setClass(getContext(), PhotoActivity.class);
                                getActivity().startActivity(intent1);
                            }
                        });
                    } else if (sMdata.getLevel().equals(3)) {
                        if (sMdata.getAuthStatus() == 0) {
                            tx_hoer.setBackgroundResource(R.color.appbar_background3);
                            TX2.setBackgroundResource(R.drawable.myz_zoeckb);
                            TX3.setBackgroundResource(R.drawable.myz_grund);
                            btn_next.setVisibility(View.GONE);
                        } else if (sMdata.getAuthStatus() == 1) {
                            tx_hoer.setBackgroundResource(R.color.appbar_background3);
                            TX2.setBackgroundResource(R.drawable.myz_zoeckb);
                            TX3.setBackgroundResource(R.drawable.myz_zoeckb);
                            btn_next.setVisibility(View.GONE);
                        } else {
                            tx_hoer.setBackgroundResource(R.color.appbar_background3);
                            TX2.setBackgroundResource(R.drawable.myz_zoeckb);
                            TX3.setBackgroundResource(R.drawable.myz_grund);
                            btn_next.setVisibility(View.VISIBLE);
                        }

                    }
                }

                if (realName != null) {
                    TextView txLoveOne = (TextView) view1.findViewById(R.id.txLoveOne);
                    txLoveOne.setText("提币额度(近24h):" + realName.getLevel2Btc() + "BTC");
                    TextView txLoveOneBtc = (TextView) view1.findViewById(R.id.txLoveOneBtc);
                    txLoveOneBtc.setText("提现额度(近24h):" + realName.getLevel2Cnye() + "CNYE");
                    TextView txTwo = (TextView) view1.findViewById(R.id.txTwo);
                    txTwo.setText("提币额度(近24h):" + realName.getLevel3Btc() + "BTC");
                    TextView txTwobtc = (TextView) view1.findViewById(R.id.txTwobtc);
                    txTwobtc.setText("提现额度(近24h):" + realName.getLevel3Cnye() + "CNYE");
                    mMyDialog = new MyDialog(getActivity(), 0, 0, view1, R.style.DialogTheme);
                    mMyDialog.setCancelable(true);
                    mMyDialog.show();
                } else {
                    Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                }

                break;

            //实名认证
            case R.id.LL_RealName:
                try {
                    if (sMdata.getLevel().equals(3)) {
                        if (sMdata.getAuthStatus().equals(2)) {
                            if (sMdata.getRemark() != null && !sMdata.getRemark().equals("")) {
                                intent.setClass(getContext(), ReailNameTypeActivity.class);
                                startActivity(intent);
                            }
                        }
                    } else if (sMdata.getLevel().equals(2)) {
                        intent.setClass(getContext(), PhotoActivity.class);
                        startActivity(intent);
                    } else {
                        intent.setClass(getContext(), RealNameActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }
                break;
            //安全中心
            case R.id.LL_security:
                //intent.setClass(getActivity(), SetActivity.class);
                intent.setClass(getActivity(), SecurityActivity.class);
                startActivity(intent);
                break;
            //收款设置
            case R.id.LL_Receivables:
                intent.setClass(getContext(), ReceivablesActivity.class);
                startActivity(intent);
                break;
            //提币地址
            case R.id.LL_WithdrawMoney:

                intent.setClass(getActivity(), CurrencyAddresListActivity.class);
                intent.putExtra("Type", "BiListData");
                startActivity(intent);
                break;
            //邀请返佣
            case R.id.LL_Invitation:
                intent.setClass(getActivity(), RechargeFYActivity.class);
                startActivity(intent);
                break;
            case R.id.LL_Merchant:
                intent.setClass(getContext(), noTradActivity.class);
                getContext().startActivity(intent);

                break;
            case R.id.LL_Language:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getActivity().getResources().getString(R.string.login_Please));
                final String items[] = {getActivity().getResources().getString(R.string.lan_chinese), getActivity().getResources().getString(R.string.lan_en)};
//                final String items[] = {getActivity().getResources().getString(R.string.lan_chinese), getActivity().getResources().getString(R.string.lan_en), getActivity().getResources().getString(R.string.lan_ja), getActivity().getResources().getString(R.string.lan_ko)};
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String item = items[which];
                        //中文
                        if (item.equals(getActivity().getResources().getString(R.string.lan_chinese))) {
                            switchLanguage("cn");
                            //英语
                        } else if (item.equals(getActivity().getResources().getString(R.string.lan_en))) {
                            switchLanguage("en");
                            //日语
                        } else if (item.equals(getActivity().getResources().getString(R.string.lan_ja))) {
                            switchLanguage("ja");
                            //韩语
                        } else if (item.equals(getActivity().getResources().getString(R.string.lan_ko))) {
                            switchLanguage("ko");


                        }
                        dialog.dismiss();
                    }
                });

                builder.show();

                break;


            //设置
            case R.id.LL_set:
//                intent.setClass(getContext(), NewSetActivity.class);
                intent.setClass(getContext(), LogoutActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void switchLanguage(String language) {

        Locale locale = new Locale(language);
        Resources resources = getActivity().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            getActivity().createConfigurationContext(config);
        }
        Locale.setDefault(locale);
        //中文
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
            //英语
        } else if (language.equals("cn")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
            //日语
        } else if (language.equals("ja")) {
            config.locale = Locale.JAPANESE;
            //韩语
        } else if (language.equals("ko")) {
            config.locale = Locale.KOREAN;

        }
        resources.updateConfiguration(config, dm);



        //保存设置语言的类型
        SharedPreferencesUtils.putShareData(languageName, language);
        //更新语言后，destroy当前页面，重新绘制
        //更新语言后，destroy当前页面，重新绘制
        getActivity().onBackPressed();



    }


    @Override
    public void onResume() {
        super.onResume();

        if (kv.decodeString("tokenId") != null) {
            //获取用户信息
            getuserview();
            //获取用户状态
            UserType();
            //是否实名
            IsName();
            //获取认证提币限额
            getMeMoneyData();
        } else {
            txName.setText(getActivity().getResources().getString(R.string.sh3));
        }

    }

    @Override
    public void onRefresh() {

        if (kv.decodeString("tokenId") != null) {
            //获取用户信息
            getuserview();
            //获取用户状态
            UserType();
            //是否实名
            IsName();
            //获取认证提币限额
            getMeMoneyData();
        } else {
            txName.setText(getActivity().getResources().getString(R.string.sh3));
        }
    }
}
