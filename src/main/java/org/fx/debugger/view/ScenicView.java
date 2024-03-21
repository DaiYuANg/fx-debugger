/*
 * Scenic View,
 * Copyright (C) 2012 Jonathan Giles, Ander Ruiz, Amy Fowler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fx.debugger.view;

import atlantafx.base.theme.CupertinoDark;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fx.debugger.constant.FXMLView;
import org.fx.debugger.context.DIContext;
import org.fx.debugger.context.UIContext;
import org.fx.debugger.fxconnector.AppController;
import org.fx.debugger.fxconnector.AppControllerImpl;
import org.fx.debugger.fxconnector.StageControllerImpl;
import org.fx.debugger.update.LocalUpdateStrategy;
import org.jetbrains.annotations.NotNull;

/** This is the entry point for all different versions of Scenic View. */
@Slf4j
public class ScenicView extends Application {

  public static final String JDK_PATH_KEY = "jdkPath";

  public static void show(final @NotNull Scene target) {
    show(target.getRoot());
  }

  public static void show(final Parent target) {
    if (target == null) {
      return;
    }

    final Stage stage = new Stage();

    // workaround for RT-10714
    stage.setWidth(1024);
    stage.setHeight(768);
    stage.setTitle("Scenic View v" + ScenicViewGui.VERSION);

    final List<AppController> controllers = new ArrayList<>();
    final AppController aController = new AppControllerImpl();
    final boolean sceneRoot = target.getScene().getRoot() == target;
    final StageControllerImpl sController = new StageControllerImpl(target, aController, sceneRoot);

    System.out.println("aController = " + aController);
    aController.getStages().add(sController);
    controllers.add(aController);

    final LocalUpdateStrategy updateStrategy = new LocalUpdateStrategy(controllers);
    ScenicViewGui.show(new ScenicViewGui(updateStrategy, stage), stage);
  }

  /**************************************************************************
   *
   * runtime discovery start point
   * (Also refer to RuntimeAttach class)
   *
   *************************************************************************/
  //  public static void premain(final String agentArgs, final Instrumentation instrumentation) {
  //    // we start up a new thread to take care of initialising Scenic View
  //    // so that we don't block the loading of the actual application.
  //    @SuppressWarnings("unused")
  //    Thread scenicViewBootThread =
  //        new Thread(
  //            () -> {
  //              while (true) {
  //                try {
  //                  Platform.runLater(
  //                      () -> {
  //                        try {
  //                          new ScenicView().start(new Stage());
  //                        } catch (Exception e) {
  //                          e.printStackTrace();
  //                        }
  //                      });
  //                  break;
  //                } catch (IllegalStateException e) {
  //                  // FX runtime hasn't been initialized yet. Actual initialization occurs in
  // method
  //                  // LauncherImpl.startup()
  //                  // which must be called only once and under normal circumstances called as
  // result
  //                  // of launching instrumented
  //                  // application, so we have to wait while FX runtime will be initialized.
  //                  try {
  //                    Thread.sleep(500);
  //                  } catch (InterruptedException ex) {
  //                  }
  //                }
  //              }
  //            },
  //            "scenic-view-boot");
  //    scenicViewBootThread.setDaemon(true);
  //    scenicViewBootThread.start();
  //  }

  public static void main(final String @NotNull [] args) {
    launch(args);
  }

  @Override
  public void init() {
    Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
  }

  @Override
  public void start(final Stage stage) throws Exception {
    //    AttachHandlerFactory.initAttachAPI(stage);
    //    val strategy = new RemoteVMsUpdateStrategy();

    //    // workaround for RT-10714
    //    stage.setWidth(1024);
    //    stage.setHeight(768);
    //    stage.setTitle("Scenic View v" + ScenicViewGui.VERSION);
    //    log.atInfo().log("Platform running");
    //    log.atInfo().log("Launching ScenicView v" + ScenicViewGui.VERSION);
    //    ScenicViewGui view = new ScenicViewGui(strategy, stage);
    //    ScenicViewGui.show(view, stage);
    //
    //    log.atInfo().log("Startup done");
    //
    //    log.atInfo().log("Creating server");
    //    strategy.setFXConnector(FXConnectorFactory.getConnector());
    //
    //    log.atInfo().log("Server done");

    val rootStage = DIContext.INSTANCE.get(Stage.class);
    val rootScene = DIContext.INSTANCE.get(Scene.class);
    val mainLayout = UIContext.INSTANCE.load(FXMLView.LAYOUT);

    rootScene.setRoot(mainLayout);
    rootStage.setScene(rootScene);
    rootStage.show();
  }
}
