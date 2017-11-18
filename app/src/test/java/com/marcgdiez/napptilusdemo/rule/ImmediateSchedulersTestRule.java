package com.marcgdiez.napptilusdemo.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class ImmediateSchedulersTestRule implements TestRule {

  private final RxAndroidSchedulersHook ImmediateRxAndroidSchedulersHook =
      new RxAndroidSchedulersHook() {
        @Override public Scheduler getMainThreadScheduler() {
          return Schedulers.immediate();
        }
      };

  @Override public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override public void evaluate() throws Throwable {
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(ImmediateRxAndroidSchedulersHook);

        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
        RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());
        RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

        try {
          base.evaluate();
        } finally {
          RxJavaHooks.reset();
          RxAndroidPlugins.getInstance().reset();
        }
      }
    };
  }
}
