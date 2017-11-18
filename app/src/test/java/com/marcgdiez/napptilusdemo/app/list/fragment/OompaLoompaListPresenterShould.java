package com.marcgdiez.napptilusdemo.app.list.fragment;

import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class OompaLoompaListPresenterShould {

  @Mock GetOompasLoompasUseCase mockGetOompasLoompasUseCase;
  @Mock OompaLoompasStoryController mockOompaLoompasStoryController;
  private OompaLoompaListPresenter oompaLoompaListPresenter;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);

    oompaLoompaListPresenter =
        new OompaLoompaListPresenter(mockGetOompasLoompasUseCase, mockOompaLoompasStoryController);
  }

  @Test public void on_stop_unsubscribe_every_use_case() {
    oompaLoompaListPresenter.stop();

    verify(mockGetOompasLoompasUseCase).unsubcribe();
  }
}