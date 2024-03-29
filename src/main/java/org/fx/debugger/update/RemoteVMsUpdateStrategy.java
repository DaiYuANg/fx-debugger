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
package org.fx.debugger.update;

import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.fx.debugger.fxconnector.AppController;
import org.fx.debugger.fxconnector.StageController;
import org.fx.debugger.fxconnector.helper.WorkerThread;
import org.fx.debugger.fxconnector.remote.FXConnector;

@Slf4j
public class RemoteVMsUpdateStrategy extends WorkerThread implements UpdateStrategy {

  private boolean first = true;
  private FXConnector connector;

  AppsRepository repository;
  List<AppController> previous = new ArrayList<>();

  public RemoteVMsUpdateStrategy() {
    super(RemoteVMsUpdateStrategy.class.getName(), 500);
  }

  @SneakyThrows
  private List<AppController> getActiveApps() {
    if (first) {
      /** Wait for the server to startup */
      first = false;
      while (connector == null) {
        Thread.sleep(5000);
      }
    }

    return connector.connect();
  }

  @Override
  public void finish() {
    super.finish();
    connector.close();
    System.exit(0);
  }

  public void setFXConnector(final FXConnector connector) {
    this.connector = connector;
  }

  @Override
  public void start(final AppsRepository repository) {
    this.repository = repository;
    start();
  }

  @Override
  protected void work() {
    boolean modifications = false;
    final List<AppController> actualApps = getActiveApps();

    final List<StageController> unused = new ArrayList<>();
    for (AppController actualApp : actualApps) {
      unused.addAll(actualApp.getStages());
    }

    /** First check new apps */
    for (int i = 0; i < actualApps.size(); i++) {
      if (isAppOnArray(actualApps.get(i), previous) == -1) {
        repository.appAdded(actualApps.get(i));
        unused.removeAll(actualApps.get(i).getStages());
        modifications = true;
      }
    }

    /** Then check remove apps */
    for (int i = 0; i < previous.size(); i++) {
      if (isAppOnArray(previous.get(i), actualApps) == -1) {
        repository.appRemoved(previous.get(i));
        modifications = true;
      }
    }

    /** Then check added/removed Stages */
    for (int i = 0; i < actualApps.size(); i++) {
      if (isAppOnArray(actualApps.get(i), previous) != -1) {
        final List<StageController> stages = actualApps.get(i).getStages();
        final List<StageController> previousStages =
            previous.get(isAppOnArray(actualApps.get(i), previous)).getStages();
        for (int j = 0; j < stages.size(); j++) {
          if (!isStageOnArray(stages.get(j), previousStages)) {
            repository.stageAdded(stages.get(j));
            unused.remove(stages.get(j));
            modifications = true;
          }
        }
        for (int j = 0; j < previousStages.size(); j++) {
          if (!isStageOnArray(previousStages.get(j), stages)) {
            repository.stageRemoved(previousStages.get(j));
            modifications = true;
          }
        }
      }
    }
    if (modifications) {
      previous = actualApps;
    }
  }

  boolean isStageOnArray(final StageController controller, final List<StageController> stages) {
    for (int i = 0; i < stages.size(); i++) {
      if (stages.get(i).getID().getStageID() == controller.getID().getStageID()) {
        return true;
      }
    }
    return false;
  }

  int isAppOnArray(final AppController controller, final List<AppController> apps) {
    for (int i = 0; i < apps.size(); i++) {
      if (apps.get(i).getID() == controller.getID()) {
        return i;
      }
    }
    return -1;
  }
}
