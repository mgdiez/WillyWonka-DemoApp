package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import rx.Observable;

public class OompaLoompaLocalDataStore implements OompaLoompaDataStore {
  @Override public Observable<List<OompaLoompa>> getOompaLoompas(int page) {
    return null;
  }

  @Override public void persistOompas(List<OompaLoompa> response) {

  }
}
