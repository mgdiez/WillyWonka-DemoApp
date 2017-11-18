package com.marcgdiez.napptilusdemo.data;

import io.realm.Realm;
import io.realm.RealmObject;
import java.util.ArrayList;
import java.util.List;

public class CommonLocalDataStore {
  protected boolean saveIterable(List<? extends RealmObject> realmObjects) {
    try (Realm realm = Realm.getDefaultInstance()) {
      realm.beginTransaction();
      realm.copyToRealmOrUpdate(realmObjects);
      realm.commitTransaction();
      if (!realm.isClosed()) {
        realm.close();
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  protected boolean saveItem(RealmObject realmObject) {
    List<RealmObject> realmObjects = new ArrayList<>();
    realmObjects.add(realmObject);
    return saveIterable(realmObjects);
  }
}
