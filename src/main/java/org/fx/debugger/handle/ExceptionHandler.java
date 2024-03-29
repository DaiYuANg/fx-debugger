package org.fx.debugger.handle;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Singleton
@Slf4j
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
  @Override
  public void uncaughtException(@NotNull Thread t, Throwable e) {
    log.error(t.getName(), e);
  }
}
