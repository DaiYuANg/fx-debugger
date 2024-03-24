/*
 * Scenic View,
 * Copyright (C) 2012, 2018 Jonathan Giles, Ander Ruiz, Amy Fowler
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
module org.fx.debugger {
  requires transitive javafx.base;
  requires transitive javafx.fxml;
  requires transitive javafx.web;
  requires transitive javafx.swing;
  requires org.slf4j;
  requires static lombok;
  requires static org.jetbrains.annotations;
  requires java.instrument;
  requires java.rmi;
  requires java.logging;
  requires jdk.attach;
  requires java.desktop;
  requires atlantafx.base;
  requires org.apache.commons.lang3;
  requires org.apache.commons.pool2;
  requires org.apache.commons.io;
  requires com.google.common;
  requires com.github.oshi;
  requires info.picocli;
  requires it.unimi.dsi.fastutil;
  requires org.kordamp.ikonli.core;
  requires org.kordamp.ikonli.devicons;
  requires org.kordamp.ikonli.fluentui;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.ikonli.fontawesome5;
  requires org.kordamp.ikonli.simpleicons;
  requires org.kordamp.ikonli.materialdesign2;
  requires java.prefs;
  requires com.google.guice;
  requires jakarta.inject;
  requires io.github.oshai.kotlinlogging;
  requires kotlin.stdlib;

  opens org.fx.debugger.view.cssfx to
      javafx.fxml;
  opens org.fx.debugger.view.threedom to
      javafx.fxml;
  opens org.fx.debugger.fxconnector.remote to
      java.instrument,
      java.rmi;
  opens org.fx.debugger.model;

  exports org.fx.debugger.view.cssfx to
      javafx.fxml;
  exports org.fx.debugger.view.threedom to
      javafx.fxml;
  exports org.fx.debugger.fxconnector.remote to
      java.instrument;
  exports org.fx.debugger.fxconnector;

  opens org.fx.debugger.controller to
      javafx.fxml;

  exports org.fx.debugger;
  exports org.fx.debugger.component to
      javafx.fxml;
  exports org.fx.debugger.view;
  exports org.fx.debugger.utils;
  exports org.fx.debugger.handle to
      com.google.guice;
  exports org.fx.debugger.service to
      com.google.guice;
  exports org.fx.debugger.provider to
      com.google.guice;
  exports org.fx.debugger.controller to
      com.google.guice;

  opens org.fx.debugger.utils to
      java.instrument,
      java.rmi;
}
