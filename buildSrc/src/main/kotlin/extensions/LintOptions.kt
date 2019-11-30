package extensions

import com.android.build.gradle.internal.dsl.LintOptions
import java.io.File

fun LintOptions.setDefaults(lintFile: File) {
    isAbortOnError = false
    isWarningsAsErrors = true
    isCheckDependencies = true
    isIgnoreTestSources = true
    lintConfig = lintFile
    disable("GradleDeprecated")
    disable("OldTargetApi")
    disable("GradleDependency")
}
