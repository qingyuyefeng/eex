package com.eex.notice.adpater;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.mine.activity.NewsPersionActivity;
import com.eex.notice.bean.IndustryBean;

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
 * @Package: com.overthrow.notice.adpater
 * @ClassName: NewAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/11 15:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/11 15:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewAdapter extends RecyclerAdapter<IndustryBean> {

    public NewAdapter(ArrayList<IndustryBean> list) {
        super(R.layout.item_newdata, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndustryBean item) {

        helper.setText(R.id.txname,item.getTitle()+"");
        helper.setText(R.id.tx_time,item.getCreateTime()+"");

        helper.setOnClickListener(R.id.linear_ll, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",item.getId());
                intent.setClass(mContext, NewsPersionActivity.class);
                mContext.startActivity(intent);
            }
        });

    }
}
