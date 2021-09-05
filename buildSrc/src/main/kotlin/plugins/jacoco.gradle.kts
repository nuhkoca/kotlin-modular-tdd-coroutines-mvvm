package plugins

import com.vanniktech.android.junit.jacoco.JunitJacocoExtension
import dependencies.Versions

plugins.apply("com.vanniktech.android.junit.jacoco")

configure<JunitJacocoExtension> {
	jacocoVersion = Versions.jacoco
	excludes = listOf(
		"**/*_Provide*/**",
		"**/*_Factory*/**",
		"**/*_MembersInjector.class",
		"**/*Dagger*",
        "jdk.internal.*"
	)
	includeNoLocationClasses = true
	includeInstrumentationCoverageInMergedReport = false
}
