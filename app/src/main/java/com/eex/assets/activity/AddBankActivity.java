package com.eex.assets.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import com.eex.R;
import com.eex.assets.bean.JsonBean;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.CommonUtil;
import com.eex.common.view.ComTitleBar;
import com.eex.home.activity.login.LoginActivity;

import org.json.JSONArray;

import java.util.ArrayList;
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
 * @ClassName: AddBankActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/3 10:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/3 10:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 *
 * 添加银行卡
 */
public class AddBankActivity extends BaseActivity {

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.Bank_edt_name)
    EditText BankEdtName;
    /**
     *
     */
    @BindView(R.id.Bank_edt_namefo)
    EditText BankEdtNamefo;
    /**
     *
     */
    @BindView(R.id.textview_bank)
    TextView textviewBank;
    /**
     *
     */
    @BindView(R.id.bank_list_LL)
    LinearLayout bankListLL;
    /**
     *
     */
    @BindView(R.id.textview_sheng)
    TextView textviewSheng;
    /**
     *
     */
    @BindView(R.id.bank_Sheng_list_LL)
    LinearLayout bankShengListLL;
    /**
     *
     */
    @BindView(R.id.textView_shi)
    TextView textViewShi;
    /**
     *
     */
    @BindView(R.id.Bank_shi_LL)
    LinearLayout BankShiLL;
    /**
     *
     */
    @BindView(R.id.Bnak_edt_bankName)
    EditText BnakEdtBankName;
    /**
     *
     */
    @BindView(R.id.recharge_edt_bankNuber)
    EditText rechargeEdtBankNuber;
    /**
     *
     */
    @BindView(R.id.AddBank_btn)
    Button AddBankBtn;
    /**
     *
     */
    @BindView(R.id.ll)
    LinearLayout ll;


    private String Key = "";
    private String sheng;
    private String shi;
    private String BankName;
    private String dicKey;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.add_bank_card));
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

    @OnClick({R.id.comtitlebar, R.id.Bank_edt_name, R.id.Bank_edt_namefo, R.id.textview_bank, R.id.bank_list_LL, R.id.textview_sheng, R.id.bank_Sheng_list_LL, R.id.textView_shi, R.id.Bank_shi_LL, R.id.Bnak_edt_bankName, R.id.recharge_edt_bankNuber, R.id.AddBank_btn, R.id.ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            case R.id.Bank_edt_name:
                break;
            case R.id.Bank_edt_namefo:
                break;
            case R.id.textview_bank:
                break;
            //选择银行
            case R.id.bank_list_LL:
                intent.setClass(AddBankActivity.this, BankListActivity.class);
                startActivityForResult(intent, 3000);
                break;
            case R.id.textview_sheng:
                break;
            //选择省
            case R.id.bank_Sheng_list_LL:
                initJsonData();
                showPickerView();
                break;
            case R.id.textView_shi:
                break;
            //选择市
            case R.id.Bank_shi_LL:

                if (Key == "") {
                    Toast.makeText(AddBankActivity.this, R.string.sheng, Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(AddBankActivity.this, ShiListActivity.class);
                    intent.putExtra("key", Key);
                    startActivityForResult(intent, 3000);
                }
                break;
            case R.id.Bnak_edt_bankName:
                break;
            case R.id.recharge_edt_bankNuber:
                break;
            //添加银行卡
            case R.id.AddBank_btn:
                AddBank();
                break;
            case R.id.ll:
                break;
            default:
                break;
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                textviewSheng.setText(text);
            }
        }).setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(13)
                .setOutSideCancelable(false)
                .build();
        //三级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    @SuppressLint("CheckResult")
    private void AddBank() {
        if (BankEdtName.getText().toString().trim().equals("")) {
            Toast.makeText(AddBankActivity.this, R.string.lxing, Toast.LENGTH_SHORT).show();
            return;
        }
        if (BankEdtNamefo.getText().toString().trim().equals("")) {
            Toast.makeText(AddBankActivity.this, R.string.lming, Toast.LENGTH_SHORT).show();
            return;
        }
        if (textviewBank.getText().toString().trim().equals(R.string.bankll)) {
            Toast.makeText(AddBankActivity.this, R.string.lbangk, Toast.LENGTH_SHORT).show();
            return;
        }
        if (BnakEdtBankName.getText().toString().trim().equals("")) {
            Toast.makeText(AddBankActivity.this, R.string.zihuang, Toast.LENGTH_SHORT).show();
            return;
        }
        if (rechargeEdtBankNuber.getText().toString().trim().equals("")) {
            Toast.makeText(AddBankActivity.this, R.string.bankname, Toast.LENGTH_SHORT).show();
            return;
        }
        if (textviewSheng.getText().toString().trim().equals(R.string.xuanz)) {
            Toast.makeText(AddBankActivity.this, R.string.kaihuhang, Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> hasMap = new HashMap<>();
        //开户行
        hasMap.put("bankName", textviewBank.getText().toString().trim());
        //开户行所在地
        hasMap.put("bankAddress", textviewSheng.getText().toString().trim());
        //开户支行
        hasMap.put("bankChildName", BnakEdtBankName.getText().toString().trim());
        //mingzi
        hasMap.put("givename", BankEdtNamefo.getText().toString().trim());
        //姓名
        hasMap.put("surname", BankEdtName.getText().toString().trim());
        //账号
        hasMap.put("bankCardNo", rechargeEdtBankNuber.getText().toString().trim());


        ApiFactory.getInstance()
                .addbank(kv.decodeString("tokenId"),hasMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode()==10002||data.getCode()==10001){

                        intent.putExtra("flage","2");
                        intent.setClass(AddBankActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(AddBankActivity.this,R.string.loginno,Toast.LENGTH_SHORT).show();
                    }

                    if (data.getCode()==200){
                        Toast.makeText(AddBankActivity.this,R.string.gobank,Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(AddBankActivity.this,R.string.gobank,Toast.LENGTH_SHORT).show();
                    }
                },throwable -> {

                });

    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = CommonUtil.getJson(this, "province_data.json");
        //用Gson 转成实体
        ArrayList<JsonBean> jsonBean = parseData(JsonData);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            //遍历省份
            ArrayList<String> CityList = new ArrayList<>();
            //该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();
            //该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                //遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                //添加城市
                CityList.add(CityName);

                ArrayList<String> City_AreaList = new ArrayList<>();
                //该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {
                        //该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);
                        //添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);
                //添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    /**
     * Gson 解析
     *
     * @param result
     * @return
     */
    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2000) {
            BankName = data.getStringExtra("textview");
            textviewBank.setText(BankName);
        }
        if (resultCode == 3000) {
            sheng = data.getStringExtra("ShengName");
            textviewSheng.setText(sheng);
            Key = data.getStringExtra("Key");
        }
        if (resultCode == 4000) {
            shi = data.getStringExtra("shiNanme");
            textViewShi.setText(shi);
        }
    }
}
