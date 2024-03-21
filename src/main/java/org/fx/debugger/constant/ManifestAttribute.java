package org.fx.debugger.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ManifestAttribute {
  IMPLEMENTATION_TITLE("Implementation-Title"),
  IMPLEMENTATION_GROUP("Implementation-Group"),
  IMPLEMENTATION_VERSION("Implementation-Version"),
  BUILT_BY("Built-By"),
  BUILT_HOST("Built-Host"),
  BUILT_DATE("Built-Date"),
  BUILT_JDK("Built-JDK"),
  BUILT_OS("Built-OS"),
  SCM_REPOSITORY("SCM-Repository"),
  SCM_BRANCH("SCM-Branch"),
  SCM_COMMIT_MESSAGE("SCM-Commit-Message"),
  SCM_COMMIT_HASH("SCM-Commit-Hash"),
  SCM_COMMIT_AUTHOR("SCM-Commit-Author"),
  SCM_COMMIT_DATE("SCM-Commit-Date"),
  MAIN_CLASS("Main-Class"),
  AGENT_CLASS("Agent-Class"),
  PREMAIN_CLASS("Premain-Class"),
  AUTOMATIC_MODULE_NAME("Automatic-Module-Name");
  private final String attributeName;
}
