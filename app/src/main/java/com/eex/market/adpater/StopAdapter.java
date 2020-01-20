package com.eex.market.adpater;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.Stoploss;

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
 * @Package: com.overthrow.market.adpater
 * @ClassName: StopAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/8/1 16:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/1 16:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StopAdapter extends RecyclerAdapter<Stoploss> {


    public StopAdapter(ArrayList<Stoploss> data) {
        super(R.layout.item_currentframent_stop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Stoploss item) {


        helper.addOnClickListener(R.id.tx_no);
        helper.setText(R.id.text_name, item.getCoinCode() + "_" + item.getFixPriceCoinCode());
        helper.setText(R.id.text_time, CommonUtil.getTimeSS(item.getCreateTime()));
        helper.setText(R.id.text_Sum, item.getDelAmount().stripTrailingZeros().toPlainString() + " " + item.getFixPriceCoinCode());
        helper.setText(R.id.text_nuber, mContext.getResources().getString(R.string.nuber11) + item.getDelNum().stripTrailingZeros().toPlainString());


        if (item.getTriggerState() == 0) {
            helper.setText(R.id.text_typedata, "未触发");
        }
        if (item.getTriggerState() == 1) {
            helper.setText(R.id.text_typedata, "触发成功");
        }
        if (item.getTriggerState() == 2) {
            helper.setText(R.id.text_typedata, "触法失败");
        }
        if (item.getTriggerState() == 3) {
            helper.setText(R.id.text_typedata, "已撤销");
        }


        if (item.getDealType().toString().equals("1")) {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.buy));
            helper.setTextColor(R.id.text_typedata, mContext.getResources().getColor(R.color.K_bul));
        } else {
            helper.setText(R.id.text_typedata, mContext.getResources().getString(R.string.sell));
            helper.setTextColor(R.id.text_typedata, mContext.getResources().getColor(R.color.appbar_background3));
        }


        helper.getView(R.id.tx_no).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getLoadMoreViewPosition());
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
    public StopAdapter.OnItemClickListener mOnItemClickListener;

    /**
     * 第三步：定义方法并暴露给外面的调用者
     */
    public void setOnItemClickListener(StopAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

