package com.marcgdiez.napptilusdemo.app.di.component;

import com.marcgdiez.napptilusdemo.app.di.module.ApplicationModule;
import com.marcgdiez.napptilusdemo.core.NapptilusApplication;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  void inject(NapptilusApplication application);
}
