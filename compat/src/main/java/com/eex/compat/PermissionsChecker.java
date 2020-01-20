package com.eex.compat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionsChecker {
  // 所需的全部权限
  static final String[] PERMISSIONS = new String[] {
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_PHONE_STATE,
      Manifest.permission.CALL_PHONE
  };
  private static final int PERMISSION_REQUEST_CODE = 39;

  private final Context mContext;

  private final ICallbk callbk;

  private AlertDialog dialog;

  public PermissionsChecker(Context context, ICallbk callbk) {
    if (!(context instanceof Activity)) {
      throw new IllegalArgumentException("context need be extend activity");
    }
    this.callbk = callbk;
    mContext = context;
  }

  // 判断权限集合
  public boolean lacksPermissions() {
    for (String permission : PERMISSIONS) {
      if (lacksPermission(permission)) {
        return true;
      }
    }
    return false;
  }

  // 判断是否缺少权限
  private boolean lacksPermission(String permission) {
    return ContextCompat.checkSelfPermission(mContext, permission) ==
        PackageManager.PERMISSION_DENIED;
  }

  public void requestPermissions() {
    ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, PERMISSION_REQUEST_CODE);
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[]
          grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
      callbk.permissionSuccess();
    } else {
      showMissingPermissionDialog();
    }
  }

  public boolean isDialogOpening() {
    return dialog != null && dialog.isShowing();
  }

  // 显示缺失权限提示
  private void showMissingPermissionDialog() {
    if (dialog != null && dialog.isShowing()) {
      return;
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    builder.setTitle(R.string.help);
    builder.setMessage(R.string.string_help_text);

    // 拒绝, 退出应用
    builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        callbk.permissionFail();
      }
    });

    builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        PermissionsCheckerHelper.INSTANCE.startAppSettings(mContext);
      }
    });
    builder.setCancelable(false);
    dialog = builder.show();
  }

  // 含有全部的权限
  private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
    for (int grantResult : grantResults) {
      if (grantResult == PackageManager.PERMISSION_DENIED) {
        return false;
      }
    }
    return true;
  }

  public interface ICallbk {
    void permissionSuccess();

    void permissionFail();
  }
}
