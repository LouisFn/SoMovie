@file:Suppress("PropertyName", "VariableNaming")

plugins {
    id(Plugins.SOMOVIE_ANDROID_APPLICATION)
    id(Plugins.SOMOVIE_ANDROID_COMPOSE)
    id(Plugins.SOMOVIE_ANDROID_HILT)
}

android {
    namespace = "com.louisfn.somovie"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    coil(versionCatalog)
    moshi(versionCatalog)

    implementation(project(":core:common"))
    implementation(project(":core:logger"))
    implementation(project(":core:imageurlprovider"))
    implementation(project(":domain:model"))
    implementation(project(":domain:usecase"))
    implementation(project(":domain:exception"))
    implementation(project(":data:network"))
    implementation(project(":data:repository"))
    implementation(project(":ui:common"))
    implementation(project(":ui:component"))
    implementation(project(":ui:theme"))
    implementation(project(":feature:navigation"))
    implementation(project(":feature:home:container"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.retrofit.core)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.insets.ui)
    implementation(libs.timber)

    debugImplementation(libs.leakcanary)
    debugImplementation(libs.flipper.core)
    debugImplementation(libs.flipper.network)
    debugImplementation(libs.soloader)
}

kapt {
    correctErrorTypes = true
}
