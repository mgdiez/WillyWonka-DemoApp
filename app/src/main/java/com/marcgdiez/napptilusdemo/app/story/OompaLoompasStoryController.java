package com.marcgdiez.napptilusdemo.app.story;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.marcgdiez.napptilusdemo.app.detail.fragment.OompaLoompaDetailFragment;
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

  public void navigateToDetail(ImageView image) {
    replaceFragmentWithSharedImage(image, storyContainer.getContainerId(),
        OompaLoompaDetailFragment.newInstance(ViewCompat.getTransitionName(image)));
  }
}
