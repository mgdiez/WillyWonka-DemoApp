package com.marcgdiez.napptilusdemo.core.executor;

import android.support.annotation.NonNull;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;

public class ThreadExecutor implements Executor {

  private static final int CORE_POOL_SIZE = 5;
  private static final int MAX_POOL_SIZE = 7;
  private static final int KEEP_ALIVE_TIME = 120;
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
  private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

  private final ThreadPoolExecutor threadPoolExecutor;

  @Inject public ThreadExecutor() {
    int corePoolSize = CORE_POOL_SIZE;
    int maxPoolSize = MAX_POOL_SIZE;
    int keepAliveTime = KEEP_ALIVE_TIME;
    TimeUnit timeUnit = TIME_UNIT;
    BlockingQueue<Runnable> workQueue = WORK_QUEUE;
    threadPoolExecutor =
        new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, timeUnit, workQueue,
            new AndroidThreadFactory());
  }

  @Override public void execute(@NonNull Runnable command) {
    threadPoolExecutor.execute(command);
  }

  private static class AndroidThreadFactory implements ThreadFactory {

    private static final AtomicInteger count = new AtomicInteger();

    @Override public Thread newThread(@NonNull Runnable r) {
      return new Thread(r, "NapptilusDemo_" + count.getAndIncrement());
    }
  }
}
