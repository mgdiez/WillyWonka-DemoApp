package com.marcgdiez.napptilusdemo.data.oompaloompa.repository;

import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import rx.Observable;

public interface OompaLoompaRepository {

  Observable<OompaLoompaPage> getOompaLoompas(int page);

  Observable<OompaLoompa> getOompaLoompa(int id);
}
