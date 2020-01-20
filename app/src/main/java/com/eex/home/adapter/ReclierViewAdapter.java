package com.eex.home.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.MainData;

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
 * @ClassName: ReclierViewAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 12:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 12:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReclierViewAdapter extends RecyclerAdapter<MainData> {


    public ReclierViewAdapter(ArrayList<MainData> datalist) {

        super(R.layout.item_main_up_frament, datalist);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainData item) {

        try {
            if (item.getDelKey() != null) {
                helper.setText(R.id.texview_name, item.getDelKey().substring(0, item.getDelKey().indexOf("_")));
                helper.setText(R.id.texview_name1, "/" + item.getDelKey().substring(item.getDelKey().indexOf("_") + 1, item.getDelKey().length()));
            } else {
                helper.setText(R.id.texview_name, "--");
            }
        } catch (Exception e) {

        }

        try {
            if (item.getNewPrice() != null) {

                if (item.getNewPrice().compareTo(item.getLastPrice()) == 1) {
                    helper.setTextColor(R.id.textview_newMoney, mContext.getResources().getColor(R.color.gred));
                    helper.setText(R.id.textview_newMoney, item.getNewPrice().setScale(6, BigDecimal.ROUND_DOWN).toString());
                } else if (item.getNewPrice().compareTo(item.getLastPrice()) == 0) {
                    helper.setTextColor(R.id.textview_newMoney, mContext.getResources().getColor(R.color.gred));
                    helper.setText(R.id.textview_newMoney, item.getNewPrice().setScale(6, BigDecimal.ROUND_DOWN).toString());
                } else {
                    helper.setTextColor(R.id.textview_newMoney, mContext.getResources().getColor(R.color.appbar_background3));
                    helper.setText(R.id.textview_newMoney, item.getNewPrice().setScale(6, BigDecimal.ROUND_DOWN).toString());
                }

            } else {
                helper.setText(R.id.textview_newMoney, "--");
            }
        } catch (Exception e) {
            helper.setText(R.id.textview_newMoney, "--");
        }

        try {
            if (item.getNewPrice() != null && item.getLastPrice() != null) {
                BigDecimal money = item.getNewPrice().divide(item.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                if (newData.compareTo(BigDecimal.ZERO) == 1) {
                    helper.setText(R.id.texview_td, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                    helper.setBackgroundRes(R.id.texview_td, R.drawable.btn_greed);
                } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                    helper.setBackgroundRes(R.id.texview_td, R.drawable.backbtn);
                    helper.setText(R.id.texview_td, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                }
            }
        } catch (Exception e) {

        }
        helper.addOnClickListener(R.id.ll_Btn);


        helper.getView(R.id.ll_Btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//
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
    public OnItemClickListener mOnItemClickListener;

    /**
     * 第三步：定义方法并暴露给外面的调用者
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
