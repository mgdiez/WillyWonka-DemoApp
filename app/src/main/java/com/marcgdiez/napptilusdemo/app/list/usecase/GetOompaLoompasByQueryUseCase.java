package com.marcgdiez.napptilusdemo.app.list.usecase;

import com.marcgdiez.napptilusdemo.app.list.exception.OompaLoompasNotFoundException;
import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class GetOompaLoompasByQueryUseCase extends Interactor<List<OompaLoompa>> {
  private final OompaLoompaRepository oompaLoompaRepository;
  private String query;

  @Inject public GetOompaLoompasByQueryUseCase(Executor executor, MainThread mainThread,
      OompaLoompaRepository oompaLoompaRepository) {
    super(executor, mainThread);
    this.oompaLoompaRepository = oompaLoompaRepository;
  }

  public void execute(String query, Subscriber<List<OompaLoompa>> subscriber) {
    this.query = query;
    super.execute(subscriber);
  }

  @Override protected Observable<List<OompaLoompa>> buildObservable() {
    return oompaLoompaRepository.getOompaLoompasByQuery(query)
        .switchIfEmpty(Observable.error(new OompaLoompasNotFoundException()));
  }
}
