package extensions

import com.android.build.api.dsl.Lint
import java.io.File

fun Lint.setDefaults(lintFile: File) {
    abortOnError = false
    warningsAsErrors = true
    checkDependencies = true
    ignoreTestSources = true
    lintConfig = lintFile
    disable.add("GradleDeprecated")
    disable.add("OldTargetApi")
    disable.add("GradleDependency")
}
