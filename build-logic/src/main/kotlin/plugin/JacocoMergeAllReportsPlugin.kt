package plugin

import Plugins
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import getLibsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import plugin.util.configureJacoco

class JacocoMergeAllReportsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.JACOCO)
            }

            val libs = getLibsVersionCatalog()
            configure<JacocoPluginExtension> {
                toolVersion = libs.findVersion("jacoco").get().toString()
            }

            afterEvaluate {
                val rootProject = this
                val rootSubProjects = subprojects
                rootSubProjects
                    .first { it.name == "app" }
                    .afterEvaluate {
                        extensions.getByType<ApplicationAndroidComponentsExtension>().onVariants {
                            val variantName = it.name
                            rootProject.tasks.create(
                                "jacocoMergeAll${variantName.capitalized()}Reports",
                                JacocoReport::class
                            ) {
                                group = "Reporting"
                                description =
                                    "Generate overall Jacoco coverage report for the $variantName build."

                                configureJacoco(rootProject, variantName)
                            }
                        }
                    }
            }
        }
    }
}
