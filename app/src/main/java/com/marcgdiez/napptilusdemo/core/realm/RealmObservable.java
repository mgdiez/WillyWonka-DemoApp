package com.marcgdiez.napptilusdemo.core.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

public class RealmObservable {
  private RealmObservable() {
  }

  public static <T extends RealmObject> Observable<T> just(final Func1<Realm, T> function) {
    return Observable.unsafeCreate(new OnSubscribeRealm<T>() {
      @Override public T get(Realm realm) {
        return function.call(realm);
      }
    });
  }

  public static <T extends RealmResults<? extends RealmObject>> Observable<T> from(
      final Func1<Realm, T> function) {
    return Observable.unsafeCreate(new OnSubscribeRealmList<T>() {
      @Override public T get(Realm realm) {
        return function.call(realm);
      }
    });
  }
}

