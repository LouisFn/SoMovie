package plugin

import Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport
import plugin.util.capitalized
import plugin.util.configureJacoco
import plugin.util.getAndroidComponentsExtension

class JacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.JACOCO)
            }

            val extension = getAndroidComponentsExtension()
            extension.onVariants { variant ->
                val unitTestTaskName = "test${variant.name.capitalized()}UnitTest"
                val androidTestCoverageReportTaskName =
                    "create${variant.name.capitalized()}AndroidTestCoverageReport"

                tasks.register(
                    "jacoco${variant.name.capitalized()}Report",
                    JacocoReport::class,
                ) {
                    dependsOn(unitTestTaskName, androidTestCoverageReportTaskName)

                    group = "Reporting"
                    description = "Generate Jacoco coverage reports for the ${variant.name} build."

                    configureJacoco(target, variant.name)
                }
            }
        }
    }
}
