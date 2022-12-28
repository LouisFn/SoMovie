package plugin

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class GradleVersionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.withType<DependencyUpdatesTask> {
                rejectVersionIf {
                    isNonStable(candidate.version) && !isNonStable(currentVersion)
                }
            }
        }
    }
}

private fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
