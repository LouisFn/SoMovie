plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.louisfn.somovie.test.testfixtures.android"
}

dependencies {
    implementation(project(":test:shared:kotlin"))
    implementation(project(":test:shared:android"))
    implementation(project(":test:testfixtures:kotlin"))
    implementation(project(":common"))
    implementation(project(":data:network"))
    implementation(project(":data:database"))
    implementation(project(":data:datastore"))
    implementation(project(":data:mapper"))
    implementation(project(":ui:common"))
    implementation(project(":feature:login"))

    implementation(Libraries.Coroutines.test)
    implementation(Libraries.Paging.common)
    implementation(Libraries.faker)
}
