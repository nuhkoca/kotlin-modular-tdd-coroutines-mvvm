plugins {
	`kotlin-dsl`
	`kotlin-dsl-precompiled-script-plugins`
	`java-gradle-plugin`
}

repositories {
	google()
	jcenter()
	mavenCentral()
	maven("https://oss.sonatype.org/content/repositories/snapshots")
	maven("https://plugins.gradle.org/m2/")
	maven("https://ci.android.com/builds/submitted/5837096/androidx_snapshot/latest/repository")
}

kotlinDslPluginOptions {
	experimentalWarning.set(false)
}

object PluginVersions {
	const val gradle_plugin = "3.5.2"
	const val kotlin_gradle_plugin = "1.3.61"
	const val ben_manes = "0.27.0"
	const val spotless = "3.26.0"
	const val detekt = "1.2.0"
	const val gradle_enterprise = "3.1"
	const val jacoco = "0.16.0-SNAPSHOT"
	const val ktlint = "9.1.1"
}

dependencies {
	implementation("com.android.tools.build:gradle:${PluginVersions.gradle_plugin}")
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersions.kotlin_gradle_plugin}")
	implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginVersions.spotless}")
	implementation("com.github.ben-manes:gradle-versions-plugin:${PluginVersions.ben_manes}")
	implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.detekt}")
	implementation("com.gradle:gradle-enterprise-gradle-plugin:${PluginVersions.gradle_enterprise}")
	implementation("com.vanniktech:gradle-android-junit-jacoco-plugin:${PluginVersions.jacoco}")
	implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.ktlint}")
}
