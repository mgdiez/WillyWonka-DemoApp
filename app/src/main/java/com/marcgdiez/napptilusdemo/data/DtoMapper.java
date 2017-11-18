package com.marcgdiez.napptilusdemo.data;

import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaListResponseDto;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
import javax.inject.Inject;

public class DtoMapper {

  @Inject public DtoMapper() {
  }

  public List<OompaLoompa> mapOompaLoompaListResponse(
      OompaLoompaListResponseDto oompaLoompaListResponseDto) {
    return null;
  }
}
