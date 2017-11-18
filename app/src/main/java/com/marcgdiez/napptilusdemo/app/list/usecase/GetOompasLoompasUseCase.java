package com.marcgdiez.napptilusdemo.app.list.usecase;

import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class GetOompasLoompasUseCase extends Interactor<OompaLoompaPage> {

  private final OompaLoompaRepository oompaLoompaRepository;
  private int page;

  @Inject public GetOompasLoompasUseCase(Executor executor, MainThread mainThread,
      OompaLoompaRepository oompaLoompaRepository) {
    super(executor, mainThread);
    this.oompaLoompaRepository = oompaLoompaRepository;
  }

  public void execute(int page, Subscriber<OompaLoompaPage> subscriber) {
    this.page = page;
    super.execute(subscriber);
  }

  @Override protected Observable<OompaLoompaPage> buildObservable() {
    return oompaLoompaRepository.getOompaLoompas(page);
  }
}
