plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
    id(Plugins.SOMOVIE_ANDROID_COMPOSE)
}

android {
    namespace = "com.louisfn.somovie.ui.component"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    coil(versionCatalog)

    implementation(project(":core:common"))
    implementation(project(":domain:model"))
    implementation(project(":ui:common"))
    implementation(project(":ui:theme"))
}
