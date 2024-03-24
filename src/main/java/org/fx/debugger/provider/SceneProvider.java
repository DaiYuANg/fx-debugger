package org.fx.debugger.provider;

import jakarta.inject.Provider;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class SceneProvider implements Provider<Scene> {
  @Override
  public Scene get() {
    return new Scene(new Pane());
  }
}
