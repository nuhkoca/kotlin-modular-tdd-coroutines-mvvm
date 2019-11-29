/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import dependencies.Dependencies
import dependencies.TestDependencies

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
