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
import commons.addTestDependencies
import dependencies.Dependencies
import dependencies.TestDependencies
import extensions.setDefaults

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
        Release.create(this, project)
        Debug.create(this, project)
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

    lintOptions.setDefaults(file("lint.xml"))
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

    androidTestImplementation(TestDependencies.test_core)
    androidTestImplementation(TestDependencies.rules)
    androidTestImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.truth_ext)
    androidTestImplementation(TestDependencies.espresso_core)

    addTestDependencies()
}

fun getSemanticAppVersionName(): String {
    val majorCode = 1
    val minorCode = 0
    val patchCode = 0

    return "$majorCode.$minorCode.$patchCode"
}
