package com.marcgdiez.napptilusdemo.data;

import com.marcgdiez.napptilusdemo.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class ServiceFactory {

  /**
   * Creates a retrofit service
   *
   * @param clazz Java interface of the retrofit service
   * @param baseUrl REST endpoint url
   */

  public static <T> T createRetrofitService(final Class<T> clazz, final String baseUrl) {
    final Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(createBaseClient())
        .build();

    return retrofit.create(clazz);
  }

  private static OkHttpClient createBaseClient() {
    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    HttpLoggingInterceptor.Level loggingLevel = BuildConfig.DEBUG ? BODY : NONE;
    loggingInterceptor.setLevel(loggingLevel);
    httpClientBuilder.addInterceptor(loggingInterceptor);

    // Time Out up to 30 seconds
    httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS);
    httpClientBuilder.addInterceptor(chain -> chain.proceed(chain.request()));
    return httpClientBuilder.build();
  }
}