package com.eex.assets.adpater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eex.R;
import com.eex.assets.bean.ResultListdata;
import com.eex.common.util.CommonUtil;

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
 * @ClassName: WithdrawalsListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 11:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 11:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WithdrawalsListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity mContext;
    private List<ResultListdata> memactivity_uncompleted_list;

    public WithdrawalsListAdapter(Activity mContext, List<ResultListdata> memactivity_uncompleted_list) {
        this.mContext = mContext;
        this.memactivity_uncompleted_list = memactivity_uncompleted_list;
        this.inflater = LayoutInflater.from(mContext);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder = new ViewHoder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_tixian,null);
            viewHoder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            viewHoder.text_money = (TextView) convertView.findViewById(R.id.text_money);
            viewHoder.text_severmoney = (TextView) convertView.findViewById(R.id.severMoney);
            viewHoder.text_type = (TextView) convertView.findViewById(R.id.text_type);
            viewHoder.text_beizhu = (TextView) convertView.findViewById(R.id.text_beizhu);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        try {
            viewHoder.text_beizhu.setText(memactivity_uncompleted_list.get(position).getRemark());
        }catch (Exception e){
            viewHoder.text_beizhu.setText("");
        }
        viewHoder.text_time.setText(CommonUtil.getTimeSS(memactivity_uncompleted_list.get(position).getCreateTime()));
        if (memactivity_uncompleted_list.get(position).getCurrency()==null){
            viewHoder.text_money.setText(memactivity_uncompleted_list.get(position).getDealAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"人民币");
        }else {
            viewHoder.text_money.setText(memactivity_uncompleted_list.get(position).getDealAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+memactivity_uncompleted_list.get(position).getCurrency());
        }
        if (memactivity_uncompleted_list.get(position).getDealStatus().equals(1)){
            viewHoder.text_type.setText("待审核");
        }else if (memactivity_uncompleted_list.get(position).getDealStatus().equals(2)){
            viewHoder.text_type.setText("已完成");
        }else if (memactivity_uncompleted_list.get(position).getDealStatus().equals(3)){
            viewHoder.text_type.setText("已否决");
        }else if (memactivity_uncompleted_list.get(position).getDealStatus().equals(4)){
            viewHoder.text_type.setText("待处理");
        }else if (memactivity_uncompleted_list.get(position).getDealStatus().equals(5)){
            viewHoder.text_type.setText("充值失败");
        }else {
            viewHoder.text_type.setText("等待成交");
        }
        try {
            viewHoder.text_severmoney.setText(memactivity_uncompleted_list.get(position).getServiceCharge().setScale(2,BigDecimal.ROUND_HALF_EVEN).toString());

        }catch (Exception e){

        }
        return convertView;
    }
    class ViewHoder{
        private TextView text_time;
        private TextView text_money;
        private TextView text_severmoney;
        private TextView text_type;
        private TextView text_beizhu;

    }
}
