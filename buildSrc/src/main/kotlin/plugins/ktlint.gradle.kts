package plugins

import Config
import dependencies.Versions
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

apply<KtlintPlugin>()

configure<KtlintExtension> {
	version.set(Versions.ktlint_internal)
	android.set(true)
	outputToConsole.set(true)
	outputColorName.set(Config.KTLINT_COLOR_NAME)
	enableExperimentalRules.set(true)
	additionalEditorconfigFile.set(file("${project.rootDir}/.editorconfig"))
	reporters {
		reporter(ReporterType.HTML)
	}
	filter {
		exclude("**/generated/**")
		include("**/kotlin/**")
	}
}
