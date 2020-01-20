package com.eex.mvp.assrtsjava.activity.withdrawal;

import android.util.SparseBooleanArray;
import android.widget.RadioButton;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.assets.bean.FunCode;
import com.eex.common.base.RecyclerAdapter;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity.withdrawal
 * @ClassName: CashWithdrawalAdapter
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/26 11:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/26 11:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CashWithdrawalAdapter extends RecyclerAdapter<FunCode> {

    public SparseBooleanArray mBooleanArray;

    public CashWithdrawalAdapter(ArrayList<FunCode> list) {
        super(R.layout.re_item_recharge, list);

        mBooleanArray = new SparseBooleanArray(list.size());
    }

    @Override
    protected void convert(BaseViewHolder helper, FunCode item) {

        helper.setText(R.id.item_name, item.getCurrencyStr() + mContext.getResources().getString(R.string.Cash_withdrawal));
        helper.addOnClickListener(R.id.item_name);
        RadioButton button = helper.getView(R.id.item_name);
        if (mBooleanArray.get(helper.getAdapterPosition())) {
            button.setChecked(true);
        } else {
            button.setChecked(false);
        }
    }
}
