package com.marcgdiez.napptilusdemo.app.list.fragment.di.module;

import com.marcgdiez.napptilusdemo.app.list.fragment.OompaLoompaListPresenter;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;

@Module public class OompaLoompaListModule {
  @Provides @PerFragment @Named("getOompasLoompasUseCase")
  public Interactor<List<OompaLoompa>> provideGetOompasLoompasUseCase(
      GetOompasLoompasUseCase getOompasLoompasUseCase) {
    return getOompasLoompasUseCase;
  }

  @Provides @PerFragment public OompaLoompaListPresenter providePresenter(
      @Named("getOompasLoompasUseCase") Interactor<List<OompaLoompa>> getOompasLoompasUseCase,
      OompaLoompasStoryController oompaLoompasStoryController) {
    return new OompaLoompaListPresenter(getOompasLoompasUseCase, oompaLoompasStoryController);
  }
}
