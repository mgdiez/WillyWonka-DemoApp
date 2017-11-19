package com.marcgdiez.napptilusdemo.app.detail.usecase;

import com.marcgdiez.napptilusdemo.app.list.exception.OompaLoompasNotFoundException;
import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.rule.ImmediateSchedulersTestRule;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetOompaDetailUseCaseShould {
  @Rule public ImmediateSchedulersTestRule testRule = new ImmediateSchedulersTestRule();
  @Mock OompaLoompaRepository mockOompaLoompaRepository;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_parameters() {
    new GetOompaDetailUseCase(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_repository() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();

    new GetOompaDetailUseCase(executor, mainThread, null);
  }

  @Test public void should_return_error_if_oompa_is_not_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<OompaLoompa> testSubscriber = TestSubscriber.create();

    when(mockOompaLoompaRepository.getOompaLoompa(anyInt())).thenReturn(Observable.empty());

    GetOompaDetailUseCase useCase =
        new GetOompaDetailUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute(1, testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertError(OompaLoompasNotFoundException.class);
  }

  @Test public void should_return_data_if_oompa_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<OompaLoompa> testSubscriber = TestSubscriber.create();
    OompaLoompa mockOompaLoompa = mock(OompaLoompa.class);

    when(mockOompaLoompaRepository.getOompaLoompa(1)).thenReturn(Observable.just(mockOompaLoompa));

    GetOompaDetailUseCase useCase =
        new GetOompaDetailUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute(1, testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValue(mockOompaLoompa);
  }
}