package plugin

import Plugins
import androidTest
import androidTestImplementation
import coil
import debugImplementation
import getLibsVersionCatalog
import implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import test
import testImplementation

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(Plugins.SOMOVIE_ANDROID_LIBRARY)
                apply(Plugins.SOMOVIE_ANDROID_COMPOSE)
                apply(Plugins.SOMOVIE_ANDROID_HILT)
            }

            val libs = getLibsVersionCatalog()
            dependencies {
                coil(libs)
                test(libs)
                androidTest(libs)

                implementation(project(":core:common"))
                implementation(project(":core:logger"))
                implementation(project(":domain:model"))
                implementation(project(":domain:usecase"))
                implementation(project(":ui:common"))
                implementation(project(":ui:component"))
                implementation(project(":ui:theme"))
                implementation(libs.findLibrary("accompanist.navigationAnimation").get())
                implementation(libs.findLibrary("accompanist.insets.ui").get())
                implementation(libs.findLibrary("androidx.constraintlayout.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtime").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewmodel").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                implementation(libs.findLibrary("coroutines.android").get())

                debugImplementation(libs.findLibrary("androidx.compose.ui.testManifest").get())

                testImplementation(project(":test:fixtures"))
                testImplementation(project(":test:shared"))

                androidTestImplementation(project(":test:fixtures"))
                androidTestImplementation(project(":test:shared"))
                androidTestImplementation(libs.findLibrary("androidx.compose.ui.test").get())
            }
        }
    }
}
