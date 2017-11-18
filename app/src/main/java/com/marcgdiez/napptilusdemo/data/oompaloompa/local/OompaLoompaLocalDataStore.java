package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import javax.inject.Inject;
import rx.Observable;

public class OompaLoompaLocalDataStore implements OompaLoompaDataStore {

  @Inject public OompaLoompaLocalDataStore() {
  }

  @Override public Observable<OompaLoompaPage> getOompaLoompas(int page) {
    return null;
  }

  @Override public void persistOompas(OompaLoompaPage response) {

  }
}
