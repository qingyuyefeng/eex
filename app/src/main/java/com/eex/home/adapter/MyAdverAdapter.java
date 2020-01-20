package com.eex.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.home.bean.AdvertisingUser;

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
 * @Package: com.overthrow.home.adapter
 * @ClassName: MyAdverAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/30 16:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/30 16:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MyAdverAdapter extends RecyclerAdapter<AdvertisingUser> {

    public MyAdverAdapter(int layoutResId, @Nullable List<AdvertisingUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvertisingUser item) {
        if (item.getTradeType()==1){
            helper.setText(R.id.coinName,item.getTradeCoin()+"卖出");
        }else {
            helper.setText(R.id.coinName,item.getTradeCoin()+"买入");
        }
        if (item.getMargin()!=null){
            helper.setText(R.id.baner,"保证金: "+item.getMargin().stripTrailingZeros().toPlainString()+item.getMarginCoin());
        }else {
            helper.setText(R.id.baner,"保证金: 0"+item.getMarginCoin());
        }

        if (item.getAccountType().size()!=0){
            if (item.getAccountType().get(0)==1){
                helper.setVisible(R.id.yinghangka, true);
            }
            if (item.getAccountType().get(0)==2){
                helper.setVisible(R.id.zhifubao, true);
            }
            if (item.getAccountType().get(0)==3){
                helper.setVisible(R.id.weixin, true);
            }

            try {
                if (item.getAccountType().get(1)==1){
                    helper.setVisible(R.id.yinghangka, true);
                }
                if (item.getAccountType().get(1)==2){
                    helper.setVisible(R.id.zhifubao, true);
                }
                if (item.getAccountType().get(1)==3){
                    helper.setVisible(R.id.weixin, true);
                }
            }catch (Exception e){

            }

            try {
                if (item.getAccountType().get(2)==1){
                    helper.setVisible(R.id.yinghangka, true);
                }
                if (item.getAccountType().get(2)==2){
                    helper.setVisible(R.id.zhifubao, true);
                }
                if (item.getAccountType().get(2)==3){
                    helper.setVisible(R.id.weixin, true);
                }
            }catch (Exception e){

            }
        }
        BigDecimal price = item.getTradePrice().multiply(item.getPremium());
        helper.setText(R.id.UnitPrice,price.setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()+"CNY");
        if (item.getPremium().subtract(new BigDecimal("1")).multiply(new BigDecimal(100)).compareTo(new BigDecimal(0))==0){
            helper.setText(R.id.premium,"0%");
        }else {
            helper.setText(R.id.premium,(item.getPremium().subtract(new BigDecimal("1"))).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()+"%");
        }

        helper.setText(R.id.nuber,item.getQuantity().stripTrailingZeros().toPlainString()+item.getTradeCoin());
        if (item.getAdOKNum()!=null){
            helper.setText(R.id.WYNuber,item.getAdOKNum().stripTrailingZeros().toPlainString()+item.getTradeCoin());
        }else {
            helper.setText(R.id.WYNuber,"0"+item.getTradeCoin());
        }


        helper.setText(R.id.Quota,item.getMinTradeNum().stripTrailingZeros().toPlainString()+"CNY-"+item.getMaxTradeNum().stripTrailingZeros().toPlainString()+"CNY");
        helper.setText(R.id.time,item.getCreateTime());
        helper.addOnClickListener(R.id.LLEditors);
        helper.addOnClickListener(R.id.LLdelete);
        helper.addOnClickListener(R.id.LLLower);
    }
}
