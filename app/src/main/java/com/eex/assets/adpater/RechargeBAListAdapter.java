package com.eex.assets.adpater;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.activity.CurrencyAddresListActivity;
import com.eex.assets.activity.RechargeMoneyBActivity;
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
 * @ClassName: RechargeBAListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 13:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 13:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RechargeBAListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity mContext;
    private FinalBitmap fb;
    private List<Bilistdata> memactivity_uncompleted_list;

    public RechargeBAListAdapter(Activity mContext, List<Bilistdata> memactivity_uncompleted_list) {
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
        ViewHouder viewHouder;
        viewHouder = new ViewHouder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_rechargba,null);
            viewHouder.name = (TextView) convertView.findViewById(R.id.name);
            viewHouder.img_url = (ImageView) convertView.findViewById(R.id.img_url);
            convertView.setTag(viewHouder);
        }else {
            viewHouder = (ViewHouder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",memactivity_uncompleted_list.get(position).getCoinCode());
                intent.putExtra("id",memactivity_uncompleted_list.get(position).getId());
                    //跳转币账户
                if (mContext.getIntent().getStringExtra("Type")!=null){

                    if (mContext.getIntent().getStringExtra("Type").equals("BiListData")){
                        intent.putExtra("BiListData",memactivity_uncompleted_list.get(position).getCoinCode());
                        intent.putExtra("BiAddreiss",memactivity_uncompleted_list.get(position).getCoinId());
                        intent.setClass(mContext,CurrencyAddresListActivity.class);
                        mContext.startActivity(intent);
                    }
                    if (mContext.getIntent().getStringExtra("Type").equals("CurrencyAddress")){
                        intent.putExtra("currencyType",memactivity_uncompleted_list.get(position).getCoinCode());
                        mContext.setResult(5000,intent);
                        mContext.finish();
                    }

                }else {//跳转冲币选币界面

                    intent.setClass(mContext,RechargeMoneyBActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });
        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        fb.display(viewHouder.img_url, WPConfig.PicBaseUrl +memactivity_uncompleted_list.get(position).getImgUrl()+"");
        viewHouder.name.setText(memactivity_uncompleted_list.get(position).getCoinCode());
        return convertView;
    }
    class ViewHouder{
        private TextView name;
        private ImageView img_url;
    }
}
