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
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.eex.R;

import com.eex.common.api.ApiFactory;
import com.eex.common.api.RxSchedulers;
import com.eex.common.base.BaseActivity;

import com.eex.common.view.ComTitleBar;
import com.eex.compat.BuildConfig;
import com.eex.home.activity.login.LoginActivity;
import com.eex.home.weight.ActionSheetDialog;
import com.eex.mine.activity.ReailNameTypeActivity;

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
 * @ClassName: PhotoActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/17 15:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/17 15:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * 实名认证
 */
public class PhotoActivity extends BaseActivity {


    private static final String TAG = "PhotoActivity";

    /**
     *
     */
    @BindView(R.id.comtitlebar)
    ComTitleBar comtitlebar;
    /**
     *
     */
    @BindView(R.id.surname)
    TextView surname;
    /**
     *
     */
    @BindView(R.id.givename)
    TextView givename;
    /**
     *
     */
    @BindView(R.id.tx1)
    TextView tx1;
    /**
     *
     */
    @BindView(R.id.img_one)
    ImageView imgOne;
    /**
     *
     */
    @BindView(R.id.tx2)
    TextView tx2;
    /**
     *
     */
    @BindView(R.id.img_two)
    ImageView imgTwo;
    /**
     *
     */
    @BindView(R.id.tx3)
    TextView tx3;
    /**
     *
     */
    @BindView(R.id.img_Three)
    ImageView imgThree;
    /**
     *
     */
    @BindView(R.id.TXtost)
    TextView TXtost;
    /**
     *
     */
    @BindView(R.id.next_btn)
    Button nextBtn;


    /**
     * 调用照相机返回图片文件
     */
    private File tempFile;

    private Uri uritempFile;
    private String path;

    private String pho1 = "";
    private String pho2 = "";
    private String pho3 = "";
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

    /**
     * type = 0
     * 本人身份证正面照片
     */

    private Integer code1 = 0;
    private Integer code2 = 0;
    private Integer code3 = 0;
    private Integer type = 0;


    @Override
    protected int getLayoutId() {

        return R.layout.activity_photo;
    }

    @Override
    protected void refreshData(Bundle savedInstanceState) {

        comtitlebar.setTitle(getActivity().getResources().getString(R.string.shimingrenz));

        //是否实名
        net();
    }

