package com.marcgdiez.napptilusdemo.entity;

import java.util.List;

public class OompaLoompaPage {
  private final int pageNumber;
  private final int totalPages;
  private final List<OompaLoompa> oompaLoompas;

  public OompaLoompaPage(int pageNumber, int totalPages, List<OompaLoompa> oompaLoompas) {
    this.pageNumber = pageNumber;
    this.totalPages = totalPages;
    this.oompaLoompas = oompaLoompas;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public List<OompaLoompa> getOompaLoompas() {
    return oompaLoompas;
  }
}
