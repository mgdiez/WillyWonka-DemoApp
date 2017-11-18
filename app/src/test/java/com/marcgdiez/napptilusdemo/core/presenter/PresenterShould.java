package com.marcgdiez.napptilusdemo.core.presenter;

import com.marcgdiez.napptilusdemo.core.view.IView;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class PresenterShould {

  @Test(expected = IllegalArgumentException.class) public void throw_exception_on_null_view() {
    Presenter presenter = Mockito.mock(Presenter.class, Mockito.CALLS_REAL_METHODS);
    presenter.setView(null);

    presenter.start();
  }

  @Test public void start_correctly_with_valid_mock_presenter() {
    IView view = Mockito.mock(IView.class);
    Presenter presenter = Mockito.mock(Presenter.class, Mockito.CALLS_REAL_METHODS);
    doNothing().when(presenter).initialize();

    presenter.setView(view);
    presenter.start();

    verify(presenter).initialize();
  }

  @Test public void start_correctly_with_valid_presenter() {
    IView view = Mockito.mock(IView.class);
    Presenter presenter = new Presenter() {
      @Override public void start() {
        super.start();
      }

      @Override protected void initialize() {

      }

      @Override public void stop() {

      }
    };

    Presenter spy = Mockito.spy(presenter);

    spy.setView(view);
    spy.start();

    verify(spy).initialize();
  }
}