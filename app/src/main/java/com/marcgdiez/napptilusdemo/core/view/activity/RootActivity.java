package com.marcgdiez.napptilusdemo.core.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.marcgdiez.napptilusdemo.app.di.component.ApplicationComponent;
import com.marcgdiez.napptilusdemo.app.di.module.ActivityModule;
import com.marcgdiez.napptilusdemo.core.NapptilusApplication;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.story.StoryController;

public abstract class RootActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeInjector();

    int layoutResourceId = getLayoutResourceId();
    if (layoutResourceId > 0) {
      setContentView(layoutResourceId);
    }

    if (savedInstanceState != null) {
      StoryController storyController = getStoryController();
      if (storyController != null) {
        storyController.restoreState(savedInstanceState);
      }
    }

    findViews();
    initializeActivity(savedInstanceState);
    ButterKnife.bind(this);
  }

  protected abstract void findViews();

  @LayoutRes protected abstract int getLayoutResourceId();

  protected abstract void initializeInjector();

  protected abstract void initializeActivity(Bundle savedInstanceState);

  @Override protected void onDestroy() {
    super.onDestroy();

    Presenter presenter = getPresenter();
    if (presenter != null) {
      presenter.stop();
    }

    StoryController storyController = getStoryController();
    if (storyController != null) {
      storyController.cleanStoryContainer();
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    StoryController storyController = getStoryController();
    if (storyController != null) {
      storyController.saveState(outState);
    }
  }

  protected abstract Presenter getPresenter();

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    StoryController storyController = getStoryController();
    if (storyController != null) {
      storyController.restoreState(savedInstanceState);
    }
  }

  protected abstract StoryController getStoryController();

  protected ApplicationComponent getApplicationComponent() {
    return ((NapptilusApplication) getApplication()).getComponent();
  }

  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
