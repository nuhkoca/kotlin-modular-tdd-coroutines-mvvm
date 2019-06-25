import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

ktlint {
    version.set("0.22.0")
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    reporters.set(setOf(ReporterType.PLAIN, ReporterType.CHECKSTYLE))
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    additionalEditorconfigFile.set(file("/some/additional/.editorconfig"))
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

val ktlintRuleset by configurations
dependencies {
    ktlintRuleset("com.github.username:rulseset:master-SNAPSHOT")
    ktlintRuleset(files("/path/to/custom/rulseset.jar"))
    ktlintRuleset(project(":chore:project-ruleset"))
}
