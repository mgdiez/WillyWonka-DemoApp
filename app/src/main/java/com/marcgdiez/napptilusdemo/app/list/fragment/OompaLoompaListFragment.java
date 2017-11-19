package com.marcgdiez.napptilusdemo.app.list.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.app.di.component.OompaLoompasComponent;
import com.marcgdiez.napptilusdemo.app.list.presenter.OompaLoompaListPresenter;
import com.marcgdiez.napptilusdemo.app.list.adapter.OompaLoompaAdapter;
import com.marcgdiez.napptilusdemo.app.list.di.component.OompaLoompaListComponent;
import com.marcgdiez.napptilusdemo.app.list.di.module.OompaLoompaListModule;
import com.marcgdiez.napptilusdemo.app.list.view.OompaLoompaListView;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.story.StoryController;
import com.marcgdiez.napptilusdemo.core.view.fragment.RootFragment;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import javax.inject.Inject;

public class OompaLoompaListFragment extends RootFragment implements OompaLoompaListView {

  @Inject OompaLoompaListPresenter presenter;
  @Inject OompaLoompasStoryController storyController;
  @Inject OompaLoompaAdapter adapter;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.progressBar) ContentLoadingProgressBar progressBar;
  @BindView(R.id.error) TextView error;

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
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!recyclerView.canScrollVertically(1)) {
          presenter.onBottomReached();
        }
      }
    });
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

  @Override public void setItems(List<OompaLoompa> oompaLoompas) {
    hideProgress();
    adapter.setItems(oompaLoompas);
  }

  @Override public void showProgress() {
    recyclerView.setVisibility(View.GONE);
    progressBar.setVisibility(View.VISIBLE);
    error.setVisibility(View.GONE);
  }

  @Override public void hideProgress() {
    recyclerView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
    error.setVisibility(View.GONE);
  }

  @Override public void showErrorMessage(String errorMessage) {
    recyclerView.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    error.setVisibility(View.VISIBLE);
  }
}