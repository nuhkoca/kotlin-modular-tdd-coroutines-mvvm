package plugins

import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask

apply<DokkaPlugin>()

tasks {
    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "${rootProject.rootDir}/docs"

        configuration {
            moduleName = project.parent?.let { parentProject ->
                if (parentProject.name == rootProject.name) {
                    project.name
                } else {
                    "${parentProject.name}/${project.name}"
                }
            } ?: run {
                project.name
            }
        }
    }
}
