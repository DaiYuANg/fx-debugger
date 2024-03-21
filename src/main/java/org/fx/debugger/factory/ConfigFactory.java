package org.fx.debugger.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import java.util.prefs.Preferences;
import org.fx.debugger.FxDebugger;

@Factory
public class ConfigFactory {

  @Bean
  Preferences preferences() {
    return Preferences.userNodeForPackage(FxDebugger.class);
  }
}
