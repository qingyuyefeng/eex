package com.eex.market.frgament.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.view.TextureView;

abstract class BaseTextureView extends TextureView implements TextureView.SurfaceTextureListener {

  private static final int MSG_DRAW = 776;

  protected HandlerThread drawThread;
  protected Handler handler;

  public BaseTextureView(Context context) {
    super(context);
    setSurfaceTextureListener(this);
  }

  public BaseTextureView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setSurfaceTextureListener(this);
  }

  public BaseTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setSurfaceTextureListener(this);
  }

  @Override public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
    drawThread = new HandlerThread("asyncDraw");
    drawThread.start();
    handler = new Handler(drawThread.getLooper()) {
      @Override public void handleMessage(Message msg) {
        switch (msg.what) {
          case MSG_DRAW:
            Canvas canvas = lockCanvas();
            onAsyncDraw(canvas);
            unlockCanvasAndPost(canvas);
            break;
        }
      }
    };
  }

  @Override public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

  }

  @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
    handler.removeMessages(MSG_DRAW);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      drawThread.quitSafely();
    } else {
      drawThread.quit();
    }
    return true;
  }

  @Override public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

  }

  public void asyncDraw() {
    handler.sendEmptyMessage(MSG_DRAW);
  }

  abstract void onAsyncDraw(Canvas canvas);
}
