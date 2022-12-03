plugins {
    id(Plugins.kotlin)
    id(Plugins.ksp)
    kotlin(Plugins.kapt)
}

dependencies {
    moshi()

    implementation(Libraries.Coroutines.android)

    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)
}
