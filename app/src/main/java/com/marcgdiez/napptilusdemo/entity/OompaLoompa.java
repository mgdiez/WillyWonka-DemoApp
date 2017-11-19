package com.marcgdiez.napptilusdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OompaLoompa implements Parcelable {
  public static final String MALE = "M";
  public static final String FEMALE = "F";

  private int id;
  private final String name;
  private final String gender;
  private final String image;
  private final String profession;
  private final String email;
  private final String description;

  public OompaLoompa(int id, String name, String gender, String image, String profession,
      String email, String description) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.image = image;
    this.profession = profession;
    this.email = email;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getImage() {
    return image;
  }

  public String getProfession() {
    return profession;
  }

  public String getEmail() {
    return email;
  }

  public String getDescription() {
    return description;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.name);
    dest.writeString(this.gender);
    dest.writeString(this.image);
    dest.writeString(this.profession);
    dest.writeString(this.email);
    dest.writeString(this.description);
  }

  protected OompaLoompa(Parcel in) {
    this.id = in.readInt();
    this.name = in.readString();
    this.gender = in.readString();
    this.image = in.readString();
    this.profession = in.readString();
    this.email = in.readString();
    this.description = in.readString();
  }

  public static final Creator<OompaLoompa> CREATOR = new Creator<OompaLoompa>() {
    @Override public OompaLoompa createFromParcel(Parcel source) {
      return new OompaLoompa(source);
    }

    @Override public OompaLoompa[] newArray(int size) {
      return new OompaLoompa[size];
    }
  };
}
