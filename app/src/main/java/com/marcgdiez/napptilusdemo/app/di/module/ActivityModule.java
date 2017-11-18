package com.marcgdiez.napptilusdemo.app.di.module;

import android.support.v7.app.AppCompatActivity;
import com.marcgdiez.napptilusdemo.core.di.PerActivity;
import com.marcgdiez.napptilusdemo.core.view.activity.RootActivity;
import dagger.Module;
import dagger.Provides;

@Module public class ActivityModule {

  private final AppCompatActivity activity;

  public ActivityModule(RootActivity activity) {
    this.activity = activity;
  }

  @Provides @PerActivity public AppCompatActivity provideActivity() {
    return activity;
  }
}
