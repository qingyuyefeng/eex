package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;
import com.eex.mine.activity.SearchTheCoinActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

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
 * @ClassName: CurrencyAddressMoneyActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 12:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 12:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CurrencyAddressMoneyActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.tx_BiName)
    TextView txBiName;
    /**
     *
     */
    @BindView(R.id.BiList_LL)
    LinearLayout BiListLL;
    /**
     *
     */
    @BindView(R.id.ed_moneyAddress)
    EditText edMoneyAddress;
    /**
     *
     */
    @BindView(R.id.ed_Remarks)
    EditText edRemarks;
    /**
     *
     */
    @BindView(R.id.btn_AddAdress)
    Button btnAddAdress;
    /**
     *
     */
    @BindView(R.id.Mention_money_scanning)
    ImageView MentionMoneyScanning;
    /**
     *
     */

    private String currencyType;


    private int REQUEST_CODE_SCAN = 111;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_currency_address_money;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.addsddads));

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


    @OnClick({R.id.comtitlebar, R.id.tx_BiName, R.id.BiList_LL, R.id.ed_moneyAddress, R.id.ed_Remarks, R.id.btn_AddAdress, R.id.Mention_money_scanning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.tx_BiName:
                break;
            case R.id.BiList_LL:
                Intent intent = new Intent();
                intent.setClass(CurrencyAddressMoneyActivity.this, SearchTheCoinActivity.class);
                intent.putExtra("Type", "CurrencyAddress");
                startActivityForResult(intent, 5000);
                break;
            case R.id.ed_moneyAddress:
                break;
            case R.id.ed_Remarks:
                break;
            case R.id.Mention_money_scanning:
                intent = new Intent(getActivity(), CaptureActivity.class);
                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                 * 也可以不传这个参数
                 * 不传的话  默认都为默认不震动  其他都为true
                 * */

                /*ZxingConfig是配置类
                 *可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */

                ZxingConfig config = new ZxingConfig();
                //底部布局（包括闪光灯和相册）
                config.setShowbottomLayout(true);
                //是否播放提示音
                config.setPlayBeep(true);
                //是否震动
                config.setShake(true);
                //是否显示相册
                config.setShowAlbum(true);
                //是否显示闪光灯
                config.setShowFlashLight(true);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.btn_AddAdress:
                getData();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 5000) {
                currencyType = data.getStringExtra("currencyType");
                txBiName.setText(currencyType);
            }
        }


        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                edMoneyAddress.setText(content);
            }
        }
    }

    @SuppressLint("CheckResult")
    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        if (txBiName.getText().toString().trim().equals(R.string.xuanzbi)) {
            Toast.makeText(CurrencyAddressMoneyActivity.this, R.string.xuanbiss, Toast.LENGTH_SHORT).show();
            return;
        }
        if (edMoneyAddress.getText().toString().toString().equals("")) {
            Toast.makeText(CurrencyAddressMoneyActivity.this, R.string.qianbbb, Toast.LENGTH_SHORT).show();
            return;
        }


        String BiAddress = edMoneyAddress.getText().toString().toString();
        hashMap.put("coinCode", currencyType);
        hashMap.put("walletAddress", BiAddress);

        ApiFactory.getInstance()
                .addcoinaddress(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.getCode() == 10002 || data.getCode() == 10001) {

                        intent.putExtra("flage", "2");
                        intent.setClass(CurrencyAddressMoneyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(CurrencyAddressMoneyActivity.this, R.string.loginno, Toast.LENGTH_SHORT).show();
                    }

                    if (data.isStauts() == true) {
                        finish();
                        Toast.makeText(CurrencyAddressMoneyActivity.this, R.string.addmonet, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CurrencyAddressMoneyActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {

                });

    }


}
