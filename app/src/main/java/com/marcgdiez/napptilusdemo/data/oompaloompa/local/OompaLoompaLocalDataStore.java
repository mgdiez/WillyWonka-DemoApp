package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.core.realm.RealmObservable;
import com.marcgdiez.napptilusdemo.data.CommonLocalDataStore;
import com.marcgdiez.napptilusdemo.data.VoMapper;
import com.marcgdiez.napptilusdemo.data.oompaloompa.vo.OompaLoompaPageVo;
import com.marcgdiez.napptilusdemo.data.oompaloompa.vo.OompaLoompaVo;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import io.realm.Case;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class OompaLoompaLocalDataStore extends CommonLocalDataStore
    implements OompaLoompaDataStore {

  private final VoMapper voMapper;

  @Inject public OompaLoompaLocalDataStore(VoMapper voMapper) {
    if (voMapper == null) {
      throw new IllegalArgumentException("VoMapper can't be null!");
    }
    this.voMapper = voMapper;
  }

  @Override public Observable<OompaLoompaPage> getOompaLoompas(int page) {
    return RealmObservable.just(realm -> realm.where(OompaLoompaPageVo.class)
        .equalTo(OompaLoompaPageVo.PRIMARY_KEY, page)
        .findFirst()).filter(result -> result != null).map(voMapper::toEntity);
  }

  @Override public void persistOompas(OompaLoompaPage response) {
    OompaLoompaPageVo vo = voMapper.toValue(response);
    saveItem(vo);
  }

  @Override public Observable<OompaLoompa> getOompaLoompaDetail(int id) {
    return RealmObservable.just(realm -> realm.where(OompaLoompaVo.class)
        .equalTo(OompaLoompaVo.PRIMARY_KEY, id)
        .findFirst()).filter(result -> result != null).map(voMapper::toEntity);
  }

  @Override public void persistOompa(OompaLoompa oompaLoompa) {
    OompaLoompaVo vo = voMapper.toValue(oompaLoompa);
    saveItem(vo);
  }

  @Override public Observable<List<OompaLoompa>> getOompaLoompasByQuery(String query) {
    return RealmObservable.from(realm -> realm.where(OompaLoompaVo.class)
        .contains(OompaLoompaVo.NAME, query, Case.INSENSITIVE)
        .findAll()).filter(result -> result != null).map(voMapper::toEntity);
  }
}
