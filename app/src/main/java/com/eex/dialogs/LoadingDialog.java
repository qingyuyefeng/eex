package com.eex.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import com.eex.R;

public class LoadingDialog extends ProgressDialog {
  public LoadingDialog(Context context) {
    this(context, R.style.CustomDialog);
  }

  public LoadingDialog(Context context, int theme) {
    super(context, theme);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    init(getContext());
  }

  private void init(Context context) {
    //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
    setCancelable(true);
    setCanceledOnTouchOutside(true);

    setContentView(R.layout.load_dialog);
    WindowManager.LayoutParams params = getWindow().getAttributes();
    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    getWindow().setAttributes(params);
  }

  @Override
  public void show() {
    super.show();
  }
}
