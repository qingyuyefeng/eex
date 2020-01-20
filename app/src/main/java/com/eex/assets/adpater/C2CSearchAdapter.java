package com.eex.assets.adpater;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.CtcaccountList;
import com.eex.common.base.RecyclerAdapter;

import net.tsz.afinal.FinalBitmap;

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
 * @Package: com.overthrow.assets.adpater
 * @ClassName: C2CSearchAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 14:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 14:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class C2CSearchAdapter extends RecyclerAdapter<CtcaccountList> {

    private FinalBitmap fb;


    public C2CSearchAdapter(List<CtcaccountList> newList) {
        super(R.layout.item_c2cmoney_frament,newList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CtcaccountList item) {

        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        ImageView imageView = helper.getView(R.id.img_tilte);
        fb.display(imageView, WPConfig.PicBaseUrl + item.getImgUrl() + "");
        helper.setText(R.id.list_name, item.getCoinCode());
        helper.setText(R.id.list_name2, "(" + item.getCoinCode() + ")");

        if (item.getUsableMoney().compareTo(new BigDecimal(0)) == 0) {
            helper.setText(R.id.list_Nowmoney, "0");
        } else {
            helper.setText(R.id.list_Nowmoney, item.getUsableMoney().add(item.getFrozenMoney()).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }
    }
}
