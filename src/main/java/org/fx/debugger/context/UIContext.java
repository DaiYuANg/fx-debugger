package org.fx.debugger.context;

import java.nio.charset.StandardCharsets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.SneakyThrows;
import lombok.val;
import org.fx.debugger.constant.FXMLView;
import org.fx.debugger.view.ScenicView;

public enum UIContext {
  INSTANCE;

  @SneakyThrows
  public Parent load(FXMLView view) {
    val url = view.getView() + ".fxml";
    val loader = new FXMLLoader(ScenicView.class.getResource(url));
    loader.setControllerFactory(DIContext.INSTANCE::get);
    loader.setCharset(StandardCharsets.UTF_8);
    return loader.load();
  }
}
