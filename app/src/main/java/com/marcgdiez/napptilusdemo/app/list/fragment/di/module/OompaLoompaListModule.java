package com.marcgdiez.napptilusdemo.app.list.fragment.di.module;

import com.marcgdiez.napptilusdemo.app.list.fragment.OompaLoompaListPresenter;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import dagger.Module;
import dagger.Provides;

@Module public class OompaLoompaListModule {

  @Provides @PerFragment public OompaLoompaListPresenter providePresenter() {
    return new OompaLoompaListPresenter();
  }
}
