package com.eex.mine.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.SwipeUtil;
import com.eex.common.view.ComTitleBar;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
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
 * @ClassName: RechargeFYActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/4 15:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/4 15:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 返佣
 */
public class RechargeFYActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "RechargeFYActivity";
    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_FriendNuber)
    TextView txFriendNuber;
    /**
     *
     */
    @BindView(R.id.btn_Friend)
    Button btnFriend;
    /**
     *
     */
    @BindView(R.id.tx_moneyNuber)
    TextView txMoneyNuber;
    /**
     *
     */
    @BindView(R.id.btn_money)
    Button btnMoney;
    /**
     *
     */
    @BindView(R.id.textview_code)
    TextView textviewCode;
    /**
     *
     */
    @BindView(R.id.btn_code)
    ImageButton btnCode;
    /**
     *
     */
    @BindView(R.id.textview_link)
    TextView textviewLink;
    /**
     *
     */
    @BindView(R.id.btn_link)
    ImageButton btnLink;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tx_Numberof)
    TextView txNumberof;
    @BindView(R.id.btn_Numberof)
    Button btnNumberof;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge2;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.fanyong));
        net();
        textviewLink.setText(WPConfig.regist + kv.decodeString("invateCode"));
        textviewCode.setText(kv.decodeString("invateCode"));

    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .brokerage(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    SwipeUtil.loadCompleted(swipeRefresh);
                    if (data != null) {

                        txFriendNuber.setText(data.getData().getFriendNum() + "");
                        txNumberof.setText(data.getData().getEbxTotal() + "");
                        if (data.getData().getChargesPrices().compareTo(BigDecimal.ZERO) == 1) {
                            txMoneyNuber.setText(data.getData().getChargesPrices().setScale(8, BigDecimal.ROUND_DOWN) + "");

                        } else {

                            txMoneyNuber.setText("0");
                        }
                    } else {
                        t(data.getMsg());
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.comtitlebar, R.id.tx_FriendNuber, R.id.btn_Friend, R.id.tx_moneyNuber, R.id.btn_money, R.id.btn_Numberof, R.id.textview_code, R.id.btn_code, R.id.textview_link, R.id.btn_link})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_FriendNuber:
                break;
            case R.id.btn_Friend:
                //朋友
                intent.setClass(RechargeFYActivity.this, FrentActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_Numberof:

                intent.setClass(RechargeFYActivity.this, FrentActivity.class);
                startActivity(intent);
                break;


            case R.id.tx_moneyNuber:
                break;
            case R.id.btn_money:
                //佣金
                intent.setClass(RechargeFYActivity.this, DetileActivity.class);
                startActivity(intent);

                break;
            case R.id.textview_code:
                break;
            case R.id.btn_code:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(textviewCode.getText().toString().trim());
                Toast.makeText(this, R.string.fuzichong, Toast.LENGTH_LONG).show();
                break;
            case R.id.textview_link:
                break;
            case R.id.btn_link:
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(textviewLink.getText().toString().trim());
                Toast.makeText(this, R.string.fuzichong, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        net();
    }
}
