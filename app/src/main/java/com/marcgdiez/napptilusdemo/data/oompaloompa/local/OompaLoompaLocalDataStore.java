package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.core.realm.RealmObservable;
import com.marcgdiez.napptilusdemo.data.CommonLocalDataStore;
import com.marcgdiez.napptilusdemo.data.VoMapper;
import com.marcgdiez.napptilusdemo.data.oompaloompa.vo.OompaLoompaPageVo;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
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
}
