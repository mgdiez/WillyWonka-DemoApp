package com.marcgdiez.napptilusdemo.data;

import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaDto;
import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaListResponseDto;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DtoMapper {

  @Inject public DtoMapper() {
  }

  public OompaLoompaPage mapOompaLoompaListResponse(
      OompaLoompaListResponseDto oompaLoompaListResponseDto) {

    OompaLoompaPage oompaLoompaPage = null;

    if (oompaLoompaListResponseDto != null) {
      List<OompaLoompaDto> results = oompaLoompaListResponseDto.getResults();
      List<OompaLoompa> oompaLoompas = new ArrayList<>();

      if (results != null && !results.isEmpty()) {
        for (OompaLoompaDto dto : results) {
          OompaLoompa oompaLoompa =
              new OompaLoompa(dto.getId(), dto.getFirst_name() + " " + dto.getLast_name(),
                  dto.getGender(), dto.getImage(), dto.getProfession(), dto.getEmail(),
                  dto.getDescription());
          oompaLoompas.add(oompaLoompa);
        }
      }
      oompaLoompaPage = new OompaLoompaPage(oompaLoompaListResponseDto.getCurrent(),
          oompaLoompaListResponseDto.getTotal(), oompaLoompas);
    }
    return oompaLoompaPage;
  }

  public OompaLoompa mapOompaLoompaResponse(OompaLoompaDto oompaLoompaDto) {
    OompaLoompa oompaLoompa = null;
    if (oompaLoompaDto != null) {
      oompaLoompa = new OompaLoompa(oompaLoompaDto.getId(),
          oompaLoompaDto.getFirst_name() + " " + oompaLoompaDto.getLast_name(),
          oompaLoompaDto.getGender(), oompaLoompaDto.getImage(), oompaLoompaDto.getProfession(),
          oompaLoompaDto.getEmail(), oompaLoompaDto.getDescription());
    }
    return oompaLoompa;
  }
}
