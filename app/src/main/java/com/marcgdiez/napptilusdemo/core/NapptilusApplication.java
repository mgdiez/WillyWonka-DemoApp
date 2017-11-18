package com.marcgdiez.napptilusdemo.core;

import android.app.Application;
import com.marcgdiez.napptilusdemo.app.di.component.ApplicationComponent;
import com.marcgdiez.napptilusdemo.app.di.component.DaggerApplicationComponent;
import com.marcgdiez.napptilusdemo.app.di.module.ApplicationModule;
import com.marcgdiez.napptilusdemo.core.di.HasComponent;

public class NapptilusApplication extends Application
    implements HasComponent<ApplicationComponent> {

  protected ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    initializeInjector();

  }

  @Override public ApplicationComponent getComponent() {
    return applicationComponent;
  }

  private void initializeInjector() {
    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    applicationComponent.inject(this);
  }
}
