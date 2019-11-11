@file:Suppress("UnstableApiUsage")

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

    tasks {
        withType<JavaCompile> {
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

        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = javaVersion.toString()
                allWarningsAsErrors = true
            }
        }

        withType<Test> {
            testLogging {
                events("started", "skipped", "passed", "failed")
                setExceptionFormat("full")
                showStandardStreams = true
            }
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
            destination = file("$buildDir/reports/detekt/detekt-report.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt-report.html")
        }
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

jacoco {
    toolVersion = "0.8.5"
    reportsDir = file("$buildDir/jacoco/report.xml")
}

tasks {
    register("clean", Delete::class) {
        delete = setOf(rootProject.buildDir)
    }

    withType<Detekt> {
        include("**/*.kt")
        include("**/*.kts")
        exclude(".*/resources/.*")
        exclude(".*/build/.*")
        exclude("/versions.gradle.kts")
        exclude("buildSrc/settings.gradle.kts")
    }

    named<DependencyUpdatesTask>("dependencyUpdates") {
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

    withType<JacocoReport> {
        reports {
            xml.isEnabled = false
            csv.isEnabled = false
            html.isEnabled = true
            html.destination = file("$buildDir/jacoco/reports.html")
        }
    }

    withType<JacocoCoverageVerification> {
        violationRules {
            rule {
                limit {
                    minimum = "0.5".toBigDecimal()
                }
            }
        }
    }

    withType<Test> {
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
        }
    }
}
