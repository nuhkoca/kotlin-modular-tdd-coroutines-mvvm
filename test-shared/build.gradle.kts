plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

android {
    compileSdkVersion(extra["compileSdkVersion"] as Int)
    defaultConfig {
        minSdkVersion(extra["minSdkVersion"] as Int)
        targetSdkVersion(extra["targetSdkVersion"] as Int)
    }
    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        maybeCreate("debug").apply {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    sourceSets {
        named("main").configure {
            java.srcDirs(file("src/main/kotlin"))
        }
        named("test").configure {
            java.srcDirs(file("src/test/kotlin"))
        }
        named("androidTest").configure {
            java.srcDirs(file("src/androidTest/kotlin"))
        }
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    lintOptions {
        isAbortOnError = false
        isWarningsAsErrors = true
        isCheckDependencies = true
        isIgnoreTestSources = true
        setLintConfig(file("lint.xml"))
        disable("GradleDeprecated")
        disable("OldTargetApi")
        disable("GradleDependency")
    }
}

dependencies {
    implementation(Dependencies.kotlin)

    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_extensions)

    implementation(TestDependencies.junit)
    implementation(TestDependencies.rules)
    api(TestDependencies.coroutines_core)
}
