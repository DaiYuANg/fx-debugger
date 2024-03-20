pluginManagement {
  repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    google()
    maven { setUrl("https://jitpack.io") }
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
  id("com.gradle.enterprise") version "3.13.4"
  id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.2"
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

gitHooks {
  preCommit {
    from {
      """
//      ./gradlew spotbugsMain
//      ./gradlew spotbugsTest
//      ./gradlew pmdMain
//      ./gradlew pmdTest
      ./gradlew spotlessApply && git add .
      """
    }
  }
  commitMsg { conventionalCommits { defaultTypes() } }
  createHooks(true)
}

rootProject.name = "scenic-view"