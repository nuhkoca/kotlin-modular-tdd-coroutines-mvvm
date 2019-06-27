import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Classpaths.gradle_plugin)
        classpath(Classpaths.kotlin_gradle_plugin)
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version Versions.detekt
    id("com.github.ben-manes.versions") version Versions.ben_manes
    id("org.jlleitschuh.gradle.ktlint-idea") version Versions.ktlint
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}

tasks.register("testAll") { dependsOn("clean", "build", "test", "connectedAndroidTest") }

subprojects {
    apply(from = "$rootDir/versions.gradle.kts")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

detekt {
    toolVersion = "1.0.0-RC15"
    input = files("$projectDir")
    config = files("$rootDir/default-detekt-config.yml")
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt-report.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt-report.html")
        }
    }
}

tasks.withType<Detekt> {
    include("**/*.kt")
    include("**/*.kts")
    exclude(".*/resources/.*")
    exclude(".*/build/.*")
    exclude("/versions.gradle.kts")
    exclude("buildSrc/settings.gradle.kts")
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "$buildDir/reports/dependencyUpdates"
    reportfileName = "dependency-report"
}
