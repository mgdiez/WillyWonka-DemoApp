package com.marcgdiez.napptilusdemo.core.interactor;

import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import java.util.concurrent.Executor;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class Interactor<T> {

  protected final Executor executor;
  protected final MainThread mainThread;
  protected Subscription subscription = Subscriptions.empty();

  public Interactor(Executor executor, MainThread mainThread) {
    if (executor == null || mainThread == null) {
      throw new IllegalArgumentException("Interactor constructor must have valid parameters.");
    }
    this.executor = executor;
    this.mainThread = mainThread;
  }

  public void execute(Subscriber<T> subscriber) {
    subscription = buildObservable().observeOn(mainThread.getScheduler())
        .subscribeOn(Schedulers.from(executor))
        .subscribe(subscriber);
  }

  protected abstract Observable<T> buildObservable();

  public void unsubcribe() {
    if (!subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}