package com.marcgdiez.napptilusdemo.core.story;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class StoryController<S extends StoryState> extends BaseStoryController<S> {

  public StoryController(AppCompatActivity appCompatActivity, StoryContainer storyContainer) {
    super(appCompatActivity, storyContainer);
  }
}