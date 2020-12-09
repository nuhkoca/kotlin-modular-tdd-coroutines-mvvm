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
package commons

import AndroidTest
import Main
import Modules
import TTest
import dependencies.Dependencies
import extensions.setDefaults
import org.gradle.api.JavaVersion.*

val javaVersion: JavaVersion by extra { VERSION_1_8 }

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(extra["compileSdkVersion"] as Int)
    defaultConfig {
        minSdkVersion(extra["minSdkVersion"] as Int)
        targetSdkVersion(extra["targetSdkVersion"] as Int)
    }

    sourceSets {
        Main.create(this)
        TTest.create(this)
        AndroidTest.create(this)
    }

    packagingOptions {
        pickFirst("mockito-extensions/org.mockito.plugins.MockMaker")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    lintOptions.setDefaults(file("lint.xml"))
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(project(Modules.base))

    kapt(Dependencies.dagger_compiler)

    addTestDependencies()
}
