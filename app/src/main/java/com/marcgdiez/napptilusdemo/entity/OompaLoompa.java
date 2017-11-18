package com.marcgdiez.napptilusdemo.entity;

public class OompaLoompa {
  private int id;
  private final String name;
  private final String gender;
  private final String image;
  private final String profession;
  private final String email;

  public OompaLoompa(int id, String name, String gender, String image, String profession,
      String email) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.image = image;
    this.profession = profession;
    this.email = email;
  }

  public int getId() {
    return id;
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
}
