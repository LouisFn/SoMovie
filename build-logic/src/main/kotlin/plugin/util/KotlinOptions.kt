package plugin.util

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinOptions() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)

            freeCompilerArgs.add(
                "-opt-in=" +
                    "kotlin.Experimental," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview," +
                    "androidx.paging.ExperimentalPagingApi," +
                    "androidx.compose.foundation.layout.ExperimentalLayoutApi," +
                    "androidx.compose.material.ExperimentalMaterialApi",
            )

            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.layout.buildDirectory.asFile.get().absolutePath + "/compose_compiler",
                    ),
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.layout.buildDirectory.asFile.get().absolutePath + "/compose_compiler",
                    ),
                )
            }
        }
    }
}
