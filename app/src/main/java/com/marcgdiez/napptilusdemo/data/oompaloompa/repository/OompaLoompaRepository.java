package com.marcgdiez.napptilusdemo.data.oompaloompa.repository;

import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import rx.Observable;

public interface OompaLoompaRepository {

  Observable<List<OompaLoompa>> getOompaLoompas(int count);
}
