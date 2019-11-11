import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

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
    `build-scan`
    jacoco
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

val testAll by tasks.registering {
    group = "verification"
    description = "Runs all the tests."

    "${subprojects.forEach {
        dependsOn(":${it.name}:clean", ":${it.name}:build", ":${it.name}:test")
    }}"
}

subprojects {
    apply(from = "$rootDir/versions.gradle.kts")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    tasks.withType<JavaCompile> {
        options.isIncremental = true
        allprojects {
            options.compilerArgs.addAll(
                arrayOf(
                    "-Xlint:-unchecked",
                    "-Xlint:deprecation",
                    "-Xdiags:verbose"
                )
            )
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
            allWarningsAsErrors = true
        }
    }

    tasks.withType<Test> {
        testLogging {
            events("started", "skipped", "passed", "failed")
            setExceptionFormat("full")
            showStandardStreams = true
        }
    }
}

detekt {
    toolVersion = Versions.detekt
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
    resolutionStrategy {
        componentSelection {
            all {
                val rejected = listOf(
                    "alpha",
                    "beta",
                    "rc",
                    "cr",
                    "m",
                    "preview",
                    "b",
                    "ea"
                ).any { qualifier ->
                    candidate.version.matches(Regex("(?i).*[.-]$qualifier[.\\d-+]*"))
                }
                if (rejected) {
                    reject("Release candidate")
                }
            }
        }
    }

    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "$buildDir/reports/dependencyUpdates"
    reportfileName = "dependency-report"
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
