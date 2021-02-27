import extensions.applyDefaults
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import plugins.BuildPlugins
import tasks.BuildTasks

val javaVersion: JavaVersion by extra { VERSION_1_8 }

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
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
    plugins.apply(BuildPlugins.DOKKA)

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
                events = setOf(FAILED, STARTED, PASSED, SKIPPED, STANDARD_OUT)
                exceptionFormat = FULL
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

fun shouldTreatCompilerWarningsAsErrors(): Boolean {
    return project.findProperty("warningsAsErrors") == "true"
}
