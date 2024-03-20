package org.scenicview.controller;

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

@Singleton
@Slf4j
@RequiredArgsConstructor
public class MenuBarController implements Initializable {
  final Stage stage;
  public CheckMenuItem collapseControlsInTree;
  public CheckMenuItem collapseContainerControlsInTree;
  public CheckMenuItem showBaselineOverlay;
  public CheckMenuItem autoRefreshScenegraph;
  public CheckMenuItem showSearchBar;
  public CheckMenuItem showInvisibleNodesInTree;
  public CheckMenuItem registerShortcuts;
  public CheckMenuItem ignoreMouseTransparentNodes;
  public CheckMenuItem componentHighlightOrSelectOnClick;
  public CheckMenuItem showFilteredNodesInTree;
  public CheckMenuItem showNodeIDs;
  public CheckMenuItem autoRefreshStyleSheets;

  @FXML CheckMenuItem showBoundsOverlaysItem;

  @FXML MenuItem exitItem;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    exitItem.setAccelerator(KeyCombination.keyCombination("CTRL+Q"));
  }

  public void onExit(ActionEvent actionEvent) {}

  public void showAbout(ActionEvent actionEvent) {}
}
