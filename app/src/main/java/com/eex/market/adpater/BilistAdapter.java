package com.eex.market.adpater;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.util.CommonUtil;
import com.eex.home.bean.MainData;

import java.math.BigDecimal;
import java.util.List;

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
 * @ClassName: BilistAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/27 15:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/27 15:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class BilistAdapter extends BaseQuickAdapter<MainData, BaseViewHolder> {

    public BilistAdapter(@LayoutRes int layoutResId, @Nullable List<MainData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainData item) {
        if (item.getDelKey().equals("")) {
            helper.setText(R.id.textView_biName, "--");
        } else {
            helper.setText(R.id.textView_biName, item.getDelKey());
        }
        try {
            if (item.getNewPrice().equals(null)) {
                helper.setText(R.id.textView_biNuber, "--");
            } else {
                helper.setText(R.id.textView_biNuber, item.getNewPrice().setScale(4, BigDecimal.ROUND_CEILING).toString());
            }
        } catch (Exception e) {
            helper.setText(R.id.textView_biNuber, "--");
        }
        try {
            if (item.getNewPrice() != null && item.getLastPrice() != null) {
                BigDecimal money = item.getNewPrice().divide(item.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
                if (newData.compareTo(BigDecimal.ZERO) == 1) {
                    helper.setText(R.id.textView_biProportion, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                    helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.K_bul));
                } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
                    helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.appbar_background3));
                    helper.setText(R.id.textView_biProportion, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
                } else {
                    helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.appbar_background3));
                    helper.setText(R.id.textView_biProportion, "---");
                }
            } else {
                helper.setTextColor(R.id.textView_biProportion,CommonUtil.getColor(R.color.appbar_background3));
                helper.setText(R.id.textView_biProportion, "---");
            }
        } catch (Exception e) {

        }


    }
}

//public class BilistAdapter extends RecyclerAdapter<MainData> {
//
//
//
//    public BilistAdapter(ArrayList<MainData> data) {
//        super(R.layout.item_summary, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MainData item) {
//
//        if (item.getDelKey().equals("")) {
//            helper.setText(R.id.textView_biName, "--");
//        } else {
//            helper.setText(R.id.textView_biName, item.getDelKey());
//        }
//
//
//        try {
//
//            if (item.getNewPrice().equals("")) {
//                helper.setText(R.id.textView_biNuber, "--");
//            } else {
//                helper.setText(R.id.textView_biNuber, item.getNewPrice().setScale(4, BigDecimal.ROUND_CEILING).toString());
//            }
//
//        } catch (Exception e) {
//            helper.setText(R.id.textView_biNuber, "--");
//        }
//
//        try {
//
//            if (item.getNewPrice() != null && item.getLastPrice() != null) {
//                BigDecimal money = item.getNewPrice().divide(item.getLastPrice(), 10, BigDecimal.ROUND_HALF_EVEN);
//                BigDecimal newData = money.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
//
//                if (newData.compareTo(BigDecimal.ZERO) == 1) {
//                    helper.setText(R.id.textView_biProportion, "+" + newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
//                    helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.K_bul));
//                } else if (newData.compareTo(BigDecimal.ZERO) == -1) {
//                    helper.setText(R.id.textView_biProportion, newData.setScale(2, BigDecimal.ROUND_DOWN) + "%");
//                    helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.K_red));
//                } else {
//                    helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.K_red));
//                    helper.setText(R.id.textView_biProportion, "0.00%");
//                }
//            } else {
//                helper.setTextColor(R.id.textView_biProportion, CommonUtil.getColor(R.color.K_red));
//                helper.setText(R.id.textView_biProportion, "---");
//            }
//        } catch (Exception e) {
//            helper.setText(R.id.textView_biProportion, "--");
//        }
//
//
//    }
//}
