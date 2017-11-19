package com.marcgdiez.napptilusdemo.app.story;

import android.support.v7.app.AppCompatActivity;
import com.marcgdiez.napptilusdemo.app.list.fragment.OompaLoompaListFragment;
import com.marcgdiez.napptilusdemo.core.story.StoryContainer;
import com.marcgdiez.napptilusdemo.core.story.StoryController;

public class OompaLoompasStoryController extends StoryController<OompaLoompasState> {
  public OompaLoompasStoryController(AppCompatActivity appCompatActivity,
      StoryContainer storyContainer) {
    super(appCompatActivity, storyContainer);
  }

  public void navigateToList() {
    addFragment(storyContainer.getContainerId(), OompaLoompaListFragment.newInstance());
  }
}
