import com.android.build.api.dsl.TestedExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id(Plugins.androidApplication) version Plugins.Versions.androidGradle apply false
    id(Plugins.androidLibrary) version Plugins.Versions.androidGradle apply false
    id(Plugins.kotlinAndroid) version Plugins.Versions.kotlin apply false
    id(Plugins.ksp) version Plugins.Versions.ksp apply false
    id(Plugins.jvm) version Plugins.Versions.jvm apply false
    id(Plugins.detekt) version Plugins.Versions.detekt
    id(Plugins.spotless) version Plugins.Versions.spotless
    id(Plugins.gradleVersionPlugin) version Plugins.Versions.gradleVersionPlugin
}

buildscript {
    dependencies {
        classpath(Plugins.hiltGradle)
    }
}

subprojects {
    project.plugins.applyAndroidBaseConfig(project)

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs =
                freeCompilerArgs + (
                "-opt-in=" +
                    "kotlin.Experimental," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview," +
                    "androidx.paging.ExperimentalPagingApi"
                )
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                        project.buildDir.absolutePath + "/compose_compiler"
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                        project.buildDir.absolutePath + "/compose_compiler"
                )
            }
        }
    }
}

//region Detekt

dependencies {
    detektPlugins(Plugins.detektCompose)
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

//endregion

//region Spotless/ktlint

spotless {
    format("misc") {
        target("*.gradle", "*.md", ".gitignore")

        trimTrailingWhitespace()
        indentWithTabs()
        endWithNewline()
    }

    kotlin {
        target("**/src/**/*.kt", "**/src/**/*.kts")
        ktlint(Plugins.Versions.ktlint)
            .setUseExperimental(true)
            .editorConfigOverride(
                mapOf(
                    "disabled_rules" to "no-wildcard-imports,filename"
                )
            )
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint(Plugins.Versions.ktlint)
    }
}

//endregion

//region Android base config

fun PluginContainer.applyAndroidBaseConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin ->
                project.extensions
                    .getByType<AppExtension>()
                    .apply { baseAndroidConfig() }
            is LibraryPlugin ->
                project.extensions
                    .getByType<LibraryExtension>()
                    .apply { baseAndroidConfig() }
        }
        project.extensions
            .findByType<TestedExtension>()
            ?.apply { baseAndroidTestConfig() }
    }
}

fun BaseExtension.baseAndroidConfig() {
    compileSdkVersion(AppConfig.compileSdkVersion)

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libraries.Versions.composeCompiler
    }
}

fun TestedExtension.baseAndroidTestConfig() {
    (project.findProperty("testBuildType") as? String)?.let {
        testBuildType = it
    }
}

//region Gradle Version Plugins

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

//endregion
