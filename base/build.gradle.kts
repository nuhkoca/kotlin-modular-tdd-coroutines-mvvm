import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinAndroidExtension)
    kotlin(Plugins.kotlinKapt)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

android {
    compileSdkVersion(extra["compileSdkVersion"] as Int)
    defaultConfig {
        minSdkVersion(extra["minSdkVersion"] as Int)
        targetSdkVersion(extra["targetSdkVersion"] as Int)
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
    implementation(Dependencies.kotlin)
    implementation(Dependencies.material)

    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)

    implementation(Dependencies.lifecycle_extensions)

    implementation(Dependencies.timber)

    implementation(Dependencies.glide)
    implementation(Dependencies.glide_okhttp) {
        exclude(mapOf("group" to "glide-parent"))
    }
    kapt(Dependencies.glide_compiler)
    kapt(Dependencies.android_annotation)

    implementation(Dependencies.dagger_android_support)
}
