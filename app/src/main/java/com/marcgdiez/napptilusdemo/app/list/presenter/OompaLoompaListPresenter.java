package com.marcgdiez.napptilusdemo.app.list.presenter;

import android.support.annotation.VisibleForTesting;
import android.widget.ImageView;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompaLoompasByGenderUseCase;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompaLoompasByQueryUseCase;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.app.list.view.OompaLoompaListView;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasState;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.subscriber.DefaultSubscriber;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import java.util.List;
import javax.inject.Inject;

public class OompaLoompaListPresenter extends Presenter<OompaLoompaListView> {

  private final OompaLoompasStoryController storyController;
  private final GetOompasLoompasUseCase getOompaLoompasUseCase;
  private final GetOompaLoompasByQueryUseCase getOompaLoompasByQueryUseCase;
  private final GetOompaLoompasByGenderUseCase getOompaLoompasByGenderUseCase;

  private boolean maleFilter = true;
  private boolean femaleFilter = true;

  @Inject public OompaLoompaListPresenter(Interactor<OompaLoompaPage> getOompaLoompasUseCase,
      Interactor<List<OompaLoompa>> getOompaLoompasByQueryUseCase,
      Interactor<List<OompaLoompa>> getOompaLoompasByGenderUseCase,
      OompaLoompasStoryController storyController) {

    this.getOompaLoompasUseCase = (GetOompasLoompasUseCase) getOompaLoompasUseCase;
    this.getOompaLoompasByQueryUseCase =
        (GetOompaLoompasByQueryUseCase) getOompaLoompasByQueryUseCase;
    this.getOompaLoompasByGenderUseCase =
        (GetOompaLoompasByGenderUseCase) getOompaLoompasByGenderUseCase;
    this.storyController = storyController;
  }

  @Override protected void initialize() {
    if (storyController.hasState()) {
      view.setItems(storyController.getStoryState().getOompaLoompas());
    } else {
      view.showProgress();
      OompaLoompasState storyState = new OompaLoompasState();
      storyController.setStoryState(storyState);
      getOompaLoompas(storyState.getPage());
    }
  }

  private void getOompaLoompas(int page) {
    getOompaLoompasUseCase.execute(page, new DefaultSubscriber<OompaLoompaPage>() {
      @Override public void onNext(OompaLoompaPage oompaLoompaPage) {
        super.onNext(oompaLoompaPage);
        List<OompaLoompa> oompaLoompas = oompaLoompaPage.getOompaLoompas();
        OompaLoompasState storyState = storyController.getStoryState();
        storyState.addOompaLoompas(oompaLoompas);
        storyState.setPage(oompaLoompaPage.getPageNumber());
        storyState.setTotalPages(oompaLoompaPage.getTotalPages());
        view.setItems(oompaLoompas);
      }

      @Override protected void onError(String errorMessage) {
        List<OompaLoompa> oompaLoompas = storyController.getStoryState().getOompaLoompas();
        if (oompaLoompas == null || oompaLoompas.isEmpty()) {
          view.showErrorMessage();
        }
      }
    });
  }

  @Override public void stop() {
    getOompaLoompasUseCase.unsubcribe();
    getOompaLoompasByQueryUseCase.unsubcribe();
  }

  public void onBottomReached() {
    if (canRequestMoreOompas() && isNotFiltering()) {
      getOompaLoompas(storyController.getStoryState().getPage() + 1);
    }
  }

  @VisibleForTesting public boolean canRequestMoreOompas() {
    OompaLoompasState storyState = storyController.getStoryState();
    return storyState.getPage() < storyState.getTotalPages();
  }

  public void onOompaSelected(OompaLoompa oompaLoompa, ImageView image) {
    storyController.getStoryState().setSelectedOompa(oompaLoompa);
    storyController.navigateToDetail(image);
  }

  public void onQueryTextChanged(String query) {
    getOompaLoompasByQueryUseCase.execute(query, new DefaultSubscriber<List<OompaLoompa>>() {
      @Override public void onNext(List<OompaLoompa> oompaLoompas) {
        super.onNext(oompaLoompas);
        view.setNewItems(oompaLoompas);
      }

      @Override protected void onError(String errorMessage) {
        view.showNoResults();
      }
    });
  }

  public void onMaleChecked() {
    maleFilter = !maleFilter;
    updateList();
  }

  public void onFemaleChecked() {
    femaleFilter = !femaleFilter;
    updateList();
  }

  private void updateList() {
    boolean shouldSearch = femaleFilter != maleFilter;
    if (!shouldSearch) {
      view.setNewItems(storyController.getStoryState().getOompaLoompas());
    } else {
      String gender = femaleFilter ? OompaLoompa.FEMALE : OompaLoompa.MALE;
      getOompaLoompasByGenderUseCase.execute(gender, new DefaultSubscriber<List<OompaLoompa>>() {
        @Override public void onNext(List<OompaLoompa> oompaLoompas) {
          super.onNext(oompaLoompas);
          view.setNewItems(oompaLoompas);
        }

        @Override protected void onError(String errorMessage) {
          //Do nothing here
        }
      });
    }
  }

  private boolean isNotFiltering() {
    return femaleFilter == maleFilter;
  }
}
