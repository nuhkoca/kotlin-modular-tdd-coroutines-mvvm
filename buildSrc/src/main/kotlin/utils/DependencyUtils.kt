package utils

import Config
import java.util.*

fun isNonStable(version: String): Boolean {
    val stableKeyword =
        listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase(Locale.ROOT).contains(it) }
    val regex = Config.BUILD_STABLE_REGEX.toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
