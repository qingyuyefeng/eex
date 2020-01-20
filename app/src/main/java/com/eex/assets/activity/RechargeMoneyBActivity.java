package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eex.R;
import com.eex.assets.weight.ZXingUtils;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
 * @Package: com.overthrow.assets.activity
 * @ClassName: RechargeMoneyBActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/2 17:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/2 17:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 充币
 */
public class RechargeMoneyBActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.textname1)
    TextView textname1;
    /**
     *
     */
    @BindView(R.id.RcMoney_ll)
    LinearLayout RcMoneyLl;
    /**
     *
     */
    @BindView(R.id.textname)
    TextView textname;
    /**
     *
     */
    @BindView(R.id.btn_newAddress)
    Button btnNewAddress;
    /**
     *
     */
    @BindView(R.id.tx_data)
    TextView txData;
    /**
     *
     */
    @BindView(R.id.imgbtn)
    ImageButton imgbtn;
    /**
     *
     */
    @BindView(R.id.Address_LL)
    LinearLayout AddressLL;
    /**
     *
     */
    @BindView(R.id.imgType)
    ImageView imgType;
    /**
     *
     */
    @BindView(R.id.textremk)
    TextView textremk;
    /**
     *
     */
    @BindView(R.id.TxRemark)
    TextView TxRemark;
    /**
     *
     */
    @BindView(R.id.ImgButton)
    ImageButton ImgButton;
    /**
     *
     */
    @BindView(R.id.imgRemark)
    ImageView imgRemark;
    /**
     *
     */
    @BindView(R.id.LLRemark)
    LinearLayout LLRemark;
    /**
     *
     */
    @BindView(R.id.txname)
    TextView txname;

    private String namel;
    private String id;
    private Bitmap bitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_money_b;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.congb));
        comtitlebar.setImageView(R.drawable.cq_recharge_zd);

        if (getIntent().getStringExtra("name") != null) {
            namel = getIntent().getStringExtra("name");
            id = getIntent().getStringExtra("id");
            textname1.setText(namel);
            textname.setText(namel);
            txname.setText("充值后需要1次网络确认后才能到账,任何非" + namel + "资产充值到" + namel + "地址后不可找回");
        } else {
            textname1.setText(R.string.xuanbi);
        }

        //获取钱包地址
        net();
        comtitlebar.setImageViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("coin", namel);
                intent.setClass(RechargeMoneyBActivity.this, RechargeMoneyListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", namel);

        ApiFactory.getInstance()
                .getaccountinfo(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(RechargeMoneyBActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RechargeMoneyBActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }
                    if (data.getData() != null) {
                        if (namel.equals("XLM") || namel.equals("XRP")) {
                            LLRemark.setVisibility(View.VISIBLE);
                            Bitmap imgbitmap = ZXingUtils.createQRImage(data.getData().getRemark(), imgType.getWidth(), imgType.getHeight());
                            imgRemark.setImageBitmap(imgbitmap);
                            TxRemark.setText(data.getData().getRemark());
                        } else {
                            LLRemark.setVisibility(View.GONE);
                        }

                        txData.setText(data.getData().getWalletAddress() + "");
                        Bitmap imgbitmap = ZXingUtils.createQRImage(data.getData().getWalletAddress(), imgType.getWidth(), imgType.getHeight());
                        imgType.setImageBitmap(imgbitmap);
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
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

    @OnClick({R.id.comtitlebar, R.id.textname1, R.id.RcMoney_ll, R.id.textname, R.id.btn_newAddress, R.id.tx_data, R.id.imgbtn, R.id.Address_LL, R.id.imgType, R.id.textremk, R.id.TxRemark, R.id.ImgButton, R.id.imgRemark, R.id.LLRemark, R.id.txname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.textname1:
                break;
            case R.id.RcMoney_ll:
                break;
            case R.id.textname:
                break;
            case R.id.btn_newAddress:
                break;
            case R.id.tx_data:
                break;
            case R.id.imgbtn:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(txData.getText().toString().trim());
                Toast.makeText(this, R.string.fuz, Toast.LENGTH_LONG).show();
                break;
            case R.id.Address_LL:
                break;
            case R.id.imgType:
                Drawable drawable = imgType.getDrawable();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                bitmap = bitmapDrawable.getBitmap();
                saveImageToGallery(getActivity(), bitmap);
                break;
            case R.id.textremk:
                break;
            case R.id.TxRemark:
                break;
            case R.id.ImgButton:
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(TxRemark.getText().toString().trim());
                Toast.makeText(this, R.string.fuz, Toast.LENGTH_LONG).show();
                break;
            case R.id.imgRemark:
                break;
            case R.id.LLRemark:
                break;
            case R.id.txname:
                break;
            default:
                break;
        }
    }

    /**
     * 保存图片
     *
     * @param activity
     * @param bitmap
     */
    private void saveImageToGallery(AppCompatActivity activity, Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "test");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(RechargeMoneyBActivity.this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //最后通知图库更新
        Intent intent = new Intent();
        //扫描单个文件
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //给图片的绝对路径
        intent.setData(Uri.fromFile(file));
        getActivity().sendBroadcast(intent);
        Toast.makeText(RechargeMoneyBActivity.this, "图片保存成功", Toast.LENGTH_SHORT).show();
    }
}
