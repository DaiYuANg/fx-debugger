import io.gitlab.plunts.gradle.plantuml.plugin.ClassDiagramsExtension
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import java.nio.charset.StandardCharsets

plugins {
  application
  alias(libs.plugins.javafx)
  alias(libs.plugins.lombok)
  alias(libs.plugins.jlink)
  alias(libs.plugins.versions)
  alias(libs.plugins.manifest)
  alias(libs.plugins.jreleaser)
  alias(libs.plugins.dotenv)
  alias(libs.plugins.spotless)
  alias(libs.plugins.plantuml)
  kotlin
  `kotlin-lombok`
  `kotlin-allopen`
  `kotlinx-serialization`
  `kotlin-kapt`
  idea
}

application {
  mainModule = "org.scenicview.scenicview"
  mainClass = "org.scenicview.ScenicView"
  applicationDefaultJvmArgs = devJvmArguments
}

group = "org.scenic-view"
version = "21.0.1"

defaultTasks("install")

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.jdkVersion.get())
  targetCompatibility = JavaVersion.toVersion(libs.versions.jdkVersion.get())
  modularity.inferModulePath.set(true)
  withSourcesJar()
  withJavadocJar()
}

tasks.compileJava {
  options.compilerArgumentProviders.add(
    CommandLineArgumentProvider {
      listOf("--patch-module", "org.scenicview.scenicview=${sourceSets["main"].output.asPath}")
    },
  )
  options.encoding = StandardCharsets.UTF_8.name()
  options.isIncremental = true
}

tasks.compileKotlin {
  val dir: DirectoryProperty = tasks.compileJava.get().destinationDirectory
  destinationDirectory.set(dir)
  kotlinOptions {
    incremental = true
  }
}

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
  google()
}

dependencies {
  implementation(libs.slf4j)
  implementation(libs.slf4jJulBridage)
  implementation(libs.slf4jJdkPlatform)
  implementation(libs.avajeInject)
  implementation(libs.oshi)
  implementation(libs.logback)

  implementation(libs.simpleicon)
  implementation(libs.fluentuiIcon)
  implementation(libs.devicons)
  implementation(libs.materialIcons)
  implementation(libs.fontawesome5)
  implementation(libs.atlantafx)
  implementation(libs.controlfx)
  implementation(libs.animated)
  implementation(libs.flowless)
  implementation(libs.preferencesfx)
  implementation(libs.ikonliJavafx)
  implementation(libs.formfx)
  compileOnly(libs.jetbrainsAnnotation)

//  annotationProcessor(libs.avajeInjectGenerator)
  annotationProcessor(libs.avajeInjectGenerator)
  implementation(libs.kotlinLogging)

  implementation(libs.gestaltConfig)
  implementation(libs.gestaltKotlin)
  implementation(libs.gestaltJSON)
  implementation(libs.gestaltToml)
  implementation(libs.gestaltYaml)

  implementation(libs.apacheCommonIO)
  implementation(libs.apacheCommonLang3)
  implementation(libs.apacheCommonPool)

  testImplementation(libs.javafxUnitTest)
  testImplementation(enforcedPlatform(libs.junitBom))
  testImplementation(libs.junitPerf)
  testImplementation(libs.junitApi)
  testImplementation(libs.junitEngine)
  testImplementation(libs.junitJuiter)
  testImplementation(libs.junitPlatformSuite)
}

kotlin {
  jvmToolchain(libs.versions.jdkVersion.get().toInt())
}

kapt {
  showProcessorStats = true
  keepJavacAnnotationProcessors = true
}

javafx {
  version = libs.versions.javafxVersion.get()
  modules = listOf("javafx.web", "javafx.fxml", "javafx.swing")
}

