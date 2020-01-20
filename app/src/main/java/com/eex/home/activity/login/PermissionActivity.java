package com.eex.home.activity.login;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 空 on 2017/2/16 0016.
 * 测试通过，使用的时候注意要复写的方法，不然会出错
 * <p>
 * 如果一个界面有多个请求，会出问题...
 */

public abstract class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 为子类提供一个权限的检查方法
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 为子类提供一个权限请求方法
     *
     * @param permissions
     */

    public void requestPermission(int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasPermission(permissions)) {
            //有权限
            onPermissionSuccess(permissions, requestCode);
        } else {
            //没有权限
            onPermissionFailed(requestCode);
        }
    }

    //请求失败的回调
    protected void onPermissionFailed(int requestCode) {
    }

    //请求成功的回调
    protected void onPermissionSuccess(String[] permissions, int requestCode) {
    }
}
