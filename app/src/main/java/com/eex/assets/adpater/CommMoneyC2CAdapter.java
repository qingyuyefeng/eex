package com.eex.assets.adpater;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.assets.activity.C2CDetailsActivity;
import com.eex.assets.bean.CtcaccountList;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.RecyclerAdapter;
import com.eex.common.base.UserConstants;
import com.eex.common.util.CommonUtil;
import com.tencent.mmkv.MMKV;

import net.tsz.afinal.FinalBitmap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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
 * @ClassName: CommMoneyC2CAdapter
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 13:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 13:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommMoneyC2CAdapter extends RecyclerAdapter<CtcaccountList> {

    private FinalBitmap fb;

    public MMKV kv = MMKV.mmkvWithID( UserConstants.USER_DAO,MMKV.MULTI_PROCESS_MODE);

    public CommMoneyC2CAdapter(ArrayList<CtcaccountList> list) {
        super(R.layout.item_c2cmoney_frament, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CtcaccountList item) {
        helper.addOnClickListener(R.id.linear_ll);

        if (item.getLockCoin() != null && !item.getLockCoin().equals("")) {
            helper.getView(R.id.list_thaw).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.list_thaw).setVisibility(View.GONE);
        }

        helper.setText(R.id.list_name2, "(" + item.getCoinCode() + ")");


        fb = FinalBitmap.create(mContext);
        fb.configLoadingImage(R.drawable.iconjiazaishibai);
        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
        ImageView imageView = helper.getView(R.id.img_tilte);
        fb.display(imageView, WPConfig.PicBaseUrl + item.getImgUrl() + "");


        if (item.getUsableMoney().setScale(8, BigDecimal.ROUND_DOWN).compareTo(new BigDecimal(0)) == 0) {
            helper.setText(R.id.list_Nowmoney, "0");
        } else {
            helper.setText(R.id.list_Nowmoney, item.getUsableMoney().add(item.getFrozenMoney()).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
        }
        helper.setText(R.id.list_name, item.getCoinCode());


        helper.getView(R.id.linear_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                if (CommonUtil.isFastClick()) {
                    Intent intent = new Intent();
                    intent.putExtra("coin", item.getCoinCode());
                    intent.putExtra("imgurl", item.getImgUrl());
                    intent.putExtra("totalAssets", item.getTotalAssets().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("usableMoney", item.getUsableMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.putExtra("frozenMoney", item.getFrozenMoney().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString());
                    intent.setClass(mContext, C2CDetailsActivity.class);
                    mContext.startActivity(intent);


                }
            }
        });


        helper.getView(R.id.list_thaw).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                // 两次点击按钮之间的点击间隔不能少于2000毫秒 防止用户多次点击
                if (CommonUtil.isFastClick()) {

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("coinCode",item.getCoinCode());
                    ApiFactory.getInstance()
                            .unfreezeCoinMoney(kv.decodeString("tokenId"),hashMap)
                            .compose(RxSchedulers.io_main())
                            .subscribe(data -> {
                                if (data.getCode()==200){
                                    Toast.makeText(mContext, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
                                }else {
                                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);

                                    normalDialog.setTitle("温馨提示：");
                                    normalDialog.setMessage("尊敬的用户你好！\n 注册赠送的"+item.getCoinCode()+"币资产，需要EBX锁仓"+item.getCoinCode()+"枚，才可以解冻自由交易、买卖、提现。注册日XX天内未解冻赠送资产，赠送资产官方将自动收回。\n  请前往     USDT/EBX交易      EBX存币锁仓");

                                    // 显示
                                    normalDialog.show();

                                }

                            },throwable -> {

                            });


                }
            }
        });

    }
}

