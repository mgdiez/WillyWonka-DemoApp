package com.marcgdiez.napptilusdemo.app.di.module;

import android.content.Context;
import com.marcgdiez.napptilusdemo.core.NapptilusApplication;
import dagger.Module;

@Module public class ApplicationModule {
  private final Context context;

  public ApplicationModule(NapptilusApplication application) {
    context = application.getApplicationContext();
  }
}