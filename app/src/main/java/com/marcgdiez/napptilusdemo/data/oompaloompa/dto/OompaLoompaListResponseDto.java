package com.marcgdiez.napptilusdemo.data.oompaloompa.dto;

import java.util.List;

public class OompaLoompaListResponseDto {
  private int current;
  private int total;
  private List<OompaLoompaDto> results;

  public int getCurrent() {
    return current;
  }

  public void setCurrent(int current) {
    this.current = current;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<OompaLoompaDto> getResults() {
    return results;
  }

  public void setResults(List<OompaLoompaDto> results) {
    this.results = results;
  }
}