package com.eex.subcribers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

public abstract class CompletionObserver<T> implements Observer<T> {

  @Override
  public void onSubscribe(Disposable d) {

  }

  @Override
  public void onError(Throwable t) {
    onDone();
    Timber.e(t);
    RxJavaPlugins.onError(t);
  }

  @Override
  public void onComplete() {
    onDone();
  }

  public abstract void onDone();
}
