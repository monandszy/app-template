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
application.mainClass = "code.TemplateApp"

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

  implementation(libs.bundles.spring.modulith)
  implementation(libs.bundles.observability)
  runtimeOnly(libs.modulith.observability)
//   implementation("org.springframework.boot:spring-boot-configuration-processor")

  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation(libs.bundles.spring.web)
//   implementation("org.springframework.session:spring-session-core")
//   implementation("org.springframework.boot:spring-boot-starter-security")
//   implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
//   testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
//   testImplementation("org.springframework.security:spring-security-test")

  runtimeOnly("org.postgresql:postgresql")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.liquibase:liquibase-core")
//  implementation("org.springframework.modulith:spring-modulith-starter-jpa")

  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)
  testImplementation(libs.lombok)
  testAnnotationProcessor(libs.lombok)
  implementation(libs.mapstruct)
  annotationProcessor(libs.bundles.mapstruct.annotation)

  testImplementation(libs.bundles.test.containers)
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.platform)
  testImplementation(libs.bundles.spring.test)

  developmentOnly(libs.spring.dev.tools)
  testJavaagent(libs.javaagent.impl)

  implementation(libs.bundles.jmolecules)
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.modulith:spring-modulith-bom:1.2.1")
    mavenBom("org.jmolecules:jmolecules-bom:2023.1.4")
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

  pmd {
    isConsoleOutput = false
    isIgnoreFailures = true
    rulesMinimumPriority = 5
    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
    pmdMain {
      exclude(
      )
    }
    pmdTest {
      exclude(
      )
    }
  }
  check {
    dependsOn(pmdMain)
    dependsOn(pmdTest)
    dependsOn(jacocoTestReport)
    dependsOn(checkstyleMain)
    dependsOn(checkstyleTest)
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