plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
}

android {
    namespace = "com.louisfn.somovie.data.repository"
}

dependencies {
    test()

    implementation(project(":common"))
    implementation(project(":data:network"))
    implementation(project(":data:mapper"))
    implementation(project(":data:datastore"))
    implementation(project(":data:database"))
    implementation(project(":domain:model"))
    implementation(project(":domain:repository"))

    implementation(Libraries.Paging.runtime)
    implementation(Libraries.Paging.compose)
    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)
    implementation(Libraries.Coroutines.android)

    testImplementation(project(":test:testfixtures:android"))
    testImplementation(project(":test:testfixtures:kotlin"))
    testImplementation(project(":test:shared:android"))
}
