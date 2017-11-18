package com.marcgdiez.napptilusdemo.core.subscriber;

public final class ExceptionMessageConverter {

  private ExceptionMessageConverter() {

  }

  public static String convert(Throwable e) {
    // TODO implement every exception
    return e.getMessage();
  }
}