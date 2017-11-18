package com.marcgdiez.napptilusdemo.app.list.story;

import android.os.Parcel;
import com.marcgdiez.napptilusdemo.core.story.StoryState;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.ArrayList;
import java.util.List;

public class OompaLoompasState implements StoryState {
  private List<OompaLoompa> oompaLoompas;
  private int page;
  private int totalPages;

  @Override public boolean isValid() {
    return oompaLoompas != null && !oompaLoompas.isEmpty();
  }

  public OompaLoompasState() {
    page = 1;
  }

  public List<OompaLoompa> getOompaLoompas() {
    return oompaLoompas;
  }

  public void setOompaLoompas(List<OompaLoompa> oompaLoompas) {
    this.oompaLoompas = oompaLoompas;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public void addOompaLoompas(List<OompaLoompa> oompaLoompas) {
    if (this.oompaLoompas == null) {
      this.oompaLoompas = new ArrayList<>();
    }
    this.oompaLoompas.addAll(oompaLoompas);
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getTotalPages() {
    return totalPages;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeList(this.oompaLoompas);
    dest.writeInt(this.page);
    dest.writeInt(this.totalPages);
  }

  protected OompaLoompasState(Parcel in) {
    this.oompaLoompas = new ArrayList<OompaLoompa>();
    in.readList(this.oompaLoompas, OompaLoompa.class.getClassLoader());
    this.page = in.readInt();
    this.totalPages = in.readInt();
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
