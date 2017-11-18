package com.marcgdiez.napptilusdemo.app.list.fragment;

import com.marcgdiez.napptilusdemo.app.list.fragment.view.OompaLoompaListView;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import javax.inject.Inject;

public class OompaLoompaListPresenter extends Presenter<OompaLoompaListView> {

  private final OompaLoompasStoryController storyController;
  private final GetOompasLoompasUseCase getOompaLoompasUseCase;

  @Inject public OompaLoompaListPresenter(Interactor<List<OompaLoompa>> getOompaLoompasUseCase,
      OompaLoompasStoryController storyController) {

    this.getOompaLoompasUseCase = (GetOompasLoompasUseCase) getOompaLoompasUseCase;
    this.storyController = storyController;
  }

  @Override protected void initialize() {

  }

  @Override public void stop() {
    getOompaLoompasUseCase.unsubcribe();
  }
}
