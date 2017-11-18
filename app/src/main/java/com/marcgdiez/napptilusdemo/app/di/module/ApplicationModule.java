package com.marcgdiez.napptilusdemo.app.di.module;

import android.content.Context;
import com.marcgdiez.napptilusdemo.core.NapptilusApplication;
import com.marcgdiez.napptilusdemo.data.ApiConstants;
import com.marcgdiez.napptilusdemo.data.ServiceFactory;
import com.marcgdiez.napptilusdemo.data.oompaloompa.OompaLoompasApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  private final Context context;

  public ApplicationModule(NapptilusApplication application) {
    context = application.getApplicationContext();
  }

  @Provides @Singleton public OompaLoompasApi provideAssetsApi() {
    return ServiceFactory.createRetrofitService(OompaLoompasApi.class, ApiConstants.BASE_URL);
  }
}