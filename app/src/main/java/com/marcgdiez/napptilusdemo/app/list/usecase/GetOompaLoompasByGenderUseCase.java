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

public class GetOompaLoompasByGenderUseCase extends Interactor<List<OompaLoompa>> {

  private final OompaLoompaRepository oompaLoompaRepository;
  private String gender;

  @Inject public GetOompaLoompasByGenderUseCase(Executor executor, MainThread mainThread,
      OompaLoompaRepository oompaLoompaRepository) {
    super(executor, mainThread);

    if (oompaLoompaRepository == null) {
      throw new IllegalArgumentException(
          "GetOompaLoompasByGenderUseCase must have valid parameters");
    }

    this.oompaLoompaRepository = oompaLoompaRepository;
  }

  public void execute(String gender, Subscriber<List<OompaLoompa>> subscriber) {
    this.gender = gender;
    super.execute(subscriber);
  }

  @Override protected Observable<List<OompaLoompa>> buildObservable() {
    return oompaLoompaRepository.getOompaLoompasByGender(gender)
        .switchIfEmpty(Observable.error(new OompaLoompasNotFoundException()));
  }
}
