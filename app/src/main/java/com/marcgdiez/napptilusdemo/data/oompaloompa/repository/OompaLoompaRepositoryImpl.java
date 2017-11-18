package com.marcgdiez.napptilusdemo.data.oompaloompa.repository;

import com.marcgdiez.napptilusdemo.data.oompaloompa.local.OompaLoompaDataStore;
import com.marcgdiez.napptilusdemo.data.oompaloompa.network.OompaLoompaDataSource;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
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

  @Override public Observable<List<OompaLoompa>> getOompaLoompas(int count) {
    return null;
  }
}
