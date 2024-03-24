package org.fx.debugger.view

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.inject.Inject
import jakarta.inject.Singleton
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import lombok.extern.slf4j.Slf4j
import org.fx.debugger.constant.ManifestAttribute
import org.fx.debugger.model.SelfManifest
import org.jetbrains.annotations.Contract

@Singleton
@Slf4j
class AboutDialog @Inject constructor(private val selfManifest: SelfManifest) : Dialog<Void?>() {
  private val log = KotlinLogging.logger {}
  private val content = GridPane()

  init {
    content.padding = Insets(20.0)
    content.hgap = 10.0
    content.vgap = 5.0
  }

  init {
    dialogPane.buttonTypes.add(ButtonType.CLOSE)
    isResizable = true
    title = "About Fx-Debugger"
  }

  private val h = HBox(content).apply { alignment = Pos.CENTER }

  private val v = VBox(h).apply { alignment = Pos.CENTER }

  init {
    val text = buildContent()
    var row = 0
    for ((key, value) in text) {
      val keyLabel = Label("$key:")
      val valueLabel = Label(value)

      content.addRow(row++, keyLabel, valueLabel)
    }
    dialogPane.content = v
  }

  @Contract(" -> new")
  private fun buildContent(): Map<String, String> {
    return mapOf(
        ManifestAttribute.IMPLEMENTATION_TITLE.attributeName to selfManifest.implementationTitle,
        ManifestAttribute.BUILT_OS.attributeName to selfManifest.builtOS)
    //    return object : Object2ObjectArrayMap<String, Supplier<String>>() {
    //      init {
    //        put(
    //          ManifestAttribute.IMPLEMENTATION_TITLE.attributeName,
    //          selfManifest::implementationTitle
    //        )
    //        put(ManifestAttribute.BUILT_OS.attributeName, selfManifest::builtOS)
    //        put(ManifestAttribute.BUILT_JDK.attributeName, selfManifest::builtJDK)
    //        put(ManifestAttribute.BUILT_BY.attributeName, selfManifest::builtBy)
    //        put(ManifestAttribute.BUILT_HOST.attributeName, selfManifest::builtHost)
    //        put(
    //          ManifestAttribute.BUILT_DATE.attributeName,
    //          Supplier { selfManifest.builtDate.toString() })
    //        put(
    //          ManifestAttribute.IMPLEMENTATION_GROUP.attributeName,
    //          selfManifest::implementationGroup
    //        )
    //        put(
    //          ManifestAttribute.IMPLEMENTATION_VERSION.attributeName,
    //          selfManifest::implementationVersion
    //        )
    //        put(ManifestAttribute.AGENT_CLASS.attributeName, selfManifest::agentClass)
    //        put(ManifestAttribute.MAIN_CLASS.attributeName, selfManifest::mainClass)
    //        put(ManifestAttribute.PREMAIN_CLASS.attributeName, selfManifest::premainClass)
    //        put(ManifestAttribute.SCM_BRANCH.attributeName, selfManifest::scmBranch)
    //        put(ManifestAttribute.SCM_COMMIT_AUTHOR.attributeName, selfManifest::scmCommitAuthor)
    //        put(ManifestAttribute.SCM_REPOSITORY.attributeName, selfManifest::scmRepository)
    //        put(ManifestAttribute.SCM_COMMIT_HASH.attributeName, selfManifest::scmCommitHash)
    //        put(
    //          ManifestAttribute.SCM_COMMIT_MESSAGE.attributeName,
    //          selfManifest::scmCommitMessage
    //        )
    //        put(
    //          ManifestAttribute.SCM_COMMIT_DATE.attributeName,
    //          Supplier { selfManifest.scmCommitDate.toString() })
    //      }
    //    }
  }
}