tasks.jar {
  manifest {
    attributes["Main-Class"] = "org.scenicview.ScenicView"
    attributes["Agent-Class"] = "org.fxconnector.remote.RuntimeAttach"
    attributes["Premain-Class"] = "org.scenicview.ScenicView"
    attributes["Automatic-Module-Name"] = "org.scenicview.scenicview"
  }
  enabled = true
  duplicatesStrategy = DuplicatesStrategy.INCLUDE
  archiveFileName = "scenicview.jar"
}

val platform =
  when (osdetector.os) {
    "osx" -> {
      "mac"
    }

    "windows" -> {
      "win"
    }

    else -> {
      ""
    }
  }

jlink {
  addExtraDependencies("slf4j", "javafx", "kotlin")
  options.addAll("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
  launcher {
    name = "scenicView"
    noConsole = true
    jvmArgs = devJvmArguments
  }
  enableCds()
  imageDir = layout.buildDirectory.dir("scenicview")
  imageZip = layout.buildDirectory.file("dist/scenicview-${JavaVersion.current()}-$platform.zip")
  mergedModule {
    additive = true
  }
}

tasks.jlink {
  doLast {
    copy {
      from(layout.buildDirectory.file("libs/scenicview.jar"))
      into(layout.buildDirectory.dir("scenicview/lib"))
    }
  }
}

manifest {
  buildAttributes = true
  implementationAttributes = true
  scmAttributes = true
}

val identWidth = 2
spotless {
  encoding = StandardCharsets.UTF_8
  format("misc") {
    target(miscTarget)
    indentWithSpaces(identWidth)
    trimTrailingWhitespace()
    targetExclude("**/node_modules/**/*")
    endWithNewline()
  }
  java {
    target("**/src/**/*.java")
    importOrder()
    googleJavaFormat().formatJavadoc(true)
    indentWithSpaces(2)
    removeUnusedImports()
    formatAnnotations()
      .addTypeAnnotation("Empty")
      .addTypeAnnotation("NonEmpty")
      .removeTypeAnnotation("Localized")
  }
  kotlin {
    target("**/*.kt")
    ktfmt()
    //        ktlint()
  }
  kotlinGradle {
    target("**/*.gradle.kts")
    ktfmt()
    ktlint()
      .setEditorConfigPath(
        "${layout.projectDirectory}/.editorconfig",
      ) // sample unusual placement
    indentWithSpaces(identWidth)
  }
  json {
    targetExclude("**/node_modules/**/*")
    target("**/src/**/*.json")
    jackson()
  }
}

idea {
  project {
    jdkName = libs.versions.jdkVersion.get()
    languageLevel = IdeaLanguageLevel(libs.versions.jdkVersion.get())
    vcs = "Git"
  }
}

tasks.test {
  useJUnitPlatform()
  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
  forkEvery = 100
  reports.html.required.set(false)
  reports.junitXml.required.set(false)
  workingDir = projectDir
}

configure<ClassDiagramsExtension> {
  val glob = "${project.group}.**"
  val internal = "internal_class_diagram"
  val full = "full_class_diagram"
  @Suppress("UNCHECKED_CAST")
  diagram(
    internal,
    closureOf<ClassDiagramsExtension.ClassDiagram> {
      include(packages().withNameLike(glob))
      writeTo(
        file(
          project.layout.buildDirectory.file(
            "$internal.${project.name}.$PLANTUML_SUFFIX",
          ),
        ),
      )
    }
      as groovy.lang.Closure<ClassDiagramsExtension.ClassDiagram>,
  )
  @Suppress("UNCHECKED_CAST")
  diagram(
    full,
    closureOf<ClassDiagramsExtension.ClassDiagram> {
      include(packages().withNameLike(glob))
      include(packages().recursive())
      writeTo(
        file(
          project.layout.buildDirectory.file(
            "$full.${project.name}.$PLANTUML_SUFFIX",
          ),
        ),
      )
    }
      as groovy.lang.Closure<ClassDiagramsExtension.ClassDiagram>,
  )
}

val integrationTestCompilation =
  kotlin.target.compilations.create("integrationTest") {
    associateWith(kotlin.target.compilations.getByName("main"))
  }