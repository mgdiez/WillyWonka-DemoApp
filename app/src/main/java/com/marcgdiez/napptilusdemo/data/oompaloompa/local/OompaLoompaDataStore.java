package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import rx.Observable;

public interface OompaLoompaDataStore {
  Observable<List<OompaLoompa>> getOompaLoompas(int page);

  void persistOompas(List<OompaLoompa> response);
}
