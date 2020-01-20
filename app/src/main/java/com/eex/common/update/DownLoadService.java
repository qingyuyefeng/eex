package com.eex.common.update;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.eex.EEXApp;
import com.eex.R;
import com.eex.compat.BuildConfig;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * 更新下载服务
 */
public class DownLoadService extends Service {
    private String download_url = "";
    private String versionName = "";
    private String savePath = Environment.getExternalStorageDirectory() + "/Download/";
    private int requestCode = (int) SystemClock.uptimeMillis();
    private NotifyUtil currentNotify;
    File mFile;
    private boolean isUpdate = false;//是否强制更新
    private Context context = EEXApp.Companion.getContext();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            download_url = intent.getStringExtra("download_url");
            versionName = intent.getStringExtra("versionName");
            isUpdate = intent.getBooleanExtra("isUpdate", false);
            savePath = savePath + "manjiwang" + ".apk";
            mFile = new File(savePath);
            Log.e("TEST", "savePath  = " + savePath);
            Log.e("TEST", "执行onStartCommand");
            //设置想要展示的数据内容
            Intent intent_noti = new Intent();
            intent_noti.setAction(Intent.ACTION_VIEW);

            //点击事件，需要传个uri，但是只能用上个页面的针对7.0的uri
            PendingIntent rightPendIntent = PendingIntent.getActivity(this, requestCode, intent_noti, PendingIntent.FLAG_UPDATE_CURRENT);
            int smallIcon = R.drawable.cq_logo;
            String ticker = "正在更新eex";
            //实例化工具类，并且调用接口
            NotifyUtil notify7 = new NotifyUtil(this, 7);
            notify7.notify_progress(rightPendIntent, smallIcon, ticker, "eex升级程序", "正在下载中...",
                    false, false, false, download_url, savePath, new NotifyUtil.DownLoadListener() {
                        @Override
                        public void OnSuccess(File file) {
                            //在此更新已经知道是不强制了才能放到后台，所以可以在下载完成直接安装APK，所以可以不执行isUpdate判断
                            currentNotify.clear();
                            installApk();
                            DownLoadService.this.stopSelf();
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {

                        }
                    });
            currentNotify = notify7;

        } catch (Exception e) {
            e.printStackTrace();
            DownLoadService.this.stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);

    }


    /**
     * 安装APK文件
     */
    private void installApk() {
//        SpUtils.putString(context, "indexdata", "");
//        SpUtils.putString(context, "classdata", "");
//        SpUtils.putString(context, "cacheClass", "");
//        SpUtils.putString(context, "nearlyClassData", "");
//        SpUtils.putString(context, "nearbydata", "");
        File apkfile = new File(savePath);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.AUTHORITY, apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        getApplicationContext().startActivity(intent);
    }


}

