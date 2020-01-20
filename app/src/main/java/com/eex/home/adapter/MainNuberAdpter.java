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
 * @ClassName: MainNuberAdpter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/19 11:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 24h交易排行榜
 */
public class MainNuberAdpter extends RecyclerAdapter<MainData> {


    public MainNuberAdpter(ArrayList<MainData> datalist) {
        super(R.layout.item_main_nuber, datalist);
    }


    @Override
    protected void convert(BaseViewHolder helper, MainData item) {


        helper.setText(R.id.texview_nuber, item.getDealNum().multiply(item.getNewPrice()).setScale(0, BigDecimal.ROUND_DOWN).toString() + "");
        if (item.getDelKey() != null) {
            helper.setText(R.id.texview_name, item.getDelKey().substring(0, item.getDelKey().indexOf("_")));
            helper.setText(R.id.texview_name1, "/" + item.getDelKey().substring(item.getDelKey().indexOf("_") + 1, item.getDelKey().length()));
        } else {
            helper.setText(R.id.texview_name, "---");
        }

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
            helper.setText(R.id.textview_newMoney, "---");
        }

        helper.getView(R.id.ll_Btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, getLoadMoreViewPosition());
                }

            }


        });


        helper.addOnClickListener(R.id.ll_Btn);
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
    public MainNuberAdpter.OnItemClickListener mOnItemClickListener;

    /**
     * 第三步：定义方法并暴露给外面的调用者
     */
    public void setOnItemClickListener(MainNuberAdpter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
