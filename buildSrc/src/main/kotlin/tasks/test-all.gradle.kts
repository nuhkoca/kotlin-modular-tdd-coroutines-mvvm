package tasks

task("testAll") {
	description = "Runs all the tests per modules."

	group = "verification"

	afterEvaluate {
		"${subprojects.forEach {
			dependsOn(
				":${it.name}:test",
				":${it.name}:connectedAndroidTest"
			)
		}}"
	}
}
