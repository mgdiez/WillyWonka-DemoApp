package com.marcgdiez.napptilusdemo.core.view.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.marcgdiez.napptilusdemo.core.di.HasComponent;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.story.StoryController;

public abstract class RootFragment extends Fragment {

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeTransition();
    initializeInjector();
  }

  protected void initializeTransition() {
    //Override if fragment performs a transition.
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    int fragmentLayoutResourceId = getFragmentLayoutResourceId();
    if (fragmentLayoutResourceId < 1) {
      throw new IllegalArgumentException("Fragment must have a valid layout resource Id");
    }

    View view = inflater.inflate(fragmentLayoutResourceId, container, false);
    if (view != null) {
      ButterKnife.bind(this, view);
    }
    return view;
  }

  protected abstract void initializePresenter();

  @LayoutRes protected abstract int getFragmentLayoutResourceId();

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    restoreStoryState(savedInstanceState);
    initializeView(view);
    initializePresenter();
  }

  protected abstract void initializeView(View view);

  protected abstract void initializeInjector();

  private void restoreStoryState(Bundle savedInstanceState) {
    StoryController storyController = getStoryController();
    if (storyController != null) {
      storyController.restoreState(savedInstanceState);
    }
  }

  protected abstract StoryController getStoryController();

  @Override public void onResume() {
    super.onResume();

    Presenter presenter = getPresenter();
    if (presenter != null) {
      presenter.start();
    }
  }

  @Override public void onStop() {
    super.onStop();

    Presenter presenter = getPresenter();
    if (presenter != null) {
      presenter.stop();
    }
  }

  protected abstract Presenter getPresenter();

  @SuppressWarnings("unchecked") protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }
}
