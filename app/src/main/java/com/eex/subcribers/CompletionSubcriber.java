package com.eex.subcribers;

import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import timber.log.Timber;

public abstract class CompletionSubcriber<T> implements Subscriber<T> {

  @Override
  public void onSubscribe(Subscription s) {

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
