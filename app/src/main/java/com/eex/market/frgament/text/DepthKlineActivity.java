package com.eex.market.frgament.text;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.market.bean.Buy;
import com.eex.market.bean.Sell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * @Package: com.overthrow.market.frgament.text
 * @ClassName: DepthKlineActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/26 14:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/26 14:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 画深度图
 */
public class DepthKlineActivity extends BaseActivity {

    @BindView(R.id.depth_view)
    DepthMapView depthView;


    private List<Sell> sellList = new ArrayList<>();
    private List<Buy> buyList = new ArrayList<>();

    private List<Sell> sells = new ArrayList<>();
    private List<Buy> buys = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_depth_kline;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        //
        net();
    }

    /**
     *
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("delkey", "BTC_USDT");
        ApiFactory.getInstance()
                .getTickMaket(hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.isStauts() == true) {

                        if (data.getData().getSell() != null) {
                            sellList.clear();
                            sellList.addAll(data.getData().getSell());
                            if (data.getData().getSell() != null && data.getData().getSell().size() != 0) {
                                sellList.addAll(data.getData().getSell());
                                if (data.getData().getSell().size() > 10) {
                                    sells.addAll(data.getData().getSell().subList(0, 10));
                                } else {
                                    sells.addAll(data.getData().getSell());
                                }

                            }

                        }

                        if (data.getData().getBuy() != null) {
                            buyList.clear();
                            buyList.addAll(data.getData().getBuy());

                            if (data.getData().getBuy() != null && data.getData().getBuy().size() != 0) {
                                if (data.getData().getBuy().size() > 10) {
                                    buyList.addAll(data.getData().getBuy().subList(0, 10));
                                } else {
                                    buyList.addAll(data.getData().getBuy());
                                }
                                buys.addAll(data.getData().getBuy());

                            }


                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Collections.reverse(sellList);
                                //画深度图
                                getDepth();
                            }


                        });




                    } else {
                        t(data.getMsg());
                    }


                }, throwable -> {

                });


    }



    /**
     * 画深度图
     */
    private void getDepth() {

        final List<DepthDataBean> listDepthBuy = new ArrayList<>();
        final List<DepthDataBean> listDepthSell = new ArrayList<>();

        DepthDataBean obj;
        DepthDataBean obj1;
        String price;
        String volume;
        for (int i = 0; i < buys.size(); i++){
            obj = new DepthDataBean();
            obj1 = new DepthDataBean();
            price = buys.get(i).getDelAmount().toString();
            volume = buys.get(i).getResidueNum().toString();
            obj.setVolume(Float.valueOf(volume));
            obj.setPrice(Float.valueOf(price));
            obj1.setVolume(Float.valueOf(volume));
            obj1.setPrice(Float.valueOf(price));
            listDepthBuy.add(obj);
        }
        for (int i = 0; i < sellList.size(); i++){
            obj = new DepthDataBean();
            obj1 = new DepthDataBean();
            price = sellList.get(i).getDelAmount().toString();
            volume = sellList.get(i).getResidueNum().toString();
            obj.setVolume(Float.valueOf(volume));
            obj.setPrice(Float.valueOf(price));
            obj1.setVolume(Float.valueOf(volume));
            obj1.setPrice(Float.valueOf(price));
            listDepthSell.add(obj);

        }
        depthView.setData(listDepthBuy, listDepthSell);

    }




    @Override
    protected void initUiAndListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
