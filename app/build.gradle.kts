plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinAndroidExtension)
    kotlin(Plugins.kotlinKapt)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

android {
    compileSdkVersion(extra["compileSdkVersion"] as Int)
    defaultConfig {
        applicationId = "com.mobilemovement.kotlintvmaze"
        minSdkVersion(extra["minSdkVersion"] as Int)
        targetSdkVersion(extra["targetSdkVersion"] as Int)
        versionCode = 1
        versionName = getSemanticAppVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isTestCoverageEnabled = false
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isTestCoverageEnabled = true
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

    packagingOptions {
        pickFirst("mockito-extensions/org.mockito.plugins.MockMaker")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    androidExtensions {
        isExperimental = true
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

    configurations {
        all {
            exclude(mapOf("group" to "com.google.guava", "module" to "listenablefuture"))
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(project(Modules.base))
    api(project(Modules.data))
    implementation(project(Modules.domain))

    implementation(Dependencies.material)
    implementation(Dependencies.constraint_layout)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)

    implementation(Dependencies.coroutines_core)

    implementation(Dependencies.lifecycle_extensions)
    implementation(Dependencies.lifecycle_viewmodel_ktx)
    kapt(Dependencies.lifecycle_compiler)

    implementation(Dependencies.dagger)
    implementation(Dependencies.dagger_android)
    compileOnly(Dependencies.javax_annotation)
    kapt(Dependencies.dagger_android_processor)
    kapt(Dependencies.dagger_compiler)

    implementation(Dependencies.timberkt)

    implementation(Dependencies.glide)

    testImplementation(TestDependencies.test_core)
    testImplementation(TestDependencies.runner)
    testImplementation(TestDependencies.rules)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.truth_ext)
    testImplementation(TestDependencies.espresso_core)
    testImplementation(TestDependencies.mockK)
    testImplementation(TestDependencies.arch_core)
    testImplementation(TestDependencies.coroutines_core)

    androidTestImplementation(TestDependencies.test_core)
    androidTestImplementation(TestDependencies.rules)
    androidTestImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.truth_ext)
    androidTestImplementation(TestDependencies.espresso_core)
}

fun getSemanticAppVersionName(): String {
    val majorCode = 1
    val minorCode = 0
    val patchCode = 0

    return "$majorCode.$minorCode.$patchCode"
}
