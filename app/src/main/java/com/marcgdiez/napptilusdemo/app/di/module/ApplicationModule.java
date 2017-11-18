package com.marcgdiez.napptilusdemo.app.di.module;

import android.content.Context;
import com.marcgdiez.napptilusdemo.core.NapptilusApplication;
import com.marcgdiez.napptilusdemo.core.executor.MainThread;
import com.marcgdiez.napptilusdemo.core.executor.ThreadExecutor;
import com.marcgdiez.napptilusdemo.data.ApiConstants;
import com.marcgdiez.napptilusdemo.data.ServiceFactory;
import com.marcgdiez.napptilusdemo.data.oompaloompa.OompaLoompasApi;
import com.marcgdiez.napptilusdemo.data.oompaloompa.local.OompaLoompaLocalDataStore;
import com.marcgdiez.napptilusdemo.data.oompaloompa.network.OompaLoompaCloudDataSource;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepository;
import com.marcgdiez.napptilusdemo.data.oompaloompa.repository.OompaLoompaRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.Executor;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  private final Context context;

  public ApplicationModule(NapptilusApplication application) {
    context = application.getApplicationContext();
  }

  @Provides @Singleton public OompaLoompasApi provideAssetsApi() {
    return ServiceFactory.createRetrofitService(OompaLoompasApi.class, ApiConstants.BASE_URL);
  }

  @Provides @Singleton public Executor provideExecutor() {
    return new ThreadExecutor();
  }

  @Provides @Singleton public MainThread providesMainThread() {
    return new MainThread();
  }

  @Provides @Singleton
  public OompaLoompaRepository provideOompaLoompaRepository(OompaLoompaLocalDataStore dataStore,
      OompaLoompaCloudDataSource dataSource) {
    return new OompaLoompaRepositoryImpl(dataStore, dataSource);
  }
}