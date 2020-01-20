package com.eex.market.adpater;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.home.bean.MainList;

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
 * @ClassName: SearchAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/10 16:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/10 16:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
//public class SearchAdapter extends RecyclerAdapter<MainList> {
//
//
//    public SearchAdapter(ArrayList<MainList> newList) {
//        super(R.layout.item_search, newList);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MainList item) {
//        helper.setText(R.id.tx_Biname1, item.getDealPair().substring(0, item.getDealPair().indexOf("_")) + "/");
//        helper.setText(R.id.texview_name1, item.getDealPair().substring(item.getDealPair().indexOf("_") + 1, item.getDealPair().length()));
//        helper.addOnClickListener(R.id.ckbox);
//    }
//}
public class SearchAdapter extends BaseQuickAdapter<MainList, BaseViewHolder> {

    public  SearchAdapter(@LayoutRes int layoutResId, @Nullable List<MainList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainList item) {
        helper.setText(R.id.tx_Biname1,item.getDealPair().substring(0,item.getDealPair().indexOf("_"))+"/");
        helper.setText(R.id.texview_name1,item.getDealPair().substring(item.getDealPair().indexOf("_")+1, item.getDealPair().length()));
        helper.addOnClickListener(R.id.ckbox);
    }
}