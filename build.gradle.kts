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
  alias(libs.plugins.shadow)
  idea
}

apply<KotlinSetting>()

group = "org.fx.debugger"
version = "21.0.1"

val manClassPath = "org.fx.debugger.FxDebugger"

application {
  mainModule = "org.fx.scenicview"
  mainClass = manClassPath
  applicationDefaultJvmArgs = devJvmArguments
}

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
      listOf("--patch-module", "$group=${sourceSets["main"].output.asPath}")
    },
  )
  options.encoding = StandardCharsets.UTF_8.name()
  options.isIncremental = true
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
  implementation(libs.oshi)
  implementation(libs.logback)
  implementation(libs.guava)
  implementation(libs.guice)
  implementation(libs.guiceAssistedinject)
  implementation(libs.guiceThrowingproviders)
  implementation(libs.guiceJMX)

  implementation(libs.simpleicon)
  implementation(libs.fluentuiIcon)
  implementation(libs.devicons)
  implementation(libs.materialIcons)
  implementation(libs.fontawesome5)
  implementation(libs.atlantafx)
  implementation(libs.controlfx)
  implementation(libs.animated)
  implementation(libs.flowless)
  implementation(libs.ikonliJavafx)
  implementation(libs.formfx)

  implementation(libs.fastutil)

  implementation(libs.picocli)
  annotationProcessor(libs.picocliCodegen)

  compileOnly(libs.jetbrainsAnnotation)

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

javafx {
  version = libs.versions.javafxVersion.get()
  modules = listOf("javafx.web", "javafx.fxml", "javafx.swing")
}

tasks.jar {
  manifest {
    attributes["Main-Class"] = manClassPath
    attributes["Agent-Class"] = "org.fxconnector.remote.RuntimeAttach"
    attributes["Premain-Class"] = "org.fx.debugger.ScenicView"
    attributes["Automatic-Module-Name"] = group
  }
  enabled = true
  duplicatesStrategy = DuplicatesStrategy.INCLUDE
  archiveFileName = "${rootProject.name}.jar"
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
  addExtraDependencies("slf4j", "javafx")
  options.addAll("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
  launcher {
    name = "scenicView"
    noConsole = true
    jvmArgs = devJvmArguments
  }
  enableCds()
  imageDir = layout.buildDirectory.dir(project.name)
  imageZip = layout.buildDirectory.file("dist/${project.name}-${JavaVersion.current()}-$platform.zip")
  mergedModule {
    additive = true
  }
}

tasks.jlink {
  doLast {
    copy {
      from(layout.buildDirectory.file("libs/${project.name}.jar"))
      into(layout.buildDirectory.dir("${project.name}/lib"))
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

tasks.shadowJar {
  minimize()
  mergeServiceFiles()
}

tasks.withType(AbstractArchiveTask::class.java) {
  isPreserveFileTimestamps = false
  isReproducibleFileOrder = true
}