package com.marcgdiez.napptilusdemo.core.story;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Marc on 18/11/2017.
 */

public abstract class BaseStoryController<S extends StoryState> {

  @VisibleForTesting public static final String STORY_STATE_KEY = "STORY_STATE_KEY";

  protected FragmentManager fragmentManager;
  protected StoryContainer storyContainer;
  protected S storyState;

  public BaseStoryController(AppCompatActivity appCompatActivity, StoryContainer storyContainer) {
    if (appCompatActivity == null || storyContainer == null) {
      throw new IllegalArgumentException("StoryController must have valid parameters.");
    }
    this.storyContainer = storyContainer;
    fragmentManager = appCompatActivity.getSupportFragmentManager();
  }

  public void cleanStoryContainer() {
    storyContainer = null;
  }

  public void saveState(Bundle outState) {
    if (outState != null) {
      outState.putParcelable(STORY_STATE_KEY, storyState);
    }
  }

  public void restoreState(Bundle savedState) {
    if (savedState != null && savedState.containsKey(STORY_STATE_KEY)) {
      storyState = savedState.getParcelable(STORY_STATE_KEY);
    }
  }

  public boolean hasState() {
    return storyState != null && storyState.isValid();
  }

  public S getStoryState() {
    return storyState;
  }

  public void setStoryState(S storyState) {
    this.storyState = storyState;
  }

  protected void addFragment(@IdRes int containerId, @NonNull Fragment fragment) {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(containerId, fragment);
    fragmentTransaction.commit();
  }

  protected void addFragment(FragmentTransaction fragmentTransaction, @IdRes int containerId,
      @NonNull Fragment fragment) {
    fragmentTransaction.add(containerId, fragment);
    fragmentTransaction.commit();
  }

  protected void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment) {
    String fragmentName = fragment.getClass().getSimpleName();

    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(containerId, fragment, fragmentName);
    fragmentTransaction.addToBackStack(fragmentName);
    fragmentTransaction.commit();
  }

  protected boolean popBackStackTo(String name) {
    return fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
  }

  protected void clearBackStack() {
    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
  }

  protected FragmentTransaction removeCurrentFragment() {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment currentFragment = fragmentManager.findFragmentById(storyContainer.getContainerId());
    if (currentFragment != null) {
      fragmentTransaction.remove(currentFragment);
    }
    return fragmentTransaction;
  }

  protected void addOrPopFragment(@IdRes int containerId, @NonNull Fragment fragment) {
    String fragmentName = fragment.getClass().getSimpleName();
    if (!popBackStackTo(fragmentName)) {
      addFragment(containerId, fragment);
    }
  }
}