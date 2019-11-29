plugins {
	id("com.gradle.enterprise") version "3.1"
}

include(":app")
include(":base")
include(":data")
include(":domain")
include(":test-shared")

rootProject.name = "KotlinTvMaze"
rootProject.buildFileName = "build.gradle.kts"

gradleEnterprise {
	buildScan {
		termsOfServiceUrl = "https://gradle.com/terms-of-service"
		termsOfServiceAgree = "yes"

		/* This is not good to run for every build
    	publishAlways()
     	*/
	}
}
