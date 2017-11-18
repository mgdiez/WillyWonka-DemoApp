package com.marcgdiez.napptilusdemo.core.presenter;

import com.marcgdiez.napptilusdemo.core.view.IView;

public abstract class Presenter<V extends IView> {

  protected V view;

  public void start() {
    if (view == null) {
      throw new IllegalArgumentException("Presenter's view can not be null.");
    }

    initialize();
  }

  protected abstract void initialize();

  public abstract void stop();

  public void setView(V view) {
    this.view = view;
  }
}