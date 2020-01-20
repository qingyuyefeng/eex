package com.eex.mine.adpater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.eex.R;
import com.eex.market.bean.Delegation;
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
 * @Package: com.overthrow.mine.adpater
 * @ClassName: TransactionAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/8 16:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/8 16:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TransactionAdapter extends BaseAdapter {
  private LayoutInflater inflater;
  private Activity mContext;
  private String data;
  private List<Delegation> memactivity_uncompleted_list;

  public TransactionAdapter(Activity mContext, List<Delegation> memactivity_uncompleted_list) {
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
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHoder viewHoder = new ViewHoder();
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_transactiondata, null);
      viewHoder.text_name = (TextView) convertView.findViewById(R.id.text_name);
      viewHoder.text_time = (TextView) convertView.findViewById(R.id.text_time);
      viewHoder.text_Sum = (TextView) convertView.findViewById(R.id.text_Sum);
      viewHoder.text_nuber = (TextView) convertView.findViewById(R.id.text_nuber);
      viewHoder.text_typedata = (TextView) convertView.findViewById(R.id.text_typedata);
      viewHoder.text_type = (TextView) convertView.findViewById(R.id.text_type);
      viewHoder.text_resnuber = (TextView) convertView.findViewById(R.id.text_resnuber);
      convertView.setTag(viewHoder);
    } else {
      viewHoder = (ViewHoder) convertView.getTag();
    }
    viewHoder.text_name.setText(memactivity_uncompleted_list.get(position).getCoinCode()
        + "_"
        + memactivity_uncompleted_list.get(position).getFixPriceCoinCode());
    viewHoder.text_time.setText(memactivity_uncompleted_list.get(position).getCreateTime());
    if (memactivity_uncompleted_list.get(position).getDealType().toString().equals("1")) {
      viewHoder.text_typedata.setText(convertView.getResources().getString(R.string.shouxfei)
          + memactivity_uncompleted_list.get(position)
          .getServiceCharge()
          .stripTrailingZeros()
          .toPlainString()
          + " "
          + memactivity_uncompleted_list.get(position).getCoinCode());
      viewHoder.text_type.setText(R.string.buy);
      viewHoder.text_type.setBackgroundColor(convertView.getResources().getColor(R.color.K_bul));
    } else {
      viewHoder.text_typedata.setText(convertView.getResources().getString(R.string.shouxfei)
          + memactivity_uncompleted_list.get(position)
          .getServiceCharge()
          .setScale(8, BigDecimal.ROUND_DOWN)
          .stripTrailingZeros()
          .toPlainString()
          + memactivity_uncompleted_list.get(position).getFixPriceCoinCode());
      viewHoder.text_type.setText(R.string.sell);
      viewHoder.text_type.setBackgroundColor(
          convertView.getResources().getColor(R.color.appbar_background3));
    }

    if (memactivity_uncompleted_list.get(position).getDelStatus() == 1) {
      viewHoder.text_typedata.setText(convertView.getResources().getString(R.string.type1));
    }
    if (memactivity_uncompleted_list.get(position).getDelStatus() == 2) {
      viewHoder.text_typedata.setText(convertView.getResources().getString(R.string.shouxfei)
          + memactivity_uncompleted_list.get(position)
          .getServiceCharge()
          .setScale(8, BigDecimal.ROUND_DOWN)
          .stripTrailingZeros()
          .toPlainString()
          + memactivity_uncompleted_list.get(position).getFixPriceCoinCode());
      viewHoder.text_nuber.setText(convertView.getResources().getString(R.string.nuberye)
          + ":"
          + memactivity_uncompleted_list.get(position)
          .getDelNum()
          .subtract(memactivity_uncompleted_list.get(position).getResidueNum())
          .stripTrailingZeros()
          .toPlainString());
    } else {
      viewHoder.text_nuber.setText(convertView.getResources().getString(R.string.nuberye)
          + ":"
          + memactivity_uncompleted_list.get(position)
          .getDelNum()
          .subtract(memactivity_uncompleted_list.get(position).getResidueNum())
          .stripTrailingZeros()
          .toPlainString());
    }
    BigDecimal Nuber = memactivity_uncompleted_list.get(position)
        .getDelNum()
        .subtract(memactivity_uncompleted_list.get(position).getResidueNum());

    if (memactivity_uncompleted_list.get(position).getAveAmount() != null) {
      viewHoder.text_Sum.setText(
          Nuber.multiply(memactivity_uncompleted_list.get(position).getAveAmount())
              .setScale(8, BigDecimal.ROUND_DOWN)
              .stripTrailingZeros()
              .toPlainString() + " " + memactivity_uncompleted_list.get(position)
              .getFixPriceCoinCode());
      viewHoder.text_resnuber.setText(convertView.getResources().getString(R.string.junprice)
          + memactivity_uncompleted_list.get(position)
          .getAveAmount()
          .stripTrailingZeros()
          .toPlainString()
          + " "
          + memactivity_uncompleted_list.get(position).getFixPriceCoinCode());
    } else {
      viewHoder.text_resnuber.setText(convertView.getResources().getString(R.string.junprice)
          + memactivity_uncompleted_list.get(position)
          .getDelAmount()
          .stripTrailingZeros()
          .toPlainString()
          + " "
          + memactivity_uncompleted_list.get(position).getFixPriceCoinCode());
      viewHoder.text_Sum.setText(
          Nuber.multiply(memactivity_uncompleted_list.get(position).getDelNum())
              .setScale(8, BigDecimal.ROUND_DOWN)
              .stripTrailingZeros()
              .toPlainString() + " " + memactivity_uncompleted_list.get(position)
              .getFixPriceCoinCode());
    }

    return convertView;
  }

  private class ViewHoder {
    private TextView text_name, text_time, text_typedata, text_Sum, text_nuber, text_type,
        text_resnuber;
  }
}
