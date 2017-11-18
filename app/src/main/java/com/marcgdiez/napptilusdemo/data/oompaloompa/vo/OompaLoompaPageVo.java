package com.marcgdiez.napptilusdemo.data.oompaloompa.vo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass public class OompaLoompaPageVo extends RealmObject {
  public static final String PRIMARY_KEY = "pageNumber";

  @PrimaryKey private int pageNumber;
  private int totalPages;
  private RealmList<OompaLoompaVo> oompaLoompas;

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public RealmList<OompaLoompaVo> getOompaLoompas() {
    return oompaLoompas;
  }

  public void setOompaLoompas(RealmList<OompaLoompaVo> oompaLoompas) {
    this.oompaLoompas = oompaLoompas;
  }
}
