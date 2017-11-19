package com.marcgdiez.napptilusdemo.app.di.module;

import android.support.v7.app.AppCompatActivity;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.di.PerActivity;
import com.marcgdiez.napptilusdemo.core.story.StoryContainer;
import dagger.Module;
import dagger.Provides;

@Module public class OompaLoompasModule {
  private final StoryContainer storyContainer;

  public OompaLoompasModule(StoryContainer storyContainer) {
    this.storyContainer = storyContainer;
  }

  @Provides @PerActivity public StoryContainer provideStoryContainer() {
    return storyContainer;
  }

  @Provides @PerActivity OompaLoompasStoryController provideStoryController() {
    return new OompaLoompasStoryController(((AppCompatActivity) storyContainer), storyContainer);
  }
}


