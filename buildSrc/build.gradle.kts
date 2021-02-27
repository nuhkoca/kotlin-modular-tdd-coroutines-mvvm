plugins {
	`kotlin-dsl`
	`kotlin-dsl-precompiled-script-plugins`
	`java-gradle-plugin`
}

repositories {
	google()
	mavenCentral()
	gradlePluginPortal()
    jcenter()
}

kotlinDslPluginOptions {
	experimentalWarning.set(false)
}

object PluginVersions {
	const val gradle_plugin = "4.1.2"
	const val kotlin_gradle_plugin = "1.4.31"
	const val ben_manes = "0.36.0"
	const val spotless = "5.10.2"
	const val detekt = "1.15.0"
	const val jacoco = "0.16.0"
	const val ktlint = "10.0.0"
	const val dokka = "0.10.0"
}

dependencies {
	implementation("com.android.tools.build:gradle:${PluginVersions.gradle_plugin}")
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersions.kotlin_gradle_plugin}")
	implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginVersions.spotless}")
	implementation("com.github.ben-manes:gradle-versions-plugin:${PluginVersions.ben_manes}")
	implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.detekt}")
	implementation("com.vanniktech:gradle-android-junit-jacoco-plugin:${PluginVersions.jacoco}")
	implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.ktlint}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${PluginVersions.dokka}")
}
