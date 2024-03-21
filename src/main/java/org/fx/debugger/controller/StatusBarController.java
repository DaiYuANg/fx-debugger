package org.fx.debugger.controller;

import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class StatusBarController implements Initializable {
  public HBox mainStatusBar;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}
}
