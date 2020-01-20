package com.eex.home.adapter;

import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.SecondkillLsitvo;

import net.tsz.afinal.FinalBitmap;

import java.math.BigDecimal;
import java.util.ArrayList;

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
 * @Package: com.overthrow.home.adapter
 * @ClassName: SecondKillAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 10:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 10:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 秒杀专区
 */
public class SecondKillAdapter extends RecyclerAdapter<SecondkillLsitvo> {


    private CountDownTimer data;
    private long day;
    private long hour;
    private long minute;
    private long second;
    private String time;
    private FinalBitmap fb;



    public SecondKillAdapter(ArrayList<SecondkillLsitvo> list) {
        super(R.layout.item_secodkill,list);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecondkillLsitvo item) {

        helper.setText(R.id.Biname, item.getCoinCode());
        helper.setText(R.id.secodkill_singlePrice, "秒杀单价:" + item.getSecKillValue().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        helper.setText(R.id.secodkill_marketPrice, "市场价格:" + item.getMarketValue().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        helper.setText(R.id.secodkill_Nuber, "秒杀总量:" + item.getSecKillTotalNum().setScale(6, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + item.getCoinCode());
        helper.setText(R.id.tx_biQuan, "(" + item.getCoinName() + ")");
        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        ImageView imageView = helper.getView(R.id.img_biImge);
        fb.display(imageView, WPConfig.PicBaseUrl + item.getImgUrl()+"");

        helper.setText(R.id.tx_zhekou, item.getSecKillDiscount().multiply(new BigDecimal(10)).stripTrailingZeros().toPlainString() + "折");
        helper.setText(R.id.tx_baifenbi, "已抢购" + (item.getSecKillCurrentNum().divide(item.getSecKillTotalNum(), 10, BigDecimal.ROUND_CEILING)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN) + "%");
        ProgressBar bar = helper.getView(R.id.pb_progressbar);
        bar.setMax(item.getSecKillTotalNum().intValue());
        bar.setProgress(item.getSecKillCurrentNum().intValue());
        TextView textView = helper.getView(R.id.secodkill_marketPrice);
        //加横线
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setTag(R.id.tx_time, helper.getAdapterPosition());
        if (item.getDealState() == 1) {
            helper.setVisible(R.id.tx_timeType, true);
            helper.setText(R.id.btn_go, "立即抢购");
            helper.setVisible(R.id.btn_go, true);
            helper.setVisible(R.id.tx_time, true);
            helper.setVisible(R.id.tx_timeName, true);
            helper.setBackgroundRes(R.id.btn_go, R.drawable.backbtn);
            helper.addOnClickListener(R.id.btn_go);
            helper.setTextColor(R.id.btn_go, mContext.getResources().getColor(R.color.background_baise));
            data = new CountDownTimer(item.getSecKillEndTimeMil() - item.getCurrentTimeMillis(), 1000) {
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
                    helper.setText(R.id.tx_time, hour + ":" + minute + ":" + second);

                }

                @Override
                public void onFinish() {
                    helper.setVisible(R.id.tx_time, false);

                }
            };
            data.start();
            helper.getView(R.id.tx_time).setTag(helper.getAdapterPosition());

        } else if (item.getDealState() == 2) {
            helper.setVisible(R.id.tx_timeType, true);
            helper.setText(R.id.btn_go, "即将开始");
            helper.setText(R.id.tx_timeName, "准时开抢");
            helper.setVisible(R.id.btn_go, true);
            helper.setVisible(R.id.tx_time, true);
            helper.setVisible(R.id.tx_timeName, true);
            helper.setBackgroundRes(R.id.btn_go, R.drawable.btn_greed);
            helper.setTextColor(R.id.btn_go, mContext.getResources().getColor(R.color.background_baise));
            helper.setText(R.id.tx_time, item.getSecKillStartTime());
        } else if (item.getDealState() == 3) {
            helper.setVisible(R.id.btn_go, true);
            helper.setText(R.id.btn_go, "已抢光");
            helper.setVisible(R.id.tx_time, false);
            helper.setVisible(R.id.tx_timeName, false);

            helper.setBackgroundRes(R.id.btn_go, R.drawable.btn_grd);
        } else if (item.getDealState() == 4) {
            helper.setVisible(R.id.btn_go, true);
            helper.setText(R.id.btn_go, "已抢光");
            helper.setVisible(R.id.tx_time, false);
            helper.setVisible(R.id.tx_timeName, false);
            helper.setBackgroundRes(R.id.btn_go, R.drawable.btn_grd);
        }


        helper.getView(R.id.item_secodkill).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getLoadMoreViewPosition());
                }

            }


        });

        helper.getView(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getLoadMoreViewPosition());
                    Log.e(TAG, "onClick: " + getLoadMoreViewPosition());
                }
            }
        });
    }

    /**
     * 第一步：自定义一个回调接口来实现Click和LongClick事件
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v);
    }

    /**
     * 第二步：声明自定义的接口
     */
    public OnItemClickListener mOnItemClickListener;

    /**
     * 第三步：定义方法并暴露给外面的调用者
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
