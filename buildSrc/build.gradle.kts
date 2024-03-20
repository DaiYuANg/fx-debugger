plugins {
  `kotlin-dsl`
}

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
  google()
}

dependencies {
  implementation(libs.kotlinGradlePlugin)
  implementation(libs.kotlinGradleLombokPlugin)
  implementation(libs.kotlinGradleAllOpenPlugin)
  implementation(libs.kotlinGradleSerializationPlugin)
}