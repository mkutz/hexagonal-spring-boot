import org.jetbrains.kotlin.gradle.internal.builtins.StandardNames.FqNames.target

plugins {
  `java-test-fixtures`
  `jvm-test-suite`
  alias(libs.plugins.flyway)
  alias(libs.plugins.kotlin.jpa)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spotless)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
}

group = "io.github.mkutz"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

repositories { mavenCentral() }

dependencies {
  implementation(libs.spring.boot.starter.webmvc)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.kafka)
  implementation(libs.jackson.module.kotlin)
  implementation(libs.kotlin.reflect)
  implementation(libs.spring.boot.starter.flyway)
  implementation(libs.flyway.database.postgresql)

  runtimeOnly(libs.postgresql)

  testFixturesImplementation(libs.kotlin.reflect)
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

testing {
  suites {
    val test by
      getting(JvmTestSuite::class) {
        useJUnitJupiter()
        dependencies { implementation(libs.assertj.core) }
      }

    val adapterTest by
      registering(JvmTestSuite::class) {
        useJUnitJupiter()
        dependencies {
          implementation(project())
          implementation(testFixtures(project()))
          implementation(libs.spring.boot.starter.test)
          implementation(libs.spring.boot.starter.webmvc.test)
          implementation(libs.spring.boot.starter.json)
          implementation(libs.spring.boot.starter.data.jpa.test)
          implementation(libs.spring.boot.starter.kafka.test)
          implementation(libs.spring.boot.testcontainers)
          implementation(libs.testcontainers.junit.jupiter)
          implementation(libs.testcontainers.postgresql)
          implementation(libs.testcontainers.kafka)
          implementation(libs.assertj.core)
          implementation(libs.awaitility.kotlin)
        }
        targets { all { testTask.configure { shouldRunAfter(test) } } }
      }
  }
}

// Make adapterTest extend main implementation dependencies
configurations.named("adapterTestImplementation") {
  extendsFrom(configurations.implementation.get())
}

configurations.named("adapterTestRuntimeOnly") { extendsFrom(configurations.runtimeOnly.get()) }

tasks.named("check") { dependsOn(testing.suites.named("adapterTest")) }

spotless {
  format("misc") {
    target("**/*.md", "**/*.xml", "**/*.yml", "**/*.yaml", "**/*.html", "**/*.css", ".gitignore")
    targetExclude("**/build/**/*", "**/.idea/**")
    trimTrailingWhitespace()
    endWithNewline()
    leadingTabsToSpaces(2)
  }

  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*")
    ktfmt().googleStyle()
    leadingTabsToSpaces(2)
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  freshmark { target("*.md") }
}
