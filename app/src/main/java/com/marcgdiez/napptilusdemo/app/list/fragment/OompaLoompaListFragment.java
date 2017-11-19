package com.marcgdiez.napptilusdemo.app.list.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.app.activity.OompaLoompasActivity;
import com.marcgdiez.napptilusdemo.app.di.component.OompaLoompasComponent;
import com.marcgdiez.napptilusdemo.app.list.adapter.OompaLoompaAdapter;
import com.marcgdiez.napptilusdemo.app.list.di.component.OompaLoompaListComponent;
import com.marcgdiez.napptilusdemo.app.list.di.module.OompaLoompaListModule;
import com.marcgdiez.napptilusdemo.app.list.presenter.OompaLoompaListPresenter;
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

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    FragmentActivity activity = getActivity();
    if (activity != null) {
      activity.getMenuInflater().inflate(R.menu.menu, menu);
      MenuItem item = menu.findItem(R.id.search);
      SearchView searchView = (SearchView) item.getActionView();
      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(String query) {
          return false;
        }

        @Override public boolean onQueryTextChange(String newText) {
          presenter.onQueryTextChanged(newText);
          return true;
        }
      });
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.male:
        item.setChecked(!item.isChecked());
        presenter.onMaleChecked();
        return true;
      case R.id.female:
        item.setChecked(!item.isChecked());
        presenter.onFemaleChecked();
      default:
        return false;
    }
  }

  @Override protected void initializeView(View view) {
    setHasOptionsMenu(true);
    if (getActivity() != null) {
      ((OompaLoompasActivity) getActivity()).hideArrowToolbar();
    }

    adapter.addOnOompaSelectedListener(
        (oompaLoompa, image) -> presenter.onOompaSelected(oompaLoompa, image));
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

  @Override public void onDestroy() {
    super.onDestroy();
    adapter.removeOnOompaSelectedListener();
  }

  @Override protected StoryController getStoryController() {
    return storyController;
  }

  @Override protected Presenter getPresenter() {
    return presenter;
  }

  @Override public void setItems(List<OompaLoompa> oompaLoompas) {
    hideProgress();
    error.setVisibility(View.GONE);
    adapter.setItems(oompaLoompas);
  }

  @Override public void showProgress() {
    progressBar.setVisibility(View.VISIBLE);
    error.setVisibility(View.GONE);
  }

  @Override public void hideProgress() {
    recyclerView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
    error.setVisibility(View.GONE);
  }

  @Override public void showErrorMessage() {
    recyclerView.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    error.setVisibility(View.VISIBLE);
    error.setText(getResources().getString(R.string.error_message));
  }

  @Override public void setNewItems(List<OompaLoompa> oompaLoompas) {
    hideProgress();
    error.setVisibility(View.GONE);
    adapter.setNewItems(oompaLoompas);
  }

  @Override public void showNoResults() {
    recyclerView.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    error.setVisibility(View.VISIBLE);
    error.setText(getResources().getString(R.string.no_results));
  }
}