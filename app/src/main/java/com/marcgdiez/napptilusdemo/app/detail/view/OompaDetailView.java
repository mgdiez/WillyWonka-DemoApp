package com.marcgdiez.napptilusdemo.app.detail.view;

import com.marcgdiez.napptilusdemo.core.view.IView;

public interface OompaDetailView extends IView{
  void showOompa(int id, String name, String profession, String gender, String email,
      String description, String image);
}
