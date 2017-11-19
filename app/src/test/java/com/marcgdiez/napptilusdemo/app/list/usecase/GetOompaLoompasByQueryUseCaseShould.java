package com.marcgdiez.napptilusdemo.app.list.usecase;

import com.marcgdiez.napptilusdemo.app.detail.usecase.GetOompaDetailUseCase;
import com.marcgdiez.napptilusdemo.app.list.exception.OompaLoompasNotFoundException;
import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.rule.ImmediateSchedulersTestRule;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GetOompaLoompasByQueryUseCaseShould {

  @Rule public ImmediateSchedulersTestRule testRule = new ImmediateSchedulersTestRule();
  @Mock OompaLoompaRepository mockOompaLoompaRepository;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_parameters() {
    new GetOompaLoompasByQueryUseCase(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_repository() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();

    new GetOompaDetailUseCase(executor, mainThread, null);
  }

  @Test public void should_return_error_if_oompas_are_not_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<List<OompaLoompa>> testSubscriber = TestSubscriber.create();

    when(mockOompaLoompaRepository.getOompaLoompasByQuery(anyString())).thenReturn(
        Observable.empty());

    GetOompaLoompasByQueryUseCase useCase =
        new GetOompaLoompasByQueryUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute("test!", testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertError(OompaLoompasNotFoundException.class);
  }

  @Test public void should_return_data_if_oompas_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<List<OompaLoompa>> testSubscriber = TestSubscriber.create();
    List<OompaLoompa> mockOompaLoompaList = new ArrayList<>();

    when(mockOompaLoompaRepository.getOompaLoompasByQuery("test")).thenReturn(
        Observable.just(mockOompaLoompaList));

    GetOompaLoompasByQueryUseCase useCase =
        new GetOompaLoompasByQueryUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute("test", testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValue(mockOompaLoompaList);
  }
}