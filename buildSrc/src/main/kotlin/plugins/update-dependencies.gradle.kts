package plugins

import Config
import com.github.benmanes.gradle.versions.VersionsPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import utils.isNonStable

apply<VersionsPlugin>()

tasks {
    withType<DependencyUpdatesTask> {
        resolutionStrategy {
            componentSelection {
                all {
                    if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                        reject("Release candidate")
                    }
                }
            }
        }

        checkForGradleUpdate = true
        outputFormatter = Config.JSON_OUTPUT_FORMATTER
        reportfileName = "dependency-report"
        outputDir = "${project.buildDir}/reports/dependencyUpdates"
    }
}
