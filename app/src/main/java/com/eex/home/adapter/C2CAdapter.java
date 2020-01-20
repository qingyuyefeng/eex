package com.eex.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.home.bean.Advertisingvo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
 * @Package: com.overthrow.home.adapter
 * @ClassName: C2CAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 17:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 17:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CAdapter extends BaseQuickAdapter<Advertisingvo,BaseViewHolder> {


    public C2CAdapter(int layoutResId, @Nullable List<Advertisingvo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Advertisingvo item) {
        helper.addOnClickListener(R.id.btn_buy);
        if (item.getMerchantStatus() == 2) {
            helper.setVisible(R.id.img_type1, true);
        } else {
            helper.setVisible(R.id.img_type1, false);
        }
        helper.setText(R.id.name_n, "商");

        helper.setText(R.id.name, item.getMerchName());
        helper.setText(R.id.nuber, "数量" + item.getTradeNum().stripTrailingZeros().toPlainString() + item.getTradeCoin());
        helper.setText(R.id.price, "¥" + item.getPremium().multiply(item.getTradePrice()).setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
        helper.setText(R.id.xiane, "限额" + item.getMinTradeNum().stripTrailingZeros().toPlainString() + "-" + item.getMaxTradeNum().stripTrailingZeros().toPlainString() + "CNY");
        if (item.getTradeOKCount() == 0 || item.getTradeCount() == 0) {
            helper.setText(R.id.rate, "交易次数" + item.getTradeCount() + "/成交率" + "0%");
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            helper.setText(R.id.rate, "交易次数" + item.getTradeCount() + "/成交率" + df.format((float) item.getTradeOKCount() / item.getTradeCount() * 100) + "%");
        }

        if (item.getAccountType().size() != 0) {
            if (item.getAccountType().get(0) == 1) {
                helper.setVisible(R.id.yinghangka, true);
            }
            if (item.getAccountType().get(0) == 2) {
                helper.setVisible(R.id.zhifubao, true);
            }
            if (item.getAccountType().get(0) == 3) {
                helper.setVisible(R.id.weixin, true);
            }

            try {
                if (item.getAccountType().get(1) == 1) {
                    helper.setVisible(R.id.yinghangka, true);
                }
                if (item.getAccountType().get(1) == 2) {
                    helper.setVisible(R.id.zhifubao, true);
                }
                if (item.getAccountType().get(1) == 3) {
                    helper.setVisible(R.id.weixin, true);
                }
            } catch (Exception e) {

            }

            try {
                if (item.getAccountType().get(2) == 1) {
                    helper.setVisible(R.id.yinghangka, true);
                }
                if (item.getAccountType().get(2) == 2) {
                    helper.setVisible(R.id.zhifubao, true);
                }
                if (item.getAccountType().get(2) == 3) {
                    helper.setVisible(R.id.weixin, true);
                }
            } catch (Exception e) {

            }

        }



    }
}
