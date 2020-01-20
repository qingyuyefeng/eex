package com.eex.assets.adpater;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.bean.Bilistdata;

import net.tsz.afinal.FinalBitmap;

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
 * @ClassName: AearchCurrAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 12:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 12:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AearchCurrAdapter extends  BaseQuickAdapter<Bilistdata, BaseViewHolder> {

    private FinalBitmap fb;

    public AearchCurrAdapter(int layoutResId, @Nullable List<Bilistdata> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bilistdata item) {
        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        ImageView imageView = helper.getView(R.id.img_url);
        fb.display(imageView, WPConfig.PicBaseUrl + item.getImgUrl() + "");
        helper.setText(R.id.name, item.getCoinCode());
    }
}
