package com.marcgdiez.napptilusdemo.core.subscriber;

import rx.Subscriber;

public abstract class DefaultSubscriber<T> extends Subscriber<T> {

  @Override public void onCompleted() {
    this.unsubscribe();
  }

  @Override public void onError(Throwable e) {
    this.unsubscribe();
    onError(ExceptionMessageConverter.convert(e));
  }

  @Override public void onNext(T t) {

  }

  protected abstract void onError(String errorMessage);
}