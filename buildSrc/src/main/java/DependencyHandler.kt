@file:Suppress("MissingPackageDeclaration", "StringLiteralDuplication")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.feature() {
    compose()
    hilt()
    coil()
    test()
    androidTest()

    implementation(project(":common"))
    implementation(project(":domain:model"))
    implementation(project(":domain:usecase"))
    implementation(project(":ui:common"))
    implementation(project(":ui:component"))
    implementation(project(":ui:theme"))

    implementation(Libraries.Accompanist.navigationAnimation)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.Coroutines.android)
    implementation(Libraries.Accompanist.insetsui)
    implementation(Libraries.Lifecycle.runtime)
    implementation(Libraries.Lifecycle.viewmodel)
    implementation(Libraries.Lifecycle.viewmodelCompose)

    debugImplementation(Libraries.Compose.testManifest)

    testImplementation(project(":test:testfixtures:kotlin"))
    testImplementation(project(":test:testfixtures:android"))
    testImplementation(project(":test:shared:android"))

    androidTestImplementation(project(":test:testfixtures:kotlin"))
    androidTestImplementation(project(":test:testfixtures:android"))
    androidTestImplementation(project(":test:shared:android"))
    androidTestImplementation(Libraries.Compose.test)
}

fun DependencyHandler.compose() {
    implementation(Libraries.Compose.ui)
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.uiToolingPreview)
    implementation(Libraries.Compose.icons)
    debugImplementation(Libraries.Compose.uiTooling)
}

fun DependencyHandler.hilt() {
    implementation(Libraries.Hilt.android)
    implementation(Libraries.Hilt.compose)
    kapt(Libraries.Hilt.compiler)
}

fun DependencyHandler.paging() {
    implementation(Libraries.Paging.common)
    implementation(Libraries.Paging.runtime)
    implementation(Libraries.Paging.compose)
}

fun DependencyHandler.coil() {
    implementation(Libraries.Coil.core)
    implementation(Libraries.Coil.compose)
}

fun DependencyHandler.room() {
    implementation(Libraries.Room.runtime)
    implementation(Libraries.Room.ktx)
    implementation(Libraries.Room.paging)
    ksp(Libraries.Room.compiler)

    androidTestImplementation(Libraries.Room.test)
}

fun DependencyHandler.moshi() {
    implementation(Libraries.Moshi.kotlin)
    ksp(Libraries.Moshi.codegen)
}

fun DependencyHandler.flipper() {
    debugImplementation(Libraries.Flipper.core)
    debugImplementation(Libraries.Flipper.soloader)
    debugImplementation(Libraries.Flipper.network)
}

fun DependencyHandler.test() {
    testImplementation(project(":test:shared:kotlin"))
    testImplementation(project(":test:testfixtures:kotlin"))

    testImplementation(Libraries.jUnit)
    testImplementation(Libraries.Kotest.core)
    testImplementation(Libraries.Coroutines.test)
}

fun DependencyHandler.androidTest() {
    androidTestImplementation(project(":test:shared:kotlin"))
    androidTestImplementation(project(":test:testfixtures:kotlin"))
    androidTestImplementation(project(":test:testfixtures:android"))

    androidTestImplementation(Libraries.AndroidTest.runner)
    androidTestImplementation(Libraries.AndroidTest.core)
    androidTestImplementation(Libraries.AndroidTest.junit)
    androidTestImplementation(Libraries.Kotest.core)
    androidTestImplementation(Libraries.Coroutines.test)
}

private fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

private fun DependencyHandler.debugImplementation(dependencyNotation: Any) {
    add("debugImplementation", dependencyNotation)
}

private fun DependencyHandler.kapt(dependencyNotation: Any) {
    add("kapt", dependencyNotation)
}

private fun DependencyHandler.ksp(dependencyNotation: Any) {
    add("ksp", dependencyNotation)
}

private fun DependencyHandler.api(dependencyNotation: Any) {
    add("api", dependencyNotation)
}

private fun DependencyHandler.testImplementation(dependencyNotation: Any) {
    add("testImplementation", dependencyNotation)
}

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}
