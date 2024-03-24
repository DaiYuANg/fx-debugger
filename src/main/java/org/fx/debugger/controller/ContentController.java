package org.fx.debugger.controller;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fx.debugger.constant.ConfigConstant;

@Singleton
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ContentController implements Initializable {

  @FXML SplitPane contentRoot;

  final Preferences preferences;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    val defaultValue =
        preferences.getDouble(ConfigConstant.SPLIT_PANE_DIVIDER_POSITION.getKey(), 0.2);
    //    contentRoot.getDividers().getFirst().
    //      contentRoot.getDividers().forEach(divider -> {
    //      divider.positionProperty()
    //      });
  }
}
