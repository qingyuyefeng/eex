package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;

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
 * @ClassName: SecondkillTypeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 17:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 17:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 完成
 */
public class SecondkillTypeActivity extends BaseActivity {


    /**
     * 顶部
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     * 秒杀成功 或者失败图片
     */
    @BindView(R.id.img_type)
    ImageView imgType;
    /**
     * 秒杀成功
     */
    @BindView(R.id.tx_type)
    TextView txType;
    /**
     * 4000USDT已您的资金账户扣除
     */
    @BindView(R.id.tx_nuber)
    TextView txNuber;


    /**
     * 秒杀结果状态
     */
    private String type;
    /**
     * 秒杀数量
     */
    private String nuber;
    /**
     * 秒杀状态
     */
    private String code;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_secondkill_type;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void refreshData(Bundle savedInstanceState) {


        if (getIntent().getStringExtra("nuber") != null) {
            nuber = getIntent().getStringExtra("nuber");

        }
        if (getIntent().getStringExtra("code") != null) {
            code = getIntent().getStringExtra("code");

        }
        if (getIntent().getStringExtra("type") != null) {
            type = getIntent().getStringExtra("type");

        }

        comtitlebar.setImageView(R.drawable.cq_recharge_zd);
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("成功")) {
                    finish();
                    intent.setClass(getApplicationContext(), SecondRecordListActivity.class);
                    startActivity(intent);
                } else if (type.equals("失败")) {
                    setResult(2000);
                    finish();
                } else {
                    setResult(2000);
                    finish();
                }
            }
        });


        if (code.equals("200")) {
            imgType.setImageResource(R.drawable.miaoshachenggong);
            txType.setText("秒杀成功");
            comtitlebar.setTitle("秒杀成功");
            txNuber.setText(nuber + "USDT已您的资金账户扣除");
            return;
        }
        if (code.equals("4017")) {
            imgType.setImageResource(R.drawable.miaoshashibai);
            txType.setText("秒杀失败");
            comtitlebar.setTitle("秒杀失败");
            txNuber.setText("秒杀已结束,您本次秒杀失败,请关注下次秒杀活动");
            return;
        }
        if (code.equals("4012")) {
            imgType.setImageResource(R.drawable.miaoshashibai);
            txType.setText("秒杀失败");
            comtitlebar.setTitle("秒杀失败");
            txNuber.setText("剩余抢购数量不足,请修改抢购数量后重新购买");
            return;
        }
        if (code.equals("4013")) {
            imgType.setImageResource(R.drawable.miaoshashibai);
            txType.setText("秒杀失败");
            comtitlebar.setTitle("秒杀失败");
            txNuber.setText("秒杀已抢光/已结束,您本次秒杀失败,请关注下次秒杀活动");
            return;
        }
        if (code.equals("4019")) {
            imgType.setImageResource(R.drawable.miaoshashibai);
            txType.setText("秒杀失败");
            comtitlebar.setTitle("秒杀失败");
            txNuber.setText("秒杀已抢光,您本次秒杀失败,请关注下次秒杀活动");
            return;
        }
        if (code.equals("4015")) {
            imgType.setImageResource(R.drawable.miaoshashibai);
            txType.setText("资金账户余额不足");
            comtitlebar.setTitle("余额不足");
            txNuber.setText("您的资金账户可用余额不足" + nuber + "USDT");
            return;
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
    }
}
