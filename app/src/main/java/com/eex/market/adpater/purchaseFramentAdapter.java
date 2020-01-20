package com.eex.market.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.Sell;

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
 * @Package: com.overthrow.market.adpater
 * @ClassName: purchaseFramentAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/25 12:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/25 12:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class purchaseFramentAdapter extends BaseAdapter   {

    private LayoutInflater inflater;
    private Activity mContext;
    private List<Sell> memactivity_uncompleted_list;

    public purchaseFramentAdapter(Activity mContext, List<Sell> memactivity_uncompleted_list) {
        this.mContext = mContext;
        this.memactivity_uncompleted_list = memactivity_uncompleted_list;
        this.inflater = LayoutInflater.from(mContext);
    }




    @Override
    public int getCount() {
        if (memactivity_uncompleted_list.size() == 0) {
            return 0;
        }
        return memactivity_uncompleted_list.size();
    }



    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder = new ViewHoder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_purchase, null);
            viewHoder.textview_Price = (TextView) convertView.findViewById(R.id.textview_Price);
            viewHoder.textview_nuber = (TextView) convertView.findViewById(R.id.textview_nuber);
            viewHoder.simpleProgressbar = (ProgressBar) convertView.findViewById(R.id.probar);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        if (memactivity_uncompleted_list.get(position).getDelAmount() == null) {
            Toast.makeText(mContext, "请再次刷新页面", Toast.LENGTH_SHORT).show();
        }

        viewHoder.textview_Price.setText(memactivity_uncompleted_list.get(position).getDelAmount().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "");
        viewHoder.textview_nuber.setText(memactivity_uncompleted_list.get(position).getResidueNum().setScale(4, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "");


        viewHoder.textview_Price.setTextColor(CommonUtil.getColor(R.color.K_red));
        BigDecimal max = memactivity_uncompleted_list.get(0).getResidueNum().multiply(new BigDecimal(100));
        for (int i = 0; i < memactivity_uncompleted_list.size(); i++) {
            if (max.compareTo(memactivity_uncompleted_list.get(position).getResidueNum()) == -1) {
                max = memactivity_uncompleted_list.get(position).getResidueNum();
            }
        }
        BigDecimal str = max.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal newStr = memactivity_uncompleted_list.get(position).getResidueNum().divide(str, 10, BigDecimal.ROUND_HALF_UP);
        int add = newStr.intValue();
        viewHoder.simpleProgressbar.setProgress(add);
        viewHoder.simpleProgressbar.setMax(10);

        return convertView;
    }


    class ViewHoder {
        private TextView textview_Price;
        private TextView textview_nuber;
        private ProgressBar simpleProgressbar;
    }


}


//public class purchaseFramentAdapter extends RecyclerAdapter<Sell> {
//
//    private ProgressBar simpleProgressbar;
//    List<Sell> data = new ArrayList<>();
//
//
//    public purchaseFramentAdapter(List<Sell> data) {
//        super(R.layout.item_purchase, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, Sell item) {
//
//        if (item == null) {
//            Toast.makeText(mContext, "请再次刷新页面", Toast.LENGTH_SHORT).show();
//        }
//
//        helper.setText(R.id.textview_Price, item.getDelAmount().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "");
//        helper.setTextColor(R.id.textview_Price, CommonUtil.getColor(R.color.K_red));
//        helper.setText(R.id.textview_nuber, item.getResidueNum().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "");
//
//        simpleProgressbar = helper.convertView.findViewById(R.id.probar);
//
//        BigDecimal max = item.getResidueNum().multiply(new BigDecimal(100));
//        for (int i = 0; i < data.size(); i++) {
//            if (max.compareTo(data.get(i).getResidueNum()) == -1) {
//                max = data.get(i).getResidueNum();
//            }
//        }
//        BigDecimal str = max.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP);
//        BigDecimal newStr = item.getResidueNum().divide(str, 10, BigDecimal.ROUND_HALF_UP);
//        int add = newStr.intValue();
//        simpleProgressbar.setProgress(add);
//        simpleProgressbar.setMax(10);
//
//    }
//}
