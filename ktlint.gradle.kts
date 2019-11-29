import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import dependencies.Versions
import dependencies.KtlintRuleset

ktlint {
    version.set(Versions.ktlint_internal)
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    outputColorName.set(Config.KTLINT_COLOR_NAME)
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    disabledRules.set(setOf("final-newline"))
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)

        customReporters {
            register("csv") {
                fileExtension = "csv"
                dependency = project(":project-reporters:csv-reporter")
            }
            register("yaml") {
                fileExtension = "yml"
                dependency = "com.example:ktlint-yaml-reporter:1.0.0"
            }
        }
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

dependencies {
    ktlintRuleset(KtlintRuleset.ruleset)
    ktlintRuleset(files(KtlintRuleset.ruleset_files))
    ktlintRuleset(project(KtlintRuleset.ruleset_project))
}
