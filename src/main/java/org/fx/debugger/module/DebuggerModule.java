package org.fx.debugger.module;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import java.util.prefs.Preferences;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.val;
import org.fx.debugger.model.SelfManifest;
import org.fx.debugger.provider.*;
import org.fx.debugger.service.RemoteAttachService;

public class DebuggerModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ServiceManager.class).toProvider(ServiceManagerProvider.class);
    bind(Preferences.class).toProvider(ConfigFactory.class);
    bind(SelfManifest.class).toProvider(ManifestFactory.class);
    bind(Scene.class).toProvider(SceneProvider.class);
    bind(Stage.class).toProvider(StageProvider.class);

    val uriBinder = Multibinder.newSetBinder(binder(), Service.class);
    uriBinder.addBinding().to(RemoteAttachService.class);
  }
}
