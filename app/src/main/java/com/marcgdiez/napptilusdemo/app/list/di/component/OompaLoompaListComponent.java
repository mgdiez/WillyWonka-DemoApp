package com.marcgdiez.napptilusdemo.app.list.di.component;

import com.marcgdiez.napptilusdemo.app.list.fragment.OompaLoompaListFragment;
import com.marcgdiez.napptilusdemo.app.list.di.module.OompaLoompaListModule;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import dagger.Subcomponent;

@PerFragment @Subcomponent(modules = OompaLoompaListModule.class)
public interface OompaLoompaListComponent {
  void inject(OompaLoompaListFragment fragment);
}
