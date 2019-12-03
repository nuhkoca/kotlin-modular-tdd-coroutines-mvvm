// Top-level build file where you can add configuration options common to all sub-projects/modules.

@file:Suppress("UnstableApiUsage")

import extensions.applyDefaults
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import plugins.BuildPlugins
import tasks.BuildTasks

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(Classpaths.gradle_plugin)
        classpath(Classpaths.kotlin_gradle_plugin)
    }
}

plugins.apply(BuildPlugins.GIT_HOOKS)
plugins.apply(BuildPlugins.DETEKT)
plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)
plugins.apply(BuildPlugins.KTLINT)

plugins.apply(BuildTasks.STATIC_CHECK)
plugins.apply(BuildTasks.TEST_ALL)

allprojects {
    repositories.applyDefaults()
}

subprojects {
    plugins.apply(BuildPlugins.SPOTLESS)
    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.JACOCO)
    plugins.apply(BuildPlugins.KTLINT)

    apply {
        from("$rootDir/versions.gradle.kts")
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

            maxParallelForks =
                (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        }
    }
}

tasks {
    register("clean", Delete::class) {
        delete = setOf(rootProject.buildDir)
    }
}

/**
 * Usage: <code>./gradlew build -PwarningsAsErrors=true</code>.
 */
fun shouldTreatCompilerWarningsAsErrors(): Boolean {
    return project.findProperty("warningsAsErrors") == "true"
}
