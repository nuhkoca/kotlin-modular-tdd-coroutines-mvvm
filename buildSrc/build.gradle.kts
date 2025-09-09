plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

object PluginVersions {
    const val gradle_plugin = "8.13.0"
    const val kotlin_gradle_plugin = "2.2.10"
    const val ben_manes = "0.52.0"
    const val spotless = "7.2.1"
    const val detekt = "1.23.8"
    const val ktlint = "13.1.0"
    const val dokka = "2.0.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginVersions.gradle_plugin}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersions.kotlin_gradle_plugin}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginVersions.spotless}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginVersions.ben_manes}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.detekt}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.ktlint}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${PluginVersions.dokka}")
}
