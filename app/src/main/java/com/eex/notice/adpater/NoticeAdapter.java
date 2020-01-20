package com.eex.notice.adpater;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.common.base.RecyclerAdapter;
import com.eex.notice.bean.NoticeBean;

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
 * @ClassName: NoticeAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/5 10:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/5 10:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NoticeAdapter extends RecyclerAdapter<NoticeBean> {


    public NoticeAdapter(@Nullable ArrayList<NoticeBean> list) {
        super(R.layout.item_notice, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean item) {

        helper.setText(R.id.tx_name,item.getCategoryName());
        helper.addOnClickListener(R.id.tx_name);

    }
}
