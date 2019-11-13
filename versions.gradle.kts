val compileSdkVersion: String by project
val minSdkVersion: String by project
val targetSdkVersion: String by project

extra.apply {
    this["compileSdkVersion"] = compileSdkVersion.toInt()
    this["minSdkVersion"] = minSdkVersion.toInt()
    this["targetSdkVersion"] = targetSdkVersion.toInt()
}
