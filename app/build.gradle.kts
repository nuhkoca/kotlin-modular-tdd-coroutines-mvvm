import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinAndroidExtension)
    kotlin(Plugins.kotlinKapt)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }
val baseUrl: String by project

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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    buildTypes.forEach {
        it.buildConfigField("String", "baseUrl", baseUrl)
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
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

    tasks.withType<JavaCompile> {
        options.isIncremental = true
        allprojects {
            options.compilerArgs.addAll(arrayOf("-Xlint:-unchecked", "-Xlint:deprecation", "-Xdiags:verbose"))
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
            allWarningsAsErrors = true
        }
    }

    configurations {
        all {
            exclude(mapOf("group" to "com.google.guava", "module" to "listenablefuture"))
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    implementation(project(Modules.base))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.material)
    implementation(Dependencies.constraint_layout)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)

    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)

    implementation(Dependencies.lifecycle_extensions)
    implementation(Dependencies.lifecycle_viewmodel)
    implementation(Dependencies.lifecycle_viewmodel_ktx)
    kapt(Dependencies.lifecycle_compiler)

    implementation(Dependencies.dagger)
    implementation(Dependencies.dagger_android)
    implementation(Dependencies.dagger_android_support)
    compileOnly(Dependencies.javax_annotation)
    kapt(Dependencies.dagger_android_processor)
    kapt(Dependencies.dagger_compiler)

    implementation(Dependencies.timber)
    implementation(Dependencies.timberkt)

    implementation(Dependencies.gson)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.logging)

    testImplementation(TestDependencies.test_core)
    testImplementation(TestDependencies.runner)
    testImplementation(TestDependencies.rules)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.truth_ext)
    testImplementation(TestDependencies.truth)
    testImplementation(TestDependencies.espresso_core)
    testImplementation(TestDependencies.mockito)
    testImplementation(TestDependencies.arch_core)

    androidTestImplementation(TestDependencies.test_core)
    androidTestImplementation(TestDependencies.rules)
    androidTestImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.truth) {
        exclude(mapOf("group" to "com.google.guava", "module" to "listenablefuture"))
    }
    androidTestImplementation(TestDependencies.truth_ext) {
        exclude(mapOf("group" to "com.google.guava", "module" to "listenablefuture"))
    }
    androidTestImplementation(TestDependencies.espresso_core)
}

fun getSemanticAppVersionName(): String {
    val majorCode = 1
    val minorCode = 0
    val patchCode = 0

    return "$majorCode.$minorCode.$patchCode"
}
