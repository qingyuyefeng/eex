package com.eex.home.activity.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.base.Data;

import com.eex.common.view.ComTitleBar;
import com.eex.home.adapter.RQCodeAdapter;
import com.eex.home.bean.OdrderId;
import com.eex.home.bean.payListVO;
import com.eex.home.weight.MyDialog;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


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
 * @Package: com.overthrow.home.activity
 * @ClassName: QRCodeActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/31 13:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/31 13:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 查看卖家收款码
 */
public class QRCodeActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.recy)
    RecyclerView recyclerView;


    private String OrderId;
    private RQCodeAdapter adapter;
    private FinalBitmap fb;
    private MyDialog mMyDialog;


    private View view1;
    private ImageView imgview;

    private ArrayList<payListVO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("查看收款码");
        if (getIntent().getStringExtra("OrderId") != null) {
            OrderId = getIntent().getStringExtra("OrderId");
        }
        //获取订单详情
        getChatLsit();

    }

    @SuppressLint("CheckResult")
    private void getChatLsit() {
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", OrderId);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        ApiFactory.getInstance()
                .getOrderDetailById(kv.decodeString("tokenId"), requestParam)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Data<OdrderId>>() {
                    @Override
                    public void accept(Data<OdrderId> data) throws Exception {
                        dialog.dismiss();
                        Log.e("照片", "accept: " + data);
                        if (data.getCode() == 200) {
                            list.clear();
                            list.addAll(data.getData().getPayList());
                            adapter.notifyDataSetChanged();


                        } else {

                        }

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dialog.dismiss();
                    }
                });


    }


    @Override
    protected void initUiAndListener() {
        adapter = new RQCodeAdapter(R.layout.item_rqcode, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.LLBank:
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(list.get(position).getAccountNo());
                        Toast.makeText(getActivity(), "银行卡号复制成功", Toast.LENGTH_SHORT).show();

                        break;


                    case R.id.LLwx:

                        view1 = getLayoutInflater().inflate(R.layout.imgview_dialog, null);
                        imgview = (ImageView) view1.findViewById(R.id.imgview);
                        fb = FinalBitmap.create(QRCodeActivity.this);
                        fb.configLoadingImage(R.drawable.iconjiazaishibai);
                        fb.configLoadfailImage(R.drawable.iconjiazaishibai);
                        fb.display(imgview, WPConfig.PicBaseUrl + list.get(position).getImageUrl());
                        mMyDialog = new MyDialog(QRCodeActivity.this, 0, 0, view1, R.style.DialogTheme);
                        mMyDialog.setCancelable(true);
                        mMyDialog.show();
                        imgview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMyDialog.dismiss();
                            }
                        });


                        break;


                    case R.id.LLzhibao:

                        view1 = getLayoutInflater().inflate(R.layout.imgview_dialog, null);
                        imgview = (ImageView) view1.findViewById(R.id.imgview);
                        fb = FinalBitmap.create(QRCodeActivity.this);
                        fb.configLoadingImage(R.drawable.iconjiazaishibai);
                        fb.configLoadfailImage(R.drawable.iconjiazaishibai);

                        fb.display(imgview, WPConfig.PicBaseUrl + list.get(position).getImageUrl());
                        mMyDialog = new MyDialog(QRCodeActivity.this, 0, 0, view1, R.style.DialogTheme);
                        mMyDialog.setCancelable(true);
                        mMyDialog.show();
                        imgview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMyDialog.dismiss();
                            }
                        });


                        break;


                    default:


                        break;
                }
            }
        });

    }


    @OnClick(R.id.comtitlebar)
    public void onViewClicked() {
        finish();
    }
}
