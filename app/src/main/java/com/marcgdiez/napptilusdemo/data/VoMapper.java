package com.marcgdiez.napptilusdemo.data;

import com.marcgdiez.napptilusdemo.data.oompaloompa.vo.OompaLoompaPageVo;
import com.marcgdiez.napptilusdemo.data.oompaloompa.vo.OompaLoompaVo;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class VoMapper {

  @Inject public VoMapper() {
  }

  public OompaLoompaPageVo toValue(OompaLoompaPage response) {
    OompaLoompaPageVo vo = new OompaLoompaPageVo();
    if (response != null) {
      vo.setPageNumber(response.getPageNumber());
      vo.setTotalPages(response.getTotalPages());

      RealmList<OompaLoompaVo> oompaLoompaVos = new RealmList<>();
      for (OompaLoompa oompaLoompa : response.getOompaLoompas()) {
        OompaLoompaVo oompaLoompaVo = new OompaLoompaVo();
        oompaLoompaVo.setId(oompaLoompa.getId());
        oompaLoompaVo.setName(oompaLoompa.getName());
        oompaLoompaVo.setEmail(oompaLoompa.getEmail());
        oompaLoompaVo.setGender(oompaLoompa.getGender());
        oompaLoompaVo.setImage(oompaLoompa.getImage());
        oompaLoompaVo.setProfession(oompaLoompa.getProfession());

        oompaLoompaVos.add(oompaLoompaVo);
      }

      vo.setOompaLoompas(oompaLoompaVos);
    }
    return vo;
  }

  public OompaLoompaPage toEntity(OompaLoompaPageVo oompaLoompaPageVo) {
    OompaLoompaPage entity = null;
    if (oompaLoompaPageVo != null) {
      List<OompaLoompa> oompaLoompas = new ArrayList<>();
      for (OompaLoompaVo oompaLoompaVo : oompaLoompaPageVo.getOompaLoompas()) {
        OompaLoompa oompaLoompa = new OompaLoompa(oompaLoompaVo.getId(), oompaLoompaVo.getName(),
            oompaLoompaVo.getGender(), oompaLoompaVo.getImage(), oompaLoompaVo.getProfession(),
            oompaLoompaVo.getEmail());

        oompaLoompas.add(oompaLoompa);
      }
      entity =
          new OompaLoompaPage(oompaLoompaPageVo.getPageNumber(), oompaLoompaPageVo.getTotalPages(),
              oompaLoompas);
    }
    return entity;
  }
}
