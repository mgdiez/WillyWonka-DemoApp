package com.marcgdiez.napptilusdemo.app.list.usecase;

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

public class GetOompaLoompasByGenderUseCaseShould {
  @Rule public ImmediateSchedulersTestRule testRule = new ImmediateSchedulersTestRule();
  @Mock OompaLoompaRepository mockOompaLoompaRepository;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_parameters() {
    new GetOompaLoompasByGenderUseCase(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_with_invalid_repository() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();

    new GetOompaLoompasByGenderUseCase(executor, mainThread, null);
  }

  @Test public void should_return_error_if_oompa_is_not_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<List<OompaLoompa>> testSubscriber = TestSubscriber.create();

    when(mockOompaLoompaRepository.getOompaLoompasByGender(anyString())).thenReturn(
        Observable.empty());

    GetOompaLoompasByGenderUseCase useCase =
        new GetOompaLoompasByGenderUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute("", testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertError(OompaLoompasNotFoundException.class);
  }

  @Test public void should_return_data_if_oompa_found() {
    Executor executor = Executors.newSingleThreadExecutor();
    MainThread mainThread = new MainThread();
    TestSubscriber<List<OompaLoompa>> testSubscriber = TestSubscriber.create();
    List<OompaLoompa> mockOompaLoompa = new ArrayList<>();

    when(mockOompaLoompaRepository.getOompaLoompasByGender("M")).thenReturn(
        Observable.just(mockOompaLoompa));

    GetOompaLoompasByGenderUseCase useCase =
        new GetOompaLoompasByGenderUseCase(executor, mainThread, mockOompaLoompaRepository);

    useCase.execute("M", testSubscriber);

    testSubscriber.awaitTerminalEvent();

    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    testSubscriber.assertValue(mockOompaLoompa);
  }
}