package com.eex.compat

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker

object PermissionsCheckerHelper {

  fun startAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:" + context.getPackageName())
    context.startActivity(intent)
  }

  fun permissionGranted(
    context: Context,
    permission: String
  ): Boolean {
    var isGranted = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      isGranted = if (context.applicationInfo.targetSdkVersion >= Build.VERSION_CODES.M) {
        ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
      } else {
        PermissionChecker.checkSelfPermission(
            context, permission
        ) == PermissionChecker.PERMISSION_GRANTED
      }
    }
    return isGranted
  }
}