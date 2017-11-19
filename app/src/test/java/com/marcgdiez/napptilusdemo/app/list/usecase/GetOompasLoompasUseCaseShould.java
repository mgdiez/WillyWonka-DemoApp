package com.marcgdiez.napptilusdemo.app.list.usecase;

import com.marcgdiez.napptilusdemo.app.list.exception.OompaLoompasNotFoundException;
import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
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

public class GetOompasLoompasUseCaseShould {

  @Rule public ImmediateSchedulersTestRule testRule = new ImmediateSchedulersTestRule();
  @Mock OompaLoompaRepository mockOompaLoompaRepository;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_parameters() {
    new GetOompasLoompasUseCase(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_repository() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();

    new GetOompasLoompasUseCase(executor, mainThread, null);
  }

  @Test public void should_return_error_if_oompas_are_not_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<OompaLoompaPage> testSubscriber = TestSubscriber.create();

    when(mockOompaLoompaRepository.getOompaLoompas(anyInt())).thenReturn(Observable.empty());

    GetOompasLoompasUseCase useCase =
        new GetOompasLoompasUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute(1, testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertError(OompaLoompasNotFoundException.class);
  }

  @Test public void should_return_data_if_oompas_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<OompaLoompaPage> testSubscriber = TestSubscriber.create();
    OompaLoompaPage mockOompaLoompaPage = mock(OompaLoompaPage.class);

    when(mockOompaLoompaRepository.getOompaLoompas(1)).thenReturn(
        Observable.just(mockOompaLoompaPage));

    GetOompasLoompasUseCase useCase =
        new GetOompasLoompasUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute(1, testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValue(mockOompaLoompaPage);
  }
}