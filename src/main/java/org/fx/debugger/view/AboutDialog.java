package org.fx.debugger.view;

import io.avaje.inject.Lazy;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.function.Supplier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fx.debugger.constant.ManifestAttribute;
import org.fx.debugger.model.SelfManifest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Singleton
@Lazy
@Slf4j
public class AboutDialog extends Dialog<Void> {

  private final GridPane content = new GridPane();

  {
    content.setPadding(new Insets(20));
    content.setHgap(10);
    content.setVgap(5);
  }

  private final SelfManifest selfManifest;

  {
    getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    setResizable(true);
    setTitle("About Fx-Debugger");
  }

  private final HBox h = new HBox(content);

  private final VBox v = new VBox(h);

  {
    v.setAlignment(Pos.CENTER);
    h.setAlignment(Pos.CENTER);
  }

  public AboutDialog(SelfManifest selfManifest) {
    log.atDebug().log("Manifest:{}", selfManifest);
    this.selfManifest = selfManifest;

    val text = buildContent();
    int row = 0;
    for (Map.Entry<String, Supplier<String>> entry : text.entrySet()) {
      val keyLabel = new Label(entry.getKey() + ":");
      val valueLabel = new Label(entry.getValue().get());

      content.addRow(row++, keyLabel, valueLabel);
    }
    getDialogPane().setContent(v);
  }

  @Contract(" -> new")
  private @NotNull Object2ObjectMap<String, Supplier<String>> buildContent() {
    return new Object2ObjectArrayMap<>() {
      {
        put(
            ManifestAttribute.IMPLEMENTATION_TITLE.getAttributeName(),
            selfManifest::implementationTitle);
        put(ManifestAttribute.BUILT_OS.getAttributeName(), selfManifest::builtOS);
        put(ManifestAttribute.BUILT_JDK.getAttributeName(), selfManifest::builtJDK);
        put(ManifestAttribute.BUILT_BY.getAttributeName(), selfManifest::builtBy);
        put(ManifestAttribute.BUILT_HOST.getAttributeName(), selfManifest::builtHost);
        put(
            ManifestAttribute.BUILT_DATE.getAttributeName(),
            () -> selfManifest.builtDate().toString());
        put(
            ManifestAttribute.IMPLEMENTATION_GROUP.getAttributeName(),
            selfManifest::implementationGroup);
        put(
            ManifestAttribute.IMPLEMENTATION_VERSION.getAttributeName(),
            selfManifest::implementationVersion);
        put(ManifestAttribute.AGENT_CLASS.getAttributeName(), selfManifest::agentClass);
        put(ManifestAttribute.MAIN_CLASS.getAttributeName(), selfManifest::mainClass);
        put(ManifestAttribute.PREMAIN_CLASS.getAttributeName(), selfManifest::premainClass);
        put(ManifestAttribute.SCM_BRANCH.getAttributeName(), selfManifest::scmBranch);
        put(ManifestAttribute.SCM_COMMIT_AUTHOR.getAttributeName(), selfManifest::scmCommitAuthor);
        put(ManifestAttribute.SCM_REPOSITORY.getAttributeName(), selfManifest::scmRepository);
        put(ManifestAttribute.SCM_COMMIT_HASH.getAttributeName(), selfManifest::scmCommitHash);
        put(
            ManifestAttribute.SCM_COMMIT_MESSAGE.getAttributeName(),
            selfManifest::scmCommitMessage);
        put(
            ManifestAttribute.SCM_COMMIT_DATE.getAttributeName(),
            () -> selfManifest.scmCommitDate().toString());
      }
    };
  }
}
