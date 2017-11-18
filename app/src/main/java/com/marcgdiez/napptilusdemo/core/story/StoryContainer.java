package com.marcgdiez.napptilusdemo.core.story;

import android.support.annotation.IdRes;

public interface StoryContainer {

  @IdRes int getContainerId();

  void close();
}
