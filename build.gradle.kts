@file:Suppress("UnstableApiUsage")

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.kotlin.jpa)
  alias(libs.plugins.spotless)
  `jvm-test-suite`
  `java-test-fixtures`
}

java { toolchain { languageVersion = JavaLanguageVersion.of(libs.versions.javaVersion.get()) } }

repositories { mavenCentral() }

dependencies {
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.kotlin.reflect)
  implementation(libs.spring.kafka)
  implementation(libs.spring.boot.starter.web)
  runtimeOnly(libs.postgresql)
}

testing {
  suites {
    withType(JvmTestSuite::class).configureEach {
      useJUnitJupiter()
      dependencies {
        implementation(testFixtures(project()))
        implementation(platform(libs.junit.bom))
        implementation(libs.junit.jupiter.api)
        implementation(libs.assertj.core)
        implementation(libs.kotlin.test.junit5)
        runtimeOnly(libs.junit.platform.launcher)
        runtimeOnly(libs.junit.jupiter.engine)
      }
    }

    val test by
      getting(JvmTestSuite::class) {
        dependencies {
          implementation(libs.junit.jupiter.params)
          implementation(libs.approvej.json.jackson)
          implementation(libs.approvej.yaml.jackson)
        }
      }

    val serviceTest by
      registering(JvmTestSuite::class) {
        dependencies {
          implementation(testFixtures(project()))
          implementation(libs.spring.boot.starter.test)
          implementation(libs.spring.kafka.test)
        }
        targets { all { testTask.configure { shouldRunAfter(test) } } }
      }
  }
}

tasks.check { dependsOn(testing.suites.named("serviceTest")) }

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> { useJUnitPlatform() }

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*")
    ktfmt().googleStyle()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }
}
