package plugin.util

import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.testing.jacoco.tasks.JacocoReport

internal fun JacocoReport.configureJacoco(project: Project, variantName: String) {
    val projects = project.subprojects.plus(project)

    reports {
        html.required.set(true)
        csv.required.set(true)
    }

    val javaClasses = projects
        .map { proj ->
            project.fileTree("${proj.buildDir}/intermediates/javac/$variantName/classes/com")
        }
        .onEach { it.exclude(coverageExclusions) }
    val kotlinClasses = projects
        .map { proj ->
            project.fileTree("${proj.buildDir}/tmp/kotlin-classes/$variantName")
        }
        .onEach { it.exclude(coverageExclusions) }

    classDirectories.setFrom(project.files(javaClasses, kotlinClasses))

    val sources = projects.map { proj ->
        listOf(
            "${proj.projectDir}/src/main/java",
            "${proj.projectDir}/src/main/kotlin"
        )
    }.flatten()

    sourceDirectories.setFrom(project.files(sources))

    val executions = projects
        .map { proj ->
            proj.fileTree(proj.buildDir).apply {
                include(
                    "/outputs/code_coverage/${variantName}AndroidTest/connected/**/coverage.ec",
                    "/outputs/unit_test_code_coverage/${variantName}UnitTest/test${variantName.capitalized()}UnitTest.exec"
                )
            }
        }

    @Suppress("SpreadOperator")
    executionData(*executions.toTypedArray())
}

private val coverageExclusions = listOf(
    "**/databinding/**/*.*",
    "**/android/databinding/*Binding.*",
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "**/Dagger*.*",
    "**/*\$Builder.*",
    "**/*_Provide*Factory.*",
    "**/*_MembersInjector.*",
    "android/**/*.*",
    "**/*\$Lambda$*.*",
    "**/*\$inlined$*.*",
    "**/*_Impl*",
    "**/*_Factory*",
    "**/di/*.*"
)
