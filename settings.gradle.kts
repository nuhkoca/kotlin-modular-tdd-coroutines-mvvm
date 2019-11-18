include(*Modules.getAllModules().toTypedArray())

rootProject.name = "KotlinTvMaze"
rootProject.buildFileName = "build.gradle.kts"

includeBuild("./buildSrc")
