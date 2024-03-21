package org.fx.debugger.controller;

import jakarta.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fx.debugger.context.DIContext;
import org.fx.debugger.view.AboutDialog;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class MenuBarController implements Initializable {
  final Stage stage;

  @FXML CheckMenuItem collapseControlsInTree;

  @FXML CheckMenuItem collapseContainerControlsInTree;

  @FXML CheckMenuItem showBaselineOverlay;

  @FXML CheckMenuItem autoRefreshScenegraph;

  @FXML CheckMenuItem showSearchBar;

  @FXML CheckMenuItem showInvisibleNodesInTree;
  @FXML CheckMenuItem registerShortcuts;
  @FXML CheckMenuItem ignoreMouseTransparentNodes;
  @FXML CheckMenuItem componentHighlightOrSelectOnClick;
  @FXML CheckMenuItem showFilteredNodesInTree;
  @FXML CheckMenuItem showNodeIDs;

  @FXML CheckMenuItem autoRefreshStyleSheets;

  @FXML CheckMenuItem showBoundsOverlaysItem;

  @FXML MenuItem exitItem;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    exitItem.setAccelerator(KeyCombination.keyCombination("CTRL+Q"));
  }

  public void onExit(ActionEvent actionEvent) {}

  public void showAbout(ActionEvent actionEvent) {
    val aboutView = DIContext.INSTANCE.get(AboutDialog.class);
    aboutView.show();
  }
}
