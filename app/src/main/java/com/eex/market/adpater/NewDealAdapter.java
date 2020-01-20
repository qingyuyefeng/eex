package com.eex.market.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eex.R;
import com.eex.common.util.CommonUtil;
import com.eex.market.bean.PurchaseDatalIst;

import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @ClassName: NewDealAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/24 15:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 15:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewDealAdapter extends BaseAdapter {
    private List <PurchaseDatalIst> memactivity_uncompleted_list;
    private LayoutInflater inflater;
    private Context mContext;

    public NewDealAdapter(List<PurchaseDatalIst> memactivity_uncompleted_list, Context mContext) {
        this.memactivity_uncompleted_list = memactivity_uncompleted_list;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }
    @SuppressWarnings("unchecked")
    public void setDeviceList(ArrayList<PurchaseDatalIst> list) {
        if (list != null) {
            memactivity_uncompleted_list = (ArrayList<PurchaseDatalIst>) list.clone();
            notifyDataSetChanged();
        }
    }
    public void clearDeviceList() {
        if (memactivity_uncompleted_list != null) {
            memactivity_uncompleted_list.clear();
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (memactivity_uncompleted_list.size() == 0)
            return 0;
        return memactivity_uncompleted_list.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder = new ViewHoder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_new_deal,null);
            viewHoder.time = (TextView) convertView.findViewById(R.id.time);
            viewHoder.money = (TextView) convertView.findViewById(R.id.money);
            viewHoder.nuber = (TextView) convertView.findViewById(R.id.nuber);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        try {
            if (memactivity_uncompleted_list.get(position+1).getDealPrice().compareTo(memactivity_uncompleted_list.get(position).getDealPrice())==1){
                viewHoder.money.setTextColor(convertView.getResources().getColor(R.color.K_red));
            }else {
                viewHoder.money.setTextColor(convertView.getResources().getColor(R.color.K_bul));
            }
        }catch (Exception e){
            viewHoder.money.setTextColor(convertView.getResources().getColor(R.color.K_red));
        }

        viewHoder.time.setText(CommonUtil.getTimeSS(memactivity_uncompleted_list.get(position).getDealTime()));
        viewHoder.money.setText(memactivity_uncompleted_list.get(position).getDealPrice().setScale(5, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString());
        viewHoder.nuber.setText(memactivity_uncompleted_list.get(position).getDealNum().setScale(5, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString());
        return convertView;
    }
    class  ViewHoder {
        private TextView time;
        private TextView money;
        private TextView nuber;
    }
}



//public class NewDealAdapter extends RecyclerAdapter<PurchaseDatalIst> {
//
//
//    public NewDealAdapter(ArrayList<PurchaseDatalIst> datalIsts) {
//        super(R.layout.item_new_deal,datalIsts);
//    }
//
//
//    @Override
//    protected void convert(BaseViewHolder helper, PurchaseDatalIst item) {
//
//        try {
//            if (item.getDealPrice().compareTo(item.getDealPrice()) == 1) {
//                helper.setTextColor(R.id.money, CommonUtil.getColor(R.color.K_red));
//            } else {
//                helper.setTextColor(R.id.money, CommonUtil.getColor(R.color.K_bul));
//            }
//        } catch (Exception e) {
//            helper.setTextColor(R.id.money, CommonUtil.getColor(R.color.K_red));
//
//        }
//
//        helper.setText(R.id.time, CommonUtil.getTimeSS(item.getDealTime()));
//        helper.setText(R.id.money, item.getDealPrice().setScale(5, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString());
//        helper.setText(R.id.nuber, item.getDealNum().setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString().toString());
//
//    }
//}
