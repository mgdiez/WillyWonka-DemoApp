package com.marcgdiez.napptilusdemo.data.oompaloompa.network;

import android.support.annotation.NonNull;
import com.marcgdiez.napptilusdemo.data.DtoMapper;
import com.marcgdiez.napptilusdemo.data.oompaloompa.OompaLoompasApi;
import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaListResponseDto;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import java.util.List;
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

  @Override public Observable<List<OompaLoompa>> getOompaLoompas(int page) {
    return oompaLoompasApi.getOompaLoompasByPage(page).map(toEntity());
  }

  @NonNull private Func1<OompaLoompaListResponseDto, List<OompaLoompa>> toEntity() {
    return dtoMapper::mapOompaLoompaListResponse;
  }
}
