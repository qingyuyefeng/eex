package com.eex.assets.adpater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eex.R;
import com.eex.assets.bean.BankList;

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
 * @ClassName: BankListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 15:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 15:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BankListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity mContext;
    private List<BankList> memactivity_uncompleted_list;
    public BankListAdapter(Activity mContext, List<BankList> memactivity_uncompleted_list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView==null){
            viewHoder = new ViewHoder();
            convertView =inflater.inflate(R.layout.item_banklist,null);
            viewHoder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        viewHoder.textView.setText(memactivity_uncompleted_list.get(position).getText());
        return convertView;
    }
    class ViewHoder{
        private TextView textView;
        private ImageView imageview;
    }
}
