package plugin

import com.diffplug.gradle.spotless.SpotlessExtension
import getLibsVersionCatalog
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibsVersionCatalog()
            val ktlintVersion = libs.findVersion("ktlint").get().toString()

            spotless {
                format("misc") {
                    target("*.gradle", "*.md", ".gitignore")

                    trimTrailingWhitespace()
                    indentWithTabs()
                    endWithNewline()
                }

                kotlin {
                    target("**/src/**/*.kt", "**/src/**/*.kts")

                    ktlint(ktlintVersion)
                        .setUseExperimental(true)
                        .editorConfigOverride(
                            mapOf(
                                "disabled_rules" to "no-wildcard-imports,filename"
                            )
                        )
                }

                kotlinGradle {
                    target("**/*.gradle.kts")
                    ktlint(ktlintVersion)
                }
            }
        }
    }
}

private fun Project.spotless(configure: Action<SpotlessExtension>) =
    (this as ExtensionAware).extensions.configure("spotless", configure)
