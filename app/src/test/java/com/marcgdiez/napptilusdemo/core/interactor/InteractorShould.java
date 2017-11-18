package com.marcgdiez.napptilusdemo.core.interactor;

import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.core.executor.ThreadExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InteractorShould {

  private Interactor interactor;

  @Mock private ThreadExecutor threadExecutor;
  @Mock private MainThread mainThread;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);

    interactor = new Interactor(threadExecutor, mainThread) {
      @Override protected Observable buildObservable() {
        return Observable.empty();
      }
    };
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_in_constructor_not_valid_parameters() {
    new Interactor(null, null) {
      @Override protected Observable buildObservable() {
        return Observable.empty();
      }
    };
  }

  @Test public void create_subscription_correctly() {
    TestSubscriber<Object> testSubscriber = TestSubscriber.create();
    interactor.execute(testSubscriber);

    verify(mainThread).getScheduler();
  }

  @Test public void testInteractorShouldUnsubscribeCorrectly() {
    TestSubscriber<Object> testSubscriber = TestSubscriber.create();
    interactor.execute(testSubscriber);
    interactor.unsubcribe();

    assertTrue(testSubscriber.isUnsubscribed());
  }

  @Test public void build_interactor_observable_on_execution() {
    Interactor spyInteractor = spy(interactor);

    when(spyInteractor.buildObservable()).thenReturn(Observable.empty());
    Subscriber mockSubscriber = TestSubscriber.create();

    spyInteractor.execute(mockSubscriber);

    verify(spyInteractor).buildObservable();
  }
}