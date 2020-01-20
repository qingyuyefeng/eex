package com.eex.mvp.assrtsjava.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.Personal;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.util.CommonUtil;
import com.eex.mvp.assrtsjava.activity.CommMoneyDetailsActivity;

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
 * @ProjectName: Futures
 * @Package: com.futures.assets.adapter
 * @ClassName: TradingAccountAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/10/16 14:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/16 14:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TradingAccountAdapter extends RecyclerAdapter<Personal> {

    public TradingAccountAdapter(List<Personal> mNewList) {
        super(R.layout.re_item_tradingaccount, mNewList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Personal item) {

        helper.setText(R.id.item_name, item.getCoinCode());

        helper.setText(R.id.item_name2, "(" + item.getCoinCode() + ")");


        ImageView imageView = helper.getView(R.id.item_img_tilte);
        Glide.with(mContext).load( WPConfig.PicBaseUrl +item.getImgUrl() + "").into(imageView);



        if (item.getCoinName() == null || item.getCoinName().equals("")) {
            helper.setText(R.id.item_Nowmoney, "0");
        } else {
            helper.setText(R.id.item_Nowmoney, "(" + item.getCoinName() + ")");
        }
        if (item.getTotalMoeny().compareTo(new BigDecimal(0)) == 0) {
            helper.setText(R.id.item_Nowmoney, "0");
        } else {
            helper.setText(R.id.item_Nowmoney, item.getTotalMoeny().setScale(4, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }

        helper.addOnClickListener(R.id.item_linear);
        helper.getView(R.id.item_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                if (CommonUtil.isFastClick()) {
                    Intent intent = new Intent();
                    intent.putExtra("coin", item.getCoinCode());
                    intent.putExtra("dongjie", item.getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("imgAddress", item.getImgUrl());
                    intent.putExtra("coinName", item.getCoinName());
                    intent.putExtra("CoinMoney", item.getCoinMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("FrozenMoney", item.getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("TotalMoeny", item.getTotalMoeny().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.setClass(mContext, CommMoneyDetailsActivity.class);
                    mContext.startActivity(intent);

                }
            }
        });

    }
}
