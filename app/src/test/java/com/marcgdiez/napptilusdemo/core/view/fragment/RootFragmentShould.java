package com.marcgdiez.napptilusdemo.core.view.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.view.IView;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class RootFragmentShould {

  @Test public void call_presenter_stop_on_fragment_stop() {
    Presenter mockPresenter = Mockito.mock(Presenter.class);
    RootFragment fragment = Mockito.mock(RootFragment.class, Mockito.CALLS_REAL_METHODS);
    doReturn(mockPresenter).when(fragment).getPresenter();

    fragment.onStop();

    verify(fragment).getPresenter();
    verify(mockPresenter).stop();
  }

  @Test public void call_presenter_start_on_view_resumed() {
    IView mockView = Mockito.mock(IView.class);
    Presenter spyPresenter = Mockito.spy(new Presenter() {
      @Override protected void initialize() {

      }

      @Override public void stop() {

      }
    });
    spyPresenter.setView(mockView);

    RootFragment fragment = Mockito.mock(RootFragment.class, Mockito.CALLS_REAL_METHODS);
    doReturn(spyPresenter).when(fragment).getPresenter();
    doNothing().when(fragment).initializePresenter();

    fragment.onResume();

    verify(fragment).getPresenter();
    verify(spyPresenter).start();
  }

  @Test public void resuming_view_do_not_throw_exception_on_null_presenter() {
    RootFragment fragment = Mockito.mock(RootFragment.class, Mockito.CALLS_REAL_METHODS);
    doReturn(null).when(fragment).getPresenter();
    doNothing().when(fragment).initializePresenter();

    fragment.onResume();

    verify(fragment).getPresenter();
  }

  @Test public void stopping_view_do_not_throw_exception_on_null_presenter() {
    RootFragment fragment = Mockito.mock(RootFragment.class, Mockito.CALLS_REAL_METHODS);
    doReturn(null).when(fragment).getPresenter();
    doNothing().when(fragment).initializePresenter();

    fragment.onStop();

    verify(fragment).getPresenter();
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_on_invalid_layout_resource_creating_fragment_view() {
    RootFragment fragment = Mockito.mock(RootFragment.class, Mockito.CALLS_REAL_METHODS);
    doReturn(-1).when(fragment).getFragmentLayoutResourceId();
    doReturn(null).when(fragment).getPresenter();
    doNothing().when(fragment).initializePresenter();

    fragment.onCreateView(null, null, null);
  }

  @Test public void call_fragment_layout_resource_creating_fragment_view() {
    RootFragment fragment = Mockito.mock(RootFragment.class, Mockito.CALLS_REAL_METHODS);
    doReturn(1).when(fragment).getFragmentLayoutResourceId();
    doReturn(null).when(fragment).getPresenter();
    doNothing().when(fragment).initializePresenter();

    LayoutInflater mockLayoutInflater = Mockito.mock(LayoutInflater.class);
    ViewGroup mockViewGroup = Mockito.mock(ViewGroup.class);

    fragment.onCreateView(mockLayoutInflater, mockViewGroup, null);

    verify(mockLayoutInflater).inflate(1, mockViewGroup, false);
  }
}