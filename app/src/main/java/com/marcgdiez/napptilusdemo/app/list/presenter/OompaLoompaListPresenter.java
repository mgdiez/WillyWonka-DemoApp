package com.marcgdiez.napptilusdemo.app.list.presenter;

import android.support.annotation.VisibleForTesting;
import android.widget.ImageView;
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

  @Inject public OompaLoompaListPresenter(Interactor<OompaLoompaPage> getOompaLoompasUseCase,
      OompaLoompasStoryController storyController) {

    this.getOompaLoompasUseCase = (GetOompasLoompasUseCase) getOompaLoompasUseCase;
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
          view.showErrorMessage(errorMessage);
        }
      }
    });
  }

  @Override public void stop() {
    getOompaLoompasUseCase.unsubcribe();
  }

  public void onBottomReached() {
    if (canRequestMoreOompas()) {
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
}
