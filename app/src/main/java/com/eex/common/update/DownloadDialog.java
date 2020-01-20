package com.eex.common.update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.eex.ActivityTaskManager;
import com.eex.R;
import com.eex.compat.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * 更新下载对话框
 */
public class DownloadDialog extends AlertDialog {
    private Context context;
    private TextView txtInfo;
    private boolean isUpdate = false;//是否强制更新
    /* 更新进度条 */
    private ProgressBar mProgress;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载保存路径 */
    private String mSavePath;
    /* 更新url */
    private String upadateUrl = "";
    /* 版本名称 */
    private String versionName = "";
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    public DownloadDialog(Context context) {
        this(context, R.style.CustomDialog);
        this.context = context;
    }

    public DownloadDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.download_dialog_layout, null);
        mProgress = (ProgressBar) view.findViewById(R.id.update_progress);
        txtInfo = (TextView) view.findViewById(R.id.textview);
/*        if (!isUpdate) {
            //非强制更新
            go.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置取消状态
                cancelUpdate = true;

                if (isUpdate) {
                    dismiss();
                    ActivityTaskManager.INSTANCE.closeAllActivity();
                } else {
                    dismiss();
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置取消状态
                cancelUpdate = true;
                Log.e("TEST", "upadateUrl = " + upadateUrl);
                dismiss();
                Intent intent = new Intent(context, DownLoadService.class);
                File apkfile = new File(mSavePath, "manjiwang" + ".apk");
                Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", apkfile);
                intent.putExtra("download_url", upadateUrl);
                intent.putExtra("versionName", versionName);
                intent.putExtra("isUpdate", isUpdate);
                intent.putExtra("contentUri", contentUri);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    context.startForegroundService(intent);
//                } else {
                    context.startService(intent);
               // }
            }
        });*/
        setContentView(view);
        // 下载文件
        downloadApk();
    }

    /**
     * 设置文字
     */
    public void setText(String text) {
        if (text == null)
            return;
        txtInfo.setText(text);
    }


    /**
     * 设置是否强制更新
     */
    public void setIsUpdate(boolean state) {
        isUpdate = state;
        if (state) {
            //强制更新
            setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            setCancelable(false);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    try {
                        // 设置进度条位置
                        mProgress.setProgress(progress);
                        setText(progress + "%");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    if (isUpdate) {
                        //强制更新
                        ActivityTaskManager.INSTANCE.closeAllActivity();
                    } else {
                        //非强制更新
                    }
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "Download";
                    Log.e("TEST", "savePath  = " + mSavePath);
                    URL url = new URL(upadateUrl);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath + "eex" + ".apk");
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "eex" + ".apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            dismiss();
        }
    }


    /**
     * 安装APK文件
     */
    private void installApk() {
        try {
            File apkfile = new File(mSavePath, "eex" + ".apk");
	        if (!apkfile.exists()) {
                return;
            }
            // 通过Intent安装APK文件
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.AUTHORITY, apkfile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置更新信息
     */
    public void setInfo(String upadateUrl, String versionName) {
        this.upadateUrl = upadateUrl;
        this.versionName = versionName;
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isUpdate) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
//                    ToastUtil.INSTANCE.toast("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    out();
                }
            } else {
                // 设置取消状态
                cancelUpdate = true;
                dismiss();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void out() {
        // 设置取消状态
        cancelUpdate = true;
        if (isUpdate) {
            dismiss();
            ActivityTaskManager.INSTANCE.closeAllActivity();
        } else {
            dismiss();
        }
    }
}