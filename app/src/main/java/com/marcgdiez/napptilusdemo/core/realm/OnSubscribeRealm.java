package com.marcgdiez.napptilusdemo.core.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;

public abstract class OnSubscribeRealm<T extends RealmObject> implements Observable.OnSubscribe<T> {

  public OnSubscribeRealm() {
  }

  @Override public void call(Subscriber<? super T> subscriber) {
    Realm realm = Realm.getDefaultInstance();

    T object;
    realm.beginTransaction();

    try {
      object = get(realm);
    } catch (RuntimeException e) {
      realm.cancelTransaction();
      subscriber.onError(new RealmException("Error during transaction.", e));
      if (!realm.isClosed()) {
        realm.close();
      }
      return;
    } catch (Exception error) {
      realm.cancelTransaction();
      subscriber.onError(error);
      if (!realm.isClosed()) {
        realm.close();
      }
      return;
    } finally {
      realm.commitTransaction();
    }

    subscriber.onNext(object);
    subscriber.onCompleted();

    if (!realm.isClosed()) {
      realm.close();
    }
  }

  public abstract T get(Realm realm);
}
