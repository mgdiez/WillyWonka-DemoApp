package com.marcgdiez.napptilusdemo.app.detail.di.module;

import com.marcgdiez.napptilusdemo.app.detail.presenter.OompaLoompaDetailPresenter;
import com.marcgdiez.napptilusdemo.app.detail.usecase.GetOompaDetailUseCase;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class OompaDetailModule {
  @Provides @PerFragment @Named("getOompaDetailUseCase")
  public Interactor<OompaLoompa> provideGetOompaDetailUseCase(
      GetOompaDetailUseCase getOompaDetailUseCase) {
    return getOompaDetailUseCase;
  }

  @Provides @PerFragment public OompaLoompaDetailPresenter providePresenter(
      @Named("getOompaDetailUseCase") Interactor<OompaLoompa> getOompasLoompasUseCase,
      OompaLoompasStoryController oompaLoompasStoryController) {
    return new OompaLoompaDetailPresenter(getOompasLoompasUseCase, oompaLoompasStoryController);
  }
}
