package plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin

apply<SpotlessPlugin>()

@Suppress("INACCESSIBLE_TYPE")
configure<SpotlessExtension> {
    format("misc") {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.md", "**/.gitignore", "**/*.yaml", "**/*.yml"),
                    "exclude" to listOf(
                        ".gradle/**",
                        ".gradle-cache/**",
                        "**/tools/**",
                        "**/build/**"
                    )
                )
            )
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    format("xml") {
        target("**/res/**/*.xml")
        targetExclude("**/build/**")
        indentWithSpaces(4)
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlin {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.kt"),
                    "exclude" to listOf("**/build/**", "**/buildSrc/**", "**/.*")
                )
            )
        )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.kt"),
            "^(package|object|import|interface)"
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    java {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.java"),
                    "exclude" to listOf("**/build/**", "**/buildSrc/**", "**/.*")
                )
            )
        )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.java"),
            "^(package|object|import|interface)"
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }

    kotlinGradle {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.gradle.kts", "*.gradle.kts"),
                    "exclude" to listOf("**/build/**")
                )
            )
        )
        licenseHeaderFile(
            rootProject.file("spotless/copyright.kt"),
            "package|import|tasks|apply|plugins|include|val|object|interface"
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}
