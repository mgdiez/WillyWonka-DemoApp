package com.marcgdiez.napptilusdemo.data.oompaloompa.network;

import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import rx.Observable;

public interface OompaLoompaDataSource {
  Observable<OompaLoompaPage> getOompaLoompas(int page);
}
