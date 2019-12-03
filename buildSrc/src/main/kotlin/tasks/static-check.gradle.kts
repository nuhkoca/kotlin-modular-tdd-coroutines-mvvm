package tasks

// Credit: https://github.com/igorwojda/android-showcase/blob/master/build.gradle.kts
task("staticCheck") {
    description = """Mimics all static checks that run on CI.
        Note that this task is intended to run locally (not on CI), because on CI we prefer to 
        have parallel execution and separate reports for each check (multiple statuses 
        eg. on github PR page).
    """.trimMargin()

    group = "verification"
    afterEvaluate {
        // Filter modules with "lintDebug" task (non-Android modules do not have lintDebug task)
        val lintTasks = subprojects.mapNotNull { "${it.name}:lintDebug" }

        // Get modules with "testDebugUnitTest" task (app module does not have it)
        val testTasks = subprojects.mapNotNull { "${it.name}:testDebugUnitTest" }

        // All task dependencies
        val taskDependencies =
            mutableListOf(
                "app:assembleAndroidTest",
                "ktlintCheck",
                "detekt"
            ).also {
                it.addAll(lintTasks)
                it.addAll(testTasks)
            }

        // By defining Gradle dependency all dependent tasks will run before this "empty" task
        dependsOn(taskDependencies)
    }
}
