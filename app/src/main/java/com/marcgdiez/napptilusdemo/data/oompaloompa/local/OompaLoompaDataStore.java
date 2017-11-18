package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import rx.Observable;

public interface OompaLoompaDataStore {
  Observable<OompaLoompaPage> getOompaLoompas(int page);

  void persistOompas(OompaLoompaPage response);
}