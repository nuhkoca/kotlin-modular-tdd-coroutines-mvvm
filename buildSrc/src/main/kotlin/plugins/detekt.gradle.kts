package plugins

import dependencies.Versions
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.detekt
import utils.userHome

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

apply<DetektPlugin>()

detekt {
	toolVersion = Versions.detekt
	baseline = file("${project.rootDir}/config/detekt/baseline.xml")
	input = files("${project.projectDir}")
	config = files("${project.rootDir}/default-detekt-config.yml")

	reports {
		xml {
			enabled = true
			destination = file("${project.buildDir}/reports/detekt/detekt-report.xml")
		}
		html {
			enabled = true
			destination = file("${project.buildDir}/reports/detekt/detekt-report.html")
		}
	}

	idea {
		path = "$userHome/.idea"
		codeStyleScheme = "$userHome/.idea/idea-code-style.xml"
		inspectionsProfile = "$userHome/.idea/inspect.xml"
		report = "${project.projectDir}/reports"
		mask = "*.kt"
	}
}

tasks {
	withType<Detekt> {
		include("**/*.kt")
		include("**/*.kts")
		exclude(".*/resources/.*")
		exclude("**/build/**")

		jvmTarget = javaVersion.toString()
	}
}

val detektFormat by tasks.registering(Detekt::class) {
	description = "Reformats whole code base."
	parallel = true
	disableDefaultRuleSets = true
	buildUponDefaultConfig = true
	autoCorrect = true
	setSource(files(project.projectDir))
	include("**/*.kt")
	include("**/*.kts")
	exclude("**/resources/**")
	exclude("**/build/**")
	config.setFrom(files("${project.rootDir}/config/detekt/format.yml"))
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
	setSource(files(project.projectDir))
	include("**/*.kt")
	include("**/*.kts")
	exclude("**/resources/**")
	exclude("**/build/**")
	baseline.set(file("${project.rootDir}/config/detekt/baseline.xml"))
	reports {
		xml.enabled = false
		html.enabled = false
		txt.enabled = false
	}
}
