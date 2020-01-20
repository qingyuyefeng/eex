package com.eex.mvp.assrtsjava.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.assets.weight.ZXingUtils;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.NewComTitleBar;
import com.eex.extensions.RxExtensionKt;

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
 * @ProjectName: overthrow
 * @Package: com.overthrow.mvp.assrtsjava.activity
 * @ClassName: CurrencyRechargeActivity
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2019/12/24 21:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 21:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CurrencyRechargeActivity extends BaseActivity {

    @BindView(R.id.comtitlebar)
    NewComTitleBar comtitlebar;
    @BindView(R.id.chargemone_name)
    TextView chargemoneName;
    @BindView(R.id.chargemone_line)
    LinearLayout chargemoneLine;
    @BindView(R.id.recharge_erimage)
    ImageView rechargeErimage;
    @BindView(R.id.Currency_iphone)
    TextView CurrencyIphone;
    @BindView(R.id.recharge_addres)
    TextView rechargeAddres;
    @BindView(R.id.Currency_copy)
    TextView CurrencyCopy;
 @BindView(R.id.Currency_two)
    TextView CurrencyTwo;


    private Bitmap bitmap;

    private ClipboardManager cm1;
    private Drawable drawable;
    private BitmapDrawable bitmapDrawable;

    private String coinCode;

    @Override
    protected int getLayoutId() {
        return R.layout.re_activity_currency_recharge;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.Currency_recharge));
        if (getIntent().getStringExtra("coinCode") != null) {
            coinCode = getIntent().getStringExtra("coinCode");

            chargemoneName.setText(coinCode);
        } else {
            chargemoneName.setText("");
        }

        CurrencyTwo.setText(getActivity().getResources().getString(R.string.Currency_two)+coinCode +getActivity().getResources().getString(R.string.Currency_two1)+coinCode +getActivity().getResources().getString(R.string.Currency_two2));

        net();
    }

    @SuppressLint("CheckResult")
    private void net() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coinCode", coinCode);
        RxExtensionKt.filterResult(ApiFactory.getInstance()
                .getaccountinfo(kv.decodeString("tokenId"), hashMap))
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
//                        if (namel.equals("XLM") || namel.equals("XRP")) {
//                            LLRemark.setVisibility(View.VISIBLE);
//                            Bitmap imgbitmap = ZXingUtils.createQRImage(data.getData().getRemark(), imgType.getWidth(), imgType.getHeight());
//                            imgRemark.setImageBitmap(imgbitmap);
//                            TxRemark.setText(data.getData().getRemark());
//                        } else {
//                            LLRemark.setVisibility(View.GONE);
//                        }

                        rechargeAddres.setText(data.getData().getWalletAddress() + "");
                        Bitmap imgbitmap = ZXingUtils.createQRImage(data.getData().getWalletAddress(), rechargeErimage.getWidth(), rechargeErimage.getHeight());
                        rechargeErimage.setImageBitmap(imgbitmap);
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

    @OnClick({R.id.comtitlebar, R.id.chargemone_name, R.id.chargemone_line, R.id.recharge_erimage, R.id.Currency_iphone, R.id.recharge_addres, R.id.Currency_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                break;
            case R.id.chargemone_name:
                break;
            case R.id.chargemone_line:

                intent = new Intent(getActivity(), CurrencyChoiceActivity.class);
                intent.putExtra("CurrencyChoice", "1");
                startActivity(intent);

                break;
            case R.id.recharge_erimage:
                break;
            case R.id.Currency_iphone:
                drawable = rechargeErimage.getDrawable();
                bitmapDrawable = (BitmapDrawable) drawable;
                bitmap = bitmapDrawable.getBitmap();
                saveImageToGallery(getActivity(), bitmap);

                break;
            case R.id.recharge_addres:
                break;
            case R.id.Currency_copy:

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(rechargeAddres.getText().toString().trim());
                Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();
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
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
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
        Toast.makeText(getActivity(), "保留图片成功", Toast.LENGTH_SHORT).show();
    }
}