    /**
     * 是否实名
     */
    @SuppressLint("CheckResult")
    private void net() {

        HashMap<String, String> hashMap = new HashMap<>();
        ApiFactory.getInstance()
                .getauthstauts(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getData() != null) {
                        if (data.getCode() == 10002 || data.getCode() == 10001) {
                            intent = new Intent();
                            intent.putExtra("type", "1");
                            intent.setClass(PhotoActivity.this, LoginActivity.class);
                            startActivity(intent);
                            t(getActivity().getResources().getString(R.string.loginno));
                        }

                        if (data.isStauts() == true) {
                            surname.setText(data.getData().getSurname());
                            givename.setText(data.getData().getGivename());
                            if (data.getData().getCardType() == 0) {
                                tx1.setText("本人身份证正面照片");
                                tx2.setText("本人身份证背面照片");
                                tx3.setText("手持本人身份证照片");
                                TXtost.setText("请确保您使用本人的真实身份进行验证,我们会保护您的个人信息安全.");
                            } else {
                                tx1.setText("本人护照正面照片");
                                tx2.setText("本人护照背面照片");
                                tx3.setText("本人手持护照照片");
                                TXtost.setText("根据国家相关规定,上传的身份信息仅用于平台身份验证,我们承诺严格保护用户隐私");
                            }
                        } else {
                            t(data.getMsg());
                        }


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

    @OnClick({R.id.comtitlebar, R.id.img_one, R.id.img_two, R.id.img_Three, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comtitlebar:
                finish();
                break;
            //本人身份证正面照片
            case R.id.img_one:

                type = 1;
                new ActionSheetDialog(PhotoActivity.this)
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
                                        //检查拍照权限
                                        checkPermission();
                                    }
                                }).show();
                break;
            case R.id.img_two:
                type = 2;
                new ActionSheetDialog(PhotoActivity.this)
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
                                        //检查拍照权限
                                        checkPermission();
                                    }
                                }).show();
                break;
            case R.id.img_Three:
                type = 3;
                new ActionSheetDialog(PhotoActivity.this)
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
                                        //检查拍照权限
                                        checkPermission();
                                    }
                                }).show();
                break;
            case R.id.next_btn:
                setwebdata();

                break;
            default:
                break;
        }
    }

    /**
     * 上传头像
     */
    @SuppressLint("CheckResult")
    private void setwebdata() {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (pho1.equals("")) {
            Toast.makeText(PhotoActivity.this, R.string.zhen, Toast.LENGTH_SHORT).show();
            return;
        }
        if (pho2.equals("")) {
            Toast.makeText(PhotoActivity.this, R.string.bei, Toast.LENGTH_SHORT).show();
            return;
        }
        if (pho3.equals("")) {
            Toast.makeText(PhotoActivity.this, R.string.zb, Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("handIdCardUrl", "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" + pho3);
        hashMap.put("idCardFrontUrl", "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" + pho1);
        hashMap.put("idCardBackUrl", "http://test-7ebit.s3-website-ap-northeast-1.amazonaws.com/" + pho2);

        ApiFactory.getInstance()
                . levelauth(kv.decodeString("tokenId"), hashMap)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {
                    if (data.isStauts() == true) {
                        intent = new Intent();
                        intent.setClass(PhotoActivity.this, ReailNameTypeActivity.class);
                        startActivity(intent);
                        PhotoActivity.this.finish();
                        t(data.getMsg());
                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

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
     * 检查拍照权限
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 进入这儿表示没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoActivity.this, Manifest.permission.CAMERA)) {
                // 提示已经禁止
                Toast.makeText(PhotoActivity.this, getResources().getString(R.string.quanxian), Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(PhotoActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
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
            Uri contentUri = FileProvider.getUriForFile(PhotoActivity.this, BuildConfig.AUTHORITY, tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("dasd", contentUri.toString());
            //否则使用Uri.fromFile(file)方法获取Uri
        } else {
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

        switch (requestCode) {
            //调用相机后返回
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(PhotoActivity.this, BuildConfig.AUTHORITY, tempFile);
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
                try {
                    Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    if (type == 1) {
                        code1 = 1;
                        path = saveImage("crop1", image);
                        //上传图片
                        upload(path, 1, image);

                    } else if (type == 2) {
                        code2 = 2;
                        path = saveImage("crop2", image);
                        //上传图片
                        upload(path, 2, image);
                    } else if (type == 3) {
                        code3 = 3;
                        path = saveImage("crop3", image);
                        //上传图片
                        upload(path, 3, image);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;

        }
    }


    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropPhoto(Uri uri) {

        verifyStoragePermissions(PhotoActivity.this);
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
    private void verifyStoragePermissions(PhotoActivity activity) {

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
     * 上传图片
     *
     * @param path
     * @param i
     * @param bitmap
     */
    @SuppressLint("CheckResult")
    private void upload(String path, int i, Bitmap bitmap) {

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ApiFactory.getInstance()
                .upload(kv.decodeString("tokenId"), filePart)
                .compose(RxSchedulers.io_main())
                .subscribe(data -> {

                    if (data.getCode() == 200) {

                        if (i == 1) {
                            pho1 = data.getData().toString();
                            imgOne.setImageBitmap(bitmap);
                            Log.e(TAG, "upload: " +pho1 );
                        }
                        if (i == 2) {
                            pho2 = data.getData().toString();
                            imgTwo.setImageBitmap(bitmap);
                            Log.e(TAG, "upload: " +pho2 );
                        }
                        if (i == 3) {
                            pho3 = data.getData().toString();
                            imgThree.setImageBitmap(bitmap);
                            Log.e(TAG, "upload: " +pho3 );
                        }

                    } else {
                        t(data.getMsg());
                    }

                }, throwable -> {

                });

    }


}
