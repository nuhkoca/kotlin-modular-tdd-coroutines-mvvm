// Top-level build file where you can add configuration options common to all sub-projects/modules.

@file:Suppress("UnstableApiUsage")

import Plugins.Buildscan
import extensions.applyDefaults
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.detekt
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }
val userHome: String = System.getProperty("user.home")

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
    id(Plugins.detekt) version Versions.detekt
    id(Plugins.ktlint) version Versions.ktlint
    `build-scan`
    jacoco
}

plugins.apply(BuildPlugins.GIT_HOOKS)
plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)
plugins.apply(BuildPlugins.STATIC_CHECK)

allprojects {
    repositories.applyDefaults()
}

task("testAll") {
    description = "Runs all the tests per modules."

    group = "verification"

    afterEvaluate {
        "${subprojects.forEach {
            dependsOn(
                ":${it.name}:test",
                ":${it.name}:connectedAndroidTest"
            )
        }}"
    }
}

subprojects {
    plugins.apply(BuildPlugins.SPOTLESS)

    apply {
        from("$rootDir/versions.gradle.kts")
        plugin(Plugins.ktlint)
        plugin(Plugins.detekt)
        plugin(Plugins.jacoco)
    }

    ktlint {
        debug.set(true)
    }

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
                // https://youtrack.jetbrains.com/issue/KT-24946
                kotlinOptions.freeCompilerArgs = listOf(
                    "-progressive",
                    "-Xskip-runtime-version-check",
                    "-Xdisable-default-scripting-plugin",
                    "-Xuse-experimental=kotlin.Experimental"
                )
                kotlinOptions.allWarningsAsErrors = shouldTreatCompilerWarningsAsErrors()
            }
        }

        withType<Test> {
            testLogging {
                // set options for log level LIFECYCLE
                events = setOf(
                    TestLogEvent.FAILED,
                    TestLogEvent.STARTED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_OUT
                )
                exceptionFormat = TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }

            configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
            }

            maxParallelForks =
                (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        }
    }
}

detekt {
    toolVersion = Versions.detekt
    baseline = file("$rootDir/config/detekt/baseline.xml")
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

    idea {
        path = "$userHome/.idea"
        codeStyleScheme = "$userHome/.idea/idea-code-style.xml"
        inspectionsProfile = "$userHome/.idea/inspect.xml"
        report = "project.projectDir/reports"
        mask = "*.kt"
    }
}

buildScan {
    termsOfServiceUrl = Buildscan.TERMS_OF_SERVICE_URL
    termsOfServiceAgree = Buildscan.TERMS_OF_SERVICE_AGREE

    /* This is not good to run for every build
    publishAlways()
     */
}

jacoco {
    toolVersion = Versions.jacoco
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
        exclude("**/build/**")

        jvmTarget = javaVersion.toString()
    }

    withType<JacocoReport> {
        reports {
            xml.isEnabled = false
            csv.isEnabled = false
            html.isEnabled = true
            html.destination = file("$buildDir/reports/jacoco/report.html")
        }

        afterEvaluate {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree(it).apply {
                    exclude(
                        "**/*_Provide*/**",
                        "**/*_Factory*/**",
                        "**/*_MembersInjector.class",
                        "**/*Dagger*"
                    )
                }
            }))
        }
    }

    withType<JacocoCoverageVerification> {
        violationRules {
            rule {
                limit {
                    minimum = Config.MIN_COVERAGE_LIMIT.toBigDecimal()
                }
            }
        }

        afterEvaluate {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree(it).apply {
                    exclude(
                        "**/*_Provide*/**",
                        "**/*_Factory*/**",
                        "**/*_MembersInjector.class",
                        "**/*Dagger*"
                    )
                }
            }))
        }
    }
}

/**
 * Usage: <code>./gradlew build -PwarningsAsErrors=true</code>.
 */
fun shouldTreatCompilerWarningsAsErrors(): Boolean {
    return project.findProperty("warningsAsErrors") == "true"
}

val detektFormat by tasks.registering(Detekt::class) {
    description = "Reformats whole code base."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("$rootDir/config/detekt/format.yml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs over whole code base without the starting overhead for each module."
    parallel = true
    buildUponDefaultConfig = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}
