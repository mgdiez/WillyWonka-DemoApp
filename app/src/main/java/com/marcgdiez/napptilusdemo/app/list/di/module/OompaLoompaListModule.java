package com.marcgdiez.napptilusdemo.app.list.di.module;

import com.marcgdiez.napptilusdemo.app.list.presenter.OompaLoompaListPresenter;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompaLoompasByGenderUseCase;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompaLoompasByQueryUseCase;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;

@Module public class OompaLoompaListModule {
  @Provides @PerFragment @Named("getOompasLoompasUseCase")
  public Interactor<OompaLoompaPage> provideGetOompasLoompasUseCase(
      GetOompasLoompasUseCase getOompasLoompasUseCase) {
    return getOompasLoompasUseCase;
  }

  @Provides @PerFragment @Named("getOompaLoompasByQueryUseCase")
  public Interactor<List<OompaLoompa>> provideGetOompaLoompasByQueryUseCase(
      GetOompaLoompasByQueryUseCase getOompaLoompasByQueryUseCase) {
    return getOompaLoompasByQueryUseCase;
  }

  @Provides @PerFragment @Named("getOompaLoompasByGenderUseCase")
  public Interactor<List<OompaLoompa>> provideGetOompaLoompasByGenderUseCase(
      GetOompaLoompasByGenderUseCase getOompaLoompasByGenderUseCase) {
    return getOompaLoompasByGenderUseCase;
  }

  @Provides @PerFragment public OompaLoompaListPresenter providePresenter(
      @Named("getOompasLoompasUseCase") Interactor<OompaLoompaPage> getOompasLoompasUseCase,
      @Named("getOompaLoompasByQueryUseCase")
          Interactor<List<OompaLoompa>> getOompaLoompasByQueryUseCase,
      @Named("getOompaLoompasByGenderUseCase")
          Interactor<List<OompaLoompa>> getOompaLoompasByGenderUseCase,
      OompaLoompasStoryController oompaLoompasStoryController) {
    return new OompaLoompaListPresenter(getOompasLoompasUseCase, getOompaLoompasByQueryUseCase,
        getOompaLoompasByGenderUseCase, oompaLoompasStoryController);
  }
}
