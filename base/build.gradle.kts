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
import com.android.build.api.dsl.AndroidSourceSet
import dependencies.Dependencies
import extensions.setDefaults
import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.kotlinParcelize)
}

val javaVersion: JavaVersion by extra { VERSION_1_8 }

android {
    compileSdkVersion(extra["compileSdkVersion"] as Int)
    defaultConfig {
        minSdkVersion(extra["minSdkVersion"] as Int)
        targetSdkVersion(extra["targetSdkVersion"] as Int)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        this as NamedDomainObjectContainer<AndroidSourceSet>
        Main.create(this)
        TTest.create(this)
        AndroidTest.create(this)
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    lintOptions.setDefaults(file("lint.xml"))
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api(Dependencies.kotlin)
    implementation(Dependencies.material)
    implementation(Dependencies.activity_ktx)

    implementation(Dependencies.coroutines_core)
    api(Dependencies.coroutines_android)

    implementation(Dependencies.lifecycle_runtime)
    kapt(Dependencies.lifecycle_compiler)

    implementation(Dependencies.timber)

    implementation(Dependencies.glide)
    implementation(Dependencies.glide_okhttp)
    kapt(Dependencies.glide_compiler)
    kapt(Dependencies.android_annotation)

    api(Dependencies.dagger_android_support)
}
