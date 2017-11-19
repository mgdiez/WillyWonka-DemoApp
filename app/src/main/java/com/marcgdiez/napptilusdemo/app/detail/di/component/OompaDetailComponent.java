package com.marcgdiez.napptilusdemo.app.detail.di.component;

import com.marcgdiez.napptilusdemo.app.detail.di.module.OompaDetailModule;
import com.marcgdiez.napptilusdemo.app.detail.fragment.OompaLoompaDetailFragment;
import com.marcgdiez.napptilusdemo.core.di.PerFragment;
import dagger.Subcomponent;

@PerFragment @Subcomponent(modules = OompaDetailModule.class)
public interface OompaDetailComponent {
  void inject(OompaLoompaDetailFragment fragment);
}
