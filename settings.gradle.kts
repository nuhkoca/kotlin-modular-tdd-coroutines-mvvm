plugins {
    `gradle-enterprise`
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
    }
}
