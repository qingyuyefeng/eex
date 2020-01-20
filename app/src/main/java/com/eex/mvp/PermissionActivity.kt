package com.eex.mvp

import com.eex.compat.PermissionsChecker

abstract class PermissionActivity<VO, V : BaseView, T : BasePresenterImpl<V, VO>> : MVPBaseActivity<VO, V, T>(),
    PermissionsChecker.ICallbk {

  private var permissionsChecker: PermissionsChecker = PermissionsChecker(this, this)

  override fun onResume() {
    super.onResume()
    if (!permissionsChecker.isDialogOpening && permissionsChecker.lacksPermissions()) {
      permissionsChecker.requestPermissions()
    } else {
      if (!permissionsChecker.lacksPermissions()) {
        permissionSuccess()
      }
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    permissionsChecker.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun permissionSuccess() {

  }

  override fun permissionFail() {

  }
}