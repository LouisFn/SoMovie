package plugin

import Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import plugin.util.configureKotlinOptions

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.KOTLIN)
                apply(Plugins.KAPT)
                apply(Plugins.KSP)
            }

            configureKotlinOptions()
        }
    }
}
