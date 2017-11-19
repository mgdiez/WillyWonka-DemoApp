package com.marcgdiez.napptilusdemo.app.list.di.module;

import com.marcgdiez.napptilusdemo.app.list.presenter.OompaLoompaListPresenter;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class OompaLoompaListModule {
  @Provides @PerFragment @Named("getOompasLoompasUseCase")
  public Interactor<OompaLoompaPage> provideGetOompasLoompasUseCase(
      GetOompasLoompasUseCase getOompasLoompasUseCase) {
    return getOompasLoompasUseCase;
  }

  @Provides @PerFragment public OompaLoompaListPresenter providePresenter(
      @Named("getOompasLoompasUseCase") Interactor<OompaLoompaPage> getOompasLoompasUseCase,
      OompaLoompasStoryController oompaLoompasStoryController) {
    return new OompaLoompaListPresenter(getOompasLoompasUseCase, oompaLoompasStoryController);
  }
}
