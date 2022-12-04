@file:Suppress("MissingPackageDeclaration")

object Plugins {

    object Versions {
        const val kotlin = "1.7.20"
        const val ksp = "1.7.20-1.0.8"
        const val jvm = "1.7.20"
        const val androidGradle = "7.3.1"
        const val detekt = "1.22.0"
        const val detektCompose = "0.0.26"
        const val spotless = "6.12.0"
        const val ktlint = "0.46.1"
        const val gradleVersionPlugin = "0.44.0"
    }

    // Android
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlin = "kotlin"
    const val kapt = "kapt"
    const val kotlinParcelize = "kotlin-parcelize"
    const val ksp = "com.google.devtools.ksp"
    const val jvm = "org.jetbrains.kotlin.jvm"
    const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${Libraries.Versions.hilt}"
    const val hilt = "dagger.hilt.android.plugin"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val spotless = "com.diffplug.spotless"
    const val testFixtures = "java-test-fixtures"
    const val detektCompose = "com.twitter.compose.rules:detekt:${Versions.detektCompose}"
    const val gradleVersionPlugin = "com.github.ben-manes.versions"
}
