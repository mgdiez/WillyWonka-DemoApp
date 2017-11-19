package com.marcgdiez.napptilusdemo.app.detail.usecase;

import com.marcgdiez.napptilusdemo.app.list.exception.OompaLoompasNotFoundException;
import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class GetOompaDetailUseCase extends Interactor<OompaLoompa> {
  private final OompaLoompaRepository oompaLoompaRepository;
  private int id;

  @Inject public GetOompaDetailUseCase(Executor executor, MainThread mainThread,
      OompaLoompaRepository oompaLoompaRepository) {
    super(executor, mainThread);

    if (oompaLoompaRepository == null) {
      throw new IllegalArgumentException("GetOompasLoompasUseCase must have valid parameters");
    }

    this.oompaLoompaRepository = oompaLoompaRepository;
  }

  public void execute(int id, Subscriber<OompaLoompa> subscriber) {
    this.id = id;
    super.execute(subscriber);
  }

  @Override protected Observable<OompaLoompa> buildObservable() {
    return oompaLoompaRepository.getOompaLoompa(id)
        .switchIfEmpty(Observable.error(new OompaLoompasNotFoundException()));
  }
}