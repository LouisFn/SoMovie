plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.gradleversions.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "somovie.android.application"
            implementationClass = "plugin.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "somovie.android.library"
            implementationClass = "plugin.AndroidLibraryPlugin"
        }
        register("androidFeature") {
            id = "somovie.android.feature"
            implementationClass = "plugin.AndroidFeaturePlugin"
        }
        register("androidCompose") {
            id = "somovie.android.compose"
            implementationClass = "plugin.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "somovie.android.hilt"
            implementationClass = "plugin.AndroidHiltPlugin"
        }
        register("kotlinLibrary") {
            id = "somovie.kotlin.library"
            implementationClass = "plugin.KotlinLibraryPlugin"
        }
        register("jacoco") {
            id = "somovie.jacoco"
            implementationClass = "plugin.JacocoPlugin"
        }
        register("jacocoMergeReports") {
            id = "somovie.jacoco.mergeallreports"
            implementationClass = "plugin.JacocoMergeAllReportsPlugin"
        }
        register("spotless") {
            id = "somovie.spotless"
            implementationClass = "plugin.SpotlessPlugin"
        }
        register("detekt") {
            id = "somovie.detekt"
            implementationClass = "plugin.DetektPlugin"
        }
        register("gradleVersions") {
            id = "somovie.gradleversions"
            implementationClass = "plugin.GradleVersionsPlugin"
        }
    }
}
