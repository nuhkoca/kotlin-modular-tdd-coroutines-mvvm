object Plugins {
    // Core
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "android"
    const val kotlinAndroidExtension = "android.extensions"
    const val kotlinKapt = "kapt"

    // Third party plugins
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ben_manes = "com.github.ben-manes.versions"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val jacoco = "jacoco"

    object Buildscan {
        const val TERMS_OF_SERVICE_URL = "https://gradle.com/terms-of-service"
        const val TERMS_OF_SERVICE_AGREE = "yes"
    }
}
