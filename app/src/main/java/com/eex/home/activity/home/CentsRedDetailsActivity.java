package com.eex.home.activity.home;

import android.os.Bundle;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @ClassName: CentsRedDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 12:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 12:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 *
 * 分红详情
 */
public class CentsRedDetailsActivity extends BaseActivity {


    /**
     * 头部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 分红币种
     */
    @BindView(R.id.tx_coin)
    TextView txCoin;
    /**
     * 流通总额
     */
    @BindView(R.id.tx_downNuber)
    TextView txDownNuber;
    /**
     * 单币流通分红
     */
    @BindView(R.id.singleCoinRed)
    TextView singleCoinRed;
    /**
     * 流通分红总额
     */
    @BindView(R.id.TotalNuber)
    TextView TotalNuber;
    /**
     * 锁仓总额
     */
    @BindView(R.id.tx_scNuber)
    TextView txScNuber;
    /**
     * 单币锁仓分红约
     */
    @BindView(R.id.singilNber)
    TextView singilNber;
    /**
     * 锁仓分红总额
     */
    @BindView(R.id.scTotalNuber)
    TextView scTotalNuber;
    /**
     * 合计
     */
    @BindView(R.id.Total)
    TextView Total;
    /**
     * 分红日期
     */
    @BindView(R.id.tx_time)
    TextView txTime;

    /**
     * 币名称
     */
    private String coinCode;
    /**
     * 池币分红数量
     */
    private String coinWelfareNum;
    /**
     * 持币单币分红额
     */
    private String coinWelfareLimit;
    /**
     * 持币总分红
     */
    private String coinTotalWelfare;
    /**
     * 锁仓分红总量
     */
    private String lockWelfareNum;
    /**
     * 锁仓单币分红额度
     */
    private String coinCodeLimit;
    /**
     * 锁仓总分红
     */
    private String lockTotalWelfare;
    /**
     * 分红汇总
     */
    private String welfareTotal;
    /**
     * 时间
     */
    private String createTime;

    String pattern = "yyyy-MM-dd";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_cents_red_details;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {


        comtitlebar.setTitle("分红详情");

        //分红币种
        if (getIntent().getStringExtra("coinCode") != null) {
            coinCode = getIntent().getStringExtra("coinCode");
            txCoin.setText(coinCode);
        }
        //流通总额
        if (getIntent().getStringExtra("coinWelfareNum") != null) {
            coinWelfareNum = getIntent().getStringExtra("coinWelfareNum");
            if (BigDecimal.ZERO.compareTo(new BigDecimal(coinWelfareNum)) == 1) {
                txDownNuber.setText("0 EBT");
            } else {
                txDownNuber.setText(coinWelfareNum + " EBT");
            }
        }
        //单币流通分红
        if (getIntent().getStringExtra("coinWelfareLimit") != null) {
            coinWelfareLimit = getIntent().getStringExtra("coinWelfareLimit");
            singleCoinRed.setText(coinWelfareLimit + " " + coinCode);

        }
        //流通分红总额
        if (getIntent().getStringExtra("coinTotalWelfare") != null) {
            coinTotalWelfare = getIntent().getStringExtra("coinTotalWelfare");
            if (BigDecimal.ZERO.compareTo(new BigDecimal(coinTotalWelfare)) == 1) {
                TotalNuber.setText("0 " + coinCode);
            } else {
                TotalNuber.setText(coinTotalWelfare + " " + coinCode);
            }
        }
        //锁仓总额
        if (getIntent().getStringExtra("lockWelfareNum") != null) {
            lockWelfareNum = getIntent().getStringExtra("lockWelfareNum");
            txScNuber.setText(lockWelfareNum + " EBT");
        }
        //单币锁仓分红约
        if (getIntent().getStringExtra("coinCodeLimit") != null) {
            coinCodeLimit = getIntent().getStringExtra("coinCodeLimit");
            singilNber.setText(coinCodeLimit + " " + coinCode);
        }
        //锁仓分红总额
        if (getIntent().getStringExtra("lockTotalWelfare") != null) {
            lockTotalWelfare = getIntent().getStringExtra("lockTotalWelfare");
            scTotalNuber.setText(lockTotalWelfare + " " + coinCode);
        }
        //合计
        if (getIntent().getStringExtra("welfareTotal") != null) {
            welfareTotal = getIntent().getStringExtra("welfareTotal");
            Total.setText(welfareTotal + " " + coinCode);
        }
        //分红日期
        if (getIntent().getStringExtra("createTime") != null) {
            createTime = getIntent().getStringExtra("createTime");
            Date date = new Date(Long.parseLong(createTime));
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            txTime.setText("分红日期:" + format.format(date));
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

    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
