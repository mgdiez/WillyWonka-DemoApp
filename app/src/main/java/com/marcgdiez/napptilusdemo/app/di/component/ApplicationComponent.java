package com.marcgdiez.napptilusdemo.app.di.component;

import com.marcgdiez.napptilusdemo.app.di.module.ActivityModule;
import com.marcgdiez.napptilusdemo.app.di.module.ApplicationModule;
import com.marcgdiez.napptilusdemo.app.list.di.component.OompaLoompasComponent;
import com.marcgdiez.napptilusdemo.app.list.di.module.OompaLoompasModule;
import com.marcgdiez.napptilusdemo.core.NapptilusApplication;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  void inject(NapptilusApplication application);

  // Sub components generation
  OompaLoompasComponent oompaLoompasComponent(OompaLoompasModule oompaLoompasModule,
      ActivityModule activityModule);
}
