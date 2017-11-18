package com.marcgdiez.napptilusdemo.app.list.story;

import android.os.Parcel;
import com.marcgdiez.napptilusdemo.core.story.StoryState;

public class OompaLoompasState implements StoryState {
  @Override public boolean isValid() {
    return false;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
  }

  public OompaLoompasState() {
  }

  protected OompaLoompasState(Parcel in) {
  }

  public static final Creator<OompaLoompasState> CREATOR = new Creator<OompaLoompasState>() {
    @Override public OompaLoompasState createFromParcel(Parcel source) {
      return new OompaLoompasState(source);
    }

    @Override public OompaLoompasState[] newArray(int size) {
      return new OompaLoompasState[size];
    }
  };
}
