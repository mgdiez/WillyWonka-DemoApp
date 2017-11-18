package com.marcgdiez.napptilusdemo.app.list.fragment.view;

import com.marcgdiez.napptilusdemo.core.view.IView;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;

public interface OompaLoompaListView extends IView {

  void setItems(List<OompaLoompa> oompaLoompas);

  void showProgress();

  void hideProgress();

  void showErrorMessage(String errorMessage);
}
