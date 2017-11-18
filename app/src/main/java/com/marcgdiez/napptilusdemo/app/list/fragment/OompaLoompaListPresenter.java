package com.marcgdiez.napptilusdemo.app.list.fragment;

import com.marcgdiez.napptilusdemo.app.list.fragment.view.OompaLoompaListView;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.subscriber.DefaultSubscriber;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
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
    getOompaLoompasUseCase.execute(1, new DefaultSubscriber<OompaLoompaPage>() {
      @Override protected void onError(String errorMessage) {

      }
    });
  }

  @Override public void stop() {
    getOompaLoompasUseCase.unsubcribe();
  }
}
