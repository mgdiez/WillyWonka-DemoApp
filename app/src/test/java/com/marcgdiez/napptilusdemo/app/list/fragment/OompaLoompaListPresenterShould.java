package com.marcgdiez.napptilusdemo.app.list.fragment;

import com.marcgdiez.napptilusdemo.app.list.fragment.view.OompaLoompaListView;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasState;
import com.marcgdiez.napptilusdemo.app.list.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.app.list.usecase.GetOompasLoompasUseCase;
import com.marcgdiez.napptilusdemo.core.subscriber.DefaultSubscriber;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Subscriber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OompaLoompaListPresenterShould {

  @Mock GetOompasLoompasUseCase mockGetOompasLoompasUseCase;
  @Mock OompaLoompasStoryController mockStoryController;
  @Mock OompaLoompaListView mockOompaLoompaListView;
  private OompaLoompaListPresenter presenter;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);

    presenter = new OompaLoompaListPresenter(mockGetOompasLoompasUseCase, mockStoryController);

    presenter.setView(mockOompaLoompaListView);
  }

  @Test public void unsubscribe_every_use_case_on_stop() {
    presenter.stop();
    verify(mockGetOompasLoompasUseCase).unsubcribe();
  }

  @Test public void check_story_state_on_initialize() {
    presenter.start();
    verify(mockStoryController).hasState();
  }

  @Test public void create_new_state_on_initialize_if_not_exist() {
    when(mockStoryController.hasState()).thenReturn(false);
    when(mockStoryController.getStoryState()).thenReturn(mock(OompaLoompasState.class));

    presenter.start();

    verify(mockStoryController).setStoryState(any());
  }

  @Test public void show_data_on_initialize_if_has_state() {
    when(mockStoryController.hasState()).thenReturn(true);
    when(mockStoryController.getStoryState()).thenReturn(mock(OompaLoompasState.class));

    presenter.start();

    verify(mockOompaLoompaListView).setItems(anyListOf(OompaLoompa.class));
  }

  @Test public void return_true_when_more_data_is_available() {
    OompaLoompasState oompaLoompasState = new OompaLoompasState();
    oompaLoompasState.setPage(1);
    oompaLoompasState.setTotalPages(4);

    when(mockStoryController.getStoryState()).thenReturn(oompaLoompasState);

    assertTrue(presenter.canRequestMoreOompas());
  }

  @Test public void return_false_when_no_data_is_available() {
    OompaLoompasState oompaLoompasState = new OompaLoompasState();
    oompaLoompasState.setPage(4);
    oompaLoompasState.setTotalPages(4);

    when(mockStoryController.getStoryState()).thenReturn(oompaLoompasState);

    assertFalse(presenter.canRequestMoreOompas());
  }

  @Test public void get_more_data_on_bottom_reached() {

    OompaLoompasState oompaLoompasState = new OompaLoompasState();
    oompaLoompasState.setPage(1);
    oompaLoompasState.setTotalPages(4);

    when(mockStoryController.getStoryState()).thenReturn(oompaLoompasState);

    presenter.onBottomReached();

    verify(mockGetOompasLoompasUseCase).execute(anyInt(), any(DefaultSubscriber.class));
  }

  @Test public void show_error_feedback_if_needed() {
    when(mockStoryController.hasState()).thenReturn(false);
    when(mockStoryController.getStoryState()).thenReturn(new OompaLoompasState());

    doAnswer(invocation -> {
      ((Subscriber) invocation.getArguments()[1]).onError(new Throwable("errorMessage"));
      return null;
    }).when(mockGetOompasLoompasUseCase).execute(anyInt(), any(Subscriber.class));

    presenter.start();

    verify(mockOompaLoompaListView).showErrorMessage("errorMessage");
  }

  @Test public void show_data_onNext() {
    when(mockStoryController.hasState()).thenReturn(false);
    when(mockStoryController.getStoryState()).thenReturn(new OompaLoompasState());

    doAnswer(invocation -> {
      ((Subscriber) invocation.getArguments()[1]).onNext(
          new OompaLoompaPage(1, 1, new ArrayList<>()));
      return null;
    }).when(mockGetOompasLoompasUseCase).execute(anyInt(), any(Subscriber.class));

    presenter.start();

    verify(mockOompaLoompaListView).setItems(any());
  }
}