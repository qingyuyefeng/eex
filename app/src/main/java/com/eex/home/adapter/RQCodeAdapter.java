package com.eex.home.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.home.bean.payListVO;

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
 * @Package: com.overthrow.home.adapter
 * @ClassName: RQCodeAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 14:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 14:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RQCodeAdapter extends BaseQuickAdapter<payListVO, BaseViewHolder> {

    private FinalBitmap fb;

    public RQCodeAdapter(@LayoutRes int layoutResId, @Nullable List<payListVO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, payListVO item) {

        helper.addOnClickListener(R.id.LLBank);
        helper.addOnClickListener(R.id.LLzhibao);
        helper.addOnClickListener(R.id.LLwx);


        if (item.getAccountType() == 1) {

            helper.setVisible(R.id.LLBank, true);
            helper.setText(R.id.txBank, item.getUserName() + " " + item.getAccountNo());
            helper.setText(R.id.txBankname, item.getBankName() + " " + item.getBankAddress() + " " + item.getChildBankName());
        }

        if (item.getAccountType() == 2) {

            helper.setVisible(R.id.LLzhibao, true);
            helper.setText(R.id.txZhifubao, item.getUserName() + " " + item.getAccountNo());


            ImageView imageView = helper.getView(R.id.imgZfb);
            fb = FinalBitmap.create(mContext);
            fb.configLoadingImage(R.drawable.iconjiazaishibai);
            fb.configLoadfailImage(R.drawable.iconjiazaishibai);
            fb.display(imageView, WPConfig.PicBaseUrl + item.getImageUrl());
        }

        if (item.getAccountType() == 3) {
            helper.setVisible(R.id.LLwx, true);
            helper.setText(R.id.TXwx, item.getUserName() + " " + item.getAccountNo());


            ImageView imageView = helper.getView(R.id.imgWX);
            fb = FinalBitmap.create(mContext);
            fb.configLoadingImage(R.drawable.iconjiazaishibai);
            fb.configLoadfailImage(R.drawable.iconjiazaishibai);
            fb.display(imageView, WPConfig.PicBaseUrl + item.getImageUrl());
        }


    }


}
