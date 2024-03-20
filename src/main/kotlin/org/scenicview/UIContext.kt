package org.scenicview

import io.github.oshai.kotlinlogging.KotlinLogging
import java.nio.charset.StandardCharsets
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import org.scenicview.constant.FXMLView

object UIContext {

  private val log = KotlinLogging.logger {}

  fun load(fxmlKey: FXMLView): Parent {
    val url = "${fxmlKey.view}.fxml"
    val loader = FXMLLoader(ScenicView::class.java.getResource(url))
    loader.setControllerFactory { DIContext.get(it) }
    loader.charset = StandardCharsets.UTF_8
    return loader.load()
  }
}
