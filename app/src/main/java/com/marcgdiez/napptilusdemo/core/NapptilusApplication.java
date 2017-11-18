package com.marcgdiez.napptilusdemo.core;

import android.app.Application;
import com.marcgdiez.napptilusdemo.app.di.component.ApplicationComponent;
import com.marcgdiez.napptilusdemo.core.di.HasComponent;

public class NapptilusApplication extends Application
    implements HasComponent<ApplicationComponent> {

  protected ApplicationComponent applicationComponent;

  @Override public ApplicationComponent getComponent() {
    return applicationComponent;
  }
}
