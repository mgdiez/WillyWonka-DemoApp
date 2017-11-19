package com.marcgdiez.napptilusdemo.app.di.component;

import com.marcgdiez.napptilusdemo.app.di.module.ActivityModule;
import com.marcgdiez.napptilusdemo.app.activity.OompaLoompasActivity;
import com.marcgdiez.napptilusdemo.app.di.module.OompaLoompasModule;
import com.marcgdiez.napptilusdemo.app.list.di.component.OompaLoompaListComponent;
import com.marcgdiez.napptilusdemo.app.list.di.module.OompaLoompaListModule;
import com.marcgdiez.napptilusdemo.core.di.PerActivity;
import dagger.Subcomponent;

@PerActivity @Subcomponent(modules = {
    ActivityModule.class, OompaLoompasModule.class
}) public interface OompaLoompasComponent {
  void inject(OompaLoompasActivity activity);

  OompaLoompaListComponent oompaLoompaListComponent(OompaLoompaListModule oompaLoompasListModule);
}
