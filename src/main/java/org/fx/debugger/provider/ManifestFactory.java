package org.fx.debugger.provider;

import jakarta.inject.Provider;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import lombok.SneakyThrows;
import lombok.val;
import org.fx.debugger.constant.ManifestAttribute;
import org.fx.debugger.model.SelfManifest;
import org.jetbrains.annotations.NotNull;

public class ManifestFactory implements Provider<SelfManifest> {
  @SneakyThrows
  @Override
  public SelfManifest get() {
    val url = ManifestFactory.class.getProtectionDomain().getCodeSource().getLocation();
    return readManifestFromJar(url);
  }

  private @NotNull SelfManifest readManifestFromJar(URL jarUrl) throws IOException {
    try (JarFile jarFile = new JarFile(jarUrl.getPath())) {
      Manifest manifest = jarFile.getManifest();
      Attributes attributes = manifest.getMainAttributes();
      return SelfManifest.builder()
          .implementationTitle(
              attributes.getValue(ManifestAttribute.IMPLEMENTATION_TITLE.getAttributeName()))
          .implementationGroup(
              attributes.getValue(ManifestAttribute.IMPLEMENTATION_GROUP.getAttributeName()))
          .implementationVersion(
              attributes.getValue(ManifestAttribute.IMPLEMENTATION_VERSION.getAttributeName()))
          .builtBy(attributes.getValue(ManifestAttribute.BUILT_BY.getAttributeName()))
          .builtHost(attributes.getValue(ManifestAttribute.BUILT_HOST.getAttributeName()))
          .builtDate(
              parseDateTime(attributes.getValue(ManifestAttribute.BUILT_DATE.getAttributeName())))
          .builtJDK(attributes.getValue(ManifestAttribute.BUILT_JDK.getAttributeName()))
          .builtOS(attributes.getValue(ManifestAttribute.BUILT_OS.getAttributeName()))
          .scmRepository(attributes.getValue(ManifestAttribute.SCM_REPOSITORY.getAttributeName()))
          .scmBranch(attributes.getValue(ManifestAttribute.SCM_BRANCH.getAttributeName()))
          .scmCommitMessage(
              attributes.getValue(ManifestAttribute.SCM_COMMIT_MESSAGE.getAttributeName()))
          .scmCommitHash(attributes.getValue(ManifestAttribute.SCM_COMMIT_HASH.getAttributeName()))
          .scmCommitAuthor(
              attributes.getValue(ManifestAttribute.SCM_COMMIT_AUTHOR.getAttributeName()))
          .scmCommitDate(
              parseDateTime(
                  attributes.getValue(ManifestAttribute.SCM_COMMIT_DATE.getAttributeName())))
          .mainClass(attributes.getValue(ManifestAttribute.MAIN_CLASS.getAttributeName()))
          .agentClass(attributes.getValue(ManifestAttribute.AGENT_CLASS.getAttributeName()))
          .premainClass(attributes.getValue(ManifestAttribute.PREMAIN_CLASS.getAttributeName()))
          .automaticModuleName(
              attributes.getValue(ManifestAttribute.AUTOMATIC_MODULE_NAME.getAttributeName()))
          .build();
    }
  }

  private @NotNull LocalDateTime parseDateTime(String dateTimeString) {
    Instant instant = Instant.parse(dateTimeString);
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
  }
}
