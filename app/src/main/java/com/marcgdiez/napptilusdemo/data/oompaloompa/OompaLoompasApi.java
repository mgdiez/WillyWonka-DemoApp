package com.marcgdiez.napptilusdemo.data.oompaloompa;

import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaListResponseDto;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface OompaLoompasApi {

  @GET("oompa-loompas?page={id}") Observable<OompaLoompaListResponseDto> getOompaLoompasByPage(
      @Path("id") int pageNumber);
}