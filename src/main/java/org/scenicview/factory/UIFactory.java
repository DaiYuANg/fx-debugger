package org.scenicview.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.Lazy;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Factory
@Lazy
public class UIFactory {

  @Bean
  Scene scene() {
    return new Scene(new Pane());
  }

  @Bean
  Stage stage() {
    return new Stage();
  }
}
