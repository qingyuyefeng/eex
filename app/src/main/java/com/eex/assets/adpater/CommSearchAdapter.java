package com.eex.assets.adpater;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.Personal;

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
 * @ClassName: CommSearchAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 11:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 11:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommSearchAdapter extends BaseQuickAdapter<Personal, BaseViewHolder> {
    private FinalBitmap fb;

    public CommSearchAdapter(@LayoutRes int layoutResId, @Nullable List<Personal> data) {
        super(R.layout.item_commonwealfragent, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Personal item) {
        helper.setText(R.id.list_name, item.getCoinCode());
        if (item.getCoinName() == null || item.equals("")) {
            helper.setText(R.id.list_name2, "");
        } else {
            helper.setText(R.id.list_name2, "(" + item.getCoinName() + ")");
        }
        if (item.getTotalMoeny().compareTo(new BigDecimal(0)) == 0) {
            helper.setText(R.id.list_Nowmoney, "0");
        } else {
            helper.setText(R.id.list_Nowmoney, item.getTotalMoeny().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }
        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        ImageView imageView = helper.getView(R.id.img_tilte);
        fb.display(imageView, WPConfig.PicBaseUrl + item.getImgUrl() + "");
    }
}

