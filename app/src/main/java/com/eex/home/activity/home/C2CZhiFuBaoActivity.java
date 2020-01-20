package com.eex.home.activity.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;
import com.eex.WPConfig;
import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;
import com.eex.common.util.TimeCount;

import com.eex.common.view.ComTitleBar;
import com.eex.compat.BuildConfig;
import com.eex.home.weight.ActionSheetDialog;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

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
 * @Package: com.overthrow.home.activity.home
 * @ClassName: C2CZhiFuBaoActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 17:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 17:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 支付宝收款
 */


public class C2CZhiFuBaoActivity extends BaseActivity {


    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.edtName)
    EditText edtName;
    /**
     *
     */
    @BindView(R.id.edtZhiFuBao)
    EditText edtZhiFuBao;
    /**
     *
     */
    @BindView(R.id.codeType)
    TextView codeType;
    /**
     *
     */
    @BindView(R.id.edtCode)
    EditText edtCode;
    /**
     *
     */
    @BindView(R.id.btnCode)
    Button btnCode;
    /**
     *
     */
    @BindView(R.id.imageView)
    ImageView imageView;
    /**
     *
     */
    @BindView(R.id.btnPut)
    Button btnPut;


    /**
     *
     */
    private String userName;
    private String accountNo;
    private String childBankName;
    private String bankAddress;
    private String bankName;
    private String id = "";
    private String imageUrl;


    private FinalBitmap fb;

    /**
     * 验证码时间
     */
    private TimeCount time;

    /**
     * yes代表已经图片上传成功NO不成功
     */
    private String ImageViwData = "NO";

    private String imgurl = "";

    /**
     * 调用照相机返回图片文件
     */
    private File tempFile;

    private Uri uritempFile;
    private String path;


    /**
     * 相册请求码
     */
    private static final int ALBUM_REQUEST_CODE = 1;
    /**
     * 相机请求码
     */
    private static final int CAMERA_REQUEST_CODE = 2;
    /**
     * 剪裁请求码
     */
    private static final int CROP_REQUEST_CODE = 3;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2_czhi_fu_bao;
    }


    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle("支付宝收款");

        if (!getIntent().getStringExtra("imageUrl").equals("no") && getIntent().getStringExtra("imageUrl") != null) {
            imageUrl = getIntent().getStringExtra("imageUrl");
            userName = getIntent().getStringExtra("userName");
            accountNo = getIntent().getStringExtra("accountNo");
            edtName.setText(userName);
            edtZhiFuBao.setText(accountNo);
            id = getIntent().getStringExtra("id");
            Log.e("TAH", "refreshData: " + imageUrl +
                    userName +
                    accountNo + id);

            fb = FinalBitmap.create(C2CZhiFuBaoActivity.this);
            fb.configLoadingImage(R.drawable.iconjiazaishibai);
            fb.configLoadfailImage(R.drawable.iconjiazaishibai);
            fb.display(imageView, WPConfig.PicBaseUrl + imageUrl + "");
        }

        if (kv.decodeString("phone") == null || kv.decodeString("phone").equals("")) {
            codeType.setText("邮箱验证码:");
        } else {
            codeType.setText("短信验证码:");
        }
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

    @OnClick({R.id.comtitlebar, R.id.edtName, R.id.edtZhiFuBao, R.id.btnCode, R.id.imageView, R.id.btnPut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:

                finish();


                break;
            case R.id.edtName:
                break;
            case R.id.edtZhiFuBao:
                break;
            //验证码
            case R.id.btnCode:

                if (kv.decodeString("tokenId") != null) {
                    if (kv.decodeString("phone") != null && !kv.decodeString("phone").equals("")) {
                        //短信验证码
                        GetCode();
                    } else if (kv.decodeString("email") != null && !kv.decodeString("email").equals("")) {
                        //邮箱发送登录验证码
                        getEmailCode();
                    }
                } else {
                    Toast.makeText(C2CZhiFuBaoActivity.this, "请登录后操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageView:
                //打开相机或者相册
                GetPhoto();
                break;
            case R.id.btnPut:

                if (edtName.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CZhiFuBaoActivity.this, "请填写真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtZhiFuBao.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CZhiFuBaoActivity.this, "请填写支付宝账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtCode.getText().toString().trim().equals("")) {
                    Toast.makeText(C2CZhiFuBaoActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ImageViwData.equals("NO")) {
                    Toast.makeText(C2CZhiFuBaoActivity.this, "请填成功上传收款码后操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交
                net();
                break;
            default:
                break;
        }
    }

    /**
     * 打开相机和相册
     */
    private void GetPhoto() {

        new ActionSheetDialog(C2CZhiFuBaoActivity.this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(getResources().getString(R.string.xc),
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                //相册
                                getPicFromAlbm();

                            }


                        })
                .addSheetItem(getResources().getString(R.string.xj),
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {

                            @Override
                            public void onClick(int which) {
                                //权限检测
                                checkPermission();
                            }


                        }).show();
    }


    /**
     * 相册
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    /**
     * 权限检测
     */
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(C2CZhiFuBaoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(C2CZhiFuBaoActivity.this, Manifest.permission.CAMERA)) {
                // 提示已经禁止
                Toast.makeText(C2CZhiFuBaoActivity.this, getResources().getString(R.string.quanxian), Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(C2CZhiFuBaoActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
            }
        } else {
            //打开相机
            getPicFromCamera();
        }
    }

    /**
     * 打开相机
     */
    private void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //判断版本
        //如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(C2CZhiFuBaoActivity.this, BuildConfig.AUTHORITY, tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 照片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CROP_REQUEST_CODE", resultCode + "--" + requestCode);
        switch (requestCode) {
            //调用相机后返回
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(C2CZhiFuBaoActivity.this, BuildConfig.AUTHORITY, tempFile);
                        //裁剪图片
                        cropPhoto(contentUri);
                    } else {
                        //裁剪图片
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            //调用相册后返回
            case ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.e("CODE", data.getData().toString());
                    //裁剪图片
                    cropPhoto(uri);
                }
                break;
            //调用剪裁后返回
            case CROP_REQUEST_CODE:

                //调用剪裁后返回
                try {
                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    path = saveImage("weixin", image);
                    upload(path, image);
                    Log.e("path", path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;

        }
    }

    /**
     * 图片上传
     *
     * @param path
     * @param image
     */
    @SuppressLint("CheckResult")
    private void upload(String path, Bitmap image) {

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ApiFactory.getInstance()
                .upload(kv.decodeString("tokenId"), filePart)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {
                        imgurl = data.getData().toString();
                        imageView.setImageBitmap(image);
                        t(data.getMsg());
                        ImageViwData = "YES";

                    } else {
                        t(data.getMsg());
                    }


                }, throwable -> {

                });

    }


    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropPhoto(Uri uri) {

        verifyStoragePermissions(C2CZhiFuBaoActivity.this);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        //intent.putExtra("return-data", true);

        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    /**
     * 动态获取内存存储权限
     *
     * @param activity
     */
    private void verifyStoragePermissions(C2CZhiFuBaoActivity activity) {

        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }


    /**
     * 图片地址
     *
     * @param name
     * @param bmp
     * @return
     */
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 短信验证码
     */
    @SuppressLint("CheckResult")
    private void GetCode() {
        if (kv.decodeString("phone") == null && kv.decodeString("phone") == "") {
            Toast.makeText(C2CZhiFuBaoActivity.this, "请绑定手机号后操作", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sendType", "sms_take_money");
        hashMap.put("phone", kv.decodeString("phone"));
        ApiFactory.getInstance()
                .send(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(C2CZhiFuBaoActivity.this, btnCode, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    /**
     * 邮箱发送登录验证码
     */
    @SuppressLint("CheckResult")
    private void getEmailCode() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sendType", "3");
        hashMap.put("email", kv.decodeString("email"));
        hashMap.put("userName", kv.decodeString("username"));
        ApiFactory.getInstance()
                .email(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        //构造CountDownTimer对象
                        time = new TimeCount(C2CZhiFuBaoActivity.this, btnCode, getActivity().getResources().getString(R.string.regain_code), 60000, 1000);
                        time.start();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });

    }

    /**
     * 提交
     */
    @SuppressLint("CheckResult")
    private void net() {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accountType", "2");
        hashMap.put("imageUrl", imgurl);

        if (id != null) {
            hashMap.put("id", id);
        }

        hashMap.put("userName", edtName.getText().toString().trim());
        hashMap.put("code", edtCode.getText().toString().trim());
        hashMap.put("accountNo", edtZhiFuBao.getText().toString().trim());


        ApiFactory.getInstance()
                .saveorupdate(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        t(data.getMsg());
                        finish();
                    } else {
                        t(data.getMsg());
                    }
                }, throwable -> {

                });
    }
}
