package plugin

import Plugins
import getLibsVersionCatalog
import implementation
import kapt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.HILT)
                apply(Plugins.KAPT)
            }

            val libs = getLibsVersionCatalog()
            dependencies {
                implementation(libs.findLibrary("hilt.android").get())
                kapt(libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}
