package com.marcgdiez.napptilusdemo.data.oompaloompa.network;

import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import rx.Observable;

public interface OompaLoompaDataSource {
  Observable<List<OompaLoompa>> getOompaLoompas(int page);
}
