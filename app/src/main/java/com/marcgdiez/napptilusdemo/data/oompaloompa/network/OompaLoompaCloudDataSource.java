package com.marcgdiez.napptilusdemo.data.oompaloompa.network;

import android.support.annotation.NonNull;
import com.marcgdiez.napptilusdemo.data.DtoMapper;
import com.marcgdiez.napptilusdemo.data.oompaloompa.OompaLoompasApi;
import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaDto;
import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaListResponseDto;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

public class OompaLoompaCloudDataSource implements OompaLoompaDataSource {

  private final OompaLoompasApi oompaLoompasApi;
  private final DtoMapper dtoMapper;

  @Inject OompaLoompaCloudDataSource(OompaLoompasApi oompaLoompasApi, DtoMapper dtoMapper) {
    this.oompaLoompasApi = oompaLoompasApi;
    this.dtoMapper = dtoMapper;
  }

  @Override public Observable<OompaLoompaPage> getOompaLoompas(int page) {
    return oompaLoompasApi.getOompaLoompasByPage(page).map(toPageEntity());
  }

  @Override public Observable<OompaLoompa> getOompaLoompaDetail(int id) {
    return oompaLoompasApi.getOompaLoompaDetail(id).map(toOompaDetail());
  }

  @NonNull private Func1<OompaLoompaDto, OompaLoompa> toOompaDetail() {
    return dtoMapper::mapOompaLoompaResponse;
  }

  @NonNull private Func1<OompaLoompaListResponseDto, OompaLoompaPage> toPageEntity() {
    return dtoMapper::mapOompaLoompaListResponse;
  }
}
