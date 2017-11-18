package com.marcgdiez.napptilusdemo.core.story;

import android.os.Parcelable;

public interface StoryState extends Parcelable {

  boolean isValid();
}
