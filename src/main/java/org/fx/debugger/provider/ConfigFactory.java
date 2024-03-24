package org.fx.debugger.provider;

import jakarta.inject.Provider;
import java.util.prefs.Preferences;
import org.fx.debugger.FxDebugger;

public class ConfigFactory implements Provider<Preferences> {

  @Override
  public Preferences get() {
    return Preferences.userNodeForPackage(FxDebugger.class);
  }
}
