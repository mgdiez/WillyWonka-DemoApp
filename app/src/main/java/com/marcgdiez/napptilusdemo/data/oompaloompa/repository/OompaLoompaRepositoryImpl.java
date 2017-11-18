package com.marcgdiez.napptilusdemo.data.oompaloompa.repository;

import com.marcgdiez.napptilusdemo.data.oompaloompa.local.OompaLoompaDataStore;
import com.marcgdiez.napptilusdemo.data.oompaloompa.network.OompaLoompaDataSource;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import javax.inject.Inject;
import rx.Observable;

public class OompaLoompaRepositoryImpl implements OompaLoompaRepository {

  private final OompaLoompaDataStore dataStore;
  private final OompaLoompaDataSource dataSource;

  @Inject public OompaLoompaRepositoryImpl(OompaLoompaDataStore dataStore,
      OompaLoompaDataSource dataSource) {
    if (dataStore == null || dataSource == null) {
      throw new IllegalArgumentException(
          "OompaLoompaRepository must have valid arguments on constructor.");
    }

    this.dataStore = dataStore;
    this.dataSource = dataSource;
  }

  @Override public Observable<OompaLoompaPage> getOompaLoompas(int page) {
    return Observable.concat(requestLocalPage(page),
        requestNetworkPage(page).doOnNext(this::persistOompasPage)).first();
  }

  private void persistOompasPage(OompaLoompaPage response) {
    dataStore.persistOompas(response);
  }

  private Observable<OompaLoompaPage> requestNetworkPage(int page) {
    return dataSource.getOompaLoompas(page);
  }

  private Observable<OompaLoompaPage> requestLocalPage(int page) {
    return dataStore.getOompaLoompas(page);
  }
}
