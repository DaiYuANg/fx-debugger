package org.fx.debugger.model;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SelfManifest(
    String implementationTitle,
    String implementationGroup,
    String implementationVersion,
    String builtBy,
    String builtHost,
    LocalDateTime builtDate,
    String builtOS,
    String builtJDK,
    String scmRepository,
    String scmBranch,
    String scmCommitMessage,
    String scmCommitHash,
    String scmCommitAuthor,
    LocalDateTime scmCommitDate,
    String mainClass,
    String agentClass,
    String premainClass,
    String automaticModuleName) {}
