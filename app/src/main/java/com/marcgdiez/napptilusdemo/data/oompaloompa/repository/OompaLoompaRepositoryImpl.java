package com.marcgdiez.napptilusdemo.data.oompaloompa.repository;

import com.marcgdiez.napptilusdemo.data.oompaloompa.exception.OompaLoompaRepositoryException;
import com.marcgdiez.napptilusdemo.data.oompaloompa.local.OompaLoompaDataStore;
import com.marcgdiez.napptilusdemo.data.oompaloompa.network.OompaLoompaDataSource;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
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

  @Override public Observable<OompaLoompaPage> getOompaLoompas(int page) {
    if (page < 1) {
      return Observable.error(
          new OompaLoompaRepositoryException("getOompaLoompas: Page must be valid."));
    }

    return Observable.concat(requestLocalPage(page),
        requestNetworkPage(page).doOnNext(this::persistOompasPage)).first();
  }

  @Override public Observable<OompaLoompa> getOompaLoompa(int id) {
    if (id < 0) {
      return Observable.error(
          new OompaLoompaRepositoryException("getOompaLoompa: ID must be valid."));
    }

    return requestLocalOompa(id).flatMap(oompaLoompa -> {
      if (oompaLoompa.getDescription() == null || oompaLoompa.getDescription().isEmpty()) {
        return requestNetworkOompa(id).doOnNext(oompaLoompaResponse -> {
          oompaLoompaResponse.setId(id);
          persistOompasDetail(oompaLoompaResponse);
        });
      } else {
        return Observable.just(oompaLoompa);
      }
    });
  }

  @Override public Observable<List<OompaLoompa>> getOompaLoompasByQuery(String query) {
    return dataStore.getOompaLoompasByQuery(query);
  }

  private void persistOompasDetail(OompaLoompa oompaLoompa) {
    dataStore.persistOompa(oompaLoompa);
  }

  private Observable<OompaLoompa> requestNetworkOompa(int id) {
    return dataSource.getOompaLoompaDetail(id);
  }

  private Observable<OompaLoompa> requestLocalOompa(int id) {
    return dataStore.getOompaLoompaDetail(id);
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
