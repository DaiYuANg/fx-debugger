package org.fx.debugger.context;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import org.fx.debugger.module.DebuggerModule;
import org.jetbrains.annotations.NotNull;

public enum DIContext {
  INSTANCE;
  private final Injector injector = Guice.createInjector(Stage.DEVELOPMENT, new DebuggerModule());

  public <T> @NotNull T get(Class<T> clazz) {
    return injector.getInstance(clazz);
  }
}
