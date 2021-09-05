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
import com.android.build.gradle.internal.dsl.BuildType
import commons.addTestDependencies
import dependencies.Dependencies
import dependencies.TestDependencies
import extensions.setDefaults

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.kotlinParcelize)
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
        this as NamedDomainObjectContainer<BuildType>
        Release.create(this, project)
        Debug.create(this, project)
    }

    sourceSets {
        this as NamedDomainObjectContainer<AndroidSourceSet>
        Main.create(this)
        TTest.create(this)
        AndroidTest.create(this)
    }

    packagingOptions {
        resources {
            pickFirsts.add("mockito-extensions/org.mockito.plugins.MockMaker")
        }
    }

    junitJacoco {
        jacocoVersion = "0.8.3"
        excludes = listOf("jdk.internal.*")
        includeNoLocationClasses = false
        includeInstrumentationCoverageInMergedReport = false
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
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
    api(project(Modules.data))
    implementation(project(Modules.domain))

    implementation(Dependencies.material)
    implementation(Dependencies.constraint_layout)
    implementation(Dependencies.core_ktx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.activity_ktx)

    implementation(Dependencies.coroutines_core)

    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_viewmodel_ktx)
    implementation(Dependencies.lifecycle_livedata_ktx)
    implementation(Dependencies.lifecycle_java8)
    kapt(Dependencies.lifecycle_compiler)

    implementation(Dependencies.dagger)
    implementation(Dependencies.dagger_android)
    compileOnly(Dependencies.javax_annotation)
    kapt(Dependencies.dagger_android_processor)
    kapt(Dependencies.dagger_compiler)

    implementation(Dependencies.timber)

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
