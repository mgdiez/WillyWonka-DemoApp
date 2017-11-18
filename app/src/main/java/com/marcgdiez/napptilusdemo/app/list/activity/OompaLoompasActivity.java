package com.marcgdiez.napptilusdemo.app.list.activity;

import android.os.Bundle;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.app.di.module.ActivityModule;
import com.marcgdiez.napptilusdemo.app.list.di.component.OompaLoompasComponent;
import com.marcgdiez.napptilusdemo.app.list.di.module.OompaLoompasModule;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.di.HasComponent;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.story.StoryContainer;
import com.marcgdiez.napptilusdemo.core.story.StoryController;
import com.marcgdiez.napptilusdemo.core.view.activity.RootActivity;
import javax.inject.Inject;

public class OompaLoompasActivity extends RootActivity
    implements StoryContainer, HasComponent<OompaLoompasComponent> {

  @Inject OompaLoompasStoryController storyController;
  private OompaLoompasComponent oompaLoompasComponent;

  @Override protected void findViews() {
  }

  @Override protected int getLayoutResourceId() {
    return R.layout.activity_main;
  }

  @Override protected void initializeInjector() {
    oompaLoompasComponent =
        getApplicationComponent().oompaLoompasComponent(new OompaLoompasModule(this),
            new ActivityModule(this));
    oompaLoompasComponent.inject(this);
  }

  @Override protected void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      storyController.navigateToList();
    }
  }

  @Override protected Presenter getPresenter() {
    //Does not need presenter
    return null;
  }

  @Override protected StoryController getStoryController() {
    return storyController;
  }

  @Override public int getContainerId() {
    return R.id.fragment_container;
  }

  @Override public void close() {
    finish();
  }

  @Override public OompaLoompasComponent getComponent() {
    return oompaLoompasComponent;
  }
}