package com.eex.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.eex.R;

/**
 * Created by Administrator on 2017/11/11.
 */

public class ComTitleBar extends FrameLayout {
  @BindView(R.id.title_tv)
  TextView titleTv;
  @BindView(R.id.right_tv)
  TextView rightTv;
  @BindView(R.id.back_iv)
  ImageView back_iv;

  @BindView(R.id.right_image)
  ImageView rightImage;

  public ComTitleBar(@NonNull Context context) {
    this(context, null);
  }

  public ComTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    View view = LayoutInflater.from(context).inflate(R.layout.title_bar, this);
    ButterKnife.bind(this, view);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ComTitleBar);

    String title = typedArray.getString(R.styleable.ComTitleBar_title);
    titleTv.setText(title);

    String right_text = typedArray.getString(R.styleable.ComTitleBar_right_text);
    rightTv.setText(right_text);

    int title_size = typedArray.getInt(R.styleable.ComTitleBar_title_size, 0);
    if (title_size != 0) titleTv.setTextSize(title_size);

    int titleColor = typedArray.getColor(R.styleable.ComTitleBar_title_color,
        ResourcesCompat.getColor(context.getResources(), R.color.white, null));

    titleTv.setTextColor(titleColor);
    int color = typedArray.getColor(R.styleable.ComTitleBar_right_text_color,
        ResourcesCompat.getColor(context.getResources(), R.color.white, null));
    rightTv.setTextColor(color);
    typedArray.recycle();
  }

  public void setTitle(String title) {
    titleTv.setText(title);
  }

  public void setBackGone() {
    back_iv.setVisibility(GONE);
  }

  public void setBackListener(OnClickListener listener) {
    back_iv.setOnClickListener(listener);
  }

  public void setRightText(String text) {
    rightTv.setText(text);
  }

  public void setRightText(String text, OnClickListener listener) {
    rightTv.setText(text);
    rightTv.setOnClickListener(listener);
  }

  public void setImageView(int right_Image) {
    rightImage.setImageDrawable(getResources().getDrawable(right_Image));
  }

  public void setImageViewListener(OnClickListener listener) {
    rightImage.setOnClickListener(listener);
  }

  @OnClick({ R.id.back_iv, R.id.right_image, R.id.right_tv })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_iv:
        if (getContext() instanceof Activity) {
          ((Activity) getContext()).onBackPressed();
        }
        break;

      case R.id.right_tv:
        if (getContext() instanceof Activity) {
          ((Activity) getContext()).onBackPressed();
        }
        break;

      case R.id.right_image:
        if (getContext() instanceof Activity) {
          ((Activity) getContext()).onBackPressed();
        }
        break;
      default:
        break;
    }
  }
}
