package org.fx.debugger.provider;

import jakarta.inject.Provider;
import javafx.stage.Stage;

public class StageProvider implements Provider<Stage> {
  @Override
  public Stage get() {
    return new Stage();
  }
}
