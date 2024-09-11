plugins {
  application
  pmd
  jacoco
  checkstyle
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.management)
  alias(libs.plugins.javaagent)
}

group = "code"
java.toolchain.languageVersion = JavaLanguageVersion.of(21)

apply(from = rootProject.file("gradle/util/misc.gradle.kts"))
apply(from = rootProject.file("gradle/util/git.gradle.kts"))
apply(from = rootProject.file("gradle/util/docker.gradle.kts"))

repositories {
  mavenCentral()
  maven {
    url = uri("https://repo.spring.io/release")
  }
}

dependencies {
  implementation(libs.spring.modulith)

  implementation(libs.bundles.spring.data)
  runtimeOnly(libs.postgresql)

  implementation(libs.bundles.observability)
  runtimeOnly(libs.modulith.observability)

  implementation(libs.bundles.events)

  implementation(libs.bundles.spring.web)
  implementation(libs.bundles.thymeleaf)

//  implementation(libs.bundles.jmolecules)
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)
  testImplementation(libs.lombok)
  testAnnotationProcessor(libs.lombok)
  implementation(libs.mapstruct)
  annotationProcessor(libs.bundles.mapstruct.annotation)

  testImplementation(libs.bundles.testcontainers)
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.platform)
  testImplementation(libs.bundles.spring.test)

  developmentOnly(libs.spring.dev.tools)
  testJavaagent(libs.javaagent.impl)

}

dependencyManagement {
  imports {
    mavenBom("org.springframework.modulith:spring-modulith-bom:1.2.1")
//    mavenBom("org.jmolecules:jmolecules-bom:2023.1.4")
    mavenBom("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom:2.6.0")
  }
}

tasks {
  bootJar {
    archiveFileName = "${project.name}-${version}.${archiveExtension.get()}"
  }
  jar {
    enabled = false
  }

  test {
    useJUnitPlatform()
    testLogging {
      events("passed", "skipped", "failed")
    }
  }

  check {
    dependsOn(pmdMain)
    dependsOn(pmdTest)
    dependsOn(jacocoTestReport)
    dependsOn(checkstyleMain)
    dependsOn(checkstyleTest)
  }

  pmd {
    toolVersion = "7.4.0"
    isConsoleOutput = false
    isIgnoreFailures = true
    rulesMinimumPriority = 5
    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
    pmdMain {
      exclude(
      )
      doLast {
        val reportPath = layout.buildDirectory.file("reports/pmd/main.html").get().asFile
        println("PmdMain report: file://${reportPath.toURI().path}")
      }
    }
    pmdTest {
      exclude(
      )
      doLast {
        val reportPath = layout.buildDirectory.file("reports/pmd/test.html").get().asFile
        println("PmdTest report: file://${reportPath.toURI().path}")
      }
    }
  }

  checkstyle {
    isIgnoreFailures = true
    isShowViolations = false
    configFile = file("config/checkstyle.xml")
    checkstyleMain {
      source("src/main/java")
      classpath = project.files()
    }
    checkstyleTest {
      source("src/test/java")
      classpath = project.files()
    }
  }

  jacoco {
    jacocoTestReport {
      reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
      }
      doLast {
        val reportPath = layout.buildDirectory.file("reports/jacoco/index.html").get().asFile
        println("Jacoco report: file://${reportPath.toURI().path}")
      }
      classDirectories.setFrom(
        files(classDirectories.files.map {
          fileTree(it) {
            exclude(
            )
          }
        })
      )
      dependsOn(test)
    }
  }

  register("report") {
    doLast {
      var reportPath = layout.buildDirectory.file("reports/jacoco/index.html").get().asFile
      println("Jacoco report: file://${reportPath.toURI().path}")
      reportPath = layout.buildDirectory.file("reports/checkstyle/main.html").get().asFile
      println("CheckstyleMain report: file://${reportPath.toURI().path}")
      reportPath = layout.buildDirectory.file("reports/checkstyle/test.html").get().asFile
      println("CheckstyleTest report: file://${reportPath.toURI().path}")
      reportPath = layout.buildDirectory.file("reports/pmd/main.html").get().asFile
      println("PmdMain report: file://${reportPath.toURI().path}")
      reportPath = layout.buildDirectory.file("reports/pmd/test.html").get().asFile
      println("PmdTest report: file://${reportPath.toURI().path}")

    }
  }

  javadoc {
    setDestinationDir(file(layout.buildDirectory.dir("docs")))
    options.encoding = "UTF-8"
  }
  compileJava {
    options.encoding = "UTF-8"
  }
  compileTestJava {
    options.encoding = "UTF-8"
  }
}