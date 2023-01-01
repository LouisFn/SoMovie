plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
    id(Plugins.SOMOVIE_ANDROID_HILT)
}

android {
    namespace = "com.louisfn.somovie.data.database"

    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
        }
    }
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    androidTest(versionCatalog)

    implementation(project(":core:common"))
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    androidTestImplementation(libs.androidx.room.testing)

    ksp(libs.androidx.room.compiler)
}
