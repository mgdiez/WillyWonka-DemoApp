package com.marcgdiez.napptilusdemo.app.list.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.app.list.di.component.OompaLoompasComponent;
import com.marcgdiez.napptilusdemo.app.list.fragment.di.component.OompaLoompaListComponent;
import com.marcgdiez.napptilusdemo.app.list.fragment.di.module.OompaLoompaListModule;
import com.marcgdiez.napptilusdemo.app.list.fragment.view.OompaLoompaListView;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.story.StoryController;
import com.marcgdiez.napptilusdemo.core.view.fragment.RootFragment;
import javax.inject.Inject;

public class OompaLoompaListFragment extends RootFragment implements OompaLoompaListView {

  @Inject OompaLoompaListPresenter presenter;
  @Inject OompaLoompasStoryController storyController;

  public static Fragment newInstance() {
    return new OompaLoompaListFragment();
  }

  @Override protected void initializePresenter() {
    presenter.setView(this);
  }

  @Override protected int getFragmentLayoutResourceId() {
    return R.layout.fragment_oompas_list;
  }

  @Override protected void initializeView(View view) {

  }

  @Override protected void initializeInjector() {
    OompaLoompaListComponent oompaLoompaListComponent =
        getComponent(OompaLoompasComponent.class).oompaLoompaListComponent(
            new OompaLoompaListModule());

    oompaLoompaListComponent.inject(this);
  }

  @Override protected StoryController getStoryController() {
    return storyController;
  }

  @Override protected Presenter getPresenter() {
    return presenter;
  }
}