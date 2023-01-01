package plugin

import getLibsVersionCatalog
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibsVersionCatalog()
            val detektCompose = libs.findPlugin("detekt.compose").get().get().toString()

            dependencies {
                detektPlugins(detektCompose)
            }

            detekt {
                buildUponDefaultConfig = true
                allRules = false // activate all available (even unstable) rules.
                config = files("$rootDir/detekt/config.yml")
                baseline = file("$rootDir/detekt/baseline.xml")
            }

            tasks.withType<Detekt>().configureEach {
                setSource(file(projectDir))
                include("**/*.kt")
                exclude("**/build/**")
                autoCorrect = true
                reports {
                    html.required.set(true)
                    sarif.required.set(false)
                    xml.required.set(false)
                    md.required.set(false)
                    txt.required.set(false)
                }
            }
        }
    }
}

private fun DependencyHandler.detektPlugins(dependencyNotation: Any): Dependency? =
    add("detektPlugins", dependencyNotation)

private fun Project.detekt(configure: Action<DetektExtension>) =
    (this as ExtensionAware).extensions.configure("detekt", configure)
