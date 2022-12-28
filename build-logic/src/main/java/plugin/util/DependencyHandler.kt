@file:Suppress("MissingPackageDeclaration", "StringLiteralDuplication")

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

fun Project.getLibsVersionCatalog(): VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

fun DependencyHandler.moshi(libs: VersionCatalog) {
    implementation(libs.findLibrary("moshi.kotlin").get())
    ksp(libs.findLibrary("moshi.kotlin.codegen").get())
}

fun DependencyHandler.paging(libs: VersionCatalog) {
    implementation(libs.findLibrary("androidx.paging.common").get())
    implementation(libs.findLibrary("androidx.paging.runtime").get())
    implementation(libs.findLibrary("androidx.paging.compose").get())
}

fun DependencyHandler.coil(libs: VersionCatalog) {
    implementation(libs.findLibrary("coil.core").get())
    implementation(libs.findLibrary("coil.compose").get())
}

fun DependencyHandler.test(libs: VersionCatalog) {
    testImplementation(project(":test:shared:kotlin"))
    testImplementation(project(":test:testfixtures:kotlin"))

    testImplementation(libs.findLibrary("junit").get())
    testImplementation(libs.findLibrary("kotest.assertions.core").get())
    testImplementation(libs.findLibrary("coroutines.test").get())
}

fun DependencyHandler.androidTest(libs: VersionCatalog) {
    androidTestImplementation(project(":test:shared:kotlin"))
    androidTestImplementation(project(":test:testfixtures:kotlin"))
    androidTestImplementation(project(":test:testfixtures:android"))

    androidTestImplementation(libs.findLibrary("androidx.test.ext.junit").get())
    androidTestImplementation(libs.findLibrary("androidx.test.runner").get())
    androidTestImplementation(libs.findLibrary("androidx.test.core").get())
    androidTestImplementation(libs.findLibrary("kotest.assertions.core").get())
    androidTestImplementation(libs.findLibrary("coroutines.test").get())
}

internal fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

internal fun DependencyHandler.debugImplementation(dependencyNotation: Any) {
    add("debugImplementation", dependencyNotation)
}

internal fun DependencyHandler.kapt(dependencyNotation: Any) {
    add("kapt", dependencyNotation)
}

internal fun DependencyHandler.ksp(dependencyNotation: Any) {
    add("ksp", dependencyNotation)
}

internal fun DependencyHandler.api(dependencyNotation: Any) {
    add("api", dependencyNotation)
}

internal fun DependencyHandler.testImplementation(dependencyNotation: Any) {
    add("testImplementation", dependencyNotation)
}

internal fun DependencyHandler.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}
