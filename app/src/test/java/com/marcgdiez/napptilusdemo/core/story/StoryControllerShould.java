package com.marcgdiez.napptilusdemo.core.story;

import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StoryControllerShould {

  @Mock AppCompatActivity mockAppCompatActivity;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);

    ApplicationInfo fakeApplicationInfo = new ApplicationInfo();
    fakeApplicationInfo.flags = 13155910;

    when(mockAppCompatActivity.getApplicationInfo()).thenReturn(fakeApplicationInfo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_on_constructor_with_invalid_parameters() {
    new StoryController((AppCompatActivity) null, null);
  }

  @Test public void not_crash_with_unexpected_outstate_value() {
    when(mockAppCompatActivity.getSupportFragmentManager()).thenReturn(null);
    StoryContainer mockStoryContainer = mock(StoryContainer.class);

    StoryController fakeStoryController =
        new StoryController(mockAppCompatActivity, mockStoryContainer);

    fakeStoryController.saveState(null);
  }

  @Test public void not_crash_with_unexpected_savedstate_value() {
    when(mockAppCompatActivity.getSupportFragmentManager()).thenReturn(null);
    StoryContainer mockStoryContainer = mock(StoryContainer.class);

    StoryController fakeStoryController =
        new StoryController(mockAppCompatActivity, mockStoryContainer);

    fakeStoryController.restoreState(null);
  }

  @Test public void retain_a_valid_state() {
    StoryState mockStoryState = mock(StoryState.class);
    when(mockStoryState.isValid()).thenReturn(true);

    when(mockAppCompatActivity.getSupportFragmentManager()).thenReturn(null);
    StoryContainer mockStoryContainer = mock(StoryContainer.class);

    StoryController fakeStoryController =
        new StoryController(mockAppCompatActivity, mockStoryContainer);
    fakeStoryController.setStoryState(mockStoryState);

    assertThat(fakeStoryController.hasState(), is(true));
  }

  @Test public void without_state_does_not_have_valid_state() {
    StoryState mockStoryState = mock(StoryState.class);
    when(mockStoryState.isValid()).thenReturn(true);

    when(mockAppCompatActivity.getSupportFragmentManager()).thenReturn(null);
    StoryContainer mockStoryContainer = mock(StoryContainer.class);

    StoryController fakeStoryController =
        new StoryController(mockAppCompatActivity, mockStoryContainer);

    assertThat(fakeStoryController.hasState(), is(false));
  }
}