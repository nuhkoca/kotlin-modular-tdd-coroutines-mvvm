package extensions

import org.gradle.api.artifacts.dsl.RepositoryHandler

fun RepositoryHandler.applyDefaults() {
    google()
    mavenCentral()
    gradlePluginPortal()
    jcenter()
}
