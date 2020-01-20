package com.eex.assets.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.common.base.UserConstants;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.activity.CurrencyActivity;
import com.eex.assets.bean.Bilistdata;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;

import com.eex.home.activity.login.LoginActivity;
import com.tencent.mmkv.MMKV;

import net.tsz.afinal.FinalBitmap;

import java.util.HashMap;
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
 * @ClassName: CurrencyListAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 12:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 12:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CurrencyListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity mContext;
    private FinalBitmap fb;
    private List<Bilistdata> memactivity_uncompleted_list;
    public MMKV kv = MMKV.mmkvWithID( UserConstants.USER_DAO,MMKV.MULTI_PROCESS_MODE);

    public CurrencyListAdapter(Activity mContext, List<Bilistdata> memactivity_uncompleted_list) {
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_currency_list, null);
            viewHoder.B_name = (TextView) convertView.findViewById(R.id.B_name);
            viewHoder.img_urll = (ImageView) convertView.findViewById(R.id.img_urll);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        fb.display(viewHoder.img_urll, WPConfig.PicBaseUrl + memactivity_uncompleted_list.get(position).getImgUrl() + "");
        viewHoder.B_name.setText(memactivity_uncompleted_list.get(position).getCoinCode());
        final Intent intent = new Intent();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isName(position);
            }
        });
        return convertView;
    }

    @SuppressLint("CheckResult")
    private void isName(final int position) {

        HashMap<String, String> hashMap = new HashMap<>();

        ApiFactory.getInstance()
                .authStauts(kv.decodeString("tokenId"),hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {
                        Intent intent = new Intent();
                        intent.putExtra("flage", "2");
                        intent.setClass(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.loginno), Toast.LENGTH_SHORT).show();
                    }

                    if (data.getData() != null) {
                        try {
                            if (data.getData().getLevel().equals(1)) {
                                Toast.makeText(mContext, "请完成实名等级2认证后操作", Toast.LENGTH_SHORT).show();
                            } else if (data.getData().getLevel().equals(2)) {
                                Intent intent = new Intent();
                                intent.putExtra("Bname", memactivity_uncompleted_list.get(position).getCoinCode());
                                intent.putExtra("currencyRate", memactivity_uncompleted_list.get(position).getCurrencyRate().stripTrailingZeros().toPlainString());
                                intent.putExtra("fixedRate", memactivity_uncompleted_list.get(position).getFixedRate().stripTrailingZeros().toPlainString());
                                intent.putExtra("coinMax", memactivity_uncompleted_list.get(position).getMaxNum().stripTrailingZeros().toPlainString());
                                intent.putExtra("coinMin", memactivity_uncompleted_list.get(position).getMinNum().stripTrailingZeros().toPlainString());
                                intent.setClass(mContext, CurrencyActivity.class);
                                mContext.startActivity(intent);

                            } else if (data.getData().getLevel().equals(3)) {

                                if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("Bname", memactivity_uncompleted_list.get(position).getCoinCode());
                                    intent.putExtra("currencyRate", memactivity_uncompleted_list.get(position).getCurrencyRate().stripTrailingZeros().toPlainString());
                                    intent.putExtra("fixedRate", memactivity_uncompleted_list.get(position).getFixedRate().stripTrailingZeros().toPlainString());
                                    intent.putExtra("coinMax", memactivity_uncompleted_list.get(position).getMaxNum().stripTrailingZeros().toPlainString());
                                    intent.putExtra("coinMin", memactivity_uncompleted_list.get(position).getMinNum().stripTrailingZeros().toPlainString());
                                    intent.setClass(mContext, CurrencyActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    Toast.makeText(mContext, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.isname), Toast.LENGTH_SHORT).show();
                        }
                    }


                }, throwable -> {

                });

    }

    class ViewHoder {
        private TextView B_name;
        private ImageView img_urll;
    }
}
