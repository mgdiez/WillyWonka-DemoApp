package com.marcgdiez.napptilusdemo.data.oompaloompa;

import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaDto;
import com.marcgdiez.napptilusdemo.data.oompaloompa.dto.OompaLoompaListResponseDto;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface OompaLoompasApi {

  @GET("oompa-loompas") Observable<OompaLoompaListResponseDto> getOompaLoompasByPage(
      @Query("page") int pageNumber);

  @GET("oompa-loompas/{id}") Observable<OompaLoompaDto> getOompaLoompaDetail(
      @Path("id") int oompaId);
}