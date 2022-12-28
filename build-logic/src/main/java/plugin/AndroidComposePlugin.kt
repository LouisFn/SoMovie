@file:Suppress("UnstableApiUsage")

package plugin

import debugImplementation
import getLibsVersionCatalog
import implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import plugin.util.getCommonExtension

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibsVersionCatalog()
            val commonExtension = getCommonExtension()

            commonExtension.apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion =
                        libs.findVersion("androidxComposeCompiler").get().toString()
                }
            }

            dependencies {
                implementation(libs.findLibrary("androidx.compose.ui").get())
                implementation(libs.findLibrary("androidx.compose.ui.toolingPreview").get())
                implementation(libs.findLibrary("androidx.compose.material").get())
                implementation(libs.findLibrary("androidx.compose.material.iconsextended").get())
                implementation(libs.findLibrary("hilt.navigationCompose").get())

                debugImplementation(libs.findLibrary("androidx.compose.ui.tooling").get())
            }
        }
    }
}
